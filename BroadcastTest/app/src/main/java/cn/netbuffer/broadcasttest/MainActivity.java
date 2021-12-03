package cn.netbuffer.broadcasttest;

import static cn.netbuffer.broadcasttest.receiver.UserAddReceiver.USERADD_ACTION;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import cn.netbuffer.broadcasttest.receiver.UserAddReceiver;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver userAddReceiver = new UserAddReceiver();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(userAddReceiver);
        Log.i(TAG, "unregister userAddReceiver");
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(USERADD_ACTION);
        registerReceiver(userAddReceiver, intentFilter);
        Log.i(TAG, "register userAddReceiver");
    }

    public void sendBroadcast(View view) {
        Intent intent = new Intent(USERADD_ACTION);
        Bundle bundle = new Bundle();
        bundle.putString("key", "your-value");
        intent.putExtras(bundle);
        sendBroadcast(intent);
        Log.i(TAG, "sendBroadcast userAddReceiver");
    }

    public void sendOrderedBroadcast(View view) {
        Intent intent = new Intent(USERADD_ACTION);
        Bundle bundle = new Bundle();
        bundle.putString("key", "your-value");
        intent.putExtras(bundle);
        sendOrderedBroadcast(intent, null);
        Log.i(TAG, "sendOrderedBroadcast userAddReceiver");
    }
}