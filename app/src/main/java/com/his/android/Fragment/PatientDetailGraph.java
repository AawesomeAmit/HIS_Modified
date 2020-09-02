package com.his.android.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.common.hichartsclasses.HIArea;
import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HIColumn;
import com.highsoft.highcharts.common.hichartsclasses.HICondition;
import com.highsoft.highcharts.common.hichartsclasses.HICredits;
import com.highsoft.highcharts.common.hichartsclasses.HICrosshair;
import com.highsoft.highcharts.common.hichartsclasses.HIDataLabels;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HILabel;
import com.highsoft.highcharts.common.hichartsclasses.HILegend;
import com.highsoft.highcharts.common.hichartsclasses.HILine;
import com.highsoft.highcharts.common.hichartsclasses.HIMarker;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotLines;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIResponsive;
import com.highsoft.highcharts.common.hichartsclasses.HIRules;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HISpline;
import com.highsoft.highcharts.common.hichartsclasses.HISubtitle;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;
import com.his.android.Adapter.FoodNutrientAdp;
import com.his.android.Adapter.SupplementNutrientAdp;
import com.his.android.Model.NutrientFoodResponseValue;
import com.his.android.Model.Nutrition;
import com.his.android.R;
import com.his.android.Response.NutrientBindRes;
import com.his.android.Response.PatientDetailFoodNutrientResp;
import com.his.android.Response.PatientDetailGraphResp;
import com.his.android.Response.PatientNutrientGraphResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientDetailGraph extends Fragment implements View.OnClickListener {
    int mYear = 0, mMonth = 0, mDay = 0;
    Calendar c;
    View view;
    Context context;
    private TextView txtDate;
    private LinearLayout llNutrientGraph;
    static String date = "";
    private HIChartView hcNutrientView, hcFoodVitalGraph, hcMedicineVitalGraph, hcActVitalGraph, hcProbVitalGraph, hcInOpGraph;
    private HIOptions options, optionsFoodVitalGraph, optionsMedicineVitalGraph, optionsActVitalGraph, optionsProbVitalGraph, optionsInOpGraph;
    private HICredits credits, creditsFoodVitalGraph;
    private HIExporting exporting, exportingFoodVitalGraph;
    private RecyclerView rvFood, rvSupplement;
    private List<Nutrition> nutritionList=new ArrayList<>();
    private ArrayList<String> selectedMembersList = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerViewMembers;
    private ChipsInput chpInput;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PatientDetailGraph() {
        // Required empty public constructor
    }

    public static PatientDetailGraph newInstance(String param1, String param2) {
        PatientDetailGraph fragment = new PatientDetailGraph();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_patient_detail_graph, container, false);
        txtDate=view.findViewById(R.id.txtDate);
        TextView btnShow = view.findViewById(R.id.btnShow);
        exporting = new HIExporting();
        exporting.setEnabled(false);
        hcNutrientView=view.findViewById(R.id.hcView);
        hcFoodVitalGraph=view.findViewById(R.id.hcFoodVitalGraph);
        hcProbVitalGraph=view.findViewById(R.id.hcProbVitalGraph);
        hcActVitalGraph=view.findViewById(R.id.hcActVitalGraph);
        hcInOpGraph=view.findViewById(R.id.hcInOpGraph);
        llNutrientGraph=view.findViewById(R.id.llNutrientGraph);
        hcMedicineVitalGraph=view.findViewById(R.id.hcMedicineVitalGraph);
        rvFood=view.findViewById(R.id.rvFood);
        rvSupplement=view.findViewById(R.id.rvSupplement);
        options=new HIOptions();
        credits = new HICredits();
        credits.setEnabled(false);
        options.setCredits(credits);
        hcNutrientView.setOptions(options);
        optionsActVitalGraph=new HIOptions();
        hcActVitalGraph.setOptions(optionsActVitalGraph);
        optionsProbVitalGraph=new HIOptions();
        hcProbVitalGraph.setOptions(optionsProbVitalGraph);
        optionsFoodVitalGraph=new HIOptions();
        hcFoodVitalGraph.setOptions(optionsFoodVitalGraph);
        optionsMedicineVitalGraph=new HIOptions();
        hcMedicineVitalGraph.setOptions(optionsMedicineVitalGraph);
        optionsInOpGraph=new HIOptions();
        hcInOpGraph.setOptions(optionsInOpGraph);
        context=view.getContext();
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.DAY_OF_MONTH);
        txtDate.setText(Utils.formatDate(date));
        txtDate.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        chpInput = view.findViewById(R.id.chpInput);
        chpInput.setChipHasAvatarIcon(true);
        chpInput.setChipDeletable(true);
        chpInput.setShowChipDetailed(false);
        rvFood.setLayoutManager(new LinearLayoutManager(context));
        rvSupplement.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewMembers = view.findViewById(R.id.recyclerViewMembers);
        recyclerViewMembers.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewMembers.setNestedScrollingEnabled(false);
        chpInput.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chip, int newSize) {
                selectedMembersList.add(String.valueOf(chip.getId()));
                nutritionList.clear();
                NutrientChipAdp nutrientChipAdp=new NutrientChipAdp(nutritionList);
                recyclerViewMembers.setAdapter(nutrientChipAdp);
                nutrientChipAdp.notifyDataSetChanged();
            }

            @Override
            public void onChipRemoved(ChipInterface chip, int newSize) {
                selectedMembersList.remove(chip.getId().toString());
            }

            @Override
            public void onTextChanged(CharSequence text) {
                bindNutrientList(String.valueOf(text));
                NutrientChipAdp nutrientChipAdp=new NutrientChipAdp(nutritionList);
                recyclerViewMembers.setAdapter(nutrientChipAdp);
                nutrientChipAdp.notifyDataSetChanged();
            }
        });
        bindFoodList();
        bindFoodVitalGraph();
        return view;
    }
    private void bindFoodVitalGraph() {
        Utils.showRequestDialog(context);
        Call<PatientDetailGraphResp> call = RetrofitClient.getInstance().getApi().getPatientDetailGraph(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), mYear + "-" + (mMonth + 1) + "-" + mDay, SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getSubDept().getId());
        call.enqueue(new Callback<PatientDetailGraphResp>() {
            @Override
            public void onResponse(Call<PatientDetailGraphResp> call, Response<PatientDetailGraphResp> response) {
                if (response.isSuccessful()) {
                    PatientDetailGraphResp patientDetailGraphResp = response.body();
                    if (patientDetailGraphResp != null) {
                        ArrayList<Float> foodList = new ArrayList<>();
                        ArrayList<Float> diasList = new ArrayList<>();
                        ArrayList<Float> sysList = new ArrayList<>();
                        ArrayList<Float> hrList = new ArrayList<>();
                        ArrayList<Float> pulseList = new ArrayList<>();
                        ArrayList<Float> rrList = new ArrayList<>();
                        ArrayList<Float> spo2List = new ArrayList<>();
                        ArrayList<Float> tempList = new ArrayList<>();
                        ArrayList<Float> rrSpList = new ArrayList<>();
                        ArrayList<Float> pvSpList = new ArrayList<>();
                        ArrayList<String> time = new ArrayList<>();
                        HIResponsive responsive = new HIResponsive();
                        optionsFoodVitalGraph=new HIOptions();
                        optionsFoodVitalGraph.setCredits(credits);
                        hcFoodVitalGraph.plugins = new ArrayList<>();
                        hcFoodVitalGraph.plugins.add("series-label");
                        for (int j = 0; j < patientDetailGraphResp.getVitalList().size(); j++) {
                            if (patientDetailGraphResp.getVitalList().get(j).getbPDias()==null)
                                diasList.add(null);
                            else
                                diasList.add(Float.parseFloat(patientDetailGraphResp.getVitalList().get(j).getbPSys()));
                            if (patientDetailGraphResp.getDetailList().get(j).getIntakeFood()==null)
                                foodList.add(null);
                            else
                                foodList.add(Float.valueOf("0.0"));
                            if (patientDetailGraphResp.getVitalList().get(j).getbPSys()==null)
                                sysList.add(null);
                            else
                                sysList.add(Float.parseFloat(patientDetailGraphResp.getVitalList().get(j).getbPSys()));
                            if (patientDetailGraphResp.getVitalList().get(j).getRespRate()==null)
                                rrList.add(null);
                            else
                                rrList.add(Float.parseFloat(patientDetailGraphResp.getVitalList().get(j).getRespRate()));
                            if (patientDetailGraphResp.getVitalList().get(j).getHeartRate()==null)
                                hrList.add(null);
                            else
                                hrList.add(Float.parseFloat(patientDetailGraphResp.getVitalList().get(j).getHeartRate()));
                            if (patientDetailGraphResp.getVitalList().get(j).getTemperature()==null)
                                tempList.add(null);
                            else
                                tempList.add(Float.parseFloat(patientDetailGraphResp.getVitalList().get(j).getTemperature()));
                            if (patientDetailGraphResp.getVitalList().get(j).getPulse()==null)
                                pulseList.add(null);
                            else
                                pulseList.add(Float.parseFloat(patientDetailGraphResp.getVitalList().get(j).getPulse()));
                            if (patientDetailGraphResp.getVitalList().get(j).getSpo2()==null)
                                spo2List.add(null);
                            else
                                spo2List.add(Float.parseFloat(patientDetailGraphResp.getVitalList().get(j).getSpo2()));
                            if (patientDetailGraphResp.getVitalList().get(j).getRr_sp()==null)
                                rrSpList.add(null);
                            else
                                rrSpList.add(Float.parseFloat(patientDetailGraphResp.getVitalList().get(j).getRr_sp()));
                            /*if (patientDetailGraphResp.getVitalList().get(j).getPv_sp()==null)
                                pvSpList.add(null);
                            else
                                pvSpList.add(Float.parseFloat(patientDetailGraphResp.getVitalList().get(j).getPv_sp()));*/
                            time.add(patientDetailGraphResp.getVitalList().get(j).getTime());
                        }
                        HITitle title = new HITitle();
                        title.setText("Patient Food Vital Graph");
                        optionsFoodVitalGraph.setTitle(title);
                        HIPlotOptions plotOptions = new HIPlotOptions();
                        plotOptions.setSeries(new HISeries());
                        plotOptions.getSeries().setLabel(new HILabel());
                        plotOptions.getSeries().getLabel().setConnectorAllowed(false);
                        final HIXAxis hixAxis=new HIXAxis();
                        hixAxis.setTitle(new HITitle());
                        hixAxis.getTitle().setText("Time");
                        hixAxis.setCategories(time);
                        optionsFoodVitalGraph.setXAxis(new ArrayList<HIXAxis>(){{add(hixAxis);}});
                        plotOptions.getSeries().setPointInterval(1.0);
                        optionsFoodVitalGraph.setPlotOptions(plotOptions);
                        HITooltip tooltip = new HITooltip();
                        tooltip.setPointFormat("<span style=\"color:{series.color}\">{series.name}</span>: <b>({point.y:,.0f})</b><br/>");
                        /*tooltip.setPointFormat("new function () {" +
                                "                    var toolTime = this.x;" +
                                "                    var arr = $.grep("+patientDetailGraphResp.getDetailList().get(0).getIntakeFood()+", function (element, index) {" +
                                "                        return element.time == toolTime;" +
                                "                    });" +
                                "                    var item = arr[0].item;" +
                                "                    if ({series.name} == Food) {" +
                                "                        <span style=\"color:{series.color}\">{series.name}</span>: "+patientDetailGraphResp.getDetailList().get(0).getIntakeFood()+"<br/>"+
                                "                    } else {" +
                                "                        return {series.name} == Food ? <span style=\"color:{series.color}\">{series.name}</span>: \"+patientDetailGraphResp.getDetailList().get(0).getIntakeFood()+\"<br/>\" : <span style=\"color:{series.color}\">{series.name}</span>: <b>({point.y:,.0f})</b><br/>"+
                                "                    }" +
                                "                }");*/
                        /*tooltip.setPointFormat("<if {{series.name} = Food ?} <span style=\"color:{series.color}\">{series.name}</span>: "
                                +patientDetailGraphResp.getDetailList().get(0).getIntakeFood() +
                                "<br/> : <span style=\"color:{series.color}\">{series.name}</span>: <b>({point.y:,.0f})</b><br/>");*/
                        /*tooltip.setPointFormat(String.valueOf(new HIFunction(f -> f.getProperty("value") + "°",
                                new String[] {"value"}))
                        );*/
                        tooltip.setSplit(false);
                        optionsFoodVitalGraph.setTooltip(tooltip);
                        HISeries line1 = new HISpline();
                        HISeries line2 = new HISpline();
                        HISeries line3 = new HISpline();
                        HISeries line4 = new HISpline();
                        HISeries line5 = new HISpline();
                        HISeries line6 = new HISpline();
                        HISeries line7 = new HISpline();
                        HISeries line8 = new HISpline();
                        HISeries line9 = new HISpline();
                        HISeries line10 = new HISpline();
                        line1.setName("Food");
                        line2.setName("SBP");
                        line3.setName("DBP");
                        line4.setName("RR");
                        line5.setName("Temp");
                        line6.setName("Pulse");
                        line7.setName("HR");
                        line8.setName("SPO2");
                        line9.setName("RR SP");
                        line10.setName("PV SP");
                        line1.setData(foodList);
                        line2.setData(sysList);
                        line3.setData(diasList);
                        line4.setData(rrList);
                        line5.setData(tempList);
                        line6.setData(pulseList);
                        line7.setData(hrList);
                        line8.setData(spo2List);
                        line9.setData(rrSpList);
                        line10.setData(pvSpList);
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
                        optionsFoodVitalGraph.setResponsive(responsive);
                        optionsFoodVitalGraph.setSeries(new ArrayList<>(Arrays.asList(line1, line2, line3, line4, line5, line6, line7, line8, line9)));
                        //optionsFoodVitalGraph.setSeries(new ArrayList<>(Arrays.asList(line1, line2, line3, line4, line5, line6, line7, line8, line9, line10)));
                        plotOptions.setLine(new HILine());
                        plotOptions.getLine().setDataLabels(new HIDataLabels());
                        plotOptions.getLine().getDataLabels().setEnabled(true);
                        plotOptions.getLine().setEnableMouseTracking(true);
                        optionsFoodVitalGraph.setPlotOptions(plotOptions);
                        hcFoodVitalGraph.setOptions(optionsFoodVitalGraph);
                        hcFoodVitalGraph.getOptions().setExporting(exporting);
                        hcFoodVitalGraph.reload();
                        hcFoodVitalGraph.setVisibility(View.VISIBLE);
                        hcFoodVitalGraph.invalidate();
                        view.invalidate();






                        optionsMedicineVitalGraph=new HIOptions();
                        optionsMedicineVitalGraph.setCredits(credits);
                        hcMedicineVitalGraph.plugins = new ArrayList<>();
                        hcMedicineVitalGraph.plugins.add("series-label");
                        ArrayList<Float> medList=new ArrayList<>();
                        for (int j = 0; j < patientDetailGraphResp.getDrugList().size(); j++) {
                            if (patientDetailGraphResp.getVitalList().get(j).getbPDias()==null) medList.add(null);
                            else medList.add(Float.parseFloat(patientDetailGraphResp.getVitalList().get(j).getbPSys()));
                        }
                        HITitle titleMed = new HITitle();
                        titleMed.setText("Patient Medical Vital Graph");
                        optionsMedicineVitalGraph.setTitle(titleMed);
                        HIPlotOptions plotMedOptions = new HIPlotOptions();
                        plotMedOptions.setSeries(new HISeries());
                        plotMedOptions.getSeries().setLabel(new HILabel());
                        plotMedOptions.getSeries().getLabel().setConnectorAllowed(false);
                        final HIXAxis hiMedxAxis=new HIXAxis();
                        hiMedxAxis.setTitle(new HITitle());
                        hiMedxAxis.getTitle().setText("Time");
                        hiMedxAxis.setCategories(time);
                        optionsMedicineVitalGraph.setXAxis(new ArrayList<HIXAxis>(){{add(hiMedxAxis);}});
                        plotMedOptions.getSeries().setPointInterval(1.0);
                        optionsMedicineVitalGraph.setPlotOptions(plotMedOptions);
                        HITooltip tooltipMed = new HITooltip();
                        tooltipMed.setPointFormat("<span style=\"color:{series.color}\">{series.name}</span>: <b>({point.y:,.0f})</b><br/>");
                        tooltipMed.setSplit(false);
                        optionsMedicineVitalGraph.setTooltip(tooltipMed);
                        HISeries lineMed1 = new HISpline();
                        HISeries lineMed2 = new HISpline();
                        HISeries lineMed3 = new HISpline();
                        HISeries lineMed4 = new HISpline();
                        HISeries lineMed5 = new HISpline();
                        HISeries lineMed6 = new HISpline();
                        HISeries lineMed7 = new HISpline();
                        HISeries lineMed8 = new HISpline();
                        HISeries lineMed9 = new HISpline();
                        lineMed1.setName("SBP");
                        lineMed2.setName("DBP");
                        lineMed3.setName("RR");
                        lineMed4.setName("Temp");
                        lineMed5.setName("Pulse");
                        lineMed6.setName("HR");
                        lineMed7.setName("SPO2");
                        lineMed8.setName("RR SP");
                        lineMed9.setName("PV SP");
                        lineMed1.setData(sysList);
                        lineMed2.setData(diasList);
                        lineMed3.setData(rrList);
                        lineMed4.setData(tempList);
                        lineMed5.setData(pulseList);
                        lineMed6.setData(hrList);
                        lineMed7.setData(spo2List);
                        lineMed8.setData(rrSpList);
                        lineMed9.setData(pvSpList);
                        HIRules rulesMed1 = new HIRules();
                        rulesMed1.setCondition(new HICondition());
                        rulesMed1.getCondition().setMaxWidth(500);
                        HashMap<String, HashMap> chartLegendMed = new HashMap<>();
                        HashMap<String, String> legendOptionsMed = new HashMap<>();
                        legendOptionsMed.put("layout", "horizontal");
                        legendOptionsMed.put("align", "center");
                        legendOptionsMed.put("verticalAlign", "bottom");
                        chartLegendMed.put("legend", legendOptionsMed);
                        rulesMed1.setChartOptions(chartLegendMed);
                        responsive.setRules(new ArrayList<>(Collections.singletonList(rulesMed1)));
                        optionsMedicineVitalGraph.setResponsive(responsive);
                        optionsMedicineVitalGraph.setSeries(new ArrayList<>(Arrays.asList(lineMed1, lineMed2, lineMed3, lineMed4, lineMed5, lineMed6, lineMed7, lineMed8)));
                        //optionsMedicineVitalGraph.setSeries(new ArrayList<>(Arrays.asList(lineMed1, lineMed2, lineMed3, lineMed4, lineMed5, lineMed6, lineMed7, lineMed8, lineMed9)));
                        plotOptions = new HIPlotOptions();
                        plotOptions.setLine(new HILine());
                        plotOptions.getLine().setDataLabels(new HIDataLabels());
                        plotOptions.getLine().getDataLabels().setEnabled(true);
                        plotOptions.getLine().setEnableMouseTracking(true);
                        optionsMedicineVitalGraph.setPlotOptions(plotOptions);
                        hcMedicineVitalGraph.setOptions(optionsMedicineVitalGraph);
                        hcMedicineVitalGraph.getOptions().setExporting(exporting);
                        hcMedicineVitalGraph.reload();
                        hcMedicineVitalGraph.setVisibility(View.VISIBLE);
                        hcMedicineVitalGraph.invalidate();
                        view.invalidate();





                        optionsActVitalGraph=new HIOptions();
                        optionsActVitalGraph.setCredits(credits);
                        hcActVitalGraph.plugins = new ArrayList<>();
                        hcActVitalGraph.plugins.add("series-label");
                        ArrayList<Float> activityList=new ArrayList<>();
                        for (int j = 0; j < patientDetailGraphResp.getDetailList().size(); j++) {
                            if (patientDetailGraphResp.getDetailList().get(j).getActivity()==null) activityList.add(null);
                            else activityList.add(Float.parseFloat("0.0"));
                        }
                        HITitle titleActivity = new HITitle();
                        titleActivity.setText("Patient Activity Vital Graph");
                        optionsActVitalGraph.setTitle(titleActivity);
                        HIPlotOptions plotActivityOptions = new HIPlotOptions();
                        plotActivityOptions.setSeries(new HISeries());
                        plotActivityOptions.getSeries().setLabel(new HILabel());
                        plotActivityOptions.getSeries().getLabel().setConnectorAllowed(false);
                        final HIXAxis hiActivityxAxis=new HIXAxis();
                        hiActivityxAxis.setTitle(new HITitle());
                        hiActivityxAxis.getTitle().setText("Time");
                        hiActivityxAxis.setCategories(time);
                        optionsActVitalGraph.setXAxis(new ArrayList<HIXAxis>(){{add(hiActivityxAxis);}});
                        plotActivityOptions.getSeries().setPointInterval(1.0);
                        optionsActVitalGraph.setPlotOptions(plotActivityOptions);
                        HITooltip tooltipAct = new HITooltip();
                        tooltipAct.setPointFormat("<span style=\"color:{series.color}\">{series.name}</span>: <b>({point.y:,.0f})</b><br/>");
                        tooltipAct.setSplit(false);
                        optionsActVitalGraph.setTooltip(tooltipAct);
                        HISeries lineAct1 = new HISpline();
                        lineAct1.setName("Activity");
                        lineAct1.setData(activityList);
                        //HIRules rulesMed1 = new HIRules();
                        rulesMed1.setCondition(new HICondition());
                        rulesMed1.getCondition().setMaxWidth(500);
                        //HashMap<String, HashMap> chartLegendMed = new HashMap<>();
                        //HashMap<String, String> legendOptionsMed = new HashMap<>();
                        legendOptionsMed.put("layout", "horizontal");
                        legendOptionsMed.put("align", "center");
                        legendOptionsMed.put("verticalAlign", "bottom");
                        chartLegendMed.put("legend", legendOptionsMed);
                        rulesMed1.setChartOptions(chartLegendMed);
                        responsive.setRules(new ArrayList<>(Collections.singletonList(rulesMed1)));
                        optionsActVitalGraph.setResponsive(responsive);
                        optionsActVitalGraph.setSeries(new ArrayList<>(Arrays.asList(lineAct1, line2, line3, line4, line5, line6, line7, line8, line9)));
                        //optionsActVitalGraph.setSeries(new ArrayList<>(Arrays.asList(lineAct1, line2, line3, line4, line5, line6, line7, line8, line9, line10)));
                        plotOptions = new HIPlotOptions();
                        plotOptions.setLine(new HILine());
                        plotOptions.getLine().setDataLabels(new HIDataLabels());
                        plotOptions.getLine().getDataLabels().setEnabled(true);
                        plotOptions.getLine().setEnableMouseTracking(true);
                        optionsActVitalGraph.setPlotOptions(plotOptions);
                        hcActVitalGraph.setOptions(optionsActVitalGraph);
                        hcActVitalGraph.getOptions().setExporting(exporting);
                        hcActVitalGraph.reload();
                        hcActVitalGraph.setVisibility(View.VISIBLE);
                        hcActVitalGraph.invalidate();
                        view.invalidate();




                        optionsProbVitalGraph=new HIOptions();
                        optionsProbVitalGraph.setCredits(credits);
                        ArrayList<Float> probList=new ArrayList<>();
                        for (int j = 0; j < patientDetailGraphResp.getDetailList().size(); j++) {
                            if (patientDetailGraphResp.getDetailList().get(j).getProblem()==null) probList.add(null);
                            else probList.add(Float.parseFloat("0.0"));
                        }
                        HITitle titleProb = new HITitle();
                        titleProb.setText("Patient Problem Vital Graph");
                        optionsProbVitalGraph.setTitle(titleProb);
                        HIPlotOptions plotProbOptions = new HIPlotOptions();
                        plotProbOptions.setSeries(new HISeries());
                        plotProbOptions.getSeries().setLabel(new HILabel());
                        plotProbOptions.getSeries().getLabel().setConnectorAllowed(false);
                        final HIXAxis hiProbxAxis=new HIXAxis();
                        hiProbxAxis.setTitle(new HITitle());
                        hiProbxAxis.getTitle().setText("Time");
                        hiProbxAxis.setCategories(time);
                        optionsProbVitalGraph.setXAxis(new ArrayList<HIXAxis>(){{add(hiProbxAxis);}});
                        plotProbOptions.getSeries().setPointInterval(1.0);
                        optionsProbVitalGraph.setPlotOptions(plotProbOptions);
                        HITooltip tooltipProb = new HITooltip();
                        tooltipProb.setPointFormat("<span style=\"color:{series.color}\">{series.name}</span>: <b>({point.y:,.0f})</b><br/>");
                        tooltipProb.setSplit(false);
                        optionsProbVitalGraph.setTooltip(tooltipProb);
                        HISeries lineProb1 = new HISpline();
                        lineProb1.setName("Problem");
                        lineProb1.setData(probList);
                        //HIRules rulesMed1 = new HIRules();
                        rulesMed1.setCondition(new HICondition());
                        rulesMed1.getCondition().setMaxWidth(500);
                        //HashMap<String, HashMap> chartLegendMed = new HashMap<>();
                        //HashMap<String, String> legendOptionsMed = new HashMap<>();
                        legendOptionsMed.put("layout", "horizontal");
                        legendOptionsMed.put("align", "center");
                        legendOptionsMed.put("verticalAlign", "bottom");
                        chartLegendMed.put("legend", legendOptionsMed);
                        rulesMed1.setChartOptions(chartLegendMed);
                        responsive.setRules(new ArrayList<>(Collections.singletonList(rulesMed1)));
                        optionsProbVitalGraph.setResponsive(responsive);
                        optionsProbVitalGraph.setSeries(new ArrayList<>(Arrays.asList(lineProb1, line2, line3, line4, line5, line6, line7, line8, line9)));
                        //optionsProbVitalGraph.setSeries(new ArrayList<>(Arrays.asList(lineProb1, line2, line3, line4, line5, line6, line7, line8, line9, line10)));
                        plotOptions = new HIPlotOptions();
                        plotOptions.setLine(new HILine());
                        plotOptions.getLine().setDataLabels(new HIDataLabels());
                        plotOptions.getLine().getDataLabels().setEnabled(true);
                        plotOptions.getLine().setEnableMouseTracking(true);
                        optionsProbVitalGraph.setPlotOptions(plotOptions);
                        hcProbVitalGraph.setOptions(optionsProbVitalGraph);
                        hcProbVitalGraph.getOptions().setExporting(exporting);
                        hcProbVitalGraph.reload();
                        hcProbVitalGraph.setVisibility(View.VISIBLE);
                        hcProbVitalGraph.invalidate();
                        view.invalidate();



                        hcInOpGraph.plugins = new ArrayList<>();
                        hcInOpGraph.plugins.add("series-label");
                        optionsInOpGraph=new HIOptions();
                        optionsInOpGraph.setCredits(credits);
                        HIChart chart = new HIChart();
                        chart.setType("column");
                        optionsInOpGraph.setChart(chart);
                        ArrayList<Integer> inList=new ArrayList<>();
                        ArrayList<Integer> outList=new ArrayList<>();
                        for (int j = 0; j < patientDetailGraphResp.getDetailList().size(); j++) {
                            if (patientDetailGraphResp.getIntakeOutputList().get(j).getIntakeQty()==null) inList.add(null);
                            else inList.add(patientDetailGraphResp.getIntakeOutputList().get(j).getIntakeQty());
                            if (patientDetailGraphResp.getIntakeOutputList().get(j).getOutputQty()==null) outList.add(null);
                            else outList.add(patientDetailGraphResp.getIntakeOutputList().get(j).getOutputQty());
                        }
                        HITitle titleInOut = new HITitle();
                        titleInOut.setText("Patient Intake Output Graph");
                        optionsInOpGraph.setTitle(titleInOut);
                        final HIXAxis hiInxAxis=new HIXAxis();
                        hiInxAxis.setTitle(new HITitle());
                        hiInxAxis.getTitle().setText("Time");
                        hiInxAxis.setCrosshair(new HICrosshair());
                        hiInxAxis.setCategories(time);
                        optionsInOpGraph.setXAxis(new ArrayList<HIXAxis>(){{add(hiInxAxis);}});
                        HITooltip tooltipIn = new HITooltip();
                        tooltipIn.setHeaderFormat("<span style=\"font-size:10px\">{point.key}</span><table>");
                        tooltipIn.setPointFormat("<tr><td style=\"color:{series.color};padding:0\">{series.name}: </td><td style=\"padding:0\"><b>{point.y:.1f} mm</b></td></tr>");
                        tooltipIn.setFooterFormat("</table>");
                        tooltipIn.setShared(true);
                        tooltipIn.setUseHTML(true);
                        optionsInOpGraph.setTooltip(tooltipIn);
                        HISeries lineIn = new HIColumn();
                        HISeries lineOut = new HIColumn();
                        lineIn.setName("Intake");
                        lineOut.setName("Output");
                        lineIn.setData(inList);
                        lineOut.setData(outList);
                        HIRules rulesIn = new HIRules();
                        rulesIn.setCondition(new HICondition());
                        rulesIn.getCondition().setMaxWidth(500);
                        HashMap<String, HashMap> chartLegendIn = new HashMap<>();
                        HashMap<String, String> legendOptionsIn = new HashMap<>();
                        legendOptionsIn.put("layout", "horizontal");
                        legendOptionsIn.put("align", "center");
                        legendOptionsIn.put("verticalAlign", "bottom");
                        chartLegendIn.put("legend", legendOptionsIn);
                        rulesIn.setChartOptions(chartLegendIn);
                        responsive.setRules(new ArrayList<>(Collections.singletonList(rulesIn)));
                        optionsInOpGraph.setResponsive(responsive);
                        optionsInOpGraph.setSeries(new ArrayList<>(Arrays.asList(lineIn, lineOut)));
                        plotOptions = new HIPlotOptions();
                        plotOptions.setColumn(new HIColumn());
                        plotOptions.getColumn().setPointPadding(0.2);
                        plotOptions.getColumn().setBorderWidth(0);
                        optionsInOpGraph.setPlotOptions(plotOptions);
                        hcInOpGraph.setOptions(optionsInOpGraph);
                        hcInOpGraph.getOptions().setExporting(exporting);
                        hcInOpGraph.reload();
                        hcInOpGraph.setVisibility(View.VISIBLE);
                        hcInOpGraph.invalidate();
                        view.invalidate();
                        Utils.hideDialog();
                    }
                }
            }
            @Override
            public void onFailure(Call<PatientDetailGraphResp> call, Throwable t) {

            }
        });
    }
    private void bindFoodList(){
        Utils.showRequestDialog(context);
        final LinearLayout llSupp=view.findViewById(R.id.llSupp);
        StringBuilder nutrient=new StringBuilder();
        for (int i = 0; i <chpInput.getSelectedChipList().size() ; i++) {
            nutrient.append(chpInput.getSelectedChipList().get(i).getLabel()).append(",");
        }
        Call<PatientDetailFoodNutrientResp> call= RetrofitClient1.getInstance().getApi().getNutriAnalyserValuesByFoodTimeIdPID("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", SharedPrefManager.getInstance(context).getPid(), "0", mYear + "-" + (mMonth + 1) + "-" + mDay, "0", String.valueOf(nutrient), 0);
        call.enqueue(new Callback<PatientDetailFoodNutrientResp>() {
            @Override
            public void onResponse(Call<PatientDetailFoodNutrientResp> call, Response<PatientDetailFoodNutrientResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        if(response.body().getResponseCode()==1) {
                            NutrientFoodResponseValue nutrientFoodResponseValue=response.body().getNutrientFoodResponseValue();
                            rvFood.setAdapter(new FoodNutrientAdp(context, nutrientFoodResponseValue.getFoodList()));
                            if(nutrientFoodResponseValue.getSupplementList().size()>0){
                                rvSupplement.setAdapter(new SupplementNutrientAdp(context, nutrientFoodResponseValue.getSupplementList()));
                                llSupp.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<PatientDetailFoodNutrientResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    private void bindNutrientList(String text){
        Call<NutrientBindRes> call= RetrofitClient.getInstance().getApi().getAutoCompleteNutrition(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), text);
        call.enqueue(new Callback<NutrientBindRes>() {
            @Override
            public void onResponse(Call<NutrientBindRes> call, Response<NutrientBindRes> response) {
                if(response.isSuccessful()){
                    nutritionList.clear();
                    if (response.body() != null) {
                        nutritionList.addAll(0, response.body().getNutrition());
                    }
                }
            }

            @Override
            public void onFailure(Call<NutrientBindRes> call, Throwable t) {

            }
        });
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    private Chip getChip(final ChipGroup entryChipGroup, String text) {
        final Chip chip = new Chip(context);
        chip.setChipDrawable(ChipDrawable.createFromResource(context, R.xml.chip_style));
        int paddingDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10,
                getResources().getDisplayMetrics()
        );
        chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
        chip.setText(text);
        chip.setOnCloseIconClickListener(v -> entryChipGroup.removeView(chip));
        return chip;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }
    public class NutrientChipAdp extends RecyclerView.Adapter<NutrientChipAdp.RecyclerViewHolder> {
        List<Nutrition> nutritionList;

        public NutrientChipAdp(List<Nutrition> nutritionList) {
            this.nutritionList = nutritionList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.inner_chip_recycler, null));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtNutrient.setText(String.valueOf(nutritionList.get(i).getNutrientName()));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int i, @NonNull List<Object> payloads) {
            holder.txtNutrient.setText(String.valueOf(nutritionList.get(i).getNutrientName()));
            holder.txtNutrient.setOnClickListener(view -> {
                chpInput.addChip(nutritionList.get(i).getId(), (Uri) null, nutritionList.get(i).getNutrientName(), "");
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return nutritionList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtNutrient;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtNutrient =itemView.findViewById(R.id.txtNutrient);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.txtDate){
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        txtDate.setText(Utils.formatDate(date));
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if(view.getId()==R.id.btnShow){
            Utils.showRequestDialog(context);
            bindFoodList();
            bindFoodVitalGraph();
            llNutrientGraph.setVisibility(View.GONE);
            if(selectedMembersList.size()>0){
                /*LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen._300sdp));
                final HIChartView hcNutrientView=new HIChartView(context);
                hcNutrientView.setLayoutParams(layoutParams);*/
                //for(int i=0;i<selectedMembersList.size();i++) {
                    Call<PatientNutrientGraphResp> call = RetrofitClient.getInstance().getApi().getPatientIntakeNutritionGraph(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), mYear + "-" + (mMonth + 1) + "-" + mDay, selectedMembersList.get(selectedMembersList.size()-1));
                    call.enqueue(new Callback<PatientNutrientGraphResp>() {
                        @Override
                        public void onResponse(Call<PatientNutrientGraphResp> call, Response<PatientNutrientGraphResp> response) {
                            if(response.isSuccessful()){
                                Utils.showRequestDialog(context);
                                PatientNutrientGraphResp nutrientGraphResp=response.body();
                                ArrayList<Float> nutrientList=new ArrayList<>();
                                ArrayList<Float> rdaList=new ArrayList<>();
                                ArrayList<String> time=new ArrayList<>();
                                HIResponsive responsive = new HIResponsive();
                                hcNutrientView.plugins = new ArrayList<>();
                                hcNutrientView.plugins.add("series-label");
                                options = new HIOptions();
                                HITitle title = new HITitle();
                                title.setText("Patient Nutrient Intake Graph");
                                options.setTitle(title);
                                exporting = new HIExporting();
                                exporting.setEnabled(false);
                                HILegend legend = new HILegend();
                                options.setLegend(legend);
                                HISubtitle subtitle = new HISubtitle();
                                subtitle.setText("");
                                options.setSubtitle(subtitle);
                                HIYAxis yaxis = new HIYAxis();
                                yaxis.setTitle(new HITitle());
                                legend.setLayout("vertical");
                                legend.setAlign("right");
                                legend.setVerticalAlign("middle");
                                options.setLegend(legend);
                                credits = new HICredits();
                                credits.setEnabled(false);
                                options.setCredits(credits);
                                HITooltip tooltip = new HITooltip();
                                tooltip.setPointFormat("<span style=\"color:{series.color}\">{series.name}</span>: <b>({point.y:,.0f})</b><br/>");
                                tooltip.setSplit(true);
                                options.setTooltip(tooltip);
                                for(int j=0;j<nutrientGraphResp.getTable2().size();j++){
                                    if(nutrientGraphResp.getTable2().get(j).getNutrientValue()==0) nutrientList.add(null);
                                    else nutrientList.add(nutrientGraphResp.getTable2().get(j).getNutrientValue());
                                    if(nutrientGraphResp.getTable2().get(j).getIntake())
                                    time.add(nutrientGraphResp.getTable2().get(j).getValueTime()+"(Intake)");
                                    else time.add(nutrientGraphResp.getTable2().get(j).getValueTime());
                                }
                                if(nutrientGraphResp.getTable().get(0).getRda()==0) rdaList.add(null);
                                else rdaList.add(nutrientGraphResp.getTable().get(0).getRda());
                                HIPlotLines plotLines=new HIPlotLines();
                                plotLines.setWidth(2);
                                plotLines.setValue(rdaList.get(0));
                                plotLines.setColor(HIColor.initWithName("green"));
                                plotLines.setLabel(new HILabel());
                                plotLines.getLabel().setRotation(0);
                                plotLines.getLabel().setY(15);
                                plotLines.getLabel().setText("RDA - "+rdaList.get(0));
                                HIPlotLines[] plotLinesList1 = new HIPlotLines[] {plotLines };
                                yaxis.setPlotLines(new ArrayList<>(Arrays.asList(plotLinesList1)));
                                yaxis.getTitle().setText("Unit in "+ Objects.requireNonNull(nutrientGraphResp).getTable().get(0).getUnitName());
                                options.setYAxis(new ArrayList<>(Collections.singletonList(yaxis)));
                                HIPlotOptions plotOptions = new HIPlotOptions();
                                plotOptions.setSeries(new HISeries());
                                plotOptions.getSeries().setLabel(new HILabel());
                                plotOptions.getSeries().getLabel().setConnectorAllowed(false);
                                plotOptions.setArea(new HIArea());
                                //plotOptions.getArea().setStacking("percent");
                                plotOptions.getArea().setLineColor(HIColor.initWithHexValue("ffffff"));
                                plotOptions.getArea().setLineWidth(1);
                                plotOptions.getArea().setMarker(new HIMarker());
                                plotOptions.getArea().getMarker().setLineWidth(1);
                                plotOptions.getArea().getMarker().setLineColor(HIColor.initWithHexValue("ffffff"));
                                options.setPlotOptions(plotOptions);
                                final HIXAxis hixAxis=new HIXAxis();
                                hixAxis.setTitle(new HITitle());
                                hixAxis.getTitle().setText("Time");
                                hixAxis.setCategories(time);
                                hixAxis.setTickmarkPlacement("on");
                                options.setXAxis(new ArrayList<HIXAxis>(){{add(hixAxis);}});
                                plotOptions.getSeries().setPointInterval(1.0);
                                options.setPlotOptions(plotOptions);
                                HISeries line1 = new HIArea();
                                line1.setName(nutrientGraphResp.getTable().get(0).getNutrient());
                                line1.setData(nutrientList);
                                HISeries line2 = new HILine();
                                line2.setName("RDA");
                                line2.setData(rdaList);
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
                                options.setSeries(new ArrayList<>(Arrays.asList(line1, line2)));
                                plotOptions.setLine(new HILine());
                                plotOptions.getLine().setDataLabels(new HIDataLabels());
                                plotOptions.getLine().getDataLabels().setEnabled(false);
                                plotOptions.getLine().setEnableMouseTracking(true);
                                options.setPlotOptions(plotOptions);
                                hcNutrientView.setOptions(options);
                                hcNutrientView.getOptions().setExporting(exporting);
                                hcNutrientView.getOptions().setExporting(exporting);
                                hcNutrientView.reload();
                                llNutrientGraph.setVisibility(View.VISIBLE);
                                //llNutrientGraph.addView(hcNutrientView);
                                Utils.hideDialog();
                            }
                        }

                        @Override
                        public void onFailure(Call<PatientNutrientGraphResp> call, Throwable t) {

                        }
                    });
                //}
            }
            Utils.hideDialog();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
