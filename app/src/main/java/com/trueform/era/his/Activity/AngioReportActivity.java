package com.trueform.era.his.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Model.AngioReportList;
import com.trueform.era.his.Model.ConsultantName;
import com.trueform.era.his.Model.ProgressList;
import com.trueform.era.his.Model.TestListAngio;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.AngioPatientDetailResp;
import com.trueform.era.his.Response.AngioReportResp;
import com.trueform.era.his.Response.AngioplastyResp;
import com.trueform.era.his.Response.ControlBySubDeptResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;
import com.trueform.era.his.view.BaseActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.richeditor.RichEditor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AngioReportActivity extends BaseActivity implements View.OnClickListener {
    private TextView txtDate, tvBillNo, tvPID, tvCrNo, tvIPNo, tvName, tvAgeGender, tvPatientType, tvBillDate;
    private TextView btnUpdate;
    private TextView btnSave;
    TextView txtDrName, txtDept;
    private Spinner spnConsultant;
    Context context;
    String test="";
    EditText edtCathId, edtImpression;
    Spinner spnTest;
    List<ConsultantName> consultantNameList;
    RecyclerView recyclerView;

    Calendar c;

    String date;

    int mYear = 0, mMonth = 0, mDay = 0;
    List<TestListAngio> testListAngio;
    ArrayAdapter<TestListAngio> testListAngioAdp;
    String detailId, entryDate;
    public ArrayAdapter<ConsultantName> consltantAdp;
    EditText etSearchBillNo;
    int pdmID = 93;

    LinearLayout llProcedureNote;

    RichEditor richTextEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angio_report);

        richTextEditor = findViewById(R.id.richTextEditor);
        richTextEditor.setPlaceholder("Enter Report");

        setupTextEditor();

        txtDrName = findViewById(R.id.txtDrName);
        tvBillDate = findViewById(R.id.tvBillDate);
        tvBillNo = findViewById(R.id.tvBillNo);
        tvPatientType = findViewById(R.id.tvPatientType);
        tvCrNo = findViewById(R.id.tvCrNo);
        tvAgeGender = findViewById(R.id.tvAgeGender);
        tvName = findViewById(R.id.tvName);
        tvIPNo = findViewById(R.id.tvIPNo);
        tvPID = findViewById(R.id.tvPID);
        spnTest = findViewById(R.id.spnTest);
        etSearchBillNo = findViewById(R.id.etSearchBillNo);
        txtDept = findViewById(R.id.txtDept);
        txtDate = findViewById(R.id.txtDate);
        //edtProgress = findViewById(R.id.edtProgress);
        spnConsultant = findViewById(R.id.spnConsultant);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        context = mActivity;
        recyclerView = findViewById(R.id.recyclerView);

        llProcedureNote = findViewById(R.id.llProcedureNote);

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
        testListAngio=new ArrayList<>();
        testListAngio.add(new TestListAngio(0, "Select Test"));
        txtDate.setText((date));
        findViewById(R.id.ivSearch).setOnClickListener(view -> getPatientDetail());
        txtDate.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(mActivity), R.style.DialogTheme,
                    (view2, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        txtDate.setText((date));
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        });
        spnTest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                test=String.valueOf(testListAngio.get(i).getId());
                Call<AngioplastyResp> call=RetrofitClient.getInstance().getApi().getAngioTemplate(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), SharedPrefManager.getInstance(mActivity).getHeadID(), Integer.valueOf(SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString()), String.valueOf(testListAngio.get(i).getId()));
                call.enqueue(new Callback<AngioplastyResp>() {
                    @Override
                    public void onResponse(Call<AngioplastyResp> call, Response<AngioplastyResp> response) {
                        if(response.isSuccessful()){
                            if (response.body() != null) {
                                if(!response.body().getTable().isEmpty()) {
                                    richTextEditor.setHtml(response.body().getTable().get(0).getTemplate().trim());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AngioplastyResp> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                
            }
        });
        getAngioReport();
        getConsultantName();

    }

    private void getConsultantName() {
        consultantNameList.add(0, new ConsultantName(0, 0, "Select Consultant", 0));
        Call<ControlBySubDeptResp> call = RetrofitClient.getInstance().getApi().getControlsBySubDept(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), SharedPrefManager.getInstance(mActivity).getSubDept().getId(), SharedPrefManager.getInstance(mActivity).getHeadID(), SharedPrefManager.getInstance(mActivity).getUser().getUserid());
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
    private void saveReport(){
        if(!tvPID.getText().toString().isEmpty()){
            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().saveAngioResult(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), String.valueOf(SharedPrefManager.getInstance(mActivity).getHeadID()), SharedPrefManager.getInstance(mActivity).getUser().getUserid(), tvBillNo.getText().toString(), test, richTextEditor.getHtml().trim(), edtImpression.getText().toString().trim(), edtCathId.getText().toString().trim(), tvPatientType.getText().toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        getAngioReport();
                        Toast.makeText(AngioReportActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }else Toast.makeText(mActivity, "Invalid Bill No.!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                if (SharedPrefManager.getInstance(context).getUser().getDesigid() == 1) {
                } else if (SharedPrefManager.getInstance(context).getUser().getDesigid() != 1 && spnConsultant.getSelectedItemPosition() != 0) {
                    saveReport();
                } else
                    Toast.makeText(context, "Consultant name required!", Toast.LENGTH_LONG).show();

                break;
            case R.id.btnUpdate:
                if (SharedPrefManager.getInstance(context).getUser().getDesigid() != 1 && spnConsultant.getSelectedItemPosition() != 0) {
                    //   updateProgressReport(SharedPrefManager.getInstance(context).getConsultantList().get(spnConsultant.getSelectedItemPosition()).getUserid());
                } else
                    Toast.makeText(context, "Consultant name required!", Toast.LENGTH_LONG).show();

                break;
        }

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void getPatientDetail() {
        Utils.showRequestDialog(mActivity);
        Call<AngioPatientDetailResp> call = RetrofitClient.getInstance().getApi().getPatientDetails(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid(),
                SharedPrefManager.getInstance(mActivity).getHeadID(),
                etSearchBillNo.getText().toString().trim(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid());
        call.enqueue(new Callback<AngioPatientDetailResp>() {
            @Override
            public void onResponse(Call<AngioPatientDetailResp> call, Response<AngioPatientDetailResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        testListAngio.addAll(1,response.body().getTestList());
                        testListAngioAdp = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, testListAngio);
                        spnTest.setAdapter(testListAngioAdp);
                        tvPID.setText(String.valueOf(response.body().getPatientDetails().get(0).getPid()));
                        tvBillDate.setText(String.valueOf(response.body().getPatientDetails().get(0).getBillDate()));
                        tvAgeGender.setText(String.valueOf(response.body().getPatientDetails().get(0).getGender()));
                        tvCrNo.setText(String.valueOf(response.body().getPatientDetails().get(0).getCrNo()));
                        tvIPNo.setText(String.valueOf(response.body().getPatientDetails().get(0).getIpNo()));
                        tvName.setText(String.valueOf(response.body().getPatientDetails().get(0).getPatientName()));
                        tvBillNo.setText(String.valueOf(response.body().getPatientDetails().get(0).getBillNo()));
                        tvPatientType.setText(String.valueOf(response.body().getPatientDetails().get(0).getIsOpd()));
                    }
                    Utils.hideDialog();
                }
            }

            @Override
            public void onFailure(Call<AngioPatientDetailResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    private void getAngioReport() {
        Utils.showRequestDialog(mActivity);
        Call<AngioReportResp> call = RetrofitClient.getInstance().getApi().getAngioReportList(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid(),
                SharedPrefManager.getInstance(mActivity).getPid(),
                SharedPrefManager.getInstance(mActivity).getHeadID(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid()
        );
        /*call.enqueue(new Callback<ViewProgressResp>() {
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
                Toast.makeText(mActivity,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });*/
        call.enqueue(new Callback<AngioReportResp>() {
            @Override
            public void onResponse(Call<AngioReportResp> call, Response<AngioReportResp> response) {
                if (response.isSuccessful()) {
                    Utils.hideDialog();
                    if (response.body() != null) {
                        recyclerView.setAdapter(new ProgressHistoryAdapter(response.body().getAngioReportList()));
                    }
                }
            }

            @Override
            public void onFailure(Call<AngioReportResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }


    public class ProgressHistoryAdapter extends RecyclerView.Adapter<ProgressHistoryAdapter.RecyclerViewHolder> {
        private List<AngioReportList> angioReportLists;

        ProgressHistoryAdapter(List<AngioReportList> angioReportLists) {
            this.angioReportLists = angioReportLists;
        }

        @NonNull
        @Override
        public ProgressHistoryAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_angio_report, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProgressHistoryAdapter.RecyclerViewHolder holder, int i) {
            holder.tvTestName.setText(angioReportLists.get(i).getItemName());
            holder.tvDate.setText(angioReportLists.get(i).getBillDate());
            holder.tvCathId.setText(angioReportLists.get(i).getCathID());
            holder.tvImpression.setText(Html.fromHtml(angioReportLists.get(i).getImpression()));
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.tvImpression.setText(Html.fromHtml(progressList.get(i).getDetails().trim(), Html.FROM_HTML_MODE_COMPACT));
            } else {*/

            holder.ivEdit.setOnClickListener(view -> {
                //detailId = String.valueOf(progressList.get(i).getId());
            });

            holder.ivRemove.setOnClickListener(view -> {
                //detailId = String.valueOf(progressList.get(i).getId());
            });


        }

        @Override
        public int getItemCount() {
            return angioReportLists.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvTestName, tvDate, tvCathId, tvImpression;
            ImageView ivEdit, ivRemove;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTestName = itemView.findViewById(R.id.tvTestName);
                tvDate = itemView.findViewById(R.id.tvDate);
                tvCathId = itemView.findViewById(R.id.tvCathId);
                tvImpression = itemView.findViewById(R.id.tvImpression);
                ivEdit = itemView.findViewById(R.id.ivEdit);
                ivRemove = itemView.findViewById(R.id.ivRemove);
            }
        }
    }

    private void setupTextEditor() {

        richTextEditor.setPadding((int) getResources().getDimension(R.dimen._5sdp),
                (int) getResources().getDimension(R.dimen._5sdp),
                (int) getResources().getDimension(R.dimen._5sdp),
                (int) getResources().getDimension(R.dimen._5sdp));

        findViewById(R.id.action_undo).setOnClickListener(v -> richTextEditor.undo());

        findViewById(R.id.action_redo).setOnClickListener(v -> richTextEditor.redo());

        findViewById(R.id.action_bold).setOnClickListener(v -> richTextEditor.setBold());

        findViewById(R.id.action_italic).setOnClickListener(v -> richTextEditor.setItalic());

        findViewById(R.id.action_subscript).setOnClickListener(v -> richTextEditor.setSubscript());

        findViewById(R.id.action_superscript).setOnClickListener(v -> richTextEditor.setSuperscript());

        findViewById(R.id.action_strikethrough).setOnClickListener(v -> richTextEditor.setStrikeThrough());

        findViewById(R.id.action_underline).setOnClickListener(v -> richTextEditor.setUnderline());

        findViewById(R.id.action_heading1).setOnClickListener(v -> richTextEditor.setHeading(1));

        findViewById(R.id.action_heading2).setOnClickListener(v -> richTextEditor.setHeading(2));

        findViewById(R.id.action_heading3).setOnClickListener(v -> richTextEditor.setHeading(3));

        findViewById(R.id.action_heading4).setOnClickListener(v -> richTextEditor.setHeading(4));

        findViewById(R.id.action_heading5).setOnClickListener(v -> richTextEditor.setHeading(5));

        findViewById(R.id.action_heading6).setOnClickListener(v -> richTextEditor.setHeading(6));

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

        findViewById(R.id.action_indent).setOnClickListener(v -> richTextEditor.setIndent());

        findViewById(R.id.action_outdent).setOnClickListener(v -> richTextEditor.setOutdent());

        findViewById(R.id.action_align_left).setOnClickListener(v -> richTextEditor.setAlignLeft());

        findViewById(R.id.action_align_center).setOnClickListener(v -> richTextEditor.setAlignCenter());

        findViewById(R.id.action_align_right).setOnClickListener(v -> richTextEditor.setAlignRight());

        findViewById(R.id.action_blockquote).setOnClickListener(v -> richTextEditor.setBlockquote());

        findViewById(R.id.action_insert_bullets).setOnClickListener(v -> richTextEditor.setBullets());

        findViewById(R.id.action_insert_numbers).setOnClickListener(v -> richTextEditor.setNumbers());

        findViewById(R.id.action_insert_image).setVisibility(View.GONE);
        findViewById(R.id.action_insert_image).setOnClickListener(v -> richTextEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                "dachshund"));


        findViewById(R.id.action_insert_link).setVisibility(View.GONE);
        findViewById(R.id.action_insert_link).setOnClickListener(v -> richTextEditor.insertLink("https://github.com/wasabeef", "wasabeef"));
        findViewById(R.id.action_insert_checkbox).setOnClickListener(v -> richTextEditor.insertTodo());
    }
}