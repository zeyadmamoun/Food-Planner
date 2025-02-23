package com.example.foodiesapp.mealDetails.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiesapp.R;

import java.util.ArrayList;
import java.util.List;

public class MealStepsAdapter extends RecyclerView.Adapter<StepViewHolder> {
    private Context context;
    private List<String> stepList = new ArrayList<>();

    public MealStepsAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<String> stepList) {
        this.stepList.clear();
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.steps_card, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        String step = stepList.get(position);
        holder.stepText.setText(step);
        holder.stepNumber.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }
}

class StepViewHolder extends RecyclerView.ViewHolder {
    TextView stepNumber;
    TextView stepText;

    public StepViewHolder(@NonNull View itemView) {
        super(itemView);
        stepNumber = itemView.findViewById(R.id.step_number);
        stepText = itemView.findViewById(R.id.step_text);
    }
}