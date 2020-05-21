package com.example.bookmanage;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, "BookManageDB", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String UserSQL = "create table USERS (" +
                "user_id varchar2 primary key," +
                "pw varchar2 not null," +
//                "name not null," +
                "birthday date," +
                "gender char(1), " +
                "mail varchar2, " +
                "phone number, "+
                "join_date date, " +
                "rent_num number(1), " +
                "fav_genre varchar2)";
//        String BookSQL = "create table BOOKS (" +
//                "book_id varchar2 primary key," +
//                "ISBN varchar2,"+
//                "title varchar2 ,"+
//                "author varchar2 ,"+
//                "genre varchar2 , " +
//                "publisher varchar2," +
//                "price number)";
//        String BurrowSQL = "create table BURROW (" +
//                "rent_num integer primary key autoincrement, " +
//                "rent_date date ," +
//                "return_date date ," +
//                "late_fee number)";
////        String RentSQL= "create table RENT(" +
////        )
//        String ExSQL = "create table EX_USERS(" +
//                "ex_num number primary key autoincrement," +
//                "exit_date date," +
//                "user_id references USERS(user_id) on delete set null"+
//                ")";

        db.execSQL(UserSQL);
//        db.execSQL(BookSQL);
//        db.execSQL(BurrowSQL);
////        db.execSQL(RentSQL);
//        db.execSQL(ExSQL);
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //버전 변경될 때마다 호출
        if (newVersion == DATABASE_VERSION){
            db.execSQL(("drop table USERS"));
        }
    }
}
