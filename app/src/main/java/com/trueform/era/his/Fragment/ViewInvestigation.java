package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.common.hichartsclasses.HICSSObject;
import com.highsoft.highcharts.common.hichartsclasses.HICondition;
import com.highsoft.highcharts.common.hichartsclasses.HICredits;
import com.highsoft.highcharts.common.hichartsclasses.HIDataLabels;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HILabel;
import com.highsoft.highcharts.common.hichartsclasses.HILegend;
import com.highsoft.highcharts.common.hichartsclasses.HILine;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotBands;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIResponsive;
import com.highsoft.highcharts.common.hichartsclasses.HIRules;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HISubtitle;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;
import com.trueform.era.his.Adapter.RecyclerTouchListener;
import com.trueform.era.his.Adapter.ViewInvestigationAdp;
import com.trueform.era.his.Model.PatientTest;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.BillDetailsResp;
import com.trueform.era.his.Response.InvestigationChartResp;
import com.trueform.era.his.Response.PatientBillResp;
import com.trueform.era.his.Utils.ClickListener;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewInvestigation extends Fragment implements View.OnClickListener {
    private Spinner spnBill;
    @SuppressLint("StaticFieldLeak")
    static Context context;
    private RecyclerView rView;
    private ProgressDialog dialog;
    private PatientBillResp patientBillResp;
    private ArrayAdapter<String> dataAdapter;
    private List<PatientTest> patientTest;
    List<String> type;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView txtDate;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ViewInvestigation() {
        // Required empty public constructor
    }

    public static ViewInvestigation newInstance(String param1, String param2) {
        ViewInvestigation fragment = new ViewInvestigation();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_investigation, container, false);
        context = view.getContext();
        rView=view.findViewById(R.id.rView);
        TextView btnAddInvestigation = view.findViewById(R.id.btnAddInvestigation);
        dialog=new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        txtDate = view.findViewById(R.id.txtDate);
        spnBill = view.findViewById(R.id.spnBill);
        btnAddInvestigation.setOnClickListener(this);
        rView.setLayoutManager(new LinearLayoutManager(context));
        dialog.show();
        patientTest=new ArrayList<>();
        type = new ArrayList<String>();
        Call<PatientBillResp> call = RetrofitClient.getInstance().getApi().getPatientBill(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<PatientBillResp>() {
            @Override
            public void onResponse(Call<PatientBillResp> call, Response<PatientBillResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        patientBillResp=response.body();
                        for (int i = 0; i < response.body().getGetPatientBills().size(); i++) {
                            type.add(response.body().getGetPatientBills().get(i).getFullName()+" ("+response.body().getGetPatientBills().get(i).getBillDate()+")");
                        }
                        dataAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, type);
                        spnBill.setAdapter(dataAdapter);
                        spnBill.setSelection(0);
                    }
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<PatientBillResp> call, Throwable t) {
                dialog.dismiss();

            }
        });
        spnBill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                patientTest.clear();
                getData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rView.addOnItemTouchListener(new RecyclerTouchListener(context, rView, new ClickListener() {
            @Override
            public void onClick(View view1, int position) {
                //showChart(view1, billDetailsResp.getPathology().get(position).getSubTestID());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }
    public void showChart(final View anchorView, int id, int billNo) {
        Utils.showRequestDialog(context);
        Call<InvestigationChartResp> call = RetrofitClient.getInstance().getApi().getGraphByTestId(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), billNo, id, "Sub");
        call.enqueue(new Callback<InvestigationChartResp>() {
            @Override
            public void onResponse(Call<InvestigationChartResp> call, Response<InvestigationChartResp> response) {
                if(response.isSuccessful()) {
                    InvestigationChartResp investigationChartResp = response.body();
                    if (investigationChartResp != null && investigationChartResp.getGraphResult().size() > 0) {
                        View popupView = LayoutInflater.from(context).inflate(R.layout.popup_chart, null);
                        PopupWindow popupWindow = new PopupWindow(popupView,
                                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new ColorDrawable());
                        int[] location = new int[2];
                        anchorView.getLocationOnScreen(location);
                        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
                        HIChartView chartView = popupView.findViewById(R.id.lineChart);
                        HIOptions options = new HIOptions();
                        HITitle title = new HITitle();
                        title.setText("Datewise " + investigationChartResp.getGraphResult().get(0).getSubTestName() + " Test Report");
                        options.setTitle(title);
                        HISubtitle subtitle = new HISubtitle();
                        subtitle.setText("");
                        options.setSubtitle(subtitle);
                        HICredits credits = new HICredits();
                        credits.setEnabled(false);
                        options.setCredits(credits);
                        HIExporting exporting = new HIExporting();
                        exporting.setEnabled(false);
                        options.setExporting(exporting);
                        HIYAxis yaxis = new HIYAxis();
                        yaxis.setTitle(new HITitle());
                        yaxis.getTitle().setText("Value " + "(" + investigationChartResp.getGraphResult().get(0).getUnit() + ")");
                        HILegend legend = new HILegend();
                        legend.setLayout("vertical");
                        legend.setAlign("right");
                        legend.setVerticalAlign("middle");
                        options.setLegend(legend);
                        final HIXAxis xAxis = new HIXAxis();
                        HISeries line1 = new HILine();
                        line1.setName(investigationChartResp.getGraphResult().get(0).getSubTestName());
                        ArrayList<Float> valueList = new ArrayList<>();
                        ArrayList<String> categoriesList = new ArrayList<>();
                        try {
                            for (int i = 0; i < investigationChartResp.graphResult.size(); i++) {
                                valueList.add(Float.valueOf(investigationChartResp.getGraphResult().get(i).getSubTestValue()));
                                categoriesList.add(investigationChartResp.getGraphResult().get(i).getBillDate());
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        line1.setData(valueList);
                        HIResponsive responsive = new HIResponsive();
                        xAxis.setCategories(categoriesList);
                        options.setXAxis(new ArrayList<HIXAxis>() {{
                            add(xAxis);
                        }});
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
                        options.setSeries(new ArrayList<>(Arrays.asList(line1)));
                        HIPlotBands plotBands = new HIPlotBands();
                        plotBands.setFrom(investigationChartResp.getGraphResult().get(0).getMin());
                        plotBands.setTo(investigationChartResp.getGraphResult().get(0).getMax());
                        plotBands.setColor(HIColor.initWithRGBA(206, 247, 181, 0.8));
                        plotBands.setLabel(new HILabel());
                        plotBands.getLabel().setStyle(new HICSSObject());
                        plotBands.getLabel().getStyle().setColor("606060");
                        HIPlotBands[] plotBandsList = new HIPlotBands[]{plotBands};
                        yaxis.setPlotBands(new ArrayList<>(Arrays.asList(plotBandsList)));
                        options.setYAxis(new ArrayList<>(Collections.singletonList(yaxis)));
                        HIPlotOptions plotOptions = new HIPlotOptions();
                        plotOptions.setLine(new HILine());
                        plotOptions.getLine().setDataLabels(new HIDataLabels());
                        plotOptions.getLine().getDataLabels().setEnabled(true);
                        plotOptions.getLine().setEnableMouseTracking(true);
                        options.setPlotOptions(plotOptions);
                        chartView.setOptions(options);
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<InvestigationChartResp> call, Throwable throwable) {
                Utils.hideDialog();
            }
        });
    }
    private void getData(Integer index) {
        //String gender="";
        dialog.show();
        /*if(SharedPrefManager.getInstance(context).getHeadID()==2)
            gender=SharedPrefManager.getInstance(context).getAdmitPatient().getGender();
        else if(SharedPrefManager.getInstance(context).getHeadID()==3 || SharedPrefManager.getInstance(context).getHeadID()==4)
            gender=SharedPrefManager.getInstance(context).getIcuAdmitPatient().getGender();*/
        txtDate.setText(patientBillResp.getGetPatientBills().get(index).getBillDate());
        if (patientBillResp.getGetPatientBills().get(index).getCategoryID().contains(",")) {
            String[] catId = patientBillResp.getGetPatientBills().get(index).getCategoryID().split(",");
            for (String s : catId) {
                Call<BillDetailsResp> call = RetrofitClient.getInstance().getApi().viewBillDetails(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), patientBillResp.getGetPatientBills().get(index).getBillMasterID().toString(), SharedPrefManager.getInstance(context).getPid(), s, SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
                call.enqueue(new Callback<BillDetailsResp>() {
                    @Override
                    public void onResponse(Call<BillDetailsResp> call, Response<BillDetailsResp> response) {
                        if (response.body() != null) {
                            if (response.isSuccessful()) {
                                patientTest.addAll(response.body().getPatientTest());
                                Log.v("investigation", String.valueOf(patientTest.get(0)));
                                if (patientTest != null && patientTest.size() > 0) {
                                    rView.setAdapter(new ViewInvestigationAdp(context, patientTest));
                                } else rView.setAdapter(null);
                            }
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<BillDetailsResp> call, Throwable t) {
                        dialog.dismiss();
                    }
                });
            }
        } else {
            Call<BillDetailsResp> call = RetrofitClient.getInstance().getApi().viewBillDetails(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), patientBillResp.getGetPatientBills().get(index).getBillMasterID().toString(), SharedPrefManager.getInstance(context).getPid(), patientBillResp.getGetPatientBills().get(index).getCategoryID(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
            call.enqueue(new Callback<BillDetailsResp>() {
                @Override
                public void onResponse(Call<BillDetailsResp> call, Response<BillDetailsResp> response) {
                    if (response.body() != null) {
                        if (response.isSuccessful()) {
                            patientTest = response.body().getPatientTest();
                            if (patientTest != null && patientTest.size() > 0) {
                                rView.setAdapter(new ViewInvestigationAdp(context, patientTest));
                            } else rView.setAdapter(null);
                        }
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<BillDetailsResp> call, Throwable t) {
                    dialog.dismiss();
                }
            });
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        AddInvestigation addInvestigation=new AddInvestigation();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, addInvestigation);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}