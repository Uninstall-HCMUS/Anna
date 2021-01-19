package com.example.anna;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.anna.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    MainActivity main;
    private NavController navController;
    Button signUpBtn, signInBtn, logoutBtn, changeDepart;
    TextView emailTextView, departmentTextView, usernameTextView;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String strArg1) {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Arg1", strArg1);
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout profileFm = (LinearLayout) inflater.inflate(R.layout.fragment_profile, null);
        return profileFm;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        signUpBtn = (Button) view.findViewById(R.id.signUpProfileFm);
        signInBtn = (Button) view.findViewById(R.id.signInProfileFm);
        logoutBtn = (Button) view.findViewById(R.id.logout);
        changeDepart = (Button) view.findViewById(R.id.selectDepart);

        emailTextView = (TextView) view.findViewById(R.id.emailProfileFm);
        departmentTextView = (TextView) view.findViewById(R.id.departProfileFm);
        usernameTextView = (TextView) view.findViewById(R.id.usernameProfileFm);

        logoutBtn.setVisibility(View.INVISIBLE);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                emailTextView.setText("");
                departmentTextView.setText("");
                usernameTextView.setText("");
                logoutBtn.setVisibility(View.INVISIBLE);
                signInBtn.setVisibility(View.VISIBLE);
                signUpBtn.setVisibility(View.VISIBLE);
                signUpBtn.setEnabled(true);
                signInBtn.setEnabled(true);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_profileFragment_to_loginFragment);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_profileFragment_to_signUpFragment);
            }
        });

        changeDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_profileFragment_to_selectDepartmentFragment);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        if (user != null) {
            userID = user.getUid();
            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    User userProfile = snapshot.getValue(User.class);

                    if (userProfile != null) {
                        String name = userProfile.username;
                        String email = userProfile.email;
                        String depart = userProfile.depart;
                        Toast.makeText(getContext(), depart, Toast.LENGTH_LONG).show();
                        usernameTextView.setText(name);
                        emailTextView.setText(email);
                        departmentTextView.setText(depart);
                        logoutBtn.setVisibility(View.VISIBLE);
                        signInBtn.setVisibility(View.INVISIBLE);
                        signUpBtn.setVisibility(View.INVISIBLE);
                    } else {
                        usernameTextView.setText("");
                        emailTextView.setText("");
                        departmentTextView.setText("");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Something went wrong.!", Toast.LENGTH_LONG);


                }
            });
        } else {

        }


    }
}