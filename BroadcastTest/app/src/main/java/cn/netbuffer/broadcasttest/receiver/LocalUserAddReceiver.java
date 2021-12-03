package cn.netbuffer.broadcasttest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class LocalUserAddReceiver extends BroadcastReceiver {

    private static final String TAG = "LocalUserAddReceiver";
    public static final String USERADD_ACTION="cn.netbuffer.broadcasttest.broadcast.local.useradd";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "receive local broadcast=" + intent);
        Toast.makeText(context, "receive local:" + intent.getAction(), Toast.LENGTH_SHORT).show();
        //中断广播消息的后续传递过程
//        abortBroadcast();
    }

}