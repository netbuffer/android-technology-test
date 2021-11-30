package cn.netbuffer.alarmtest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SampleBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "SampleBootReceiver:" + intent.getAction(), Toast.LENGTH_LONG).show();
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.i("SampleBootReceiver", "onReceive intent=" + intent);
            AlarmReceiver.exactExecuteReceiver(context);
        }
    }
}
