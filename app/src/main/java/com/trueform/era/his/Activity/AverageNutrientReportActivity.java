package com.trueform.era.his.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Model.GetNutrientAverageDeficiencyModel;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.GetNutrientAverageDeficiencyResp;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient1;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.trueform.era.his.Fragment.NutriAnalyserFragment.NUTRI_TOKEN;

public class AverageNutrientReportActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvFrom, tvTo;

    int mDay = 0, mMonth = 0, mYear = 0, hour = 0, minutes = 0;

    Calendar c;

    String fromDate, toDate;

    RecyclerView recyclerViewResult;

    AdapterNutrient adapterNutrient;

    ConstraintLayout clResult;

    Button btnResult;

    List<GetNutrientAverageDeficiencyModel> getNutrientAverageDeficiencyModelList = new ArrayList<>();

    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_nutrient_report);

        init();

        setListeners();

    }

    private void init() {
        ivBack = findViewById(R.id.ivBack);

        btnResult = findViewById(R.id.btnResult);

        tvFrom = findViewById(R.id.tvFrom);
        tvTo = findViewById(R.id.tvTo);

        clResult = findViewById(R.id.clResult);

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);

        recyclerViewResult = findViewById(R.id.recyclerViewResult);

        recyclerViewResult.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewResult.setNestedScrollingEnabled(false);
        recyclerViewResult.setHasFixedSize(true);

    }

    private void setListeners() {
        tvFrom.setOnClickListener(this);
        tvTo.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnResult.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvFrom:

                selectDate(tvFrom, 1);

                break;

            case R.id.tvTo:

                selectDate(tvTo, 2);

                break;

            case R.id.ivBack:

                onBackPressed();

                break;

            case R.id.btnResult:

                if (ConnectivityChecker.checker(getApplicationContext())){

                    if (!tvFrom.getText().toString().isEmpty()
                            && !tvTo.getText().toString().isEmpty()){
                        hitGetNutrientAverageDeficiency();
                    }else {
                        Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void hitGetNutrientAverageDeficiency() {

        Utils.showRequestDialog(AverageNutrientReportActivity.this);

        Call<GetNutrientAverageDeficiencyResp> call = RetrofitClient1.getInstance().getApi().getNutrientAverageDeficiency(
                NUTRI_TOKEN,
                SharedPrefManager.getInstance(getApplicationContext()).getMemberId().getMemberId().toString(),
                SharedPrefManager.getInstance(getApplicationContext()).getMemberId().getUserLoginId().toString(),
                fromDate,
                toDate
        );

        call.enqueue(new Callback<GetNutrientAverageDeficiencyResp>() {
            @Override
            public void onResponse(Call<GetNutrientAverageDeficiencyResp> call, Response<GetNutrientAverageDeficiencyResp> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        getNutrientAverageDeficiencyModelList = response.body().getResponseValue();



                        if (getNutrientAverageDeficiencyModelList.size()>0){
                            clResult.setVisibility(View.VISIBLE);
                        }else {
                            Toast.makeText(AverageNutrientReportActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                            clResult.setVisibility(View.GONE);
                        }

                        adapterNutrient = new AdapterNutrient(getNutrientAverageDeficiencyModelList);
                        recyclerViewResult.setAdapter(adapterNutrient);

                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetNutrientAverageDeficiencyResp> call, Throwable t) {
                Log.e("onFailure:", t.getMessage());

                Toast.makeText(AverageNutrientReportActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                Utils.hideDialog();
            }
        });
    }

    private class AdapterNutrient extends RecyclerView.Adapter<HolderNutrient> {
        List<GetNutrientAverageDeficiencyModel> data = new ArrayList<>();
        private int lastPosition = -1;

        public AdapterNutrient(List<GetNutrientAverageDeficiencyModel> favList) {
            data = favList;
        }

        public HolderNutrient onCreateViewHolder(ViewGroup parent, int viewType) {
            // return new HolderNutrient(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_selected_problems, parent, false));
            return new HolderNutrient(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_average_nutrient_report, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderNutrient holder, final int position) {

            final GetNutrientAverageDeficiencyModel getNutrientAverageDeficiencyModel = data.get(position);

            holder.tvNutrientName.setText(getNutrientAverageDeficiencyModel.getNutrientName());
            holder.tvRda.setText(getNutrientAverageDeficiencyModel.getRda().toString());
            holder.tvAchievementPerc.setText(getNutrientAverageDeficiencyModel.getAchievedPercentage().toString());
            holder.tvAchievementAvg.setText(getNutrientAverageDeficiencyModel.getNutrientAvg().toString());

            //AppUtils.setRecyclerViewAnimation(mActivity,holder.itemView, position, lastPosition);
        }

        @Override
        public int getItemViewType(int position) {
            //return super.getItemViewType(position);
            return position;
        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class HolderNutrient extends RecyclerView.ViewHolder {

        TextView tvNutrientName, tvRda, tvAchievementPerc, tvAchievementAvg;

        public HolderNutrient(View itemView) {
            super(itemView);
            tvNutrientName = itemView.findViewById(R.id.tvNutrientName);
            tvRda = itemView.findViewById(R.id.tvRda);
            tvAchievementPerc = itemView.findViewById(R.id.tvAchievementPerc);
            tvAchievementAvg = itemView.findViewById(R.id.tvAchievementAvg);
        }
    }

    private void selectDate(final TextView textView, int type) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(AverageNutrientReportActivity.this,
                (view, year, monthOfYear, dayOfMonth) -> {

                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;

                    if (type == 1) {

                        fromDate = year + "-"
                                + (monthOfYear + 1) + "-" + dayOfMonth;
                        textView.setText(Utils.formatDateNew(fromDate));
                    } else if (type == 2) {

                        toDate = year + "-"
                                + (monthOfYear + 1) + "-" + dayOfMonth;
                        textView.setText(Utils.formatDateNew(toDate));
                    }



                    c.set(Calendar.YEAR, year);
                    c.set(Calendar.MONTH, monthOfYear);
                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                }, mYear, mMonth, mDay);
        // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
    }

}
