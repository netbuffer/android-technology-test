package cn.netbuffer.propertiestest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.util.Properties;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
    }

    private Properties readConfig(String profile) {
        Properties props = new Properties();
        try {
            props.load(getApplicationContext().getAssets().open(profile + ".properties"));
        } catch (IOException e) {
            Log.e(TAG, "读取配置文件出错:" + e.getMessage());
        }
        return props;
    }

    public void readDevConfig(View view) {
        textView.setText(readConfig("dev").toString());
    }

    public void readPrdConfig(View view) {
        textView.setText(readConfig("prd").toString());
    }
}