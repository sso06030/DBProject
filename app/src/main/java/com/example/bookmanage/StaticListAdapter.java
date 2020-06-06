package com.example.bookmanage;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StaticListAdapter extends BaseAdapter {

    private Context context;
    private List<Statistic> statisticList;

    public StaticListAdapter(Context context,List<Statistic> statisticList) {
        this.context = context;
        this.statisticList = statisticList;
    }

    @Override
    public int getCount() {
        return statisticList.size();
    }

    @Override
    public Object getItem(int position) {
        return statisticList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View v = View.inflate(context,R.layout.statistics,null);

        final String UserID = MainActivity.UserID;

        TextView author = (TextView) v.findViewById(R.id.bookAuthor);
        TextView title = (TextView) v.findViewById(R.id.bookTitle);
        TextView rentDate = (TextView) v.findViewById(R.id.rentDate);
        TextView returnDate = (TextView) v.findViewById(R.id.returnDate);
        final TextView rentNum = (TextView) v.findViewById(R.id.totalBookNum);


        author.setText(statisticList.get(position).getBookAuthor());
        title.setText(statisticList.get(position).getBookTitle());
        returnDate.setText(statisticList.get(position).getReturnDate());
        rentDate.setText(statisticList.get(position).getRentDate());

        v.setTag(statisticList.get(position).getBookTitle());

        final Button returnButton = (Button)v.findViewById(R.id.returnButton);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                DBHelper dbHelper = new DBHelper(context);
                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
                String sql = "DELETE FROM BORROW WHERE rent_num='"+statisticList.get(position).getRent_num()+"'";
                sqLiteDatabase.execSQL(sql);
                sql = "DELETE FROM RENT WHERE rent_num='" +statisticList.get(position).getRent_num()+"'";
                sqLiteDatabase.execSQL(sql);
                sql = "SELECT rent_num FROM USERS WHERE user_id='"+ UserID+"'";
                Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
                cursor.moveToNext();
                int book_num = cursor.getInt(0);
                book_num-=1;

                sql = "UPDATE USERS SET rent_num='"+book_num+"' WHERE user_id='"+UserID+"'";
                sqLiteDatabase.execSQL(sql);

                Toast t = Toast.makeText(context,"반납되었습니다.",Toast.LENGTH_LONG);
                t.show();

                returnButton.setBackgroundColor(Color.GRAY);
                returnButton.setText("반납됨");
                returnButton.setClickable(false);
//
                rentNum.setText(Integer.toString(book_num));
            }
        });

        return v;
    }
}
