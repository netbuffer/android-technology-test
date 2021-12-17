package cn.netbuffer.eventbustest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.netbuffer.eventbustest.event.MessageEventInAsync;
import cn.netbuffer.eventbustest.event.MessageEventInBackground;
import cn.netbuffer.eventbustest.event.MessageEventInMain;
import cn.netbuffer.eventbustest.event.MessageEventStick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.eventbus_info);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventInMain messageEventInMain) {
        Log.i(TAG, "onMessageEvent receive messageEvent=" + messageEventInMain + " in " + Thread.currentThread().getName());
        textView.setText("onMessageEvent:" + messageEventInMain.getData());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEventInAsync(MessageEventInAsync messageEventInAsync) {
        Log.i(TAG, "onMessageEventInAsync receive messageEvent=" + messageEventInAsync + " in " + Thread.currentThread().getName());
        textView.setText("onMessageEventInAsync:" + messageEventInAsync.getData());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEventInBackground(MessageEventInBackground messageEventInBackground) {
        Log.i(TAG, "onMessageEventInBackground receive messageEvent=" + messageEventInBackground + " in " + Thread.currentThread().getName());
        textView.setText("onMessageEventInBackground:" + messageEventInBackground.getData());
    }

    @Subscribe
    public void onMessageEventStick(MessageEventStick messageEventStick) {
        Log.i(TAG, "onMessageEventStick receive messageEvent=" + messageEventStick + " in " + Thread.currentThread().getName());
    }

    public void messageEventInMain(View view) {
        EventBus.getDefault().post(new MessageEventInMain(String.valueOf(Math.random())));
    }

    public void messageEventInAsync(View view) {
        EventBus.getDefault().post(new MessageEventInAsync(String.valueOf(Math.random())));
    }

    public void messageEventInBackground(View view) {
        EventBus.getDefault().post(new MessageEventInBackground(String.valueOf(Math.random())));
    }

    public void openSecondActivity(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    public void messageEventStick(View view) {
        EventBus.getDefault().postSticky(new MessageEventStick(String.valueOf(Math.random()), false));
    }

    public void messageEventStickRemove(View view) {
        EventBus.getDefault().postSticky(new MessageEventStick(String.valueOf(Math.random()), true));
    }

}