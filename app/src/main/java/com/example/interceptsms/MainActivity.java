package com.example.interceptsms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.example.interceptsms.service.MyService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    SharedPreferences.Editor editor;
    Intent intent;
    boolean isWorkOn;
    @Bind(R.id.start)
    Button start;
    @Bind(R.id.kill)
    Button kill;
    @Bind(R.id.boot_start)
    Button bootStart;
    @Bind(R.id.boot_kill)
    Button bootKill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        editor= getSharedPreferences("ClientInfo",MODE_PRIVATE).edit();
        intent = new Intent(Intent.ACTION_RUN);
        intent.setClass(MyApplication.context, MyService.class);
    }

    @OnClick(R.id.start)
    void start(){
        MyApplication.context.startService(intent);
        isWorkOn=true;
        Toast.makeText(MainActivity.this, "启动成功", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.kill)
    void kill(){
        MyApplication.context.stopService(intent);
        isWorkOn=false;
        Toast.makeText(MainActivity.this, "已取消复制", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.boot_start)
    void setBootStart(){
        editor.putBoolean("isBootStart", true);
        editor.commit();
        Toast.makeText(MainActivity.this, "开机自启动设置成功", Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.boot_kill)
    void cancelBootStart(){
        editor.putBoolean("isBootStart",false);
        editor.commit();
        Toast.makeText(MainActivity.this, "已取消开机自启动", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
