package com.example.foodiesapp.search.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiesapp.R;
import com.example.foodiesapp.model.country.Country;
import com.example.foodiesapp.model.repository.CategoryRepository;
import com.example.foodiesapp.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountryCardViewHolder> {
    Context context;
    List<Country> countryList = new ArrayList<>();

    public CountriesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CountryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_card,parent,false);
        return new CountryCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryCardViewHolder holder, int position) {
        Country country = countryList.get(position);
        holder.countryName.setText(country.getStrArea());

        if(CategoryRepository.flagsMap.get(country.getStrArea()) != null){
            holder.countryImage.setImageResource(CategoryRepository.flagsMap.get(country.getStrArea()));
        }
    }

    public void setList(List<Country> newList){
        Log.i(SearchPresenter.TAG, "setList: form countriesAdapter " + newList.size());
        countryList.clear();
        countryList.addAll(newList);
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }
}

class CountryCardViewHolder extends RecyclerView.ViewHolder {
    CardView countryCard;
    ImageView countryImage;
    TextView countryName;

    public CountryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        countryCard = itemView.findViewById(R.id.category_card);
        countryImage = itemView.findViewById(R.id.category_image);
        countryName = itemView.findViewById(R.id.category_name);
    }
}
