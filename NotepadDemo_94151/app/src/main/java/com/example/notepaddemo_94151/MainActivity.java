package com.example.notepaddemo_94151;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText theme =null;
    private EditText content =null;
    private EditText time =null;

    private Button chooseDate = null;
    private Button add =null;
    private Button query =null;

    private LinearLayout title=null;

    private ListView resultLV = null;

    private MyDatabaseHelper myDatabaseHelper =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chooseDate = (Button)findViewById(R.id.chooseDate);
        add=(Button)findViewById(R.id.add);
        query=(Button)findViewById(R.id.query);

        theme=(EditText)findViewById(R.id.theme);
        content=(EditText)findViewById(R.id.content);
        time=(EditText)findViewById(R.id.time);

        title=(LinearLayout)findViewById(R.id.title);
        resultLV=(ListView)findViewById(R.id.resultLV);



        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                time.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            myDatabaseHelper = new MyDatabaseHelper(MainActivity.this,"notepad.db",null,1);

            SQLiteDatabase database =myDatabaseHelper.getReadableDatabase();

            String themeStr =theme.getText().toString().trim();
            String contentStr =content.getText().toString().trim();
            String dateStr =time.getText().toString().trim();

            switch (v.getId()){
                case R.id.add:
                    title.setVisibility(View.INVISIBLE);
                    addData();
                    Toast.makeText(MainActivity.this,"添加成功！",Toast.LENGTH_LONG).show();
                    resultLV.setAdapter(null);
                    break;
                case R.id.query:
                    title.setVisibility(View.VISIBLE);
                    Cursor cursor = queryData();
                    SimpleCursorAdapter resultAdapt =new SimpleCursorAdapter(
                            MainActivity.this,
                            R.id.resultLV,
                            cursor,
                            new String[]{"_id","theme","content","date"},
                            new int[]{}
                    );
                    resultLV.setAdapter(resultAdapt);
            }
        }
    }
}
