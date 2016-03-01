package com.example.interceptsms.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.interceptsms.MyApplication;
import com.example.interceptsms.service.MyService;

/**
 * 开机自启动
 * Created by kevin on 2016/3/1.
 */
public class BootStartService extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
            if(MyApplication.isBootStart){
                Intent i=new Intent(context, MyService.class);
                context.startService(i);
                Log.e("test--->>","机器启动");
            }

    }
}
