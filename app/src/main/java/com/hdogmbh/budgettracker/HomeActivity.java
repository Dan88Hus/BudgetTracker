package com.hdogmbh.budgettracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private FrameLayout frameLayout;
    private Fragment_Dashboard dashboardFragment;
    private Fragment_Income incomeFragment;
    private Fragment_Expense expenseFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.bottomNavBar);
        frameLayout = findViewById(R.id.frame_layout_main);

        dashboardFragment = new Fragment_Dashboard();
        incomeFragment = new Fragment_Income();
        expenseFragment = new Fragment_Expense();

        setFragment(dashboardFragment);


        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                String menuTitle = item.getTitle().toString() ;

                switch (menuTitle){
                    case "Dashboard":
                        System.out.println("println dashboard:"+menuTitle);
                        setFragment(dashboardFragment);
                        break;
                    case "Income":
                        System.out.println("println Income:"+menuTitle);
                        setFragment(incomeFragment);
                        break;
                    case "Expense":
                        System.out.println("println Expense:"+menuTitle);
                        setFragment(expenseFragment);
                        break;

                }
                return true;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main,fragment);
        fragmentTransaction.commit();

    }
}