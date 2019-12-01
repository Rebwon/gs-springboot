package ko.maeng.gsspringbootwebsocket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingIntegrationTests {

	// 런타임시 할당된 포트를 주입받는 매개변수.
	@LocalServerPort
	private int port;

	// 소켓통신 클라이언트
	private SockJsClient sockJsClient;

	// 소켓통신 서버
	private WebSocketStompClient stompClient;

	// 소켓통신 헤더 정보
	private final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

	@BeforeEach //JUnit4 @Before
	public void setup(){
		// List자료구조에 SockJS 전송을 위한 클라이언트 측 Transport를 담았다.
		List<Transport> transports = new ArrayList<>();
		// Transport를 implements 받는 WebSocketTransport 안에 WebSocketClient인터페이스를 상속받은
		// StandardWebSocketClient 객체를 주입해서 WebSocket 연결을 만드는데 사용되는 컨테이너 객체를
		// 제공받아서 add()메서드에 담는다.
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));

		// 소켓통신 클라이언트에 클라이언트 측 List 자료구조에 담긴 transport객체를 담는다.
		this.sockJsClient = new SockJsClient(transports);
		// 소켓통신 클라이언트 객체를 담는다.
		this.stompClient = new WebSocketStompClient(sockJsClient);
		// 소켓통신 클라이언트 메세지 변환으로 JSON 형식 변환을 한다는 의미이다.
		this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());
	}

	@Test
	public void getGreeting() throws Exception {
		// CountDownLatch클래스는 스레드를 N개 실행할 때, 일정 개수의 스레드가 모두 끝날 때까지 기다려야만
		// 다음으로 진행하거나 다른 스레드를 실행시킬 수 있는 경우 사용한다.
		// CountDownLatch는 초기화 할때 정수값을 넣어줌으로써, 개별 스레드가 끝날 때마다
		// 카운트를 하나씩 빼서 0이 되면 개별 스레드를 종료하고 메인 스레드로 취합하는 형태이다.
		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<Throwable> failure = new AtomicReference<>();

		StompSessionHandler handler = new TestSessionHandler(failure) {
			@Override
			public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
				session.subscribe("/topic/greetings", new StompFrameHandler() {
					@Override
					public Type getPayloadType(StompHeaders stompHeaders) {
						return Greeting.class;
					}

					@Override
					public void handleFrame(StompHeaders stompHeaders, Object payload) {
						Greeting greeting = (Greeting) payload;
						try{
							assertEquals("Hello, Spring!", greeting.getContent());
						} catch (Throwable t) {
							failure.set(t);
						} finally {
							session.disconnect();
							latch.countDown();
						}
					}
				});
				try {
					session.send("/app/hello", new HelloMessage("Spring"));
				} catch (Throwable t){
					failure.set(t);
					latch.countDown();
				}
			}
		};

		this.stompClient.connect("ws://localhost:{port}/gs-guide-websocket", this.headers, handler, this.port);

		if(latch.await(3, TimeUnit.SECONDS)){
			if(failure.get() != null){
				throw new AssertionError("", failure.get());
			}
		}
		else{
			fail("Greeting not received");
		}
	}

	private class TestSessionHandler extends StompSessionHandlerAdapter{
		private final AtomicReference<Throwable> failure;

		public TestSessionHandler(AtomicReference<Throwable> failure){
			this.failure = failure;
		}

		@Override
		public void handleFrame(StompHeaders headers, Object payload) {
			this.failure.set(new Exception(headers.toString()));
		}

		@Override
		public void handleException(StompSession s, StompCommand c, StompHeaders h, byte[] p, Throwable ex) {
			this.failure.set(ex);
		}

		@Override
		public void handleTransportError(StompSession session, Throwable ex) {
			this.failure.set(ex);
		}
	}

}
