package com.hdogmbh.budgettracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.bottomNavBar);
        frameLayout = findViewById(R.id.frame_layout_main);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                String menuTitle = item.getTitle().toString() ;

                switch (menuTitle){
                    case "Dashboard":
                        System.out.println("println dashboard:"+menuTitle);
                        break;
                    case "Income":
                        System.out.println("println Income:"+menuTitle);
                        break;
                    case "Expense":
                        System.out.println("println Expense:"+menuTitle);
                        break;

                }
                return true;
            }
        });
    }
}