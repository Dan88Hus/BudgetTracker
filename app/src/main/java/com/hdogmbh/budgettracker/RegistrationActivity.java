package com.hdogmbh.budgettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mEmailRegister;
    private EditText mPasswordRegister;
    private Button btnRegister;
    private TextView haveAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        register();
    }
    private void register(){
        mEmailRegister = findViewById(R.id.email_register);
        mPasswordRegister = findViewById(R.id.password_register);
        btnRegister = findViewById(R.id.btn_register);
        haveAccount = findViewById(R.id.have_account);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailRegister = mEmailRegister.getText().toString().trim();
                String passwordRegister = mPasswordRegister.getText().toString().trim();
                if (TextUtils.isEmpty(emailRegister)||TextUtils.isEmpty(passwordRegister)){
                    //can be added Toast message in here.
                    mEmailRegister.setError("Email and Password are required");
                    return;
                }

            }
        });
        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


    }
}