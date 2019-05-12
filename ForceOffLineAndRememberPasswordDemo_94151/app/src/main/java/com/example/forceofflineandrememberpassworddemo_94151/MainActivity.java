package com.example.forceofflineandrememberpassworddemo_94151;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String FORCE_OFFLINE = "cn.edu.nuc.MainAcitivity.forceOffline";
    private Button sendBtn=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendBtn=(Button)findViewById(R.id.send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FORCE_OFFLINE);
                sendBroadcast(intent);
            }
        });
    }
}
