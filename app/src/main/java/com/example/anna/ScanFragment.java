package com.example.anna;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.example.anna.data.DBDictionaryManager;
import com.example.anna.model.Word;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanFragment extends Fragment {
    ImageView imageView;
    TextView scanText, translateText, title;
    Button btnOpenCamera, btnTranlate, btnBack;
    LinearLayout translate;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanFragment newInstance(String param1, String param2) {
        ScanFragment fragment = new ScanFragment();
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
        FrameLayout v = (FrameLayout) inflater.inflate(R.layout.fragment_scan, null);
        imageView = v.findViewById(R.id.image);
//        translateText = v.findViewById(R.id.tranlateText);
        scanText = v.findViewById(R.id.scanText);
        translate = v.findViewById(R.id.tranlate);
        title = v.findViewById(R.id.title);
        btnOpenCamera = v.findViewById(R.id.btnOpenCamera);
        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);
            }
        });
        btnTranlate = v.findViewById(R.id.btnTranlaste);
        btnBack = v.findViewById(R.id.btnBack);
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageBitmap(bitmap);
        final FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
        btnOpenCamera.setVisibility(View.GONE);
        btnTranlate.setVisibility(View.VISIBLE);
        btnTranlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTranlate.setVisibility(View.GONE);
                btnBack.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                translate.setVisibility(View.VISIBLE);
                FirebaseVision firebaseVision = FirebaseVision.getInstance();
                FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();
                Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(firebaseVisionImage);
                task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        String s = firebaseVisionText.getText();
                        DBDictionaryManager dbDictionaryManager = new DBDictionaryManager(getActivity());
                        //dbDictionaryManager.deleteDB();
                        dbDictionaryManager.createDataBase();
                        dbDictionaryManager.openDataBase();
                        String[] scanWords = s.split("\n");
                        ArrayList<Pair<String, String>> translateWords = new ArrayList<>();
                        for (String word : scanWords) {
                            Word w = dbDictionaryManager.searchWord(word);
                            Pair<String, String> tmp;
                            if (!w.getmVietnamese().equals("")) {
                                tmp = new Pair<>(word, w.getmVietnamese());
                                translateWords.add(tmp);
                            }
                        }
                        String content = "";
                        for (Pair<String, String> w : translateWords) {
                            content += w.first + " : " + w.second + "\n";
                        }
                        title.setText("Tìm thấy " + translateWords.size() + " từ");
                        scanText.setText(content);
                    }
                });
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scanText.setText("");
                        title.setText("");
                        translate.setVisibility(View.GONE);
//                        translateText.setText("");
                        btnOpenCamera.setVisibility(View.VISIBLE);
                        btnBack.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}