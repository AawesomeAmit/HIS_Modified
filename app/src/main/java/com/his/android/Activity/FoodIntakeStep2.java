package com.his.android.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Model.FoodTiming;
import com.his.android.Model.IntakeFoodStep;
import com.his.android.Model.PatientActivityDetail;
import com.his.android.R;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodIntakeStep2 extends BaseActivity {
    RecyclerView rvFood;
    LinearLayoutManager linearLayoutManager;
    List<IntakeFoodStep>  intakeFoodList=new ArrayList<>();
    LinearLayoutManager linearLayoutManagerData = new LinearLayoutManager(mActivity);
    int mCurX = 0;
    int mFirst=0, mLast=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_intake_step2);
        rvFood = findViewById(R.id.rvFood);
        linearLayoutManager = new  LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false);
        linearLayoutManager = new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        rvFood.setLayoutManager(linearLayoutManager);
        bind();
    }

    public void bind() {
        if (ConnectivityChecker.checker(mActivity)) {
            Utils.showRequestDialog(mActivity);
            Call<List<IntakeFoodStep>> call = RetrofitClient.getInstance().getApi().getFoodListByTimeID(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), SharedPrefManager.getInstance(mActivity).getPid(), SharedPrefManager.getInstance(mActivity).getFoodTimeId(), SharedPrefManager.getInstance(mActivity).getFoodDate());
            call.enqueue(new Callback<List<IntakeFoodStep>>() {
                @Override
                public void onResponse(Call<List<IntakeFoodStep>> call, Response<List<IntakeFoodStep>> response) {
                    if(response.isSuccessful()){
                        intakeFoodList=response.body();
                        if(intakeFoodList.size()>0)
                        rvFood.setAdapter(new FoodAdp(intakeFoodList));
                        else {
                            Toast.makeText(mActivity, "No food has been prescribed for the selected slot!", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<List<IntakeFoodStep>> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        }
    }

    public class FoodAdp extends RecyclerView.Adapter<FoodAdp.RecyclerViewHolder> {
        private List<IntakeFoodStep> intakeFoodList;
        public FoodAdp(List<IntakeFoodStep> intakeFoodList) {
            this.intakeFoodList = intakeFoodList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_food_intake_step2, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtQue.setText("How much " +intakeFoodList.get(i).getFoodName()+" was eaten out of "+intakeFoodList.get(i).getFoodQuantity()+" "+intakeFoodList.get(i).getUnit()+"?");
            holder.txt0.setOnClickListener(view -> {
                intakeFoodList.get(i).setPercent("0");
                holder.txt0.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle_blue));
                holder.txt0.setTextColor(Color.WHITE);
                holder.txt25.setTextColor(Color.parseColor("#10207A"));
                holder.txt50.setTextColor(Color.parseColor("#10207A"));
                holder.txt75.setTextColor(Color.parseColor("#10207A"));
                holder.txt100.setTextColor(Color.parseColor("#10207A"));
                holder.txt25.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt50.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt75.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt100.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
            });
            holder.txt25.setOnClickListener(view -> {
                intakeFoodList.get(i).setPercent("25");
                holder.txt25.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle_blue));
                holder.txt25.setTextColor(Color.WHITE);
                holder.txt0.setTextColor(Color.parseColor("#10207A"));
                holder.txt50.setTextColor(Color.parseColor("#10207A"));
                holder.txt75.setTextColor(Color.parseColor("#10207A"));
                holder.txt100.setTextColor(Color.parseColor("#10207A"));
                holder.txt0.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt50.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt75.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt100.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
            });
            holder.txt50.setOnClickListener(view -> {
                intakeFoodList.get(i).setPercent("50");
                holder.txt50.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle_blue));
                holder.txt50.setTextColor(Color.WHITE);
                holder.txt25.setTextColor(Color.parseColor("#10207A"));
                holder.txt0.setTextColor(Color.parseColor("#10207A"));
                holder.txt75.setTextColor(Color.parseColor("#10207A"));
                holder.txt100.setTextColor(Color.parseColor("#10207A"));
                holder.txt25.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt0.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt75.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt100.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
            });
            holder.txt75.setOnClickListener(view -> {
                intakeFoodList.get(i).setPercent("75");
                holder.txt75.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle_blue));
                holder.txt75.setTextColor(Color.WHITE);
                holder.txt25.setTextColor(Color.parseColor("#10207A"));
                holder.txt50.setTextColor(Color.parseColor("#10207A"));
                holder.txt0.setTextColor(Color.parseColor("#10207A"));
                holder.txt100.setTextColor(Color.parseColor("#10207A"));
                holder.txt25.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt50.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt0.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt100.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
            });
            holder.txt100.setOnClickListener(view -> {
                intakeFoodList.get(i).setPercent("100");
                holder.txt100.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle_blue));
                holder.txt100.setTextColor(Color.WHITE);
                holder.txt25.setTextColor(Color.parseColor("#10207A"));
                holder.txt50.setTextColor(Color.parseColor("#10207A"));
                holder.txt75.setTextColor(Color.parseColor("#10207A"));
                holder.txt0.setTextColor(Color.parseColor("#10207A"));
                holder.txt25.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt50.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt75.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt0.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
            });

            holder.txtNext.setOnClickListener(v -> {
                if(intakeFoodList.size()-2==i) holder.txtNext.setText(R.string.finish);
                else holder.txtNext.setText(R.string.next);
                if(intakeFoodList.get(i).getPercent()==null) Toast.makeText(mActivity, "Please select the quantity percentage of the diet!", Toast.LENGTH_LONG).show();
                else {
                    giveDiet(intakeFoodList.get(i).getDietID(), SharedPrefManager.getInstance(mActivity).getFoodDate(), SharedPrefManager.getInstance(mActivity).getFoodTime(), 0, intakeFoodList.get(i).getPercent());
                    rvFood.getLayoutManager().scrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);
                }
            });

            holder.txtBack.setOnClickListener(v -> {
                if(intakeFoodList.size()-1==i) holder.txtNext.setText(R.string.finish);
                else holder.txtNext.setText(R.string.next);
                if(i==0) onBackPressed();
                else rvFood.getLayoutManager().scrollToPosition(linearLayoutManager.findLastVisibleItemPosition() - 1);
            });
        }

        @Override
        public int getItemCount() {
            return intakeFoodList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtQue, txtBack, txtNext, txt0, txt25, txt50, txt75, txt100;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtQue=itemView.findViewById(R.id.txtQue);
                txtBack=itemView.findViewById(R.id.txtBack);
                txtNext=itemView.findViewById(R.id.txtNext);
                txt0=itemView.findViewById(R.id.txt0);
                txt25=itemView.findViewById(R.id.txt25);
                txt50=itemView.findViewById(R.id.txt50);
                txt75=itemView.findViewById(R.id.txt75);
                txt100=itemView.findViewById(R.id.txt100);
            }
        }
    }

    private void giveDiet(int dietID, String dietDate, String dietTime, int isSupplement, String consumptionPercentage) {
        Utils.showRequestDialog1(mActivity);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().UpdateIntakeConsumption(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()), dietID, dietDate, dietTime, isSupplement, consumptionPercentage);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mActivity, "Submitted Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
}