package ko.maeng.gsspringbootwebsocket;

// 서버에서 클라이언트로 응답 할 때 사용할 객체 정의.
public class Greeting {
    private String content;

    public Greeting(){}

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
