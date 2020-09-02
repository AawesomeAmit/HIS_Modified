package com.his.android.Activity.UploadMultipleImg.DischargePatient;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.his.android.Activity.UploadMultipleImg.Api;
import com.his.android.Activity.UploadMultipleImg.ApiUtilsLocalUrl;
import com.his.android.Activity.UploadMultipleImg.Universalres;
import com.his.android.R;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DischargePatient extends BaseFragment {

    static SharedPrefManager sharedPrefManager;
    private static Activity context;

    EditText etRemark;
    Spinner spnType;
    List<DischargeTypeList> dischargeType = new ArrayList<>();

    private String dischargeTypeID="";

    TextView tvSubmit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discharge_patient, container, false);

        context = mActivity;

        sharedPrefManager = SharedPrefManager.getInstance(getActivity());

        etRemark = view.findViewById(R.id.etReview);
        tvSubmit = view.findViewById(R.id.tvSubmit);
        spnType = view.findViewById(R.id.spnDischargeType);

        if (ConnectivityChecker.checker(mActivity)) {
            hitGetDischargeType(mActivity);
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

        tvSubmit.setOnClickListener(view1 -> {
            try {

                if (dischargeTypeID.equals("0") || dischargeTypeID.equals("")) {
                    Toast.makeText(mActivity, "Please select discharge type", Toast.LENGTH_SHORT).show();
                }
                else if (etRemark.getText().toString().isEmpty()) {
                    Toast.makeText(mActivity, "Please enter reason", Toast.LENGTH_SHORT).show();
                }

                else {

                    if (ConnectivityChecker.checker(context)) {
                        hitGetDischargePatient(mActivity);

                    } else {
                        Toast.makeText(context, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                }


            } catch (Exception e) {

                e.printStackTrace();

            }
        });

        return view;
    }

    //hit Discharge Patient
    private void hitGetDischargePatient(Activity mActivity) {
        JSONArray array=new JSONArray();
        JSONObject object=new JSONObject();
        try {
            object.put("detailID", 0);
            object.put("details", etRemark.getText().toString().trim());
            object.put("pdmID", 106);
            array.put(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dischargeType.clear();
        Utils.showRequestDialog(mActivity);

        Utils.showRequestDialog(mActivity);
        Api iRestInterfaces = ApiUtilsLocalUrl.getAPIService();
        Call<Universalres> call = iRestInterfaces.hitDischargePatient(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                dischargeTypeID,
                SharedPrefManager.getInstance(mActivity).getIpNo(),
                "",
                String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                String.valueOf(array)
        );

        call.enqueue(new Callback<Universalres>() {
            @Override
            public void onResponse(Call<Universalres> call, Response<Universalres> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(context, "Discharged Successfully!", Toast.LENGTH_SHORT).show();
                }
                else {
                    // error case
                    switch (response.code()) {
                        case 400:
                            Toast.makeText(context, "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 401:
                            Toast.makeText(context, "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(context, "Data not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(context, "Internal Server Error", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<Universalres> call, Throwable t) {

                Utils.hideDialog();
                // progressDialog.dismiss();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

    //Hit Get Discharge Type
    private void hitGetDischargeType(Activity mActivity) {
        dischargeType.clear();

        Utils.showRequestDialog(mActivity);

        Api iRestInterfaces = ApiUtilsLocalUrl.getAPIService();
        Call<GetDischargeTypeRes> call = iRestInterfaces.getDischargeType(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString()
        );

        call.enqueue(new Callback<GetDischargeTypeRes>() {
            @Override
            public void onResponse(Call<GetDischargeTypeRes> call, Response<GetDischargeTypeRes> response) {

                if (response.isSuccessful()) {

                    // getTaskProjectLists.addAll(response.body().getProject());

                    //ArrayAdapter arrayAdapter = new ArrayAdapter(mActivity, R.layout.inflate_spinner_item, getTaskProjectLists);
                    //actvProject.setAdapter(arrayAdapter);
                    dischargeType.add(new DischargeTypeList());
                    dischargeType.get(0).setId(0);
                    dischargeType.get(0).setDischargeType("Select Discharge Type");
                    dischargeType.addAll(response.body().getDischargeType());

                    ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.inflate_spinner_item, dischargeType);
                    spnType.setAdapter(arrayAdapter);

                    spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                           /* if (position == 0) {
                                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey));
                            }*/

                            dischargeTypeID = String.valueOf(dischargeType.get(spnType.getSelectedItemPosition()).getId());

                            Log.v("asfasgtrhasb", String.valueOf(dischargeType.get(spnType.getSelectedItemPosition()).getId()));

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } else {
                    // error case
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(mActivity, "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(mActivity, "Internal Server Error", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(mActivity, "No Data Found", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetDischargeTypeRes> call, Throwable t) {

                Utils.hideDialog();
                // progressDialog.dismiss();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }
}