package com.example.musicplayerdemo_94151;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageButton stopBtn =null;
    private ImageButton playBtn = null;
    private TextView songNameTv = null;
    private TextView authorTv =null;
    private MyListener myListener =null;
    public final static String CONTROL="cn.edu.nuc.MainActivity.control";
    public final static String UPDATE="cm.edu.nuc..MainActivity.update";

    private int status = 0x11;
    private int current = 0;
    private String []songNames =new String[]{"生活不止眼前的苟且","平凡之路","夜空中最亮的星"};
    private String []authors =new String[]{"许巍","朴树","逃跑计划"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopBtn = (ImageButton)findViewById(R.id.stopBtn);
        stopBtn = (ImageButton)findViewById(R.id.playBtn);
        songNameTv =(TextView)findViewById(R.id.songNameTv);
        authorTv =(TextView)findViewById(R.id.authorTv);

        IntentFilter intentFilter =new IntentFilter(MainActivity.CONTROL);
        ActivityReceiver activityReceiver = new ActivityReceiver();
        registerReceiver(activityReceiver,intentFilter);
        Intent intent = new Intent(MainActivity.this,MusicService.class);
        startActivity(intent);
    }

    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent =new Intent();
            switch (v.getId()){
                case R.id.playBtn:
                    intent.putExtra("control",1);
                    break;
                case R.id.stopBtn:
                    intent.putExtra("control",2);
                    break;

            }
            sendBroadcast(intent);
        }
    }

    private class  ActivityReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            status=intent.getIntExtra("update",-1);
            current=intent.getIntExtra("current",-1);
            if(current>=0){
                songNameTv.setText(songNames[current]);
                authorTv.setText(authors[current]);
            }

            switch (status){
                case 0x11:
                    playBtn.setImageResource(R.drawable.play);
                    break;
                case 0x12:
                    playBtn.setImageResource(R.drawable.pause);
                    break;
                case 0x13:
                    playBtn.setImageResource(R.drawable.play);
                    break;
            }
        }
    }
}
