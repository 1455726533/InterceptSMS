package com.example.interceptsms.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.interceptsms.MyApplication;

/**
 * Created by kevin on 2016/3/1.
 */
public class MyService extends Service{

    private ClipMessage clipMessage;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter filter=new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        clipMessage=new ClipMessage();
        registerReceiver(clipMessage,filter);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(clipMessage);
    }

    /**
     * 读取短信及复制验证码
     */
    class ClipMessage extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            //获取短信信息的方法

            Bundle bundle=intent.getExtras();
            Object[] msgByte= (Object[]) bundle.get("pdus");
            SmsMessage[] msg=new SmsMessage[msgByte.length];
            for(int i=0;i<msg.length;i++){
                msg[i]=SmsMessage.createFromPdu((byte[])(msgByte[i]));
            }


            String Addr=msg[0].getOriginatingAddress(); //发送方的号码

            //拼接完整的短信息
            String fullMsg=" ";
            for(SmsMessage smsMessage:msg){
                fullMsg+=smsMessage.getMessageBody();
            }


            String s=" "; //验证码
            int pos=-1;  //计算"码"字位置

            pos=fullMsg.indexOf("码");

            //拼接验证码,原理是基于ascii码
            if(pos!=-1)
            for(int i=0;i<fullMsg.length();i++){
                if(fullMsg.charAt(i)>=48&&fullMsg.charAt(i)<=57){
                    s+=fullMsg.charAt(i);
                }
            }

            String code=fullMsg.substring(pos-2,pos+1);//获得xx码字符串

            Log.e(code, s);//打印信息

            Toast.makeText(MyApplication.context,code+"复制成功",Toast.LENGTH_SHORT).show();//告知用户复制成功


            //由于版本问题,clipboard所在的包的位置不同,所以分版本获取

        if (android.os.Build.VERSION.SDK_INT > 11) {
            android.content.ClipboardManager c = (android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            c.setText(s);//将信息剪切到clipboard上

        } else {
            android.text.ClipboardManager c = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            c.setText(s);
        }

        }

    }
}
