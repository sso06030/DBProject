package com.example.bookmanage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
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

    private ListView statisticsView;
    private StaticListAdapter adapter;
    private List<Statistic> statisticList;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);
        String UserID = MainActivity.UserID;

        TextView totalrentNum = (TextView)getView().findViewById(R.id.totalBookNum);

        DBHelper dbHelper = new DBHelper(getActivity());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor =  database.rawQuery("SELECT rent_num FROM USERS WHERE user_id='" + UserID +"'",null);
        cursor.moveToNext();
        int book_num = cursor.getInt(0);

        totalrentNum.setText(Integer.toString(book_num)+"권");
        statisticsView = (ListView) getView().findViewById(R.id.rentListView);
        statisticList = new ArrayList<Statistic>();
        adapter = new StaticListAdapter(getContext().getApplicationContext(),statisticList);
        statisticsView.setAdapter(adapter);

        statisticList.clear();

        cursor = database.rawQuery("SELECT BORROW.rent_date,BORROW.return_date,RENT.book_id,RENT.rent_num FROM Borrow NATURAL JOIN RENT WHERE RENT.user_id='"+ UserID +"'",null);

        while(cursor.moveToNext()){
            String rent_date = cursor.getString(0);
            String return_date = cursor.getString(1);
            String book_id = cursor.getString(2);
            int rent_num = cursor.getInt(3);

            Cursor cursor1 = database.rawQuery("SELECT title,author FROM BOOKS WHERE book_id='"+book_id+"'",null);
            cursor1.moveToNext();
            String Title = cursor1.getString(0);
            String Author = cursor1.getString(1);

            Statistic statistic = new Statistic(Author,Title,rent_date,return_date,rent_num);
            statisticList.add(statistic);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }
}
