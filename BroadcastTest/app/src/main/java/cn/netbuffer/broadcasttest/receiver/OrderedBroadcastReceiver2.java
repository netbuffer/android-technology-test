package cn.netbuffer.broadcasttest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class OrderedBroadcastReceiver2 extends BroadcastReceiver {

    private static final String TAG = "OrderedBroadcastReceiver2";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "receive broadcast=" + intent);
        Toast.makeText(context, "receive:" + intent.getAction(), Toast.LENGTH_SHORT).show();
    }

}