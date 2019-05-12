package com.example.forceofflineandrememberpassworddemo_94151;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BaseAcitivity extends AppCompatActivity {

    private forceOfflineReceiver receiver = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter =new IntentFilter(MainActivity.FORCE_OFFLINE);
        receiver= new forceOfflineReceiver();
        registerReceiver(receiver,intentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(receiver != null){
            unregisterReceiver(receiver);
        }
        receiver=null;

    }

    class forceOfflineReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("警告对话框");
            builder.setMessage("您被强制下线了！！！请重新登录~");

            builder.setCancelable(false);

            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCollector.finishAll();
                    Intent intent1= new Intent(BaseAcitivity.this,LoginActivity.class);

                    startActivity(intent1);
                }
            });
        builder.show();
        }
    }
}
