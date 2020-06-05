package com.example.bookmanage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("SyntaxError")
public class AccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);
        final String UserId = MainActivity.UserID;

        DBHelper dbHelper = new DBHelper(getContext());
        final SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String sql = "SELECT user_id,pw,name,mail,phone FROM USERS WHERE user_id='"+ UserId +"'";
        final Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToNext();

        TextView user_id = (TextView) getView().findViewById(R.id.user_id);
        TextView pw = (TextView) getView().findViewById(R.id.pw);
        TextView name = (TextView) getView().findViewById(R.id.name);
        TextView mail = (TextView) getView().findViewById(R.id.mail);
        TextView phone = (TextView) getView().findViewById(R.id.phonenumber);

        user_id.setText(cursor.getString(0));
        pw.setText(cursor.getString(1));
        name.setText(cursor.getString(2));
        mail.setText(cursor.getString(3));
        phone.setText(Integer.toString(cursor.getInt(4)));

        Button dropout = (Button)getView().findViewById(R.id.dropout);
        dropout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                String sql1 = "SELECT rent_num FROM USERS WHERE user_id='"+UserId +"'";
                Cursor rent = sqLiteDatabase.rawQuery(sql1,null);
                rent.moveToNext();
                int rent_num = rent.getInt(0);

                if (rent_num == 0){

                dialog.setMessage("정말 탈퇴하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String deletdSQL = "DELETE FROM USERS WHERE user_id ='"+UserId+"'";
                                sqLiteDatabase.execSQL(deletdSQL);
                                Toast t = Toast.makeText(getContext(),"탈퇴처리되었습니다.",Toast.LENGTH_SHORT);
                                t.show();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Toast t = Toast.makeText(getContext(),"취소되었습니다.",Toast.LENGTH_SHORT);
                                t.show();
                            }
                        });}
                else{
                    dialog.setMessage("반납하지 않은 도서가 존재합니다.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            };
                dialog.show();

        }
        });
    }




        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }
}
