package cn.netbuffer.alarmtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import java.util.Date;
import java.util.List;
import cn.netbuffer.alarmtest.constant.Constants;
import cn.netbuffer.alarmtest.receiver.AlarmReceiver;
import cn.netbuffer.alarmtest.service.EchoService;
import cn.netbuffer.alarmtest.service.ExactExecuteService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        requestPermissions();
    }

    private void requestPermissions() {
        XXPermissions.with(this)
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                        } else {
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(MainActivity.this, permissions);
                        } else {
                        }
                    }
                });
    }

    public void setRepeatingService(View view) {
        Intent intent = new Intent();
        intent.setAction("cn.netbuffer.alarmtest.action.execute_echo_task");
        intent.setClass(this, EchoService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, Constants.START_ECHO_SERVICE_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 10 * 1000, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        Log.i(TAG, Thread.currentThread().getName() + " setRepeating at " + new Date());
        Toast.makeText(this, "setRepeating", Toast.LENGTH_LONG).show();
    }

    public void cancelPendingIntent(View view) {
        Intent intent = new Intent();
        intent.setAction("cn.netbuffer.alarmtest.action.execute_echo_task");
        intent.setClass(this, EchoService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, Constants.START_ECHO_SERVICE_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        Log.i(TAG, Thread.currentThread().getName() + " cancel at " + new Date());
        Toast.makeText(this, "cancel", Toast.LENGTH_LONG).show();
    }

    public void getNextAlarmClock(View view) {
        AlarmManager.AlarmClockInfo alarmClockInfo = alarmManager.getNextAlarmClock();
        Log.i(TAG, "getNextAlarmClock=" + alarmClockInfo);
    }

    public void setExactExecuteService(View view) {
        ExactExecuteService.exactExecuteService(this);
    }

    public void exactExecuteReceiver(View view) {
        AlarmReceiver.exactExecuteReceiver(this);
    }

}