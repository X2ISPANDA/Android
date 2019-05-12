package com.example.musicplayerdemo_94151;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {
    //0x11表示停止状态，0x12表示播放状态，0x13表示暂停状态
    int status =0x11;
    int current = 0;
    //current存放的是当前正在播放的歌曲对应的索引值
    private MediaPlayer mediaPlayer=null;
    private AssetManager assetManager =null;
    private String[] musics=new String[]{"Life.mp3","Road.mp3","Star.mp3"};
    private ServiceReceiver serviceReceiver=null;
    public MusicService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer=new MediaPlayer();
        assetManager=getAssets();
        serviceReceiver =new ServiceReceiver();
        //动态注册serviceReceiver
        IntentFilter intentFilter =new IntentFilter(MainActivity.CONTROL);
        registerReceiver(serviceReceiver,intentFilter);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(current>=3){
                    current=0;
                }
                Intent intent =new Intent(MainActivity.UPDATE);
                intent.putExtra("current",current);
                sendBroadcast(intent);
                prepareAndPlay(musics[current]);
            }
        });
    }

    public void prepareAndPlay(String music) {
        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(music);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private class ServiceReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int control =intent.getIntExtra("control",-1);
            switch(control){
                case 1:
                    if(status == 0x12){
                        //播放器暂停播放
                        mediaPlayer.pause();
                        status = 0x13;
                    }else if(status == 0x11){
                        prepareAndPlay(musics[current]);
                        status = 0x12;
                    }else if(status == 0x13){
                        mediaPlayer.start();
                        status = 0x12;
                    }
                    break;
                case 2:
                    mediaPlayer.stop();
                    status=0x11;
                    break;
            }
            Intent sendIntent =new Intent(MainActivity.UPDATE);
            sendIntent.putExtra("update",status);
            sendIntent.putExtra("current",current);
            sendBroadcast(sendIntent);

        }


    }
}
