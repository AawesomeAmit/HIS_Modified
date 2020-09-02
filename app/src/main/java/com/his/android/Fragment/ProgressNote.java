package com.his.android.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.his.android.Model.ProgressList;
import com.his.android.R;
import com.his.android.Response.ViewProgressResp;
import com.his.android.Response.VitalListResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.richeditor.RichEditor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressNote extends Fragment implements View.OnClickListener {
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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private VitalListResp vitalChartList;
    private Spinner spnConsultant;
    Context context;
    //private EditText edtProgress;
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;

    Calendar c;

    String date;

    int mYear = 0, mMonth = 0, mDay = 0;

    private OnFragmentInteractionListener mListener;

    String detailId, entryDate;

    RichEditor richTextEditor;

    public ProgressNote() {
        // Required empty public constructor
    }

    public static ProgressNote newInstance(String param1, String param2) {
        ProgressNote fragment = new ProgressNote();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_note, container, false);

        richTextEditor = view.findViewById(R.id.richTextEditor);
        richTextEditor.setPlaceholder("Enter Note");

        setupTextEditor(view);

        txtDate = view.findViewById(R.id.txtDate);
        txtPulse = view.findViewById(R.id.txtPulse);
        txtSys = view.findViewById(R.id.txtSys);
        txtDys = view.findViewById(R.id.txtDys);
       // edtProgress = view.findViewById(R.id.edtProgress);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        spnConsultant = toolbar.findViewById(R.id.spnConsultant);
        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        txtRR = view.findViewById(R.id.txtRR);
        txtHR = view.findViewById(R.id.txtHR);
        txtTemp = view.findViewById(R.id.txtTemp);
        context = view.getContext();
        txtWeight = view.findViewById(R.id.txtWeight);
        txtHeight = view.findViewById(R.id.txtHeight);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        date = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);

        txtDate.setText((date));

        txtDate.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), R.style.DialogTheme,
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

        return view;
    }

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        if (!richTextEditor.getHtml().trim().isEmpty()) {
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
        } else Toast.makeText(context, "Empty progress note!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSave:

                if (SharedPrefManager.getInstance(context).getUser().getDesigid() == 1) {
                    saveProgressReport(SharedPrefManager.getInstance(context).getUser().getUserid());
                }
                else if (SharedPrefManager.getInstance(context).getUser().getDesigid()!=1 && spnConsultant.getSelectedItemPosition() != 0) {
                    saveProgressReport(SharedPrefManager.getInstance(context).getConsultantList().get(spnConsultant.getSelectedItemPosition()).getUserid());
                } else Toast.makeText(context, "Consultant name required!", Toast.LENGTH_LONG).show();

                break;
            case R.id.btnUpdate:
                if (SharedPrefManager.getInstance(context).getUser().getDesigid() !=1 && spnConsultant.getSelectedItemPosition() != 0) {
                    updateProgressReport(
                            SharedPrefManager.getInstance(context).getConsultantList().get(spnConsultant.getSelectedItemPosition()).getUserid());
                } else Toast.makeText(context, "Consultant name required!", Toast.LENGTH_LONG).show();

                break;
        }

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    
    
    private void hitGetProgressHistory(String date){
        Utils.showRequestDialog(getActivity());
        
        Call<ViewProgressResp> call= RetrofitClient.getInstance().getApi().getProgressHistory(
                SharedPrefManager.getInstance(getActivity()).getUser().getAccessToken(),
                SharedPrefManager.getInstance(getActivity()).getUser().getUserid(),
                SharedPrefManager.getInstance(getActivity()).getPid(),
                SharedPrefManager.getInstance(getActivity()).getHeadID(),
                SharedPrefManager.getInstance(getActivity()).getIpNo(),
                SharedPrefManager.getInstance(getActivity()).getSubDept().getId(),
                SharedPrefManager.getInstance(getActivity()).getUser().getUserid(),
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
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    

    public class ProgressHistoryAdapter extends RecyclerView.Adapter<ProgressHistoryAdapter.RecyclerViewHolder> {
        private Context mCtx;
        private List<ProgressList> progressList;

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

            holder.tvDate.setText(progressList.get(i).getCreatedDate());
            holder.tvTime.setText(progressList.get(i).getTime());
            holder.tvConsultant.setText(progressList.get(i).getConsultant());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.tvProgressNote.setText(Html.fromHtml(progressList.get(i).getDetails().trim(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.tvProgressNote.setText(Html.fromHtml(progressList.get(i).getDetails().trim()));
            }

            holder.ivEdit.setOnClickListener(view -> {
                detailId = String.valueOf(progressList.get(i).getId());
                entryDate = progressList.get(i).getCreatedDate() +" "+progressList.get(i).getTime();

                richTextEditor.setHtml(progressList.get(i).getDetails().trim());
             //   edtProgress.setText(holder.tvProgressNote.getText().toString().trim());
            });

            holder.ivRemove.setOnClickListener(view -> {
                detailId = String.valueOf(progressList.get(i).getId());
                deleteProgressReport();
            });


        }

        @Override
        public int getItemCount() {
            return progressList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvDate, tvTime, tvProgressNote, tvConsultant;

            ImageView ivEdit, ivRemove;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                tvDate = itemView.findViewById(R.id.tvDate);
                tvTime = itemView.findViewById(R.id.tvTime);
                tvProgressNote = itemView.findViewById(R.id.tvProgressNote);
                tvConsultant = itemView.findViewById(R.id.tvConsultant);
                ivEdit = itemView.findViewById(R.id.ivEdit);
                ivRemove = itemView.findViewById(R.id.ivRemove);
            }
        }
    }

    private void setupTextEditor(View view){

        richTextEditor.setPadding((int) getResources().getDimension(R.dimen._5sdp),
                (int) getResources().getDimension(R.dimen._5sdp),
                (int) getResources().getDimension(R.dimen._5sdp),
                (int) getResources().getDimension(R.dimen._5sdp));

        view.findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.undo();
            }
        });

        view.findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.redo();
            }
        });

        view.findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setBold();
            }
        });

        view.findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setItalic();
            }
        });

        view.findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setSubscript();
            }
        });

        view.findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setSuperscript();
            }
        });

        view.findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setStrikeThrough();
            }
        });

        view.findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setUnderline();
            }
        });

        view.findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setHeading(1);
            }
        });

        view.findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setHeading(2);
            }
        });

        view.findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setHeading(3);
            }
        });

        view.findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setHeading(4);
            }
        });

        view.findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setHeading(5);
            }
        });

        view.findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setHeading(6);
            }
        });

        view.findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                richTextEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });

        view.findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                richTextEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        view.findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setIndent();
            }
        });

        view.findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setOutdent();
            }
        });

        view.findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setAlignLeft();
            }
        });

        view.findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setAlignCenter();
            }
        });

        view.findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setAlignRight();
            }
        });

        view.findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setBlockquote();
            }
        });

        view.findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setBullets();
            }
        });

        view.findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.setNumbers();
            }
        });

        view.findViewById(R.id.action_insert_image).setVisibility(View.GONE);
        view.findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                        "dachshund");
            }
        });


        view.findViewById(R.id.action_insert_link).setVisibility(View.GONE);
        view.findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.insertLink("https://github.com/wasabeef", "wasabeef");
            }
        });
        view.findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                richTextEditor.insertTodo();
            }
        });
    }


}