package cn.netbuffer.handlertest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
    private Handler subThreadHandler;
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

    public void testSubThreadUseHandler1(View view) {
        Log.i(TAG, "btn1_2 click at " + new Date());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, Thread.currentThread().getName() + " execute at " + new Date());
                mainThreadHandler1.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("testSubThreadUseHandler1");
                    }
                });
            }
        }, "your-thread-name").start();
    }

    public void testCreateSubThread(View view) {
        Log.i(TAG, "btn4 click at " + new Date());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                subThreadHandler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message msg) {
                        Log.i(TAG, Thread.currentThread().getName() + " subThreadHandler.Callback.handleMessage msg=" + msg);
                        return true;
                    }
                });
                Looper.loop();
            }
        }, "your-thread-name");
        thread.setDaemon(true);
        thread.start();
    }

    private static final int TESTSUBTHREADHANDLER1_MESSAGE_TYPE = 1;

    public void testSubThreadHandler1(View view) {
        Log.i(TAG, "btn4_1 click at " + new Date());
        if (subThreadHandler == null) {
            Log.w(TAG, "subThreadHandler is null");
            return;
        }
        subThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, Thread.currentThread().getName() + " execute at " + new Date());
            }
        });
        //主线程向子线程发送消息，回调在子线程中执行
        Message message = new Message();
        message.what = TESTSUBTHREADHANDLER1_MESSAGE_TYPE;
        subThreadHandler.sendMessage(message);
    }

}