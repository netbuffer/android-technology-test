package cn.netbuffer.eventbustest.event;

public class MessageEventInMain {

    private String data;

    public MessageEventInMain(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MessageEventInMain{" +
                "data='" + data + '\'' +
                '}';
    }
}