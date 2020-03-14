package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;
import com.trueform.era.his.Activity.ShowPatientAnalyzingGraph;
import com.trueform.era.his.Model.Nutrition;
import com.trueform.era.his.Model.VitalAnalysisModel;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.NutrientBindRes;
import com.trueform.era.his.Response.VitalAutoCompleteResp;
import com.trueform.era.his.Response.VitalList;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CombinedGraph extends Fragment implements View.OnClickListener {
    private Context context;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Spinner spnHour, spnIntakeType;
    private List<String> hourList = new ArrayList<>();
    ArrayAdapter<VitalList> adapter;
    List<String> intakeTypeList;
    private List<VitalAnalysisModel> vitalLists = new ArrayList<>();
    private RecyclerView rvVital, rvNutrient;
    private ChipsInput chpVital, chpNutrient;
    private SimpleDateFormat format2;
    private int mYear = 0, mMonth = 0, mDay = 0, mHour = 0, mMinute = 0;
    private Date today = new Date();
    private TextView txtFrmDate;
    private TextView txtFrmTime;
    private static String fromDate = "";
    CheckBox chkFood, chkInvestigation, chkActivity, chkProb, chkIntakeOut, chkMedIntake;
    private List<Nutrition> nutritionList = new ArrayList<>();
    private ArrayList<String> selectedMembersList = new ArrayList<>();
    private ArrayList<String> selectedVitalList = new ArrayList<>();
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CombinedGraph() {
        // Required empty public constructor
    }

    public static CombinedGraph newInstance(String param1, String param2) {
        CombinedGraph fragment = new CombinedGraph();
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

    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_combined_graph, container, false);
        context = view.getContext();
        rvVital = view.findViewById(R.id.rvVital);
        spnHour = view.findViewById(R.id.spnHour);
        spnIntakeType = view.findViewById(R.id.spnIntakeType);
        chkFood = view.findViewById(R.id.chkFood);
        chkInvestigation = view.findViewById(R.id.chkInvestigation);
        chkActivity = view.findViewById(R.id.chkActivity);
        chkProb = view.findViewById(R.id.chkProb);
        chkIntakeOut = view.findViewById(R.id.chkIntakeOut);
        chkMedIntake = view.findViewById(R.id.chkMedIntake);
        rvVital.setLayoutManager(new LinearLayoutManager(context));
        rvVital.setNestedScrollingEnabled(false);
        rvNutrient = view.findViewById(R.id.rvNutrient);
        rvNutrient.setLayoutManager(new LinearLayoutManager(context));
        rvNutrient.setNestedScrollingEnabled(false);
        chpVital = view.findViewById(R.id.chpVital);
        chpVital.setChipHasAvatarIcon(true);
        chpVital.setChipDeletable(true);
        chpVital.setShowChipDetailed(false);
        chpNutrient = view.findViewById(R.id.chpNutrient);
        chpNutrient.setChipHasAvatarIcon(true);
        chpNutrient.setChipDeletable(true);
        chpNutrient.setShowChipDetailed(false);
        txtFrmTime = view.findViewById(R.id.txtFrmTime);
        txtFrmDate = view.findViewById(R.id.txtFrmDate);
        TextView btnShow = view.findViewById(R.id.btnShow);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        format2 = new SimpleDateFormat("hh:mm a");
        fromDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtFrmDate.setText(Utils.formatDate(fromDate));
        today.setHours(8);
        today.setMinutes(0);
        txtFrmTime.setText(format2.format(today));
        txtFrmDate.setOnClickListener(this);
        txtFrmTime.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        intakeTypeList=new ArrayList<>();
        intakeTypeList.add(0, "All");
        intakeTypeList.add(1, "Food");
        intakeTypeList.add(2, "Supplement");
        intakeTypeList.add(3, "Medicine");
        ArrayAdapter<String> intakeTypeAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, intakeTypeList);
        spnIntakeType.setAdapter(intakeTypeAdp);
        chpVital.addChip(74, (Uri) null, "heartRate", "");
        chpVital.addChip(3, (Uri) null, "Pulse", "");
        chpVital.addChip(56, (Uri) null, "spo2", "");
        chpVital.addChip(5, (Uri) null, "Temperature", "");
        chpVital.addChip(4, (Uri) null, "BP_Sys", "");
        chpVital.addChip(6, (Uri) null, "BP_Dias", "");
        chpVital.addChip(7, (Uri) null, "respRate", "");
        chpVital.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chip, int newSize) {
                selectedVitalList.add(String.valueOf(chip.getId()));
                vitalLists.clear();
                VitalChipAdp vitalChipAdp = new VitalChipAdp(vitalLists);
                rvVital.setAdapter(vitalChipAdp);
                vitalChipAdp.notifyDataSetChanged();
            }

            @Override
            public void onChipRemoved(ChipInterface chip, int newSize) {
                selectedVitalList.remove(chip.getId().toString());
            }

            @Override
            public void onTextChanged(CharSequence text) {
                bindVitals(String.valueOf(text));
                VitalChipAdp vitalChipAdp = new VitalChipAdp(vitalLists);
                rvVital.setAdapter(vitalChipAdp);
                vitalChipAdp.notifyDataSetChanged();
            }
        });
        chpNutrient.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chip, int newSize) {
                selectedMembersList.add(String.valueOf(chip.getId()));
                nutritionList.clear();
                NutrientChipAdp nutrientChipAdp = new NutrientChipAdp(nutritionList);
                rvNutrient.setAdapter(nutrientChipAdp);
                nutrientChipAdp.notifyDataSetChanged();
            }

            @Override
            public void onChipRemoved(ChipInterface chip, int newSize) {
                selectedMembersList.remove(chip.getId().toString());
            }

            @Override
            public void onTextChanged(CharSequence text) {
                bindNutrientList(String.valueOf(text));
                NutrientChipAdp nutrientChipAdp = new NutrientChipAdp(nutritionList);
                rvNutrient.setAdapter(nutrientChipAdp);
                nutrientChipAdp.notifyDataSetChanged();
            }
        });
        for (int i = 0; i < 24; i++) {
            hourList.add(i, (i + 1) + " Hour");
        }
        ArrayAdapter<String> hourAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, hourList);
        spnHour.setAdapter(hourAdp);
        spnHour.setSelection(23);
        return view;
    }

    private void bindVitals(String text) {
        Call<VitalAutoCompleteResp> call = RetrofitClient.getInstance().getApi().getAutoCompleteVital(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), text);
        call.enqueue(new Callback<VitalAutoCompleteResp>() {
            @Override
            public void onResponse(Call<VitalAutoCompleteResp> call, Response<VitalAutoCompleteResp> response) {
                if (response.isSuccessful()) {
                    vitalLists.clear();
                    if (response.body() != null) {
                        vitalLists.addAll(response.body().getVitalList());
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<VitalAutoCompleteResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    private void bindNutrientList(String text) {
        Call<NutrientBindRes> call = RetrofitClient.getInstance().getApi().getAutoCompleteNutrition(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), text);
        call.enqueue(new Callback<NutrientBindRes>() {
            @Override
            public void onResponse(Call<NutrientBindRes> call, Response<NutrientBindRes> response) {
                if (response.isSuccessful()) {
                    nutritionList.clear();
                    if (response.body() != null) {
                        nutritionList.addAll(0, response.body().getNutrition());
                    }
                }
            }

            @Override
            public void onFailure(Call<NutrientBindRes> call, Throwable t) {

            }
        });
    }

    public class NutrientChipAdp extends RecyclerView.Adapter<NutrientChipAdp.RecyclerViewHolder> {
        List<Nutrition> nutritionList;

        NutrientChipAdp(List<Nutrition> nutritionList) {
            this.nutritionList = nutritionList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.inner_chip_recycler, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtNutrient.setText(String.valueOf(nutritionList.get(i).getNutrientName()));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int i, @NonNull List<Object> payloads) {
            holder.txtNutrient.setText(String.valueOf(nutritionList.get(i).getNutrientName()));
            holder.txtNutrient.setOnClickListener(view -> {
                chpNutrient.addChip(nutritionList.get(i).getId(), (Uri) null, nutritionList.get(i).getNutrientName(), "");
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return nutritionList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtNutrient;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtNutrient = itemView.findViewById(R.id.txtNutrient);
            }
        }
    }

    public class VitalChipAdp extends RecyclerView.Adapter<VitalChipAdp.RecyclerViewHolder> {
        List<VitalAnalysisModel> vitalLists;

        VitalChipAdp(List<VitalAnalysisModel> vitalLists) {
            this.vitalLists = vitalLists;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.inner_vital_chip_recycler, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtVital.setText(String.valueOf(vitalLists.get(i).getVitalName()));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int i, @NonNull List<Object> payloads) {
            holder.txtVital.setText(String.valueOf(vitalLists.get(i).getVitalName()));
            holder.txtVital.setOnClickListener(view -> {
                chpVital.addChip(vitalLists.get(i).getId(), (Uri) null, vitalLists.get(i).getVitalName(), "");
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtFrmDate) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
                    (view12, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        fromDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        today.setDate(dayOfMonth);
                        today.setMonth(monthOfYear);
                        today.setYear(year - 1900);
                        txtFrmDate.setText(Utils.formatDate(fromDate));
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if (view.getId() == R.id.txtFrmTime) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, i, i1) -> {
                mHour = i;
                mMinute = i1;
                today.setHours(mHour);
                today.setMinutes(mMinute);
                txtFrmTime.setText(format2.format(today));
            }, mHour, mMinute, false);
            timePickerDialog.updateTime(today.getHours(), today.getMinutes());
            timePickerDialog.show();
        } else if (view.getId() == R.id.btnShow) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format2 = new SimpleDateFormat("hh:mm a");
            StringBuilder vital = new StringBuilder();
            for (int i = 0; i < chpVital.getSelectedChipList().size(); i++) {
                vital.append(chpVital.getSelectedChipList().get(i).getId()).append(",");
            }
            StringBuilder nutrient = new StringBuilder();
            for (int i = 0; i < chpNutrient.getSelectedChipList().size(); i++) {
                nutrient.append(chpNutrient.getSelectedChipList().get(i).getId()).append(",");
            }
            Intent intent = new Intent(context, ShowPatientAnalyzingGraph.class);
            intent.putExtra("from", format.format(today));
            intent.putExtra("fromDate", format1.format(today));
            intent.putExtra("fromTime", format2.format(today));
            intent.putExtra("vital", vital.toString());
            intent.putExtra("nutrient", nutrient.toString());
            intent.putExtra("isFoodIntake", String.valueOf(chkFood.isChecked()));
            intent.putExtra("isInvestigation", String.valueOf(chkInvestigation.isChecked()));
            intent.putExtra("isActivity", String.valueOf(chkActivity.isChecked()));
            intent.putExtra("isProblem", String.valueOf(chkProb.isChecked()));
            intent.putExtra("isIO", String.valueOf(chkIntakeOut.isChecked()));
            intent.putExtra("isIntakeMedicine", String.valueOf(chkMedIntake.isChecked()));
            intent.putExtra("intakeType", intakeTypeList.get(spnIntakeType.getSelectedItemPosition()).equals("All") ? null : intakeTypeList.get(spnIntakeType.getSelectedItemPosition()));
            intent.putExtra("hour", String.valueOf(spnHour.getSelectedItemId() + 1));
            startActivity(intent);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}