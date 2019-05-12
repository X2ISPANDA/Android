package com.example.forceofflineandrememberpassworddemo_94151;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseAcitivity {

    private EditText usernameEt=null;
    private EditText passwordEt=null;
    private CheckBox rememberPassword=null;
    private Button login =null;

    private SharedPreferences sharedPreferences = null;

    private SharedPreferences.Editor editor =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEt=(EditText)findViewById(R.id.usernameEt);
        passwordEt=(EditText)findViewById(R.id.passwordEt);
        rememberPassword=(CheckBox)findViewById(R.id.rememberPassword);
        login=(Button)findViewById(R.id.login);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor =sharedPreferences.edit();
        boolean isRemember =sharedPreferences.getBoolean("rememberPassword",false);

        if(isRemember){
            String usernameStr =sharedPreferences.getString("username","");
            String passwordStr =sharedPreferences.getString("password","");
            usernameEt.setText(usernameStr);
            passwordEt.setText(passwordStr);
            rememberPassword.setChecked(true);

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr=usernameEt.getText().toString().trim();
                String passwordStr=passwordEt.getText().toString().trim();
                if(usernameStr.equals("admin")&&passwordStr.equals("123456")){
                    if (rememberPassword.isChecked()) {

                        editor.putString("username",usernameStr);
                        editor.putString("password",passwordStr);
                        editor.putBoolean("rememberPassword",rememberPassword.isChecked());
                    }else {
                        editor.clear();
                    }
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"您输入的用户名或密码不正确！请重新输入",Toast.LENGTH_LONG).show();
                    usernameEt.setText("");
                    passwordEt.setText("");
                }
            }
        });
    }
}
