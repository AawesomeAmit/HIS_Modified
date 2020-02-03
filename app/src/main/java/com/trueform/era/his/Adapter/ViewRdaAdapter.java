package com.trueform.era.his.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Model.NutriAnalyze;
import com.trueform.era.his.R;

import java.util.ArrayList;
import java.util.List;

public class ViewRdaAdapter extends RecyclerView.Adapter<ViewRdaAdapter.MyViewHolder>  implements Filterable {//
    private List<NutriAnalyze> listResp;
    private List<NutriAnalyze> listRespFiltered;
    private int NutrientID;
    private Context ctx;

    public ViewRdaAdapter(Context ctx, List<NutriAnalyze> list) {
        this.listResp = list;
        this.listRespFiltered = list;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.inner_achievement_rda, viewGroup, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

       final NutriAnalyze nutriAnalyze = listRespFiltered.get(i);

        NutrientID = nutriAnalyze.getNutrientID();

        myViewHolder.txtName.setText(nutriAnalyze.getNutrientName());

        if (nutriAnalyze.getTarget().equalsIgnoreCase("0.00")
                || nutriAnalyze.getTarget().equalsIgnoreCase("0.000")
                || nutriAnalyze.getTarget().equalsIgnoreCase("0.0000")){
            myViewHolder.achPer.setText("(?)");
        }else {
            myViewHolder.achPer.setText("("+""+nutriAnalyze.getTotalRDAPercentage()+"%" +""+ ")");
        }
        myViewHolder.extraAchPer.setText(nutriAnalyze.getExtraNutrientValue() +" "+ nutriAnalyze.getUnitName());

        myViewHolder.achRdaVal.setText(nutriAnalyze.getTotalNutrientValue()+""+ "/" +""+nutriAnalyze.getTarget()+" "+nutriAnalyze.getUnitName());
        myViewHolder.prUn.getProgressDrawable().setColorFilter(Color.parseColor(nutriAnalyze.getAchievedRDAColorCode()), PorterDuff.Mode.SRC_OUT);
        myViewHolder.prUn.setProgress(Integer.parseInt(String.valueOf(nutriAnalyze.getAchievedRDAPercentage())));
        if(nutriAnalyze.getExtraRDAPercentage()==0){
            myViewHolder.prAb.setVisibility(View.GONE);
            myViewHolder.extraAchPer.setVisibility(View.GONE);
        } else {
            myViewHolder.prAb.setVisibility(View.VISIBLE);
            myViewHolder.extraAchPer.setVisibility(View.VISIBLE);
            myViewHolder.prAb.getProgressDrawable().setColorFilter(Color.parseColor(nutriAnalyze.getExtraRDAColorCode()), PorterDuff.Mode.SRC_OUT);
            myViewHolder.prAb.setProgress(Integer.parseInt(String.valueOf(nutriAnalyze.getExtraRDAPercentage())));
        }
        myViewHolder.rdaVal.setText(nutriAnalyze.getExtraRDAPercentage() +"%");
        myViewHolder.sNo.setText((i + 1)+" "+ ".");

        /*myViewHolder.linearLayout.setOnClickListener(v -> {
            Intent a = new Intent(ctx, ViewNutriAnlzerIntkRatio.class);
            a.putExtra("nutrientID",NutrientID);
            a.putExtra("nutrientName",nutriAnalyze.getNutrientName());
            a.putExtra("totalNutrientValue",nutriAnalyze.getTotalNutrientValue());
            a.putExtra("unitName",nutriAnalyze.getUnitName());
            ctx.startActivity(a);
        });*/


    }

    @Override
    public int getItemCount() {
        return listRespFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listRespFiltered = listResp;
                } else {
                    List<NutriAnalyze> filteredList = new ArrayList<>();
                    for (NutriAnalyze row : listResp) {
                        if (row.getNutrientName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    listRespFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listRespFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listRespFiltered = (ArrayList<NutriAnalyze>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, achPer, sNo, extraAchPer, achRdaVal, rdaVal;
        ProgressBar prUn, prAb;
        LinearLayout linearLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            extraAchPer = itemView.findViewById(R.id.extraAchPer);
            achRdaVal = itemView.findViewById(R.id.achRdaVal);
            achPer = itemView.findViewById(R.id.achPer);
            sNo = itemView.findViewById(R.id.sNo);
            prUn=itemView.findViewById(R.id.prUn);
            prAb=itemView.findViewById(R.id.prAb);
            rdaVal = itemView.findViewById(R.id.rdaVal);

            linearLayout = itemView.findViewById(R.id.linearlayout);
        }
    }
}