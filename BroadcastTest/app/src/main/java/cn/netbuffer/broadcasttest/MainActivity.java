package cn.netbuffer.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.netbuffer.broadcasttest.receiver.LocalUserAddReceiver;
import cn.netbuffer.broadcasttest.receiver.UserAddReceiver;
import static cn.netbuffer.broadcasttest.receiver.UserAddReceiver.USERADD_ACTION;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver userAddReceiver = new UserAddReceiver();
    private BroadcastReceiver localUserAddReceiver = new LocalUserAddReceiver();
    private static final String TAG = "MainActivity";
    //本地广播管理器，只能作用在当前应用中
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        registerLocalReceiver();
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

    private void registerLocalReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocalUserAddReceiver.USERADD_ACTION);
        localBroadcastManager.registerReceiver(localUserAddReceiver, intentFilter);
        Log.i(TAG, "registerLocalReceiver userAddReceiver");
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

    public void sendLocalBroadcast(View view) {
        localBroadcastManager.sendBroadcast(new Intent(LocalUserAddReceiver.USERADD_ACTION));
    }
}