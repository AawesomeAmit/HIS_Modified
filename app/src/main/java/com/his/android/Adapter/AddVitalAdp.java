package com.his.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Fragment.InputVital;
import com.his.android.R;
import com.his.android.Response.VitalList;

import java.util.List;

public class AddVitalAdp extends RecyclerView.Adapter<AddVitalAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<VitalList> vitalLists;
    private InputVital inputVital=new InputVital();
    public AddVitalAdp(Context mCtx, List<VitalList> vitalLists) {
        this.mCtx = mCtx;
        this.vitalLists = vitalLists;
    }

    @NonNull
    @Override
    public AddVitalAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_vital_input, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final AddVitalAdp.RecyclerViewHolder holder, final int i) {
        VitalList vitalList= vitalLists.get(i);
        holder.txtVital.setText(vitalList.getVitalName());
        holder.txtQty.setText(vitalList.getValue() + " " + vitalList.getUnit());
        holder.txtRemove.setOnClickListener(view -> inputVital.removeRow(i));
    }

    @Override
    public int getItemCount() {
        return vitalLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtVital, txtQty;
        ImageView txtRemove;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtVital =itemView.findViewById(R.id.txtVital);
            txtQty =itemView.findViewById(R.id.txtStr);
            txtRemove =itemView.findViewById(R.id.txtRemove);
        }
    }
}
