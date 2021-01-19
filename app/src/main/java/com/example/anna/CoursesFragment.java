package com.example.anna;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoursesFragment extends Fragment implements  View.OnClickListener {

    MainActivity main;
    private Button layout_LearnVocabulary;
    private NavController navController;

    public CoursesFragment() {
        // Required empty public constructor
    }

    public static CoursesFragment newInstance(String strArg1) {
        CoursesFragment fragment = new CoursesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Arg1", strArg1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!(getActivity() instanceof MainCallbacks)) {
//            throw new IllegalStateException("Activity must implement MainCallbacks");
//        }
        main = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout fragment_courses = (LinearLayout) inflater.inflate(R.layout.fragment_courses, null);
        return fragment_courses;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        layout_LearnVocabulary = view.findViewById(R.id.layout_LearnVocabulary);

        layout_LearnVocabulary.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_LearnVocabulary: {
                navController.navigate(R.id.action_coursesFragment_to_learnVocabulary);
                break;
            }
        }
    }
}