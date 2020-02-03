package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.trueform.era.his.Model.OutputList;
import com.trueform.era.his.Model.UnitMaster;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.FluidListResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientOutput extends Fragment implements View.OnClickListener {
    private EditText edtUrineQty, edtStoolQty, edtVomitQty;
    private Spinner spnUrineUnit, spnStoolUQty, spnVomitUnit;
    private MaterialButton btnSubmit;
    private List<OutputList> outputLists;
    private List<UnitMaster> unitList;
    TextView txtDate, txtTime;
    Context context;
    static String date = "", time;
    SimpleDateFormat format1;
    SimpleDateFormat format2;
    int mYear = 0, mMonth = 0, mDay = 0, mHour = 0, mMinute = 0;
    Date today = new Date();
    Calendar c;
    private ArrayAdapter<UnitMaster> unitAdp;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PatientOutput() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientOutput.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientOutput newInstance(String param1, String param2) {
        PatientOutput fragment = new PatientOutput();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_output, container, false);
        edtUrineQty = view.findViewById(R.id.edtUrineQty);
        edtStoolQty = view.findViewById(R.id.edtStoolQty);
        edtVomitQty = view.findViewById(R.id.edtVomitQty);
        spnUrineUnit = view.findViewById(R.id.spnUrineUnit);
        spnStoolUQty = view.findViewById(R.id.spnStoolUQty);
        spnVomitUnit = view.findViewById(R.id.spnVomitUnit);
        txtDate = view.findViewById(R.id.txtDate);
        txtTime = view.findViewById(R.id.txtTime);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        format1 = new SimpleDateFormat("dd/MM/yyyy");
        format2 = new SimpleDateFormat("hh:mm a");
        date = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtDate.setText(Utils.formatDate(date));
        txtTime.setText(format2.format(today));
        context = view.getContext();//486
        outputLists = new ArrayList<>();
        unitList = new ArrayList<>();
        outputLists.clear();
        unitList.clear();
        outputLists.add(0, new OutputList(0, "-Select-", 0));
        unitList.add(0, new UnitMaster(0, "-Select-"));
        Call<FluidListResp> call = RetrofitClient.getInstance().getApi().intakeOutType(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString());
        call.enqueue(new Callback<FluidListResp>() {
            @Override
            public void onResponse(Call<FluidListResp> call, Response<FluidListResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        FluidListResp fluidListResp = response.body();
                        if (fluidListResp.getOutputList().size() > 0)
                            outputLists.addAll(1, fluidListResp.getOutputList());
                        if (fluidListResp.getUnitMaster() != null)
                            if (fluidListResp.getUnitMaster().size() > 0)
                                unitList.addAll(1, fluidListResp.getUnitMaster());
                    }
                    unitAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, unitList);
                    spnUrineUnit.setAdapter(unitAdp);
                    spnStoolUQty.setAdapter(unitAdp);
                    spnVomitUnit.setAdapter(unitAdp);
                }
            }

            @Override
            public void onFailure(Call<FluidListResp> call, Throwable t) {

            }
        });
        return view;
    }

    private void saveFluid(Integer output, String qty, Integer unit) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String dateToStr = format.format(today);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().saveOutputData(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getHeadID(), SharedPrefManager.getInstance(context).getIpNo(), dateToStr, qty, output, unit, SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    edtUrineQty.setText("");
                    edtStoolQty.setText("");
                    edtVomitQty.setText("");
                    if (output == 2)
                        Toast.makeText(context, "Urine Data Saved successfully!", Toast.LENGTH_LONG).show();
                    else if (output == 3)
                        Toast.makeText(context, "Stool Data Saved successfully!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context, "Vomit Data Saved successfully!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Network issue!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        if (view.getId() == R.id.txtDate) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        today.setDate(dayOfMonth);
                        today.setMonth(monthOfYear);
                        today.setYear(year - 1900);
                        txtDate.setText(Utils.formatDate(date));
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if (view.getId() == R.id.txtTime) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, i, i1) -> {
                mHour = i;
                mMinute = i1;
                today.setHours(mHour);
                today.setMinutes(mMinute);
                txtTime.setText(format2.format(today));
            }, mHour, mMinute, false);
            timePickerDialog.updateTime(today.getHours(), today.getMinutes());
            timePickerDialog.show();
        } else if (view.getId() == R.id.btnSubmit) {
            if ((!edtUrineQty.getText().toString().isEmpty()) || spnUrineUnit.getSelectedItemPosition() != 0) {
                if (!edtUrineQty.getText().toString().isEmpty()) {
                    if (spnUrineUnit.getSelectedItemPosition() != 0) {
                        saveFluid(2, edtUrineQty.getText().toString().trim(), unitList.get(spnUrineUnit.getSelectedItemPosition()).getUnitid());
                    } else
                        Toast.makeText(context, "Please enter urine unit!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Please enter urine quantity!", Toast.LENGTH_SHORT).show();
            }
            if ((!edtStoolQty.getText().toString().isEmpty()) || spnStoolUQty.getSelectedItemPosition() != 0) {
                if (!edtStoolQty.getText().toString().isEmpty()) {
                    if (spnStoolUQty.getSelectedItemPosition() != 0) {
                        saveFluid(3, edtStoolQty.getText().toString().trim(), unitList.get(spnStoolUQty.getSelectedItemPosition()).getUnitid());
                    } else
                        Toast.makeText(context, "Please enter stool unit!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Please enter stool quantity!", Toast.LENGTH_SHORT).show();
            }
            if ((!edtVomitQty.getText().toString().isEmpty()) || spnVomitUnit.getSelectedItemPosition() != 0) {
                if (!edtVomitQty.getText().toString().isEmpty()) {
                    if (spnVomitUnit.getSelectedItemPosition() != 0) {
                        saveFluid(4, edtVomitQty.getText().toString().trim(), unitList.get(spnVomitUnit.getSelectedItemPosition()).getUnitid());
                    } else
                        Toast.makeText(context, "Please select vomit unit!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Please enter vomit quantity!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
