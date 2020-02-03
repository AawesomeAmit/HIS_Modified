package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Adapter.SupplimentIntakeAdp;
import com.trueform.era.his.Model.SupplementList;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.InsertResponse;
import com.trueform.era.his.Response.SupplementDetailResp;
import com.trueform.era.his.Response.SupplementListResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.RetrofitClient1;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InputSupplement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InputSupplement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputSupplement extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView txtUnit;
    private SupplementList supplementList;
    private EditText edtVal;
    Context context;
    private SimpleDateFormat format1;
    SimpleDateFormat format2;
    private TextView txtDate;
    private TextView txtTime;
    static String date = "";
    int  mYear = 0, mMonth = 0, mDay = 0, mHour=0, mMinute=0;
    Date today = new Date();
    Calendar c;
    private RecyclerView rvSupp;
    private AutoCompleteTextView edtSupp;
    private ArrayAdapter<SupplementList> supplementListArrayAdapter;
    private SupplementListResp supplementListResp;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InputSupplement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputSupplement.
     */
    // TODO: Rename and change types and number of parameters
    public static InputSupplement newInstance(String param1, String param2) {
        InputSupplement fragment = new InputSupplement();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_input_supplement, container, false);
        context=view.getContext();
        Utils.showRequestDialog(context);
        edtVal=view.findViewById(R.id.edtQty);
        TextView btnSave = view.findViewById(R.id.btnSave);
        rvSupp=view.findViewById(R.id.rvSupp);
        txtUnit=view.findViewById(R.id.txtUnit);
        edtSupp=view.findViewById(R.id.edtSupp);
        txtDate=view.findViewById(R.id.txtDate);
        txtTime=view.findViewById(R.id.txtTime);
        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        //txtDate.setText(date);
        format1 = new SimpleDateFormat("dd/MM/yyyy");
        format2 = new SimpleDateFormat("hh:mm a");
        date = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtDate.setText(Utils.formatDate(date));
        txtTime.setText(format2.format(today));
        rvSupp.setLayoutManager(new LinearLayoutManager(context));
        Call<SupplementListResp> call = RetrofitClient1.getInstance().getApi().getSupplementList("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", SharedPrefManager.getInstance(context).getMemberId().getMemberId(),SharedPrefManager.getInstance(context).getMemberId().getUserLoginId());
        call.enqueue(new Callback<SupplementListResp>() {
            @Override
            public void onResponse(Call<SupplementListResp> call, Response<SupplementListResp> response) {
                Utils.showRequestDialog(context);
                if (response.body() != null) {
                    supplementListResp = response.body();
                    if (supplementListResp.getResponseCode() == 1) {
                        supplementListArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, supplementListResp.getResponseValue());
                        supplementListArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);
                        edtSupp.setAdapter(supplementListArrayAdapter);
                    } else edtSupp.setAdapter(null);
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<SupplementListResp> call, Throwable t) {
                edtSupp.setAdapter(null);
                Utils.hideDialog();
            }
        });
        edtSupp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtUnit.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtSupp.setOnItemClickListener((adapterView, view12, i, l) -> {
            supplementList=(SupplementList) adapterView.getItemAtPosition(i);
            txtUnit.setText(supplementList.getUnitName());
        });
        btnSave.setOnClickListener(view1 -> {
            if((!txtUnit.getText().toString().isEmpty()) && (!edtVal.getText().toString().isEmpty())){
                Utils.showRequestDialog(context);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dateToStr = format.format(today);
                Call<InsertResponse> call1 =RetrofitClient1.getInstance().getApi().addMedicineIntakeDetails("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", SharedPrefManager.getInstance(context).getMemberId().getMemberId(), supplementList.getMedicineID(), supplementList.getMedicineBrandID(), supplementList.getMedicineDose().toString(), edtVal.getText().toString().trim(), supplementList.getDoseUnitID(), today.getHours() + ":" + today.getMinutes(), dateToStr, SharedPrefManager.getInstance(context).getMemberId().getUserLoginId());
                call1.enqueue(new Callback<InsertResponse>() {
                    @Override
                    public void onResponse(Call<InsertResponse> call1, Response<InsertResponse> response) {
                        if(response.body()!=null){
                            if(response.body().getResponseCode()==1){
                                Toast.makeText(context, "Saved successfully!", Toast.LENGTH_LONG).show();
                                bind();
                                edtVal.setText("");
                                txtUnit.setText("");
                            }
                        }
                        Utils.hideDialog();
                    }

                    @Override
                    public void onFailure(Call<InsertResponse> call1, Throwable t) {
                        Utils.hideDialog();
                    }
                });
            } else Toast.makeText(context, "Invalid selection!", Toast.LENGTH_SHORT).show();
        });
        bind();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    private void bind(){
        Utils.showRequestDialog(context);
        Call<SupplementDetailResp> call= RetrofitClient.getInstance().getApi().getIntakeSupplimentData(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<SupplementDetailResp>() {
            @Override
            public void onResponse(Call<SupplementDetailResp> call, Response<SupplementDetailResp> response) {
                if (response.isSuccessful()){
                    SupplementDetailResp supplementDetailResp=response.body();
                    if (supplementDetailResp != null && supplementDetailResp.getSupplimentDetail().size() > 0) {
                        rvSupp.setAdapter(new SupplimentIntakeAdp(context, supplementDetailResp.getSupplimentDetail()));
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<SupplementDetailResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
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
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if(view.getId()==R.id.txtTime){
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, i, i1) -> {
                mHour=i;
                mMinute=i1;
                c.set(Calendar.HOUR_OF_DAY, mHour);
                c.set(Calendar.MINUTE, mMinute);
                today.setHours(mHour);
                today.setMinutes(mMinute);
                txtTime.setText(format2.format(today));
            },mHour,mMinute,false);
            timePickerDialog.updateTime(today.getHours(), today.getMinutes());
            timePickerDialog.show();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
