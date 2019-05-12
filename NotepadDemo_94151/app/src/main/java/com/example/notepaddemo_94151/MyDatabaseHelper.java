package com.example.notepaddemo_94151;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    final String CREAT_TABLE_SQL="CREATE TABLE NOTEPAD_TAB(_id integer primary key autoincrement," +
            "theme," +
            "content," +
            "date)";
    private static String TAG="MyDatabaseHelper";

    public MyDatabaseHelper(Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG,"onUpgrade:"+oldVersion+"------->"+newVersion);

    }
}
