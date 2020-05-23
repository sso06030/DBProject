package com.example.bookmanage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = (Spinner) findViewById(R.id.genreSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.genre, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText idView = (EditText) findViewById(R.id.idText);
        final EditText pwView = (EditText) findViewById(R.id.passwordText);
        final EditText nameView = (EditText) findViewById(R.id.nameText);
        final EditText emailView = (EditText) findViewById(R.id.emailText);
        final EditText pnumView = (EditText) findViewById(R.id.phoneText);
        final RadioGroup rg = (RadioGroup) findViewById(R.id.genderGroup);
        final Spinner genre_spinner = (Spinner) findViewById(R.id.genreSpinner);

        Button RgstButton = (Button) findViewById(R.id.registerButton);
        RgstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idView.getText().toString();
                String pw = pwView.getText().toString();
                String name = nameView.getText().toString();
                String email = emailView.getText().toString();
                String pnum = pnumView.getText().toString();
                int gender_id = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(gender_id);

                Long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                String rgst_date = (simpleDateFormat.format(date));
                String gender = rb.getText().toString();
                String genre = genre_spinner.getSelectedItem().toString();


                if ( id.equals("") || pw.equals("") || name.equals("")) {
                    Toast t = Toast.makeText(RegisterActivity.this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT);
                    t.show();
                }
                else{
                    DBHelper user = new DBHelper(getApplicationContext());
                    SQLiteDatabase database = user.getReadableDatabase();
                    Cursor cursor = user.getReadableDatabase().rawQuery("SELECT user_id FROM USERS",null);
                    boolean checkDB = false;
                    while (cursor.moveToNext()){
                        if (cursor.getString(0).equals(id)){
                            checkDB = true;
                            break;
                        }
                    }
                    if (checkDB) {
                            Toast t = Toast.makeText(RegisterActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT);
                            t.show();
                    }
                    else{
                        try{
                            int p = Integer.parseInt(pnum);
                        }catch(NumberFormatException nfe){
                            Toast t = Toast.makeText(RegisterActivity.this, "잘못된 전화번호 형식입니다.", Toast.LENGTH_SHORT);
                            t.show();
                            return;
                        };
                        int phone = Integer.parseInt(pnum);
                        DBHelper helper = new DBHelper(getApplicationContext());
                        SQLiteDatabase db = helper.getWritableDatabase();
                        db.execSQL("INSERT INTO  USERS"+ " ( " +
                                "USER_ID, PW, NAME, MAIL,JOIN_DATE,GENDER,FAV_GENRE,PHONE )" +
                                "VALUES( ?, ?, ?,?,?,?,?,? )", new Object[] {id, pw,name, email,rgst_date,gender,genre,phone});
                        Toast t = Toast.makeText(RegisterActivity.this, "가입이 완료되었습니다.",Toast.LENGTH_SHORT);
                        t.show();
                        db.close();
                        Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.startActivity(LoginIntent);

                    }
                }
            }
        });
    }
}
