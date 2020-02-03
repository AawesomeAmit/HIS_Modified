package com.trueform.era.his.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trueform.era.his.Fragment.VitalRange;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.VitalRangeHistory;

import java.util.List;

public class VitalRangeHistoryAdp extends RecyclerView.Adapter<VitalRangeHistoryAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<VitalRangeHistory> vitalRangeHistoryList;
    VitalRange vitalRange=new VitalRange();
    public VitalRangeHistoryAdp(Context mCtx, List<VitalRangeHistory> vitalRangeHistoryList) {
        this.mCtx = mCtx;
        this.vitalRangeHistoryList = vitalRangeHistoryList;
    }

    @NonNull
    @Override
    public VitalRangeHistoryAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_vital_range, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VitalRangeHistoryAdp.RecyclerViewHolder holder, int i) {
        final VitalRangeHistory vitalRangeHistory=vitalRangeHistoryList.get(i);
        if (i != 0) {
            if(vitalRangeHistory.getCreatedDate().equalsIgnoreCase(vitalRangeHistoryList.get(i-1).getCreatedDate())){
                holder.txtDate.setVisibility(View.GONE);
            } else {
                holder.txtDate.setText(vitalRangeHistory.getCreatedDate());
                holder.txtDate.setVisibility(View.VISIBLE);
            }
        } else {
            holder.txtDate.setText(vitalRangeHistory.getCreatedDate());
            holder.txtDate.setVisibility(View.VISIBLE);
        }
        holder.txtMin.setText(vitalRangeHistory.getMinRange().toString()+" -- "+vitalRangeHistory.getMaxRange().toString());
        holder.txtVital.setText(vitalRangeHistory.getVitalName());
        holder.txtRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vitalRange.delete(vitalRangeHistory.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return vitalRangeHistoryList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtVital, txtMin, txtDate;
        ImageView txtRemove;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtVital = itemView.findViewById(R.id.txtVital);
            txtRemove = itemView.findViewById(R.id.txtRemove);
            txtMin = itemView.findViewById(R.id.txtMin);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
