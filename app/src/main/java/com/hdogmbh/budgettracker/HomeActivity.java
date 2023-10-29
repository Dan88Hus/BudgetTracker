package com.hdogmbh.budgettracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private FrameLayout frameLayout;
    private Fragment_Dashboard dashboardFragment;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //create firebase instance
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        navigationView = findViewById(R.id.bottomNavBar);
        frameLayout = findViewById(R.id.frame_layout_main);

        dashboardFragment = new Fragment_Dashboard();

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
//                        setFragment(incomeFragment);
                        Intent incomeIntent = new Intent(HomeActivity.this, IncomeActivity.class);
                        incomeIntent.putExtra("uid", uid);
                        HomeActivity.this.startActivity(incomeIntent);
                        break;
                    case "Expense":
                        System.out.println("println Expense:"+menuTitle);
//                        setFragment(expenseFragment);
                        Intent expenseIntent = new Intent(HomeActivity.this, ExpenseActivity.class);
                        expenseIntent.putExtra("uid", uid);
                        HomeActivity.this.startActivity(expenseIntent);
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