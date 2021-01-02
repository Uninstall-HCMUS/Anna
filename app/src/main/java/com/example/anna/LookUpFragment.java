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
import android.widget.Toast;

public class LookUpFragment extends Fragment implements View.OnClickListener {

    MainActivity main;
    private LinearLayout layout_EnglishToVietnamese;
    private NavController navController;

    public LookUpFragment() {
        // Required empty public constructor
    }

    public static LookUpFragment newInstance(String strArg1) {
        LookUpFragment fragment = new LookUpFragment();
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
        LinearLayout fragment_look_up = (LinearLayout) inflater.inflate(R.layout.fragment_look_up, null);
        return fragment_look_up;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        layout_EnglishToVietnamese = view.findViewById(R.id.layout_EnglishToVietnamese);

        layout_EnglishToVietnamese.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_EnglishToVietnamese: {
                navController.navigate(R.id.action_lookUpFragment_to_englishToVietnameseFragment);
                break;
            }
        }
    }
}