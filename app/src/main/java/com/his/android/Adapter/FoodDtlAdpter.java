package com.his.android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Model.NutriAnlzrFoodListDtlList;
import com.his.android.R;

import java.util.List;

public class FoodDtlAdpter extends RecyclerView.Adapter<FoodDtlAdpter.ViewHolder> {

    private Context context;
    private List<NutriAnlzrFoodListDtlList> viewList;

    public FoodDtlAdpter(Context context, List<NutriAnlzrFoodListDtlList> viewList) {
        this.context = context;
        this.viewList = viewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.innerview_nutrianlzr_food_dtl, viewGroup, false);
        return new FoodDtlAdpter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        NutriAnlzrFoodListDtlList nutriList = viewList.get(i);

        viewHolder.tvNutrientnam.setText(String.valueOf(nutriList.getNutrientName()));
        viewHolder.tvQty.setText(String.valueOf(nutriList.getAchievedNutrientValue())+""+"/"+""+ String.valueOf(nutriList.getRda())+" "+ String.valueOf(nutriList.getUnitName())+""+"("+""+ String.valueOf(nutriList.getAchievedRDAPercentage()+""+"%)"));
        viewHolder.tvSNo.setText((i + 1)+" "+ ".");

    }

    @Override
    public int getItemCount() {
        return viewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNutrientnam,tvQty,tvSNo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNutrientnam = itemView.findViewById(R.id.foodNam);
            tvQty = itemView.findViewById(R.id.qtyvalue);
            tvSNo = itemView.findViewById(R.id.sNo);
        }
    }
}
