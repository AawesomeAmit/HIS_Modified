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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
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

import com.trueform.era.his.Adapter.MealIntakeAdp;
import com.trueform.era.his.Model.MealList;
import com.trueform.era.his.Model.UnitList;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.FoodDetailResp;
import com.trueform.era.his.Response.InsertResponse;
import com.trueform.era.his.Response.MealResp;
import com.trueform.era.his.Response.UnitResp;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.RetrofitClient1;
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
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.trueform.era.his.Fragment.InputVital.REQUEST_AUDIO_PERMISSION_CODE;

public class InputMeal extends Fragment implements View.OnClickListener {
    private AutoCompleteTextView edtMeal;
    private EditText edtQty;
    private Spinner spnUnit;
    private RecyclerView rvMeal;
    private TextView txtDate;
    private TextView txtTime, txtVoice;
    private SimpleDateFormat format1;
    SimpleDateFormat format2;
    private MealResp mealResp;
    private UnitResp unitResp;
    Context context;
    static String date = "";
    int mYear = 0, mMonth = 0, mDay = 0, mHour=0, mMinute=0;
    Date today = new Date();
    private int mealId=0;
    Calendar c;
    private List<UnitList> unitLists;
    private ArrayAdapter<MealList> mealListArrayAdapter;
    private ArrayAdapter<UnitList> unitListArrayAdapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InputMeal() {
        // Required empty public constructor
    }
    public static InputMeal newInstance(String param1, String param2) {
        InputMeal fragment = new InputMeal();
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
        View view=inflater.inflate(R.layout.fragment_input_meal, container, false);
        edtMeal=view.findViewById(R.id.edtMeal);
        edtQty=view.findViewById(R.id.edtQty);
        spnUnit=view.findViewById(R.id.txtUnit);
        rvMeal=view.findViewById(R.id.rvMeal);
        TextView btnSave = view.findViewById(R.id.btnSave);
        txtDate=view.findViewById(R.id.txtDate);
        txtTime=view.findViewById(R.id.txtTime);
        txtVoice=view.findViewById(R.id.txtVoice);
        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        txtVoice.setOnClickListener(this);
        context=view.getContext();
        Utils.showRequestDialog(context);
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR);
        txtDate.setText(date);
        rvMeal.setLayoutManager(new LinearLayoutManager(context));
        txtTime.setText(c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + " " + c.get(Calendar.AM_PM));
        unitLists=new ArrayList<>();
        format1 = new SimpleDateFormat("dd/MM/yyyy");
        format2 = new SimpleDateFormat("hh:mm a");
        date = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtDate.setText(Utils.formatDate(date));
        txtTime.setText(format2.format(today));
        bind();
        edtMeal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                spnUnit.setAdapter(null);
                if (edtMeal.getText().length() > 1) {
                    //Utils.showRequestDialog(context);
                    Call<MealResp> call = RetrofitClient1.getInstance().getApi().getFoodListByPrefixText("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", SharedPrefManager.getInstance(context).getMemberId().getMemberId(), edtMeal.getText().toString().trim(), SharedPrefManager.getInstance(context).getMemberId().getUserLoginId());
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
            unitLists.add(0, new UnitList(0,"-Select-"));
            mealId=mealResp.getResponseValue().get(i).getId();
            bindUnit(mealId);
        });

        btnSave.setOnClickListener(view12 -> {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateToStr = format.format(today);
            Utils.showRequestDialog(context);
            Call<InsertResponse> call= RetrofitClient1.getInstance().getApi().addIntakeDetails("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", mealId, edtQty.getText().toString().trim(), today.getHours() + ":" + today.getMinutes(), unitLists.get(spnUnit.getSelectedItemPosition()).getId().toString(), dateToStr, String.valueOf(SharedPrefManager.getInstance(context).getMemberId().getMemberId()), SharedPrefManager.getInstance(context).getMemberId().getUserLoginId());
            call.enqueue(new Callback<InsertResponse>() {
                @Override
                public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                    if(Objects.requireNonNull(response.body()).getResponseCode()==1){
                        edtQty.setText("");
                        bind();
                        Toast.makeText(context, "Saved successfully!", Toast.LENGTH_SHORT).show();
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<InsertResponse> call, Throwable t) {
                    Toast.makeText(context, "Network issue!", Toast.LENGTH_SHORT).show();
                    Utils.hideDialog();
                }
            });
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

    private void bindUnit(int foodId){
        Utils.showRequestDialog(context);
        Call<UnitResp> call = RetrofitClient1.getInstance().getApi().getFoodUnitByFoodId("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", foodId);
        call.enqueue(new Callback<UnitResp>() {
            @Override
            public void onResponse(Call<UnitResp> call, Response<UnitResp> response) {
                if(response.body()!=null){
                    unitResp=response.body();
                    if(unitResp.getResponseCode()==1){
                        unitLists.addAll(1,  unitResp.getResponseValue());
                    }
                }
                unitListArrayAdapter=new ArrayAdapter<>(context, R.layout.spinner_layout, unitLists);
                spnUnit.setAdapter(unitListArrayAdapter);
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<UnitResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    private void bind(){
        Utils.showRequestDialog(context);
        Call<FoodDetailResp> call= RetrofitClient.getInstance().getApi().getIntakeFoodData(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<FoodDetailResp>() {
            @Override
            public void onResponse(Call<FoodDetailResp> call, Response<FoodDetailResp> response) {
                if(response.isSuccessful()) {
                    FoodDetailResp foodDetailResp=response.body();
                    if ((foodDetailResp != null ? foodDetailResp.getFoodDetail().size() : 0) >0) {
                        rvMeal.setAdapter(new MealIntakeAdp(context, foodDetailResp.getFoodDetail()));
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<FoodDetailResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
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
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                            today.setDate(dayOfMonth);
                            today.setMonth(monthOfYear);
                            today.setYear(year-1900);
                            txtDate.setText(Utils.formatDate(date));
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if(view.getId()==R.id.txtTime){
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, i, i1) -> {
                mHour=i;
                mMinute=i1;
                c.set(Calendar.HOUR_OF_DAY, mHour);
                c.set(Calendar.MINUTE, mMinute);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
