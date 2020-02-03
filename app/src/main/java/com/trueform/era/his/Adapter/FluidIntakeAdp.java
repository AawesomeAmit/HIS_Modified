package com.trueform.era.his.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trueform.era.his.Model.IntakeHistory;
import com.trueform.era.his.R;

import java.util.List;

public class FluidIntakeAdp extends RecyclerView.Adapter<FluidIntakeAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<IntakeHistory> intakeHistories;
    public FluidIntakeAdp(Context mCtx, List<IntakeHistory> intakeHistories) {
        this.mCtx = mCtx;
        this.intakeHistories = intakeHistories;
    }

    @NonNull
    @Override
    public FluidIntakeAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_display_input_fluid, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FluidIntakeAdp.RecyclerViewHolder holder, int i) {
        holder.txtFluid.setText(String.valueOf(intakeHistories.get(i).getTypeName()));
        holder.txtQty.setText(String.valueOf(intakeHistories.get(i).getQuantity()));
        holder.txtUnit.setText(String.valueOf(intakeHistories.get(i).getUnitname()));
        holder.txtDateTime.setText(intakeHistories.get(i).getIntakeDate());
    }

    @Override
    public int getItemCount() {
        return intakeHistories.size();
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
