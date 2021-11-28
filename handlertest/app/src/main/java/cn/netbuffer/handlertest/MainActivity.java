package cn.netbuffer.handlertest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Date;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Handler mainThreadHandler1 = new Handler();
    private Handler mainThreadHandler2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Log.i(TAG, Thread.currentThread().getName() + " mainThreadHandler2.Callback.handleMessage msg=" + msg);
            Log.i(TAG, "get data=" + msg.getData());
            //继续后续Handler.handleMessage方法的执行
            return false;
        }
    }) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.i(TAG, Thread.currentThread().getName() + " mainThreadHandler2.handleMessage msg=" + msg);
        }
    };
    private Handler mainThreadHandler3 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Log.i(TAG, Thread.currentThread().getName() + " mainThreadHandler3.Callback.handleMessage msg=" + msg);
            Log.i(TAG, "get data=" + msg.getData());
            //阻止后续Handler.handleMessage方法的执行
            return true;
        }
    }) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.i(TAG, Thread.currentThread().getName() + " mainThreadHandler3.handleMessage msg=" + msg);
        }
    };
    private TextView textView;
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.btn1);
        textView = findViewById(R.id.text);
    }

    public void testMainThreadHandler1(View view) {
        Log.i(TAG, "btn1 click");
        mainThreadHandler1.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, Thread.currentThread().getName() + " execute at " + new Date());
                textView.setText("mainThreadHandler1");
            }
        });
    }

    public void testMainThreadHandler1Delay(View view) {
        Log.i(TAG, "btn1_1 click at " + new Date());
        mainThreadHandler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, Thread.currentThread().getName() + " execute at " + new Date());
                textView.setText("testMainThreadHandler1Delay");
            }
        }, 1000);
    }

    public void testMainThreadHandler2(View view) {
        Log.i(TAG, "btn2 click at " + new Date());
        Message message = new Message();
        Bundle data = new Bundle();
        data.putString("your_key", "your_value");
        message.setData(data);
        mainThreadHandler2.sendMessage(message);
    }

    public void testMainThreadHandler3(View view) {
        Log.i(TAG, "btn3 click at " + new Date());
        Message message = new Message();
        Bundle data = new Bundle();
        data.putString("your_key", "your_value");
        message.setData(data);
        mainThreadHandler3.sendMessage(message);
    }
}