package com.hdogmbh.budgettracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mEmailRegister;
    private EditText mPasswordRegister;
    private Button btnRegister;
    private TextView haveAccount;
    private ProgressDialog mDialog;
    //Firebase
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this,0);
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
                mDialog.setMessage("Processing");
                mDialog.show();
                mAuth.createUserWithEmailAndPassword(emailRegister,passwordRegister).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //Redirect to home page
                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Registration complete",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Registration failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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