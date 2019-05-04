package com.example.librarydemo_94151;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyHolder extends RecyclerView.ViewHolder {
    private TextView bookName;
    private TextView author;
    private TextView price;
    private TextView page;

    public MyHolder(View itemView) {
        super(itemView);
        bookName = itemView.findViewById(R.id.book_name);
        author = itemView.findViewById(R.id.author_name);
        price = itemView.findViewById(R.id.price);
        page = itemView.findViewById(R.id.page);
    }

    public void bindView(Book book) {
        bookName.setText(book.getBookName());
        author.setText(book.getAuthorName());
        price.setText(book.getPrice() + "");
        page.setText(book.getPage() + "");
    }
}
