package com.his.android.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.his.android.Activity.UploadMultipleImg.UploadImg;
import com.his.android.Activity.ViewDietImage;
import com.his.android.Model.GetMemberId;
import com.his.android.Model.MealList;
import com.his.android.Model.UnitList;
import com.his.android.Model.UnitResponseValue;
import com.his.android.R;
import com.his.android.Response.InsertResponse;
import com.his.android.Response.IntakeData;
import com.his.android.Response.IntekeUnitResp;
import com.his.android.Response.MealResp;
import com.his.android.Response.MemberIdResp;
import com.his.android.Response.UnitResp;
import com.his.android.Response.UniversalResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.InputFilterMinMax;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.RetrofitClientFile;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
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
import static com.his.android.Fragment.InputVital.REQUEST_AUDIO_PERMISSION_CODE;

public class Intake extends Fragment implements View.OnClickListener {
    private AutoCompleteTextView edtMeal;
    private EditText edtQty;
    private Spinner spnUnit;
    private static RecyclerView rvMeal;
    private TextView txtDate,tvViewdoc;
    private TextView txtTime, txtVoice, btnImg;
    private SimpleDateFormat format1;
    SimpleDateFormat format2;
    private MealResp mealResp;
    private UnitResponseValue unitResp;
    static Context context;
    static String date = "";
    int mYear = 0, mMonth = 0, mDay = 0, mHour = 0, mMinute = 0;
    int mYear1 = 0, mMonth1 = 0, mDay1 = 0, mHour1 = 0, mMinute1 = 0;
    Date today = new Date();
//    private int mealId = 0;
    Calendar c;
    Calendar c1;
    private static MealList mealList;
    private List<UnitList> unitLists;
    private ArrayAdapter<MealList> mealListArrayAdapter;
    private ArrayAdapter<UnitList> unitListArrayAdapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intake, container, false);
        edtMeal = view.findViewById(R.id.edtMeal);
        edtQty = view.findViewById(R.id.edtQty);
        spnUnit = view.findViewById(R.id.txtUnit);
        rvMeal = view.findViewById(R.id.rvMeal);
        TextView btnSave = view.findViewById(R.id.btnSave);
        txtDate = view.findViewById(R.id.txtDate);
        txtTime = view.findViewById(R.id.txtTime);
        txtVoice = view.findViewById(R.id.txtVoice);
        btnImg = view.findViewById(R.id.btnImg);
        tvViewdoc = view.findViewById(R.id.tvViewdoc);
        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        txtVoice.setOnClickListener(this);
        btnImg.setOnClickListener(this);
        context = view.getContext();
        Utils.showRequestDialog(context);
        c = Calendar.getInstance();
        mYear=mYear1 = c.get(Calendar.YEAR);
        mMonth=mMonth1 = c.get(Calendar.MONTH);
        mDay=mDay1 = c.get(Calendar.DAY_OF_MONTH);
        date = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
        //txtDate.setText(date);
        edtMeal.setThreshold(0);
        rvMeal.setLayoutManager(new LinearLayoutManager(context));
        txtTime.setText(c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + " " + c.get(Calendar.AM_PM));
        unitLists = new ArrayList<>();
        unitLists.add(0, new UnitList(0, "-Select-"));
        format1 = new SimpleDateFormat("dd/MM/yyyy");
        format2 = new SimpleDateFormat("hh:mm a");
        date = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtDate.setText(Utils.formatDate(date));
        txtTime.setText(format2.format(today));
        bind();
        Call<MemberIdResp> call = RetrofitClient1.getInstance().getApi().getUserProfileByPID("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", String.valueOf(SharedPrefManager.getInstance(context).getPid()));
        call.enqueue(new Callback<MemberIdResp>() {
            @Override
            public void onResponse(Call<MemberIdResp> call, Response<MemberIdResp> response) {
                if (response.body() != null) {
                    MemberIdResp memberIdResp = response.body();
                    if (memberIdResp.getResponseCode() == 1) SharedPrefManager.getInstance(context).setMemberId(memberIdResp.getResponseValue().get(0));
                    else SharedPrefManager.getInstance(context).setMemberId(new GetMemberId(0, 0));
                }
            }

            @Override
            public void onFailure(Call<MemberIdResp> call, Throwable t) {
                Log.v("showError", t.getMessage());
            }
        });
        tvViewdoc.setOnClickListener(view1 -> {
            Intent n = new Intent(context, ViewDietImage.class);
            n.putExtra("meal", "");
            startActivity(n);
        });
        edtMeal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                spnUnit.setAdapter(null);
                if (edtMeal.getText().length() > 0) {
                    //Utils.showRequestDialog(context);
                    mealList = null;
                    edtQty.setText("");
                    edtQty.setEnabled(true);
                    spnUnit.setEnabled(true);
                    Call<MealResp> call = RetrofitClient1.getInstance().getApi().getIntakeListByPrefixText("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", SharedPrefManager.getInstance(context).getMemberId().getMemberId(), edtMeal.getText().toString().trim(), SharedPrefManager.getInstance(context).getMemberId().getUserLoginId());
                    call.enqueue(new Callback<MealResp>() {
                        @Override
                        public void onResponse(Call<MealResp> call, Response<MealResp> response) {
                            if (response.body() != null) {
                                mealResp = response.body();
                                if (mealResp.getResponseCode() == 1) {
                                    mealListArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, mealResp.getResponseValue());
                                    mealListArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);
                                    edtMeal.setAdapter(mealListArrayAdapter);
                                }
                            }
                            //Utils.hideDialog();
                        }

                        @Override
                        public void onFailure(Call<MealResp> call, Throwable t) {
                            //Utils.hideDialog();
                        }
                    });
                } else edtMeal.setAdapter(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtMeal.setOnItemClickListener((adapterView, view1, i, l) -> {
            unitLists.clear();
            unitLists.add(0, new UnitList(0, "-Select-"));
//            mealId = mealResp.getResponseValue().get(i).getIntakeID();
            mealList = mealResp.getResponseValue().get(i);
            bindUnit(mealList);
        });
        btnSave.setOnClickListener(view12 -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
            String dateToStr = format.format(today);
            if (mealList != null) {
                if (mealList.getIntakeID() != 0) {
                    Utils.showRequestDialog(context);
                    Call<InsertResponse> call1;
                    if (mealList.getIsSupplement() == 1) call1 = RetrofitClient1.getInstance().getApi().addSupplementDetails("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", mealList.getIntakeID(), edtQty.getText().toString().trim(), today.getHours() + ":" + today.getMinutes(), unitLists.get(spnUnit.getSelectedItemPosition()).getId().toString(), dateToStr, String.valueOf(SharedPrefManager.getInstance(context).getMemberId().getMemberId()), SharedPrefManager.getInstance(context).getMemberId().getUserLoginId(), SharedPrefManager.getInstance(context).getUser().getUserid(), 1);
                    else call1 = RetrofitClient1.getInstance().getApi().addIntakeDetails("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", mealList.getIntakeID(), edtQty.getText().toString().trim(), today.getHours() + ":" + today.getMinutes(), unitLists.get(spnUnit.getSelectedItemPosition()).getId().toString(), dateToStr, String.valueOf(SharedPrefManager.getInstance(context).getMemberId().getMemberId()), SharedPrefManager.getInstance(context).getMemberId().getUserLoginId(), SharedPrefManager.getInstance(context).getUser().getUserid(), 1);
                    call1.enqueue(new Callback<InsertResponse>() {
                        @Override
                        public void onResponse(Call<InsertResponse> call1, Response<InsertResponse> response) {
                            if (Objects.requireNonNull(response.body()).getResponseCode() == 1) {
                                edtQty.setText("");
                                mealList = null;
                                edtQty.setEnabled(true);
                                spnUnit.setEnabled(true);
                                edtMeal.setText("");
                                bind();
                            }
                            Toast.makeText(context, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                            Utils.hideDialog();
                        }

                        @Override
                        public void onFailure(Call<InsertResponse> call1, Throwable t) {
                            Toast.makeText(context, "Network issue!", Toast.LENGTH_SHORT).show();
                            Utils.hideDialog();
                        }
                    });
                }
            }
        });
        return view;
    }

    private void dateTimePopup(int id, int isSupplement, String intake) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_intake_date_time_change, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        LinearLayout lLayout = popupView.findViewById(R.id.lLayout);
        TextView btnSave = popupView.findViewById(R.id.btnUpdate);
        TextView btnClose = popupView.findViewById(R.id.btnClose);
        TextView txtTitle = popupView.findViewById(R.id.txtTitle);
        TextView txtDate1 = popupView.findViewById(R.id.txtDate1);
        TextView txtTime1 = popupView.findViewById(R.id.txtTime1);
        txtTitle.setText(intake);
        Date today1 = new Date();
        c1 = Calendar.getInstance();
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        lLayout.getLocationOnScreen(location);
        popupWindow.showAtLocation(lLayout, Gravity.CENTER, 0, 0);
        btnClose.setOnClickListener(view -> popupWindow.dismiss());
        txtDate1.setText(Utils.formatDate(mYear1 + "/" + (mMonth1 + 1) + "/" + mDay1));
        txtTime1.setText(format2.format(today1));
        txtTime1.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, i, i1) -> {
                c1.set(Calendar.HOUR_OF_DAY, i);
                c1.set(Calendar.MINUTE, i1);
                today1.setHours(i);
                today1.setMinutes(i1);
                txtTime1.setText(format2.format(today1));
            }, today1.getHours(), today1.getMinutes(), false);
            timePickerDialog.updateTime(today.getHours(), today.getMinutes());
            timePickerDialog.show();
        });
        txtDate1.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        c1.set(Calendar.MONTH, monthOfYear);
                        c1.set(Calendar.YEAR, year);
//                        date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        today1.setDate(dayOfMonth);
                        today1.setMonth(monthOfYear);
                        today1.setYear(year - 1900);
                        txtDate1.setText(Utils.formatDate(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth));
                    }, mYear1, mMonth1, mDay1);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        });
        btnSave.setOnClickListener(view -> updateDateTime(id, (new SimpleDateFormat("dd/MM/yyyy hh:mm a")).format(today1), isSupplement, popupWindow));
    }
    private void updateDateTime(int id, String dateTime, int isSupplement, PopupWindow popupWindow){
        Call<UniversalResp> call = RetrofitClient1.getInstance().getApi().updateIntakeDateTimeByIntakeID("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", id, dateTime, isSupplement, SharedPrefManager.getInstance(context).getMemberId().getMemberId(), SharedPrefManager.getInstance(context).getMemberId().getUserLoginId());
        call.enqueue(new Callback<UniversalResp>() {
            @Override
            public void onResponse(Call<UniversalResp> call, Response<UniversalResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        Toast.makeText(context, response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
                        bind();
                        popupWindow.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<UniversalResp> call, Throwable t) {

            }
        });
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
        txtTitle.setText("Record Input");
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
                mFileName += "/" + timeStamp + "_OutputRecording.mp3";
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
            Log.d("filePath", "File Path: " + fileParts);
            if (ConnectivityChecker.checker(context)) {
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

    private void bindUnit(MealList mealList) {
        Utils.showRequestDialog(context);
        Call<IntekeUnitResp> call = RetrofitClient1.getInstance().getApi().getIntakeUnitByIntakeId("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", mealList.getIntakeID(), mealList.getTextID(), mealList.getIsSupplement(), mealList.getIsSynonym());
        call.enqueue(new Callback<IntekeUnitResp>() {
            @Override
            public void onResponse(Call<IntekeUnitResp> call, Response<IntekeUnitResp> response) {
                if (response.body() != null) {
                    unitResp = response.body().getResponseValue();
                    if (response.body().getResponseCode() == 1) {
                        unitLists.addAll(1, unitResp.getUnitList());
                        unitListArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, unitLists);
                        spnUnit.setAdapter(unitListArrayAdapter);
                        if(mealList.getIsSynonym()==1) {
                            for (int i = 0; i < unitResp.getUnitList().size(); i++) {
                                if (unitLists.get(i+1).getId().equals(unitResp.getDefaultUnitID()))
                                    spnUnit.setSelection(i+1);
                            }
                            edtQty.setText(unitResp.getDefaultQuantity());
                            edtQty.setEnabled(false);
                            spnUnit.setEnabled(false);
                        } else {
                            edtQty.setEnabled(true);
                            spnUnit.setEnabled(true);
                        }
                    }
                }
                /*unitListArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, unitLists);
                spnUnit.setAdapter(unitListArrayAdapter);*/
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<IntekeUnitResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    public void bind() {
        Utils.showRequestDialog(context);
        Call<List<IntakeData>> call = RetrofitClient.getInstance().getApi().getIntakeData(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), (new SimpleDateFormat("dd/MM/yyyy")).format(today)+" 12:00 AM", (new SimpleDateFormat("dd/MM/yyyy")).format(today)+" 11:59 PM", SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<List<IntakeData>>() {
            @Override
            public void onResponse(Call<List<IntakeData>> call, Response<List<IntakeData>> response) {
                if (response.isSuccessful()) {
                      List<IntakeData> foodDetailResp = response.body();
                    if (foodDetailResp != null) {
                        rvMeal.setAdapter(new IntakeAdp(context, foodDetailResp));
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<List<IntakeData>> call, Throwable t) {
                Utils.hideDialog();
            }
        });
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
        } else if (view.getId() == R.id.btnImg) {
            Intent intent=new Intent(context, UploadImg.class);
            intent.putExtra("meal", "");
            startActivity(intent);
        }
    }

    public class IntakeAdp extends RecyclerView.Adapter<IntakeAdp.RecyclerViewHolder> {
        private Context mCtx;
        private List<IntakeData> foodDetails;
        public IntakeAdp(Context mCtx, List<IntakeData> foodDetails) {
            this.mCtx = mCtx;
            this.foodDetails = foodDetails;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_input_meal, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtFluid.setText(String.valueOf(foodDetails.get(i).getIntakeName()));
            holder.txtQty.setText(String.valueOf(foodDetails.get(i).getIntakeQuantity()));
            holder.txtUnit.setText(String.valueOf(foodDetails.get(i).getUnitName()));
            holder.txtDateTime.setText(foodDetails.get(i).getIntakeDateTime());
            holder.txtEditDate.setOnClickListener(view -> dateTimePopup(foodDetails.get(i).getId(), foodDetails.get(i).getIsSupplement(), foodDetails.get(i).getIntakeName()));
            holder.txtEdit.setOnClickListener(view -> {
                holder.txtEdit.setVisibility(View.GONE);
                holder.txtSave.setVisibility(View.VISIBLE);
                holder.edtQty.setVisibility(View.VISIBLE);
                holder.txtPer.setVisibility(View.VISIBLE);
                holder.txtClose.setVisibility(View.VISIBLE);
                holder.edtQty.setEnabled(true);
                holder.edtQty.requestFocus();
                InputMethodManager imm = (InputMethodManager) mCtx.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(holder.edtQty, InputMethodManager.SHOW_IMPLICIT);
            });
            holder.txtClose.setOnClickListener(view -> {
                holder.txtEdit.setVisibility(View.VISIBLE);
                holder.txtClose.setVisibility(View.GONE);
                holder.txtSave.setVisibility(View.GONE);
                holder.edtQty.setVisibility(View.GONE);
                holder.txtPer.setVisibility(View.GONE);
                holder.edtQty.setEnabled(false);
            });
            holder.txtSave.setOnClickListener(view -> {
                JSONArray array=new JSONArray();
                JSONObject object = new JSONObject();
                try {
                    object.put("dietID", foodDetails.get(i).getId());
                    object.put("consumptionPercentage", holder.edtQty.getText().toString().trim());
                    array.put(object);
                    Log.v("array", String.valueOf(array));
                    Utils.showRequestDialog(mCtx);
                    Call<UnitResp> call = null;
                    if (foodDetails.get(i).getIsSupplement()==0)
                    call = RetrofitClient1.getInstance().getApi().UpdateIntakeConsumption("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", array,SharedPrefManager.getInstance(mCtx).getMemberId().getMemberId().toString(), String.valueOf(SharedPrefManager.getInstance(mCtx).getMemberId().getUserLoginId()));
                    else call = RetrofitClient1.getInstance().getApi().UpdateConsumptionPercentage("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", holder.edtQty.getText().toString().trim(), String.valueOf(SharedPrefManager.getInstance(mCtx).getMemberId().getUserLoginId()), SharedPrefManager.getInstance(mCtx).getMemberId().getMemberId().toString(), String.valueOf(SharedPrefManager.getInstance(mCtx).getUser().getUserid()), foodDetails.get(i).getId().toString());
                    call.enqueue(new Callback<UnitResp>() {
                        @Override
                        public void onResponse(Call<UnitResp> call, Response<UnitResp> response) {
                            if (response.isSuccessful()){
                                if (response.body() != null && response.body().getResponseCode() == 1) {
                                    holder.txtEdit.setVisibility(View.VISIBLE);
                                    holder.txtClose.setVisibility(View.GONE);
                                    holder.txtSave.setVisibility(View.GONE);
                                    holder.edtQty.setVisibility(View.GONE);
                                    holder.txtPer.setVisibility(View.GONE);
                                    holder.edtQty.setEnabled(false);
                                    bind();
                                    notifyItemChanged(i);
                                    Utils.hideDialog();
                                } else if (response.body() != null) {
                                    Toast.makeText(mCtx, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UnitResp> call, Throwable t) {
                            Utils.hideDialog();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }

        @Override
        public int getItemCount() {
            return foodDetails.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtFluid,txtQty,txtUnit, txtDateTime, txtEdit, txtSave, txtPer, txtEditDate, txtClose;
            EditText edtQty;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtFluid =itemView.findViewById(R.id.txtFluid);
                txtQty=itemView.findViewById(R.id.txtStr);
                txtUnit=itemView.findViewById(R.id.txtUnit);
                txtDateTime=itemView.findViewById(R.id.txtDateTime);
                txtEdit=itemView.findViewById(R.id.txtEdit);
                edtQty=itemView.findViewById(R.id.edtQty);
                txtSave=itemView.findViewById(R.id.txtSave);
                txtPer=itemView.findViewById(R.id.txtPer);
                txtEditDate=itemView.findViewById(R.id.txtEditDate);
                txtClose=itemView.findViewById(R.id.txtClose);
                edtQty.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 100)});
            }
        }
    }

}
