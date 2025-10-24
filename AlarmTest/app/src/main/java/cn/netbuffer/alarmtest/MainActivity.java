package cn.netbuffer.alarmtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.Manifest;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.util.Date;
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

    private static final int REQUEST_CODE_MANAGE_EXTERNAL_STORAGE = 1001;
    private static final int REQUEST_CODE_PERMISSION_SETTINGS = 1002;

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 对于Android 11及以上，使用MANAGE_EXTERNAL_STORAGE权限
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE_MANAGE_EXTERNAL_STORAGE);
            }
        } else {
            // 对于Android 10及以下，使用传统的存储权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_MANAGE_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_MANAGE_EXTERNAL_STORAGE) {
            // 检查是否所有请求的权限都被授予
            boolean allGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            if (allGranted) {
                // 权限被授予
                Log.i(TAG, "存储权限已授予");
            } else {
                // 权限被拒绝，检查是否需要向用户解释为什么需要这些权限
                boolean shouldShowRationale = false;
                for (String permission : permissions) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                        shouldShowRationale = true;
                        break;
                    }
                }

                if (!shouldShowRationale) {
                    // 用户选择了"不再询问"，跳转到应用设置页面
                    showPermissionSettingsDialog();
                } else {
                    // 用户拒绝了权限请求但没有选择"不再询问"
                    Log.i(TAG, "存储权限被拒绝");
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MANAGE_EXTERNAL_STORAGE) {
            // 检查MANAGE_EXTERNAL_STORAGE权限是否被授予
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    Log.i(TAG, "所有文件访问权限已授予");
                } else {
                    Log.i(TAG, "所有文件访问权限被拒绝");
                }
            }
        } else if (requestCode == REQUEST_CODE_PERMISSION_SETTINGS) {
            // 从设置页面返回，重新检查权限
            requestPermissions();
        }
    }

    private void showPermissionSettingsDialog() {
        // 这里可以显示一个对话框，解释为什么需要权限并引导用户去设置页面
        Toast.makeText(this, "需要存储权限才能正常工作，请在设置中开启", Toast.LENGTH_LONG).show();
        
        // 直接跳转到应用设置页面
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CODE_PERMISSION_SETTINGS);
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