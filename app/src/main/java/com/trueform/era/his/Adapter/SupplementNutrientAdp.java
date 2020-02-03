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
import com.trueform.era.his.Model.SupplementFoodList;
import com.trueform.era.his.R;

import java.util.List;

public class SupplementNutrientAdp extends RecyclerView.Adapter<SupplementNutrientAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<SupplementFoodList> supplementLists;
    private InputVital inputVital=new InputVital();
    public SupplementNutrientAdp(Context mCtx, List<SupplementFoodList> supplementLists) {
        this.mCtx = mCtx;
        this.supplementLists = supplementLists;
    }

    @NonNull
    @Override
    public SupplementNutrientAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_supplement_nutrient, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final SupplementNutrientAdp.RecyclerViewHolder holder, final int i) {
        holder.txtSupplement.setText(String.valueOf(supplementLists.get(i).getSupplementName()));
        holder.txtMineral.setText(supplementLists.get(i).getNutrientName());
        holder.txtAch.setText(supplementLists.get(i).getNutrientValue());
        holder.txtRda.setText(supplementLists.get(i).getRda());
        holder.txtRcmnded.setText(supplementLists.get(i).getSupplementDose());
    }

    @Override
    public int getItemCount() {
        return supplementLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtSupplement, txtMineral, txtAch, txtRda, txtRcmnded;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSupplement =itemView.findViewById(R.id.txtSupplement);
            txtMineral =itemView.findViewById(R.id.txtMineral);
            txtAch =itemView.findViewById(R.id.txtAch);
            txtRda =itemView.findViewById(R.id.txtRda);
            txtRcmnded =itemView.findViewById(R.id.txtRcmnded);
        }
    }
}
