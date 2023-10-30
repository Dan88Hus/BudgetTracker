package com.hdogmbh.budgettracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hdogmbh.budgettracker.dataInput_Controllers.IncomeInput;


public class IncomeActivity extends AppCompatActivity {
    private FirebaseFirestore budgetTrackerDb = FirebaseFirestore.getInstance();
    private CollectionReference incomeInputRef = budgetTrackerDb.collection("BudgetTracker");
    private IncomeAdapter incomeAdapter ;
    private String uid;
    private TextView incomeTextField;

    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        setupRecyclerView(uid);
        incomeTextField = findViewById(R.id.incomeTextField);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to back homeActivity
                Intent intent = new Intent(IncomeActivity.this,HomeActivity.class);
                startActivity(intent);

            }
        });
        Query query = incomeInputRef.whereEqualTo("uid",uid);

        incomeInputRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    double sumIncome = 0.0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Assuming 'amount' is the field you want to sum
                        Double amount = document.getDouble("incomeAmount");
                        if (amount != null) {
                            sumIncome += amount;
                        }
                    }
                    incomeTextField.setText(String.valueOf(sumIncome));
                }
            }
        });
    }

    private void setupRecyclerView(String uid) {
        Query query = incomeInputRef.whereEqualTo("uid",uid).whereNotEqualTo("incomeAmount",0);

        FirestoreRecyclerOptions<IncomeInput> options = new FirestoreRecyclerOptions.Builder<IncomeInput>().setQuery(query, IncomeInput.class).build();

        incomeAdapter = new IncomeAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(incomeAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        incomeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        incomeAdapter.stopListening();
    }

}