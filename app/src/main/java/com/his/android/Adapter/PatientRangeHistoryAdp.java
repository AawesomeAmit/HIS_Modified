package com.his.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.his.android.Fragment.IORange;
import com.his.android.R;
import com.his.android.Response.RangeHistory;

import java.util.List;

public class PatientRangeHistoryAdp extends RecyclerView.Adapter<PatientRangeHistoryAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<RangeHistory> rangeHistoryList;
    IORange ioRange=new IORange();
    public PatientRangeHistoryAdp(Context mCtx, List<RangeHistory> rangeHistoryList) {
        this.mCtx = mCtx;
        this.rangeHistoryList = rangeHistoryList;
    }

    @NonNull
    @Override
    public PatientRangeHistoryAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_patient_range, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientRangeHistoryAdp.RecyclerViewHolder holder, int i) {
        final RangeHistory rangeHistory=rangeHistoryList.get(i);
        if (i != 0) {
            if(rangeHistory.getCreatedDate().equalsIgnoreCase(rangeHistoryList.get(i-1).getCreatedDate())){
                holder.txtDate.setVisibility(View.GONE);
            } else {
                holder.txtDate.setText(rangeHistory.getCreatedDate());
                holder.txtDate.setVisibility(View.VISIBLE);
            }
        } else {
            holder.txtDate.setText(rangeHistory.getCreatedDate());
            holder.txtDate.setVisibility(View.VISIBLE);
        }
        holder.txtMin.setText(rangeHistory.getMinRange().toString()+" -- "+rangeHistory.getMaxRange().toString()+" "+rangeHistory.getUnitName());
        holder.txtType.setText(rangeHistory.getTypeName());
        holder.txtDiagnosis.setText(rangeHistory.getDiagnosis());
        holder.txtRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ioRange.delete(rangeHistory.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return rangeHistoryList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtType, txtMin, txtDate, txtDiagnosis;
        ImageView txtRemove;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtType=itemView.findViewById(R.id.txtType);
            txtRemove =itemView.findViewById(R.id.txtRemove);
            txtMin =itemView.findViewById(R.id.txtMin);
            txtDate =itemView.findViewById(R.id.txtDate);
            txtDiagnosis =itemView.findViewById(R.id.txtDiagnosis);
        }
    }
}
