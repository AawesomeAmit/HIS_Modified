package com.his.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.his.android.Model.Investigation;
import com.his.android.R;
import com.his.android.Response.AddInvestigationResp;

public class AddInvestigationAdp extends RecyclerView.Adapter<AddInvestigationAdp.RecyclerViewHolder> {
    private Context mCtx;
    private AddInvestigationResp addInvestigationResp;
    public AddInvestigationAdp(Context mCtx, AddInvestigationResp addInvestigationResp) {
        this.mCtx = mCtx;
        this.addInvestigationResp=addInvestigationResp;
    }

    @NonNull
    @Override
    public AddInvestigationAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_investigation_list, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddInvestigationAdp.RecyclerViewHolder holder, int i) {
        Investigation investigation=addInvestigationResp.getInvestigation().get(i);
        holder.txtTestName.setText(investigation.getItemName());
        holder.txtComment.setText(investigation.getRemark());
    }

    @Override
    public int getItemCount() {
        return addInvestigationResp.getInvestigation().size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtTestName,txtComment;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTestName=itemView.findViewById(R.id.txtTestName);
            txtComment=itemView.findViewById(R.id.txtComment);
        }
    }
}
