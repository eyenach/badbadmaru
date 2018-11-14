package com.example.eyenach.badbadmaru;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {

    FirebaseAuth mAuth;
    EditText _email, _pwd;
    String _emailStr, _pwdStr;
    Button _regisBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        initRegisBtn();
    }

    public void initRegisBtn(){

        _regisBtn = getView().findViewById(R.id.regis_regis_btn);
        _regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _email = getView().findViewById(R.id.regis_email);
                _pwd = getView().findViewById(R.id.regis_pwd);

                _emailStr = _email.getText().toString();
                _pwdStr = _pwd.getText().toString();

                createUser(_emailStr, _pwdStr);
            }
        });
    }

    void createUser(String email, String pwd){
        mAuth.createUserWithEmailAndPassword(email, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                sendVerifiedEmail(mAuth.getCurrentUser());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void sendVerifiedEmail(FirebaseUser _user){
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("REGISTER", "SEND ALREADY");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, new LoginFragment())
                        .commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
