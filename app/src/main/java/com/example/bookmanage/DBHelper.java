package com.example.bookmanage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;


public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, "BookManageDB", null, DATABASE_VERSION);
    }

    private static final String UserSQL = "create table USERS (" +
            "user_id varchar2 primary key," +
            "pw varchar2 not null," +
            "name varchar2 not null," +
            "birthday date," +
            "gender varchar2, " +
            "mail varchar2, " +
            "phone number, " +
            "join_date date, " +
            "rent_num number(1), " +
            "fav_genre varchar2)";

    private static final String BookSQL = "create table BOOKS (" +
            "book_id integer primary key autoincrement," +
            "ISBN varchar2," +
            "title varchar2 ," +
            "author varchar2 ," +
            "genre varchar2 , "+
            "publisher varchar2," +
            "price number)";

    private static final String BurrowSQL = "create table BORROW (" +
            "rent_num integer primary key autoincrement, " +
            "rent_date date ," +
            "return_date date ," +
            "late_fee number)";

    private static final String ExSQL = "create table EX_USERS(" +
            "ex_num integer primary key autoincrement," +
            "exit_date date," +
            "user_id varchar2 references USERS(user_id) on delete no action)";

    private static final String RentSQL = "create table RENT(" +
            "book_id varchar2 references BOOKS(book_id) on delete cascade,"+
            "user_id varchar2 references USERS(user_id) on delete cascade,"+
            "rent_num integer references BURROW(rent_num) on delete cascade)";

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    if (!db.isReadOnly());
        {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                String query = String.format("PRAGMA foreign_keys = %s", "ON");
                db.execSQL(query);
            } else {
                db.setForeignKeyConstraintsEnabled(true);
            }
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserSQL);
        db.execSQL(BookSQL);
        db.execSQL(BurrowSQL);
        db.execSQL(ExSQL);
        db.execSQL(RentSQL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table USERS");
        db.execSQL("drop table BookSQL");
        db.execSQL("drop table BurrowSQL");
        db.execSQL("drop table ExSQL");
        db.execSQL("drop table RentSQL");

        onCreate(db);
    }
}
