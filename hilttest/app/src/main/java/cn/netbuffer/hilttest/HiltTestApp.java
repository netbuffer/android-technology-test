package cn.netbuffer.hilttest;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import org.apache.commons.lang3.RandomStringUtils;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class HiltTestApp extends Application {

    public static Context application_context;

    @Override
    public void onCreate() {
        super.onCreate();
        application_context = getApplicationContext();
    }

    public String method() {
        Log.i(this.getClass().getSimpleName(), "method invoke");
        return RandomStringUtils.random(10, true, false);
    }

}
