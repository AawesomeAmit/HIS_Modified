package com.his.android.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.his.android.Activity.UploadMultipleImg.Api;
import com.his.android.Activity.UploadMultipleImg.ApiUtils;
import com.his.android.Activity.UploadMultipleImg.Utils;
import com.his.android.Model.DietImageList;
import com.his.android.R;
import com.his.android.Response.DietImageResp;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.view.BaseActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDietImage extends BaseActivity {
    List<DietImageList> documentLists;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diet_image);
        recyclerView = findViewById(R.id.recycler_view_image);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            hitGetImg();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //GetImage
    private void hitGetImg() {
        Utils.showRequestDialog(mActivity);

        Api iRestInterfaces = ApiUtils.getAPIService();

        Call<DietImageResp> call = iRestInterfaces.getPatientDietImage(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                String.valueOf(SharedPrefManager.getInstance(mActivity).getUser().getUserid()),
                String.valueOf(SharedPrefManager.getInstance(mActivity).getPid())
        );


        call.enqueue(new Callback<DietImageResp>() {
            @Override
            public void onResponse(Call<DietImageResp> call, Response<DietImageResp> response) {
                if(response.isSuccessful()){
                    documentLists = response.body().getDietImageList();
                    ViewImgAdp recyclerviewAdapter = new ViewImgAdp(documentLists);
                    recyclerView.setAdapter(recyclerviewAdapter);
                }
                else {
                    switch (response.code()) {
                        case 400:
                            Toast.makeText(getApplicationContext(), "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }



                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<DietImageResp> call, Throwable t) {
                Utils.hideDialog();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ViewImgAdp extends RecyclerView.Adapter<ViewImgAdp.MyViewHolder> {
        private List<DietImageList> getPatientAllDocumentLists;
        public ViewImgAdp(List<DietImageList> getPatientAllDocumentLists) {
            this.getPatientAllDocumentLists = getPatientAllDocumentLists;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.innerview_patient_doc,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
            final DietImageList model = getPatientAllDocumentLists.get(i);
            holder.tvDateTime.setText(model.getImageDietTime());
            if(model.getPatientDietImage().trim().isEmpty()) {
                holder.imageView.setImageResource(R.drawable.ic_bloodpressure);
            }
            else {
                Picasso.with(mActivity).load(model.getPatientDietImage()).into(holder.imageView);
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
}