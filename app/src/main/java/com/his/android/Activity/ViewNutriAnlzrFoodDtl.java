package com.his.android.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Adapter.FoodDtlAdpter;
import com.his.android.Model.NutriAnlzrFoodListDtlList;
import com.his.android.R;
import com.his.android.Response.NutriAnlzrFoodListDtlRes;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.his.android.Fragment.NutriAnalyserFragment.NUTRI_TOKEN;

public class ViewNutriAnlzrFoodDtl extends AppCompatActivity {

    static SharedPrefManager sharedPrefManager;
    int dietId,dietType,foodQty;
    String foodName,unitName;

    RecyclerView recyclerView;

    TextView tvFoodnam,tvQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_nutri_anlzr_food_dtl);
        recyclerView = findViewById(R.id.list);
        tvFoodnam = findViewById(R.id.foodNam);
        tvQty = findViewById(R.id.tvQuantity);


        sharedPrefManager = SharedPrefManager.getInstance(this);

        dietId = getIntent().getIntExtra("dietId",0);
        dietType = getIntent().getIntExtra("dietType",0);
        foodQty = getIntent().getIntExtra("foodQuantity",0);
        foodName = getIntent().getStringExtra("foodName");
        unitName = getIntent().getStringExtra("unitName");

        tvFoodnam.setText(foodName);
        tvQty.setText(foodQty+" "+unitName);


        hitViewDetails();



    }

    private void hitViewDetails() {

        Call<NutriAnlzrFoodListDtlRes> call = RetrofitClient1.getInstance().getApi().viewFoodDtl(
                NUTRI_TOKEN,
                SharedPrefManager.getInstance(ViewNutriAnlzrFoodDtl.this).getMemberId().getMemberId().toString(),
                String.valueOf(dietId),
                String.valueOf(dietType),
                SharedPrefManager.getInstance(ViewNutriAnlzrFoodDtl.this).getMemberId().getUserLoginId().toString()
        );

        call.enqueue(new Callback<NutriAnlzrFoodListDtlRes>() {


            @Override
            public void onResponse(Call<NutriAnlzrFoodListDtlRes> call, Response<NutriAnlzrFoodListDtlRes> response) {


                // Log.v("emergencycon========",new Gson(response.body()))
                NutriAnlzrFoodListDtlRes prescriptionRes = (NutriAnlzrFoodListDtlRes) response.body();

                if (prescriptionRes != null && prescriptionRes.getResponseCode() == 1){
                    // progressDialog.dismiss();
                    List<NutriAnlzrFoodListDtlList> tableResponses = ((NutriAnlzrFoodListDtlRes) response.body()).getResponseValue();
                    FoodDtlAdpter prescriptionHisAdapter = new FoodDtlAdpter(ViewNutriAnlzrFoodDtl.this,tableResponses);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ViewNutriAnlzrFoodDtl.this));
                    recyclerView.setAdapter(prescriptionHisAdapter);
                }

            }

            @Override
            public void onFailure(Call<NutriAnlzrFoodListDtlRes> call, Throwable t) {
                //progressDialog.dismiss();

                // Toast.makeText(TravelHistory.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
