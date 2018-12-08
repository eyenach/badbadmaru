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
import android.widget.TextView;
import android.widget.Toast;

import com.example.eyenach.badbadmaru.add_recipe.AddMenuFragment;
import com.example.eyenach.badbadmaru.favorite.FavoriteFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        initLoginBtn();
        initRegisBtn();
    }

    public void initLoginBtn(){
        Button _loginBtn = getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                EditText _email = getView().findViewById(R.id.login_email);
                String _emailStr = _email.getText().toString();

                EditText _password  = getView().findViewById(R.id.login_pwd);
                String _passStr = _password.getText().toString(); */

                mAuth.signInWithEmailAndPassword("eye.nanchoo@gmail.com", "522210220").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mUser = mAuth.getCurrentUser();
                        Log.d("LOGIN", "LOGIN WITH "+mUser.getUid());

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack(null)
//                                .replace(R.id.main_view, new FavoriteFragment())
//                                .replace(R.id.main_view, new AddMenuFragment())
//                                .replace(R.id.main_view, new UploadImage())
                                .replace(R.id.main_view, new ImgViewFragment())
                                .commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "กรุณาลงทะเบียนก่อนเข้าใช้งาน", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    void initRegisBtn(){
        TextView _regisBtn = getView().findViewById(R.id.login_resis_btn);
        _regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
//                            .replace(R.id.main_view, new ImgViewFragment())
//                            .replace(R.id.main_view, new FavoriteFragment())
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("LOGIN", "GOTO REGISTER");
            }
        });
    }
}
