package com.trueform.era.his.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trueform.era.his.Model.SubDept;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.SubHeadIDResp;

public class SubHeadAdp extends RecyclerView.Adapter<SubHeadAdp.RecyclerViewHolder> {
    private Context mCtx;
    private SubHeadIDResp subHeadList;

    public SubHeadAdp(Context mCtx, SubHeadIDResp subHeadList) {
        this.mCtx = mCtx;
        this.subHeadList=subHeadList;
    }

    @NonNull
    @Override
    public SubHeadAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.card_layout_subhead, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubHeadAdp.RecyclerViewHolder recyclerViewHolder, int i) {
        SubDept subDept=subHeadList.getSubDept().get(i);
        recyclerViewHolder.txtSubHead.setText(subDept.getSubDepartmentName());
    }

    @Override
    public int getItemCount() {
        return subHeadList.getSubDept().size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtSubHead;
        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSubHead=itemView.findViewById(R.id.txtSubHead);
        }
    }
}
