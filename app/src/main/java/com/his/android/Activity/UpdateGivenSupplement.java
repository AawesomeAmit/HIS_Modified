package com.his.android.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.reflect.TypeToken;
import com.his.android.Adapter.HeadAdp;
import com.his.android.Model.IntakeDashboard;
import com.his.android.R;
import com.his.android.Response.FoodDetailResp;
import com.his.android.Response.UpdateSupplementResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateGivenSupplement extends BaseActivity {
    RecyclerView rvPendingSupp, rvUpcomingSupp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_given_supplement);
        rvPendingSupp = findViewById(R.id.rvPendingSupp);
        rvUpcomingSupp = findViewById(R.id.rvUpcomingSupp);
        rvUpcomingSupp.setLayoutManager(new LinearLayoutManager(mActivity));
        rvPendingSupp.setLayoutManager(new LinearLayoutManager(mActivity));

    }

    private void bind() {
        Utils.showRequestDialog(context);
        Call<UpdateSupplementResp> call = RetrofitClient.getInstance().getApi().getIntakeFoodData(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid());
        call.enqueue(new Callback<UpdateSupplementResp>() {
            @Override
            public void onResponse(Call<UpdateSupplementResp> call, Response<UpdateSupplementResp> response) {
                if (response.isSuccessful()) {
                    List<UpdateSupplementList> previousSupplementList = gson.fromJson(foodDetails.get(i).getIntake(), new TypeToken<List<UpdateSupplementList>>() {}.getType());
                    List<UpdateSupplementList> pendingSupplementList = gson.fromJson(foodDetails.get(i).getIntake(), new TypeToken<List<UpdateSupplementList>>() {}.getType());
                    List<UpdateSupplementList> upcomingSupplementList = gson.fromJson(foodDetails.get(i).getIntake(), new TypeToken<List<UpdateSupplementList>>() {}.getType());
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<UpdateSupplementResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
}