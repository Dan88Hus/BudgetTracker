package com.hdogmbh.budgettracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.hdogmbh.budgettracker.dataInput_Controllers.ExpenseInput;
import com.hdogmbh.budgettracker.dataInput_Controllers.IncomeInput;

public class ExpenseAdapter extends FirestoreRecyclerAdapter<ExpenseInput, ExpenseAdapter.ExpenseHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ExpenseAdapter(@NonNull FirestoreRecyclerOptions<ExpenseInput> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ExpenseHolder holder, int position, @NonNull ExpenseInput model) {
        holder.date.setText(model.getDate());
        holder.amount.setText(String.valueOf((int) model.getExpenseAmount()));
        holder.type.setText(model.getType());

    }

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout will be single card
        View recylerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_recycler_layout,parent,false);
        return new ExpenseHolder(recylerView);
    }

    class ExpenseHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView amount;
        TextView type;

        public ExpenseHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_income);
            amount = itemView.findViewById(R.id.amount_income);
            type = itemView.findViewById(R.id.explanation_income);


        }
    }
}
