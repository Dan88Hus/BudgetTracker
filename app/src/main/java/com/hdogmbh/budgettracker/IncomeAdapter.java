package com.hdogmbh.budgettracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.hdogmbh.budgettracker.dataInput_Controllers.IncomeInput;

import org.w3c.dom.Text;

public class IncomeAdapter extends FirestoreRecyclerAdapter<IncomeInput,IncomeAdapter.IncomeHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public IncomeAdapter(@NonNull FirestoreRecyclerOptions<IncomeInput> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull IncomeHolder holder, int position, @NonNull IncomeInput model) {
        holder.date.setText(model.getDate());
        holder.amount.setText(String.valueOf((int) model.getIncomeAmount()));
        holder.type.setText(model.getType());

    }

    @NonNull
    @Override
    public IncomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout will be single card
        View recylerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_recycler_layout,parent,false);

        return new IncomeHolder(recylerView);
    }

    class IncomeHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView amount;
        TextView type;

        public IncomeHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_income);
            amount = itemView.findViewById(R.id.amount_income);
            type = itemView.findViewById(R.id.explanation_income);


        }
    }
}
