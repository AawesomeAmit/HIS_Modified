package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.trueform.era.his.Adapter.AddMedicationAdp;
import com.trueform.era.his.Model.GetIcdCodeModel;
import com.trueform.era.his.Model.MedicineList;
import com.trueform.era.his.Model.MedicineSearch;
import com.trueform.era.his.Model.PatientHistory;
import com.trueform.era.his.Model.Prescription;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.FreqUnitResp;
import com.trueform.era.his.Response.GetIcdCodeResp;
import com.trueform.era.his.Response.MedicineListResp;
import com.trueform.era.his.Response.MedicineSearchResp;
import com.trueform.era.his.Response.PrescribedMedResp;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMedication extends Fragment {
    private static AutoCompleteTextView etUnit, etConsultant;
    private TextView txtDate;
    private static RecyclerView rvMedication;
    private ArrayAdapter<MedicineSearch> arrayAdapter;
    private MedicineSearch medicineSearch;
    private Spinner spnConsultant;
    private MedicineSearchResp medicineSearchResp2;
    @SuppressLint("StaticFieldLeak")
    private static AutoCompleteTextView edtMedName;
    @SuppressLint("StaticFieldLeak")
    private static EditText edtRemark, edtStr;//edtHour, edtMinute, txtTime
    private static TextView tvTime;
    private String hour = "", minutes = "", amPm = "", time = "";
    @SuppressLint("StaticFieldLeak")
    private static Spinner spnFreq, spnForm; //,spnUnit
    private List<String> formList;
    private List<String> freqList;
    private List<String> unitList;
    List<MedicineList> medicineList;
    private static ArrayAdapter<String> arrayAdapter12;
    private static ArrayAdapter<String> arrayAdapter22;
    private static ArrayAdapter<String> arrayAdapter32;
    private JSONArray notification, notificationForSave, notificationForSave1;
    private TextView txtNotification, btnHistory;
    ScrollView scrollNoti;
    private FreqUnitResp freqResponse;
    private String notificationShow = "";
    private boolean selected = false;
    private Integer drugID = 0;
    private ArrayList<PatientHistory> patientHistoryArrayList;
    private String drugName = "";
    private static PrescribedMedResp medicineSearchResp1;
    private static List<Prescription> medicineSearches;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    List<GetIcdCodeModel> getIcdCodeModelListMain = new ArrayList<>();

    RecyclerView recyclerViewDiagnosis;

    AdapterNutrient adapterNutrient;

    Calendar c;

    public AddMedication() {

    }

    public static AddMedication newInstance(String param1, String param2) {
        AddMedication fragment = new AddMedication();
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
        View view = inflater.inflate(R.layout.fragment_add_medication, container, false);
        context = view.getContext();
        edtMedName = view.findViewById(R.id.edtMedName);
        String pHistory = "patientHistoryList";
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        spnConsultant = toolbar.findViewById(R.id.spnConsultant);
        txtDate = view.findViewById(R.id.txtDate);
        //TextView btnAdd = view.findViewById(R.id.btnAdd);
        TextView txtAction = view.findViewById(R.id.txtAction);
        TextView btnSave = view.findViewById(R.id.btnSave);
        // edtHour =view.findViewById(R.id.edtHour);
        // edtMinute =view.findViewById(R.id.edtMinute);
        //  txtTime=view.findViewById(R.id.txtTime);
        tvTime = view.findViewById(R.id.tvTime);
        edtStr = view.findViewById(R.id.txtStr);
        edtRemark = view.findViewById(R.id.edtRemark);
        spnForm = view.findViewById(R.id.txtDoseForm);
        spnFreq = view.findViewById(R.id.spnFreq);
        //  spnUnit=view.findViewById(R.id.txtUnit);
        etUnit = view.findViewById(R.id.etUnit);
        etConsultant = view.findViewById(R.id.etConsultant);

        recyclerViewDiagnosis = view.findViewById(R.id.recyclerViewDiagnosis);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        recyclerViewDiagnosis.setLayoutManager(layoutManager);
        recyclerViewDiagnosis.setNestedScrollingEnabled(false);
        recyclerViewDiagnosis.setHasFixedSize(true);

        etConsultant.setThreshold(3);

        c = Calendar.getInstance();
        hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        minutes = String.valueOf(c.get(Calendar.MINUTE));

        formList = new ArrayList<>();
        freqList = new ArrayList<>();
        unitList = new ArrayList<>();
        notificationForSave1 = new JSONArray();
        medicineSearches = new ArrayList<>();
        txtNotification = view.findViewById(R.id.txtNotification);
        scrollNoti = view.findViewById(R.id.scrollNoti);
        patientHistoryArrayList = SharedPrefManager.getInstance(context).getPatientHistoryList(pHistory);
        medicineSearchResp1 = new PrescribedMedResp();
        rvMedication = view.findViewById(R.id.rvMedication);
        rvMedication.setLayoutManager(new LinearLayoutManager(context));
        Call<FreqUnitResp> call = RetrofitClient.getInstance().getApi().getUnitFrequencyDosages(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString());
        call.enqueue(new Callback<FreqUnitResp>() {
            @Override
            public void onResponse(Call<FreqUnitResp> call, Response<FreqUnitResp> response) {
                Utils.showRequestDialog(context);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        freqResponse = response.body();
                        formList.clear();
                        freqList.clear();
                        unitList.clear();
                        for (int i = 0; i < freqResponse.getDosages().size(); i++) {
                            formList.add(i, freqResponse.getDosages().get(i).getName());
                        }
                        for (int i = 0; i < freqResponse.getFrequency().size(); i++) {
                            freqList.add(i, freqResponse.getFrequency().get(i).getName());
                        }
                        for (int i = 0; i < freqResponse.getDrugUnit().size(); i++) {
                            unitList.add(i, freqResponse.getDrugUnit().get(i).getDrugunitname());
                        }

                        arrayAdapter12 = new ArrayAdapter<>(context, R.layout.spinner_layout, formList);
                        arrayAdapter22 = new ArrayAdapter<>(context, R.layout.spinner_layout, freqList);
                        arrayAdapter32 = new ArrayAdapter<>(context, R.layout.spinner_layout, unitList);
                        spnForm.setAdapter(arrayAdapter12);
                        spnForm.setSelection(0);
                        spnFreq.setAdapter(arrayAdapter22);
                        spnFreq.setSelection(0);

                        etUnit.setAdapter(arrayAdapter32);
                        etUnit.setThreshold(0);

//                        spnUnit.setAdapter(arrayAdapter32);
//                        spnUnit.setSelection(0);
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<FreqUnitResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
        bindData();
        edtMedName.setOnItemClickListener((adapterView, view1, i, l) -> {
            drugID = medicineSearchResp2.getMedicineSearches().get(i).getDrugID();
            drugName = medicineSearchResp2.getMedicineSearches().get(i).getDrugName();
            selected = true;

            int spinnerPosition1 = arrayAdapter12.getPosition(medicineSearchResp2.getMedicineSearches().get(i).getDosageForm());
            spnForm.setSelection(spinnerPosition1);

            int spinnerPosition2 = arrayAdapter22.getPosition(medicineSearchResp2.getMedicineSearches().get(i).getDoseFrequency());
            spnFreq.setSelection(spinnerPosition2);

            edtStr.setText(medicineSearchResp2.getMedicineSearches().get(i).getDoseStrength());
            etUnit.setText(medicineSearchResp2.getMedicineSearches().get(i).getDoseUnit());
            edtRemark.setText(medicineSearchResp2.getMedicineSearches().get(i).getRemark());

        });
        edtMedName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtMedName.getText().length() > 1) {
                    Call<MedicineSearchResp> call = RetrofitClient.getInstance().getApi().getMedicineBySearch(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(),
                            edtMedName.getText().toString().trim(), SharedPrefManager.getInstance(context).getUser().getUserid());
                    call.enqueue(new Callback<MedicineSearchResp>() {
                        @Override
                        public void onResponse(Call<MedicineSearchResp> call, Response<MedicineSearchResp> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    medicineSearchResp2 = response.body();
                                    //medicineSearchResp = response.body();
                                    arrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout,
                                            medicineSearchResp2.getMedicineSearches());
                                    arrayAdapter.setDropDownViewResource(R.layout.spinner_layout);
                                    edtMedName.setAdapter(arrayAdapter);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MedicineSearchResp> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etConsultant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    if (ConnectivityChecker.checker(getActivity())) {
                        hitGetICDCode(s.toString());
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tvTime.setOnClickListener(view1 -> {

            TimePickerDialog picker = new TimePickerDialog(context,
                    (tp, sHour, sMinute) -> {

                        tvTime.setText(Utils.formatTime(sHour, sMinute));
                        //   tvTime.setText(sHour + ":" + sMinute);

                        hour = String.valueOf(sHour);
                        minutes = String.valueOf(sMinute);
                        //amPm =
                        time = Utils.formatTime(sHour, sMinute);

                    }, Integer.parseInt(hour), Integer.parseInt(minutes), false);


            picker.show();
        });

        txtAction.setOnClickListener(view12 -> {
            if (!edtMedName.getText().toString().isEmpty()) {
                //if (((edtHour.getText().toString().isEmpty() && edtMinute.getText().toString().isEmpty() && txtTime.getText().toString().isEmpty()) || ((!edtHour.getText().toString().isEmpty() && !edtMinute.getText().toString().isEmpty() && !txtTime.getText().toString().isEmpty()) && ((Integer.parseInt(edtHour.getText().toString())<=12) && (Integer.parseInt(edtMinute.getText().toString())<60)))))
                {

                    //  String time = edtHour.getText().toString().trim() + ":" + edtMinute.getText().toString().trim() + " " + txtTime.getText().toString().trim();
//                    String time = hour + ":" + minutes + " " + amPm;

                    if (tvTime.getText().toString().isEmpty()) {
                        time = "";
                    }

                    if (selected)
                        medicineSearches.add(new Prescription(drugID, drugName, spnForm.getSelectedItem().toString().trim(),
                                //edtStr.getText().toString().trim(), spnUnit.getSelectedItem()==null?"":spnUnit.getSelectedItem().toString(),
                                edtStr.getText().toString().trim(), etUnit.getText().toString(),
                                spnFreq.getSelectedItem() == null ? "" : spnFreq.getSelectedItem().toString().trim(), time/*checkDigit(hours) + ":" + checkDigit(minutes) + " " + am*/,
                                edtRemark.getText().toString().trim(), 2));
                    else
                        medicineSearches.add(new Prescription(0, edtMedName.getText().toString().trim(),
                                // spnForm.getSelectedItem().toString(), edtStr.getText().toString().trim(), spnUnit.getSelectedItem()==null?"":spnUnit.getSelectedItem().toString(),
                                spnForm.getSelectedItem().toString(), edtStr.getText().toString().trim(), etUnit.getText().toString(),
                                spnFreq.getSelectedItem() == null ? "" : spnFreq.getSelectedItem().toString().trim(), time/*checkDigit(hours) + ":" + checkDigit(minutes) + " " + am*/,
                                edtRemark.getText().toString().trim(), 2));
                    medicineSearchResp1.getPrescription().add(medicineSearches.get(medicineSearches.size() - 1));
                    //medicineSearchResp1.setPrescription(medicineSearches);
                    AddMedicationAdp adp = new AddMedicationAdp(context, medicineSearchResp1);
                    rvMedication.setAdapter(adp);
                    getNotification();
                    edtMedName.setText("");
                    edtRemark.setText("");
                    edtStr.setText("");
                    etUnit.setText("");
                    tvTime.setText("");

                    edtMedName.setEnabled(true);
                }
               /* else {
                    Toast.makeText(context, "Please enter proper time", Toast.LENGTH_LONG).show();
                }*/
            } else Toast.makeText(context, "Please enter medicine", Toast.LENGTH_LONG).show();
        });
        btnSave.setOnClickListener(view13 -> {
            if (SharedPrefManager.getInstance(context).getUser().getDesigid() == 1) {
                sendPrescription(SharedPrefManager.getInstance(context).getUser().getUserid());
            } else if (SharedPrefManager.getInstance(context).getUser().getDesigid() != 1 && spnConsultant.getSelectedItemPosition() != 0) {
                sendPrescription(SharedPrefManager.getInstance(context).getConsultantList().get(spnConsultant.getSelectedItemPosition()).getUserid());
            } else Toast.makeText(context, "Consultant name required!", Toast.LENGTH_LONG).show();
        });
        return view;
    }

    private void bindData() {
        Call<PrescribedMedResp> call1 = RetrofitClient.getInstance().getApi().getCurrentPrescripttionHistory(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getHeadID(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call1.enqueue(new Callback<PrescribedMedResp>() {
            @Override
            public void onResponse(Call<PrescribedMedResp> call1, Response<PrescribedMedResp> response) {
                Utils.showRequestDialog(context);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        medicineSearchResp1 = response.body();
                        if (medicineSearchResp1.getPrescription().size() > 0)
                            txtDate.setText(medicineSearchResp1.getPrescription().get(0).getPrescribedDated());
                        AddMedicationAdp adp = new AddMedicationAdp(context, medicineSearchResp1);
                        rvMedication.setAdapter(adp);

                        getIcdCodeModelListMain.clear();

                        for (int i = 0; i < medicineSearchResp1.getPatientHistory().size(); i++) {

                            if (medicineSearchResp1.getPatientHistory().get(i).getPdmId() == 4) {
                                getIcdCodeModelListMain.add(0, new GetIcdCodeModel());
                                getIcdCodeModelListMain.get(0).setDetailID(medicineSearchResp1.getPatientHistory().get(i).getDetailID()); // get item
                                getIcdCodeModelListMain.get(0).setDetails(medicineSearchResp1.getPatientHistory().get(i).getDetails());
                                getIcdCodeModelListMain.get(0).setPdmID(medicineSearchResp1.getPatientHistory().get(i).getPdmId());
                                //getNutrientByPrefixTextModelListMain.get(0).setSelected(true);
                            }
                        }


                        adapterNutrient = new AdapterNutrient(getIcdCodeModelListMain);
                        recyclerViewDiagnosis.setAdapter(adapterNutrient);


                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<PrescribedMedResp> call1, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    public void edit(Integer drugID, String drugName, String dosageForm, String doseStrength, String doseUnit, String doseFrequency, String duration, String remark) {
        medicineSearch = new MedicineSearch(drugID, drugName, dosageForm, doseStrength, doseUnit, doseFrequency, duration, remark);
        edtMedName.setText(drugName);
        edtMedName.setEnabled(false);
        spnForm.setSelection(arrayAdapter12.getPosition(dosageForm));
        spnFreq.setSelection(arrayAdapter22.getPosition(doseFrequency));
        edtStr.setText(doseStrength);
        // spnUnit.setSelection(arrayAdapter32.getPosition(doseUnit));

        etUnit.setText(doseUnit);
        tvTime.setText(duration);

      /*  String[] time=duration.split(":");
        if(time.length==3){
            edtHour.setText(time[0]);
            edtMinute.setText(time[1]);
            txtTime.setText(time[2]);
        }*/
        edtRemark.setText(remark);
    }

    public void removeRow(int i) {
        try {
            medicineSearchResp1.getPrescription().remove(i);

            medicineSearchResp1.setPrescription(medicineSearchResp1.getPrescription());
            AddMedicationAdp adp = new AddMedicationAdp(context, medicineSearchResp1);
            rvMedication.setAdapter(adp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stopDelMed(final int i, int isStop, int status) {
        try {
            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateMedication(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), medicineSearchResp1.getPrescription().get(i).getId(), isStop, status);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Utils.showRequestDialog(context);
                    if (response.isSuccessful()) {
                        medicineSearchResp1.getPrescription().remove(i);
                        medicineSearchResp1.setPrescription(medicineSearchResp1.getPrescription());
                        AddMedicationAdp adp = new AddMedicationAdp(context, medicineSearchResp1);
                        rvMedication.setAdapter(adp);
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void getNotification() {
        Utils.showRequestDialog(context);
        Log.v("hitApi:", RetrofitClient.BASE_URL + "Prescription/GetNotification");
        JSONArray medArray = new JSONArray();
        JSONArray diagnosisArray = new JSONArray();
        JSONArray patientDetails = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            if (medicineSearchResp1.getPrescription().size() > 0) {
                for (int i = 0; i < medicineSearchResp1.getPrescription().size(); i++) {
                    JSONObject object = new JSONObject();
                    object.put("medID", medicineSearchResp1.getPrescription().get(i).getDrugID());
                    object.put("medName", medicineSearchResp1.getPrescription().get(i).getDrugName());
                    object.put("medType", "P");
                    if (i == medicineSearchResp1.getPrescription().size() - 1)
                        object.put("currentStatus", 1);
                    else object.put("currentStatus", 0);
                    medArray.put(object);
                }
            }
            if (patientHistoryArrayList.size() > 0) {
                for (int i = 0; i < patientHistoryArrayList.size(); i++) {
                    if (patientHistoryArrayList.get(i).getPdmId() == 4) {
                        JSONObject object = new JSONObject();
                        object.put("diagnosisID", patientHistoryArrayList.get(i).getDetailID());
                        object.put("diagnosisName", patientHistoryArrayList.get(i).getDetails());
                        diagnosisArray.put(object);
                    }
                }
            }
            if (patientHistoryArrayList.size() > 0) {
                for (int i = 0; i < patientHistoryArrayList.size(); i++) {
                    if (patientHistoryArrayList.get(i).getPdmId() == 1) {
                        JSONObject object = new JSONObject();
                        object.put("detailID", patientHistoryArrayList.get(i).getDetailID());
                        object.put("details", patientHistoryArrayList.get(i).getDetails());
                        object.put("nameMaster", patientHistoryArrayList.get(i).getNameMaster());
                        object.put("pdmId", patientHistoryArrayList.get(i).getPdmId());
                        object.put("pmID", patientHistoryArrayList.get(i).getPmID());
                        patientDetails.put(object);
                    }
                }
            }
            jsonObject.put("PID", SharedPrefManager.getInstance(context).getPid());
            jsonObject.put("headID", SharedPrefManager.getInstance(context).getHeadID());
            jsonObject.put("ipNo", SharedPrefManager.getInstance(context).getIpNo());
            jsonObject.put("medList", medArray);
            jsonObject.put("patientDetails", patientDetails);
            jsonObject.put("diagnosisList", diagnosisArray);
            Log.v("hitApiArr", String.valueOf(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(RetrofitClient.BASE_URL + "Prescription/GetNotification")
                .addHeaders("accessToken", SharedPrefManager.getInstance(context).getUser().getAccessToken())
                .addHeaders("userID", SharedPrefManager.getInstance(context).getUser().getUserid().toString())
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getJSONArray("showNotificaton") != null)
                                notification = response.getJSONArray("showNotificaton");
                            else notification = null;
                            if (response.getJSONArray("notificatonForSave") != null) {
                                notificationForSave = response.getJSONArray("notificatonForSave");
                                for (int i = 0; i < notificationForSave.length(); i++) {
                                    notificationForSave1.put(notificationForSave.get(i));
                                }
                            } else notificationForSave = null;
                            JSONArray medID = response.getJSONArray("medId");
                            JSONObject medIDObject = medID.getJSONObject(0);
                            medicineSearches.get(medicineSearches.size() - 1).setDrugID(medIDObject.getInt("currentMedID"));
                            if (notification != null) {
                                scrollNoti.setVisibility(View.VISIBLE);
                                for (int i = 0; i < notification.length(); i++) {
                                    JSONObject object = notification.getJSONObject(i);
                                    notificationShow = notificationShow.concat((object.getString("notification") + "\n" + Html.fromHtml(object.getString("remark"))));
                                }
                            }
                            txtNotification.setText(notificationShow);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Utils.hideDialog();
                    }

                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            //loader.cancel();
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                        Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }
                });
    }

    private void sendPrescription(int drId) {
        Log.v("hitApi:", RetrofitClient.BASE_URL + "Prescription/SavePrescriptionAndInvistigation");
        JSONArray array = new JSONArray();
        JSONArray arrayPatientDetails = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        boolean empty = true;
        try {
            if (medicineSearchResp1.getPrescription().size() > 0) {
                for (int i = 0; i < medicineSearchResp1.getPrescription().size(); i++) {
                    JSONObject object = new JSONObject();
                    object.put("sno", i);
                    object.put("dosageForm", medicineSearchResp1.getPrescription().get(i).getDosageForm());
                    object.put("doseFrequency", medicineSearchResp1.getPrescription().get(i).getDoseFrequency());
                    object.put("doseStrength", medicineSearchResp1.getPrescription().get(i).getDoseStrength());
                    object.put("doseUnit", medicineSearchResp1.getPrescription().get(i).getDoseUnit());
                    object.put("drugID", medicineSearchResp1.getPrescription().get(i).getDrugID());
                    object.put("drugName", medicineSearchResp1.getPrescription().get(i).getDrugName());
                    object.put("remark", medicineSearchResp1.getPrescription().get(i).getRemark());
                    object.put("duration", medicineSearchResp1.getPrescription().get(i).getDuration());
                    array.put(object);
                }
                empty = false;
            } else empty = true;

            if (getIcdCodeModelListMain.size() > 0) {
                for (int i = 0; i < getIcdCodeModelListMain.size(); i++) {
                    JSONObject object = new JSONObject();
                    object.put("pmID", SharedPrefManager.getInstance(getActivity()).getPmId());
                    object.put("pdmId", getIcdCodeModelListMain.get(i).getPdmID());
                    object.put("details", getIcdCodeModelListMain.get(i).getDetails());
                    object.put("detailID", getIcdCodeModelListMain.get(i).getDetailID());
                    object.put("nameMaster", "diagnosisDetails");
                    object.put("isFinal", false);
                    arrayPatientDetails.put(object);
                }
            }

            jsonObject.put("PID", SharedPrefManager.getInstance(context).getPid());
            jsonObject.put("headID", SharedPrefManager.getInstance(context).getHeadID());
            jsonObject.put("subDeptID", SharedPrefManager.getInstance(context).getSubDept().getId());
            jsonObject.put("investigation", new JSONArray());
            jsonObject.put("ipNo", SharedPrefManager.getInstance(context).getIpNo());
            jsonObject.put("userID", SharedPrefManager.getInstance(context).getUser().getUserid());
            jsonObject.put("consultantName", drId);
            jsonObject.put("prescription", array);
            jsonObject.put("patientDetails", arrayPatientDetails);
            jsonObject.put("medNotification", notificationForSave1);
            Log.v("hitApiArr", String.valueOf(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!empty) {
            AndroidNetworking.post(RetrofitClient.BASE_URL + "Prescription/SavePrescriptionAndInvistigation")
                    .addHeaders("accessToken", SharedPrefManager.getInstance(context).getUser().getAccessToken())
                    .addHeaders("userID", SharedPrefManager.getInstance(context).getUser().getUserid().toString())
                    .addJSONObjectBody(jsonObject)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(context, "Prescription saved successfully", Toast.LENGTH_SHORT).show();
                            bindData();
                        }

                        @Override
                        public void onError(ANError error) {
                            if (error.getErrorCode() != 0) {
                                Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                                Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                                Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                            } else {
                                Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                            }
                            Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else
            Toast.makeText(context, "Please add atleast 1 medication", Toast.LENGTH_SHORT).show();
    }

    private void bindMed(int diseaseId, String packageName){
        Call<MedicineListResp> call = RetrofitClient.getInstance().getApi().getMedicationList(
                SharedPrefManager.getInstance(context).getUser().getAccessToken(),
                SharedPrefManager.getInstance(context).getUser().getUserid().toString(),
                diseaseId,SharedPrefManager.getInstance(context).getSubDept().getId(),SharedPrefManager.getInstance(context).getUser().getUserid(),
                packageName);
        call.enqueue(new Callback<MedicineListResp>() {
            @Override
            public void onResponse(Call<MedicineListResp> call, Response<MedicineListResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        if(!response.body().getMedicineList().isEmpty()) {
                            medicineList=response.body().getMedicineList();
                            showPopup();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MedicineListResp> call, Throwable t) {

            }
        });
    }
    private void showPopup() {
        View popupView = getLayoutInflater().inflate(R.layout.popup_diagnosis_add_medication, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        RecyclerView rvMedList = popupView.findViewById(R.id.rvMedList);
        TextView btnAdd = popupView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view ->{
            for (int i = 0; i < medicineList.size(); i++) {
                if(medicineList.get(i).getSelected()){
                    medicineSearches.add(new Prescription(medicineList.get(i).getDrugID(), medicineList.get(i).getDrugName(),
                            medicineList.get(i).getDosageForm(), medicineList.get(i).getDoseStrength(), medicineList.get(i).getDoseUnit(),
                            medicineList.get(i).getDoseFrequency(), "",
                            "", 2));
                    medicineSearchResp1.getPrescription().add(medicineSearches.get(medicineSearches.size() - 1));
                }
            }
            AddMedicationAdp adp = new AddMedicationAdp(context, medicineSearchResp1);
            rvMedication.setAdapter(adp);
            popupWindow.dismiss();
        });
        rvMedList.setLayoutManager(new LinearLayoutManager(context));
        rvMedList.setAdapter(new MedListAdp());
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        recyclerViewDiagnosis.getLocationOnScreen(location);
        popupWindow.showAtLocation(recyclerViewDiagnosis, Gravity.CENTER, 0, 0);
    }
    private void hitGetICDCode(String searchText) {

        // Utils.showRequestDialog(getActivity());

        Call<GetIcdCodeResp> call = RetrofitClient.getInstance().getApi().getICDCode(
                SharedPrefManager.getInstance(context).getUser().getAccessToken(),
                SharedPrefManager.getInstance(context).getUser().getUserid().toString(),
                searchText,
                SharedPrefManager.getInstance(context).getUser().getUserid().toString());

        call.enqueue(new Callback<GetIcdCodeResp>() {
            @Override
            public void onResponse(Call<GetIcdCodeResp> call, Response<GetIcdCodeResp> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        List<GetIcdCodeModel> getIcdCodeModelList = response.body().getIcdList();

                        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout_new, getIcdCodeModelList);
                        //arrayAdapter.setDropDownViewResource(R.layout.inflate_auto_complete_text);
                        etConsultant.setAdapter(arrayAdapter);

                        etConsultant.setOnItemClickListener((parent, view, position, id) -> {
                            try {
                                if (!containsDetail(getIcdCodeModelListMain, getIcdCodeModelList.get(position).getDetailID().toString())) {
                                    getIcdCodeModelListMain.add(0, new GetIcdCodeModel());
                                    getIcdCodeModelListMain.get(0).setDetailID(getIcdCodeModelList.get(position).getDetailID()); // get item
                                    getIcdCodeModelListMain.get(0).setDetails(getIcdCodeModelList.get(position).getDetails());
                                    getIcdCodeModelListMain.get(0).setPdmID(getIcdCodeModelList.get(position).getPdmID());
                                    //getNutrientByPrefixTextModelListMain.get(0).setSelected(true);
                                    bindMed(getIcdCodeModelList.get(position).getDetailID(), searchText/*getIcdCodeModelList.get(position).getDetails()*/);
                                    etConsultant.setText("");
                                    if (adapterNutrient != null) {
                                        adapterNutrient.notifyItemInserted(0);
                                        // adapterNutrient.smoothScrollToPosition(0);
                                    } else {
                                        adapterNutrient = new AdapterNutrient(getIcdCodeModelListMain);
                                        recyclerViewDiagnosis.setAdapter(adapterNutrient);
                                    }
                                } else {
                                    //AppUtils.hideSoftKeyboard(mActivity);

                                    Toast.makeText(getActivity(), "Diagnosis already added", Toast.LENGTH_SHORT).show();

                                    etConsultant.setText("");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        });

                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetIcdCodeResp> call, Throwable t) {
                Log.e("onFailure:", t.getMessage());

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                Utils.hideDialog();
            }
        });
    }

    public class MedListAdp extends RecyclerView.Adapter<MedListAdp.RecyclerViewHolder> {
        MedListAdp() {}

        @NonNull
        @Override
        public MedListAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.inner_diagnosis_med_list, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull MedListAdp.RecyclerViewHolder holder, int i) {
            holder.tvMedName.setText(medicineList.get(i).getDrugName());
            holder.tvStrength.setText(medicineList.get(i).getDoseStrength() + " " + medicineList.get(i).getDoseUnit());
            holder.tvForm.setText(medicineList.get(i).getDosageForm());
            holder.tvSFreq.setText(medicineList.get(i).getDoseFrequency());
            if (medicineList.get(i).getSelected()) holder.rowLayout.setBackgroundColor(getResources().getColor(R.color.blue_selected));
            else holder.rowLayout.setBackgroundColor(getResources().getColor(R.color.white));
            holder.rowLayout.setOnClickListener(view -> {
                if (medicineList.get(i).getSelected()) {
                    medicineList.get(i).setSelected(false);
                    holder.rowLayout.setBackgroundColor(getResources().getColor(R.color.white));
                } else {
                    medicineList.get(i).setSelected(true);
                    holder.rowLayout.setBackgroundColor(getResources().getColor(R.color.blue_selected));
                }
            });
        }

        @Override
        public int getItemCount() {
            return medicineList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvMedName, tvStrength, tvForm, tvSFreq;
            LinearLayout rowLayout;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                tvMedName = itemView.findViewById(R.id.tvMedName);
                tvStrength = itemView.findViewById(R.id.tvStrength);
                tvForm = itemView.findViewById(R.id.tvForm);
                tvSFreq = itemView.findViewById(R.id.tvSFreq);
                rowLayout = itemView.findViewById(R.id.rowLayout);
            }
        }
    }
    boolean containsDetail(List<GetIcdCodeModel> list, String name) {

        for (GetIcdCodeModel item : list) {
            if (item.getDetailID().toString().equals(name)) {

                return true;
            }
        }

        return false;
    }


    private class AdapterNutrient extends RecyclerView.Adapter<HolderNutrient> {
        List<GetIcdCodeModel> data = new ArrayList<>();
        private int lastPosition = -1;

        public AdapterNutrient(List<GetIcdCodeModel> favList) {
            data = favList;
        }

        public HolderNutrient onCreateViewHolder(ViewGroup parent, int viewType) {
            // return new HolderNutrient(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_selected_problems, parent, false));
            return new HolderNutrient(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_nutrient, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderNutrient holder, final int position) {

            final GetIcdCodeModel getAllSuggestedDiseaseModel = data.get(position);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.tvNutrient.setText(Html.fromHtml(getAllSuggestedDiseaseModel.getDetails().trim(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.tvNutrient.setText(Html.fromHtml(getAllSuggestedDiseaseModel.getDetails().trim()));
            }

            holder.ivRemove.setOnClickListener(view -> {
                getIcdCodeModelListMain.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getIcdCodeModelListMain.size());

            });


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

        TextView tvNutrient;

        ImageView ivRemove;

        public HolderNutrient(View itemView) {
            super(itemView);
            tvNutrient = itemView.findViewById(R.id.tvNutrient);
            ivRemove = itemView.findViewById(R.id.ivRemove);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}