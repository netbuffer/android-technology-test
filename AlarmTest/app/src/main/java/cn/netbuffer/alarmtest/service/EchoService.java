package cn.netbuffer.alarmtest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import java.util.Date;

public class EchoService extends Service {

    private Logger Log = XLog.tag("EchoService").build();

    @Override
    public void onCreate() {
        Log.i("onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        Bundle bundle = intent.getExtras();
        Log.i("action=" + action + " bundle=" + bundle);
        Log.i(Thread.currentThread().getName() + " execute onStartCommand at " + new Date());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
