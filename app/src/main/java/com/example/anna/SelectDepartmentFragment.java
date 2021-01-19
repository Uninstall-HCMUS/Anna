package com.example.anna;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.anna.model.CustomCourseAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectDepartmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectDepartmentFragment extends Fragment {
    MainActivity main;
    String selectedDepartment = "";
    private NavController navController;

    ListView listView;
    String[] departmentList;

    private FirebaseUser user;
    DatabaseReference reference;
    String UserID;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectDepartmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectDepartmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectDepartmentFragment newInstance(String param1, String param2) {
        SelectDepartmentFragment fragment = new SelectDepartmentFragment();
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
        main = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_department, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        reference = FirebaseDatabase.getInstance().getReference("User");
        user = FirebaseAuth.getInstance().getCurrentUser();

        listView = (ListView) view.findViewById(R.id.listView_Department_FM);
        departmentList = new String[]{
                "Information Technology",
                "Mathematics",
                "Biology",
                "Chemistry",
                "Physical",
                "Law",
                "Economy",
                "Culture",
                "Travel"
        };

        CustomCourseAdapter customDepartmentAdapter = new CustomCourseAdapter(getContext(), R.layout.custom_department, Arrays.asList(departmentList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedDepartment = departmentList[position].toString();
                Toast.makeText(getContext(),
                        selectedDepartment,
                        Toast.LENGTH_LONG).show();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String departmemt_temp = sharedPreferences.getString(getString(R.string.DEPARTMENT), "");

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getContext());
                dlgAlert.setMessage("Do you want to change your department from " + departmemt_temp + " to " + selectedDepartment);
                dlgAlert.setTitle("Warning");
                dlgAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //update selected department to share_preference
                        editor.putString(getString(R.string.DEPARTMENT), selectedDepartment);
                        editor.commit();
                        //update selected department to firebase
                        if(user!=null){
                            UserID = user.getUid();
                            reference.child(UserID).child("depart").setValue(selectedDepartment);
                        }

                        //then switch to profile fragment
                        navController.navigate(R.id.action_selectDepartmentFragment_to_profileFragment);
                    }
                });

                dlgAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                });

                dlgAlert.create().show();


            }
        });
        listView.setAdapter(customDepartmentAdapter);
    }
}