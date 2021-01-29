package com.his.android.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Adapter.ActivityDisplayAdp;
import com.his.android.Fragment.InputVital;
import com.his.android.Fragment.IntakeInput;
import com.his.android.Fragment.Output;
import com.his.android.Model.BillList;
import com.his.android.Model.SampleType;
import com.his.android.R;
import com.his.android.Response.BillResp;
import com.his.android.Response.SampleTypeResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SampleCollection extends BaseActivity {
    RecyclerView rvBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_collection);
        rvBill = findViewById(R.id.rvBill);
        load();
    }

    private void load() {
        Utils.showRequestDialog(mActivity);
        Call<BillResp> call = RetrofitClient.getInstance().getApi().getBillListForSampleCollection(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()));
        call.enqueue(new Callback<BillResp>() {
            @Override
            public void onResponse(Call<BillResp> call, Response<BillResp> response) {
                if (response.isSuccessful()) {
                    rvBill.setAdapter(new BillAdp(response.body().getTable()));
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<BillResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    private void showPopup(String billID) {
        Utils.showRequestDialog(mActivity);
        Call<SampleTypeResp> call=RetrofitClient.getInstance().getApi().getSampleList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), billID);
        call.enqueue(new Callback<SampleTypeResp>() {
            @Override
            public void onResponse(Call<SampleTypeResp> call, Response<SampleTypeResp> response) {
                if (response.isSuccessful()){
                    View popupView = getLayoutInflater().inflate(R.layout.popup_sample_type, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
                    RecyclerView rvType=popupView.findViewById(R.id.rvType);
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new ColorDrawable());
                    int[] location = new int[2];
                    rvBill.getLocationOnScreen(location);
                    popupWindow.showAtLocation(rvBill, Gravity.CENTER, 0, 0);
                    rvType.setAdapter(new TypeAdp(response.body().getTable(), billID));
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<SampleTypeResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    class BillAdp extends RecyclerView.Adapter<BillAdp.ViewHolder> {
        List<BillList> billList;

        public BillAdp(List<BillList> billList) {
            this.billList = billList;
        }

        @NonNull
        @Override
        public BillAdp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_sample, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new BillAdp.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.txtBill.setText(billList.get(position).getBillNo());
            holder.llMain.setOnClickListener(view -> /*startActivity(new Intent(mActivity, SampleTestSelect.class).putExtra("billId", billList.get(position).getBillID().toString()))*/
                    showPopup(billList.get(position).getBillID().toString()));
        }

        @Override
        public int getItemCount() {
            return billList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtBill;
            LinearLayout llMain;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtBill = itemView.findViewById(R.id.txtBill);
                llMain = itemView.findViewById(R.id.llMain);
            }
        }
    }

    class TypeAdp extends RecyclerView.Adapter<TypeAdp.ViewHolder> {
        List<SampleType> sampleTypeList;
        String billID;
        public TypeAdp(List<SampleType> sampleTypeList, String billID) {
            this.sampleTypeList = sampleTypeList;
            this.billID = billID;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_sample, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.txtBill.setText(sampleTypeList.get(position).getSampleName());
            holder.llMain.setOnClickListener(view -> startActivity(new Intent(mActivity, SampleTestSelect.class).putExtra("sampleId", sampleTypeList.get(position).getSampleID().toString()).putExtra("billId", billID)));
        }

        @Override
        public int getItemCount() {
            return sampleTypeList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtBill;
            LinearLayout llMain;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtBill = itemView.findViewById(R.id.txtBill);
                llMain = itemView.findViewById(R.id.llMain);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mActivity, ScanSelector.class));
    }
}