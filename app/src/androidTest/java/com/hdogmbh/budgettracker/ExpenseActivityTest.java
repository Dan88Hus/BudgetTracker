package com.hdogmbh.budgettracker;

import static org.junit.Assert.*;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ExpenseActivityTest {
    private FirebaseFirestore budgetTrackerDb = FirebaseFirestore.getInstance();
    private CollectionReference expenseInputRef = budgetTrackerDb.collection("BudgetTracker");

    @Test
    public void onCreate(){
        expenseInputRef.whereEqualTo("uid", "obT03ebqJmai8G58hfZutU9Lcvz2").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    double sumExpense = 0.0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Assuming 'amount' is the field you want to sum
                        Double amount = document.getDouble("expenseAmount");
                        if (amount != null) {
                            sumExpense += amount;
                        }
                        assertEquals("Expected income sum: ",1000.0,sumExpense,0.1);
                    }
                }
            }
        });

    }
}