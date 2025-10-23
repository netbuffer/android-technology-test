package cn.netbuffer.xlog_test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Comparator;

public class LogViewerActivity extends AppCompatActivity {

    private TextView logTextView;
    private Button refreshButton;
    private String logPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_viewer);
        
        logTextView = findViewById(R.id.log_text_view);
        refreshButton = findViewById(R.id.refresh_button);
        
        // 获取日志路径
        logPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xlogtest/logs";
        
        // 加载日志
        loadLogs();
        
        // 设置刷新按钮点击事件
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLogs();
            }
        });
    }

    private void loadLogs() {
        // 先显示加载提示
        logTextView.setText("正在加载最新日志...");
        
        // 在后台线程读取日志文件
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder result = new StringBuilder();
                
                try {
                    // 获取最新的日志文件
                    File logDir = new File(logPath);
                    if (!logDir.exists() || !logDir.isDirectory()) {
                        result.append("日志目录不存在: " + logPath);
                    } else {
                        File[] files = logDir.listFiles();
                        if (files == null || files.length == 0) {
                            result.append("日志目录为空: " + logPath);
                        } else {
                            // 按修改时间排序，获取最新的文件（最新的在前面）
                            Arrays.sort(files, new Comparator<File>() {
                                @Override
                                public int compare(File f1, File f2) {
                                    return Long.compare(f2.lastModified(), f1.lastModified());
                                }
                            });
                            
                            File latestLogFile = files[0];
                            result.append("最新日志文件: " + latestLogFile.getName() + "\n");
                            result.append("文件大小: " + formatFileSize(latestLogFile.length()) + "\n");
                            result.append("最后修改时间: " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(latestLogFile.lastModified())) + "\n\n");
                            
                            // 读取日志文件内容，从文件末尾开始读取最新的日志
                            try (BufferedReader reader = new BufferedReader(new FileReader(latestLogFile))) {
                                // 先读取所有行到内存中
                                java.util.List<String> lines = new java.util.ArrayList<>();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    lines.add(line);
                                }
                                
                                // 显示最新的日志行，从后往前显示
                                final int MAX_LINES = 100; // 增加显示行数
                                int startLine = Math.max(0, lines.size() - MAX_LINES);
                                
                                for (int i = startLine; i < lines.size(); i++) {
                                    result.append(lines.get(i)).append("\n");
                                }
                                
                                if (lines.size() > MAX_LINES) {
                                    result.append("\n... (仅显示最新的").append(MAX_LINES).append("行日志，共").append(lines.size()).append("行)");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    result.append("读取日志文件失败: " + e.getMessage());
                }
                
                // 在主线程更新UI
                final String finalResult = result.toString();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        logTextView.setText(finalResult);
                    }
                });
            }
        }).start();
    }
    
    // 格式化文件大小显示
    private String formatFileSize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else {
            return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
        }
    }
}