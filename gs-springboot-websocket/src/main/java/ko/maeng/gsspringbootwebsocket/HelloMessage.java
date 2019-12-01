package ko.maeng.gsspringbootwebsocket;

// 클라이언트가 서버로 보낼 요청 메세지 구조 정의
public class HelloMessage {
    private String name;

    public HelloMessage(){}

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
