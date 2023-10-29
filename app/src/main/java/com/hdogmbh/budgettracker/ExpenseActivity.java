package com.hdogmbh.budgettracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hdogmbh.budgettracker.dataInput_Controllers.ExpenseInput;
import com.hdogmbh.budgettracker.dataInput_Controllers.IncomeInput;

public class ExpenseActivity extends AppCompatActivity {
    private String uid;

    private FirebaseFirestore budgetTrackerDb = FirebaseFirestore.getInstance();
    private CollectionReference expenseInputRef = budgetTrackerDb.collection("BudgetTracker");
    private ExpenseAdapter expenseAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        setupRecyclerView(uid);
    }

    private void setupRecyclerView(String uid) {
        Query query = expenseInputRef.whereEqualTo("uid",uid);
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