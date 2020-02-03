package com.trueform.era.his.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Fragment.InputVital;
import com.trueform.era.his.Model.FoodList;
import com.trueform.era.his.R;

import java.util.List;

public class FoodNutrientAdp extends RecyclerView.Adapter<FoodNutrientAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<FoodList> foodLists;
    private InputVital inputVital=new InputVital();
    public FoodNutrientAdp(Context mCtx, List<FoodList> foodLists) {
        this.mCtx = mCtx;
        this.foodLists = foodLists;
    }

    @NonNull
    @Override
    public FoodNutrientAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_food_nutrient, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final FoodNutrientAdp.RecyclerViewHolder holder, final int i) {
        holder.txtSno.setText(String.valueOf(i+1));
        holder.txtFood.setText(foodLists.get(i).getFoodName());
        holder.txtQty.setText(foodLists.get(i).getFoodQuantity() + " " + foodLists.get(i).getUnitName());
        holder.txtTiming.setText(foodLists.get(i).getDietTiming());
    }

    @Override
    public int getItemCount() {
        return foodLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtSno, txtFood, txtQty, txtTiming;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSno =itemView.findViewById(R.id.txtSno);
            txtFood =itemView.findViewById(R.id.txtFood);
            txtQty =itemView.findViewById(R.id.txtQty);
            txtTiming =itemView.findViewById(R.id.txtTiming);
        }
    }
}
