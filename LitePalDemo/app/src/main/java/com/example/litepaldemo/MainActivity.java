package com.example.litepaldemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button create =(Button)findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
                Toast.makeText(mContext,"Create success",Toast.LENGTH_SHORT).show();
            }
        });
        Button add=(Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book=new Book();
                book.setName("共青团牛逼");
                book.setAuthor("中华人民共和国");
                book.setPages(54);
                book.setPrice(5.4);
                book.setPress("青年出版社");
                book.save();
            }
        });
        Button delete =(Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Book.class,"price<?","15");
            }
        });
        Button lookup =(Button)findViewById(R.id.lookup);
        lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books =DataSupport.findAll(Book.class);
                for(Book book:books){
                    Log.d("MainActivity","Book's name is"+book.getName());
                    Log.d("MainActivity","Book's author is"+book.getAuthor());
                    Log.d("MainActivity","Book's pages is"+book.getPages());
                    Log.d("MainActivity","Book's price is"+book.getPrice());
                    Log.d("MainActivity","Book's press is"+book.getPress());
                }
            }
        });
    }
}
