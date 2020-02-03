package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.snackbar.Snackbar;
import com.trueform.era.his.Model.AttributeList;
import com.trueform.era.his.Model.AttributeValueList;
import com.trueform.era.his.Model.GetAllSuggestedProblemModel;
import com.trueform.era.his.Model.GetAllSymptomsModel;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.AttribValueResp;
import com.trueform.era.his.Response.GetAllSuggestedProblemRes;
import com.trueform.era.his.Response.GetAllSymptomsRes;
import com.trueform.era.his.Response.ProbAttribResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.RetrofitClientOrgan;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.trueform.era.his.Fragment.ProblemBodyPart.selectedRegionId;

public class ProblemSymptomsSelect extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerViewSymptoms;
    private ArrayAdapter<AttributeList> attributeListAdp;
    private ArrayAdapter<AttributeValueList> attributeValueListAdp;
    private List<GetAllSymptomsModel> getAllSymptomsModelList;
    private List<GetAllSuggestedProblemModel> getAllSuggestedProblemModelList = new ArrayList<>();
    Context context;
    private AdapterSymptoms adapterSymptoms;
    private AdapterProblem adapterProblem;
    private ConstraintLayout clBack;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProblemSymptomsSelect() {
        // Required empty public constructor
    }

    public static ProblemSymptomsSelect newInstance(String param1, String param2) {
        ProblemSymptomsSelect fragment = new ProblemSymptomsSelect();
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
        View v = inflater.inflate(R.layout.fragment_problem_symptoms_select, container, false);
        context = v.getContext();
        String key = getArguments() != null ? getArguments().getString("key") : null;
        clBack = v.findViewById(R.id.clBack);
        TextView btnSubmit = v.findViewById(R.id.btnSubmit);
        TextView tvText = v.findViewById(R.id.tvText);
        recyclerViewSymptoms = v.findViewById(R.id.recyclerViewSymptoms);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        recyclerViewSymptoms.setLayoutManager(layoutManager);
        recyclerViewSymptoms.setNestedScrollingEnabled(false);
        recyclerViewSymptoms.setHasFixedSize(true);
        btnSubmit.setOnClickListener(this);
        if (key != null && key.equalsIgnoreCase("1")) hitGetAllSymptoms();
        else {
            getAllProblems();
            tvText.setText(getString(R.string.common_symptoms));
        }
        return v;
    }

    private void hitGetAllSymptoms() {
        Call<GetAllSymptomsRes> call = RetrofitClientOrgan.getInstance().getApi().getAllSymptoms("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiZmlyc3ROYW1lIjoic2FkZGFtIiwibGFzdE5hbWUiOm51bGwsImVtYWlsSWQiOm51bGwsIm1vYmlsZU5vIjoiODk2MDI1MzEzMyIsImNvdW50cnkiOiJJTkRJQSIsInppcENvZGUiOiIyMjYwMjAiLCJvY2N1cGF0aW9uSWQiOjEsImFnZSI6bnVsbCwiZ2VuZGVyIjpudWxsLCJoZWlnaHRJbkZlZXQiOm51bGwsImhlaWdodEluSW5jaCI6bnVsbCwid2VpZ2h0IjpudWxsLCJwYWNrYWdlTmFtZSI6IkZyZWUiLCJpYXQiOjE1NjMwMTM4MDUsImV4cCI6MTU5NDU0OTgwNX0.l220lljQyTXmDPD-gyU53H4vV-I1GDPociKcp2qrWe8", "2", selectedRegionId);
        call.enqueue(new Callback<GetAllSymptomsRes>() {
            @Override
            public void onResponse(Call<GetAllSymptomsRes> call, Response<GetAllSymptomsRes> response) {
                if (response != null && response.body().getResponseCode() == 1) {
                    getAllSymptomsModelList = response.body().getResponseValue();
                    adapterSymptoms = new AdapterSymptoms(getAllSymptomsModelList);
                    recyclerViewSymptoms.setAdapter(adapterSymptoms);
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetAllSymptomsRes> call, Throwable t) {
                Utils.hideDialog();
                Snackbar.make(clBack, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllProblems() {
        Utils.showRequestDialog(context);
        Call<GetAllSuggestedProblemRes> call = RetrofitClientOrgan.getInstance().getApi().getAllSuggestedProblem("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiZmlyc3ROYW1lIjoic2FkZGFtIiwibGFzdE5hbWUiOm51bGwsImVtYWlsSWQiOm51bGwsIm1vYmlsZU5vIjoiODk2MDI1MzEzMyIsImNvdW50cnkiOiJJTkRJQSIsInppcENvZGUiOiIyMjYwMjAiLCJvY2N1cGF0aW9uSWQiOjEsImFnZSI6bnVsbCwiZ2VuZGVyIjpudWxsLCJoZWlnaHRJbkZlZXQiOm51bGwsImhlaWdodEluSW5jaCI6bnVsbCwid2VpZ2h0IjpudWxsLCJwYWNrYWdlTmFtZSI6IkZyZWUiLCJpYXQiOjE1NjMwMTM4MDUsImV4cCI6MTU5NDU0OTgwNX0.l220lljQyTXmDPD-gyU53H4vV-I1GDPociKcp2qrWe8", "2");
        call.enqueue(new Callback<GetAllSuggestedProblemRes>() {
            @Override
            public void onResponse(Call<GetAllSuggestedProblemRes> call, Response<GetAllSuggestedProblemRes> response) {
                if (response != null && response.body().getResponseCode() == 1) {
                    getAllSuggestedProblemModelList = response.body().getResponseValue();
                    adapterProblem = new AdapterProblem(getAllSuggestedProblemModelList);
                    recyclerViewSymptoms.setAdapter(adapterProblem);
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetAllSuggestedProblemRes> call, Throwable t) {
                Utils.hideDialog();
                Snackbar.make(clBack, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isSymptomsChecked()) {
                //saveActivity();
            }
        }
    }

    private void callSave(int probId, String attrib, String value) {
        new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to submit the selected problem?")
                .setCancelable(true)
                .setPositiveButton(
                        "Yes",
                        (dialog, id) -> saveActivity(probId, attrib, value))

                .setNegativeButton(
                        "No",
                        (dialog, id) -> dialog.cancel())
                .show();
    }

    private void saveActivity(int probId, String attrib, String value) {
        Utils.showRequestDialog(context);
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        String toDate = getArguments() != null ? getArguments().getString("to") : null;
        String fromDate = getArguments() != null ? getArguments().getString("from") : null;
        Log.v("hitApi:", RetrofitClient.BASE_URL + "PatientPhysicalActivity/SavePatientComplain");
        JSONObject jsonObject = new JSONObject();
        try {
            object.put("attributeID", attrib);
            object.put("attributeValueID", value);
            array.put(object);
            jsonObject.put("PID", SharedPrefManager.getInstance(context).getPid());
            jsonObject.put("problemID", probId);
            jsonObject.put("subDeptID", SharedPrefManager.getInstance(context).getSubDept().getId());
            jsonObject.put("timeFrom", fromDate);
            jsonObject.put("timeTo", toDate);
            jsonObject.put("userID", SharedPrefManager.getInstance(context).getUser().getUserid());
            jsonObject.put("ListPatientComplain", array);
            Log.v("hitApiArr", String.valueOf(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(RetrofitClient.BASE_URL + "PatientPhysicalActivity/SavePatientComplain")
                .addHeaders("accessToken", SharedPrefManager.getInstance(context).getUser().getAccessToken())
                .addHeaders("userID", SharedPrefManager.getInstance(context).getUser().getUserid().toString())
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Problem saved successfully!", Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }

                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                        Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }
                });
    }

    private void showPopup(final View anchorView, GetAllSuggestedProblemModel getAllSymptomsModel, TextView tvSymptom, CardView cvMain) {
        Utils.showRequestDialog(context);
        Spinner spnAttrib, spnValue;
        View popupView = getLayoutInflater().inflate(R.layout.popup_attribute_value, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        spnAttrib = popupView.findViewById(R.id.spnAttrib);
        spnValue = popupView.findViewById(R.id.spnValue);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        List<AttributeList> attributeLists = new ArrayList<>();
        List<AttributeValueList> attributeValueLists = new ArrayList<>();
        attributeLists.add(0, new AttributeList(0, "Select Attribute"));
        Utils.showRequestDialog(context);
        Call<ProbAttribResp> call = RetrofitClient.getInstance().getApi().getProblemAttribute(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), getAllSymptomsModel.getId());
        call.enqueue(new Callback<ProbAttribResp>() {
            @Override
            public void onResponse(Call<ProbAttribResp> call, Response<ProbAttribResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getAttributeList().size() > 0) {
                            attributeLists.addAll(1, response.body().getAttributeList());
                            attributeListAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, attributeLists);
                            spnAttrib.setAdapter(attributeListAdp);
                            popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
                        } else {
                            callSave(getAllSymptomsModel.getId(), "", "");
                            getAllSymptomsModel.setSelected(!getAllSymptomsModel.isSelected());
                            cvMain.setCardBackgroundColor(getAllSymptomsModel.isSelected() ? getResources().getColor(R.color.blue_light) : getResources().getColor(R.color.gryText));
                            tvSymptom.setTextColor(getAllSymptomsModel.isSelected() ? getResources().getColor(R.color.white) : getResources().getColor(R.color.black));
                        }
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ProbAttribResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
        spnAttrib.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                attributeValueLists.clear();
                Utils.showRequestDialog(context);
                attributeValueLists.add(0, new AttributeValueList(0, "Select Value"));
                Call<AttribValueResp> call = RetrofitClient.getInstance().getApi().getProblemAttributeValue(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), attributeLists.get(i).getAttributeID());
                call.enqueue(new Callback<AttribValueResp>() {
                    @Override
                    public void onResponse(Call<AttribValueResp> call, Response<AttribValueResp> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getAttributeValueList().size() > 0) {
                                    attributeValueLists.addAll(1, response.body().getAttributeValueList());
                                    attributeValueListAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, attributeValueLists);
                                }
                                spnValue.setAdapter(attributeValueListAdp);
                            }
                        }
                        Utils.hideDialog();
                    }

                    @Override
                    public void onFailure(Call<AttribValueResp> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    callSave(getAllSymptomsModel.getId(), String.valueOf(attributeLists.get(spnAttrib.getSelectedItemPosition()).getAttributeID()), String.valueOf(attributeValueLists.get(spnValue.getSelectedItemPosition()).getId()));
                    getAllSymptomsModel.setSelected(!getAllSymptomsModel.isSelected());
                    cvMain.setCardBackgroundColor(getAllSymptomsModel.isSelected() ? getResources().getColor(R.color.blue_light) : getResources().getColor(R.color.gryText));
                    tvSymptom.setTextColor(getAllSymptomsModel.isSelected() ? getResources().getColor(R.color.white) : getResources().getColor(R.color.black));
                    popupWindow.dismiss();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void showPopup1(final View anchorView, GetAllSymptomsModel getAllSymptomsModel, TextView tvSymptom, CardView cvMain) {
        Utils.showRequestDialog(context);
        Spinner spnAttrib, spnValue;
        View popupView = getLayoutInflater().inflate(R.layout.popup_attribute_value, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        spnAttrib = popupView.findViewById(R.id.spnAttrib);
        spnValue = popupView.findViewById(R.id.spnValue);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        List<AttributeList> attributeLists = new ArrayList<>();
        List<AttributeValueList> attributeValueLists = new ArrayList<>();
        attributeLists.add(0, new AttributeList(0, "Select Attribute"));
        Utils.showRequestDialog(context);
        Call<ProbAttribResp> call = RetrofitClient.getInstance().getApi().getProblemAttribute(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), getAllSymptomsModel.getId());
        call.enqueue(new Callback<ProbAttribResp>() {
            @Override
            public void onResponse(Call<ProbAttribResp> call, Response<ProbAttribResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getAttributeList().size() > 0) {
                            attributeLists.addAll(1, response.body().getAttributeList());
                            attributeListAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, attributeLists);
                            spnAttrib.setAdapter(attributeListAdp);
                            popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
                        } else {
                            callSave(getAllSymptomsModel.getId(), "", "");
                            getAllSymptomsModel.setSelected(!getAllSymptomsModel.isSelected());
                            cvMain.setCardBackgroundColor(getAllSymptomsModel.isSelected() ? getResources().getColor(R.color.blue_light) : getResources().getColor(R.color.gryText));
                            tvSymptom.setTextColor(getAllSymptomsModel.isSelected() ? getResources().getColor(R.color.white) : getResources().getColor(R.color.black));
                        }
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ProbAttribResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
        spnAttrib.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                attributeValueLists.clear();
                Utils.showRequestDialog(context);
                attributeValueLists.add(0, new AttributeValueList(0, "Select Value"));
                Call<AttribValueResp> call = RetrofitClient.getInstance().getApi().getProblemAttributeValue(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), attributeLists.get(i).getAttributeID());
                call.enqueue(new Callback<AttribValueResp>() {
                    @Override
                    public void onResponse(Call<AttribValueResp> call, Response<AttribValueResp> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getAttributeValueList().size() > 0) {
                                    attributeValueLists.addAll(1, response.body().getAttributeValueList());
                                }
                            }
                        }
                        attributeValueListAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, attributeValueLists);
                        spnValue.setAdapter(attributeValueListAdp);
                        Utils.hideDialog();
                    }

                    @Override
                    public void onFailure(Call<AttribValueResp> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    callSave(getAllSymptomsModel.getId(), String.valueOf(attributeLists.get(spnAttrib.getSelectedItemPosition()).getAttributeID()), String.valueOf(attributeValueLists.get(spnValue.getSelectedItemPosition()).getId()));
                    getAllSymptomsModel.setSelected(!getAllSymptomsModel.isSelected());
                    cvMain.setCardBackgroundColor(getAllSymptomsModel.isSelected() ? getResources().getColor(R.color.blue_light) : getResources().getColor(R.color.gryText));
                    tvSymptom.setTextColor(getAllSymptomsModel.isSelected() ? getResources().getColor(R.color.white) : getResources().getColor(R.color.black));
                    popupWindow.dismiss();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private class AdapterProblem extends RecyclerView.Adapter<Holder> {
        List<GetAllSuggestedProblemModel> data;

        AdapterProblem(List<GetAllSuggestedProblemModel> favList) {
            data = favList;
        }

        @NonNull
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_symptoms, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final Holder holder, final int position) {
            final GetAllSuggestedProblemModel getAllSymptomsModel = data.get(position);
            holder.tvSymptom.setText(getAllSymptomsModel.getProblemName());
            holder.tvSymptom.setTextColor(getAllSymptomsModel.isSelected() ? getResources().getColor(R.color.white) : getResources().getColor(R.color.black));
            holder.cvMain.setCardBackgroundColor(getAllSymptomsModel.isSelected() ? getResources().getColor(R.color.blue_light) : getResources().getColor(R.color.gryText));
            holder.tvSymptom.setOnClickListener(v -> {
                //if (getAllSymptomsModel.isSelected()) {
                    /*for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            if (getAllSymptomsModel.getId() == Integer.parseInt(jsonArray.getJSONObject(i).getString("problemID")))
                                jsonArray.remove(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    getAllSymptomsModel.setSelected(!getAllSymptomsModel.isSelected());
                    holder.cvMain.setCardBackgroundColor(getAllSymptomsModel.isSelected() ? getResources().getColor(R.color.blue_light) : getResources().getColor(R.color.gryText));
                    holder.tvSymptom.setTextColor(getAllSymptomsModel.isSelected() ? getResources().getColor(R.color.white) : getResources().getColor(R.color.black));*/
                //} else
                showPopup(recyclerViewSymptoms, getAllSymptomsModel, holder.tvSymptom, holder.cvMain);
            });
        }

        public int getItemCount() {
            return data == null ? 0 : data.size();
        }
    }

    private class AdapterSymptoms extends RecyclerView.Adapter<Holder> {
        List<GetAllSymptomsModel> data;

        AdapterSymptoms(List<GetAllSymptomsModel> favList) {
            data = favList;
        }

        @NonNull
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_symptoms, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final Holder holder, final int position) {
            final GetAllSymptomsModel getAllSymptomsModel = data.get(position);
            holder.tvSymptom.setText(getAllSymptomsModel.getProblemName());
            holder.tvSymptom.setTextColor(getAllSymptomsModel.isSelected() ? getResources().getColor(R.color.white) : getResources().getColor(R.color.black));
            holder.cvMain.setCardBackgroundColor(getAllSymptomsModel.isSelected() ? getResources().getColor(R.color.blue_light) : getResources().getColor(R.color.gryText));
            holder.tvSymptom.setOnClickListener(v -> showPopup1(recyclerViewSymptoms, getAllSymptomsModel, holder.tvSymptom, holder.cvMain));
        }

        public int getItemCount() {
            return data == null ? 0 : data.size();
        }
    }

    private boolean isSymptomsChecked() {
        if (getAllSymptomsModelList != null) {
            for (int i = 0; i < getAllSymptomsModelList.size(); i++) {
                if (getAllSymptomsModelList.get(i).isSelected()) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < getAllSuggestedProblemModelList.size(); i++) {
                if (getAllSuggestedProblemModelList.get(i).isSelected()) {
                    return true;
                }
            }
        }
        return false;
    }

    private class Holder extends RecyclerView.ViewHolder {
        TextView tvSymptom;
        CardView cvMain;

        Holder(View itemView) {
            super(itemView);
            tvSymptom = itemView.findViewById(R.id.tvSymptom);
            cvMain = itemView.findViewById(R.id.cvMain);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}