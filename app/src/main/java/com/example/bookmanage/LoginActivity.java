package com.example.bookmanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText pwText = (EditText) findViewById(R.id.passwordText);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idText.getText().toString();
                String pw = pwText.getText().toString();

                DBHelper user = new DBHelper(getApplicationContext());
                SQLiteDatabase database = user.getReadableDatabase();

                if (id.equals("")) {
                    Toast t = Toast.makeText(LoginActivity.this, "아이디를 입력하십시오", Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
                Cursor cursor = user.getReadableDatabase().rawQuery("SELECT user_id FROM USERS WHERE user_id = '" +id + "'",null);
                if (cursor.getCount() != 1){
                    Toast t = Toast.makeText(LoginActivity.this, "존재하지 않는 아이디입니다.",Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
                if (pw.equals("")) {
                    Toast t = Toast.makeText(LoginActivity.this, "비밀번호를 입력하십시오", Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
                String sql = "SELECT pw FROM USERS WHERE user_id='"+id +"'";
                cursor = database.rawQuery(sql,null);
                cursor.moveToNext();

                if (!pw.equals(cursor.getString(0))) {
                    Toast t = Toast.makeText(LoginActivity.this, "비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
                else{
                    Toast t = Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT);
                    t.show();
                }

                database.close();

                Intent MainIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(MainIntent);
            }
        });

    };
}
