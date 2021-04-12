package com.his.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Model.HeadAssign;
import com.his.android.R;

import java.util.List;

public class HeadAdp extends RecyclerView.Adapter<HeadAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<HeadAssign> headList;

    public HeadAdp(Context mCtx, List<HeadAssign> headList) {
        this.mCtx = mCtx;
        this.headList = headList;
    }

    @NonNull
    @Override
    public HeadAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.card_layout_head, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadAdp.RecyclerViewHolder recyclerViewHolder, int i) {
        try {
            String uri = "@drawable/" + headList.get(i).getHeadName().toLowerCase();
            int imageResource = mCtx.getResources().getIdentifier(uri, null, mCtx.getPackageName());
            recyclerViewHolder.txtHead.setText(headList.get(i).getHeadDiscription());
            Drawable res = mCtx.getResources().getDrawable(imageResource);
            recyclerViewHolder.imgHead.setImageDrawable(res);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (headList == null) {
            return 0;
        } else return headList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHead;
        TextView txtHead;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHead = itemView.findViewById(R.id.imgHead);
            txtHead = itemView.findViewById(R.id.txtHead);
        }
    }
}
