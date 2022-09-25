package com.example.foodutan;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodutan.databinding.FragmentRegisterBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register extends Fragment {

    private loginList loginList;
    FragmentRegisterBinding binding;
    private EditText userinput;
    private EditText emailinput;
    private EditText passwordinput;
    private EditText retypepasswordinput;
    private Button register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        //load databases and get the login array list
        loginList = new loginList();
        loginList.load(getActivity());
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userinput = (EditText) view.findViewById(R.id.userinput);
        emailinput = (EditText) view.findViewById(R.id.emailinput);
        passwordinput = (EditText) view.findViewById(R.id.passwordinput);
        retypepasswordinput = (EditText) view.findViewById(R.id.retypepassinput);
        register = (Button) view.findViewById(R.id.registerButton);

        //doing all kind of validation to prevent the app crashes
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateUsername(userinput)) {
                    if(validateEmail(emailinput)){
                        if(validatePassword(passwordinput, retypepasswordinput)) {
                            if(loginList.checkMailExisted(emailinput)){
                                Snackbar sb = Snackbar.make(view, "Email existed in Database. Please login", Snackbar.LENGTH_SHORT);
                                sb.show();
                            }else{
                                String username = userinput.getText().toString();
                                String password = passwordinput.getText().toString();
                                String email = emailinput.getText().toString();
                                loginData ld = new loginData(email, username, password);
                                loginList.add(ld);
                                Snackbar sb = Snackbar.make(view, " Registration Successful! Go to the login page to login", Snackbar.LENGTH_SHORT);
                                sb.show();
                            }
                        } else {
                            Snackbar sb = Snackbar.make(view, "The password is not matched or empty, input again", Snackbar.LENGTH_SHORT);
                            sb.show();
                        }
                    }else {
                        Snackbar sb = Snackbar.make(view, "Invalid email " + emailinput.getText(), Snackbar.LENGTH_SHORT);
                        sb.show();
                        System.out.println(emailinput.getText());
                    }
                } else {
                    Snackbar sb = Snackbar.make(view, "Username should not be empty! " + userinput.getText(), Snackbar.LENGTH_SHORT);
                    sb.show();
                }
            }

        });

        //return to the last activity
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public boolean validateEmail(EditText email) {
        String mail = email.getText().toString();
        if(!mail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            return true;
        }else {
            return false;
        }
    }

    public boolean validatePassword(EditText password, EditText retypepassword) {
        String pwd = password.getText().toString();
        String retypepwd = retypepassword.getText().toString();

        if((!pwd.equals("")) && (!retypepwd.equals("")) && pwd.equals(retypepwd)) {
            return true;
        }else {
            return false;
        }
    }

    public boolean validateUsername(EditText username) {
        String usrname = username.getText().toString();

        if(usrname.isEmpty()) {
            return false;
        }else {
            return true;
        }
    }
}