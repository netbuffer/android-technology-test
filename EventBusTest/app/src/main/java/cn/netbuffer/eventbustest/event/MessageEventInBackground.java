package cn.netbuffer.eventbustest.event;

public class MessageEventInBackground {

    private String data;

    public MessageEventInBackground(String data) {
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
        return "MessageEventInBackground{" +
                "data='" + data + '\'' +
                '}';
    }
}