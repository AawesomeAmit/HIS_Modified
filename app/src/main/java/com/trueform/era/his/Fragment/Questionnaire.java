package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.snackbar.Snackbar;
import com.trueform.era.his.Model.OptionList;
import com.trueform.era.his.Model.QuestionList;
import com.trueform.era.his.Model.ReportList;
import com.trueform.era.his.Model.SetList;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.GetQuestionnaireList;
import com.trueform.era.his.Response.QuestionnaireResp;
import com.trueform.era.his.Response.SetIDResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Questionnaire extends Fragment implements View.OnClickListener {
    private TextView txtQues, btnSave;
    private int selectedIndex=0;
    private Spinner spnSets;
    private SetIDResp setID;
    private ScrollView svQuesResp, svQues;
    private TextView txtDate, label;
    private TextView txtTime;
    private static String date = "";
    private SimpleDateFormat format2;
    private int mYear = 0, mMonth = 0, mDay = 0, mHour=0, mMinute=0;
    private Date today = new Date();
    private ArrayAdapter<SetList> setListArrayAdapter;
    private LinearLayout.LayoutParams params, params1, params2, params3;
    private LinearLayout radioLayout, checkLayout;
    private QuestionnaireResp questionnaireResp;
    private OptionList optionList;
    private LinearLayout rvQuestionnaire, rvQuestionnaireResp;
    Context context;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressDialog progressDialog;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Questionnaire() {
        // Required empty public constructor
    }

    public static Questionnaire newInstance(String param1, String param2) {
        Questionnaire fragment = new Questionnaire();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questionnaire, container, false);
        context=view.getContext();
        rvQuestionnaire=view.findViewById(R.id.rvQuestionnaire);
        rvQuestionnaireResp=view.findViewById(R.id.rvQuestionnaireResp);
        spnSets=view.findViewById(R.id.spnSets);
        btnSave=view.findViewById(R.id.btnSave);
        svQuesResp=view.findViewById(R.id.svQuesResp);
        progressDialog=new ProgressDialog(context);
        txtDate=view.findViewById(R.id.txtDate);
        label=view.findViewById(R.id.label);
        txtTime=view.findViewById(R.id.txtTime);
        svQues=view.findViewById(R.id.svQues);
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
        txtTime.setText(format2.format(today));
        progressDialog.setMessage("Please wait...");
        final int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0, (int) getResources().getDimension(R.dimen._5sdp));
        params1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins((int) getResources().getDimension(R.dimen._7sdp),(int) getResources().getDimension(R.dimen._2sdp),(int) getResources().getDimension(R.dimen._5sdp),0);
        params2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.setMargins((int) getResources().getDimension(R.dimen._10sdp),0,(int) getResources().getDimension(R.dimen._7sdp),0);
        params3=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        radioLayout = view.findViewById(R.id.radioLayout);
        checkLayout = view.findViewById(R.id.checkLayout);
        Call<SetIDResp> call1=RetrofitClient.getInstance().getApi().getSetList(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getHeadID());
        call1.enqueue(new Callback<SetIDResp>() {
            @Override
            public void onResponse(Call<SetIDResp> call, Response<SetIDResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        setID=response.body();
                        if(setID.getSetList().size()>0) {
                            setListArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, setID.getSetList());
                            spnSets.setAdapter(setListArrayAdapter);
                            //bindResponse();
                        } else {
                            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SetIDResp> call, Throwable t) {

            }
        });
        spnSets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, final View view, int i, long l) {
                progressDialog.show();
                rvQuestionnaire.removeAllViews();
                selectedIndex=i;
                bindResponse();
                Call<QuestionnaireResp> call2= RetrofitClient.getInstance().getApi().getQuestionnaireList(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), setID.getSetList().get(i).getSetID(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
                call2.enqueue(new Callback<QuestionnaireResp>() {
                    @Override
                    public void onResponse(Call<QuestionnaireResp> call, Response<QuestionnaireResp> response) {
                        if (response.isSuccessful()) {
                            questionnaireResp = response.body();
                            QuestionList questionList;
                            int l=0;
                            List<QuestionList> questionLists=new ArrayList<>();
                            if (questionnaireResp.getQuestionList().size() > 0) {
                                for (int j = 0; j < questionnaireResp.getQuestionList().size(); j++) {
                                    if(questionnaireResp.getQuestionList().get(j).getDependentQuestionID()!=0) {
                                        if(!questionLists.contains(questionnaireResp.getQuestionList().get(j))) {
                                            questionLists.add(l, questionnaireResp.getQuestionList().get(j));
                                            l++;
                                        }
                                        for (int k = 0; k < questionnaireResp.getQuestionList().size(); k++) {
                                            if(questionnaireResp.getQuestionList().get(j).getDependentQuestionID().equals(questionnaireResp.getQuestionList().get(k).getId())){
                                                if(!questionLists.contains(questionnaireResp.getQuestionList().get(k))) {
                                                    questionLists.add(l, questionnaireResp.getQuestionList().get(k));
                                                    l++;
                                                }
                                            }
                                        }
                                    } else {
                                        if(!questionLists.contains(questionnaireResp.getQuestionList().get(j))) {
                                            questionLists.add(l, questionnaireResp.getQuestionList().get(j));
                                            l++;
                                        }
                                    }
                                }
                                questionnaireResp.setQuestionList(questionLists);
                                for (int i = 0; i < questionnaireResp.getQuestionList().size(); i++) {
                                    LinearLayout ll = new LinearLayout(context);
                                    ll.setOrientation(LinearLayout.VERTICAL);
                                    ll.setLayoutParams(params);
                                    GridLayout gridLayout = new GridLayout(context);
                                    gridLayout.setColumnCount(5);
                                    ll.setId(questionnaireResp.getQuestionList().get(i).getSubCategoryID());
                                    questionList = questionnaireResp.getQuestionList().get(i);
                                    ll.setBackgroundResource(R.drawable.edt_bor);
                                    txtQues = new TextView(context);
                                    txtQues.setTextColor(Color.BLACK);
                                    txtQues.setId(questionnaireResp.getQuestionList().get(i).getId());
                                    if(screenSize==Configuration.SCREENLAYOUT_SIZE_XLARGE)
                                    txtQues.setTextSize((int) getResources().getDimension(R.dimen._4sdp));
                                    else txtQues.setTextSize((int) getResources().getDimension(R.dimen._4sdp));
                                    txtQues.setLayoutParams(params1);
                                    txtQues.setText(questionList.getQuestionText());
                                    txtQues.setPadding(0, (int) getResources().getDimension(R.dimen._2sdp), 0, (int) getResources().getDimension(R.dimen._2sdp));
                                    ll.addView(txtQues);
                                    if (questionList.getAnswerDataType().equalsIgnoreCase("text")) {
                                        for (int k = 0; k < questionnaireResp.getOptionList().size(); k++) {
                                            optionList = questionnaireResp.getOptionList().get(k);
                                            if (questionList.getId().equals(optionList.getQuestionMasterID())) {
                                                gridLayout.setId(questionnaireResp.getOptionList().get(k).getCategoryID());
                                            }
                                        }
                                        EditText edtAns = new EditText(context);
                                        gridLayout.setTag("text");
                                        edtAns.setBackgroundResource(R.drawable.edt_bor);
                                        edtAns.setTextColor(Color.BLACK);
                                        edtAns.setLayoutParams(params3);
                                        edtAns.setId(0);
                                        edtAns.setPadding((int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._5sdp));
                                        gridLayout.addView(edtAns);
                                    } /*else if (questionList.getAnswerDataType().equalsIgnoreCase("numeric")) {
                                        for (int k = 0; k < questionnaireResp.getOptionList().size(); k++) {
                                            optionList = questionnaireResp.getOptionList().get(k);
                                            if (questionList.getId().equals(optionList.getQuestionMasterID())) {
                                                gridLayout.setId(questionnaireResp.getOptionList().get(k).getCategoryID());
                                            }
                                        }
                                        EditText edtAns = new EditText(context);
                                        gridLayout.setTag("text");
                                        edtAns.setBackgroundResource(R.drawable.edt_bor);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                            edtAns.setInputType(UCharacter.NumericType.DIGIT);
                                        }
                                        edtAns.setTextColor(Color.BLACK);
                                        edtAns.setLayoutParams(params3);
                                        edtAns.setId(0);
                                        edtAns.setPadding((int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._3sdp), (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._3sdp));
                                        gridLayout.addView(edtAns);
                                    }*/
                                    else if (questionList.getAnswerDataType().equalsIgnoreCase("radio")) {
                                        RadioGroup rdGrp = new RadioGroup(context);
                                        gridLayout.setTag("radio");
                                        for (int k = 0; k < questionnaireResp.getOptionList().size(); k++) {
                                            optionList = questionnaireResp.getOptionList().get(k);
                                            if (questionList.getId().equals(optionList.getQuestionMasterID())) {
                                                gridLayout.setId(questionnaireResp.getOptionList().get(k).getCategoryID());
                                                final RadioButton rButton = new RadioButton(context);
                                                rButton.setTextColor(context.getResources().getColor(R.color.black));
                                                rButton.setButtonDrawable(null);
                                                if(screenSize==Configuration.SCREENLAYOUT_SIZE_XLARGE)
                                                    rButton.setTextSize((int) getResources().getDimension(R.dimen._4sdp));
                                                else rButton.setTextSize((int) getResources().getDimension(R.dimen._4sdp));
                                                rButton.setTag(questionnaireResp.getOptionList().get(k).getDependentQuestionID());
                                                rButton.setButtonDrawable(context.getResources().getDrawable(R.drawable.radio_selector));
                                                rButton.setText(optionList.getOptionText());
                                                rButton.setId(questionnaireResp.getOptionList().get(k).getId());
                                                rButton.setTextColor(Color.BLACK);
                                                rButton.setPadding(0, (int) getResources().getDimension(R.dimen._2sdp), 0, (int) getResources().getDimension(R.dimen._2sdp));
                                                rdGrp.addView(rButton);
                                                rButton.setOnCheckedChangeListener((compoundButton, b) -> {
                                                    if(!rButton.getTag().equals(0)){
                                                        for (int j = 0; j < questionnaireResp.getQuestionList().size(); j++) {
                                                            if(rButton.getTag().equals(questionnaireResp.getQuestionList().get(j).getId())) {
                                                                LinearLayout ll1 = (LinearLayout) rvQuestionnaire.getChildAt(j);
                                                                ll1.setVisibility(View.VISIBLE);
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                        gridLayout.addView(rdGrp);
                                    } else if (questionList.getAnswerDataType().equalsIgnoreCase("checkbox")) {
                                        for (int k = 0; k < questionnaireResp.getOptionList().size(); k++) {
                                            optionList = questionnaireResp.getOptionList().get(k);
                                            if (questionList.getId().equals(optionList.getQuestionMasterID())) {
                                                gridLayout.setId(questionnaireResp.getOptionList().get(k).getCategoryID());
                                                CheckBox chk = new CheckBox(context);
                                                chk.setText(optionList.getOptionText());
                                                chk.setId(questionnaireResp.getOptionList().get(k).getId());
                                                chk.setButtonDrawable(null);
                                                if(screenSize==Configuration.SCREENLAYOUT_SIZE_XLARGE)
                                                    chk.setTextSize((int) getResources().getDimension(R.dimen._4sdp));
                                                else chk.setTextSize((int) getResources().getDimension(R.dimen._4sdp));
                                                chk.setTextColor(context.getResources().getColor(R.color.black));
                                                chk.setButtonDrawable(context.getResources().getDrawable(R.drawable.radio_selector));
                                                chk.setPadding(0, (int) getResources().getDimension(R.dimen._3sdp), 0, (int) getResources().getDimension(R.dimen._3sdp));
                                                gridLayout.addView(chk);
                                            }
                                        }
                                    }
                                    gridLayout.setLayoutParams(params2);
                                    gridLayout.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen._10sdp));
                                    ll.addView(gridLayout);
                                    rvQuestionnaire.addView(ll);
                                }
                                for (int j = 0; j < questionnaireResp.getQuestionList().size(); j++) {
                                    if(questionnaireResp.getQuestionList().get(j).getDependentQuestionID()!=0) {
                                        for (int k = 0; k < questionnaireResp.getQuestionList().size(); k++) {
                                            if(questionnaireResp.getQuestionList().get(j).getDependentQuestionID().equals(questionnaireResp.getQuestionList().get(k).getId())){
                                                LinearLayout ll= (LinearLayout) rvQuestionnaire.getChildAt(k);
                                                ll.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                }
                                btnSave.setVisibility(View.VISIBLE);
                            } else {
                                Snackbar.make(view, "No data", Snackbar.LENGTH_LONG).show();
                                btnSave.setVisibility(View.GONE);
                            }
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<QuestionnaireResp> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSave.setOnClickListener(view1 -> sendResponse());
        return view;
    }
    private void bindResponse(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        LinearLayout.LayoutParams layoutParams2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams layoutParams1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3);
        layoutParams.topMargin=30;
        layoutParams.bottomMargin=20;
        layoutParams1.bottomMargin=10;
        layoutParams1.topMargin=20;
        RelativeLayout.LayoutParams paramss = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramss.addRule(RelativeLayout.ALIGN_PARENT_END);
        Call<GetQuestionnaireList> call = RetrofitClient.getInstance().getApi().GetQuestionnaireInputReport(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), format.format(today), SharedPrefManager.getInstance(context).getPid(),setID.getSetList().get(selectedIndex).getSetID());
        call.enqueue(new Callback<GetQuestionnaireList>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetQuestionnaireList> call, Response<GetQuestionnaireList> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        List<ReportList> reportLists=response.body().getReportList();
                        rvQuestionnaireResp.removeAllViews();
                        if(reportLists.size()>0){
                            for (int i = 0; i < reportLists.size(); i++) {
                                RelativeLayout linearLayout = new RelativeLayout(context);
                                LinearLayout linearLayout1 = new LinearLayout(context);
                                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                                TextView txtDate = new TextView(context);
                                TextView txtQues = new TextView(context);
                                TextView txtOption = new TextView(context);
                                TextView txtDelete = new TextView(context);
                                txtDelete.setText("Delete");
                                txtDate.setTextSize((int) getResources().getDimension(R.dimen._4sdp));
                                txtQues.setTextSize((int) getResources().getDimension(R.dimen._4sdp));
                                txtOption.setTextSize((int) getResources().getDimension(R.dimen._4sdp));
                                txtDelete.setTextSize((int) getResources().getDimension(R.dimen._3sdp));
                                txtDate.setTextColor(Color.BLACK);
                                txtQues.setTextColor(Color.BLACK);
                                txtOption.setTextColor(Color.BLACK);
                                txtDelete.setTextColor(Color.WHITE);
                                txtDelete.setPadding((int) getResources().getDimension(R.dimen._10sdp), (int) getResources().getDimension(R.dimen._3sdp), (int) getResources().getDimension(R.dimen._10sdp), (int) getResources().getDimension(R.dimen._5sdp));
                                txtDelete.setBackgroundResource(R.drawable.btn_investigation);
                                txtDelete.setGravity(Gravity.END);
                                txtDate.setTypeface(null, Typeface.BOLD);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    txtDelete.setForegroundGravity(Gravity.END);
                                }
                                if (i != 0) {
                                    if (reportLists.get(i).getInputTime().equalsIgnoreCase(reportLists.get(i - 1).getInputTime())) {
                                        linearLayout.setVisibility(View.GONE);
                                    } else {
                                        txtDate.setText("Questionnaire Time: "+reportLists.get(i).getInputTime());
                                        linearLayout.setVisibility(View.VISIBLE);
                                        linearLayout.setLayoutParams(layoutParams);
                                        View view =new View(context);
                                        view.setBackgroundColor(Color.GRAY);
                                        view.setLayoutParams(layoutParams1);
                                        linearLayout1.addView(view);
                                    }
                                } else {
                                    txtDate.setText("Questionnaire Time: "+reportLists.get(i).getInputTime());
                                    linearLayout.setVisibility(View.VISIBLE);
                                    linearLayout.setLayoutParams(layoutParams);
                                }
                                txtDelete.setId(reportLists.get(i).getMainID());
                                txtQues.setText("Q: "+reportLists.get(i).getQuestionText());
                                txtOption.setText("A: "+reportLists.get(i).getOptionText());
                                txtDelete.setLayoutParams(paramss);
                                linearLayout.addView(txtDate);
                                linearLayout.addView(txtDelete);
                                txtQues.setLayoutParams(params1);
                                txtOption.setLayoutParams(params2);
                                txtOption.setTypeface(txtOption.getTypeface(), Typeface.BOLD);
                                linearLayout1.addView(linearLayout);
                                linearLayout1.addView(txtQues);
                                linearLayout1.addView(txtOption);
                                rvQuestionnaireResp.addView(linearLayout1);
                                svQues.setLayoutParams(layoutParams2);
                                svQuesResp.setVisibility(View.VISIBLE);
                                txtDelete.setOnClickListener(view ->
                                        new AlertDialog.Builder(context).setIcon(R.drawable.ic_warning).setTitle("Exit")
                                        .setMessage("Are you sure you want to delete the questionnaire?")
                                        .setCancelable(true)
                                        .setPositiveButton(
                                                "Yes",
                                                (dialog, id) -> deleteQues(txtDelete.getId()))
                                        .setNegativeButton(
                                                "No",
                                                (dialog, id) -> dialog.cancel())
                                        .show());
                            }
                            layoutParams2.weight=14;
                            label.setVisibility(View.VISIBLE);
                        } else {
                            layoutParams2.weight=28;
                            svQues.setLayoutParams(layoutParams2);
                            svQuesResp.setVisibility(View.GONE);
                            label.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetQuestionnaireList> call, Throwable t) {

            }
        });
    }
    private void deleteQues(int id) {
        Call<ResponseBody> call1 = RetrofitClient.getInstance().getApi().deleteQuestionnaireInput(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), id);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utils.showRequestDialog(context);
                if (response.isSuccessful()) {
                    Log.v("dfdf", String.valueOf(response.code()));
                    Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    bindResponse();
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
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
    private void sendResponse() {
        Log.v("hitApi:", RetrofitClient.BASE_URL+"Questionnaire/SaveAnswerList");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        boolean isChk=false;
        boolean isData=false;
        try {
            if (questionnaireResp.getQuestionList().size() > 0) {
                boolean validate = false;
                for (int i = 0; i < questionnaireResp.getQuestionList().size(); i++) {
                    LinearLayout ll = (LinearLayout) rvQuestionnaire.getChildAt(i);
                    TextView txt = (TextView) ll.getChildAt(0);
                    GridLayout gridLayout = (GridLayout) ll.getChildAt(1);
                    EditText edt;
                    RadioGroup radioGroup;
                    RadioButton radioButton;
                    CheckBox chk;
                    int optID = 0;
                    String optText = "";
                    if (questionnaireResp.getQuestionList().get(i).getAnswerDataType().equalsIgnoreCase("text") || questionnaireResp.getQuestionList().get(i).getAnswerDataType().equalsIgnoreCase("numeric")) {
                        edt = (EditText) gridLayout.getChildAt(0);
                        optText = edt.getText().toString().trim();
                        isChk = false;
                        if (!optText.equalsIgnoreCase(""))
                            isData = true;
                        validate = !optText.equalsIgnoreCase("");
                    } else if (questionnaireResp.getQuestionList().get(i).getAnswerDataType().equalsIgnoreCase("radio")) {
                        radioGroup = (RadioGroup) gridLayout.getChildAt(0);
                        isChk = false;
                        for (int j = 0; j < radioGroup.getChildCount(); j++) {
                            radioButton = (RadioButton) radioGroup.getChildAt(j);
                            if (radioButton.isChecked()) {
                                optID = radioButton.getId();
                                optText = radioButton.getText().toString();
                                validate = true;
                                isData = true;
                                break;
                            } else validate = false;
                        }
                    } else if (questionnaireResp.getQuestionList().get(i).getAnswerDataType().equalsIgnoreCase("checkbox")) {
                        for (int j = 0; j < gridLayout.getChildCount(); j++) {
                            chk = (CheckBox) gridLayout.getChildAt(j);
                            isChk = true;
                            if (chk.isChecked()) {
                                optID = chk.getId();
                                optText = chk.getText().toString();
                                validate = true;
                                isData = true;
                                JSONObject object = new JSONObject();
                                object.put("categoryId", gridLayout.getId());
                                object.put("optionID", optID);
                                object.put("optionText", optText);
                                object.put("questionID", txt.getId());
                                object.put("setId", setID.getSetList().get(selectedIndex).getSetID());
                                object.put("subCategoryId", ll.getId());
                                array.put(object);
                            } else validate = false;
                        }
                    }
                    if (!isChk) {
                        if (validate) {
                            JSONObject object = new JSONObject();
                            object.put("categoryId", gridLayout.getId());
                            object.put("optionID", optID);
                            object.put("optionText", optText);
                            object.put("questionID", txt.getId());
                            object.put("setId", setID.getSetList().get(selectedIndex).getSetID());
                            object.put("subCategoryId", ll.getId());
                            array.put(object);
                        }
                    }
                }
            }
            jsonObject.put("PID", SharedPrefManager.getInstance(context).getPid());
            jsonObject.put("subDeptID", SharedPrefManager.getInstance(context).getSubDept().getId());
            jsonObject.put("userID", SharedPrefManager.getInstance(context).getUser().getUserid());
            jsonObject.put("setId", setID.getSetList().get(selectedIndex).getSetID());
            jsonObject.put("questionnaireDate", format.format(today));
            jsonObject.put("answerList", array);
            Log.v("hitApiArr", String.valueOf(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(isData)
        AndroidNetworking.post(RetrofitClient.BASE_URL+"Questionnaire/SaveAnswerList")
                .addHeaders("accessToken", SharedPrefManager.getInstance(context).getUser().getAccessToken())
                .addHeaders("userID", SharedPrefManager.getInstance(context).getUser().getUserid().toString())
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Questionnaire saved successfully", Toast.LENGTH_SHORT).show();
                        bindResponse();
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
        else Toast.makeText(context, "Please enter atleast one response ", Toast.LENGTH_SHORT).show();
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
                        bindResponse();
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
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
