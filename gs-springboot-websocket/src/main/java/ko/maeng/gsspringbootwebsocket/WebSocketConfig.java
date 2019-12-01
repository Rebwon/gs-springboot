package ko.maeng.gsspringbootwebsocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");

        // Server에서 사용할 접두사
        // 아래에 설정으로 Controller에서 @MessageMapping을 시도할 때,
        // /app/hello가 아닌 /hello으로 선언가능
        // RequestMapping 애노테이션과 유사함.
        config.setApplicationDestinationPrefixes("/app");
    }

    // Client가 접속할 Server의 URL
    // 웹에서 connect 버튼 클릭 시 다음과 같이 요청이 온다.
    // Request URL:http://localhost:8080/gs-guide-websocket/info?t=1488257937687
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }
}
