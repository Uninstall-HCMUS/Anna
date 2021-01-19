package com.example.anna;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anna.data.DBDictionaryManager;
import com.example.anna.model.Word;
import com.google.firebase.database.DatabaseReference;

import java.util.LinkedList;
import java.util.List;

public class EnglishToVietnameseFragment extends Fragment {

    private AutoCompleteTextView txt_SearchBox;
    private Button btn_Search;
    private TextView txt_English, txt_Type, txt_Vietnamese, txt_Spec, txt_isStar;
    private List<Word> ListWord = new LinkedList<Word>();
    private List<String> words = new LinkedList<String>();

    public EnglishToVietnameseFragment() {
        // Required empty public constructor
    }

    public static EnglishToVietnameseFragment newInstance(String param1, String param2) {
        EnglishToVietnameseFragment fragment = new EnglishToVietnameseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_english_to_vietnamese, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txt_SearchBox = view.findViewById(R.id.txt_SearchBox);
        btn_Search = view.findViewById((R.id.btn_Search));
        txt_English = view.findViewById(R.id.txt_English);
        txt_Type = view.findViewById(R.id.txt_Type);
        txt_Vietnamese = view.findViewById(R.id.txt_Vietnamese);

        DBDictionaryManager dbDictionaryManager = new DBDictionaryManager(getActivity());
        //dbDictionaryManager.deleteDB();
        dbDictionaryManager.createDataBase();
        dbDictionaryManager.openDataBase();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String department_temp = sharedPreferences.getString(getString(R.string.DEPARTMENT), "");

        ListWord = dbDictionaryManager.GetAll(department_temp);
        words = dbDictionaryManager.GetAllEnglish(department_temp);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, words);
        txt_SearchBox.setAdapter(arrayAdapter);

        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word();
                if (txt_SearchBox.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Mời bạn nhập vào khung search", Toast.LENGTH_SHORT).show();
                } else {
                    word = dbDictionaryManager.searchWord(txt_SearchBox.getText().toString());

                    txt_English.setText(word.getmEnglish().toString());
                    if (word.getmType().toString().equals("")) {
                        txt_Type.setText("");
                    } else {
                        txt_Type.setText("(" + word.getmType().toString() + ")");
                    }
                    txt_Vietnamese.setText(word.getmVietnamese().toString());
                }
                hideSoftKeyboard(getActivity());
            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
    }
}