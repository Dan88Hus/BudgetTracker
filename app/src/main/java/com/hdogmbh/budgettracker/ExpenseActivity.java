package com.hdogmbh.budgettracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hdogmbh.budgettracker.dataInput_Controllers.ExpenseInput;



public class ExpenseActivity extends AppCompatActivity {
    private String uid;

    private FirebaseFirestore budgetTrackerDb = FirebaseFirestore.getInstance();
    private CollectionReference expenseInputRef = budgetTrackerDb.collection("BudgetTracker");
    private ExpenseAdapter expenseAdapter ;
    private TextView expenseTextField;

    private ImageButton btnBack;
    // Initialize Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        setupRecyclerView(uid);
        btnBack = findViewById(R.id.btnBackExpense);
        expenseTextField =  findViewById(R.id.expenseTextField);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to back homeActivity
                Intent intent = new Intent(ExpenseActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        Query query = expenseInputRef.whereEqualTo("uid",uid);
        expenseInputRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    double sumExpense = 0.0;
                  for (QueryDocumentSnapshot document : task.getResult()) {
                      // Assuming 'amount' is the field you want to sum
                      Double amount = document.getDouble("expenseAmount");
                      if (amount != null) {
                          sumExpense += amount;
                      }
                  }
                    expenseTextField.setText(String.valueOf(sumExpense));
                }
            }
        });

    }

    private void setupRecyclerView(String uid) {
        Query query = expenseInputRef.whereEqualTo("uid",uid).whereNotEqualTo("expenseAmount",0);
        FirestoreRecyclerOptions<ExpenseInput> options = new FirestoreRecyclerOptions.Builder<ExpenseInput>().setQuery(query, ExpenseInput.class).build();

        expenseAdapter = new ExpenseAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.expenseRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(expenseAdapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        expenseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        expenseAdapter.stopListening();
    }
}