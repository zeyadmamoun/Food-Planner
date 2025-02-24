package com.example.foodiesapp.plan.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodiesapp.MealsApplication;
import com.example.foodiesapp.databinding.FragmentPlanBinding;
import com.example.foodiesapp.model.meal.DatabaseMeal;
import com.example.foodiesapp.plan.presenter.PlanPresenter;
import com.example.foodiesapp.plan.view.adapters.PlanMealsAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PlanFragment extends Fragment implements PlanContract {
    FragmentPlanBinding binding;
    PlanPresenter presenter;
    PlanMealsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MealsApplication app = (MealsApplication) requireActivity().getApplicationContext();
        presenter = new PlanPresenter(app.getContainer().getMealsRepository(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlanBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new PlanMealsAdapter(getActivity(),this);
        binding.planMealsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.planMealsRv.setAdapter(adapter);

        binding.calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth, 2, 0, 0); // Set 02:00:00 as time

            // Format the date
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            String date = sdf.format(calendar.getTime());
            presenter.getMealsByDate(date);
        });
    }

    @Override
    public void assignPlanAdapter(List<DatabaseMeal> planMeals) {
        binding.planMealsRv.setVisibility(View.VISIBLE);
        binding.emptyPlanLl.setVisibility(View.INVISIBLE);
        adapter.setList(planMeals);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyListIcon() {
        binding.planMealsRv.setVisibility(View.INVISIBLE);
        binding.emptyPlanLl.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }
}