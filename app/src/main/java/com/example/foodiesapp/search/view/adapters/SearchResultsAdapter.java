package com.example.foodiesapp.search.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodiesapp.R;
import com.example.foodiesapp.model.searchResults.QueryResult;
import com.example.foodiesapp.search.view.SearchContract;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchItemViewHolder> {
    Context context;
    SearchContract contract;
    List<QueryResult> resultList = new ArrayList<>();

    public SearchResultsAdapter(Context context,SearchContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void updateList(List<QueryResult> newList) {
        resultList.clear();
        resultList.addAll(newList);
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_result_item, parent, false);
        return new SearchItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        QueryResult result = resultList.get(position);
        holder.resultName.setText(result.getName());
        holder.resultDesc.setText(result.getName());

        if (result.getImage() != null)
            Glide.with(context).load(result.getImage()).into(holder.resultImage);
        else
            holder.resultImage.setImageResource(result.getImageSrc());

        holder.parent.setOnClickListener(view -> {
            contract.NavigateToFilteredMeals(result.getName());
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }
}

class SearchItemViewHolder extends RecyclerView.ViewHolder {
    CardView parent;
    ImageView resultImage;
    TextView resultName;
    TextView resultDesc;

    public SearchItemViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView.findViewById(R.id.resultItem);
        resultImage = itemView.findViewById(R.id.result_image);
        resultName = itemView.findViewById(R.id.result_name);
        resultDesc = itemView.findViewById(R.id.result_description);
    }
}
