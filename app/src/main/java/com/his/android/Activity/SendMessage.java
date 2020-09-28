package com.his.android.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.gson.JsonArray;
import com.his.android.Activity.UploadMultipleImg.adapters.MyAdapter;
import com.his.android.Fragment.ObservationGraph;
import com.his.android.Model.RecepientList;
import com.his.android.Model.SubjectList;
import com.his.android.R;
import com.his.android.Response.RecepientListResp;
import com.his.android.Response.SubjectListResp;
import com.his.android.Response.UniversalResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessage extends BaseActivity {
    RecyclerView rvImg;
    MyAdapter myAdapter;
    Options options;
    RecyclerView rvRecipient;
    TextView btnSubmit;
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
        rvRecipient.setLayoutManager(new LinearLayoutManager(mActivity));
        rvRecipient.setNestedScrollingEnabled(true);
        chpRecipient = findViewById(R.id.chpRecipient);
        chpRecipient.setChipHasAvatarIcon(true);
        chpRecipient.setChipDeletable(true);
        chpRecipient.setShowChipDetailed(false);
        subjectList=new ArrayList<>();
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
            JSONArray jsonArray = new JSONArray();
            JSONObject object = null;
            try {
                for (int i = 0; i < chpRecipient.getSelectedChipList().size(); i++) {
                    object = new JSONObject();
                    object.put("id", chpRecipient.getSelectedChipList().get(i).getId());
                    object.put("name", chpRecipient.getSelectedChipList().get(i).getLabel());
                }
                jsonArray.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Call<UniversalResp> call = RetrofitClient.getInstance().getApi().createNewChatMessage(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), SharedPrefManager.getInstance(mActivity).getPid(), subjectList.get(spnSubject.getSelectedItemPosition()).getId(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), jsonArray.toString(), "", 0, "");
            call.enqueue(new Callback<UniversalResp>() {
                @Override
                public void onResponse(Call<UniversalResp> call, Response<UniversalResp> response) {
                    if(response.isSuccessful()){

                    }
                }

                @Override
                public void onFailure(Call<UniversalResp> call, Throwable t) {

                }
            });
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
    
    private void bindRecipient(String text) {
        if(!text.equalsIgnoreCase("")) {
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

    private void bindSubject() {
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
                chpRecipient.addChip(vitalLists.get(i).getId(), (Uri) null, vitalLists.get(i).getName(), "");
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
}