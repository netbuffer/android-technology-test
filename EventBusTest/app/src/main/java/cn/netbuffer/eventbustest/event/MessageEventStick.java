package cn.netbuffer.eventbustest.event;

public class MessageEventStick {

    private String data;

    private boolean remove;

    public MessageEventStick(String data, boolean remove) {
        this.data = data;
        this.remove = remove;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MessageEventStick{" +
                "data='" + data + '\'' +
                ", remove=" + remove +
                '}';
    }

}