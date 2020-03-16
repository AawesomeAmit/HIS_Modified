package com.trueform.era.his.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorLong;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.common.hichartsclasses.HIArea;
import com.highsoft.highcharts.common.hichartsclasses.HIArearange;
import com.highsoft.highcharts.common.hichartsclasses.HIBar;
import com.highsoft.highcharts.common.hichartsclasses.HICSSObject;
import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HIColumn;
import com.highsoft.highcharts.common.hichartsclasses.HICondition;
import com.highsoft.highcharts.common.hichartsclasses.HICredits;
import com.highsoft.highcharts.common.hichartsclasses.HICrosshair;
import com.highsoft.highcharts.common.hichartsclasses.HIDataLabels;
import com.highsoft.highcharts.common.hichartsclasses.HIDrilldown;
import com.highsoft.highcharts.common.hichartsclasses.HIEvents;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HILabel;
import com.highsoft.highcharts.common.hichartsclasses.HILabels;
import com.highsoft.highcharts.common.hichartsclasses.HILegend;
import com.highsoft.highcharts.common.hichartsclasses.HILine;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotBands;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPoint;
import com.highsoft.highcharts.common.hichartsclasses.HIResponsive;
import com.highsoft.highcharts.common.hichartsclasses.HIRules;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HISubtitle;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;
import com.highsoft.highcharts.core.HIFunction;
import com.trueform.era.his.Model.NutrientSubListAnalysingGraph;
import com.trueform.era.his.Model.VitalSubListAnalysingGraph;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.AnalyzingGraphResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
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
    int n = 0;
    HIPlotBands hiPlotBands;
    HIPlotBands hiPlotBands1;
    HIXAxis hixAxis;
    HISeries hiSeries;
    Gson g = new Gson();
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
        txtDateTime.setText("Graph for " + getIntent().getStringExtra("hour") + "hours from " + getIntent().getStringExtra("from"));
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
        yaxis.getTitle().setText("Values");
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

    private void showGraph() {
        Call<AnalyzingGraphResp> call = RetrofitClient.getInstance().getApi().getPatientAnalyzingGraph(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), getIntent().getStringExtra("vital"), getIntent().getStringExtra("fromDate"), SharedPrefManager.getInstance(context).getSubDept().getId(), getIntent().getStringExtra("fromTime"), Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("hour"))), getIntent().getStringExtra("nutrient"), "HOUR", getIntent().getStringExtra("isFoodIntake"), getIntent().getStringExtra("isInvestigation"), getIntent().getStringExtra("isActivity"), getIntent().getStringExtra("isProblem"), getIntent().getStringExtra("isIO"), getIntent().getStringExtra("isIntakeMedicine"), getIntent().getStringExtra("intakeType"));
        call.enqueue(new Callback<AnalyzingGraphResp>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<AnalyzingGraphResp> call, Response<AnalyzingGraphResp> response) {
                Utils.showRequestDialog(ShowPatientAnalyzingGraph.this);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        AnalyzingGraphResp analyzingGraphResp = response.body();
                        HIResponsive responsive = new HIResponsive();
                        ArrayList<String> category = new ArrayList<>();
                        ArrayList<HashMap<String, Object>> list1 = null;
                        ArrayList<HashMap<String, Object>> list2 = null;
                        ArrayList<HashMap<String, Object>> list3 = null;
                        ArrayList<HashMap<String, Object>> list4 = null;
                        ArrayList<HashMap<String, Object>> list5 = null;
                        ArrayList<HashMap<String, Object>> list6 = null;
                        ArrayList<HashMap<String, Object>> list7 = null;
                        ArrayList<HashMap<String, Object>> list8 = null;
                        List<HISeries> hiSeriesList = new ArrayList<>();
                        for (int i = 0; i < analyzingGraphResp.getVitalList().size(); i++) {
                            category.add(analyzingGraphResp.getVitalList().get(i).getValueTime());
                            if (i == 0) {
                                list1 = new ArrayList<>();
                                list2 = new ArrayList<>();
                                list3 = new ArrayList<>();
                                list4 = new ArrayList<>();
                                list5 = new ArrayList<>();
                                list6 = new ArrayList<>();
                                list7 = new ArrayList<>();
                                list8 = new ArrayList<>();
                            }
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put("name", "Intake");
                            map1.put("y", analyzingGraphResp.getVitalList().get(i).getIntakeQty());
                            map1.put("drilldown", "Intake");
                            list1.add(map1);
                            HashMap<String, Object> map2 = new HashMap<>();
                            map2.put("name", "Output");
                            map2.put("y", analyzingGraphResp.getVitalList().get(i).getIntakeQty());
                            map2.put("drilldown", "Output");
                            list2.add(map2);
                            HashMap<String, Object> map3 = new HashMap<>();
                            if (analyzingGraphResp.getVitalList().get(i).getFoodIntake() != null) {
                                map3.put("name", analyzingGraphResp.getVitalList().get(i).getFoodIntake());
                                map3.put("y", 170);
                                map3.put("drilldown", analyzingGraphResp.getVitalList().get(i).getFoodIntake());
                            }
                            list3.add(map3);
                            HashMap<String, Object> map4 = new HashMap<>();
                            if (analyzingGraphResp.getVitalList().get(i).getProblem() != null) {
                                map4.put("name", "Problem");
                                map4.put("y", 140);
                                map4.put("drilldown", "Problem");
                            }
                            list4.add(map4);
                            HashMap<String, Object> map5 = new HashMap<>();
                            if (analyzingGraphResp.getVitalList().get(i).getActivity() != null) {
                                map5.put("name", "Activity");
                                map5.put("y", 160);
                                map5.put("drilldown", "Activity");
                            }
                            list5.add(map5);
                            HashMap<String, Object> map6 = new HashMap<>();
                            if (analyzingGraphResp.getVitalList().get(i).getExercise() != null) {
                                map6.put("name", "Exercise");
                                map6.put("y", 150);
                                map6.put("drilldown", "Exercise");
                            }
                            list6.add(map6);
                            HashMap<String, Object> map7 = new HashMap<>();
                            if (analyzingGraphResp.getVitalList().get(i).getIntakeMedicine() != null) {
                                map7.put("name", "Intake Medicine");
                                map7.put("y", 130);
                                map7.put("drilldown", "Intake Medicine");
                            }
                            list7.add(map7);
                            HashMap<String, Object> map8 = new HashMap<>();
                            if (analyzingGraphResp.getVitalList().get(i).getInvestigation() != null) {
                                map8.put("name", "Investigation");
                                map8.put("y", 120);
                                map8.put("drilldown", "Investigation");
                            }
                            list8.add(map8);
                        }
                        hiSeries = new HIColumn();
                        hiSeries.setData(list1);
                        hiSeries.setName("Intake");
                        hiSeries.setId("Intake");
                        if(Boolean.parseBoolean(getIntent().getStringExtra("isIO")))
                        hiSeriesList.add(hiSeries);
                        hiSeries = new HIColumn();
                        hiSeries.setData(list2);
                        hiSeries.setName("Output");
                        hiSeries.setId("Output");
                        if(Boolean.parseBoolean(getIntent().getStringExtra("isIO")))
                        hiSeriesList.add(hiSeries);
                        hiSeries = new HILine();
                        hiSeries.setData(list3);
                        hiSeries.setName("Food Intake");
                        hiSeries.setId("Food Intake");
                        if(Boolean.parseBoolean(getIntent().getStringExtra("isFoodIntake")))
                        hiSeriesList.add(hiSeries);
                        hiSeries = new HILine();
                        hiSeries.setData(list4);
                        hiSeries.setName("Problem");
                        hiSeries.setId("Problem");
                        if(Boolean.parseBoolean(getIntent().getStringExtra("isProblem")))
                        hiSeriesList.add(hiSeries);
                        hiSeries = new HILine();
                        hiSeries.setData(list5);
                        hiSeries.setName("Activity");
                        hiSeries.setId("Activity");
                        if(Boolean.parseBoolean(getIntent().getStringExtra("isActivity")))
                        hiSeriesList.add(hiSeries);
                        hiSeries = new HILine();
                        hiSeries.setData(list6);
                        hiSeries.setName("Exercise");
                        hiSeries.setId("Exercise");
                        if(Boolean.parseBoolean(getIntent().getStringExtra("isIO")))
                        hiSeriesList.add(hiSeries);
                        hiSeries = new HILine();
                        hiSeries.setData(list7);
                        hiSeries.setName("Intake Medicine");
                        hiSeries.setId("Intake Medicine");
                        if(Boolean.parseBoolean(getIntent().getStringExtra("isIntakeMedicine")))
                        hiSeriesList.add(hiSeries);
                        hiSeries = new HILine();
                        hiSeries.setData(list8);
                        hiSeries.setName("Investigation");
                        hiSeries.setId("Investigation");
                        if(Boolean.parseBoolean(getIntent().getStringExtra("isInvestigation")))
                        hiSeriesList.add(hiSeries);
                        if (analyzingGraphResp.getNutrientTableList().size() > 0) {
                            for (int i = 0; i < analyzingGraphResp.getNutrientTableList().size(); i++) {
                                Type type = new TypeToken<List<NutrientSubListAnalysingGraph>>() {
                                }.getType();
                                List<NutrientSubListAnalysingGraph> p = g.fromJson(analyzingGraphResp.getNutrientTableList().get(i).getNutrient(), type);
                                /*try {
                                    JSONArray array=new JSONArray(analyzingGraphResp.getNutrientList().get(i).getNutrient());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }*/
                                if (p != null) {
                                    list1 = new ArrayList<>();
                                    for (int j = 0; j < p.size(); j++) {
                                        HashMap<String, Object> map1 = new HashMap<>();
                                        map1.put("name", p.get(j).getNutrientName());
                                        map1.put("y", p.get(j).getNutrientValueP());
                                        map1.put("drilldown", p.get(j).getNutrientName());
                                        list1.add(map1);
                                    }
                                }
                                hiSeries = new HIArea();
                                hiSeries.setData(list1);
                                hiSeries.setName(String.valueOf(list1.get(i).get("name")));
                                hiSeries.setId(String.valueOf(list1.get(i).get("name")));
                                hiSeries.setEvents(new HIEvents());
                                /*hiSeries.getEvents().setClick(new HIFunction(() -> {
                                    Log.v("name2", String.valueOf(hixAxis.getCategories().get(0)));
                                    Log.v("name3", String.valueOf(hiSeries.getData().get(0)));
                                    drildown(String.valueOf(hiSeries.getPoint().getYAxis()));
                                }));*/
                                hiSeriesList.add(hiSeries);
                            }
                        }
                        if (analyzingGraphResp.getVitalList().size() > 0) {
                            for (int i = 0; i < analyzingGraphResp.getVitalTableList().size(); i++) {
                                Type type = new TypeToken<List<VitalSubListAnalysingGraph>>() {
                                }.getType();
                                List<VitalSubListAnalysingGraph> p = g.fromJson(analyzingGraphResp.getVitalTableList().get(i).getVitalValue(), type);
                                if (p != null) {
                                    list1 = new ArrayList<>();
                                    for (int j = 0; j < p.size(); j++) {
                                        HashMap<String, Object> map1 = new HashMap<>();
                                        map1.put("name", analyzingGraphResp.getVitalTableList().get(i).getVitalName());
                                        map1.put("y", p.get(j).getVmValue());
                                        map1.put("drilldown", analyzingGraphResp.getVitalTableList().get(i).getVitalName());
                                        list1.add(map1);
                                    }
                                }
                                hiSeries = new HILine();
                                hiSeries.setData(list1);
                                hiSeries.setName(String.valueOf(list1.get(i).get("name")));
                                hiSeries.setId(String.valueOf(list1.get(i).get("name")));
                                hiSeriesList.add(hiSeries);
                            }
                        }
                        /*if (analyzingGraphResp.getObservationList().size() > 0) {
                                for (int i = 0; i < analyzingGraphResp.getObservationList().size(); i++) {
                                HashMap<String, Object> map1 = new HashMap<>();
                                map1.put("name", analyzingGraphResp.getObservationList());
                                map1.put("y", analyzingGraphResp.getNutrientList().get(i).getNutrientValueP());
                                map1.put("drilldown", "HR");
                                    if (i < 48)
                                        category.add(analyzingGraphResp.getObservationList().get(i).getValueTime());
                                }
                            }*/
                        HITooltip tooltip = new HITooltip();
//                        tooltip.setHeaderFormat("<span style=\"font-size:11px\">{series.name}</span><br>");
                        tooltip.setPointFormat("<span style=\"color:{point.color}\">{point.name}</span>: <b>{point.y:.2f}%</b><br/>");
                        tooltip.setShared(true);
                        tooltip.setUseHTML(true);
                        options.setTooltip(tooltip);


                        HIPlotOptions plotOptions = new HIPlotOptions();
                        plotOptions.setSeries(new HISeries());
                        plotOptions.getSeries().setDataLabels(new HIDataLabels());
                        plotOptions.getSeries().getDataLabels().setEnabled(true);
                        plotOptions.getSeries().getDataLabels().setFormat("{point.y:.1f}%");
                        hixAxis = new HIXAxis();
                        hixAxis.setTitle(new HITitle());
                        hixAxis.getTitle().setText("");
                        hixAxis.setCategories(category);
                        hixAxis.setLabels(new HILabels());
                        hixAxis.getLabels().setOverflow("justify");
                        hixAxis.setEvents(new HIEvents());
                        hiPlotBands=new HIPlotBands();
                        hiPlotBands.setFrom(category.indexOf("8:00AM"));
                        hiPlotBands.setColor(HIColor.initWithRGB(159,212,243));
                        hiPlotBands.setTo(category.indexOf("8:00PM"));
                        hiPlotBands1=new HIPlotBands();
                        hiPlotBands1.setFrom(category.indexOf("8:00PM"));
                        hiPlotBands1.setColor(HIColor.initWithRGB(37,41,49));
                        hiPlotBands1.setTo(category.indexOf("7:30AM"));
                        HILabel hiLabel=new HILabel();
                        hiLabel.setText("<img src='http://developer.android.com/assets/images/dac_logo.png'>");
                        HICSSObject hicssObject=new HICSSObject();
                        hiLabel.setStyle(hicssObject);
                        hiLabel.setUseHTML(true);
                        //hiPlotBands1.setLabel(hiLabel);
                        //hiPlotBands.setLabel(hiLabel);
                        HIPlotBands[] plotBandsList = new HIPlotBands[] { hiPlotBands, hiPlotBands1};
                        hixAxis.setPlotBands(new ArrayList<>(Arrays.asList(plotBandsList)));
                        hixAxis.setCrosshair(new HICrosshair());
                        options.setXAxis(new ArrayList<HIXAxis>() {{
                            add(hixAxis);
                        }});
                        plotOptions.getSeries().setPointInterval(1.0);
                        Log.v("name1", String.valueOf(list1.get(0).get("name")));
                        Log.v("name2", String.valueOf(list1.get(0).containsValue("name")));
                        /*ArrayList<HashMap<String, Object>> lists = new ArrayList<>();
                            for (int i = 0; i < list1.size(); i++) {
                                if (i < list1.size() - 1) {
                                    if (!String.valueOf(list1.get(i).get("name")).equals(String.valueOf(list1.get(i + 1).get("name")))) {
                                        if (i > 0)
                                            lists = new ArrayList<>();
                                        for (int j = n; j < i; j++) {
                                            n = i;
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
                                    }
                                    else {
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
                                }
                                }
                            }*/
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
                        plotOptions.setLine(new HILine());
                        plotOptions.getLine().setDataLabels(new HIDataLabels());
                        plotOptions.getLine().getDataLabels().setEnabled(true);
                        plotOptions.getLine().setEnableMouseTracking(true);
                        options.setPlotOptions(plotOptions);
                        options.setSeries(new ArrayList<>(hiSeriesList));


                            /*HIDrilldown drillDown = new HIDrilldown();
                            drillDown.setSeries(new ArrayList<>(hiSeriesList));
                            options.setDrilldown(drillDown);*/

                            /*HILine series2 = new HILine();
                            series2.setName("Calcium");
                            series2.setId("Calcium");
                            Object[] object1 = new Object[]{"v11.0", 24.13};
                            Object[] object2 = new Object[]{"v8.0", 17.2};
                            Object[] object3 = new Object[]{"v9.0", 8.11};
                            Object[] object4 = new Object[]{"v10.0", 5.33};
                            Object[] object5 = new Object[]{"v6.0", 1.06};
                            Object[] object6 = new Object[]{"v7.0", 0.5};
                            series2.setData(new ArrayList<>(Arrays.asList(object1, object2, object3, object4, object5, object6)));
                            HILine series = new HILine();
                            series.setData(new ArrayList<>(Collections.singletonList(series2)));
                            HIDrilldown drillDown1 = new HIDrilldown();
                            drillDown1.setSeries(new ArrayList<>(hiSeriesList));
                            options.setDrilldown(drillDown1);*/


                        hcView.setOptions(options);
                        hcView.reload();
                        hcView.setVisibility(View.VISIBLE);
                        hcView.invalidate();
                        //} else
                        //Toast.makeText(ShowPatientAnalyzingGraph.this, "No data found!", Toast.LENGTH_SHORT).show();
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
    private void drildown(String time){
        Call<AnalyzingGraphResp> call1 = RetrofitClient.getInstance().getApi().getPatientAnalyzingGraphDrilled(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), getIntent().getStringExtra("vital"), getIntent().getStringExtra("fromDate"), SharedPrefManager.getInstance(context).getSubDept().getId(), getIntent().getStringExtra("fromTime"), Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("hour"))), getIntent().getStringExtra("nutrient"), "MINUTE", getIntent().getStringExtra("isFoodIntake"), getIntent().getStringExtra("isInvestigation"), getIntent().getStringExtra("isActivity"), getIntent().getStringExtra("isProblem"), getIntent().getStringExtra("isIO"), getIntent().getStringExtra("isIntakeMedicine"), "2020-03-11 19:30:00 ");
        call1.enqueue(new Callback<AnalyzingGraphResp>() {
            @Override
            public void onResponse(Call<AnalyzingGraphResp> call, Response<AnalyzingGraphResp> response) {
                Utils.showRequestDialog(ShowPatientAnalyzingGraph.this);
                if (response.isSuccessful()) {
                    AnalyzingGraphResp drilledResp = response.body();

                    HISeries hiSeries;
                    List<HISeries> hiSeriesListDrilled = new ArrayList<>();
                    ArrayList<HashMap<String, Object>> list1 = null;
                    ArrayList<String> category1 = new ArrayList<>();

                    for (int i = 0; i < drilledResp.getVitalList().size(); i++) {
                        category1.add(drilledResp.getVitalList().get(i).getValueTime());
                    }

                    if (drilledResp.getNutrientTableList().size() > 0) {
                        for (int i = 0; i < drilledResp.getNutrientTableList().size(); i++) {
                            Type type = new TypeToken<List<NutrientSubListAnalysingGraph>>() {
                            }.getType();
                            List<NutrientSubListAnalysingGraph> p = g.fromJson(drilledResp.getNutrientTableList().get(i).getNutrient(), type);
                                            /*try {
                                                JSONArray array=new JSONArray(analyzingGraphResp.getNutrientList().get(i).getNutrient());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }*/
                            if (p != null) {
                                list1 = new ArrayList<>();
                                for (int j = 0; j < p.size(); j++) {
                                    HashMap<String, Object> map1 = new HashMap<>();
                                    map1.put("name", p.get(j).getNutrientName());
                                    map1.put("y", p.get(j).getNutrientValueP());
                                    map1.put("drilldown", p.get(j).getNutrientName());
                                    list1.add(map1);
                                }
                            }
                            hiSeries = new HIArea();
                            hiSeries.setData(list1);
                            hiSeries.setName(String.valueOf(list1.get(i).get("name")));
                            hiSeries.setId(String.valueOf(list1.get(i).get("name")));
                            hiSeriesListDrilled.add(hiSeries);
                        }
                    }
                    if (drilledResp.getVitalList().size() > 0) {
                        for (int i = 0; i < drilledResp.getVitalTableList().size(); i++) {
                            Type type = new TypeToken<List<VitalSubListAnalysingGraph>>() {
                            }.getType();
                            List<VitalSubListAnalysingGraph> p = g.fromJson(drilledResp.getVitalTableList().get(i).getVitalValue(), type);
                            if (p != null) {
                                list1 = new ArrayList<>();
                                for (int j = 0; j < p.size(); j++) {
                                    HashMap<String, Object> map1 = new HashMap<>();
                                    map1.put("name", drilledResp.getVitalTableList().get(i).getVitalName());
                                    map1.put("y", p.get(j).getVmValue());
                                    map1.put("drilldown", drilledResp.getVitalTableList().get(i).getVitalName());
                                    list1.add(map1);
                                }
                            }
                            hiSeries = new HILine();
                            hiSeries.setData(list1);
                            hiSeries.setName(String.valueOf(list1.get(i).get("name")));
                            hiSeries.setId(String.valueOf(list1.get(i).get("name")));
                            hiSeriesListDrilled.add(hiSeries);
                        }
                    }

                    final HIXAxis hixAxis = new HIXAxis();
                    hixAxis.setTitle(new HITitle());
                    hixAxis.getTitle().setText("");
                    hixAxis.setCategories(category1);
                    HIDrilldown drillDown1 = new HIDrilldown();
                    drillDown1.setSeries(new ArrayList<>(hiSeriesListDrilled));
                    options.setDrilldown(drillDown1);
                    //hcView.setOptions(options);
                    //hcView.reload();
                    //hcView.invalidate();
                }
            }

            @Override
            public void onFailure(Call<AnalyzingGraphResp> call, Throwable t) {

            }
        });
    }
}
