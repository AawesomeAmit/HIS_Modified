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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trueform.era.his.Activity.MainActivity;
import com.trueform.era.his.Adapter.ShowVitalAdp;
import com.trueform.era.his.Model.VitalChart;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.VitalList;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.RetrofitClientFile;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

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
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class InputVital extends Fragment implements View.OnClickListener {
    private Spinner spnVital;
    private TextView txtUnit;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtVoice;
    private EditText edtVal;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null;
    //ImageView btnAdd;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private VitalList vital;
    private Calendar c;
    private Spinner spnConsultant;
    //static List<VitalList> vitalLists1;
    private SimpleDateFormat format1;
    private SimpleDateFormat format2;
    private static String date = "";
    private Date today = new Date();
    private int mYear = 0, mMonth = 0, mDay = 0, mHour = 0, mMinute = 0;
    private RecyclerView rvVitalDisplay;
    //static RecyclerView rvAddVital;
    private ArrayAdapter<VitalList> adapter;
    private List<VitalList> vitalLists;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InputVital() {
        // Required empty public constructor
    }

    public static InputVital newInstance(String param1, String param2) {
        InputVital fragment = new InputVital();
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

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_vital, container, false);
        spnVital = view.findViewById(R.id.spnVital);
        txtUnit = view.findViewById(R.id.txtUnit);
        txtVoice = view.findViewById(R.id.txtVoice);
        edtVal = view.findViewById(R.id.edtQty);
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        spnConsultant = toolbar.findViewById(R.id.spnConsultant);
        rvVitalDisplay = view.findViewById(R.id.rvVitalDisplay);
        TextView btnSave = view.findViewById(R.id.btnSave);
        txtDate = view.findViewById(R.id.txtDate);
        txtTime = view.findViewById(R.id.txtTime);
        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        txtVoice.setOnClickListener(this);
        context = view.getContext();
        c = Calendar.getInstance();
        Utils.showRequestDialog(context);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
        txtTime.setText(c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + " " + c.get(Calendar.AM_PM));
        format1 = new SimpleDateFormat("dd/MM/yyyy");
        format2 = new SimpleDateFormat("hh:mm a");
        date = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtDate.setText(Utils.formatDate(date));
        txtTime.setText(format2.format(today));
        //txtDate.setText(date);
        //vitalLists1=new ArrayList<>();
        vitalLists = new ArrayList<>();
        rvVitalDisplay.setLayoutManager(new LinearLayoutManager(context));
        //rvAddVital.setLayoutManager(new LinearLayoutManager(context));
        bind();
        /*btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!edtVal.getText().toString().isEmpty())&& spnVital.getSelectedItemPosition()!=0){//(!txtUnit.getText().toString().equalsIgnoreCase("-")) &&
                    vital.setValue(edtVal.getText().toString().trim());
                    vitalLists1.add(vitalLists1.size(), vital);
                    //rvAddVital.setAdapter(new AddVitalAdp(context, vitalLists1));
                    edtVal.setText("");
                    txtUnit.setText("");
                }
            }
        });*/
        vitalLists.add(0, new VitalList(0, "Select", "", ""));
        Call<List<VitalList>> call = RetrofitClient.getInstance().getApi().getVitalMaster(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString());
        call.enqueue(new Callback<List<VitalList>>() {
            @Override
            public void onResponse(Call<List<VitalList>> call, Response<List<VitalList>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        vitalLists.addAll(1, response.body());
                        if (vitalLists != null && vitalLists.size() > 0) {
                            adapter = new ArrayAdapter<>(context, R.layout.spinner_layout, vitalLists);
                            spnVital.setAdapter(adapter);
                        } else spnVital.setAdapter(null);
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<List<VitalList>> call, Throwable t) {
                Utils.hideDialog();
            }
        });
        btnSave.setOnClickListener(view1 -> {
            if (SharedPrefManager.getInstance(context).getUser().getDesigid() == 1) {
                saveVitals(SharedPrefManager.getInstance(context).getUser().getUserid());
            } else if (SharedPrefManager.getInstance(context).getUser().getDesigid() != 1 && spnConsultant.getSelectedItemPosition() != 0) {
                saveVitals(SharedPrefManager.getInstance(context).getConsultantList().get(spnConsultant.getSelectedItemPosition()).getUserid());
            } else Toast.makeText(context, "Consultant name required!", Toast.LENGTH_LONG).show();
        });
        spnVital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    txtUnit.setText(vitalLists.get(i).getUnit());
                    vital = vitalLists.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    private void recordingPopup() {
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
        txtTitle.setText("Record Vital");
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
            if (CheckPermissions()) {
                stopbtn.setEnabled(true);
                startbtn.setEnabled(false);
                playbtn.setEnabled(false);
                btnSave.setEnabled(false);
                stopplay.setEnabled(false);
                startbtn.setVisibility(View.GONE);
                stopbtn.setVisibility(View.VISIBLE);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
                mFileName += "/" + timeStamp + "_VitalRecording.mp3";
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
            } else {
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
            if (mPlayer != null) {
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
            Log.d("filePath", "File Path: " + fileParts);//115741037999
            if (ConnectivityChecker.checker(context)) {
                Call<String> call = RetrofitClientFile.getInstance().getApi().patientAudioVitalData(
                        SharedPrefManager.getInstance(context).getUser().getAccessToken(),
                        SharedPrefManager.getInstance(context).getUser().getUserid().toString(),
                        fileParts,
                        RequestBody.create(MediaType.parse("text/plain"), SharedPrefManager.getInstance(context).getUser().getUserid().toString()),
                        RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SharedPrefManager.getInstance(context).getPid())),
                        RequestBody.create(MediaType.parse("text/plain"), "vital")
                );
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
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
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(context, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Permission Denied", Toast.LENGTH_LONG).show();
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

    public void removeRow(int id) {
        try {
            //vitalLists1.remove(id);
            //rvAddVital.setAdapter(new AddVitalAdp(context, vitalLists1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    private void saveVitals(int drId) {
        Utils.showRequestDialog(context);
        Log.v("hitApi:", "http://182.156.200.179:201/api/Prescription/saveVitals");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject object = new JSONObject();
            object.put("vmID", vitalLists.get(spnVital.getSelectedItemPosition()).getId());
            object.put("vmValue", edtVal.getText().toString().trim());
            array.put(object);
            jsonObject.put("PID", SharedPrefManager.getInstance(context).getPid());
            jsonObject.put("headID", SharedPrefManager.getInstance(context).getHeadID());
            jsonObject.put("entryDate", format.format(today));
            jsonObject.put("subDeptID", SharedPrefManager.getInstance(context).getSubDept().getId());
            jsonObject.put("isFinalDiagnosis", false);
            jsonObject.put("ipNo", SharedPrefManager.getInstance(context).getIpNo());
            jsonObject.put("userID", SharedPrefManager.getInstance(context).getUser().getUserid());
            jsonObject.put("consultantName", drId);
            jsonObject.put("vitals", array);
            Log.v("hitApiArr", String.valueOf(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(RetrofitClient.BASE_URL + "Prescription/saveVitals")
                .addHeaders("accessToken", SharedPrefManager.getInstance(context).getUser().getAccessToken())
                .addHeaders("userID", SharedPrefManager.getInstance(context).getUser().getUserid().toString())
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Vitals saved successfully", Toast.LENGTH_SHORT).show();
                        bind();
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
                        Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }
                });
    }

    private void bind() {
        Utils.showRequestDialog(context);
        Call<List<VitalChart>> call = RetrofitClient.getInstance().getApi().getVitalChart(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), format1.format(today), SharedPrefManager.getInstance(context).getHeadID(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<List<VitalChart>>() {
            @Override
            public void onResponse(Call<List<VitalChart>> call, Response<List<VitalChart>> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).size() > 0) {
                        rvVitalDisplay.setAdapter(new ShowVitalAdp(context, response.body()));
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<List<VitalChart>> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtDate) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        today.setDate(dayOfMonth);
                        today.setMonth(monthOfYear);
                        today.setYear(year - 1900);
                        txtDate.setText(Utils.formatDate(date));
                        bind();
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if (view.getId() == R.id.txtTime) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, i, i1) -> {
                mHour = i;
                mMinute = i1;
                c.set(Calendar.HOUR_OF_DAY, mHour);
                c.set(Calendar.MINUTE, mMinute);
                today.setHours(mHour);
                today.setMinutes(mMinute);
                txtTime.setText(format2.format(today));
            }, mHour, mMinute, false);
            timePickerDialog.updateTime(today.getHours(), today.getMinutes());
            timePickerDialog.show();
        } else if (view.getId() == R.id.txtVoice) {
            recordingPopup();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}