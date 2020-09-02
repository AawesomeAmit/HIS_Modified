package com.his.android.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Model.GraphColorcodeList;
import com.his.android.R;

import java.util.List;

public class GraphColorcodeAdp extends RecyclerView.Adapter<GraphColorcodeAdp.ViewHolder> {

    private Context context;
    private List<GraphColorcodeList> colorcodeLists;

    public GraphColorcodeAdp(Context context, List<GraphColorcodeList> colorcodeLists) {
        this.context = context;
        this.colorcodeLists = colorcodeLists;
    }

    @NonNull
    @Override
    public GraphColorcodeAdp.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.innerview_graphcolor_dialouge, viewGroup, false);
        return new GraphColorcodeAdp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GraphColorcodeAdp.ViewHolder viewHolder, int i) {

        GraphColorcodeList colorcodeList = colorcodeLists.get(i);

        viewHolder.tvColor.setBackgroundColor(Color.parseColor(String.valueOf(colorcodeList.getColorCode())));
        viewHolder.tvName.setTextColor(Color.parseColor(String.valueOf(colorcodeList.getColorCode())));
        viewHolder.tvName.setText(String.valueOf(colorcodeList.getColor())+" "+"Color");
       // viewHolder.tvQty.setText(nutridtlList.getConsumed() +" "+ "/" +" "+nutridtlList.getTarget());
        viewHolder.tvPercentage.setText(colorcodeList.getAchievedPercentage());

    }

    @Override
    public int getItemCount() {
        return colorcodeLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvColor,tvName,tvPercentage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvColor = itemView.findViewById(R.id.tvColor);
            tvName = itemView.findViewById(R.id.tvcolorname);
            tvPercentage = itemView.findViewById(R.id.tvPercent);

        }
    }
}
