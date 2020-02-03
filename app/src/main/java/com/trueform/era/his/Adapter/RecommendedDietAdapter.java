package com.trueform.era.his.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Model.GetRequiredSupplementByFoodTimeId;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.GetRequiredSupplementByFoodTimeIdResp;


public class RecommendedDietAdapter extends RecyclerView.Adapter<RecommendedDietAdapter.MyViewHolder> {
    private GetRequiredSupplementByFoodTimeIdResp getRequiredSupplementByFoodTimeIdResp;
    private Context ctx;

    public RecommendedDietAdapter(GetRequiredSupplementByFoodTimeIdResp getRequiredSupplementByFoodTimeIdResp, Context ctx) {
        this.getRequiredSupplementByFoodTimeIdResp = getRequiredSupplementByFoodTimeIdResp;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.innerview_nutri_analyser_intake_diet, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        GetRequiredSupplementByFoodTimeId getRequiredSupplementByFoodTimeId = getRequiredSupplementByFoodTimeIdResp.getResponseValue().get(i);
        myViewHolder.nutrient.setText(getRequiredSupplementByFoodTimeId.getNutrientName());
        myViewHolder.tvSupplementNam.setText(getRequiredSupplementByFoodTimeId.getSupplementName());
        myViewHolder.ach.setText(getRequiredSupplementByFoodTimeId.getNutrientValue());
        myViewHolder.rda.setText(getRequiredSupplementByFoodTimeId.getRda());
        myViewHolder.recommended.setText(getRequiredSupplementByFoodTimeId.getSupplementDose());
    }

    @Override
    public int getItemCount() {
        return getRequiredSupplementByFoodTimeIdResp.getResponseValue().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nutrient, tvSupplementNam, ach, rda, recommended;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nutrient = itemView.findViewById(R.id.nutrient);
            tvSupplementNam = itemView.findViewById(R.id.tvSupplementNam);
            ach = itemView.findViewById(R.id.ach);
            rda = itemView.findViewById(R.id.rda);
            recommended = itemView.findViewById(R.id.recommended);
        }
    }
}
