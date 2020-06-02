package com.example.bookmanage;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookFragment newInstance(String param1, String param2) {
        BookFragment fragment = new BookFragment();
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

    private ArrayAdapter genreAdapter;
    private Spinner genreSpinner;
    private Button SearchButton;
    private String titleGenre = "";

    private ListView bookListView;
    private BookListAdapter adapter;
    private List<Book> BookList;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        final RadioGroup titleGenreGroup = (RadioGroup) getView().findViewById(R.id.titleGenreGroup);
        genreSpinner = (Spinner) getView().findViewById(R.id.genreSpinner);
        SearchButton = (Button) getView().findViewById(R.id.searchButton);
        final EditText titleView = (EditText) getView().findViewById(R.id.searchTitle);


        titleGenreGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton genreButton = (RadioButton) getView().findViewById(i);
                titleGenre = genreButton.getText().toString();
                SearchButton = (Button) getView().findViewById(R.id.searchButton);

                if (titleGenre.equals("장르")) {
                    titleView.setVisibility(View.GONE);
                    genreSpinner.setVisibility(View.VISIBLE);
                    genreAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.genre, android.R.layout.simple_spinner_dropdown_item);
                    genreSpinner.setAdapter(genreAdapter);
                } else {
                    genreSpinner.setVisibility(View.GONE);
                    titleView.setVisibility(View.VISIBLE);
                }
            }
        });

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper book = new DBHelper(getContext());
                SQLiteDatabase database = book.getReadableDatabase();
                String sql;

                bookListView = (ListView)getView().findViewById(R.id.bookListView);
                BookList = new ArrayList<Book>();
                adapter = new BookListAdapter(getContext().getApplicationContext(),BookList);
                bookListView.setAdapter(adapter);

                DBHelper helper = new DBHelper(getContext());
                SQLiteDatabase db = helper.getReadableDatabase();

                BookList.clear();
                if (titleGenre.equals("장르")) {
                    String search_genre = genreSpinner.getSelectedItem().toString();
                    sql = "SELECT book_id,isbn,title,author,genre,publisher,price FROM BOOKS WHERE genre='" + search_genre + "'";
                } else {
                    String search_title = titleView.getText().toString();
                    sql = "SELECT book_id,isbn,title,author,genre,publisher,price FROM BOOKS WHERE title='" + search_title + "'";
                }
                Cursor cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        int bookId = cursor.getInt(0);
                        String ISBN = cursor.getString(1);
                        String title = cursor.getString(2);
                        String author = cursor.getString(3);
                        String genre = cursor.getString(4);
                        String publisher = cursor.getString(5);
                        int price = cursor.getInt(6);

                        Book book1 = new Book(bookId, ISBN, title, author, genre, publisher, price);
                        BookList.add(book1);
                    }
                    Toast t = Toast.makeText(getContext(), "이건됨",Toast.LENGTH_SHORT);
                    t.show();
                    adapter.notifyDataSetChanged();
                } else {
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(BookFragment.this.getActivity());
                    dialog = builder.setMessage("조회된 책이 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                }
            }
        });
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book, container, false);
    }
}
