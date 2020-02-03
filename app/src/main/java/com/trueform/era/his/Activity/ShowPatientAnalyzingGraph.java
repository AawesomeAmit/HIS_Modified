package com.trueform.era.his.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HICondition;
import com.highsoft.highcharts.common.hichartsclasses.HICredits;
import com.highsoft.highcharts.common.hichartsclasses.HIDataLabels;
import com.highsoft.highcharts.common.hichartsclasses.HIDrilldown;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HILabel;
import com.highsoft.highcharts.common.hichartsclasses.HILegend;
import com.highsoft.highcharts.common.hichartsclasses.HILine;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIResponsive;
import com.highsoft.highcharts.common.hichartsclasses.HIRules;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HISubtitle;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.AnalyzingGraphResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPatientAnalyzingGraph extends AppCompatActivity {
    Context context;
    TextView txtDateTime;
    int n=0;
    private HIChartView hcView;
    private HIOptions options;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_patient_analyzing_graph);
        hcView = findViewById(R.id.hcView);
        txtDateTime = findViewById(R.id.txtDateTime);
        hcView.plugins = new ArrayList<>(Arrays.asList("drilldown"));
        options = new HIOptions();
        HIChart chart = new HIChart();
        chart.setType("column");
        options.setChart(chart);
        txtDateTime.setText("Graph for "+getIntent().getStringExtra("hour") +"hours from "+getIntent().getStringExtra("from"));
        HITitle title = new HITitle();
        title.setText("");
        options.setTitle(title);
        HIExporting exporting = new HIExporting();
        exporting.setEnabled(false);
        HILegend legend = new HILegend();
        options.setLegend(legend);
        HISubtitle subtitle = new HISubtitle();
        subtitle.setText("");
        options.setSubtitle(subtitle);
        HIYAxis yaxis = new HIYAxis();
        yaxis.setTitle(new HITitle());
        yaxis.getTitle().setText("Data");
        options.setYAxis(new ArrayList<>(Collections.singletonList(yaxis)));
        legend.setLayout("vertical");
        legend.setAlign("right");
        legend.setVerticalAlign("middle");
        options.setLegend(legend);
        HICredits credits = new HICredits();
        credits.setEnabled(false);
        options.setCredits(credits);
        hcView.setOptions(options);
        hcView.getOptions().setExporting(exporting);
        showGraph();
    }

    private void showGraph(){
        Call<AnalyzingGraphResp> call = RetrofitClient.getInstance().getApi().getPatientAnalyzingGraph(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), getIntent().getStringExtra("vital"), getIntent().getStringExtra("fromDate"), SharedPrefManager.getInstance(context).getSubDept().getId(), getIntent().getStringExtra("fromTime"), Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("hour"))),getIntent().getStringExtra("nutrient"), "HOUR");
        call.enqueue(new Callback<AnalyzingGraphResp>() {
            @Override
            public void onResponse(Call<AnalyzingGraphResp> call, Response<AnalyzingGraphResp> response) {
                Utils.showRequestDialog(ShowPatientAnalyzingGraph.this);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        AnalyzingGraphResp analyzingGraphResp = response.body();
                        HIResponsive responsive = new HIResponsive();
                        ArrayList<String> category=new ArrayList<>();
                        ArrayList<HashMap<String, Object>> list1 = new ArrayList<>();
                        if (analyzingGraphResp.getNutrientList().size() > 0) {
                            for (int i = 0; i < analyzingGraphResp.getNutrientList().size(); i++) {
                                HashMap<String, Object> map1=new HashMap<>();
                                map1.put("name", analyzingGraphResp.getNutrientList().get(i).getNutrientName());
                                map1.put("y", analyzingGraphResp.getNutrientList().get(i).getNutrientValueP());
                                map1.put("drilldown", analyzingGraphResp.getNutrientList().get(i).getNutrientName());
                                list1.add(map1);
                            }
                            if (analyzingGraphResp.getVitalList().size() > 0) {
                                for (int i = 0; i < analyzingGraphResp.getVitalList().size(); i++) {
                                    HashMap<String, Object> map1 = new HashMap<>();
                                    map1.put("name", analyzingGraphResp.getVitalList().get(i).getVitalName());
                                    map1.put("y", analyzingGraphResp.getVitalList().get(i).getVmValue());
                                    map1.put("drilldown", analyzingGraphResp.getVitalList().get(i).getVitalName());
                                    list1.add(map1);
                                }
                            }

                            if (analyzingGraphResp.getObservationList().size() > 0) {
                                for (int i = 0; i < analyzingGraphResp.getObservationList().size(); i++) {
                                    /*HashMap<String, Object> map1 = new HashMap<>();
                                    map1.put("name", analyzingGraphResp.getObservationList());
                                    map1.put("y", analyzingGraphResp.getNutrientList().get(i).getNutrientValueP());
                                    map1.put("drilldown", "HR");*/
                                    if(i<48)
                                    category.add(analyzingGraphResp.getObservationList().get(i).getValueTime());
                                }
                            }
                            HITooltip tooltip = new HITooltip();
                            tooltip.setHeaderFormat("<span style=\"font-size:11px\">{series.name}</span><br>");
                            tooltip.setPointFormat("<span style=\"color:{point.color}\">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>");
                            options.setTooltip(tooltip);



                            HIPlotOptions plotOptions = new HIPlotOptions();
                            plotOptions.setSeries(new HISeries());
                            plotOptions.getSeries().setLabel(new HILabel());
                            plotOptions.getSeries().getLabel().setConnectorAllowed(false);
                            final HIXAxis hixAxis=new HIXAxis();
                            hixAxis.setTitle(new HITitle());
                            hixAxis.getTitle().setText("Time");
                            hixAxis.setCategories(category);
                            options.setXAxis(new ArrayList<HIXAxis>(){{add(hixAxis);}});
                            plotOptions.getSeries().setPointInterval(1.0);
                            options.setPlotOptions(plotOptions);
                            List<HILine> hiSeriesList=new ArrayList<>();
                            Log.v("name1", String.valueOf(list1.get(0).get("name")));
                            Log.v("name2", String.valueOf(list1.get(0).containsValue("name")));
                            HILine hiSeries;
                            ArrayList<HashMap<String, Object>> lists = new ArrayList<>();
                            for (int i = 0; i < list1.size(); i++) {
                                if (i < list1.size() - 1) {
                                    if (!String.valueOf(list1.get(i).get("name")).equals(String.valueOf(list1.get(i + 1).get("name")))) {
                                        if(i>0)
                                        lists = new ArrayList<>();
                                        for (int j = n; j < i; j++) {
                                            n=i;
                                            if (j < list1.size() - 1) {
                                                if (String.valueOf(list1.get(j).get("name")).equals(String.valueOf(list1.get(j + 1).get("name")))) {
                                                    lists.add(list1.get(j));
                                                }
                                            }
                                        }
                                        hiSeries = new HILine();
                                        hiSeries.setData(lists);
                                        hiSeries.setName(String.valueOf(list1.get(i).get("name")));
                                        hiSeriesList.add(hiSeries);
                                    } /*else {
                                        for (int j = n; j < i; j++) {
                                            n=i;
                                            if (j < list1.size() - 1) {
                                                if (String.valueOf(list1.get(j).get("name")).equals(String.valueOf(list1.get(j + 1).get("name")))) {
                                                    lists.add(list1.get(j));
                                                }
                                            }
                                        }
                                        hiSeries = new HILine();
                                        hiSeries.setData(lists);
                                        hiSeries.setName(String.valueOf(list1.get(i).get("name")));
                                        hiSeriesList.add(hiSeries);
                                    }*/
                                }
                            }


                            HIRules rules1 = new HIRules();
                            rules1.setCondition(new HICondition());
                            rules1.getCondition().setMaxWidth(500);
                            HashMap<String, HashMap> chartLegend = new HashMap<>();
                            HashMap<String, String> legendOptions = new HashMap<>();
                            legendOptions.put("layout", "horizontal");
                            legendOptions.put("align", "center");
                            legendOptions.put("verticalAlign", "bottom");
                            chartLegend.put("legend", legendOptions);
                            rules1.setChartOptions(chartLegend);
                            responsive.setRules(new ArrayList<>(Collections.singletonList(rules1)));
                            options.setResponsive(responsive);
                            options.setSeries(new ArrayList<>(hiSeriesList));
                            plotOptions.setLine(new HILine());
                            plotOptions.getLine().setDataLabels(new HIDataLabels());
                            plotOptions.getLine().getDataLabels().setEnabled(true);
                            plotOptions.getLine().setEnableMouseTracking(true);
                            options.setPlotOptions(plotOptions);




                            HILine series2 = new HILine();
                            series2.setName("Pulse");
                            series2.setId("Pulse");
                            Object[] object1 = new Object[] { "v11.0", 24.13 };
                            Object[] object2 = new Object[] { "v8.0", 17.2 };
                            Object[] object3 = new Object[] { "v9.0", 8.11 };
                            Object[] object4 = new Object[] { "v10.0", 5.33 };
                            Object[] object5 = new Object[] { "v6.0", 1.06 };
                            Object[] object6 = new Object[] { "v7.0", 0.5 };
                            series2.setData(new ArrayList<>(Arrays.asList(object1, object2, object3, object4, object5, object6)));
                            HILine series=new HILine();
                            series.setData(new ArrayList<>(hiSeriesList));
                            HIDrilldown drillDown = new HIDrilldown();
                            drillDown.setSeries(new ArrayList<>(hiSeriesList));
                            options.setDrilldown(drillDown);




                            hcView.setOptions(options);
                            hcView.reload();
                            hcView.setVisibility(View.VISIBLE);
                            hcView.invalidate();
                            //view.invalidate();
                        } else Toast.makeText(ShowPatientAnalyzingGraph.this, "No data found!", Toast.LENGTH_SHORT).show();
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<AnalyzingGraphResp> call, Throwable t) {
                hcView.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Utils.hideDialog();
            }
        });
    }
}
