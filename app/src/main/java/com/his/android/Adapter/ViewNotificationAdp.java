package com.his.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.R;
import com.his.android.Response.ViewNotificationResp;

import java.util.List;

public class ViewNotificationAdp extends RecyclerView.Adapter<ViewNotificationAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<ViewNotificationResp> notificationList;
    public ViewNotificationAdp(Context mCtx, List<ViewNotificationResp> notificationList) {
        this.mCtx = mCtx;
        this.notificationList=notificationList;
    }

    @NonNull
    @Override
    public ViewNotificationAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_progress_note, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewNotificationAdp.RecyclerViewHolder holder, int i) {
        holder.txtNBody.setText(Html.fromHtml(notificationList.get(i).getNotificationBody()));
        holder.txtNDate.setText(mCtx.getResources().getString(R.string.date) + " " + notificationList.get(i).getNotificationDate());
        holder.txtNTitle.setText(notificationList.get(i).getNotificationTitle());
        Drawable res;
        if(notificationList.get(i).getRead()){
            holder.txtNBody.setTypeface(null, Typeface.NORMAL);
            res = mCtx.getResources().getDrawable(R.drawable.ic_read);
            holder.txtNTitle.setCompoundDrawablesWithIntrinsicBounds(res, null, null, null);
        }
        else{
            res = mCtx.getResources().getDrawable(R.drawable.ic_unread);
            holder.txtNTitle.setCompoundDrawablesWithIntrinsicBounds(res, null, null, null);
            holder.txtNBody.setTypeface(null, Typeface.BOLD);
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtNBody,txtNDate, txtNTitle;
        public LinearLayout lLayout;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNBody=itemView.findViewById(R.id.txtNBody);
            txtNDate=itemView.findViewById(R.id.txtNDate);
            txtNTitle=itemView.findViewById(R.id.txtNTitle);
            lLayout=itemView.findViewById(R.id.lLayout);
        }
    }
}
