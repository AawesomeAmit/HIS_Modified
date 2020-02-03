package com.trueform.era.his.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.common.hichartsclasses.HIAreaspline;
import com.highsoft.highcharts.common.hichartsclasses.HICredits;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HILegend;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotBands;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;
import com.trueform.era.his.Model.GetIdealNutrientGraphDetailModel;
import com.trueform.era.his.Model.GetNutrientAverageDeficiencyModel;
import com.trueform.era.his.Model.GetNutrientSearchListModel;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.GetIdealNutrientIntakeResp;
import com.trueform.era.his.Response.GetNutrientSearchListResp;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient1;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.trueform.era.his.Fragment.NutriAnalyserFragment.NUTRI_TOKEN;

public class IdealNutrientIntakeActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerViewResult;

    AdapterNutrient adapterNutrient;

    Button btnResult;

    List<GetIdealNutrientGraphDetailModel> getIdealNutrientGraphDetailModelList = new ArrayList<>();

    ImageView ivBack;

    String nutrientID = "";

    AutoCompleteTextView etNutrient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideal_nutrient_intake);

        init();

        setListeners();

    }

    private void init() {
        ivBack = findViewById(R.id.ivBack);

        etNutrient = findViewById(R.id.etNutrient);
        etNutrient.setThreshold(2);

        btnResult = findViewById(R.id.btnResult);

        recyclerViewResult = findViewById(R.id.recyclerViewResult);

        recyclerViewResult.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewResult.setNestedScrollingEnabled(false);
        recyclerViewResult.setHasFixedSize(true);

    }

    private void setListeners() {
        ivBack.setOnClickListener(this);
        btnResult.setOnClickListener(this);

        etNutrient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    if (ConnectivityChecker.checker(getApplicationContext())) {
                        hitGetNutrientSearchList(s.toString());
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivBack:

                onBackPressed();

                break;

            case R.id.btnResult:

                if (ConnectivityChecker.checker(getApplicationContext())) {

                    if (!etNutrient.getText().toString().isEmpty() && !nutrientID.equalsIgnoreCase("")) {
                        hitGetIdealNutrientIntakeGraphData();
                    } else {
                        Toast.makeText(this, "Select Nutrient", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void hitGetNutrientSearchList(String searchText) {

        Utils.showRequestDialog(this);

        Call<GetNutrientSearchListResp> call = RetrofitClient1.getInstance().getApi().getNutrientSearchList(
                NUTRI_TOKEN,
                SharedPrefManager.getInstance(getApplicationContext()).getMemberId().getMemberId().toString(),
                SharedPrefManager.getInstance(getApplicationContext()).getMemberId().getUserLoginId().toString(),
                searchText
        );

        call.enqueue(new Callback<GetNutrientSearchListResp>() {
            @Override
            public void onResponse(Call<GetNutrientSearchListResp> call, Response<GetNutrientSearchListResp> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        List<GetNutrientSearchListModel> getNutrientSearchListModelList = response.body().getResponseValue();

                        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout_new, getNutrientSearchListModelList);
                        //arrayAdapter.setDropDownViewResource(R.layout.inflate_auto_complete_text);
                        etNutrient.setAdapter(arrayAdapter);

                        etNutrient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                try {

                                    nutrientID = getNutrientSearchListModelList.get(position).getNutrientID().toString();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetNutrientSearchListResp> call, Throwable t) {
                Log.e("onFailure:", t.getMessage());

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                Utils.hideDialog();
            }
        });
    }


    private void hitGetIdealNutrientIntakeGraphData() {

        Utils.showRequestDialog(IdealNutrientIntakeActivity.this);

        Call<GetIdealNutrientIntakeResp> call = RetrofitClient1.getInstance().getApi().getIdealNutrientIntakeGraphData(
                NUTRI_TOKEN,
                SharedPrefManager.getInstance(getApplicationContext()).getMemberId().getMemberId().toString(),
                SharedPrefManager.getInstance(getApplicationContext()).getMemberId().getUserLoginId().toString(),
                nutrientID
        );

        call.enqueue(new Callback<GetIdealNutrientIntakeResp>() {
            @Override
            public void onResponse(Call<GetIdealNutrientIntakeResp> call, Response<GetIdealNutrientIntakeResp> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        getIdealNutrientGraphDetailModelList = response.body().getResponseValue().getGraphDetails();
                        setGraph();

                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetIdealNutrientIntakeResp> call, Throwable t) {
                Log.e("onFailure:", t.getMessage());

                Toast.makeText(IdealNutrientIntakeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

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

    private void setGraph() {

        ArrayList<String> XAxisValues = new ArrayList<>();
        ArrayList<String> dataValues = new ArrayList<>();
        try {
            HIChartView chartView = findViewById(R.id.chartView);

            HIOptions options = new HIOptions();

            options.setExporting(new HIExporting());
            options.getExporting().setEnabled(false);

            HITitle title = new HITitle();
            title.setText("Nutrient Timeline Graph");
            options.setTitle(title);

            HILegend legend = new HILegend();
            legend.setLayout("vertical");
            legend.setAlign("left");
            legend.setVerticalAlign("top");
            legend.setX(150);
            legend.setY(150);
            legend.setFloating(true);
            legend.setBorderWidth(1);
            legend.setBackgroundColor(HIColor.initWithHexValue("FFFFFF"));
            options.setLegend(legend);

            HIXAxis xaxis = new HIXAxis();

            for (int i = 0; i < getIdealNutrientGraphDetailModelList.size(); i++) {

                XAxisValues.add("(" + getIdealNutrientGraphDetailModelList.get(i).getIntakeQuantity() + " mg) " +
                        getIdealNutrientGraphDetailModelList.get(i).getGraphTime());

                dataValues.add(getIdealNutrientGraphDetailModelList.get(i).getNutrientValue().toString());

            }

//        String[] categories = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
//        xaxis.setCategories(new ArrayList<>(Arrays.asList(categories)));

            xaxis.setCategories(XAxisValues);

            HIPlotBands plotband = new HIPlotBands();
            plotband.setFrom(4.5);
            plotband.setTo(6.5);
            plotband.setColor(HIColor.initWithRGBA(68, 170, 213, 2));
            xaxis.setPlotBands(new ArrayList<>(Arrays.asList(plotband)));
            options.setXAxis(new ArrayList<HIXAxis>() {{
                add(xaxis);
            }});

            HIYAxis yaxis = new HIYAxis();
            yaxis.setTitle(new HITitle());
            yaxis.getTitle().setText("Value in mg");
            options.setYAxis(new ArrayList<HIYAxis>() {{
                add(yaxis);
            }});

            HITooltip tooltip = new HITooltip();
            tooltip.setShared(true);
//        tooltip.setValueSuffix(" units");
            options.setTooltip(tooltip);

            HICredits credits = new HICredits();
            credits.setEnabled(false);
            options.setCredits(credits);

            HIPlotOptions plotOptions = new HIPlotOptions();
            plotOptions.setAreaspline(new HIAreaspline());
            plotOptions.getAreaspline().setFillOpacity(0.5);
            options.setPlotOptions(plotOptions);

            HIAreaspline areaspline1 = new HIAreaspline();
            areaspline1.setName("Nutrient Value");
//        Number[] areaspline1Data = new Number[] {3, 4, 3, 5, 4, 10, 12 };
//        areaspline1.setData(new ArrayList<>(Arrays.asList(areaspline1Data)));
            areaspline1.setData(dataValues);

            options.setSeries(new ArrayList<>(Arrays.asList(areaspline1)));

            chartView.setOptions(options);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
