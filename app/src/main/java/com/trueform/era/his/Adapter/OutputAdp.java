package com.trueform.era.his.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trueform.era.his.Model.OutputHistory;
import com.trueform.era.his.R;

import java.util.List;

public class OutputAdp extends RecyclerView.Adapter<OutputAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<OutputHistory> outputHistories;
    public OutputAdp(Context mCtx, List<OutputHistory> outputHistories) {
        this.mCtx = mCtx;
        this.outputHistories = outputHistories;
    }

    @NonNull
    @Override
    public OutputAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_display_input_fluid, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutputAdp.RecyclerViewHolder holder, int i) {
        holder.txtFluid.setText(String.valueOf(outputHistories.get(i).getTypeName()));
        holder.txtQty.setText(String.valueOf(outputHistories.get(i).getQuantity()));
        holder.txtUnit.setText(String.valueOf(outputHistories.get(i).getUnitname()));
        holder.txtDateTime.setText(outputHistories.get(i).getOutputDate());
    }

    @Override
    public int getItemCount() {
        return outputHistories.size();
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
