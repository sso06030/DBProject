package com.example.bookmanage;

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
    private Spinner genre_spinner;
    private String titleGenre;

    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);

        final EditText titleView = (EditText) getView().findViewById(R.id.searchTitle);
        final Button searchButton = (Button) getView().findViewById(R.id.searchButton);
        final ListView bookListView = (ListView) getView().findViewById(R.id.list);

        titleView.setVisibility(View.GONE);

        final RadioGroup search_Radio = (RadioGroup)getView().findViewById(R.id.titleGenreGroup);
        genre_spinner = (Spinner) getView().findViewById(R.id.genreSpinner);

        search_Radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group,int checked){
                RadioButton get_titleGenre = (RadioButton) getView().findViewById(checked);
                titleGenre = get_titleGenre.getText().toString();

                if(titleGenre.equals("장르")){
                    titleView.setVisibility(View.GONE);
                    genre_spinner.setVisibility(View.VISIBLE);
                    genreAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.genre,android.R.layout.simple_spinner_dropdown_item);
                    genre_spinner.setAdapter(genreAdapter);
                }else{
                    genre_spinner.setVisibility(View.GONE);
                    titleView.setVisibility(View.VISIBLE);
                }
            }

    });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book, container, false);
    }
}
