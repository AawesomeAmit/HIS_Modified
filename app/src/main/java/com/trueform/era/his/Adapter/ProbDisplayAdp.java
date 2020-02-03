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

import com.trueform.era.his.Fragment.ProblemInput;
import com.trueform.era.his.Model.ComplainList;
import com.trueform.era.his.R;

import java.util.List;

public class ProbDisplayAdp extends RecyclerView.Adapter<ProbDisplayAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<ComplainList> complainLists;
    private ProblemInput input=new ProblemInput();
    public ProbDisplayAdp(Context mCtx, List<ComplainList> complainLists) {
        this.mCtx = mCtx;
        this.complainLists = complainLists;
    }

    @NonNull
    @Override
    public ProbDisplayAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_prob_display, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProbDisplayAdp.RecyclerViewHolder holder, final int i) {
        final ComplainList complainList= complainLists.get(i);
        holder.txtProb.setText(complainList.getProblemName());
        holder.txtAttrib.setText(complainList.getAttributeName());
        holder.txtAttVal.setText(complainList.getAttributeValue());
        holder.txtDateTime.setText(complainList.getTimeFrom() + " - " + complainList.getTimeTo());
        holder.txtRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    input.delete(complainList.getPatientComplainID(), complainList.getPatientComplainAttributeID());
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return complainLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtProb, txtDateTime, txtAttrib, txtAttVal;
        ImageView txtRemove;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProb =itemView.findViewById(R.id.txtProb);
            txtDateTime =itemView.findViewById(R.id.txtDateTime);
            txtRemove =itemView.findViewById(R.id.txtRemove);
            txtAttrib =itemView.findViewById(R.id.txtAttrib);
            txtAttVal =itemView.findViewById(R.id.txtAttVal);
        }
    }
}
