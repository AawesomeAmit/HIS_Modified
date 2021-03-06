package com.his.android.Activity.UploadMultipleImg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.his.android.Activity.UploadMultipleImg.adapters.MyAdapter;
import com.his.android.R;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.view.BaseActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImg extends BaseActivity {
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    Options options;
    ArrayList<String> returnValue = new ArrayList<>();
    Button btnSubmit;

    String dateString;
    Calendar calendar;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_img);
        btnSubmit = findViewById(R.id.btnSubmit);
        context = mActivity;

        btnSubmit.setOnClickListener(view -> {
            try {
                hitUploadDietImg();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        calendar = Calendar.getInstance();

        Date date= Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateString = df.format(date);
        Log.v("dateTime",dateString);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        myAdapter = new MyAdapter(this);
        options = Options.init()
                .setRequestCode(100)
                .setCount(5)
                .setFrontfacing(false)
                .setPreSelectedUrls(returnValue)
                .setExcludeVideos(false)
                .setVideoDurationLimitinSeconds(30)
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                .setPath("/pix/images");
        recyclerView.setAdapter(myAdapter);
        findViewById(R.id.fab).setOnClickListener((View view) -> {
            options.setPreSelectedUrls(returnValue);
            Pix.start(UploadImg.this, options);
        });
        if(getIntent().getStringExtra("meal")!=null){
            options.setPreSelectedUrls(returnValue);
            Pix.start(UploadImg.this, options);
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
                    myAdapter.addImage(returnValue);
                }
            }
            break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(UploadImg.this, options);
                } else {
                    Toast.makeText(UploadImg.this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    private void hitUploadDietImg() {
        Date date= Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
        dateString = df.format(date);
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
        call = iRestInterfaces.UserDateWiseDietImage(
                SharedPrefManager.getInstance(context).getUser().getAccessToken(),
                String.valueOf(SharedPrefManager.getInstance(context).getUser().getUserid()),
                fileParts,
                RequestBody.create(MediaType.parse("text/plain"),  String.valueOf(SharedPrefManager.getInstance(context).getPid())),
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SharedPrefManager.getInstance(context).getUser().getUserid())),
                RequestBody.create(MediaType.parse("text/plain"), dateString)
        );

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
}