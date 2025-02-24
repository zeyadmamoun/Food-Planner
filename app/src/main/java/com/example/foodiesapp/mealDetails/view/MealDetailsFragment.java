package com.example.foodiesapp.mealDetails.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.foodiesapp.MealsApplication;
import com.example.foodiesapp.databinding.FragmentMealDetailsBinding;
import com.example.foodiesapp.mealDetails.presenter.MealDetailsPresenter;
import com.example.foodiesapp.mealDetails.view.adapter.IngredientsDetailAdapter;
import com.example.foodiesapp.mealDetails.view.adapter.MealStepsAdapter;
import com.example.foodiesapp.model.meal.Meal;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MealDetailsFragment extends Fragment implements MealDetailsContract {
    MealDetailsFragmentArgs navArgs;
    FragmentMealDetailsBinding binding;
    IngredientsDetailAdapter ingredientsDetailAdapter;
    MealStepsAdapter mealStepsAdapter;
    MealDetailsPresenter presenter;
    private static final String TAG = "MealDetailsFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navArgs = MealDetailsFragmentArgs.fromBundle(getArguments());
        MealsApplication app = (MealsApplication) getActivity().getApplication();
        presenter = new MealDetailsPresenter(app.getContainer().getMealsRepository(), this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String id = navArgs.getMealId();

        ingredientsDetailAdapter = new IngredientsDetailAdapter(getActivity());
        binding.mealIngredientsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.mealIngredientsRv.setAdapter(ingredientsDetailAdapter);

        mealStepsAdapter = new MealStepsAdapter(getActivity());
        binding.cookingStepsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.cookingStepsRv.setAdapter(mealStepsAdapter);

        presenter.getMealDetails(id);

        binding.favoriteButtonOutlined.setOnClickListener(view1 -> presenter.onFavoriteButtonClicked());
        binding.favoriteButtonFilled.setOnClickListener(view1 -> presenter.onFavoriteButtonClicked());
        binding.backButton.setOnClickListener(view1 -> Navigation.findNavController(requireView()).navigateUp());
        binding.addMealButton.setOnClickListener(view1 -> showDatePicker());
    }

    private void showDatePicker() {
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
            presenter.addMealToPlan(date);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.youtubePlayerView.release();
        binding = null;
    }

    @Override
    public void assignIngredientsRecyclerView(List<String> ingredients) {
        Log.i(TAG, "assignIngredientsRecyclerView: " + ingredients.size());
        ingredientsDetailAdapter.setList(ingredients);
        ingredientsDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void assignStepsRecyclerView(List<String> steps) {
        Log.i(TAG, "assignStepsRecyclerView: " + steps.size());
        mealStepsAdapter.setList(steps);
        mealStepsAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateUI(Meal meal) {
        binding.mealName.setText(meal.getStrMeal());
        Glide.with(getActivity()).load(meal.getStrMealThumb()).into(binding.mealImage);
        binding.mealDescription.setText(meal.getStrCategory());
        binding.mealOrigin.setText(meal.getStrArea());
    }

    @Override
    public void setupVideo(String videoLink) {
        getLifecycle().addObserver(binding.youtubePlayerView);
        binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                Log.i(TAG, "onReady: " + videoLink);
                youTubePlayer.cueVideo(videoLink, 0);
            }
        });
    }

    @Override
    public void updateFavoriteStateToRemoved() {
        binding.favoriteButtonFilled.setVisibility(View.INVISIBLE);
        binding.favoriteButtonOutlined.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateFavoriteStateToAdded() {
        binding.favoriteButtonFilled.setVisibility(View.VISIBLE);
        binding.favoriteButtonOutlined.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}