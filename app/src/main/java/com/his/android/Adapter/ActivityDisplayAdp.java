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

import com.his.android.Fragment.ActivityInput;
import com.his.android.Model.ActivityList;
import com.his.android.R;

import java.util.List;

public class ActivityDisplayAdp extends RecyclerView.Adapter<ActivityDisplayAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<ActivityList> activityLists;
    private ActivityInput input=new ActivityInput();
    public ActivityDisplayAdp(Context mCtx, List<ActivityList> activityLists) {
        this.mCtx = mCtx;
        this.activityLists = activityLists;
    }

    @NonNull
    @Override
    public ActivityDisplayAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_activity_display, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ActivityDisplayAdp.RecyclerViewHolder holder, final int i) {
        final ActivityList activityList= activityLists.get(i);
        holder.txtActivity.setText(activityList.getActivityName());
        holder.txtDateTime.setText(activityList.getTimeFrom() + " - " + activityList.getTimeTo());
        if(activityList.getActivityName().equalsIgnoreCase("Mood")) holder.txtRating.setVisibility(View.VISIBLE);
        if(activityList.getRating()==1)
            holder.txtRating.setText(R.string.smile_rating_terrible);
        else if(activityList.getRating()==2)
            holder.txtRating.setText(R.string.smile_rating_bad);
        else if(activityList.getRating()==3)
            holder.txtRating.setText(R.string.smile_rating_okay);
        else if(activityList.getRating()==4)
            holder.txtRating.setText(R.string.smile_rating_good);
        else if(activityList.getRating()==5)
            holder.txtRating.setText(R.string.smile_rating_great);
        holder.txtRemove.setOnClickListener(view -> input.delete(activityList.getPhysicalActivityID()));
    }

    @Override
    public int getItemCount() {
        return activityLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtActivity, txtDateTime, txtRating;
        ImageView txtRemove;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtActivity =itemView.findViewById(R.id.txtActivity);
            txtDateTime =itemView.findViewById(R.id.txtDateTime);
            txtRemove =itemView.findViewById(R.id.txtRemove);
            txtRating =itemView.findViewById(R.id.txtRating);
        }
    }
}
