package cn.netbuffer.eventbustest.event;

public class MessageEventInAsync {

    private String data;

    public MessageEventInAsync(String data) {
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
        return "MessageEventInAsync{" +
                "data='" + data + '\'' +
                '}';
    }
}