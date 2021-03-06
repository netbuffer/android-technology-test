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
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
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
        requestPermissions();
        initXLog(path);
        TextView textView = findViewById(R.id.text);
        textView.setText("日志路径:" + path);
        logger = XLog.tag("MainActivity").build();
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

}