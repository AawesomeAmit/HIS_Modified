package com.his.android.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.his.android.Model.ConsultantName;
import com.his.android.Model.ProgressList;
import com.his.android.R;
import com.his.android.Response.ControlBySubDeptResp;
import com.his.android.Response.ViewProgressResp;
import com.his.android.Response.VitalListResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.richeditor.RichEditor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressNoteActivity extends BaseActivity implements View.OnClickListener {
    private TextView txtPulse;
    private TextView txtRR;
    private TextView txtHR;
    private TextView txtWeight;
    private TextView txtHeight;
    private TextView txtSys;
    private TextView txtDys;
    private TextView txtTemp;
    private TextView txtDate;
    private TextView btnUpdate;
    private TextView btnSave;
    TextView txtDrName, txtDept;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private VitalListResp vitalChartList;
    private Spinner spnConsultant;
    Context context;
    //   private EditText edtProgress;
    private String mParam1;
    private String mParam2;
    private int subDeptID;
    List<ConsultantName> consultantNameList;

    RecyclerView recyclerView;

    Calendar c;

    String date;

    int mYear = 0, mMonth = 0, mDay = 0;

    private OnFragmentInteractionListener mListener;

    String detailId, entryDate;

    public ArrayAdapter<ConsultantName> consltantAdp;

    RichEditor richTextEditor;

    String name, pid, ward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_progress_note);

        richTextEditor = findViewById(R.id.richTextEditor);
        richTextEditor.setPlaceholder("Enter Note");

        setupTextEditor();

        Intent intent = getIntent();
        name = intent.getStringExtra("pname");
        pid = intent.getStringExtra("pid");
        ward = intent.getStringExtra("ward");


        txtDrName = findViewById(R.id.txtDrName);
        txtDept = findViewById(R.id.txtDept);
        txtDate = findViewById(R.id.txtDate);
        txtPulse = findViewById(R.id.txtPulse);
        txtSys = findViewById(R.id.txtSys);
        txtDys = findViewById(R.id.txtDys);
        //edtProgress = findViewById(R.id.edtProgress);
        Toolbar toolbar = findViewById(R.id.toolbar);
        spnConsultant = findViewById(R.id.spnConsultant);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        txtRR = findViewById(R.id.txtRR);
        txtHR = findViewById(R.id.txtHR);
        txtTemp = findViewById(R.id.txtTemp);
        context = mActivity;
        txtWeight = findViewById(R.id.txtWeight);
        txtHeight = findViewById(R.id.txtHeight);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setNestedScrollingEnabled(false);

        txtDrName.setText(SharedPrefManager.getInstance(this).getUser().getDisplayName());
        txtDept.setText(SharedPrefManager.getInstance(this).getSubDept().getSubDepartmentName());

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        consultantNameList = new ArrayList<>();

        date = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);

        txtDate.setText((date));
        txtDate.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(mActivity), R.style.DialogTheme,
                    (view2, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        txtDate.setText((date));
                        hitGetProgressHistory(date);
                        //getVitals();
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        });

        getVitals();
        hitGetProgressHistory(date);
        getConsultantName();

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    private void getConsultantName() {
        consultantNameList.add(0, new ConsultantName(0, 0, "Select Consultant", 0));
        Call<ControlBySubDeptResp> call = RetrofitClient.getInstance().getApi().getControlsBySubDept(SharedPrefManager.getInstance(this).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), SharedPrefManager.getInstance(this).getSubDept().getId(), SharedPrefManager.getInstance(this).getHeadID(), SharedPrefManager.getInstance(this).getUser().getUserid());
        call.enqueue(new Callback<ControlBySubDeptResp>() {
            @Override
            public void onResponse(Call<ControlBySubDeptResp> call, Response<ControlBySubDeptResp> response) {
                if (response.isSuccessful()) {
                    consultantNameList.addAll(1, response.body().getConsultantName());
                    SharedPrefManager.getInstance(mActivity).setConsultantList(consultantNameList);
                }
                consltantAdp = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, consultantNameList);
                spnConsultant.setAdapter(consltantAdp);
                spnConsultant.setSelection(0);
            }

            @Override
            public void onFailure(Call<ControlBySubDeptResp> call, Throwable t) {

            }
        });

    }

    private void getVitals() {
        Call<VitalListResp> call = RetrofitClient.getInstance().getApi().getPatientVitalList(
                SharedPrefManager.getInstance(context).getUser().getAccessToken(),
                SharedPrefManager.getInstance(context).getUser().getUserid().toString(),
                SharedPrefManager.getInstance(context).getPid(),
                SharedPrefManager.getInstance(context).getHeadID(),
                SharedPrefManager.getInstance(context).getSubDept().getId(),
                SharedPrefManager.getInstance(context).getUser().getUserid(), 0);
        call.enqueue(new Callback<VitalListResp>() {
            @Override
            public void onResponse(Call<VitalListResp> call, Response<VitalListResp> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        vitalChartList = response.body();
                        if (vitalChartList.getVitalList().size() > 0) {
                            for (int i = vitalChartList.getVitalList().size() - 1; i > (-1); i--) {
                                if (vitalChartList.getVitalList().get(i).getVmID() == 74)
                                    txtHR.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                                if (vitalChartList.getVitalList().get(i).getVmID() == 7)
                                    txtRR.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                                if (vitalChartList.getVitalList().get(i).getVmID() == 3)
                                    txtPulse.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                                if (vitalChartList.getVitalList().get(i).getVmID() == 5)
                                    txtTemp.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                                if (vitalChartList.getVitalList().get(i).getVmID() == 6)
                                    txtDys.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                                if (vitalChartList.getVitalList().get(i).getVmID() == 4)
                                    txtSys.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                                if (vitalChartList.getVitalList().get(i).getVmID() == 1)
                                    txtHeight.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                                if (vitalChartList.getVitalList().get(i).getVmID() == 2)
                                    txtWeight.setText(vitalChartList.getVitalList().get(i).getVitalValue());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<VitalListResp> call, Throwable t) {

            }
        });
    }

    private void saveProgressReport(int drId) {
        Log.v("hitApi:", RetrofitClient.BASE_URL + "Prescription/SaveProgress");
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject object = new JSONObject();
            object.put("detailID", 0);
            object.put("details", richTextEditor.getHtml().trim());
            object.put("pdmID", 60);
            array.put(object);

            jsonObject.put("PID", SharedPrefManager.getInstance(context).getPid());
            jsonObject.put("headID", SharedPrefManager.getInstance(context).getHeadID());
            jsonObject.put("ipNo", SharedPrefManager.getInstance(context).getIpNo());
            jsonObject.put("subDeptID", SharedPrefManager.getInstance(context).getSubDept().getId());
            jsonObject.put("userID", SharedPrefManager.getInstance(context).getUser().getUserid());
            jsonObject.put("consultantName", drId);
            jsonObject.put("patientDetails", array);
            Log.v("hitApiArr", String.valueOf(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!richTextEditor.getHtml().trim().isEmpty()) {
            AndroidNetworking.post(RetrofitClient.BASE_URL + "Prescription/SaveProgress")
                    .addHeaders("accessToken", SharedPrefManager.getInstance(context).getUser().getAccessToken())
                    .addHeaders("userID", SharedPrefManager.getInstance(context).getUser().getUserid().toString())
                    .addJSONObjectBody(jsonObject)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v("hitApiArrRes", response.toString());
                            Toast.makeText(context, "Progress Note saved successfully", Toast.LENGTH_SHORT).show();
                            hitGetProgressHistory(date);
                        }

                        @Override
                        public void onError(ANError error) {
                            if (error.getErrorCode() != 0) {
                                //loader.cancel();
                                Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                                Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                                Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                            } else {
                                Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                            }
                            Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else Toast.makeText(context, "Empty progress note!", Toast.LENGTH_SHORT).show();
    }


    private void updateProgressReport(int drId) {
        Log.v("hitApi:", RetrofitClient.BASE_URL + "Prescription/UpdateProgress");
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject object = new JSONObject();
            object.put("detailID", 0);
            object.put("details", richTextEditor.getHtml().trim());
            object.put("pdmID", 60);
            array.put(object);
            jsonObject.put("id", detailId);
            jsonObject.put("PID", SharedPrefManager.getInstance(context).getPid());
            jsonObject.put("headID", SharedPrefManager.getInstance(context).getHeadID());
            jsonObject.put("subDeptID", SharedPrefManager.getInstance(context).getSubDept().getId());
            jsonObject.put("userID", SharedPrefManager.getInstance(context).getUser().getUserid());
            jsonObject.put("consultantName", drId);
            jsonObject.put("entryDate", entryDate);
            jsonObject.put("patientDetails", array);
            Log.v("hitApiArr", String.valueOf(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!richTextEditor.getHtml().trim().isEmpty()) {
            AndroidNetworking.post(RetrofitClient.BASE_URL + "Prescription/UpdateProgress")
                    .addHeaders("accessToken", SharedPrefManager.getInstance(context).getUser().getAccessToken())
                    .addHeaders("userID", SharedPrefManager.getInstance(context).getUser().getUserid().toString())
                    .addJSONObjectBody(jsonObject)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v("hitApiArrRes", response.toString());
                            Toast.makeText(context, "Progress Note updated successfully", Toast.LENGTH_SHORT).show();
                            hitGetProgressHistory(date);
                        }

                        @Override
                        public void onError(ANError error) {
                            if (error.getErrorCode() != 0) {
                                //loader.cancel();
                                Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                                Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                                Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                            } else {
                                Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                            }
                            Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else Toast.makeText(context, "Empty progress note!", Toast.LENGTH_SHORT).show();
    }


    private void deleteProgressReport() {
        Log.v("hitApi:", RetrofitClient.BASE_URL + "Prescription/DeleteProgress");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", detailId);
            jsonObject.put("PID", SharedPrefManager.getInstance(context).getPid());
            jsonObject.put("ipNo", SharedPrefManager.getInstance(context).getIpNo());
            jsonObject.put("headID", SharedPrefManager.getInstance(context).getHeadID());
            jsonObject.put("subDeptID", SharedPrefManager.getInstance(context).getSubDept().getId());
            jsonObject.put("userID", SharedPrefManager.getInstance(context).getUser().getUserid());

            Log.v("hitApiArr", String.valueOf(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(RetrofitClient.BASE_URL + "Prescription/DeleteProgress")
                .addHeaders("accessToken", SharedPrefManager.getInstance(context).getUser().getAccessToken())
                .addHeaders("userID", SharedPrefManager.getInstance(context).getUser().getUserid().toString())
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("hitApiArrRes", response.toString());
                        Toast.makeText(context, "Progress Note Deleted Successfully", Toast.LENGTH_SHORT).show();
                        hitGetProgressHistory(date);
                    }

                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            //loader.cancel();
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                        Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:

                if (SharedPrefManager.getInstance(context).getUser().getDesigid() == 1) {
                    saveProgressReport(SharedPrefManager.getInstance(context).getUser().getUserid());
                } else if (SharedPrefManager.getInstance(context).getUser().getDesigid() != 1 && spnConsultant.getSelectedItemPosition() != 0) {
                    saveProgressReport(SharedPrefManager.getInstance(context).getConsultantList().get(spnConsultant.getSelectedItemPosition()).getUserid());
                } else
                    Toast.makeText(context, "Consultant name required!", Toast.LENGTH_LONG).show();

                break;
            case R.id.btnUpdate:
                if (SharedPrefManager.getInstance(context).getUser().getDesigid() == 1) {
                    updateProgressReport(SharedPrefManager.getInstance(context).getUser().getUserid());
                } else if (SharedPrefManager.getInstance(context).getUser().getDesigid() != 1 && spnConsultant.getSelectedItemPosition() != 0) {
                    updateProgressReport(SharedPrefManager.getInstance(context).getConsultantList().get(spnConsultant.getSelectedItemPosition()).getUserid());
                } else
                    Toast.makeText(context, "Consultant name required!", Toast.LENGTH_LONG).show();

                break;
        }

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    private void hitGetProgressHistory(String date) {
        Utils.showRequestDialog(mActivity);

        Call<ViewProgressResp> call = RetrofitClient.getInstance().getApi().getProgressHistory(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid(),
                SharedPrefManager.getInstance(mActivity).getPid(),
                SharedPrefManager.getInstance(mActivity).getHeadID(),
                SharedPrefManager.getInstance(mActivity).getIpNo(),
                SharedPrefManager.getInstance(mActivity).getSubDept().getId(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid(),
                date,
                60
        );

        call.enqueue(new Callback<ViewProgressResp>() {
            @Override
            public void onResponse(Call<ViewProgressResp> call, Response<ViewProgressResp> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        recyclerView.setAdapter(new ProgressHistoryAdapter(context, response.body().getProgressList()));
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ViewProgressResp> call, Throwable t) {
                Utils.hideDialog();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public class ProgressHistoryAdapter extends RecyclerView.Adapter<ProgressHistoryAdapter.RecyclerViewHolder> {
        private Context mCtx;
        private List<ProgressList> progressList;
        Spanned progress_note;

        public ProgressHistoryAdapter(Context mCtx, List<ProgressList> progressList) {
            this.mCtx = mCtx;
            this.progressList = progressList;
        }

        @NonNull
        @Override
        public ProgressHistoryAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inflate_progress_note, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProgressHistoryAdapter.RecyclerViewHolder holder, int i) {

            String[] time = progressList.get(i).getTime().split(":");
            holder.tvDate.setText(progressList.get(i).getCreatedDate());
            holder.tvTime.setText(Utils.formatTime(Integer.parseInt(time[0]), Integer.parseInt(time[1])));
            holder.tvConsultant.setText(progressList.get(i).getConsultant());
            holder.details.setText(name + " " + pid + " \n" + ward);

            Log.d(TAG, "onBindViewHolder: " + name);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                progress_note = Html.fromHtml(progressList.get(i).getDetails().trim(), Html.FROM_HTML_MODE_COMPACT);
                holder.tvProgressNote.setText(Html.fromHtml(progressList.get(i).getDetails().trim(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.tvProgressNote.setText(Html.fromHtml(progressList.get(i).getDetails().trim()));
            }

            holder.ivEdit.setOnClickListener(view -> {
                detailId = String.valueOf(progressList.get(i).getId());
                entryDate = progressList.get(i).getCreatedDate() + " " + progressList.get(i).getTime();
                //edtProgress.setText(holder.tvProgressNote.getText().toString().trim());
                richTextEditor.setHtml(progressList.get(i).getDetails().trim());
                btnUpdate.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.GONE);
            });

            holder.ivRemove.setOnClickListener(view -> {
                detailId = String.valueOf(progressList.get(i).getId());
                deleteProgressReport();
            });


            holder.Ivcopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("AppConstant.APP_NAME", progress_note);
                    if (clipboard == null || clip == null) return;
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(mActivity, "copied !!", Toast.LENGTH_SHORT).show();
                }
            });

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    String shareBody = String.valueOf(progress_note + " " + String.valueOf("Patient Name & Age " + name + "Pid " + pid + "Ward " + ward));
                    intent.setType("text/plain");
                    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "progress note");
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(intent, "share"));
                }
            });

        }

        @Override
        public int getItemCount() {
            return progressList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvDate, tvTime, tvProgressNote, tvConsultant, details;

            ImageView ivEdit, ivRemove, Ivcopy, share;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                tvDate = itemView.findViewById(R.id.tvDate);
                tvTime = itemView.findViewById(R.id.tvTime);
                tvProgressNote = itemView.findViewById(R.id.tvProgressNote);
                tvConsultant = itemView.findViewById(R.id.tvConsultant);
                ivEdit = itemView.findViewById(R.id.ivEdit);
                ivRemove = itemView.findViewById(R.id.ivRemove);
                Ivcopy = itemView.findViewById(R.id.copyclipboard);
                share = itemView.findViewById(R.id.share);
                details = itemView.findViewById(R.id.textView55);
            }
        }
    }

    private void setupTextEditor() {

        richTextEditor.setPadding((int) getResources().getDimension(R.dimen._5sdp),
                (int) getResources().getDimension(R.dimen._5sdp),
                (int) getResources().getDimension(R.dimen._5sdp),
                (int) getResources().getDimension(R.dimen._5sdp));

        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.undo();
            }
        });

        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.redo();
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setBold();
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setItalic();
            }
        });

        findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setSubscript();
            }
        });

        findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setSuperscript();
            }
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setStrikeThrough();
            }
        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setUnderline();
            }
        });

        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setHeading(1);
            }
        });

        findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setHeading(2);
            }
        });

        findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setHeading(3);
            }
        });

        findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setHeading(4);
            }
        });

        findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setHeading(5);
            }
        });

        findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setHeading(6);
            }
        });

        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                richTextEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                richTextEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setIndent();
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setOutdent();
            }
        });

        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setAlignLeft();
            }
        });

        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setAlignCenter();
            }
        });

        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setAlignRight();
            }
        });

        findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setBlockquote();
            }
        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setBullets();
            }
        });

        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.setNumbers();
            }
        });

        findViewById(R.id.action_insert_image).setVisibility(View.GONE);
        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                        "dachshund");
            }
        });


        findViewById(R.id.action_insert_link).setVisibility(View.GONE);
        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.insertLink("https://github.com/wasabeef", "wasabeef");
            }
        });
        findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richTextEditor.insertTodo();
            }
        });
    }


}