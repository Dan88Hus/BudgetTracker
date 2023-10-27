package com.hdogmbh.budgettracker;

import android.app.AlertDialog;
import android.os.Bundle;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    //Floating Buttons textview
    private TextView expenseText;
    private TextView incomeText;

    //isOpen
    private boolean isOpen = false; //closed by default

    //Animation objects
    private Animation mOpen,mClose;

    //Firebase variables
    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDb;
    private DatabaseReference mExpenseDb;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //create firebase instance
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mIncomeDb = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        mExpenseDb = FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);

        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment__dashboard, container, false);

        // integrate the buttons to fragment

        mainFloatingButton = myview.findViewById(R.id.mainFloatingButton);
        incomeButton = myview.findViewById(R.id.incomeButton);
        expenseButton = myview.findViewById(R.id.expenseButton);

        // integrate the buttons to fragment
        expenseText = myview.findViewById(R.id.expenseText);
        incomeText = myview.findViewById(R.id.incomeText);

        //Animation integrating to class
        mOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.enlarge_anim);
        mClose = AnimationUtils.loadAnimation(getActivity(), R.anim.shrink_anim);

        //onClickListeners

        mainFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                // if its open it close and vice-versa
                if (isOpen){
                    incomeButton.startAnimation(mClose);
                    expenseButton.startAnimation(mClose);
                    incomeButton.setClickable(false);
                    expenseButton.setClickable(false);
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
    // to assign onClickListener on floating buttons
    private void insertData(){

        incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeDataInput();
            }
        });

        expenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    // this method will save the income amount, it inflates the dialog
    private void incomeDataInput(){

        System.out.println("println incomeDataInput runs");

        AlertDialog.Builder incomeDialog = new AlertDialog.Builder(getActivity());

        LayoutInflater incomeLayoutInflater = LayoutInflater.from(getActivity());

        View incomeView = incomeLayoutInflater.inflate(R.layout.layout_data_input,null);

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
                Long amount = Long.parseLong(inputAmount.getText().toString().trim());

                if (TextUtils.isEmpty(type)){
                    inputType.setError("Enter type");
                    return;

                }
                if (amount.equals(null)){
                    inputAmount.setError("Enter amount");
                    return;
                }



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