package cn.netbuffer.alarmtest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

public class SampleBootReceiver extends BroadcastReceiver {

    private Logger Log = XLog.tag("SampleBootReceiver").build();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.i("onReceive intent=" + intent);
        }
    }
}
