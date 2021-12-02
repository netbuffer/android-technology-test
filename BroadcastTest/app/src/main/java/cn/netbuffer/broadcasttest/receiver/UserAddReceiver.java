package cn.netbuffer.broadcasttest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class UserAddReceiver extends BroadcastReceiver {

    private static final String TAG = "UserAddReceiver";
    public static final String USERADD_ACTION="cn.netbuffer.broadcasttest.broadcast.useradd";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "receive broadcast=" + intent);
        Toast.makeText(context, "receive:" + intent.getAction(), Toast.LENGTH_SHORT).show();
    }

}