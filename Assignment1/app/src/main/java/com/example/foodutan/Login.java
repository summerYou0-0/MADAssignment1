package com.example.foodutan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.foodutan.databinding.FragmentLoginBinding;
import com.google.android.material.snackbar.Snackbar;

public class Login extends Fragment {

    private loginList loginList;
    FragmentLoginBinding binding;
    EditText email;
    EditText password;
    Button login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        loginList = new loginList();
        loginList.load(getActivity());
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = (EditText) view.findViewById(R.id.loginmailinput);
        password = (EditText) view.findViewById(R.id.loginpassinput);
        login = (Button) view.findViewById(R.id.loginButton);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        binding.regDirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register register = new Register();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if(getActivity() != null) {
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.register_container, register);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginList.checkMailExisted(email)) {
                    if (loginList.attemptLogin(email, password)) {
                        Snackbar sb = Snackbar.make(view, "Login success", Snackbar.LENGTH_SHORT);
                        sb.show();
                    } else {
                        Snackbar sb = Snackbar.make(view, "The password is incorrect", Snackbar.LENGTH_SHORT);
                        sb.show();
                    }
                }else {
                    Snackbar sb = Snackbar.make(view, "The email is not exist in Database. Click the Top Right button to continue", Snackbar.LENGTH_SHORT);
                    sb.show();
                }
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}