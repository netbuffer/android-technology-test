package cn.netbuffer.alarmtest.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import java.util.Date;
import cn.netbuffer.alarmtest.constant.Constants;

public class ExactExecuteService extends Service {

    private Logger Log = XLog.tag("ExactExecuteService").build();

    @Override
    public void onCreate() {
        Log.i("onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        Bundle bundle = intent.getExtras();
        Log.i("action=" + action + " bundle=" + bundle);
        Log.i(Thread.currentThread().getName() + " execute onStartCommand at " + new Date());
        exactExecuteService(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void exactExecuteService(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent();
        intent.setAction("cn.netbuffer.alarmtest.action.execute_exact_task");
        intent.setClass(context, ExactExecuteService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, Constants.START_EXACT_EXECUTE_SERVICE_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 10 * 1000, pendingIntent);
        android.util.Log.i("ExactExecuteService", Thread.currentThread().getName() + " set at " + new Date());
        Toast.makeText(context, "set", Toast.LENGTH_LONG).show();
    }

}
