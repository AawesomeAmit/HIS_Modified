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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodIntakeStep2 extends BaseActivity {
    RecyclerView rvFood;
    List<IntakeFoodStep>  intakeFoodList=new ArrayList<>();
    LinearLayoutManager linearLayoutManagerData = new LinearLayoutManager(mActivity);
    int mCurX = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_intake_step2);
        rvFood = findViewById(R.id.rvFood);
        /*LinearLayoutManager linearLayoutManager = new  LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvFood.setLayoutManager(linearLayoutManager);*/
        rvFood.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        rvFood.removeOnScrollListener(masterOnScrollListener);
        rvFood.scrollToPosition(0);
        rvFood.scrollBy(mCurX, 0);
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
                        rvFood.setAdapter(new FoodAdp(intakeFoodList));
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
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtQue.setText("How much " +intakeFoodList.get(i).getFoodName()+" was eaten out of "+intakeFoodList.get(i).getFoodQuantity()+" "+intakeFoodList.get(i).getUnit());
            holder.txt0.setOnClickListener(view -> {
                intakeFoodList.get(i).setPercent(0);
                holder.txt0.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle_blue));
                holder.txt25.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt50.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt75.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt100.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
            });
            holder.txt25.setOnClickListener(view -> {
                intakeFoodList.get(i).setPercent(25);
                holder.txt25.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle_blue));
                holder.txt0.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt50.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt75.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt100.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
            });
            holder.txt50.setOnClickListener(view -> {
                intakeFoodList.get(i).setPercent(50);
                holder.txt50.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle_blue));
                holder.txt25.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt0.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt75.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt100.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
            });
            holder.txt75.setOnClickListener(view -> {
                intakeFoodList.get(i).setPercent(75);
                holder.txt75.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle_blue));
                holder.txt25.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt50.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt0.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt100.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
            });
            holder.txt100.setOnClickListener(view -> {
                intakeFoodList.get(i).setPercent(100);
                holder.txt100.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle_blue));
                holder.txt25.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt50.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt75.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
                holder.txt0.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_circle));
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

    private RecyclerView.OnScrollListener  masterOnScrollListener = new RecyclerView.OnScrollListener() {
        RecyclerView masterRecyclerView = null;

        @Override
        public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:
                    if (masterRecyclerView != null) {
                        masterRecyclerView = null;
                        final int firstVisibleItemPosition = linearLayoutManagerData.findFirstVisibleItemPosition();
                        final int lastVisibleItemPosition = linearLayoutManagerData.findLastVisibleItemPosition();
                        /*for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; ++i) {
                            RecyclerView horizontalRecyclerView = rvFood.findViewHolderForAdapterPosition(i).itemView;
                            if (horizontalRecyclerView != recyclerView)
                                horizontalRecyclerView.addOnScrollListener(this);
                        }*/
                    }
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    //TODO fix out-of-sync scrolling issues, probably here
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    if (masterRecyclerView == null) {
                        masterRecyclerView = recyclerView;
                        final int firstVisibleItemPosition = linearLayoutManagerData.findFirstVisibleItemPosition();
                        final int lastVisibleItemPosition = linearLayoutManagerData.findLastVisibleItemPosition();
                        /*for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; ++i) {
                            RecyclerView horizontalRecyclerView = rvFood.findViewHolderForAdapterPosition(i).itemView;
                            if (horizontalRecyclerView != recyclerView)
                                horizontalRecyclerView.removeOnScrollListener(this);
                        }*/
                    }
                    break;
            }
        }

        @Override
        public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mCurX += dx;
            final int firstVisibleItemPosition = linearLayoutManagerData.findFirstVisibleItemPosition();
            final int lastVisibleItemPosition = linearLayoutManagerData.findLastVisibleItemPosition();
            /*for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; ++i) {
                RecyclerView horizontalRecyclerView = rvFood.findViewHolderForAdapterPosition(i).itemView;
                if (horizontalRecyclerView != recyclerView)
                    horizontalRecyclerView.scrollBy(dx, dy);
            }*/
        }
    };
}