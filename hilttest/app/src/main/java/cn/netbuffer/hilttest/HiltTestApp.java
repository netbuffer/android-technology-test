package cn.netbuffer.hilttest;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class HiltTestApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
