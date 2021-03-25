package com.his.android.Activity.UploadMultipleImg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import com.his.android.Activity.UploadMultipleImg.GetPatientAllDocumentList;
import com.his.android.R;

import java.util.List;

public class ViewDocAdapter extends RecyclerView.Adapter<ViewDocAdapter.MyViewHolder> {

    Context mContext;
    private List<GetPatientAllDocumentList> getPatientAllDocumentLists;

    public ViewDocAdapter(Context mContext, List<GetPatientAllDocumentList> getPatientAllDocumentLists) {
        this.mContext = mContext;
        this.getPatientAllDocumentLists = getPatientAllDocumentLists;
    }

    @NonNull
    @Override
    public ViewDocAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.innerview_patient_doc, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDocAdapter.MyViewHolder holder, int i) {

        final GetPatientAllDocumentList model = getPatientAllDocumentLists.get(i);

        holder.tvDateTime.setText(model.getFileDateTime());


        //For Image
        if (model.getFilePath().trim().isEmpty()) {
            holder.imageView.setImageResource(R.drawable.ic_bloodpressure);
        } else {

            Picasso.with(mContext).load(model.getFilePath()).into(holder.imageView);

        }

    }

    @Override
    public int getItemCount() {
        return getPatientAllDocumentLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDateTime;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            imageView = itemView.findViewById(R.id.imageView5);
        }
    }
}
