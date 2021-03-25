package com.his.android.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.his.android.Activity.UploadMultipleImg.Api;
import com.his.android.Activity.UploadMultipleImg.ApiUtilsForFile;
import com.his.android.Activity.UploadMultipleImg.Utils;
import com.his.android.Activity.UploadMultipleImg.adapters.MyAdapter;
import com.his.android.R;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.view.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.zelory.compressor.FileUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadDocument extends BaseActivity {
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    Options options;
    ArrayList<String> returnValue = new ArrayList<>();
    Button btnSubmit;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    String dateString, documenttypeId;
    Calendar calendar;
    Context context;
    EditText DateET, TimeET, consentimage;
    ImageView date, time;
    String[] document = {"Select Document Type", "Patient Image", "Patient Activity Image", "ECG Report", "Patient Video", "Patient Consent"};

    int year;
    int month;
    int dayOfMonth;


    int hour;
    int minute;
    int ss;

    String ConsentImage = null;
    Calendar dateSelected = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);
        btnSubmit = findViewById(R.id.btnSubmit);
        Spinner niceSpinner = findViewById(R.id.nice_spinner);
        DateET = findViewById(R.id.editTextDate);
        TimeET = findViewById(R.id.editTextTime);


        consentimage = findViewById(R.id.consentImageET);


        context = mActivity;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateET.setText(dateFormat.format(new Date())); // it will show 16/07/2013


        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTime = sdf.format(new Date());
        TimeET.setText(currentTime);


        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, document) {


            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        };


        aa.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        //Setting the ArrayAdapter data on the Spinner
        niceSpinner.setAdapter(aa);

        niceSpinner.setSelection(5);


        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        DateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Datedialog();

            }
        });


        TimeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog();
            }
        });


        btnSubmit.setOnClickListener(view ->

        {

            dateString = DateET.getText() + "  " + TimeET.getText();
            documenttypeId = String.valueOf(niceSpinner.getSelectedItemId());

            try {


                if (TextUtils.isEmpty(niceSpinner.getSelectedItem().toString().trim())) {
                    Toast.makeText(context, "Select Document type First", Toast.LENGTH_SHORT).show();
                    niceSpinner.setFocusable(View.FOCUSABLE);
                } else if (TextUtils.isEmpty(DateET.getText())) {
                    DateET.setError("Select Date");
                    Toast.makeText(context, "Select Date Firt", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(TimeET.getText())) {
                    TimeET.setError("Select Time First");
                    Toast.makeText(context, "Select Time First", Toast.LENGTH_SHORT).show();

                } else {
                    hitUploadDietImg();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        myAdapter = new MyAdapter(this);

        options = Options.init().setRequestCode(100).setCount(5)
                .

                        setFrontfacing(false)
                .

                        setPreSelectedUrls(returnValue)
                .

                        setExcludeVideos(false)
                .

                        setVideoDurationLimitinSeconds(30)
                .

                        setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                .

                        setPath("/pix/images");
        recyclerView.setAdapter(myAdapter);

        findViewById(R.id.fab).

                setOnClickListener(this::onClick);
        if (

                getIntent().

                        getStringExtra("meal") != null) {


            options.setPreSelectedUrls(returnValue);
            Pix.start(UploadDocument.this, options);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("val", "requestCode ->  " + requestCode+"  resultCode "+resultCode);
        switch (requestCode) {
            case (100): {
                if (resultCode == Activity.RESULT_OK) {
                    returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);

                    Log.d(TAG, "onActivityResult: " + returnValue);
                    myAdapter.addImage(returnValue);

                }
            }
            break;
            case 200: {
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    Log.d(TAG, "onActivityResult: " + data.getData().getPath());


                    returnValue.add(data.getData().getPath());
                    Log.d(TAG, "onActivityResult: " + returnValue.get(0));
                    myAdapter.addImage(returnValue);

//                    File file = null;
//                    try {
//                        file = FileUtil.from(this, uri);
//
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }


                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(UploadDocument.this, options);
                } else {
                    Toast.makeText(UploadDocument.this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void hitUploadDietImg() {


        MultipartBody.Part[] fileParts = new MultipartBody.Part[returnValue.size()];
        try {
            for (int i = 0; i < returnValue.size(); i++) {
                String path = returnValue.get(i);
                Log.d("filePath", "File Path: " + path);
                File file = new File(path);
                MediaType mediaType = MediaType.parse(Utils.getMimeType(path));
                RequestBody fileBody = RequestBody.create(mediaType, file);
                fileParts[i] = MultipartBody.Part.createFormData("fileName", file.getName(), fileBody);
//                fileParts = MultipartBody.Part.createFormData("attachedFile", file.getName(), fileBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Utils.showRequestDialog(mActivity);
        Api iRestInterfaces = ApiUtilsForFile.getAPIService();
        Call<String> call;
        call = iRestInterfaces.uploaddocument(
                SharedPrefManager.getInstance(context).getUser().getAccessToken(),
                String.valueOf(SharedPrefManager.getInstance(context).getUser().getUserid()),
                fileParts,
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SharedPrefManager.getInstance(context).getPid())),
                consentimage.getText().toString(),
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SharedPrefManager.getInstance(context).getUser().getUserid())),
                documenttypeId,
                RequestBody.create(MediaType.parse("text/plain"), dateString)
        );

        Log.d(TAG, "hitUploadDietImg: " + SharedPrefManager.getInstance(context).getPid());


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    finish();
                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(getApplicationContext(), "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Utils.hideDialog();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void TimeDialog() {

        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        timePickerDialog = new TimePickerDialog(UploadDocument.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                TimeET.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }


    public void Datedialog() {

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(UploadDocument.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        DateET.setText(year + "-" + (month + 1) + "-" + day);
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void onClick(View view) {
//
        ImagePicker.Companion.with(this)
                .crop(4f, 6f)//Crop image(Optional), Check Customization for more option
                .compress(512)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start(200);

//
//        options.setPreSelectedUrls(returnValue);
//
//        Log.d(TAG, "onClick:" + options);
//        Pix.start(UploadDocument.this, options);
    }
}

