package com.his.android.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.his.android.Adapter.RecyclerTouchListener;
import com.his.android.Model.RecepientList;
import com.his.android.Model.SubjectNameListTab;
import com.his.android.R;
import com.his.android.Response.SubjectNameTabResp;
import com.his.android.Utils.ClickListener;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatTitle extends BaseActivity {
    RecyclerView rvChat;
    TextView btnMessage;
    LinearLayoutManager mLinearLayoutManager;
    private List<SubjectNameListTab> subjectNameListTabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_title);
        rvChat=findViewById(R.id.rvChat);
        btnMessage=findViewById(R.id.btnMessage);
        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        rvChat.setLayoutManager(mLinearLayoutManager);
        Utils.showRequestDialog(mActivity);
        btnMessage.setOnClickListener(view -> startActivity(new Intent(mActivity, SendMessage.class).putExtra("type", "new")));
        Call<SubjectNameTabResp> call= RetrofitClient.getInstance().getApi().getSubjectNameforTabsPatientWise(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), SharedPrefManager.getInstance(mActivity).getPid(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<SubjectNameTabResp>() {
            @Override
            public void onResponse(Call<SubjectNameTabResp> call, Response<SubjectNameTabResp> response) {
                if(response.isSuccessful()){
                    subjectNameListTabs=response.body().getSubjectNameListTabs();
                    rvChat.setAdapter(new ChatTitleAdp(subjectNameListTabs));
//                    mLinearLayoutManager.scrollToPosition(subjectNameListTabs.size() - 1);
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<SubjectNameTabResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
        rvChat.addOnItemTouchListener(new RecyclerTouchListener(this, rvChat, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SharedPrefManager.getInstance(mActivity).setChatID(subjectNameListTabs.get(position).getChatMasterId());
                startActivity(new Intent(mActivity, ChatActivity.class).putExtra("title", subjectNameListTabs.get(position).getSubjectName()));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    public class ChatTitleAdp extends RecyclerView.Adapter<ChatTitleAdp.RecyclerViewHolder> {
        List<SubjectNameListTab> vitalLists;

        ChatTitleAdp(List<SubjectNameListTab> vitalLists) {
            this.vitalLists = vitalLists;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.inner_chat_title, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtTitle.setText(String.valueOf(vitalLists.get(i).getSubjectName()));
        }

        @Override
        public int getItemCount() {
            return vitalLists.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtTitle, txtRecipient;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtTitle = itemView.findViewById(R.id.txtTitle);
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mActivity, PatientList.class));
    }
}