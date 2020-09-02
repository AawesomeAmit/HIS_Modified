package com.his.android.Activity.UploadMultipleImg;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Activity.UploadMultipleImg.adapters.ViewDocAdapter;
import com.his.android.R;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.view.BaseActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPatientDoc extends BaseActivity {

    private ViewDocAdapter recyclerviewAdapter;
    List<GetPatientAllDocumentList> documentLists;
    private RecyclerView recyclerView;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_doc);
        recyclerView = findViewById(R.id.recycler_view_image);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        context = mActivity;

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

        Call<GetPatientDocRes> call = iRestInterfaces.getPatientDoc(
                SharedPrefManager.getInstance(context).getUser().getAccessToken(),
                String.valueOf(SharedPrefManager.getInstance(context).getUser().getUserid()),
                String.valueOf(SharedPrefManager.getInstance(context).getPid())
        );


        call.enqueue(new Callback<GetPatientDocRes>() {
            @Override
            public void onResponse(Call<GetPatientDocRes> call, Response<GetPatientDocRes> response) {

                GetPatientDocRes productListRes = (GetPatientDocRes) response.body();

                    if(response.isSuccessful()){
                        documentLists = ((GetPatientDocRes) response.body()).getGetPatientAllDocumentList();
                        recyclerviewAdapter = new ViewDocAdapter(ViewPatientDoc.this,documentLists);
                        recyclerView.setAdapter(recyclerviewAdapter);
                    }
                    else {
                        // error case
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
            public void onFailure(Call<GetPatientDocRes> call, Throwable t) {
                //progressDialog.cancel();
                Utils.hideDialog();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}