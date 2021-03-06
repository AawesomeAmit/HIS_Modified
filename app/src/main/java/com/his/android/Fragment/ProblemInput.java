package com.his.android.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.his.android.Adapter.ProbDisplayAdp;
import com.his.android.Model.AttributeList;
import com.his.android.Model.AttributeValueList;
import com.his.android.Model.ProblemList;
import com.his.android.R;
import com.his.android.Response.AttribValueResp;
import com.his.android.Response.ProbAttribResp;
import com.his.android.Response.ProblemDataResp;
import com.his.android.Response.ProblemListResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.RetrofitClientFile;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.his.android.Fragment.InputVital.REQUEST_AUDIO_PERMISSION_CODE;

public class ProblemInput extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView txtFrmDate;
    private TextView txtFrmTime;
    private TextView txtToDate;
    private TextView txtToTime, txtVoice;
    private static String fromDate = "";
    private static String toDate = "";
    private SimpleDateFormat format1;
    private AutoCompleteTextView edtProb;
    private static RecyclerView rvProblem;
    private int mYear = 0, mMonth = 0, mDay = 0, tYear = 0, tMonth = 0, tDay = 0, mHour=0, mMinute=0;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null;
    Date today = new Date();
    private Date toToday = new Date();
    @SuppressLint("StaticFieldLeak")
    static Context context;
    Calendar c;
    private int probId=0;
    SimpleDateFormat format2;
    private List<ProblemList> problemLists;
    private ArrayAdapter<ProblemList> problemListAdp;
    private ArrayAdapter<AttributeList> attributeListAdp;
    private List<AttributeList> attributeLists;
    private List<AttributeValueList> attributeValueLists;
    private ArrayAdapter<AttributeValueList> attributeValueListAdp;
    private Spinner spnAttrib, spnAttVal;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProblemInput() {
        // Required empty public constructor
    }

    public static ProblemInput newInstance(String param1, String param2) {
        ProblemInput fragment = new ProblemInput();
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

    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_problem_input, container, false);
        txtFrmDate=view.findViewById(R.id.txtFrmDate);
        txtToTime=view.findViewById(R.id.txtToTime);
        TextView btnSave = view.findViewById(R.id.btnSave);
        edtProb = view.findViewById(R.id.edtProb);
        spnAttVal = view.findViewById(R.id.spnAttVal);
        spnAttrib = view.findViewById(R.id.spnAttrib);
        txtToDate=view.findViewById(R.id.txtToDate);
        txtFrmTime=view.findViewById(R.id.txtFrmTime);
        rvProblem=view.findViewById(R.id.rvProblem);
        txtVoice=view.findViewById(R.id.txtVoice);
        context=view.getContext();
        Utils.showRequestDialog(context);
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        tYear = c.get(Calendar.YEAR);
        tMonth = c.get(Calendar.MONTH);
        tDay = c.get(Calendar.DAY_OF_MONTH);
        format1 = new SimpleDateFormat("dd/MM/yyyy");
        format2 = new SimpleDateFormat("hh:mm a");
        fromDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        toDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtFrmDate.setText(Utils.formatDate(fromDate));
        txtToDate.setText(Utils.formatDate(fromDate));
        txtFrmTime.setText(format2.format(today));
        txtToTime.setText(format2.format(today));
        txtFrmDate.setOnClickListener(this);
        txtFrmTime.setOnClickListener(this);
        txtToDate.setOnClickListener(this);
        txtToTime.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        txtVoice.setOnClickListener(this);
        problemLists=new ArrayList<>();
        attributeLists=new ArrayList<>();
        attributeValueLists=new ArrayList<>();
        attributeLists.add(0, new AttributeList(0, "Select Attribute"));
        attributeValueLists.add(0, new AttributeValueList(0, "Select Attrib. Value"));
        rvProblem.setLayoutManager(new LinearLayoutManager(context));
        edtProb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                spnAttrib.setAdapter(null);
                spnAttVal.setAdapter(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edtProb.getText().toString().length()>1){
                    //Utils.showRequestDialog(context);
                    Call<ProblemListResp> call= RetrofitClient.getInstance().getApi().getAutoCompleteProblem(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), edtProb.getText().toString().trim(), SharedPrefManager.getInstance(context).getUser().getUserid());
                    call.enqueue(new Callback<ProblemListResp>() {
                        @Override
                        public void onResponse(Call<ProblemListResp> call, Response<ProblemListResp> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    problemLists.clear();
                                    problemLists.addAll(response.body().getProblemList());
                                    if (problemLists.size() > 0) {
                                        problemListAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, problemLists);
                                        problemListAdp.setDropDownViewResource(R.layout.spinner_layout);
                                        edtProb.setAdapter(problemListAdp);
                                    }
                                }
                            }
                            //Utils.hideDialog();
                        }

                        @Override
                        public void onFailure(Call<ProblemListResp> call, Throwable t) {
                            Utils.hideDialog();
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtProb.setOnItemClickListener((adapterView, view1, i, l) -> {
            attributeLists.clear();
            attributeLists.add(0, new AttributeList(0, "Select Attribute"));
            probId=problemLists.get(i).getId();
            Utils.showRequestDialog(context);
            Call<ProbAttribResp> call= RetrofitClient.getInstance().getApi().getProblemAttribute(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), problemLists.get(i).getId());
            call.enqueue(new Callback<ProbAttribResp>() {
                @Override
                public void onResponse(Call<ProbAttribResp> call, Response<ProbAttribResp> response) {
                    if(response.isSuccessful()){
                        if (response.body() != null) {
                            if(response.body().getAttributeList().size()>0) {
                                attributeLists.addAll(1, response.body().getAttributeList());
                                attributeListAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, attributeLists);
                            }
                        }
                    }
                    spnAttrib.setAdapter(attributeListAdp);
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<ProbAttribResp> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        });
        spnAttrib.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                attributeValueLists.clear();
                Utils.showRequestDialog(context);
                attributeValueLists.add(0, new AttributeValueList(0, "Select Attrib. Value"));
                Call<AttribValueResp> call= RetrofitClient.getInstance().getApi().getProblemAttributeValue(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), attributeLists.get(i).getAttributeID());
                call.enqueue(new Callback<AttribValueResp>() {
                    @Override
                    public void onResponse(Call<AttribValueResp> call, Response<AttribValueResp> response) {
                        if(response.isSuccessful()){
                            if (response.body() != null) {
                                if (response.body().getAttributeValueList().size() > 0) {
                                    attributeValueLists.addAll(1, response.body().getAttributeValueList());
                                }
                            }
                        }
                        attributeValueListAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, attributeValueLists);
                        spnAttVal.setAdapter(attributeValueListAdp);
                        Utils.hideDialog();
                    }

                    @Override
                    public void onFailure(Call<AttribValueResp> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        bind();
        return view;
    }


    private void recordingPopup(){
        View popupView = getLayoutInflater().inflate(R.layout.popup_vital_recording, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        LinearLayout lLayout = popupView.findViewById(R.id.lLayout);
        ImageButton startbtn = popupView.findViewById(R.id.btnRecord);
        ImageButton stopbtn = popupView.findViewById(R.id.btnStop);
        ImageButton playbtn = popupView.findViewById(R.id.btnPlay);
        ImageButton stopplay = popupView.findViewById(R.id.btnStopPlay);
        TextView btnSave = popupView.findViewById(R.id.btnSave);
        TextView btnClose = popupView.findViewById(R.id.btnClose);
        TextView txtTitle = popupView.findViewById(R.id.txtTitle);
        txtTitle.setText("Record Problem");
        stopbtn.setEnabled(false);
        playbtn.setEnabled(false);
        stopplay.setEnabled(false);
        btnSave.setEnabled(false);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        lLayout.getLocationOnScreen(location);
        popupWindow.showAtLocation(lLayout, Gravity.CENTER, 0, 0);
        btnClose.setOnClickListener(view -> popupWindow.dismiss());
        startbtn.setOnClickListener(v -> {
            if(CheckPermissions()) {
                stopbtn.setEnabled(true);
                startbtn.setEnabled(false);
                playbtn.setEnabled(false);
                btnSave.setEnabled(false);
                stopplay.setEnabled(false);
                startbtn.setVisibility(View.GONE);
                stopbtn.setVisibility(View.VISIBLE);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
                mFileName += "/"+timeStamp+"_OutputRecording.mp3";
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                mRecorder.setOutputFile(mFileName);
                try {
                    mRecorder.prepare();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "prepare() failed");
                }
                mRecorder.start();
                Toast.makeText(context, "Recording Started", Toast.LENGTH_SHORT).show();
            }
            else
            {
                RequestPermissions();
            }
        });
        stopbtn.setOnClickListener(v -> {
            stopbtn.setEnabled(false);
            startbtn.setEnabled(true);
            playbtn.setEnabled(true);
            btnSave.setEnabled(true);
            stopplay.setEnabled(false);
            startbtn.setVisibility(View.VISIBLE);
            stopbtn.setVisibility(View.GONE);
            playbtn.setVisibility(View.VISIBLE);
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            Toast.makeText(context, "Recording Stopped", Toast.LENGTH_SHORT).show();
        });
        playbtn.setOnClickListener(v -> {
            stopbtn.setEnabled(false);
            startbtn.setEnabled(true);
            btnSave.setEnabled(true);
            playbtn.setEnabled(false);
            stopplay.setEnabled(true);
            stopplay.setVisibility(View.VISIBLE);
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(mFileName);
                mPlayer.prepare();
                mPlayer.start();
                mPlayer.setOnCompletionListener(mediaPlayer -> playbtn.setEnabled(true));
                Toast.makeText(context, "Recording Started Playing", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
        });
        stopplay.setOnClickListener(v -> {
            if(mPlayer!=null) {
                mPlayer.release();
                mPlayer = null;
                stopbtn.setEnabled(false);
                startbtn.setEnabled(true);
                btnSave.setEnabled(true);
                playbtn.setEnabled(true);
                stopplay.setEnabled(false);
                Toast.makeText(context, "Playing Audio Stopped", Toast.LENGTH_SHORT).show();
            }
        });
        btnSave.setOnClickListener(view -> {
            MultipartBody.Part[] fileParts = new MultipartBody.Part[1];
            Log.d("filePath", "File Path: " + mFileName);
            File file = new File(mFileName);
            MediaType mediaType = MediaType.parse(getMimeType(mFileName));
            RequestBody fileBody = RequestBody.create(mediaType, file);
            fileParts[0] = MultipartBody.Part.createFormData("voiceData", file.getName(), fileBody);
            Log.d("filePath", "File Path: " + fileParts);
            if(ConnectivityChecker.checker(context)){
                Call<String> call = RetrofitClientFile.getInstance().getApi().patientAudioVitalData(
                        SharedPrefManager.getInstance(context).getUser().getAccessToken(),
                        SharedPrefManager.getInstance(context).getUser().getUserid().toString(),
                        fileParts,
                        RequestBody.create(MediaType.parse("text/plain"), SharedPrefManager.getInstance(context).getUser().getUserid().toString()),
                        RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SharedPrefManager.getInstance(context).getPid())),
                        RequestBody.create(MediaType.parse("text/plain"), "problem")
                );
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
                            btnSave.setEnabled(false);
                            popupWindow.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length> 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] ==  PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(context, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    private boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(context, RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void saveActivity() {
        Utils.showRequestDialog(context);
        Log.v("hitApi:", RetrofitClient.BASE_URL + "PatientPhysicalActivity/SavePatientComplain");
         @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject object = new JSONObject();
            object.put("attributeID", attributeLists.get(spnAttrib.getSelectedItemPosition()).getAttributeID());
            object.put("attributeValueID", attributeValueLists.get(spnAttVal.getSelectedItemPosition()).getId());
            array.put(object);
            jsonObject.put("PID", SharedPrefManager.getInstance(context).getPid());
            jsonObject.put("problemID", probId);
            jsonObject.put("subDeptID", SharedPrefManager.getInstance(context).getSubDept().getId());
            jsonObject.put("timeFrom", format.format(today));
            jsonObject.put("timeTo", format.format(toToday));
            jsonObject.put("userID", SharedPrefManager.getInstance(context).getUser().getUserid());
            jsonObject.put("ListPatientComplain", array);
            Log.v("hitApiArr", String.valueOf(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(RetrofitClient.BASE_URL + "PatientPhysicalActivity/SavePatientComplain")
                .addHeaders("accessToken", SharedPrefManager.getInstance(context).getUser().getAccessToken())
                .addHeaders("userID", SharedPrefManager.getInstance(context).getUser().getUserid().toString())
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("resp", String.valueOf(response));
                        Toast.makeText(context, "Problem saved successfully", Toast.LENGTH_SHORT).show();
                        bind();
                        spnAttrib.setSelection(0, true);
                        spnAttVal.setSelection(0, true);
                        Utils.hideDialog();
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
                        bind();
                        spnAttrib.setSelection(0, true);
                        spnAttVal.setSelection(0, true);
                        Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }
                });
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
    private void bind(){
        Utils.showRequestDialog(context);
        Call<ProblemDataResp> call= RetrofitClient.getInstance().getApi().getPatientComplainList(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<ProblemDataResp>() {
            @Override
            public void onResponse(Call<ProblemDataResp> call, Response<ProblemDataResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        rvProblem.setAdapter(new ProbDisplayAdp(context, response.body().getComplainList()));
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ProblemDataResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    public void delete(int complainId, int attribId){
        Utils.showRequestDialog(context);
        Call<ResponseBody> call= RetrofitClient.getInstance().getApi().deletePatientComplain(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), attribId, complainId, SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    bind();
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideDialog();
            }
        });
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtFrmDate) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
                    (view12, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        fromDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        today.setDate(dayOfMonth);
                        today.setMonth(monthOfYear);
                        today.setYear(year - 1900);
                        txtFrmDate.setText(Utils.formatDate(fromDate));
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if (view.getId() == R.id.txtFrmTime) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, i, i1) -> {
                mHour = i;
                mMinute = i1;
                today.setHours(mHour);
                today.setMinutes(mMinute);
                txtFrmTime.setText(format2.format(today));
            }, mHour, mMinute, false);
            timePickerDialog.updateTime(today.getHours(), today.getMinutes());
            timePickerDialog.show();
        } else if (view.getId() == R.id.txtToDate) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        tYear = year;
                        tMonth = monthOfYear;
                        tDay = dayOfMonth;
                        toDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        toToday.setDate(dayOfMonth);
                        toToday.setMonth(monthOfYear);
                        toToday.setYear(year - 1900);
                        txtToDate.setText(Utils.formatDate(toDate));
                    }, tYear, tMonth, tDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(toToday.getTime());
        } else if (view.getId() == R.id.txtToTime) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, i, i1) -> {
                mHour = i;
                mMinute = i1;
                toToday.setHours(mHour);
                toToday.setMinutes(mMinute);
                txtToTime.setText(format2.format(toToday));
            }, mHour, mMinute, false);
            timePickerDialog.updateTime(toToday.getHours(), toToday.getMinutes());
            timePickerDialog.show();
        } else if (view.getId() == R.id.btnSave) {
            try {
                if (!edtProb.getText().toString().isEmpty() || (spnAttrib.getSelectedItemPosition() != 0 && spnAttVal.getSelectedItemPosition() != 0)) {
                    saveActivity();
                }
            } catch (Exception ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if(view.getId()==R.id.txtVoice){
            recordingPopup();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
