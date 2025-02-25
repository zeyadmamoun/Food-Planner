package com.example.foodiesapp.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.foodiesapp.MainActivity;
import com.example.foodiesapp.MealsApplication;
import com.example.foodiesapp.R;
import com.example.foodiesapp.databinding.FragmentHomeBinding;
import com.example.foodiesapp.home.presenter.HomePresenter;
import com.example.foodiesapp.home.view.adapters.InspireMealsAdapter;
import com.example.foodiesapp.home.view.adapters.RecommendationsAdapter;
import com.example.foodiesapp.home.view.adapters.SliderTransformer;
import com.example.foodiesapp.model.meal.Meal;
import com.example.foodiesapp.model.repository.MealsRepository;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class HomeFragment extends Fragment implements HomeContract {

    FragmentHomeBinding binding;
    HomePresenter presenter;
    InspireMealsAdapter viewPagerAdapter;
    RecommendationsAdapter recommendationsAdapter;
    public static final String TAG = "HomeFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MealsApplication app = (MealsApplication) requireActivity().getApplication();
        MealsRepository repo = app.getContainer().getMealsRepository();
        presenter = new HomePresenter(repo, this);
        if (presenter.getmAuth().getCurrentUser() != null){
            app.setGuestModeOn(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.usernameTv.setText(String.valueOf(presenter.getUsername()));
        String imageUri = presenter.getUserAvatar();
        if (imageUri != null) {
            Glide.with(requireView()).load(presenter.getUserAvatar()).into(binding.imageView);
        }

        // Initializing viewPager adapter
        viewPagerAdapter = new InspireMealsAdapter(getActivity(), this);
        binding.viewpager.setAdapter(viewPagerAdapter);
        binding.viewpager.setOffscreenPageLimit(3);
        binding.viewpager.setPageTransformer(new SliderTransformer(3));

        // Initializing recommendations adapter
        recommendationsAdapter = new RecommendationsAdapter(getActivity(), this);
        binding.mealsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.mealsRv.setAdapter(recommendationsAdapter);

        presenter.getInspirationMeals();
        presenter.getRecommendationMeals();
    }

    private void showDatePicker(Meal meal) {
        // we decide the constraints of date picker
        CalendarConstraints constraintsBuilder = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now()).build();

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setCalendarConstraints(constraintsBuilder)
                .build();

        datePicker.show(getActivity().getSupportFragmentManager(), "PlanDatePicker");
        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(selection);
            String date = calendar.getTime().toString();
            presenter.addMealToPlan(meal, date);
        });
    }

    @Override
    public void assignMealsListToPager(List<Meal> meals) {
        viewPagerAdapter.setList(meals);
        viewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void assignMealsListToRecommendations(List<Meal> recommendationMeals) {
        Log.i(TAG, "assignMealsListToPager: meals list size = " + recommendationMeals.size());
        recommendationsAdapter.setList(recommendationMeals);
        recommendationsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCardClicked(String id) {
        NavDirections action = HomeFragmentDirections
                .actionHomeFragmentToMealDetailsFragment(id);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onAddToPlanClicked(Meal meal) {
        MealsApplication app = (MealsApplication) getActivity().getApplication();
        if (app.getGuestModeOn()){
            MainActivity.showDialog(Navigation.findNavController(requireView()),R.id.loginFragment,getActivity());
            return;
        }
        showDatePicker(meal);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}