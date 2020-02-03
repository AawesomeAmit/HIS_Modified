package com.trueform.era.his.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trueform.era.his.Model.FoodDetail;
import com.trueform.era.his.R;

import java.util.List;

public class MealIntakeAdp extends RecyclerView.Adapter<MealIntakeAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<FoodDetail> foodDetails;
    public MealIntakeAdp(Context mCtx, List<FoodDetail> foodDetails) {
        this.mCtx = mCtx;
        this.foodDetails = foodDetails;
    }

    @NonNull
    @Override
    public MealIntakeAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_display_input_fluid, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealIntakeAdp.RecyclerViewHolder holder, int i) {
        holder.txtFluid.setText(String.valueOf(foodDetails.get(i).getTypeName()));
        holder.txtQty.setText(String.valueOf(foodDetails.get(i).getQuantity()));
        holder.txtUnit.setText(String.valueOf(foodDetails.get(i).getUnitname()));
        holder.txtDateTime.setText(foodDetails.get(i).getIntakeDate());
    }

    @Override
    public int getItemCount() {
        return foodDetails.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtFluid,txtQty,txtUnit, txtDateTime;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFluid =itemView.findViewById(R.id.txtFluid);
            txtQty=itemView.findViewById(R.id.txtQty);
            txtUnit=itemView.findViewById(R.id.txtUnit);
            txtDateTime=itemView.findViewById(R.id.txtDateTime);
        }
    }
}
