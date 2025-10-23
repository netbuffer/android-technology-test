package cn.netbuffer.xlog_test;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSONObject;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Comparator;
import android.os.Handler;
import android.os.Looper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.netbuffer.xlog_test.logpattern.XLogTestLogFlattener;

public class MainActivity extends AppCompatActivity {

    private Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xlogtest/logs";
        requestPermissions(); // 请求权限
        initXLog(path);
        TextView textView = findViewById(R.id.text);
        textView.setText("日志路径:" + path);
        logger = XLog.tag("MainActivity").build();
    }

    private void requestPermissions() {
        // Android 10及以上需要MANAGE_EXTERNAL_STORAGE权限
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            if (!android.os.Environment.isExternalStorageManager()) {
                // 跳转到管理存储权限设置页面
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        } else {
            // Android 10以下使用旧的权限请求方式
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }
    
    // 处理权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            // 这里可以添加权限被授予或拒绝的处理逻辑
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            
            if (!allGranted) {
                // 权限被拒绝，可以提示用户或跳转到设置页面
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }
    }

    private void initXLog(String logPath) {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(LogLevel.ALL)
//                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.WARN)
                .enableThreadInfo()
                .enableStackTrace(5)
                .enableBorder()
                .build();
        Printer androidPrinter = new AndroidPrinter(true);
        Printer filePrinter = new FilePrinter
                .Builder(logPath)
                .flattener(new XLogTestLogFlattener())
                .fileNameGenerator(new DateFileNameGenerator())
                .backupStrategy(new NeverBackupStrategy())
                .build();
        XLog.init(config, androidPrinter, filePrinter);
    }

    public void debug(View view) {
        XLog.d("debug message %s", view.toString());
    }

    public void info(View view) {
        XLog.i("info message %s", view.toString());
    }

    public void warn(View view) {
        XLog.w("warn message %s", view.toString());
    }

    public void error(View view) {
        XLog.e("error message %s", view.toString());
    }

    public void map(View view) {
        Map map = new HashMap();
        map.put("key1", 1);
        map.put("key2", "value2");
        XLog.d(map);
        XLog.d("map data=%s", map);
    }

    public void json(View view) {
        JSONObject json = new JSONObject();
        json.put("key1", 1);
        json.put("key2", "value2");
        XLog.json(json.toJSONString());
    }

    public void logger(View view) {
        logger.d("使用单独的logger打印日志");
    }

    public void viewLogs(View view) {
        // 检查权限
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R && !android.os.Environment.isExternalStorageManager()) {
            // 没有足够的存储权限，请求权限
            requestPermissions();
            return;
        }
        
        // 有权限，启动LogViewerActivity来显示日志
        Intent intent = new Intent(MainActivity.this, LogViewerActivity.class);
        startActivity(intent);
    }

}