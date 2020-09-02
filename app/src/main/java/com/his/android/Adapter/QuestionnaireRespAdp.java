package com.his.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Model.ReportList;
import com.his.android.R;

import java.util.List;

public class QuestionnaireRespAdp extends RecyclerView.Adapter<QuestionnaireRespAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<ReportList> reportLists;
    public QuestionnaireRespAdp(Context mCtx, List<ReportList> reportLists) {
        this.mCtx = mCtx;
        this.reportLists = reportLists;
        Log.v("yigyig", "QuestionnaireRespAdp");
    }

    @NonNull
    @Override
    public QuestionnaireRespAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.v("yigyig", "onCreateViewHolder");
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_view_questionnaire, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionnaireRespAdp.RecyclerViewHolder holder, final int i) {
        ReportList reportList=reportLists.get(i);
        if (i != 0) {
            if(reportList.getQuestionnaireDateTime().equalsIgnoreCase(reportLists.get(i-1).getQuestionnaireDateTime())){
                holder.txtDate.setVisibility(View.GONE);
            } else {
                holder.txtDate.setText(reportList.getQuestionnaireDateTime());
                holder.txtDate.setVisibility(View.VISIBLE);
            }
        } else {
            holder.txtDate.setText(reportList.getQuestionnaireDateTime());
            holder.txtDate.setVisibility(View.VISIBLE);
        }
        holder.txtQues.setText(reportList.getQuestionText());
        holder.txtOption.setText(reportList.getOptionText());
    }

    @Override
    public int getItemCount() {
        Log.v("yigyig", String.valueOf(reportLists.size()));
        return reportLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtOption, txtQues, btnDelete, txtDate;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOption=itemView.findViewById(R.id.txtOption);
            txtQues =itemView.findViewById(R.id.txtQues);
            btnDelete =itemView.findViewById(R.id.btnDelete);
            txtDate =itemView.findViewById(R.id.txtDate);
        }
    }
}
