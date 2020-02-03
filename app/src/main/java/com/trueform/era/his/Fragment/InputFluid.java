package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Adapter.FluidIntakeAdp;
import com.trueform.era.his.Model.IntakeHistory;
import com.trueform.era.his.Model.IntakeMaster;
import com.trueform.era.his.Model.UnitMaster;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.FluidListResp;
import com.trueform.era.his.Response.GetIntakeOuttakeResp;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InputFluid.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InputFluid#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputFluid extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Spinner spnFluid;
    private EditText edtVal;
    private Spinner spnUnit;
    Context context;
    private TextView txtDate;
    private TextView txtTime;
    private List<IntakeMaster> fluidList;
    private List<UnitMaster> unitList;
    private RecyclerView rvFluidIntake;
    private static String date = "";
    private SimpleDateFormat format2;
    private int mYear = 0, mMonth = 0, mDay = 0, mHour=0, mMinute=0;
    private Date today = new Date();
    private ArrayAdapter<IntakeMaster> fluidAdp;
    private ArrayAdapter<UnitMaster> unitAdp;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InputFluid() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputFluid.
     */
    // TODO: Rename and change types and number of parameters
    public static InputFluid newInstance(String param1, String param2) {
        InputFluid fragment = new InputFluid();
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
        View view=inflater.inflate(R.layout.fragment_input_fluid, container, false);
        spnFluid=view.findViewById(R.id.spnOutput);
        edtVal=view.findViewById(R.id.edtQty);
        spnUnit=view.findViewById(R.id.txtUnit);
        TextView btnSave = view.findViewById(R.id.btnSave);
        rvFluidIntake=view.findViewById(R.id.rvFluidIntake);
        context=view.getContext();
        fluidList=new ArrayList<>();
        unitList=new ArrayList<>();
        Utils.showRequestDialog(context);
        txtDate=view.findViewById(R.id.txtDate);
        txtTime=view.findViewById(R.id.txtTime);
        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        txtDate.setText(date);
        format2 = new SimpleDateFormat("hh:mm a");
        date = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtDate.setText(Utils.formatDate(date));
        //txtDate.setText(format1.format(today));
        txtTime.setText(format2.format(today));
        rvFluidIntake.setLayoutManager(new LinearLayoutManager(context));
        fluidList.clear();
        unitList.clear();
        fluidList.add(0, new IntakeMaster(0,"-Select-", 0));
        unitList.add(0, new UnitMaster(0,"-Select-"));
        Call<FluidListResp> call= RetrofitClient.getInstance().getApi().intakeOutType(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString());
        call.enqueue(new Callback<FluidListResp>() {
            @Override
            public void onResponse(Call<FluidListResp> call, Response<FluidListResp> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        FluidListResp fluidListResp=response.body();
                        if(fluidListResp.getIntakeMaster().size()>0)
                        fluidList.addAll(1,fluidListResp.getIntakeMaster());
                        if(fluidListResp.getUnitMaster()!=null)
                        if(fluidListResp.getUnitMaster().size()>0)
                        unitList.addAll(1,fluidListResp.getUnitMaster());
                    }
                }
                fluidAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, fluidList);
                unitAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, unitList);
                spnFluid.setAdapter(fluidAdp);
                spnUnit.setAdapter(unitAdp);
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<FluidListResp> call, Throwable t) {
                Log.v("error", t.getMessage());
                Utils.hideDialog();
            }
        });
        bindData();
        btnSave.setOnClickListener(view1 -> {
            if(spnFluid.getSelectedItemPosition()!=0 && spnUnit.getSelectedItemPosition()!=0 && !(edtVal.getText().toString().isEmpty())){
                saveFluid();
            }
        });
        return view;
    }
    private void bindData(){
        Utils.showRequestDialog(context);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatTo = new SimpleDateFormat("dd/MM/yyyy");
        String dateToStr = format.format(today);
        Call<GetIntakeOuttakeResp> call1= RetrofitClient.getInstance().getApi().getIntakeOutputData(
                SharedPrefManager.getInstance(context).getUser().getAccessToken(),
                SharedPrefManager.getInstance(context).getUser().getUserid().toString(),
                SharedPrefManager.getInstance(context).getPid(),
                SharedPrefManager.getInstance(context).getHeadID(),
                dateToStr+" 12:00 AM",
                SharedPrefManager.getInstance(context).getIpNo(),
                formatTo.format(today)+" 11:59 PM",
                SharedPrefManager.getInstance(context).getSubDept().getId(),
                SharedPrefManager.getInstance(context).getUser().getUserid());
        call1.enqueue(new Callback<GetIntakeOuttakeResp>() {
            @Override
            public void onResponse(Call<GetIntakeOuttakeResp> call, Response<GetIntakeOuttakeResp> response) {
                if(response.isSuccessful()){
                    GetIntakeOuttakeResp getIntakeOuttakeResp=response.body();
                    if(getIntakeOuttakeResp!=null){
                        rvFluidIntake.setAdapter(new FluidIntakeAdp(context, getIntakeOuttakeResp.getIntakeHistory()));
                    } else{
                        List<IntakeHistory> historyList=new ArrayList<>();
                        rvFluidIntake.setAdapter(new FluidIntakeAdp(context, historyList));
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetIntakeOuttakeResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    private void saveFluid(){
        Utils.showRequestDialog(context);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String dateToStr = format.format(today);
        Call<ResponseBody> call= RetrofitClient.getInstance().getApi().saveIntakeData(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getHeadID(), SharedPrefManager.getInstance(context).getIpNo(), 0, dateToStr, edtVal.getText().toString().trim(), fluidList.get(spnFluid.getSelectedItemPosition()).getId().toString(), unitList.get(spnUnit.getSelectedItemPosition()).getUnitid().toString(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    edtVal.setText("");
                    Toast.makeText(context, "Saved successfully!", Toast.LENGTH_SHORT).show();
                    bindData();
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Network issue!", Toast.LENGTH_SHORT).show();
                Utils.hideDialog();
            }
        });
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.txtDate){
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        today.setDate(dayOfMonth);
                        today.setMonth(monthOfYear);
                        today.setYear(year-1900);
                        txtDate.setText(Utils.formatDate(date));
                        bindData();
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if(view.getId()==R.id.txtTime){
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, i, i1) -> {
                mHour=i;
                mMinute=i1;
                today.setHours(mHour);
                today.setMinutes(mMinute);
                txtTime.setText(format2.format(today));
            },mHour,mMinute,false);
            timePickerDialog.updateTime(today.getHours(), today.getMinutes());
            timePickerDialog.show();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
