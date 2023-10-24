package com.hdogmbh.budgettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mEmailLogin;
    private EditText mPasswordLogin;
    private Button btnLogin;
    private TextView newAccount;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login();
    }
    private void login(){
        mEmailLogin = findViewById(R.id.email_login);
        mPasswordLogin = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.btn_login);
        newAccount = findViewById(R.id.new_account);
        forgotPassword = findViewById(R.id.forget_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailLogin = mEmailLogin.getText().toString().trim();
                String passwordLogin = mPasswordLogin.getText().toString().trim();
                if (TextUtils.isEmpty(emailLogin)||TextUtils.isEmpty(passwordLogin)){
                    //can be added Toast message in here.
                    mEmailLogin.setError("Email and Password are required");
                    return;
                }
            }
        });
        // register new account and intent
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });
        //reset Password || forgot password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
            }
        });
    }
}