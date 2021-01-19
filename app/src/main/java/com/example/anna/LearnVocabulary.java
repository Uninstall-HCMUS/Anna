package com.example.anna;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.anna.data.DBDictionaryManager;
import com.example.anna.model.Word;
import com.google.android.material.card.MaterialCardView;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LearnVocabulary#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearnVocabulary extends Fragment {

    private final int QUANTITY_OF_VOCABULARIES = 20;

    private MaterialCardView vocabularyCard;
    private TextView vocabulary;
    private TextView vocabularyType;
    private TextView meaning;
    private TextView txtLearnedWords;
    private Button btnRemember;
    DBDictionaryManager dictionaryManager;
    List<Word> vocabularies;
    private int index = 0;
    private int learnedWords = 0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LearnVocabulary() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LearnVocabulary.
     */
    // TODO: Rename and change types and number of parameters
    public static LearnVocabulary newInstance(String param1, String param2) {
        LearnVocabulary fragment = new LearnVocabulary();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn_vocabulary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vocabularyCard = view.findViewById(R.id.vocabularyCard);
        vocabulary = view.findViewById(R.id.vocabulary);
        vocabularyType = view.findViewById(R.id.vocabularyType);
        meaning = view.findViewById(R.id.meaning);
        btnRemember = view.findViewById(R.id.btnRemember);
        txtLearnedWords = view.findViewById(R.id.txtLearnedWords);

        dictionaryManager = new DBDictionaryManager(getActivity());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String department_temp = sharedPreferences.getString(getString(R.string.DEPARTMENT), "");
        vocabularies = dictionaryManager.GetVocabularies(department_temp, QUANTITY_OF_VOCABULARIES);
        for (Word word : vocabularies) {
            if (word.getIsStar() == 1) {
                learnedWords++;
            }
        }
        txtLearnedWords.setText(learnedWords + " / " + QUANTITY_OF_VOCABULARIES);

        vocabulary.setText(vocabularies.get(index).getmEnglish());
        vocabularyType.setText("/" + vocabularies.get(index).getmType() + "/");
        meaning.setText(vocabularies.get(index).getmVietnamese());
        vocabularyCard.setChecked(vocabularies.get(index).getIsStar() == 1);


        vocabularyCard.setOnClickListener(new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {

                // Single tap here.
                meaning.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onDoubleClick(View view) {

                // Double tap here.
                meaning.setVisibility(View.VISIBLE);
            }
        }));
        //  use this to define your own interval
        //  }, 100));

        btnRemember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vocabularyCard.setChecked(!vocabularyCard.isChecked());
                if (vocabularies.get(index).getIsStar() == 1) {
                    learnedWords--;
                    vocabularies.get(index).setIsStar(0);
                } else {
                    learnedWords++;
                    vocabularies.get(index).setIsStar(1);
                }
                txtLearnedWords.setText(learnedWords + " / " + QUANTITY_OF_VOCABULARIES);
            }
        });

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                                //vuốt sang trái
                                if (index != QUANTITY_OF_VOCABULARIES - 1) {
                                    index++;
                                } else {
                                    index = 0;
                                }

                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                                //vuốt sang phải
                                if (index != 0) {
                                    index--;
                                } else {
                                    index = QUANTITY_OF_VOCABULARIES - 1;
                                }

                            }
                        } catch (Exception e) {
                            // nothing
                        } finally {
                            vocabulary.setText(vocabularies.get(index).getmEnglish());
                            vocabularyType.setText("/" + vocabularies.get(index).getmType() + "/");
                            meaning.setText(vocabularies.get(index).getmVietnamese());
                            vocabularyCard.setChecked(vocabularies.get(index).getIsStar() == 1);
                            meaning.setVisibility(View.INVISIBLE);
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
    }
}
