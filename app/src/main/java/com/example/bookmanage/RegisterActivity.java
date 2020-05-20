package com.example.bookmanage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


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
        final EditText emailView = (EditText) findViewById(R.id.emailText);

        Button RgstButton = (Button) findViewById(R.id.registerButton);
        RgstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idView.getText().toString();
                String pw = pwView.getText().toString();
                String email = emailView.getText().toString();

                if (id == null || id.equals("")) {
                    Toast t = Toast.makeText(RegisterActivity.this, "아이디를 입력하십시오", Toast.LENGTH_SHORT);
                    t.show();
                }else if (pw == null || pw.equals("")){
                    Toast t = Toast.makeText(RegisterActivity.this, "비밀번호를 입력하십시오", Toast.LENGTH_SHORT);
                    t.show();
                }else {

                    DBHelper helper = new DBHelper(getApplicationContext());
                    SQLiteDatabase db = helper.getWritableDatabase();
                    db.execSQL("INSERT INTO  USERS"+ " ( " +
                            "USER_ID, PW, MAIL )" +
                            "VALUES( ?, ?, ? )", new Object[] {id, pw,email});
                    Toast t = Toast.makeText(RegisterActivity.this, "가입이 완료되었습니다.",Toast.LENGTH_SHORT);
                    t.show();
                    db.close();
                }
            }
        });
    }
}
