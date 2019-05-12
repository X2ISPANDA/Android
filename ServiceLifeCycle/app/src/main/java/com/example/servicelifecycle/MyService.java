package com.example.servicelifecycle;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {

    private static final String TAG = "MyService";
    private boolean quit = false;//计数子线程的循环控制变量
    private int count = 0;

    private Mybinder myBinder =new Mybinder();

    public class Mybinder extends Binder{
        public Mybinder(){
            Log.i(TAG,"MyBinder's constructor has invoked!");
        }

        public int getCount(){
            return count;
        }
    }

    @Nullable
    @Override
    //IBinder是一个接口，它用于实现Service和其他组件的数据交互；
    //该接口有一个实现类Binder，这也是个抽象类，通常我们会写一个Binder类的子类，让这个子类的对象来具体实现数据的交互
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"MyService's onBind has invoked!");
        return myBinder;
    }

    @Override
    public void onCreate() {
        Log.i(TAG,"MyService's onCreate has invoked!");
        super.onCreate();
        Intent intent = new Intent(MyService.this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("this is content title")
                .setContentText("this is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .build();

        startForeground(0,notification);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!quit){
                    try {
                        count++;
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"MyService's onDestory has invoked!");
        super.onDestroy();
        quit = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"MyService's onStartCommand has invoked!");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG,"MyService's onRebind has invoked!");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG,"MyService's onUnbind has invoked!");
        return super.onUnbind(intent);
    }
}
