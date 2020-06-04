 package com.example.bookmanage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class StatisticsBookListAdaptor extends BaseAdapter {

    private Context context;
    private List<Book> bookList;

    public StatisticsBookListAdaptor(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int i) {return bookList.get(i);}

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.book, null);

        TextView book_Id = (TextView) v.findViewById(R.id.bookID);
        TextView ISBN = (TextView) v.findViewById(R.id.bookISBN);
        TextView Title = (TextView)v.findViewById(R.id.bookTitle);
        TextView bookAuthor = (TextView)v.findViewById(R.id.bookAuthor);
        TextView Genre = (TextView) v.findViewById(R.id.bookGenre);
        TextView publisher = (TextView) v.findViewById(R.id.bookPublisher);
        TextView price = (TextView) v.findViewById(R.id.bookPrice);

        book_Id.setText(String.valueOf(bookList.get(i).getBook_id()));
        ISBN.setText(bookList.get(i).getISBN());
        Title.setText(bookList.get(i).getTitle());
        bookAuthor.setText(bookList.get(i).getAuthor());
        Genre.setText(bookList.get(i).getGenre());
        publisher.setText(bookList.get(i).getPublisher());
        price.setText(String.valueOf(bookList.get(i).getPrice()));

        v.setTag(bookList.get(i).getTitle());
        return v;
    }
}
