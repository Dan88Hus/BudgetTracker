package com.hdogmbh.budgettracker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class IncomeActivityTest {
    private FirebaseFirestore budgetTrackerDb = FirebaseFirestore.getInstance();
    private CollectionReference incomeInputRef = budgetTrackerDb.collection("BudgetTracker");

    @Test
    public void calculateSumIncomeTest() {
        incomeInputRef.whereEqualTo("uid", "obT03ebqJmai8G58hfZutU9Lcvz2").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    double sumIncome = calculateSumIncome(task.getResult());
                    System.out.println("println: sum "+sumIncome);
                }
                assertEquals("Expected income sum: ",1000.0,calculateSumIncome(task.getResult()),0.1);
            }

        });

    }

    public double calculateSumIncome(QuerySnapshot querySnapshot) {
        double sumIncome = 0.0;

        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            // Assuming 'incomeAmount' is the field you want to sum
            Double amount = document.getDouble("incomeAmount");
            if (amount != null) {
                sumIncome += amount;
            }
        }
        return sumIncome;
    }
}
