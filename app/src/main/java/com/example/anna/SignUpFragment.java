package com.example.anna;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.anna.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    MainActivity main;
    private NavController navController;
    Button signUpBtn, signInBtn;
    EditText editEmail, editUsername, editPassword, editRetypePassword;

    private FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        signUpBtn = (Button) view.findViewById(R.id.signUp_SignUpFM);
        signInBtn = (Button) view.findViewById(R.id.login_SignUpFM);

        editEmail = (EditText) view.findViewById(R.id.editEmailSignUp);
        editUsername = (EditText) view.findViewById(R.id.editNameSignUp);
        editPassword = (EditText) view.findViewById(R.id.editPasswordSignUp);
        editRetypePassword = (EditText) view.findViewById(R.id.editRetypePasswordSignUp);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signUpFragment_to_loginFragment);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {

        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String username = editUsername.getText().toString().trim();
        String retypePassword = editRetypePassword.getText().toString().trim();

        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            editUsername.setError("Username is required");
            editUsername.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please provide valid email");
            ;
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editPassword.setError("Min password length should be 6 characters");
            editPassword.requestFocus();
            return;
        }
        if (retypePassword.isEmpty()) {
            editRetypePassword.setError("RetypePassword is required");
            editRetypePassword.requestFocus();
            return;
        }

        if (password.equals(retypePassword) == false) {
            editRetypePassword.setText("");
            editRetypePassword.setError("Password and RetypePassword are not match");
            editRetypePassword.requestFocus();
            return;
        }
        int result = 0;
        signUpBtn.setBackgroundResource(R.color.blue_click);
        signUpBtn.setEnabled(false);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(username, email, "");
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(),"User has been registered",Toast.LENGTH_LONG).show();
                                        signUpBtn.setBackgroundResource(R.color.blue);
                                        signUpBtn.setEnabled(true);
                                        navController.navigate(R.id.action_signUpFragment_to_loginFragment);
                                    } else {
                                        signUpBtn.setEnabled(true);
                                        Toast.makeText(getContext(),"Register failed",Toast.LENGTH_LONG).show();
                                        signUpBtn.setBackgroundResource(R.color.blue);
                                    }
                                }
                            });
                        }
                        else{
                            signUpBtn.setEnabled(true);
                            Toast.makeText(getContext(),"Register failed",Toast.LENGTH_LONG).show();
                            signUpBtn.setBackgroundResource(R.color.blue);
                        }
                    }
                });
    }
}