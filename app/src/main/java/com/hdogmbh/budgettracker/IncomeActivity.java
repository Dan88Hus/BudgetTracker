package com.hdogmbh.budgettracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hdogmbh.budgettracker.dataInput_Controllers.IncomeInput;


public class IncomeActivity extends AppCompatActivity {
    private FirebaseFirestore budgetTrackerDb = FirebaseFirestore.getInstance();
    private CollectionReference incomeInputRef = budgetTrackerDb.collection("BudgetTracker");
    private IncomeAdapter incomeAdapter ;
    private String uid;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        setupRecyclerView(uid);
    }

    private void setupRecyclerView(String uid) {
        Query query = incomeInputRef.whereEqualTo("uid",uid);

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