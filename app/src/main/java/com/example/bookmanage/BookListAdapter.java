package com.example.bookmanage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookListAdapter extends BaseAdapter {

    private Context context;
    private List<Book> bookList;

    public BookListAdapter(Context context, List<Book> bookList) {
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

        final TextView book_Id = (TextView) v.findViewById(R.id.bookID);
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

        final DBHelper dbHelper = new DBHelper(context);
        final SQLiteDatabase database = dbHelper.getReadableDatabase();
        final int bookId = bookList.get(i).getBook_id();
        String sql = "SELECT rent_num FROM RENT WHERE book_id = " +bookId;
        Cursor cursor = database.rawQuery(sql,null);

        final Button rentButton = (Button) v.findViewById(R.id.rentButton);
        if (cursor.getCount() != 0) {
            rentButton.setBackgroundColor(Color.GRAY);
            rentButton.setText("대여중");
            rentButton.setEnabled(false);
        }
        else{
            rentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { SQLiteDatabase db = dbHelper.getWritableDatabase();
                    String userID = MainActivity.UserID;

                    Long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.DATE,14);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                    DateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
                    String timeDate = sdFormat.format(date);
                    int RentNum = Integer.parseInt(timeDate + Integer.toString(bookId));
                    db.execSQL("INSERT INTO BORROW (RENT_NUM,RENT_DATE,RETURN_DATE)" + "VALUES(?,?,?)",new Object[]{RentNum,simpleDateFormat.format(date),simpleDateFormat.format(cal.getTime())});
                    db.execSQL("INSERT INTO RENT(book_id,user_id,rent_num) VALUES(?,?,?)",new Object[]{bookId,userID,RentNum});

//                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("대여가 완료되었습니다.");
//                    builder.setMessage("반납일은 "+simpleDateFormat.format(cal.getTime())+"입니다.");
//                    builder.setPositiveButton("확인",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            });
//                    builder.show();
                    Toast t = Toast.makeText(context,"반납일은 "+simpleDateFormat.format(cal.getTime())+"입니다.",Toast.LENGTH_SHORT);
                    t.show();

                    rentButton.setBackgroundColor(Color.GRAY);
                    rentButton.setText("대여중");
                    rentButton.setEnabled(false);;  }
            });

        }
        return v;
    }
}
