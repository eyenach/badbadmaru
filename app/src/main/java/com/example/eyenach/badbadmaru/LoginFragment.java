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

import com.example.eyenach.badbadmaru.add_menu.AddMenuFragment;
import com.example.eyenach.badbadmaru.favorite.FavoriteFragment;

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initLoginBtn();
    }

    public void initLoginBtn(){
        Button _loginBtn = getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _user = getView().findViewById(R.id.login_user_id);
                String _userStr = _user.getText().toString();

                EditText _password  = getView().findViewById(R.id.login_password);
                String _passStr = _password.getText().toString();

                if(_userStr.isEmpty() || _passStr.isEmpty()){
                    Toast.makeText(getActivity(), "กรุณาระบุ username or password", Toast.LENGTH_SHORT).show();
                    Log.d("LOGIN", "USER OR PASSWORD IS EMPTY");
                } else if(_userStr.equals("admin") && _passStr.equals("admin")){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
//                            .replace(R.id.main_view, new ImgViewFragment())
//                            .replace(R.id.main_view, new FavoriteFragment())
                            .replace(R.id.main_view, new AddMenuFragment())
                            .addToBackStack(null)
                            .commit();
                    Log.d("LOGIN", "GOTO HOME");
                } else {
                    Toast.makeText(getActivity(), "user and password ไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                    Log.d("LOGIN", "INVALID USER OR PASSWORD");
                }
            }
        });
    }
}
