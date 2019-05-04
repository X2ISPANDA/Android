package com.example.librarydemo_94151;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button createButton;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;

    private MyDatabaseHelper databaseHelper;

    private EditText bookNameET;
    private EditText authorET;
    private EditText pageET;
    private EditText priceET;

    private String bookName;
    private String authorName;
    private int page = 0;
    private double price = 0;

    private MyAdapter myAdapter;
    private List<Book> list = new ArrayList<>();
    private RecyclerView recyclerView;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createButton = findViewById(R.id.create_table_button);
        addButton = findViewById(R.id.add_button);
        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.delete_button);
        bookNameET = findViewById(R.id.book_name);
        authorET = findViewById(R.id.author_name);
        pageET = findViewById(R.id.page);
        priceET = findViewById(R.id.price);
        recyclerView = findViewById(R.id.recycler_view);
        myAdapter = new MyAdapter(list, MainActivity.this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        databaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 1);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.getWritableDatabase();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookName = bookNameET.getText().toString();
                authorName = authorET.getText().toString();
                String pa = pageET.getText().toString();
                String pr = priceET.getText().toString();
                if (bookName.equals("") || authorName.equals("") || pa.equals("") || pr.equals("")) {
                    Toast.makeText(MainActivity.this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
                } else {
                    page = Integer.valueOf(pa);
                    price = Double.valueOf(pr);
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("name", bookName);
                    values.put("author", authorName);
                    values.put("pages", page);
                    values.put("price", price);
                    db.insert("Book", null, values);
                    values.clear();
                    Toast.makeText(MainActivity.this, "插入信息成功", Toast.LENGTH_SHORT).show();
                    initData();
                }
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookName = bookNameET.getText().toString();
                String pr = priceET.getText().toString();

                if (pr.equals("") || bookName.equals("")) {

                    Toast.makeText(MainActivity.this, "请输入有效的书名和价钱",
                            Toast.LENGTH_SHORT).show();
                } else {
                    price = Double.valueOf(priceET.getText().toString());
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("price", price);
                    db.update("Book", values, "name=?",
                            new String[]{bookName});
                    Toast.makeText(MainActivity.this, bookName + "的价钱已被改为" + price,
                            Toast.LENGTH_SHORT).show();
                    initData();
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookName = bookNameET.getText().toString();
                if (bookName.equals("")) {
                    Toast.makeText(MainActivity.this, "请输入正确的书名", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    db.delete("Book", "name=?", new String[]{bookName});
                    Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    initData();
                }
            }
        });
    }

    private void initData() {
        bookNameET.setText("");
        authorET.setText("");
        pageET.setText("");
        priceET.setText("");
        price = 0;
        page = 0;
        bookName = "";
        authorName = "";
        queryData();
    }

    private void queryData() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        list.clear();
        Cursor cursor = db.query("Book", null, null, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setBookName(cursor.getString(cursor.getColumnIndex("name")));
                book.setAuthorName(cursor.getString(cursor.getColumnIndex("author")));
                book.setPage(cursor.getInt(cursor.getColumnIndex("pages")));
                book.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                list.add(book);
                Log.d(TAG, "queryData: " + book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        myAdapter.notifyDataSetChanged();
    }
}
