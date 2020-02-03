package com.trueform.era.his.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trueform.era.his.Adapter.AddInvestigationAdp;
import com.trueform.era.his.Adapter.RecyclerTouchListener;
import com.trueform.era.his.Model.Investigation;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.AddInvestigationResp;
import com.trueform.era.his.Utils.ClickListener;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddInvestigation extends Fragment {
    private RecyclerView rvAddInvestigation;
    private Spinner spnInvestigation;
    private ProgressDialog dialog;
    Context context;
    private Spinner spnConsultant;
    private List<Investigation> investigationList;
    private AddInvestigationResp addInvestigationResp;
    private AddInvestigationResp addInvestigationResp1=new AddInvestigationResp();
    private ArrayAdapter<Investigation> arrayAdapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddInvestigation() {
        // Required empty public constructor
    }

    public static AddInvestigation newInstance(String param1, String param2) {
        AddInvestigation fragment = new AddInvestigation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
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
        final View view = inflater.inflate(R.layout.fragment_add_investigation, container, false);
        TextView txtDate = view.findViewById(R.id.txtDate);
        TextView btnAdd = view.findViewById(R.id.btnAdd);
        TextView btnSave = view.findViewById(R.id.btnSave);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        spnConsultant=toolbar.findViewById(R.id.spnConsultant);
        context = view.getContext();
        dialog=new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        investigationList = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        rvAddInvestigation = view.findViewById(R.id.rvAddInvestigation);
        rvAddInvestigation.setLayoutManager(new LinearLayoutManager(context));
        dialog.show();
        Call<AddInvestigationResp> call = RetrofitClient.getInstance().getApi().getInvestigationUnitFrequency(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString());
        call.enqueue(new Callback<AddInvestigationResp>() {
            @Override
            public void onResponse(Call<AddInvestigationResp> call, Response<AddInvestigationResp> response) {
                try {
                    dialog.show();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            addInvestigationResp = response.body();
                            arrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_item_text_size, addInvestigationResp.getInvestigation());
                        }
                    }
                    dialog.dismiss();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddInvestigationResp> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        btnSave.setOnClickListener(view1 -> {
            if (SharedPrefManager.getInstance(context).getUser().getDesigid()==1) {
                sendInvestigation(SharedPrefManager.getInstance(context).getUser().getUserid());
            }
            else if (SharedPrefManager.getInstance(context).getUser().getDesigid()!=1 && spnConsultant.getSelectedItemPosition() != 0) {
                sendInvestigation(SharedPrefManager.getInstance(context).getConsultantList().get(spnConsultant.getSelectedItemPosition()).getUserid());
            } else Toast.makeText(context, "Consultant name required!", Toast.LENGTH_LONG).show();
        });
        btnAdd.setOnClickListener(this::showPopup);
        rvAddInvestigation.addOnItemTouchListener(new RecyclerTouchListener(context, rvAddInvestigation, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                investigationList.remove(position);
                addInvestigationResp1.setInvestigation(investigationList);
                rvAddInvestigation.setAdapter(new AddInvestigationAdp(context, addInvestigationResp1));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }

    private void sendInvestigation(int drId) {
        Log.v("hitApi:", RetrofitClient.BASE_URL+"Prescription/SavePrescriptionAndInvistigation");
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        boolean empty=true;
        try {
            if (investigationList.size() > 0) {
                for (int i = 0; i < investigationList.size(); i++) {
                    JSONObject object = new JSONObject();
                    object.put("itemID", investigationList.get(i).getItemID());
                    object.put("remark", investigationList.get(i).getRemark());
                    array.put(object);
                }
                empty=false;
            } else empty=true;
            jsonObject.put("PID", SharedPrefManager.getInstance(context).getPid());
            jsonObject.put("headID", SharedPrefManager.getInstance(context).getHeadID());
            jsonObject.put("subDeptID", SharedPrefManager.getInstance(context).getSubDept().getId());
            jsonObject.put("prescription", new JSONArray());
            jsonObject.put("ipNo", SharedPrefManager.getInstance(context).getIpNo());
            jsonObject.put("userID", SharedPrefManager.getInstance(context).getUser().getUserid());
            jsonObject.put("consultantName", drId);
            jsonObject.put("investigation", array);
            Log.v("hitApiArr", String.valueOf(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!empty) {
            AndroidNetworking.post(RetrofitClient.BASE_URL + "Prescription/SavePrescriptionAndInvistigation")
                    .addHeaders("accessToken", SharedPrefManager.getInstance(context).getUser().getAccessToken())
                    .addHeaders("userID", SharedPrefManager.getInstance(context).getUser().getUserid().toString())
                    .addJSONObjectBody(jsonObject)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(context, "Investigations saved successfully", Toast.LENGTH_SHORT).show();
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
        } else Toast.makeText(context, "Please add atleast 1 investigation", Toast.LENGTH_SHORT).show();
    }
    private void showPopup(View anchorView) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        spnInvestigation=popupView.findViewById(R.id.spnInvestigation);
        TextView btnAdd=popupView.findViewById(R.id.btnAdd);
        final EditText edtRemark=popupView.findViewById(R.id.edtRemark);
        spnInvestigation.setAdapter(arrayAdapter);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
        btnAdd.setOnClickListener(view -> {
            investigationList.add(new Investigation(addInvestigationResp.getInvestigation().get((int) spnInvestigation.getSelectedItemId()).getItemID(), addInvestigationResp.getInvestigation().get(spnInvestigation.getSelectedItemPosition()).getItemName(), addInvestigationResp.getInvestigation().get(spnInvestigation.getSelectedItemPosition()).getAmt(), edtRemark.getText().toString().trim(), true));
            addInvestigationResp1.setInvestigation(investigationList);
            rvAddInvestigation.setAdapter(new AddInvestigationAdp(context, addInvestigationResp1));
            popupWindow.dismiss();
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}