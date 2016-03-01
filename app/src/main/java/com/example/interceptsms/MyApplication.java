package com.example.interceptsms;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kevin on 2016/3/1.
 */
public class MyApplication extends Application{



    public static Context context; //全局的context
    public static boolean isBootStart=false; //是否开启自启动的标识
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        SharedPreferences preferences=getSharedPreferences("ClientInfo",MODE_PRIVATE);
        isBootStart=preferences.getBoolean("isBootStart",false);
    }
}
