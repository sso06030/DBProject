package com.example.bookmanage;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    public static String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        deleteDatabase("BookManageDB");

        UserID =getIntent().getStringExtra("userID");

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
//        sqLiteDatabase.execSQL("INSERT INTO  USERS"+ " ( " +
//                "USER_ID, PW, NAME, MAIL)" +
//                "VALUES( ?, ?, ?,? )", new Object[] {"Juhee", "wngml","juhe", "maii"});
//        sqLiteDatabase.execSQL("INSERT INTO BOOKS(ISBN,title,author,genre,publisher,price) VALUES(?,?,?,?,?,?)",new Object[]{"Qddkw2","디비!","어려워","판타지","출판사",1234});

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        noticeList.add(new Notice("공지사항입니다.", "최별 정주희 김예지", "2020-05-21"));
        noticeList.add(new Notice("공지사항입니다.", "최별 정주희 김예지", "2020-05-21"));
        noticeList.add(new Notice("공지사항입니다.", "최별 정주희 김예지", "2020-05-21"));
        noticeList.add(new Notice("공지사항입니다.", "최별 정주희 김예지", "2020-05-21"));
        noticeList.add(new Notice("공지사항입니다.", "최별 정주희 김예지", "2020-05-21"));
        noticeList.add(new Notice("공지사항입니다.", "최별 정주희 김예지", "2020-05-21"));
        noticeList.add(new Notice("공지사항입니다.", "최별 정주희 김예지", "2020-05-21"));
        noticeList.add(new Notice("공지사항입니다.", "최별 정주희 김예지", "2020-05-21"));
        noticeList.add(new Notice("공지사항입니다.", "최별 정주희 김예지", "2020-05-21"));
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(adapter);

        final Button bookButton = (Button) findViewById(R.id.bookButton);
        final Button statisticsButton = (Button) findViewById(R.id.statisticsButton);
        final Button accountButton = (Button) findViewById(R.id.accountButton);
        final LinearLayout notice = (LinearLayout) findViewById(R.id.notice);

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(v.GONE);
                bookButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                accountButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new BookFragment());
                fragmentTransaction.commit();
            }
        });

        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(v.GONE);
                bookButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                accountButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new StatisticsFragment());
                fragmentTransaction.commit();
            }
        });

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(v.GONE);
                bookButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                accountButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new AccountFragment());
                fragmentTransaction.commit();
            }
        });
    }

    private long lastTimeBackPressed;

    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-lastTimeBackPressed<1500){
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT);
        lastTimeBackPressed = System.currentTimeMillis();
    }
}