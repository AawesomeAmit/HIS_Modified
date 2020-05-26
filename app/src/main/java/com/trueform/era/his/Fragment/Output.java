package com.trueform.era.his.Fragment;

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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Adapter.OutputAdp;
import com.trueform.era.his.Model.OutputList;
import com.trueform.era.his.Model.UnitMaster;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.FluidListResp;
import com.trueform.era.his.Response.GetIntakeOuttakeResp;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.RetrofitClientFile;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

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
import static com.trueform.era.his.Fragment.InputVital.REQUEST_AUDIO_PERMISSION_CODE;

public class Output extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    private Spinner spnOutput;
    private EditText edtVal;
    private Spinner spnUnit;
    Context context;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null;
    private TextView txtDate;
    private TextView txtTime, txtVoice;
    private List<OutputList> outputLists;
    private List<UnitMaster> unitList;
    private RecyclerView rvOutput;
    static String date = "", time;
    SimpleDateFormat format2;
    int mYear = 0, mMonth = 0, mDay = 0, mHour=0, mMinute=0;
    Date today = new Date();
    Calendar c;
    private ArrayAdapter<OutputList> outputAdp;
    private ArrayAdapter<UnitMaster> unitAdp;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Output() {
        // Required empty public constructor
    }

    public static Output newInstance(String param1, String param2) {
        Output fragment = new Output();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_output, container, false);
        spnOutput =view.findViewById(R.id.spnOutput);
        edtVal=view.findViewById(R.id.edtQty);
        spnUnit=view.findViewById(R.id.txtUnit);
        TextView btnSave = view.findViewById(R.id.btnSave);
        rvOutput=view.findViewById(R.id.rvOutput);
        context=view.getContext();
        outputLists =new ArrayList<>();
        unitList=new ArrayList<>();
        txtDate=view.findViewById(R.id.txtDate);
        txtTime=view.findViewById(R.id.txtTime);
        txtVoice=view.findViewById(R.id.txtVoice);
        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        txtVoice.setOnClickListener(this);
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        txtDate.setText(date);
        //time=c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + " " + c.get(Calendar.AM_PM);
        //txtTime.setText(time);
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        format2 = new SimpleDateFormat("hh:mm a");
        date = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtDate.setText(Utils.formatDate(date));
        //txtDate.setText(format1.format(today));
        txtTime.setText(format2.format(today));
        rvOutput.setLayoutManager(new LinearLayoutManager(context));
        outputLists.clear();
        unitList.clear();
        outputLists.add(0, new OutputList(0,"-Select-", 0));
        unitList.add(0, new UnitMaster(0,"-Select-"));
        Call<FluidListResp> call= RetrofitClient.getInstance().getApi().intakeOutType(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString());
        call.enqueue(new Callback<FluidListResp>() {
            @Override
            public void onResponse(Call<FluidListResp> call, Response<FluidListResp> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        FluidListResp fluidListResp=response.body();
                        if(fluidListResp.getOutputList().size()>0)
                            outputLists.addAll(1,fluidListResp.getOutputList());
                        if(fluidListResp.getUnitMaster()!=null)
                            if(fluidListResp.getUnitMaster().size()>0)
                                unitList.addAll(1,fluidListResp.getUnitMaster());
                    }
                    outputAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, outputLists);
                    unitAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, unitList);
                    spnOutput.setAdapter(outputAdp);
                    spnUnit.setAdapter(unitAdp);
                }
            }

            @Override
            public void onFailure(Call<FluidListResp> call, Throwable t) {

            }
        });
        bindData();
        btnSave.setOnClickListener(view1 -> {
            if(spnOutput.getSelectedItemPosition()!=0 && spnUnit.getSelectedItemPosition()!=0 && !(edtVal.getText().toString().isEmpty())){
                saveFluid();
            }
        });
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
        txtTitle.setText("Record Output");
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
                        RequestBody.create(MediaType.parse("text/plain"), "intakeOutput")
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

    private void bindData(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatTo = new SimpleDateFormat("dd/MM/yyyy");
        String dateToStr = format.format(today);
        Call<GetIntakeOuttakeResp> call1= RetrofitClient.getInstance().getApi().getIntakeOutputData(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getHeadID(), dateToStr+" 12:00 AM", SharedPrefManager.getInstance(context).getIpNo(), formatTo.format(today)+" 11:59 PM", SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call1.enqueue(new Callback<GetIntakeOuttakeResp>() {
            @Override
            public void onResponse(Call<GetIntakeOuttakeResp> call, Response<GetIntakeOuttakeResp> response) {
                if(response.isSuccessful()){
                    GetIntakeOuttakeResp getIntakeOuttakeResp=response.body();
                    if(getIntakeOuttakeResp!=null){
                        rvOutput.setAdapter(new OutputAdp(context, getIntakeOuttakeResp.getOutputHistory()));
                    }
                }
            }

            @Override
            public void onFailure(Call<GetIntakeOuttakeResp> call, Throwable t) {

            }
        });
    }
    private void saveFluid(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String dateToStr = format.format(today);
        Call<ResponseBody> call= RetrofitClient.getInstance().getApi().saveOutputData(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getHeadID(), SharedPrefManager.getInstance(context).getIpNo(), dateToStr, edtVal.getText().toString().trim(), outputLists.get(spnOutput.getSelectedItemPosition()).getId(), unitList.get(spnUnit.getSelectedItemPosition()).getUnitid(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    edtVal.setText("");
                    Toast.makeText(context, "Saved successfully!", Toast.LENGTH_SHORT).show();
                    bindData();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(context, "Network issue!", Toast.LENGTH_SHORT).show();
            }
        });
    }
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getFragmentManager() != null) {
                getFragmentManager().beginTransaction().detach(this).attach(this).commit();
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
                        today.setDate(dayOfMonth);
                        today.setMonth(monthOfYear);
                        today.setYear(year-1900);
                        txtDate.setText(Utils.formatDate(date));
                        bindData();
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if(view.getId()==R.id.txtTime){
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, i, i1) -> {
                mHour=i;
                mMinute=i1;
                today.setHours(mHour);
                today.setMinutes(mMinute);
                txtTime.setText(format2.format(today));
            },mHour,mMinute,false);
            timePickerDialog.updateTime(today.getHours(), today.getMinutes());
            timePickerDialog.show();
        } else if(view.getId()==R.id.txtVoice){
            recordingPopup();
        }
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}