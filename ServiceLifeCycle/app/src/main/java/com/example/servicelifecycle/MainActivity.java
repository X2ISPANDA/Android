package com.example.servicelifecycle;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button getData = null;
    private Button start = null;
    private Button stop = null;
    private Button bind = null;
    private Button unbind = null;
    private MyService.Mybinder mybinder = null;
    private static final String TAG = "MAINACTIVITY";

    private ServiceConnection serviceConnection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button)findViewById(R.id.start);
        stop = (Button)findViewById(R.id.stop);
        bind = (Button)findViewById(R.id.bind);
        unbind = (Button)findViewById(R.id.unbind);
        getData=(Button)findViewById(R.id.getData);

        serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG,"MainActivity's onServiceConnected has invoked!");
                mybinder =(MyService.Mybinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG,"MainActivity's onServiceDisconnected has invoked!");
            }


        };

        final Intent intent = new Intent(MainActivity.this,MyService.class);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setAction("cn.edu.nuc.MY_SERVICE");
                startService(intent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });
        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE);
            }
        });

        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(serviceConnection);
            }
        });

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"count="+mybinder.getCount(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
