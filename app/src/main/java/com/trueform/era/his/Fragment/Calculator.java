package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.snackbar.Snackbar;
import com.trueform.era.his.Model.CalculatorList;
import com.trueform.era.his.Model.ParameterScore;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.CalculatorResp;
import com.trueform.era.his.Response.GetCalculatorListResp;
import com.trueform.era.his.Response.ParameterScoreResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Calculator extends Fragment {
    private Spinner spnCalculator;
    private LinearLayout lLayout, lResult;
    Context context;
    private ParameterScore parameterScore;
    private TextView txtResult;
    private TextView txtFormula;
    private CalculatorResp calculatorResp;
    private GetCalculatorListResp calculatorList;
    private int selected = 0;
    private ArrayAdapter<CalculatorList> calculatorListAdaptor;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LinearLayout.LayoutParams params, params1, params2;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Calculator() {
        // Required empty public constructor
    }

    public static Calculator newInstance(String param1, String param2) {
        Calculator fragment = new Calculator();
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

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        context = view.getContext();
        spnCalculator = view.findViewById(R.id.spnCalculator);
        TextView btnCalculate = view.findViewById(R.id.btnCalculate);
        txtFormula = view.findViewById(R.id.txtFormula);
        lLayout = view.findViewById(R.id.lLayout);
        lResult = view.findViewById(R.id.lResult);
        txtResult = view.findViewById(R.id.txtResult);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 30);
        params1 = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen._130sdp), LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(30, 0, 0, 0);
        params2 = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen._80sdp), LinearLayout.LayoutParams.WRAP_CONTENT);
        Call<GetCalculatorListResp> call = RetrofitClient.getInstance().getApi().getCalculatorList(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString());
        call.enqueue(new Callback<GetCalculatorListResp>() {
            @Override
            public void onResponse(Call<GetCalculatorListResp> call, Response<GetCalculatorListResp> response) {
                if (response.isSuccessful()) {
                    calculatorList = response.body();
                    if (calculatorList != null) {
                        calculatorListAdaptor = new ArrayAdapter<>(context, R.layout.spinner_layout, calculatorList.getCalculatorList());
                        calculatorListAdaptor.setDropDownViewResource(R.layout.spinner_layout);
                        spnCalculator.setAdapter(calculatorListAdaptor);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCalculatorListResp> call, Throwable t) {

            }
        });
        spnCalculator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Utils.showRequestDialog(context);
                lLayout.removeAllViews();
                lResult.setVisibility(View.GONE);
                selected = calculatorList.getCalculatorList().get(i).getId();
                Call<CalculatorResp> call1 = RetrofitClient.getInstance().getApi().getCalculatorFormula(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), calculatorList.getCalculatorList().get(i).getId());
                call1.enqueue(new Callback<CalculatorResp>() {
                    @SuppressLint({"ResourceType", "SetTextI18n"})
                    @Override
                    public void onResponse(Call<CalculatorResp> call, Response<CalculatorResp> response) {
                        if (response.isSuccessful()) {
                            calculatorResp = response.body();
                            if (calculatorResp != null) {
                                if (calculatorResp.getControlList().size() > 0) {
                                    Log.v("calculator", String.valueOf(calculatorResp));
                                    for (int j = 0; j < calculatorResp.getControlList().size(); j++) {
                                        LinearLayout linearLayout = new LinearLayout(context);
                                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                        TextView txt = new TextView(context);
                                        txt.setText(calculatorResp.getControlList().get(j).getLabelDisplay());
                                        txt.setTextSize((int) getResources().getDimension(R.dimen._4sdp));
                                        txt.setTextColor(getResources().getColor(R.color.black));
                                        txt.setLayoutParams(params2);
                                        linearLayout.addView(txt);
                                        final TextView txtScore = new TextView(context);
                                        if (calculatorResp.getControlList().get(j).getScore()) {
                                            if (calculatorResp.getControlList().get(j).getParameterScore() != null)
                                                txtScore.setText("Score: " + calculatorResp.getControlList().get(j).getParameterScore());
                                            else txtScore.setText("Score: -");
                                            txtScore.setId(1);
                                        } else txtScore.setId(0);
                                        if (calculatorResp.getControlList().get(j).getControlType().equalsIgnoreCase("number")) {
                                            final EditText edt = new EditText(context);
                                            edt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                                            edt.setId(calculatorResp.getControlList().get(j).getParameterID());
                                            edt.setHint(calculatorResp.getControlList().get(j).getParameterName());
                                            edt.setText(calculatorResp.getControlList().get(j).getParameterValue());
                                            edt.setSingleLine();
                                            edt.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.edt_bor));
                                            edt.setPadding((int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._4sdp));
                                            if (calculatorResp.getControlList().get(j).getScore())
                                                edt.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        Call<ParameterScoreResp> call = RetrofitClient.getInstance().getApi().getParameterScoreValue(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), selected, edt.getId(), edt.getText().toString().trim());
                                                        call.enqueue(new Callback<ParameterScoreResp>() {
                                                            @Override
                                                            public void onResponse(Call<ParameterScoreResp> call, Response<ParameterScoreResp> response) {
                                                                if (response.isSuccessful()) {
                                                                    try {
                                                                        parameterScore = response.body().getParameterScore().get(0);
                                                                        if (parameterScore.getScore() != null)
                                                                            txtScore.setText("Score: " + parameterScore.getScore());
                                                                        else
                                                                            txtScore.setText("Score: -");
                                                                        txtScore.setTag(parameterScore.getScore());
                                                                    } catch (Exception ex) {
                                                                        ex.printStackTrace();
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ParameterScoreResp> call, Throwable t) {

                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void afterTextChanged(Editable editable) {

                                                    }
                                                });
                                            edt.setLayoutParams(params1);
                                            linearLayout.addView(edt);
                                        } else {
                                            //if (calculatorResp.getControlList().get(j).getControlType().equalsIgnoreCase("text") || calculatorResp.getControlList().get(j).getControlType().equalsIgnoreCase("dropdown")) {
                                            final EditText edt = new EditText(context);
                                            edt.setId(calculatorResp.getControlList().get(j).getParameterID());
                                            edt.setHint(calculatorResp.getControlList().get(j).getParameterName());
                                            edt.setText(calculatorResp.getControlList().get(j).getParameterValue());
                                            edt.setSingleLine();
                                            edt.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.edt_bor));
                                            edt.setPadding((int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._4sdp));
                                            if (calculatorResp.getControlList().get(j).getScore())
                                                edt.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                        Call<ParameterScoreResp> call = RetrofitClient.getInstance().getApi().getParameterScoreValue(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), selected, edt.getId(), edt.getText().toString().trim());
                                                        call.enqueue(new Callback<ParameterScoreResp>() {
                                                            @Override
                                                            public void onResponse(Call<ParameterScoreResp> call, Response<ParameterScoreResp> response) {
                                                                if (response.isSuccessful()) {
                                                                    try {
                                                                        parameterScore = response.body() != null ? response.body().getParameterScore().get(0) : null;
                                                                        if ((parameterScore != null ? parameterScore.getScore() : null) != null)
                                                                            txtScore.setText("Score: " + parameterScore.getScore());
                                                                        else
                                                                            txtScore.setText("Score: -");
                                                                        txtScore.setTag(parameterScore.getScore());
                                                                    } catch (Exception ex) {
                                                                        ex.printStackTrace();
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ParameterScoreResp> call, Throwable t) {

                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void afterTextChanged(Editable editable) {

                                                    }
                                                });
                                            edt.setLayoutParams(params1);
                                            linearLayout.addView(edt);
                                        }
                                        txtScore.setTextSize((int) getResources().getDimension(R.dimen._3sdp));
                                        txtScore.setTextColor(getResources().getColor(R.color.black));
                                        txtScore.setTag(calculatorResp.getControlList().get(j).getScore());
                                        txtScore.setPadding((int) getResources().getDimension(R.dimen._4sdp), 0, (int) getResources().getDimension(R.dimen._4sdp), 0);
                                        if (calculatorResp.getControlList().get(j).getScore())
                                            txtScore.setVisibility(View.VISIBLE);
                                        else txtScore.setVisibility(View.GONE);
                                        txtScore.setTag(calculatorResp.getControlList().get(j).getParameterScore());
                                        linearLayout.addView(txtScore);
                                        linearLayout.setLayoutParams(params);
                                        lLayout.addView(linearLayout);
                                    }
                                }
                                if (!calculatorResp.getResult().get(0).getCalculatedResult().equalsIgnoreCase("")) {
                                    txtResult.setText(calculatorResp.getResult().get(0).getCalculatedResult() + " " + calculatorResp.getResult().get(0).getUnit());
                                    lResult.setVisibility(View.VISIBLE);
                                } else lResult.setVisibility(View.GONE);
                                if (!calculatorResp.getResult().get(0).getFormula().equalsIgnoreCase("")) {
                                    txtFormula.setText(calculatorResp.getResult().get(0).getFormula());
                                }
                            }
                        }
                        Utils.hideDialog();
                    }

                    @Override
                    public void onFailure(Call<CalculatorResp> call, Throwable t) {
                        Utils.hideDialog();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnCalculate.setOnClickListener(view1 -> {
            try {
                JSONArray array = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                boolean validator = true;
                for (int i = 0; i < lLayout.getChildCount(); i++) {
                    LinearLayout linearLayout = (LinearLayout) lLayout.getChildAt(i);
                    EditText edt = (EditText) linearLayout.getChildAt(1);
                    TextView txt = (TextView) linearLayout.getChildAt(2);
                    JSONObject object = new JSONObject();
                    object.put("parameterID", edt.getId());
                    if (txt.getId() == 1) {
                        if (txt.getTag() != null)
                            object.put("parameterValue", txt.getTag());
                        else {
                            validator = false;
                            break;
                        }
                    } else object.put("parameterValue", edt.getText().toString().trim());
                    array.put(object);
                }
                jsonObject.put("id", selected);
                jsonObject.put("parameterList", array);
                Log.v("hitApiArray", String.valueOf(jsonObject));
                Log.v("apiName", RetrofitClient.BASE_URL + "Calculator/GetCalculatorResultByParameters");
                if (validator)
                    AndroidNetworking.post(RetrofitClient.BASE_URL + "Calculator/GetCalculatorResultByParameters")
                            .addHeaders("accessToken", SharedPrefManager.getInstance(context).getUser().getAccessToken())
                            .addHeaders("userID", SharedPrefManager.getInstance(context).getUser().getUserid().toString())
                            .addJSONObjectBody(jsonObject)
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONObject array1 = (JSONObject) response.getJSONArray("result").get(0);
                                        lResult.setVisibility(View.VISIBLE);
                                        txtResult.setText(array1.getString("calculatedResult") + " " + array1.getString("unit"));
                                    } catch (Exception ex) {
                                        Log.v("status", "success");
                                        lResult.setVisibility(View.GONE);
                                    }
                                    Log.v("status", "success");
                                }

                                @Override
                                public void onError(ANError error) {
                                    if (error.getErrorCode() != 0) {
                                        Log.v("status", "onError errorCode : " + error.getErrorCode());
                                        Log.v("onError errorBody", "onError errorBody : " + error.getErrorBody());
                                        Log.v("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                                    } else {
                                        Log.v("status", "onError errorDetail : " + error.getErrorDetail());
                                    }
                                    lResult.setVisibility(View.GONE);
                                    Toast.makeText(context, "Some error occurred", Toast.LENGTH_LONG).show();
                                }
                            });
                else
                    Toast.makeText(context, "Please fill all scores!", Toast.LENGTH_LONG).show();
                Snackbar.make(lLayout, "Please fill all scores!", Snackbar.LENGTH_LONG);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return view;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}