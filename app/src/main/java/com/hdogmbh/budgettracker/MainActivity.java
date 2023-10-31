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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText mEmailLogin;
    private EditText mPasswordLogin;
    private Button btnLogin;
    private TextView newAccount;
    private TextView forgotPassword;
    private ProgressDialog mDialog;
    //Firebase
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDialog = new ProgressDialog(this);
        login();
    }
    private void login(){
        mEmailLogin = findViewById(R.id.email_login);
        mPasswordLogin = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.btn_login);
        newAccount = findViewById(R.id.new_account);
        mAuth = FirebaseAuth.getInstance();

        //auto login
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // User is signed in
//            System.out.println("println: auto login");
//            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
//            startActivity(i);
//        } else {
//            // User is signed out
//            System.out.println("println: auto login failed");
//        }



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
                mDialog.setMessage("Processing");
                mDialog.show();
                mAuth.signInWithEmailAndPassword(emailLogin, passwordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        } else{
                            mDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Login NOT Successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        // register new account and intent
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });
    }
}