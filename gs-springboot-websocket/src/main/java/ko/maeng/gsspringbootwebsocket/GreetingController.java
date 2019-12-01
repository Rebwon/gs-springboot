package ko.maeng.gsspringbootwebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings") // 응답을 보낼 주소
    public Greeting greeting(HelloMessage message) throws Exception{
        Thread.sleep(1000); // simulated delay
        // Greeting클래스로 응답 전달. json 형식으로 전달됨.
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
