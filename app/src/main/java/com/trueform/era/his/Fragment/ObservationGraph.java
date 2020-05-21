package com.trueform.era.his.Fragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.common.hichartsclasses.HIBackground;
import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HIColorAxis;
import com.highsoft.highcharts.common.hichartsclasses.HICondition;
import com.highsoft.highcharts.common.hichartsclasses.HICredits;
import com.highsoft.highcharts.common.hichartsclasses.HIDataLabels;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HILabel;
import com.highsoft.highcharts.common.hichartsclasses.HILabels;
import com.highsoft.highcharts.common.hichartsclasses.HILegend;
import com.highsoft.highcharts.common.hichartsclasses.HILine;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPane;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIResponsive;
import com.highsoft.highcharts.common.hichartsclasses.HIRules;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HISolidgauge;
import com.highsoft.highcharts.common.hichartsclasses.HISubtitle;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;
import com.trueform.era.his.Activity.PriscriptionOverviewPopup;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.ObservationGraphResp;
import com.trueform.era.his.Response.VitalListResp;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;
import com.trueform.era.his.database.DatabaseController;
import com.trueform.era.his.database.TablePatientVitalGraph;
import com.trueform.era.his.database.TablePatientVitalList;

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

public class ObservationGraph extends Fragment implements View.OnClickListener {
    HIChartView hcView;
    HIChartView hcSys;
    HIChartView hcDys;
    TextView btnPresOverview, txtDate, btnCall;
    HIExporting exporting;
    LinearLayout llBP;
    String sp;
    Spinner spnType;
    Calendar c;
    VitalListResp vitalChartList;
    int mYear = 0, mMonth = 0, mDay = 0;
    HICredits credits;
    Context context;
    View view;
    static String date = "";
    HIOptions options;
    private List<TypeSelector> typeSelectorList;
    private TextView txtSpo2, txtTemp, txtPulse, txtRR, txtHR, txtWeight, txtHeight;
    ObservationGraphResp observationGraphResp;
    ProgressBar mProgress;
    int pStatus = 0;
    private Handler handler = new Handler();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ObservationGraph() {
        // Required empty public constructor
    }

    public static ObservationGraph newInstance(String param1, String param2) {
        ObservationGraph fragment = new ObservationGraph();
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
        view = inflater.inflate(R.layout.fragment_observation_graph, container, false);
        hcView = view.findViewById(R.id.hcView);
        hcSys = view.findViewById(R.id.hcSys);
        hcDys = view.findViewById(R.id.hcDys);
        txtPulse = view.findViewById(R.id.txtPulse);
        spnType = view.findViewById(R.id.spnType);
        btnCall = view.findViewById(R.id.btnCall);
        llBP = view.findViewById(R.id.llBP);
        txtRR = view.findViewById(R.id.txtRR);
        txtHR = view.findViewById(R.id.txtHR);
        txtWeight = view.findViewById(R.id.txtWeight);
        txtHeight = view.findViewById(R.id.txtHeight);
        txtDate = view.findViewById(R.id.txtDate);
        context = view.getContext();
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
        txtDate.setText(Utils.formatDate(date));
        txtSpo2 = view.findViewById(R.id.txtSpo2);
        txtTemp = view.findViewById(R.id.txtTemp);
        mProgress = view.findViewById(R.id.progressBar);
        Utils.showRequestDialog(context);
        btnPresOverview = view.findViewById(R.id.btnPresOverview);
        btnPresOverview.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        typeSelectorList = new ArrayList<>();
        typeSelectorList.add(0, new TypeSelector(0, "All"));
        typeSelectorList.add(1, new TypeSelector(1, "Manual Data"));
        typeSelectorList.add(2, new TypeSelector(2, "Machine Data"));
        ArrayAdapter<TypeSelector> adapter = new ArrayAdapter<>(context, R.layout.spinner_layout, typeSelectorList);
        spnType.setAdapter(adapter);
        try {
            options = new HIOptions();
            HITitle title = new HITitle();
            title.setText("");
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
            yaxis.getTitle().setText("Data");
            options.setYAxis(new ArrayList<>(Collections.singletonList(yaxis)));
            legend.setLayout("vertical");
            legend.setAlign("right");
            legend.setVerticalAlign("middle");
            options.setLegend(legend);
            credits = new HICredits();
            credits.setEnabled(false);
            options.setCredits(credits);
            hcView.setOptions(options);
            hcView.getOptions().setExporting(exporting);
            hcSys.setOptions(options);
            hcDys.setOptions(options);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getVitals();
                showGraph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Utils.hideDialog();
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void showGraph() {
        if (ConnectivityChecker.checker(context)) {

            Call<ObservationGraphResp> call = RetrofitClient.getInstance().getApi().getVitalGraph(
                    SharedPrefManager.getInstance(context).getUser().getAccessToken(),
                    SharedPrefManager.getInstance(context).getUser().getUserid().toString(),
                    SharedPrefManager.getInstance(context).getPid(),
                    date + " 12:00 AM",
                    SharedPrefManager.getInstance(context).getHeadID(),
                    24,
                    typeSelectorList.get(spnType.getSelectedItemPosition()).getId()
            );
            call.enqueue(new Callback<ObservationGraphResp>() {
                @Override
                public void onResponse(Call<ObservationGraphResp> call, Response<ObservationGraphResp> response) {
                    Utils.showRequestDialog(context);
                    if (response.isSuccessful()) {
                        if (response.body() != null) {

                            try {

                                DatabaseController.myDataBase.beginTransaction();

/*

                                DatabaseController.deleteRow(TablePatientVitalGraph.patient_vital_graph,
                                        TablePatientVitalGraph.patientVitalGraphColumn.pId_headId.toString(),
                                        SharedPrefManager.getInstance(context).getPid() + "_" + SharedPrefManager.getInstance(context).getHeadID());
*/


                                DatabaseController.delete(TablePatientVitalGraph.patient_vital_graph,
                                        TablePatientVitalGraph.patientVitalGraphColumn.pId_headId.toString() + "='" + SharedPrefManager.getInstance(context).getPid() + "_" + SharedPrefManager.getInstance(context).getHeadID() + "'"
                                                + " and " + TablePatientVitalGraph.patientVitalGraphColumn.createdDate + "='" + date + "'",
                                        null);

                                observationGraphResp = response.body();

                                if (observationGraphResp.getPatientVitalGraph().size() > 0) {
                                    HIResponsive responsive = new HIResponsive();
                                    ArrayList<String> category = new ArrayList<>();
                                    ArrayList<Float> list1 = new ArrayList<>(), list2 = new ArrayList<>(), list3 = new ArrayList<>(), list4 = new ArrayList<>(), list5 = new ArrayList<>(), list6 = new ArrayList<>();
                                    for (int i = 0; i < observationGraphResp.getPatientVitalGraph().size(); i++) {

                                        ContentValues contentValues = new ContentValues();


                                        contentValues.put(TablePatientVitalGraph.patientVitalGraphColumn.pId_headId.toString(),
                                                SharedPrefManager.getInstance(getActivity()).getPid() + "_" + SharedPrefManager.getInstance(getActivity()).getHeadID());

                                        if (observationGraphResp.getPatientVitalGraph().get(i).getHeartRate().equalsIgnoreCase("0"))
                                            list1.add(null);
                                        else {

                                            try {

                                                contentValues.put(TablePatientVitalGraph.patientVitalGraphColumn.heartRate.toString(),
                                                        observationGraphResp.getPatientVitalGraph().get(i).getHeartRate());

                                                list1.add(Float.parseFloat(observationGraphResp.getPatientVitalGraph().get(i).getHeartRate()));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        if (observationGraphResp.getPatientVitalGraph().get(i).getRespRate().equalsIgnoreCase("0"))
                                            list2.add(null);
                                        else {
                                            try {

                                                contentValues.put(TablePatientVitalGraph.patientVitalGraphColumn.respRate.toString(),
                                                        observationGraphResp.getPatientVitalGraph().get(i).getRespRate());
                                                list2.add(Float.parseFloat(observationGraphResp.getPatientVitalGraph().get(i).getRespRate()));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        if (observationGraphResp.getPatientVitalGraph().get(i).getSpo2().equalsIgnoreCase("0"))
                                            list3.add(null);
                                        else {
                                            try {

                                                contentValues.put(TablePatientVitalGraph.patientVitalGraphColumn.spo2.toString(),
                                                        observationGraphResp.getPatientVitalGraph().get(i).getSpo2());
                                                list3.add(Float.parseFloat(observationGraphResp.getPatientVitalGraph().get(i).getSpo2()));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        if (observationGraphResp.getPatientVitalGraph().get(i).getPulse().equalsIgnoreCase("0"))
                                            list4.add(null);
                                        else {
                                            try {
                                                contentValues.put(TablePatientVitalGraph.patientVitalGraphColumn.pulse.toString(),
                                                        observationGraphResp.getPatientVitalGraph().get(i).getPulse());
                                                list4.add(Float.parseFloat(observationGraphResp.getPatientVitalGraph().get(i).getPulse()));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        if (observationGraphResp.getPatientVitalGraph().get(i).getbP_Sys().equalsIgnoreCase("0"))
                                            list5.add(null);
                                        else {
                                            try {

                                                contentValues.put(TablePatientVitalGraph.patientVitalGraphColumn.bp_sys.toString(),
                                                        observationGraphResp.getPatientVitalGraph().get(i).getbP_Sys());
                                                list5.add(Float.parseFloat(observationGraphResp.getPatientVitalGraph().get(i).getbP_Sys()));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        if (observationGraphResp.getPatientVitalGraph().get(i).getbP_Dias().equalsIgnoreCase("0"))
                                            list6.add(null);
                                        else {

                                            try {

                                                contentValues.put(TablePatientVitalGraph.patientVitalGraphColumn.bp_dias.toString(),
                                                        observationGraphResp.getPatientVitalGraph().get(i).getbP_Dias());
                                                list6.add(Float.parseFloat(observationGraphResp.getPatientVitalGraph().get(i).getbP_Dias()));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        category.add(observationGraphResp.getPatientVitalGraph().get(i).getHr().toString() + ":00");

                                        contentValues.put(TablePatientVitalGraph.patientVitalGraphColumn.hr.toString(),
                                                observationGraphResp.getPatientVitalGraph().get(i).getHr().toString());

                                        contentValues.put(TablePatientVitalGraph.patientVitalGraphColumn.x.toString(),
                                                observationGraphResp.getPatientVitalGraph().get(i).getX().toString());

                                        contentValues.put(TablePatientVitalGraph.patientVitalGraphColumn.createdDate.toString(),
                                                observationGraphResp.getPatientVitalGraph().get(i).getCreatedDate());

                                        contentValues.put(TablePatientVitalGraph.patientVitalGraphColumn.createdDate.toString(),
                                                date);

                                        DatabaseController.insertData(contentValues, TablePatientVitalGraph.patient_vital_graph);

                                    }


                                    HIPlotOptions plotOptions = new HIPlotOptions();
                                    plotOptions.setSeries(new HISeries());
                                    plotOptions.getSeries().setLabel(new HILabel());
                                    plotOptions.getSeries().getLabel().setConnectorAllowed(false);
                                    final HIXAxis hixAxis = new HIXAxis();
                                    hixAxis.setTitle(new HITitle());
                                    hixAxis.getTitle().setText("Time");
                                    hixAxis.setCategories(category);
                                    options.setXAxis(new ArrayList<HIXAxis>() {{
                                        add(hixAxis);
                                    }});
                                    plotOptions.getSeries().setPointInterval(1.0);
                                    options.setPlotOptions(plotOptions);
                                    HISeries line1 = new HILine();
                                    HISeries line2 = new HILine();
                                    HISeries line3 = new HILine();
                                    HISeries line4 = new HILine();
                                    HISeries line5 = new HILine();
                                    HISeries line6 = new HILine();
                                    line1.setName("HR");
                                    line2.setName("RR");
                                    line3.setName("SpO2");
                                    line4.setName("Pulse");
                                    line5.setName("BP Systolic");
                                    line6.setName("BP Diastolic");
                                    line1.setData(list1);
                                    line2.setData(list2);
                                    line3.setData(list3);
                                    line4.setData(list4);
                                    line5.setData(list5);
                                    line6.setData(list6);
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
                                    options.setSeries(new ArrayList<>(Arrays.asList(line5, line1, line2, line6, line4, line3)));
                                    plotOptions.setLine(new HILine());
                                    plotOptions.getLine().setDataLabels(new HIDataLabels());
                                    plotOptions.getLine().getDataLabels().setEnabled(true);
                                    plotOptions.getLine().setEnableMouseTracking(true);
                                    options.setPlotOptions(plotOptions);
                                    hcView.setOptions(options);
                                    hcView.reload();
                                    hcView.setVisibility(View.VISIBLE);
                                    hcView.invalidate();
                                    view.invalidate();
                                } else {
                                    hcView.setVisibility(View.GONE);
                                }

                                DatabaseController.myDataBase.setTransactionSuccessful();
                            } finally {
                                DatabaseController.myDataBase.endTransaction();

                                Utils.hideDialog();


                            }
                        }
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<ObservationGraphResp> call, Throwable t) {
                    hcView.setVisibility(View.GONE);
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Utils.hideDialog();
                }
            });
        } else {
            observationGraphResp = new ObservationGraphResp();

            observationGraphResp.setPatientVitalGraph(DatabaseController.getPatientVitalGraph(
                    SharedPrefManager.getInstance(context).getPid() + "_" + SharedPrefManager.getInstance(context).getHeadID()
                    , date));

            try {

                setGraph();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void setGraph() throws Exception {

        if (observationGraphResp.getPatientVitalGraph().size() > 0) {
            HIResponsive responsive = new HIResponsive();
            ArrayList<String> category = new ArrayList<>();
            ArrayList<Float> list1 = new ArrayList<>(), list2 = new ArrayList<>(), list3 = new ArrayList<>(), list4 = new ArrayList<>(), list5 = new ArrayList<>(), list6 = new ArrayList<>();
            for (int i = 0; i < observationGraphResp.getPatientVitalGraph().size(); i++) {

                if (observationGraphResp.getPatientVitalGraph().get(i).getHeartRate() != null) {
                    if (observationGraphResp.getPatientVitalGraph().get(i).getHeartRate().equalsIgnoreCase("0"))
                        list1.add(null);
                    else {
                        list1.add(Float.parseFloat(observationGraphResp.getPatientVitalGraph().get(i).getHeartRate()));
                    }
                }

                if (observationGraphResp.getPatientVitalGraph().get(i).getRespRate() != null) {
                    if (observationGraphResp.getPatientVitalGraph().get(i).getRespRate().equalsIgnoreCase("0"))
                        list2.add(null);
                    else {

                        list2.add(Float.parseFloat(observationGraphResp.getPatientVitalGraph().get(i).getRespRate()));
                    }
                }

                if (observationGraphResp.getPatientVitalGraph().get(i).getSpo2() != null) {
                    if (observationGraphResp.getPatientVitalGraph().get(i).getSpo2().equalsIgnoreCase("0"))
                        list3.add(null);
                    else {

                        list3.add(Float.parseFloat(observationGraphResp.getPatientVitalGraph().get(i).getSpo2()));
                    }
                }

                if (observationGraphResp.getPatientVitalGraph().get(i).getPulse() != null) {

                    if (observationGraphResp.getPatientVitalGraph().get(i).getPulse() != null) {
                        if (observationGraphResp.getPatientVitalGraph().get(i).getPulse().equalsIgnoreCase("0"))
                            list4.add(null);
                        else {
                            list4.add(Float.parseFloat(observationGraphResp.getPatientVitalGraph().get(i).getPulse()));
                        }
                    }
                }

                if (observationGraphResp.getPatientVitalGraph().get(i).getbP_Sys() != null) {

                    if (observationGraphResp.getPatientVitalGraph().get(i).getbP_Sys().equalsIgnoreCase("0"))
                        list5.add(null);
                    else {
                        list5.add(Float.parseFloat(observationGraphResp.getPatientVitalGraph().get(i).getbP_Sys()));
                    }
                }

                if (observationGraphResp.getPatientVitalGraph().get(i).getbP_Dias() != null) {

                    if (observationGraphResp.getPatientVitalGraph().get(i).getbP_Dias().equalsIgnoreCase("0"))
                        list6.add(null);
                    else {

                        list6.add(Float.parseFloat(observationGraphResp.getPatientVitalGraph().get(i).getbP_Dias()));
                    }
                }

                if (observationGraphResp.getPatientVitalGraph().get(i).getHr() != null) {

                    category.add(observationGraphResp.getPatientVitalGraph().get(i).getHr().toString() + ":00");
                }

            }

            HIPlotOptions plotOptions = new HIPlotOptions();
            plotOptions.setSeries(new HISeries());
            plotOptions.getSeries().setLabel(new HILabel());
            plotOptions.getSeries().getLabel().setConnectorAllowed(false);
            final HIXAxis hixAxis = new HIXAxis();
            hixAxis.setTitle(new HITitle());
            hixAxis.getTitle().setText("Time");
            hixAxis.setCategories(category);
            options.setXAxis(new ArrayList<HIXAxis>() {{
                add(hixAxis);
            }});
            plotOptions.getSeries().setPointInterval(1.0);
            options.setPlotOptions(plotOptions);
            HISeries line1 = new HILine();
            HISeries line2 = new HILine();
            HISeries line3 = new HILine();
            HISeries line4 = new HILine();
            HISeries line5 = new HILine();
            HISeries line6 = new HILine();
            line1.setName("HR");
            line2.setName("RR");
            line3.setName("SpO2");
            line4.setName("Pulse");
            line5.setName("BP Systolic");
            line6.setName("BP Diastolic");
            line1.setData(list1);
            line2.setData(list2);
            line3.setData(list3);
            line4.setData(list4);
            line5.setData(list5);
            line6.setData(list6);
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
            options.setSeries(new ArrayList<>(Arrays.asList(line5, line1, line2, line6, line4, line3)));
            plotOptions.setLine(new HILine());
            plotOptions.getLine().setDataLabels(new HIDataLabels());
            plotOptions.getLine().getDataLabels().setEnabled(true);
            plotOptions.getLine().setEnableMouseTracking(true);
            options.setPlotOptions(plotOptions);
            hcView.setOptions(options);
            hcView.reload();
            hcView.setVisibility(View.VISIBLE);
            hcView.invalidate();
            view.invalidate();
        } else hcView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnPresOverview)
            startActivity(new Intent(view.getContext(), PriscriptionOverviewPopup.class));
        else if (view.getId() == R.id.txtDate) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        txtDate.setText(Utils.formatDate(date));
                        showGraph();
                        //getVitals();
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if(view.getId()==R.id.btnCall){
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
            alertDialog.setMessage("Do you want to Call?");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setPositiveButton("Yes", (dialog, which) -> startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",SharedPrefManager.getInstance(view.getContext()).getCovidPatient().getMobileNo(), null))));
            alertDialog.setNeutralButton("No", (dialog, which) -> dialog.cancel());
            alertDialog.show();
        }
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

    private void getVitals() {
        if (ConnectivityChecker.checker(context)) {
            Call<VitalListResp> call = RetrofitClient.getInstance().getApi().getPatientVitalList(
                    SharedPrefManager.getInstance(context).getUser().getAccessToken(),
                    SharedPrefManager.getInstance(context).getUser().getUserid().toString(),
                    SharedPrefManager.getInstance(context).getPid(),
                    SharedPrefManager.getInstance(context).getHeadID(),
                    SharedPrefManager.getInstance(context).getSubDept().getId(),
                    SharedPrefManager.getInstance(context).getUser().getUserid(),
                    typeSelectorList.get(spnType.getSelectedItemPosition()).getId());

            call.enqueue(new Callback<VitalListResp>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<VitalListResp> call, Response<VitalListResp> response) {
                    Utils.showRequestDialog(context);
                    if (response.body() != null) {
                        if (response.isSuccessful()) {
                            String sys, dys;

                            vitalChartList = response.body();

                            DatabaseController.deleteRow(TablePatientVitalList.patient_vital_list,
                                    TablePatientVitalList.patientVitalListColumn.pId_headId.toString(),
                                    SharedPrefManager.getInstance(context).getPid() + "_" + SharedPrefManager.getInstance(context).getHeadID());

                            for (int i = 0; i < vitalChartList.getVitalList().size(); i++) {

                                ContentValues contentValues = new ContentValues();

                                contentValues.put(TablePatientVitalList.patientVitalListColumn.pId_headId.toString(),
                                        SharedPrefManager.getInstance(context).getPid() + "_" + SharedPrefManager.getInstance(context).getHeadID());
                                contentValues.put(TablePatientVitalList.patientVitalListColumn.vitalName.toString(), vitalChartList.getVitalList().get(i).getVitalName());
                                contentValues.put(TablePatientVitalList.patientVitalListColumn.vitalValue.toString(), vitalChartList.getVitalList().get(i).getVitalValue());
                                contentValues.put(TablePatientVitalList.patientVitalListColumn.vitalUnit.toString(), vitalChartList.getVitalList().get(i).getVitalUnit());
                                contentValues.put(TablePatientVitalList.patientVitalListColumn.vmID.toString(), vitalChartList.getVitalList().get(i).getVmID());

                                DatabaseController.insertData(contentValues, TablePatientVitalList.patient_vital_list);

                                if (vitalChartList.getVitalList().get(i).getVmID() == 1)
                                    txtHeight.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                                if (vitalChartList.getVitalList().get(i).getVmID() == 2)
                                    txtWeight.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                                if (vitalChartList.getVitalList().get(i).getVmID() == 4) {
                                    sys = vitalChartList.getVitalList().get(i).getVitalValue();
                                    if (!sys.equalsIgnoreCase("-")) {
                                        HIOptions options1 = new HIOptions();
                                        HIChart chart = new HIChart();
                                        chart.setType("solidgauge");
                                        options1.setChart(chart);
                                        HITitle title1 = new HITitle();
                                        title1.setText("");
                                        options1.setTitle(title1);
                                        HIPane pane = new HIPane();
                                        String[] center = new String[]{"50%", "85%"};
                                        pane.setCenter(new ArrayList<>(Arrays.asList(center)));
                                        pane.setSize("120%");
                                        pane.setStartAngle(-90);
                                        pane.setEndAngle(90);
                                        HIBackground background = new HIBackground();
                                        background.setBackgroundColor(HIColor.initWithHexValue("EEE"));
                                        background.setInnerRadius("60%");
                                        background.setOuterRadius("100%");
                                        background.setShape("arc");
                                        pane.setBackground(new ArrayList<>(Arrays.asList(background)));
                                        options1.setPane(pane);
                                        HITooltip tooltip = new HITooltip();
                                        tooltip.setEnabled(false);
                                        options1.setTooltip(tooltip);
                                        HIPlotOptions plotoptions1 = new HIPlotOptions();
                                        plotoptions1.setSolidgauge(new HISolidgauge());
                                        plotoptions1.getSolidgauge().setDataLabels(new HIDataLabels());
                                        plotoptions1.getSolidgauge().getDataLabels().setY(5);
                                        plotoptions1.getSolidgauge().getDataLabels().setBorderWidth(0);
                                        plotoptions1.getSolidgauge().getDataLabels().setUseHTML(true);
                                        options1.setPlotOptions(plotoptions1);
                                        HIYAxis yaxis1 = new HIYAxis();
                                        yaxis1.setLineWidth(0);
                                        yaxis1.setTickAmount(2);
                                        yaxis1.setTitle(new HITitle());
                                        yaxis1.getTitle().setText("");
                                        yaxis1.setLabels(new HILabels());
                                        yaxis1.getTitle().setY(16);
                                        yaxis1.setMin(0);
                                        yaxis1.setMax(200);
                                        options1.setYAxis(new ArrayList<>(Arrays.asList(yaxis1)));
                                        options1.setCredits(credits);
                                        HISeries series1 = new HISolidgauge();
                                        series1.setName("");
                                        series1.setTooltip(new HITooltip());
                                        series1.getTooltip().setValueSuffix("SBP");
                                        series1.setDataLabels(new HIDataLabels());
                                        series1.getDataLabels().setFormat("<div style=\"text-align:center\"><span style=\"font-size:25px;color:black\">{y}</span><br/><span style=\"font-size:12px;color:silver\">SBP</span></div>");
                                        Number[] series1_data = new Number[]{Integer.parseInt(sys)};
                                        series1.setData(new ArrayList<>(Arrays.asList(series1_data)));
                                        options1.setSeries(new ArrayList<>(Arrays.asList(series1)));
                                        HIColorAxis axis = new HIColorAxis();
                                        axis.setLineColor(HIColor.initWithRGB(57, 181, 74));
                                        options1.setColorAxis(axis);
                                        hcSys.setOptions(options1);
                                        hcSys.getOptions().setExporting(exporting);
                                        llBP.setVisibility(View.VISIBLE);
                                    }
                                }
                                if (vitalChartList.getVitalList().get(i).getVmID() == 6) {
                                    dys = vitalChartList.getVitalList().get(i).getVitalValue();
                                    if (!dys.equalsIgnoreCase("-")) {
                                        HIOptions options2 = new HIOptions();
                                        HIChart chart2 = new HIChart();
                                        chart2.setType("solidgauge");
                                        options2.setChart(chart2);
                                        HITitle title2 = new HITitle();
                                        title2.setText("");
                                        options2.setTitle(title2);
                                        HIPane pane2 = new HIPane();
                                        String[] center2 = new String[]{"50%", "85%"};
                                        pane2.setCenter(new ArrayList<>(Arrays.asList(center2)));
                                        pane2.setSize("120%");
                                        pane2.setStartAngle(-90);
                                        pane2.setEndAngle(90);
                                        HIBackground background2 = new HIBackground();
                                        background2.setBackgroundColor(HIColor.initWithHexValue("EEE"));
                                        background2.setInnerRadius("60%");
                                        background2.setOuterRadius("100%");
                                        background2.setShape("arc");
                                        pane2.setBackground(new ArrayList<>(Arrays.asList(background2)));
                                        options2.setPane(pane2);
                                        HITooltip tooltip2 = new HITooltip();
                                        tooltip2.setEnabled(false);
                                        options2.setTooltip(tooltip2);
                                        HIPlotOptions plotoptions2 = new HIPlotOptions();
                                        plotoptions2.setSolidgauge(new HISolidgauge());
                                        plotoptions2.getSolidgauge().setDataLabels(new HIDataLabels());
                                        plotoptions2.getSolidgauge().getDataLabels().setY(5);
                                        plotoptions2.getSolidgauge().getDataLabels().setBorderWidth(0);
                                        plotoptions2.getSolidgauge().getDataLabels().setUseHTML(true);
                                        options2.setPlotOptions(plotoptions2);
                                        HIYAxis yaxis2 = new HIYAxis();
                                        yaxis2.setLineWidth(0);
                                        yaxis2.setTickAmount(2);
                                        yaxis2.setTitle(new HITitle());
                                        yaxis2.getTitle().setText("");
                                        yaxis2.setLabels(new HILabels());
                                        yaxis2.getTitle().setY(16);
                                        yaxis2.setMin(0);
                                        yaxis2.setMax(200);
                                        options2.setYAxis(new ArrayList<>(Arrays.asList(yaxis2)));
                                        options2.setCredits(credits);
                                        HISeries series2 = new HISolidgauge();
                                        series2.setName("");
                                        series2.setTooltip(new HITooltip());
                                        series2.getTooltip().setValueSuffix("DBP");
                                        series2.setDataLabels(new HIDataLabels());
                                        series2.getDataLabels().setFormat("<div style=\"text-align:center\"><span style=\"font-size:25px;color:black\">{y}</span><br/><span style=\"font-size:12px;color:silver\">DBP</span></div>");
                                        Number[] series2_data = new Number[]{Integer.parseInt(dys)};
                                        series2.setData(new ArrayList<>(Arrays.asList(series2_data)));
                                        options2.setSeries(new ArrayList<>(Arrays.asList(series2)));
                                        hcDys.setOptions(options2);
                                        hcDys.getOptions().setExporting(exporting);
                                        llBP.setVisibility(View.VISIBLE);
                                    }
                                }
                                if (vitalChartList.getVitalList().get(i).getVmID() == 5)
                                    txtTemp.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                                if (vitalChartList.getVitalList().get(i).getVmID() == 56) {
                                    sp = vitalChartList.getVitalList().get(i).getVitalValue();
                                    if (!sp.equalsIgnoreCase("-")) {
                                        txtSpo2.setText(sp + "%");
                                        mProgress.setProgress(Integer.parseInt(sp));
                                        ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, Integer.parseInt(sp));
                                        animation.setDuration(2000);
                                        animation.setInterpolator(new DecelerateInterpolator());
                                        animation.start();
                                        new Thread(() -> {
                                            while (pStatus < Integer.parseInt(sp)) {
                                                pStatus += 1;
                                                handler.post(() -> {
                                                    mProgress.setProgress(pStatus);
                                                    txtSpo2.setText(pStatus + "%");
                                                });
                                                try {
                                                    Thread.sleep(8);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                    }
                                }
                                if (vitalChartList.getVitalList().get(i).getVmID() == 74)
                                    txtHR.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                                if (vitalChartList.getVitalList().get(i).getVmID() == 7)
                                    txtRR.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                                if (vitalChartList.getVitalList().get(i).getVmID() == 3)
                                    txtPulse.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                            }
                        }
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<VitalListResp> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        } else {

            String sys, dys;

            vitalChartList = new VitalListResp();

            vitalChartList.setVitalList(DatabaseController.getPatientVitalList(SharedPrefManager.getInstance(context).getPid() + "_" + SharedPrefManager.getInstance(context).getHeadID()));

            for (int i = 0; i < vitalChartList.getVitalList().size(); i++) {

                if (vitalChartList.getVitalList().get(i).getVmID() == 1)
                    txtHeight.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                if (vitalChartList.getVitalList().get(i).getVmID() == 2)
                    txtWeight.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                if (vitalChartList.getVitalList().get(i).getVmID() == 4) {
                    sys = vitalChartList.getVitalList().get(i).getVitalValue();
                    if (!sys.equalsIgnoreCase("-")) {
                        HIOptions options1 = new HIOptions();
                        HIChart chart = new HIChart();
                        chart.setType("solidgauge");
                        options1.setChart(chart);
                        HITitle title1 = new HITitle();
                        title1.setText("");
                        options1.setTitle(title1);
                        HIPane pane = new HIPane();
                        String[] center = new String[]{"50%", "85%"};
                        pane.setCenter(new ArrayList<>(Arrays.asList(center)));
                        pane.setSize("120%");
                        pane.setStartAngle(-90);
                        pane.setEndAngle(90);
                        HIBackground background = new HIBackground();
                        background.setBackgroundColor(HIColor.initWithHexValue("EEE"));
                        background.setInnerRadius("60%");
                        background.setOuterRadius("100%");
                        background.setShape("arc");
                        pane.setBackground(new ArrayList<>(Arrays.asList(background)));
                        options1.setPane(pane);
                        HITooltip tooltip = new HITooltip();
                        tooltip.setEnabled(false);
                        options1.setTooltip(tooltip);
                        HIPlotOptions plotoptions1 = new HIPlotOptions();
                        plotoptions1.setSolidgauge(new HISolidgauge());
                        plotoptions1.getSolidgauge().setDataLabels(new HIDataLabels());
                        plotoptions1.getSolidgauge().getDataLabels().setY(5);
                        plotoptions1.getSolidgauge().getDataLabels().setBorderWidth(0);
                        plotoptions1.getSolidgauge().getDataLabels().setUseHTML(true);
                        options1.setPlotOptions(plotoptions1);
                        HIYAxis yaxis1 = new HIYAxis();
                        yaxis1.setLineWidth(0);
                        yaxis1.setTickAmount(2);
                        yaxis1.setTitle(new HITitle());
                        yaxis1.getTitle().setText("");
                        yaxis1.setLabels(new HILabels());
                        yaxis1.getTitle().setY(16);
                        yaxis1.setMin(0);
                        yaxis1.setMax(200);
                        options1.setYAxis(new ArrayList<>(Arrays.asList(yaxis1)));
                        options1.setCredits(credits);
                        HISeries series1 = new HISolidgauge();
                        series1.setName("");
                        series1.setTooltip(new HITooltip());
                        series1.getTooltip().setValueSuffix("SBP");
                        series1.setDataLabels(new HIDataLabels());
                        series1.getDataLabels().setFormat("<div style=\"text-align:center\"><span style=\"font-size:25px;color:black\">{y}</span><br/><span style=\"font-size:12px;color:silver\">SBP</span></div>");
                        Number[] series1_data = new Number[]{Integer.parseInt(sys)};
                        series1.setData(new ArrayList<>(Arrays.asList(series1_data)));
                        options1.setSeries(new ArrayList<>(Arrays.asList(series1)));
                        HIColorAxis axis = new HIColorAxis();
                        axis.setLineColor(HIColor.initWithRGB(57, 181, 74));
                        options1.setColorAxis(axis);
                        hcSys.setOptions(options1);
                        hcSys.getOptions().setExporting(exporting);
                        llBP.setVisibility(View.VISIBLE);
                    }
                }
                if (vitalChartList.getVitalList().get(i).getVmID() == 6) {
                    dys = vitalChartList.getVitalList().get(i).getVitalValue();
                    if (!dys.equalsIgnoreCase("-")) {
                        HIOptions options2 = new HIOptions();
                        HIChart chart2 = new HIChart();
                        chart2.setType("solidgauge");
                        options2.setChart(chart2);
                        HITitle title2 = new HITitle();
                        title2.setText("");
                        options2.setTitle(title2);
                        HIPane pane2 = new HIPane();
                        String[] center2 = new String[]{"50%", "85%"};
                        pane2.setCenter(new ArrayList<>(Arrays.asList(center2)));
                        pane2.setSize("120%");
                        pane2.setStartAngle(-90);
                        pane2.setEndAngle(90);
                        HIBackground background2 = new HIBackground();
                        background2.setBackgroundColor(HIColor.initWithHexValue("EEE"));
                        background2.setInnerRadius("60%");
                        background2.setOuterRadius("100%");
                        background2.setShape("arc");
                        pane2.setBackground(new ArrayList<>(Arrays.asList(background2)));
                        options2.setPane(pane2);
                        HITooltip tooltip2 = new HITooltip();
                        tooltip2.setEnabled(false);
                        options2.setTooltip(tooltip2);
                        HIPlotOptions plotoptions2 = new HIPlotOptions();
                        plotoptions2.setSolidgauge(new HISolidgauge());
                        plotoptions2.getSolidgauge().setDataLabels(new HIDataLabels());
                        plotoptions2.getSolidgauge().getDataLabels().setY(5);
                        plotoptions2.getSolidgauge().getDataLabels().setBorderWidth(0);
                        plotoptions2.getSolidgauge().getDataLabels().setUseHTML(true);
                        options2.setPlotOptions(plotoptions2);
                        HIYAxis yaxis2 = new HIYAxis();
                        yaxis2.setLineWidth(0);
                        yaxis2.setTickAmount(2);
                        yaxis2.setTitle(new HITitle());
                        yaxis2.getTitle().setText("");
                        yaxis2.setLabels(new HILabels());
                        yaxis2.getTitle().setY(16);
                        yaxis2.setMin(0);
                        yaxis2.setMax(200);
                        options2.setYAxis(new ArrayList<>(Arrays.asList(yaxis2)));
                        options2.setCredits(credits);
                        HISeries series2 = new HISolidgauge();
                        series2.setName("");
                        series2.setTooltip(new HITooltip());
                        series2.getTooltip().setValueSuffix("DBP");
                        series2.setDataLabels(new HIDataLabels());
                        series2.getDataLabels().setFormat("<div style=\"text-align:center\"><span style=\"font-size:25px;color:black\">{y}</span><br/><span style=\"font-size:12px;color:silver\">DBP</span></div>");
                        Number[] series2_data = new Number[]{Integer.parseInt(dys)};
                        series2.setData(new ArrayList<>(Arrays.asList(series2_data)));
                        options2.setSeries(new ArrayList<>(Arrays.asList(series2)));
                        hcDys.setOptions(options2);
                        hcDys.getOptions().setExporting(exporting);
                        llBP.setVisibility(View.VISIBLE);
                    }
                }
                if (vitalChartList.getVitalList().get(i).getVmID() == 5)
                    txtTemp.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                if (vitalChartList.getVitalList().get(i).getVmID() == 56) {
                    sp = vitalChartList.getVitalList().get(i).getVitalValue();
                    if (!sp.equalsIgnoreCase("-")) {
                        txtSpo2.setText(sp + "%");
                        mProgress.setProgress(Integer.parseInt(sp));
                        ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, Integer.parseInt(sp));
                        animation.setDuration(2000);
                        animation.setInterpolator(new DecelerateInterpolator());
                        animation.start();
                        new Thread(() -> {
                            while (pStatus < Integer.parseInt(sp)) {
                                pStatus += 1;
                                handler.post(() -> {
                                    mProgress.setProgress(pStatus);
                                    txtSpo2.setText(pStatus + "%");
                                });
                                try {
                                    Thread.sleep(8);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
                if (vitalChartList.getVitalList().get(i).getVmID() == 74)
                    txtHR.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                if (vitalChartList.getVitalList().get(i).getVmID() == 7)
                    txtRR.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                if (vitalChartList.getVitalList().get(i).getVmID() == 3)
                    txtPulse.setText(vitalChartList.getVitalList().get(i).getVitalValue());
            }
        }


    }

    private class TypeSelector {
        String value;
        int id;

        TypeSelector(int id, String value) {
            this.value = value;
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public int getId() {
            return id;
        }

        @NonNull
        @Override
        public String toString() {
            return value;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
