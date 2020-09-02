package com.his.android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Model.GetNutrientLevelImmediateEffectByFoodTimeId;
import com.his.android.R;
import com.his.android.Response.GetNutrientLevelImmediateEffectByFoodTimeIdResp;

public class HealthPredictionAdapter extends RecyclerView.Adapter<HealthPredictionAdapter.MyViewHolder> {
    private GetNutrientLevelImmediateEffectByFoodTimeIdResp getNutrientLevelImmediateEffectByFoodTimeIdResp;
    private Context ctx;

    public HealthPredictionAdapter(GetNutrientLevelImmediateEffectByFoodTimeIdResp getNutrientLevelImmediateEffectByFoodTimeIdResp, Context ctx) {
        this.getNutrientLevelImmediateEffectByFoodTimeIdResp = getNutrientLevelImmediateEffectByFoodTimeIdResp;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.innerview_healthprediction, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        GetNutrientLevelImmediateEffectByFoodTimeId getNutrientLevelImmediateEffectByFoodTimeId = getNutrientLevelImmediateEffectByFoodTimeIdResp.getResponseValue().get(i);

        myViewHolder.mineral.setText(getNutrientLevelImmediateEffectByFoodTimeId.getNutrientName());
        myViewHolder.tvSupplementNam.setText(getNutrientLevelImmediateEffectByFoodTimeId.getNutrientLevel());
        myViewHolder.symptom.setText(getNutrientLevelImmediateEffectByFoodTimeId.getSymptom());
    }

    @Override
    public int getItemCount() {
        return getNutrientLevelImmediateEffectByFoodTimeIdResp.getResponseValue().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mineral, tvSupplementNam, symptom;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mineral = itemView.findViewById(R.id.mineral);
            tvSupplementNam = itemView.findViewById(R.id.tvSupplementNam);
            symptom = itemView.findViewById(R.id.symptom);
        }
    }
}
