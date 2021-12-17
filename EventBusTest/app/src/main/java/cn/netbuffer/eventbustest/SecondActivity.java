package cn.netbuffer.eventbustest;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import cn.netbuffer.eventbustest.event.MessageEventStick;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.i(TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

//    @Subscribe(sticky = true)
//    public void onMessageEventStick2(MessageEventStick messageEventStick) {
//        Log.i(TAG, "onMessageEventStick2 receive messageEvent=" + messageEventStick + " in " + Thread.currentThread().getName());
//    }

    @Subscribe(sticky = true)
    public void onMessageEventStick(MessageEventStick messageEventStick) {
        Log.i(TAG, "onMessageEventStick receive messageEvent=" + messageEventStick + " in " + Thread.currentThread().getName());
        //移除当前messageEventStick粘性消息，直到下次发送messageEventStick粘性消息才会接收执行
        if (messageEventStick.isRemove()) {
            EventBus.getDefault().removeStickyEvent(messageEventStick);
        }
    }

}