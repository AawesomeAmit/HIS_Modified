package com.trueform.era.his.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trueform.era.his.Model.VitalChart;
import com.trueform.era.his.R;

import java.util.List;

public class ShowVitalAdp extends RecyclerView.Adapter<ShowVitalAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<VitalChart> vitalLists;
    public ShowVitalAdp(Context mCtx, List<VitalChart> vitalLists) {
        this.mCtx = mCtx;
        this.vitalLists = vitalLists;
    }

    @NonNull
    @Override
    public ShowVitalAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_vital_display, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowVitalAdp.RecyclerViewHolder holder, final int i) {
        VitalChart vitalList= vitalLists.get(i);
        if (i != 0) {
            if(vitalList.getCreatedDate().equalsIgnoreCase(vitalLists.get(i-1).getCreatedDate())){
                holder.txtDate.setVisibility(View.GONE);
            } else {
                holder.txtDate.setText(vitalList.getCreatedDate());
                holder.txtDate.setVisibility(View.VISIBLE);
            }
        } else {
            holder.txtDate.setText(vitalList.getCreatedDate());
            holder.txtDate.setVisibility(View.VISIBLE);
        }
        if(vitalList.getSys()!=null)
        if(!vitalList.getSys().equalsIgnoreCase("")) {
            holder.txtBpQty.setText("BPS-" + vitalList.getSys() + " -- BPD-" + vitalList.getDys());
            holder.llBp.setVisibility(View.VISIBLE);
        }
        if(vitalList.getPulse()!=null)
        if(!vitalList.getPulse().equalsIgnoreCase("")) {
            holder.txtPulseQty.setText(vitalList.getPulse());
            holder.llPulse.setVisibility(View.VISIBLE);
        }
        if(vitalList.getRespRate()!=null)
        if(!vitalList.getRespRate().equalsIgnoreCase("")) {
            holder.txtRRQty.setText(vitalList.getRespRate());
            holder.llRr.setVisibility(View.VISIBLE);
        }
        if(vitalList.getTemperature()!=null)
        if(!vitalList.getTemperature().equalsIgnoreCase("")) {
            holder.txtTempQty.setText(vitalList.getTemperature());
            holder.llTemp.setVisibility(View.VISIBLE);
        }
        if(vitalList.getHeight()!=null)
        if(!vitalList.getHeight().equalsIgnoreCase("")) {
            holder.txtHeightQty.setText(vitalList.getHeight());
            holder.llHeight.setVisibility(View.VISIBLE);
        }
        if(vitalList.getWeight()!=null)
        if(!vitalList.getWeight().equalsIgnoreCase("")) {
            holder.txtWeightQty.setText(vitalList.getWeight());
            holder.llWeight.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return vitalLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtBp, txtBpQty, txtPulse, txtPulseQty, txtRR, txtRRQty, txtTemp, txtTempQty, txtHeight, txtHeightQty,
                txtWeight, txtWeightQty, txtDate;
        LinearLayout llBp, llPulse, llRr, llTemp, llHeight, llWeight;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBp =itemView.findViewById(R.id.txtBp);
            llBp =itemView.findViewById(R.id.llBp);
            txtBpQty =itemView.findViewById(R.id.txtBpQty);
            llPulse =itemView.findViewById(R.id.llPulse);
            txtPulse =itemView.findViewById(R.id.txtPulse);
            txtPulseQty =itemView.findViewById(R.id.txtPulseQty);
            txtRR =itemView.findViewById(R.id.txtRR);
            txtRRQty =itemView.findViewById(R.id.txtRRQty);
            llRr =itemView.findViewById(R.id.llRr);
            llTemp =itemView.findViewById(R.id.llTemp);
            txtTemp =itemView.findViewById(R.id.txtTemp);
            txtTempQty =itemView.findViewById(R.id.txtTempQty);
            llHeight =itemView.findViewById(R.id.llHeight);
            txtHeight =itemView.findViewById(R.id.txtHeight);
            txtHeightQty =itemView.findViewById(R.id.txtHeightQty);
            llWeight =itemView.findViewById(R.id.llWeight);
            txtWeight =itemView.findViewById(R.id.txtWeight);
            txtWeightQty =itemView.findViewById(R.id.txtWeightQty);
            txtDate =itemView.findViewById(R.id.txtDate);
        }
    }
}
