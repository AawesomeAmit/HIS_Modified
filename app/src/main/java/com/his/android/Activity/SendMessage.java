package com.his.android.Activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.his.android.Activity.UploadMultipleImg.Api;
import com.his.android.Activity.UploadMultipleImg.ApiUtilsForFile;
import com.his.android.Activity.UploadMultipleImg.adapters.MyAdapter;
import com.his.android.Fragment.ObservationGraph;
import com.his.android.Model.RecepientList;
import com.his.android.Model.SubjectList;
import com.his.android.R;
import com.his.android.Response.ChatFilesUploaderResp;
import com.his.android.Response.RecepientListResp;
import com.his.android.Response.SubjectListResp;
import com.his.android.Response.UniversalResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.RetrofitClientFile;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bsh.util.Util;
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

public class SendMessage extends BaseActivity {
    RecyclerView rvImg;
    MyAdapter myAdapter;
    Options options;
    RecyclerView rvRecipient;
    TextView btnSubmit;
    EditText edtMsg;
    CheckBox chkTimeline;
    private MediaPlayer mPlayer;
    private MediaRecorder mRecorder;
    private static String mFileName = null;
    private List<SubjectList> subjectList;
    private List<RecepientList> recepientList = new ArrayList<>();
    private ArrayList<String> selectedRecipientList = new ArrayList<>();
    private ChipsInput chpRecipient;
    Spinner spnSubject;
    private ArrayAdapter<SubjectList> adapter;
    ArrayList<String> returnValue = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        rvImg = findViewById(R.id.rvImg);
        spnSubject = findViewById(R.id.spnSubject);
        rvRecipient = findViewById(R.id.rvRecipient);
        btnSubmit = findViewById(R.id.btnSubmit);
        edtMsg = findViewById(R.id.edtMsg);
        chkTimeline = findViewById(R.id.chkTimeline);
        rvRecipient.setLayoutManager(new LinearLayoutManager(mActivity));
        rvRecipient.setNestedScrollingEnabled(true);
        chpRecipient = findViewById(R.id.chpRecipient);
        chpRecipient.setChipHasAvatarIcon(true);
        chpRecipient.setChipDeletable(true);
        chpRecipient.setShowChipDetailed(false);
        subjectList = new ArrayList<>();
        subjectList.add(0, new SubjectList(0, "Select Subject"));
        rvImg.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        myAdapter = new MyAdapter(mActivity);
        options = Options.init()
                .setRequestCode(100)
                .setCount(5)
                .setFrontfacing(false)
                .setPreSelectedUrls(returnValue)
                .setExcludeVideos(false)
                .setVideoDurationLimitinSeconds(30)
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                .setPath("/pix/images");
        rvImg.setAdapter(myAdapter);
        findViewById(R.id.fab).setOnClickListener((View view) -> {
            options.setPreSelectedUrls(returnValue);
            Pix.start(SendMessage.this, options);
            rvImg.setVisibility(View.VISIBLE);
        });
        findViewById(R.id.btnRec).setOnClickListener((View view) -> recordingPopup());
        chpRecipient.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chip, int newSize) {
                selectedRecipientList.add(String.valueOf(chip.getId()));
                recepientList.clear();
                RecipientChipAdp RecipientChipAdp = new RecipientChipAdp(recepientList);
                rvRecipient.setAdapter(RecipientChipAdp);
                RecipientChipAdp.notifyDataSetChanged();
            }

            @Override
            public void onChipRemoved(ChipInterface chip, int newSize) {
                selectedRecipientList.remove(chip.getId().toString());
            }

            @Override
            public void onTextChanged(CharSequence text) {
                bindRecipient(String.valueOf(text));
                RecipientChipAdp RecipientChipAdp = new RecipientChipAdp(recepientList);
                rvRecipient.setAdapter(RecipientChipAdp);
                RecipientChipAdp.notifyDataSetChanged();
            }
        });
        bindSubject();
        btnSubmit.setOnClickListener(view -> {
            if (!edtMsg.getText().toString().isEmpty()) {
                if (spnSubject.getSelectedItemPosition() != 0) {
                    if (chpRecipient.getSelectedChipList().size() > 0) {
                        Utils.showRequestDialog(mActivity);
                        JSONArray jsonArray = new JSONArray();
                        JSONObject object;
                        MultipartBody.Part[] fileParts = new MultipartBody.Part[returnValue.size()];
                        MultipartBody.Part[] fileParts1 = new MultipartBody.Part[1];
                        try {
                            for (int i = 0; i < chpRecipient.getSelectedChipList().size(); i++) {
                                object = new JSONObject();
                                object.put("id", chpRecipient.getSelectedChipList().get(i).getId());
                                object.put("name", chpRecipient.getSelectedChipList().get(i).getLabel());
                                object.put("userType", chpRecipient.getSelectedChipList().get(i).getInfo());
                                jsonArray.put(object);
                            }
                            Log.d("rcpt", String.valueOf(jsonArray));
                            for (int i = 0; i < returnValue.size(); i++) {
                                String path = returnValue.get(i);
                                Log.d("filePath", "File Path: " + path);
                                File file = new File(path);
                                MediaType mediaType = MediaType.parse(com.his.android.Activity.UploadMultipleImg.Utils.getMimeType(path));
                                RequestBody fileBody = RequestBody.create(mediaType, file);
                                fileParts[i] = MultipartBody.Part.createFormData("fileName", file.getName(), fileBody);
                                //fileParts = MultipartBody.Part.createFormData("attachedFile", file.getName(), fileBody);
                            }
                            if (mFileName != null) {
                                Log.d("filePath", "File Path: " + mFileName);
                                File file = new File(mFileName);
                                MediaType mediaType = MediaType.parse(getMimeType(mFileName));
                                RequestBody fileBody = RequestBody.create(mediaType, file);
                                fileParts1[0] = MultipartBody.Part.createFormData("fileName", file.getName(), fileBody);
                                //fileParts[fileParts.length] = MultipartBody.Part.createFormData("fileName", file.getName(), fileBody);
                                //Log.d("filePath", "File Path: " + fileParts1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (returnValue.size() > 0 || mFileName != null) {
                            Utils.showRequestDialog(mActivity);
                            Api iRestInterfaces = ApiUtilsForFile.getAPIService();
                            Call<List<ChatFilesUploaderResp>> call = iRestInterfaces.chatBoxFileUploadHandler(fileParts, fileParts1);
                            call.enqueue(new Callback<List<ChatFilesUploaderResp>>() {
                                @Override
                                public void onResponse(Call<List<ChatFilesUploaderResp>> call, Response<List<ChatFilesUploaderResp>> response) {
                                    if (response.isSuccessful()) {
                                        Gson gson = new Gson();
                                        String listString = gson.toJson(response.body(), new TypeToken<ArrayList<ChatFilesUploaderResp>>() {
                                        }.getType());
                                        try {
                                            JSONArray fileJson = new JSONArray(listString);
                                            sendMsg(jsonArray, fileJson);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    Utils.hideDialog();
                                }

                                @Override
                                public void onFailure(Call<List<ChatFilesUploaderResp>> call, Throwable t) {
                                    Toast.makeText(SendMessage.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    Utils.hideDialog();
                                }
                            });
                        } else sendMsg(jsonArray, null);
                    } else Toast.makeText(mActivity, "Please add atleast one recipient!", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(mActivity, "Please select a subject!", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(mActivity, "Please type a message!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
    private void sendMsg(JSONArray jsonArray, JSONArray files) {
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().createNewChatMessage(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), SharedPrefManager.getInstance(mActivity).getPid(), subjectList.get(spnSubject.getSelectedItemPosition()).getId(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), jsonArray, edtMsg.getText().toString().trim(), chkTimeline.isChecked() ? 1 : 0, files);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SendMessage.this, "Message sent successfully!", Toast.LENGTH_SHORT).show();
                    edtMsg.setText("");
                } else {
                    try {
                        Toast.makeText(SendMessage.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Utils.hideDialog();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SendMessage.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Utils.hideDialog();
            }
        });
    }
    private void bindRecipient(String text) {
        if(!text.equalsIgnoreCase("")) {
            Utils.showRequestDialog1(mActivity);
            Call<RecepientListResp> call = RetrofitClient.getInstance().getApi().getDepartmentDesignationUserList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), text);
            call.enqueue(new Callback<RecepientListResp>() {
                @Override
                public void onResponse(Call<RecepientListResp> call, Response<RecepientListResp> response) {
                    if (response.isSuccessful()) {
                        recepientList.clear();
                        if (response.body() != null) {
                            recepientList.addAll(response.body().getRecepientList());
                        }
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<RecepientListResp> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        } else {
            recepientList.clear();
            recepientList.addAll(recepientList);
        }
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
        btnSave.setText(R.string.pix_ok);
        txtTitle.setText("Record Output");
        stopbtn.setEnabled(false);
        playbtn.setEnabled(false);
        stopplay.setEnabled(false);
//        btnSave.setEnabled(false);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        lLayout.getLocationOnScreen(location);
        popupWindow.showAtLocation(lLayout, Gravity.CENTER, 0, 0);
        btnClose.setOnClickListener(view -> {
            stopbtn.setEnabled(false);
            startbtn.setEnabled(true);
            playbtn.setEnabled(true);
            btnSave.setEnabled(true);
            stopplay.setEnabled(false);
            startbtn.setVisibility(View.VISIBLE);
            stopbtn.setVisibility(View.GONE);
            playbtn.setVisibility(View.VISIBLE);
            try {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                Toast.makeText(mActivity, "Recording Stopped", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            popupWindow.dismiss();
        });
        btnSave.setOnClickListener(view -> {
            stopbtn.setEnabled(false);
            startbtn.setEnabled(true);
            playbtn.setEnabled(true);
            btnSave.setEnabled(true);
            stopplay.setEnabled(false);
            startbtn.setVisibility(View.VISIBLE);
            stopbtn.setVisibility(View.GONE);
            playbtn.setVisibility(View.VISIBLE);
            try {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                Toast.makeText(mActivity, "Recording Stopped", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            popupWindow.dismiss();
        });
        if (CheckPermissions()) {
            stopbtn.setEnabled(true);
            startbtn.setEnabled(false);
            playbtn.setEnabled(false);
//            btnSave.setEnabled(false);
            stopplay.setEnabled(false);
            startbtn.setVisibility(View.GONE);
            stopbtn.setVisibility(View.VISIBLE);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFileName += "/" + timeStamp + "_MessageRecording.mp3";
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mRecorder.setOutputFile(mFileName);
            try {
                mRecorder.prepare();
                mRecorder.start();
                Toast.makeText(mActivity, "Recording Started", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.e("mRecorder_start", "prepare() failed");
            }
        } else {
            RequestPermissions();
        }
        startbtn.setOnClickListener(v -> {
            if (CheckPermissions()) {
                stopbtn.setEnabled(true);
                startbtn.setEnabled(false);
                playbtn.setEnabled(false);
//                btnSave.setEnabled(false);
                stopplay.setEnabled(false);
                startbtn.setVisibility(View.GONE);
                stopbtn.setVisibility(View.VISIBLE);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
                mFileName += "/" + timeStamp + "_MessageRecording.mp3";
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                mRecorder.setOutputFile(mFileName);
                try {
                    mRecorder.prepare();
                } catch (IOException e) {
                    Log.e("mRecorder_start", "prepare() failed");
                }
                mRecorder.start();
                Toast.makeText(mActivity, "Recording Started", Toast.LENGTH_SHORT).show();
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
            try {
                mRecorder.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mRecorder.release();
            mRecorder = null;
            Toast.makeText(mActivity, "Recording Stopped", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(mActivity, "Recording Started Playing", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.e("prepare", "prepare() failed");
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
                Toast.makeText(mActivity, "Playing Audio Stopped", Toast.LENGTH_SHORT).show();
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
    private boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(mActivity, WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(mActivity, RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(mActivity, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }
    private void bindSubject() {
        Utils.showRequestDialog(mActivity);
        Call<SubjectListResp> call = RetrofitClient.getInstance().getApi().getSubjectList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<SubjectListResp>() {
            @Override
            public void onResponse(Call<SubjectListResp> call, Response<SubjectListResp> response) {
                if (response.isSuccessful()) {
                    subjectList.addAll(1, response.body().getSubjectList());
                }
                adapter = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, subjectList);
                spnSubject.setAdapter(adapter);
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<SubjectListResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(SendMessage.this, options);
                } else {
                    Toast.makeText(SendMessage.this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public class RecipientChipAdp extends RecyclerView.Adapter<RecipientChipAdp.RecyclerViewHolder> {
        List<RecepientList> vitalLists;

        RecipientChipAdp(List<RecepientList> vitalLists) {
            this.vitalLists = vitalLists;
        }

        @NonNull
        @Override
        public RecipientChipAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecipientChipAdp.RecyclerViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.inner_vital_chip_recycler, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecipientChipAdp.RecyclerViewHolder holder, int i) {
            holder.txtVital.setText(String.valueOf(vitalLists.get(i).getName()));
        }

        @Override
        public void onBindViewHolder(@NonNull RecipientChipAdp.RecyclerViewHolder holder, final int i, @NonNull List<Object> payloads) {
            holder.txtVital.setText(String.valueOf(vitalLists.get(i).getName()));
            holder.txtVital.setOnClickListener(view -> {
                chpRecipient.addChip(vitalLists.get(i).getId(), (Uri) null, vitalLists.get(i).getName(), vitalLists.get(i).getUserType());
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return vitalLists.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtVital;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtVital = itemView.findViewById(R.id.txtVital);
            }
        }
    }
/*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(mActivity, ChatActivity.class);
        finish();
        moveTaskToBack(true);
        startActivity(intent);
    }*/
}