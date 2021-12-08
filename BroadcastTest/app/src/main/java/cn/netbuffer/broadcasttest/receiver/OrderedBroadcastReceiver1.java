package cn.netbuffer.broadcasttest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class OrderedBroadcastReceiver1 extends BroadcastReceiver {

    private static final String TAG = "OrderedBroadcastReceiver1";
    public static final String ORDERED_MESSAGE_ACTION = "cn.netbuffer.broadcasttest.broadcast.ordered.message";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "receive broadcast=" + intent);
        Toast.makeText(context, "receive:" + intent.getAction(), Toast.LENGTH_SHORT).show();
        boolean truncation = intent.getBooleanExtra("truncation", false);
        if (truncation) {
            Log.i(TAG, "截断消息传递");
            //中断广播消息的后续传递过程
            abortBroadcast();
        }
    }

}