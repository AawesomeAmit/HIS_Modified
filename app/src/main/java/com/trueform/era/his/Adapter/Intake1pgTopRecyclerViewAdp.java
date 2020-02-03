package com.trueform.era.his.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Model.ViewIntkAllPriortyNutriList;
import com.trueform.era.his.R;

import java.util.List;

public class Intake1pgTopRecyclerViewAdp extends RecyclerView.Adapter<Intake1pgTopRecyclerViewAdp.ViewHolder> {
    private Context context;
    private List<ViewIntkAllPriortyNutriList> priortyNutriLists;

    public Intake1pgTopRecyclerViewAdp(Context context, List<ViewIntkAllPriortyNutriList> priortyNutriLists) {
        this.context = context;
        this.priortyNutriLists = priortyNutriLists;
    }

    @NonNull
    @Override
    public Intake1pgTopRecyclerViewAdp.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.innerview_proritynutrient_intk_1pgprogressbar_vertical, viewGroup, false);
        return new Intake1pgTopRecyclerViewAdp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Intake1pgTopRecyclerViewAdp.ViewHolder viewHolder, int i) {

        ViewIntkAllPriortyNutriList nutridtlList = priortyNutriLists.get(i);

        viewHolder.tvMnrlNam.setText(nutridtlList.getNutrientName());
        viewHolder.tvMnrlNam.setTextColor(Color.parseColor(nutridtlList.getColorCode()));
        viewHolder.tvQty.setText(nutridtlList.getConsumedNormal());
        viewHolder.tvExceed.setText(nutridtlList.getConsumedExceed()+" "+ "exceed");
        viewHolder.tvPercent.setText(nutridtlList.getAchievedRDAPercentage() +""+ " % ");

        viewHolder.progressBar.getProgressDrawable().setColorFilter(Color.parseColor(nutridtlList.getColorCode()), PorterDuff.Mode.SRC_OUT);
        viewHolder.progressBar.setProgress(Integer.parseInt(String.valueOf(nutridtlList.getAchievedRDAPercentage())));

    }

    @Override
    public int getItemCount() {
        return priortyNutriLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvExceed,tvMnrlNam,tvQty,tvPercent;
        ProgressBar progressBar;
        View viewLine;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQty = itemView.findViewById(R.id.consume);
            tvPercent = itemView.findViewById(R.id.target);
            tvMnrlNam = itemView.findViewById(R.id.tvMnrlName);
            tvExceed = itemView.findViewById(R.id.exceed);
            progressBar = itemView.findViewById(R.id.textView7);
            viewLine = itemView.findViewById(R.id.view);
        }
    }
}
