package com.his.android.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.his.android.Model.SubjectList;
import com.his.android.Model.SubjectWiseChatList;
import com.his.android.R;
import com.his.android.Response.ChatFilePath;
import com.his.android.Response.ChatFilesUploaderResp;
import com.his.android.Response.SubjectListResp;
import com.his.android.Response.SubjectWiseChatResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends BaseActivity {
    Spinner spnSubject;
    RecyclerView rvChat;
    private List<SubjectList> subjectList;
    private ArrayAdapter<SubjectList> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        spnSubject = findViewById(R.id.spnSubject);
        rvChat = findViewById(R.id.rvChat);
        rvChat.setLayoutManager(new LinearLayoutManager(mActivity));
        subjectList = new ArrayList<>();
//      subjectList.add(0, new SubjectList(0, "Select Subject"));
        bindSubject();
        spnSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(i!=0){
                    bindChat(subjectList.get(i).getId());
//                }else rvChat.setAdapter(new ChatAdp(new ArrayList<>()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void bindChat(int subID) {
        Utils.showRequestDialog(mActivity);
        Call<SubjectWiseChatResp> call = RetrofitClient.getInstance().getApi().getSubjectWiseChatList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), subID, SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<SubjectWiseChatResp>() {
            @Override
            public void onResponse(Call<SubjectWiseChatResp> call, Response<SubjectWiseChatResp> response) {
                if (response.isSuccessful()) {
                    rvChat.setAdapter(new ChatAdp(response.body().getSubjectWiseChatList()));
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<SubjectWiseChatResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    private void bindSubject() {
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
    }
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
}