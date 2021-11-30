package cn.netbuffer.alarmtest.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import java.util.Date;
import cn.netbuffer.alarmtest.constant.Constants;
import cn.netbuffer.alarmtest.service.ExactExecuteService;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive context=" + context);
        Intent startServiceIntent = new Intent(context, ExactExecuteService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(startServiceIntent);
        } else {
            context.startService(startServiceIntent);
        }
    }

    public static void exactExecuteReceiver(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent();
        intent.setAction("cn.netbuffer.alarmtest.action.execute_exact_receiver_task");
        intent.setClass(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Constants.START_EXACT_EXECUTE_SERVICE_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 10 * 1000, pendingIntent);
        android.util.Log.i(TAG, Thread.currentThread().getName() + " set at " + new Date());
        Toast.makeText(context, "set", Toast.LENGTH_LONG).show();
    }

}
