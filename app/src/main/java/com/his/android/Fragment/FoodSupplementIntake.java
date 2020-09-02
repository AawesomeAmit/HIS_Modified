package com.his.android.Fragment;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.his.android.Adapter.MealIntakeAdp;
import com.his.android.Adapter.SupplimentIntakeAdp;
import com.his.android.Model.MealList;
import com.his.android.Model.SupplementList;
import com.his.android.Model.UnitList;
import com.his.android.R;
import com.his.android.Response.FoodDetailResp;
import com.his.android.Response.InsertResponse;
import com.his.android.Response.MealResp;
import com.his.android.Response.SupplementDetailResp;
import com.his.android.Response.SupplementListResp;
import com.his.android.Response.UnitResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodSupplementIntake extends Fragment implements View.OnClickListener {
    private TextView txtFrmDate;
    private TextView txtFrmTime, edtSuppQty, txtSuppUnit;
    private static String fromDate = "";
    private SimpleDateFormat format2;
    Context context;
    private MealResp mealResp;
    private RecyclerView rvSupp;
    private Date today = new Date();
    private int mYear = 0, mMonth = 0, mDay = 0, mHour = 0, mMinute = 0;
    private AutoCompleteTextView edtMeal, edtSupp;
    private SupplementList supplementList;
    private ArrayAdapter<MealList> mealListArrayAdapter;
    private ArrayAdapter<UnitList> unitListArrayAdapter;
    private SupplementListResp supplementListResp;
    private ArrayAdapter<SupplementList> supplementListArrayAdapter;
    private List<UnitList> unitMealLists;
    private Spinner spnMealUnit;
    private UnitResp unitResp;
    private int mealId=0;
    private EditText edtMealQty;
    private RecyclerView rvMeal;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FoodSupplementIntake() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodSupplementIntake.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodSupplementIntake newInstance(String param1, String param2) {
        FoodSupplementIntake fragment = new FoodSupplementIntake();
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
        View v=inflater.inflate(R.layout.fragment_food_supplement_intake, container, false);
        context = v.getContext();
        txtFrmDate = v.findViewById(R.id.txtFrmDate);
        txtFrmTime = v.findViewById(R.id.txtFrmTime);
        rvSupp=v.findViewById(R.id.rvSupp);
        edtMeal=v.findViewById(R.id.edtMeal);
        edtMealQty=v.findViewById(R.id.edtMealQty);
        txtSuppUnit=v.findViewById(R.id.txtSuppUnit);
        edtSuppQty=v.findViewById(R.id.edtSuppQty);
        edtSupp=v.findViewById(R.id.edtSupp);
        spnMealUnit=v.findViewById(R.id.txtMealUnit);
        rvMeal=v.findViewById(R.id.rvMeal);
        rvSupp.setLayoutManager(new LinearLayoutManager(context));
        rvMeal.setLayoutManager(new LinearLayoutManager(context));
        MaterialButton btnSubmit=v.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        format2 = new SimpleDateFormat("hh:mm a");
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        fromDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtFrmDate.setText(Utils.formatDate(fromDate));
        txtFrmTime.setText(format2.format(today));
        unitMealLists=new ArrayList<>();
        txtFrmDate.setOnClickListener(this);
        txtFrmTime.setOnClickListener(this);
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
        edtSupp.setOnItemClickListener((adapterView, view12, i, l) -> {
            supplementList=(SupplementList) adapterView.getItemAtPosition(i);
            txtSuppUnit.setText(supplementList.getUnitName());
        });
        edtMeal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                spnMealUnit.setAdapter(null);
                if (edtMeal.getText().length() > 1) {
                    //Utils.showRequestDialog(context);
                    Call<MealResp> call = RetrofitClient1.getInstance().getApi().getFoodListByPrefixText("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", SharedPrefManager.getInstance(context).getMemberId().getMemberId(), edtMeal.getText().toString().trim(), SharedPrefManager.getInstance(context).getMemberId().getUserLoginId());
                    call.enqueue(new Callback<MealResp>() {
                        @Override
                        public void onResponse(Call<MealResp> call, Response<MealResp> response) {
                            if (response.body() != null) {
                                mealResp = response.body();
                                if (mealResp.getResponseCode() == 1) {
                                    mealListArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, mealResp.getResponseValue());
                                    mealListArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);
                                    edtMeal.setAdapter(mealListArrayAdapter);
                                }
                            }
                            //Utils.hideDialog();
                        }

                        @Override
                        public void onFailure(Call<MealResp> call, Throwable t) {
                            //Utils.hideDialog();
                        }
                    });
                } else edtMeal.setAdapter(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtMeal.setOnItemClickListener((adapterView, view1, i, l) -> {
            unitMealLists.clear();
            unitMealLists.add(0, new UnitList(0,"-Select-"));
            mealId=mealResp.getResponseValue().get(i).getIntakeID();
            bindMealUnit(mealId);
        });
        bindSupplement();
        bindMeal();
        return v;
    }

    private void bindMealUnit(int foodId){
        Utils.showRequestDialog(context);
        Call<UnitResp> call = RetrofitClient1.getInstance().getApi().getFoodUnitByFoodId("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", foodId);
        call.enqueue(new Callback<UnitResp>() {
            @Override
            public void onResponse(Call<UnitResp> call, Response<UnitResp> response) {
                if(response.body()!=null){
                    unitResp=response.body();
                    if(unitResp.getResponseCode()==1){
                        unitMealLists.addAll(1,  unitResp.getResponseValue());
                    }
                }
                unitListArrayAdapter=new ArrayAdapter<>(context, R.layout.spinner_layout, unitMealLists);
                spnMealUnit.setAdapter(unitListArrayAdapter);
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<UnitResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    private void bindMeal(){
        Utils.showRequestDialog(context);
        Call<FoodDetailResp> call= RetrofitClient.getInstance().getApi().getIntakeFoodData(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<FoodDetailResp>() {
            @Override
            public void onResponse(Call<FoodDetailResp> call, Response<FoodDetailResp> response) {
                if(response.isSuccessful()) {
                    FoodDetailResp foodDetailResp=response.body();
                    if ((foodDetailResp != null ? foodDetailResp.getFoodDetail().size() : 0) >0) {
                        rvMeal.setAdapter(new MealIntakeAdp(context, foodDetailResp.getFoodDetail()));
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<FoodDetailResp> call, Throwable t) {
                Utils.hideDialog();
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

    private void bindSupplement(){
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtFrmDate:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
                        (view1, year, monthOfYear, dayOfMonth) -> {
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
                break;
            case R.id.txtFrmTime:
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, i, i1) -> {
                    mHour = i;
                    mMinute = i1;
                    today.setHours(mHour);
                    today.setMinutes(mMinute);
                    txtFrmTime.setText(format2.format(today));
                }, mHour, mMinute, false);
                timePickerDialog.updateTime(today.getHours(), today.getMinutes());
                timePickerDialog.show();
                break;
            case R.id.btnSubmit:
                if (mealId != 0) {
                    if (spnMealUnit.getSelectedItemPosition() != 0 && !edtMealQty.getText().toString().isEmpty()) {
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String dateToStr = format.format(today);
                        Utils.showRequestDialog(context);
                        Call<InsertResponse> call = RetrofitClient1.getInstance().getApi().addIntakeDetails("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", mealId, edtMealQty.getText().toString().trim(), today.getHours() + ":" + today.getMinutes(), unitMealLists.get(spnMealUnit.getSelectedItemPosition()).getId().toString(), dateToStr, String.valueOf(SharedPrefManager.getInstance(context).getMemberId().getMemberId()), SharedPrefManager.getInstance(context).getMemberId().getUserLoginId());
                        call.enqueue(new Callback<InsertResponse>() {
                            @Override
                            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                                if (Objects.requireNonNull(response.body()).getResponseCode() == 1) {
                                    edtMealQty.setText("");
                                    bindMeal();
                                    Toast.makeText(context, "Food Saved successfully!", Toast.LENGTH_SHORT).show();
                                }
                                Utils.hideDialog();
                            }

                            @Override
                            public void onFailure(Call<InsertResponse> call, Throwable t) {
                                Toast.makeText(context, "Network issue!", Toast.LENGTH_SHORT).show();
                                Utils.hideDialog();
                            }
                        });
                    } else Toast.makeText(context, "Invalid medicine quantity/unit!", Toast.LENGTH_SHORT).show();
                }
                if((!txtSuppUnit.getText().toString().isEmpty()) && (!edtSuppQty.getText().toString().isEmpty())){
                    Utils.showRequestDialog(context);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String dateToStr = format.format(today);
                    Call<InsertResponse> call1 =RetrofitClient1.getInstance().getApi().addMedicineIntakeDetails("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", SharedPrefManager.getInstance(context).getMemberId().getMemberId(), supplementList.getMedicineID(), supplementList.getMedicineBrandID(), supplementList.getMedicineDose().toString(), edtSuppQty.getText().toString().trim(), supplementList.getDoseUnitID(), today.getHours() + ":" + today.getMinutes(), dateToStr, SharedPrefManager.getInstance(context).getMemberId().getUserLoginId());
                    call1.enqueue(new Callback<InsertResponse>() {
                        @Override
                        public void onResponse(Call<InsertResponse> call1, Response<InsertResponse> response) {
                            if(response.body()!=null){
                                if(response.body().getResponseCode()==1){
                                    Toast.makeText(context, "Supplement Saved successfully!", Toast.LENGTH_LONG).show();
                                    bindSupplement();
                                    edtSuppQty.setText("");
                                    txtSuppUnit.setText("");
                                }
                            }
                            Utils.hideDialog();
                        }

                        @Override
                        public void onFailure(Call<InsertResponse> call1, Throwable t) {
                            Utils.hideDialog();
                        }
                    });
                } else Toast.makeText(context, "Invalid supplement quantity", Toast.LENGTH_SHORT).show();
        break;
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
