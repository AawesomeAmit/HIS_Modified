package com.his.android.Activity;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.his.android.Activity.UploadMultipleImg.Api;
import com.his.android.Activity.UploadMultipleImg.ApiUtilsForFile;
import com.his.android.Activity.UploadMultipleImg.adapters.MyAdapter;
import com.his.android.Model.RecepientList;
import com.his.android.Model.SubjectList;
import com.his.android.Model.SubjectWiseChatList;
import com.his.android.R;
import com.his.android.Response.ChatFilePath;
import com.his.android.Response.ChatFilesUploaderResp;
import com.his.android.Response.RecepientListResp;
import com.his.android.Response.SubjectListResp;
import com.his.android.Response.SubjectWiseChatResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import uk.co.senab.photoview.PhotoViewAttacher;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.his.android.Fragment.InputVital.REQUEST_AUDIO_PERMISSION_CODE;

public class ChatActivity extends BaseActivity {
    TextView txtRecipient;
    RecyclerView rvChat;
    private List<SubjectList> subjectList;
//    private ArrayAdapter<SubjectList> adapter;
    TextView btnReply, txtTitle;
    MyAdapter myAdapter;
    LinearLayoutManager mLinearLayoutManager;
    ArrayList<String> returnValue = new ArrayList<>();
    PhotoViewAttacher photoViewAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        txtRecipient = findViewById(R.id.txtRecipient);
        rvChat = findViewById(R.id.rvChat);
        txtTitle = findViewById(R.id.txtTitle);
        btnReply = findViewById(R.id.btnReply);
        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        rvChat.setLayoutManager(mLinearLayoutManager);
        subjectList = new ArrayList<>();
        if(getIntent().getStringExtra("pid")!=null) {
            SharedPrefManager.getInstance(getApplicationContext()).setPid(Integer.parseInt(getIntent().getStringExtra("pid")));
            SharedPrefManager.getInstance(getApplicationContext()).setChatID(Integer.parseInt(getIntent().getStringExtra("chatId")));
        }
        btnReply.setOnClickListener(view -> startActivity(new Intent(mActivity, SendMessage.class).putExtra("type", "reply")));
        bindChat(SharedPrefManager.getInstance(mActivity).getChatID());
    }

    private void bindChat(int subID) {
        Utils.showRequestDialog(mActivity);
        Call<SubjectWiseChatResp> call = RetrofitClient.getInstance().getApi().getSubjectWiseChatList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), subID, SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()));
        call.enqueue(new Callback<SubjectWiseChatResp>() {
            @Override
            public void onResponse(Call<SubjectWiseChatResp> call, Response<SubjectWiseChatResp> response) {
                if (response.isSuccessful()) {
                    if(response.body().getSubjectWiseChatList().size()>0)
                    txtTitle.setText(response.body().getSubjectWiseChatList().get(0).getSubjectName());
                    rvChat.setAdapter(new ChatAdp(response.body().getSubjectWiseChatList()));
                    mLinearLayoutManager.scrollToPosition(response.body().getSubjectWiseChatList().size() - 1);
                    txtRecipient.setText(response.body().getSubjectWiseRecipientList().get(0).getRecipientName());
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<SubjectWiseChatResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    /*private void messagingPopup() {
        View popupView = getLayoutInflater().inflate(R.layout.activity_send_message, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        LinearLayout lLayout = popupView.findViewById(R.id.lLayout);
        RecyclerView rvImg;
        RecyclerView rvRecipient;
        TextView btnSubmit;
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        lLayout.getLocationOnScreen(location);
        popupWindow.showAtLocation(lLayout, Gravity.CENTER, 0, 0);


        rvImg = popupView.findViewById(R.id.rvImg);
        spnSubject = popupView.findViewById(R.id.spnSubject);
        rvRecipient = popupView.findViewById(R.id.rvRecipient);
        btnSubmit = popupView.findViewById(R.id.btnSubmit);
        edtMsg = popupView.findViewById(R.id.edtMsg);
        rvRecipient.setLayoutManager(new LinearLayoutManager(mActivity));
        rvRecipient.setNestedScrollingEnabled(true);
        chpRecipient = popupView.findViewById(R.id.chpRecipient);
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
        popupView.findViewById(R.id.fab).setOnClickListener((View view) -> {
            options.setPreSelectedUrls(returnValue);
            Pix.start(this, options);
            rvImg.setVisibility(View.VISIBLE);
        });
        popupView.findViewById(R.id.btnRec).setOnClickListener((View view) -> recordingPopup());
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
                                }

                                @Override
                                public void onFailure(Call<List<ChatFilesUploaderResp>> call, Throwable t) {
                                    Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else sendMsg(jsonArray, null);
                    } else Toast.makeText(mActivity, "Please add atleast one recipient!", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(mActivity, "Please select a subject!", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(mActivity, "Please type a message!", Toast.LENGTH_SHORT).show();
        });

        btnClose.setOnClickListener(view -> {
            popupWindow.dismiss();
        });
    }*/
    private void zoomPopup(String path, String fileType) {
        Log.v("vdoPath", path);
        View popupView = getLayoutInflater().inflate(R.layout.popup_chat_img, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        ConstraintLayout lLayout = popupView.findViewById(R.id.lLayout);
        TextView btnClose = popupView.findViewById(R.id.btnClose);
        ImageView imgChat = popupView.findViewById(R.id.imgChat);
        VideoView videoView = popupView.findViewById(R.id.vView);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        lLayout.getLocationOnScreen(location);
        photoViewAttacher = new PhotoViewAttacher(imgChat);
        photoViewAttacher.update();
        popupWindow.showAtLocation(lLayout, Gravity.CENTER, 0, 0);
        btnClose.setOnClickListener(view -> popupWindow.dismiss());
        if (fileType.equalsIgnoreCase("image/jpeg")) {
            Picasso.with(mActivity).load(path).resize((int) getResources().getDimension(R.dimen._294sdp), (int) getResources().getDimension(R.dimen._180sdp)).into(imgChat);
            videoView.setVisibility(View.GONE);
            imgChat.setVisibility(View.VISIBLE);
        } else {
            videoView.setVisibility(View.VISIBLE);
            imgChat.setVisibility(View.GONE);
            MediaController mediaController = new MediaController(this);
            Uri uri = Uri.parse(path);
            videoView.setVideoURI(uri);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            videoView.setOnInfoListener((mediaPlayer, i, i1) -> {
                if (i == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    Utils.hideDialog();
                }
                return false;
            });
            videoView.setOnPreparedListener(mp -> {
                Utils.hideDialog();
                Log.v("finished", "finished");
                videoView.start();
            });
        }
    }
    /*private void bindSubject() {
        Utils.showRequestDialog(mActivity);
        Call<SubjectListResp> call = RetrofitClient.getInstance().getApi().getSubjectList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<SubjectListResp>() {
            @Override
            public void onResponse(Call<SubjectListResp> call, Response<SubjectListResp> response) {
                if (response.isSuccessful()) {
                    subjectList.addAll(0, response.body().getSubjectList());
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
    }*/
    public class ChatAdp extends RecyclerView.Adapter<ChatAdp.RecyclerViewHolder> {
        private List<SubjectWiseChatList> subjectWiseChatLists;

        public ChatAdp(List<SubjectWiseChatList> subjectWiseChatLists) {
            this.subjectWiseChatLists = subjectWiseChatLists;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_chat, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<ChatFilePath>>(){}.getType();
//                String li stString = gson.toJson(subjectWiseChatLists.get(i).getFilePath(), new TypeToken<ArrayList<ChatFilePath>>() {}.getType());
            List<ChatFilePath> filePathList = gson.fromJson(subjectWiseChatLists.get(i).getFilePath(), type);
                if(subjectWiseChatLists.get(i).getSide().equalsIgnoreCase("left")) {
                holder.txtMsgLeft.setText(subjectWiseChatLists.get(i).getChatMessage());
                holder.txtLeftDate.setText(subjectWiseChatLists.get(i).getCreatedDate());
                holder.txtLeftSender.setText(subjectWiseChatLists.get(i).getUserList());
                if(filePathList!=null && filePathList.size()>0)
                holder.rvLeftImg.setAdapter(new ChatImgAdp(filePathList));
                holder.llRight.setVisibility(View.GONE);
            }
            else {
                holder.txtMsgRight.setText(String.valueOf(subjectWiseChatLists.get(i).getChatMessage()));
                holder.txtRightDate.setText(subjectWiseChatLists.get(i).getCreatedDate());
                holder.txtRightSender.setText(subjectWiseChatLists.get(i).getUserList());
                    if(filePathList!=null && filePathList.size()>0)
                holder.rvRightImg.setAdapter(new ChatImgAdp(filePathList));
                holder.llLeft.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return subjectWiseChatLists.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtMsgLeft, txtMsgRight, txtLeftDate, txtLeftSender, txtRightDate, txtRightSender;
            LinearLayout llLeft, llRight;
            RecyclerView rvLeftImg, rvRightImg;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtMsgLeft = itemView.findViewById(R.id.txtMsgLeft);
                txtMsgRight = itemView.findViewById(R.id.txtMsgRight);
                llLeft = itemView.findViewById(R.id.llLeft);
                llRight = itemView.findViewById(R.id.llRight);
                txtLeftDate = itemView.findViewById(R.id.txtLeftDate);
                txtLeftSender = itemView.findViewById(R.id.txtLeftSender);
                txtRightDate = itemView.findViewById(R.id.txtRightDate);
                txtRightSender = itemView.findViewById(R.id.txtRightSender);
                rvLeftImg = itemView.findViewById(R.id.rvLeftImg);
                rvRightImg = itemView.findViewById(R.id.rvRightImg);
                rvLeftImg.setLayoutManager(new LinearLayoutManager(mActivity));
                rvRightImg.setLayoutManager(new LinearLayoutManager(mActivity));
            }
        }
    }
    public class ChatImgAdp extends RecyclerView.Adapter<ChatImgAdp.RecyclerViewHolder> {
        private List<ChatFilePath> filePathList;

        public ChatImgAdp(List<ChatFilePath> filePathList) {
            this.filePathList = filePathList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_chat_image, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            if(filePathList.get(i).getFileType().equalsIgnoreCase("image/jpeg"))
            Picasso.with(mActivity).load(filePathList.get(i).getFilePath()).resize((int) getResources().getDimension(R.dimen._294sdp), (int) getResources().getDimension(R.dimen._180sdp)).into(holder.imgChat);
//            else if(filePathList.get(i).getFileType().equalsIgnoreCase("image/jpeg"))
            holder.imgChat.setOnClickListener(view -> {
                zoomPopup(filePathList.get(i).getFilePath(), filePathList.get(i).getFileType());
            });
        }

        @Override
        public int getItemCount() {
            return filePathList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            ImageView imgChat;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                imgChat = itemView.findViewById(R.id.imgChat);
            }
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mActivity, ChatTitle.class));
    }
}