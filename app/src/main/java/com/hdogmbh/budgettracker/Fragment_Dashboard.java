package com.hdogmbh.budgettracker;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hdogmbh.budgettracker.dataInput_Controllers.ExpenseInput;
import com.hdogmbh.budgettracker.dataInput_Controllers.GoalInput;
import com.hdogmbh.budgettracker.dataInput_Controllers.IncomeInput;

import java.text.DateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Dashboard extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Dashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Dashboard newInstance(String param1, String param2) {
        Fragment_Dashboard fragment = new Fragment_Dashboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //Floating buttons
    private FloatingActionButton mainFloatingButton;
    private FloatingActionButton incomeButton;
    private FloatingActionButton expenseButton;
    private FloatingActionButton individualGoalButton;

    //Floating Buttons textview
    private TextView expenseText;
    private TextView incomeText;
    private TextView individual_goal_textview;
    private TextView income_set_result;
    private TextView expense_set_result;
    private TextView individual_goal_result;
    private TextView difference_result;
    private double sumIncomeFinal = 0.0;
    private double sumExpenseFinal = 0.0;

    private double sumDifferenceFinal = 0.0;




    //isOpen
    private boolean isOpen = false; //closed by default

    //Animation objects
    private Animation mOpen,mClose;

    //Firebase variables
    private FirebaseAuth mAuth;

    private DatabaseReference mBudgetTracker;
    FirebaseFirestore firestore;
    private CollectionReference dashboardInputRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //create firebase instance
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mBudgetTracker = FirebaseDatabase.getInstance().getReference();

        firestore = FirebaseFirestore.getInstance();
        dashboardInputRef = firestore.collection("BudgetTracker");

        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment__dashboard, container, false);

        // integrate the buttons to fragment

        mainFloatingButton = myview.findViewById(R.id.mainFloatingButton);
        incomeButton = myview.findViewById(R.id.incomeButton);
        expenseButton = myview.findViewById(R.id.expenseButton);
        individualGoalButton = myview.findViewById(R.id.individualGoalButton);


        // integrate the buttons to fragment
        expenseText = myview.findViewById(R.id.expenseText);
        incomeText = myview.findViewById(R.id.incomeText);
        individual_goal_textview = myview.findViewById(R.id.individual_goal_textview);
        income_set_result = myview.findViewById(R.id.income_set_result);
        expense_set_result = myview.findViewById(R.id.expense_set_result);
        individual_goal_result = myview.findViewById(R.id.individual_goal_result);
        difference_result = myview.findViewById(R.id.difference_result);


        //Animation integrating to class
        mOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.enlarge_anim);
        mClose = AnimationUtils.loadAnimation(getActivity(), R.anim.shrink_anim);

        firebaseFetch(uid);
        //onClickListeners

        mainFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData(uid);
                // if its open it close and vice-versa
                if (isOpen){
                    incomeButton.startAnimation(mClose);
                    expenseButton.startAnimation(mClose);
                    incomeButton.setClickable(false);
                    expenseButton.setClickable(false);
                    individualGoalButton.startAnimation(mClose);
                    individualGoalButton.setClickable(false);
                    individual_goal_textview.startAnimation(mClose);
                    individual_goal_textview.setClickable(false);
                    incomeText.startAnimation(mClose);
                    expenseText.startAnimation(mClose);
                    incomeText.setClickable(false);
                    expenseText.setClickable(false);
                    isOpen = false;
                } else {
                    incomeButton.startAnimation(mOpen);
                    expenseButton.startAnimation(mOpen);
                    incomeButton.setClickable(true);
                    expenseButton.setClickable(true);
                    individualGoalButton.startAnimation(mOpen);
                    individualGoalButton.setClickable(true);
                    individual_goal_textview.startAnimation(mOpen);
                    individual_goal_textview.setClickable(true);
                    incomeText.startAnimation(mOpen);
                    expenseText.startAnimation(mOpen);
                    incomeText.setClickable(true);
                    expenseText.setClickable(true);
                    isOpen = true;
                }
            }
        });

        return myview;
    }

    private void firebaseFetch(String uid) {

        Query query = dashboardInputRef.whereEqualTo("uid",uid);
        Query queryForGoal = dashboardInputRef.whereEqualTo("uid",uid).orderBy("date", Query.Direction.DESCENDING).limit(1);

//       for incomeAmount
        dashboardInputRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    double sumIncome = 0.0;
                    double sumExpenseNew = 0.0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Assuming 'amount' is the field you want to sum
                        Double amount = document.getDouble("incomeAmount");
                        Double expense = document.getDouble("expenseAmount");
                        if (amount != null) {
                            sumIncome += amount;
                        }
                        if (expense != null) {
                            sumExpenseNew += expense;
                        }
                    }

                    sumIncomeFinal = sumIncome;
                    sumExpenseFinal = sumExpenseNew ;
                    sumDifferenceFinal = sumIncomeFinal-sumExpenseFinal;

                    difference_result.setText(String.valueOf(sumDifferenceFinal));
                    income_set_result.setText(String.valueOf(sumIncome));
                    expense_set_result.setText(String.valueOf(sumExpenseNew));
                }
            }
        });

        //       for goalAmount
        dashboardInputRef.whereEqualTo("uid",uid).whereGreaterThan("goalAmount",0).orderBy("goalAmount", Query.Direction.DESCENDING).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    double latestGoalAmount = 0.0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Assuming 'amount' is the field you want to sum
                        Double amount = document.getDouble("goalAmount");
                        if (amount != null) {
                            latestGoalAmount += amount;
                        }
                    }
                    individual_goal_result.setText(String.valueOf(latestGoalAmount));
                } else {
                    System.out.println("println: fetch goal is failed : "+task.getException().getMessage());
                }
            }
        });
        sumDifferenceFinal = sumIncomeFinal-sumExpenseFinal ;
        System.out.println("println: sum "+ sumDifferenceFinal);



    }

    // to assign onClickListener on floating buttons
    private void insertData(String uid){

        incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeDataInput(uid);
            }
        });

        expenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { expenseDataInput(uid); }
        });

        individualGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalDataInput(uid);
            }
        });



    }


    private void goalDataInput(String uid){

        AlertDialog.Builder incomeDialog = new AlertDialog.Builder(getActivity());

        LayoutInflater incomeLayoutInflater = LayoutInflater.from(getActivity());

        View incomeView = incomeLayoutInflater.inflate(R.layout.layout_data_input,null);

        incomeDialog.setTitle("Goal Data");

        incomeDialog.setView(incomeView);

        AlertDialog incomeAmountDialog = incomeDialog.create();

        EditText inputAmount = incomeView.findViewById(R.id.amount_id);
        EditText inputType = incomeView.findViewById(R.id.type_id);

        Button btnSave = incomeView.findViewById(R.id.btnSave);
        Button btnCancel = incomeView.findViewById(R.id.btnCancel);

        //clickListeners
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type =  inputType.getText().toString().trim();
                String amountString = inputAmount.getText().toString().trim();
                Long amountLong = Long.parseLong(amountString);

                if (TextUtils.isEmpty(type)){
                    inputType.setError("Enter type");
                    return;

                }
                if (TextUtils.isEmpty(amountString)){
                    inputAmount.setError("Enter Amount");
                    return;
                }
                // saving to Db

                String mDate = DateFormat.getDateInstance().format(new Date());

                GoalInput dataInput = new GoalInput(amountLong,type,mDate,uid);

//                mIncomeDb.child(id).setValue(dataInput);
                firestore.collection("BudgetTracker").add(dataInput).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(),"Goal saved to Db",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Goal failed to save Db",Toast.LENGTH_SHORT).show();
                        System.out.println("println: "+e);
                    }
                });

                incomeAmountDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeAmountDialog.dismiss();
            }
        });

        incomeAmountDialog.show();

    }

    // this method will save the income amount, it inflates the dialog
    private void incomeDataInput(String uid){

        AlertDialog.Builder incomeDialog = new AlertDialog.Builder(getActivity());

        LayoutInflater incomeLayoutInflater = LayoutInflater.from(getActivity());

        View incomeView = incomeLayoutInflater.inflate(R.layout.layout_data_input,null);

        incomeDialog.setTitle("Income Data");

        incomeDialog.setView(incomeView);

        AlertDialog incomeAmountDialog = incomeDialog.create();

        EditText inputAmount = incomeView.findViewById(R.id.amount_id);
        EditText inputType = incomeView.findViewById(R.id.type_id);

        Button btnSave = incomeView.findViewById(R.id.btnSave);
        Button btnCancel = incomeView.findViewById(R.id.btnCancel);

        //clickListeners
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type =  inputType.getText().toString().trim();
                String amountString = inputAmount.getText().toString().trim();
                Long amountLong = Long.parseLong(amountString);

                if (TextUtils.isEmpty(type)){
                    inputType.setError("Enter type");
                    return;

                }
                if (TextUtils.isEmpty(amountString)){
                    inputAmount.setError("Enter Amount");
                    return;
                }
                // saving to Db

                String mDate = DateFormat.getDateInstance().format(new Date());

                IncomeInput dataInput = new IncomeInput(amountLong,type,mDate,uid);

//                mIncomeDb.child(id).setValue(dataInput);
                firestore.collection("BudgetTracker").add(dataInput).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(),"Income saved to Db",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Income failed to save Db",Toast.LENGTH_SHORT).show();
                        System.out.println("println: "+e);
                    }
                });

                incomeAmountDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeAmountDialog.dismiss();
            }
        });

        incomeAmountDialog.show();

    }

    //ExpenseDataInput
    private void expenseDataInput(String uid){


        AlertDialog.Builder incomeDialog = new AlertDialog.Builder(getActivity());

        LayoutInflater incomeLayoutInflater = LayoutInflater.from(getActivity());

        View incomeView = incomeLayoutInflater.inflate(R.layout.layout_data_input,null);

        incomeDialog.setTitle("Expense Data");

        incomeDialog.setView(incomeView);

        AlertDialog incomeAmountDialog = incomeDialog.create();

        EditText inputAmount = incomeView.findViewById(R.id.amount_id);
        EditText inputType = incomeView.findViewById(R.id.type_id);

        Button btnSave = incomeView.findViewById(R.id.btnSave);
        Button btnCancel = incomeView.findViewById(R.id.btnCancel);

        //clickListeners
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type =  inputType.getText().toString().trim();
                String amountString = inputAmount.getText().toString().trim();
                Long amountLong = Long.parseLong(amountString);

                if (TextUtils.isEmpty(type)){
                    inputType.setError("Enter type");
                    return;

                }
                if (TextUtils.isEmpty(amountString)){
                    inputAmount.setError("Enter Amount");
                    return;
                }
                // saving to Db

                String mDate = DateFormat.getDateInstance().format(new Date());

                ExpenseInput dataInput = new ExpenseInput(amountLong,type,mDate,uid);

                firestore.collection("BudgetTracker").add(dataInput).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(),"Expense saved to Db",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Expense failed to save Db",Toast.LENGTH_SHORT).show();
                        System.out.println("println: "+e);
                    }
                });

                incomeAmountDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeAmountDialog.dismiss();
            }
        });

        incomeAmountDialog.show();

    }
}