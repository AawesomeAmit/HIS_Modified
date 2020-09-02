package com.his.android.Fragment;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.hsalf.smilerating.SmileRating;
import com.his.android.Adapter.ActivityDisplayAdp;
import com.his.android.Model.ActivityMaster;
import com.his.android.R;
import com.his.android.Response.ActivityDataResp;
import com.his.android.Response.ActivityResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActivityInput.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActivityInput#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityInput extends Fragment implements View.OnClickListener {
    private TextView txtFrmDate;
    private TextView txtFrmTime;
    private TextView txtToDate;
    private TextView txtToTime;
    private Spinner spnActivity;
    private static String fromDate = "";
    private static String toDate = "";
    private static RecyclerView rvActivity;
    private SimpleDateFormat format2;
    private List<ActivityMaster> activityList;
    private ArrayAdapter<ActivityMaster> activityAdapter;
    private SmileRating rating;
    private int mYear = 0, mMonth = 0, mDay = 0, tYear = 0, tMonth = 0, tDay = 0, mHour=0, mMinute=0;
    private Date today = new Date();
    private Date toToday = new Date();
    @SuppressLint("StaticFieldLeak")
    static Context context;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ActivityInput() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivityInput.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityInput newInstance(String param1, String param2) {
        ActivityInput fragment = new ActivityInput();
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
        View view=inflater.inflate(R.layout.fragment_activity_input, container, false);
        spnActivity=view.findViewById(R.id.spnActivity);
        txtFrmDate=view.findViewById(R.id.txtFrmDate);
        txtToTime=view.findViewById(R.id.txtToTime);
        TextView btnSave = view.findViewById(R.id.btnSave);
        txtToDate=view.findViewById(R.id.txtToDate);
        txtFrmTime=view.findViewById(R.id.txtFrmTime);
        rvActivity=view.findViewById(R.id.rvActivity);
        rating=view.findViewById(R.id.rating);
        context=view.getContext();
        Calendar c = Calendar.getInstance();
        Utils.showRequestDialog(context);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        tYear = c.get(Calendar.YEAR);
        tMonth = c.get(Calendar.MONTH);
        tDay = c.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        format2 = new SimpleDateFormat("hh:mm a");
        fromDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        toDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtFrmDate.setText(Utils.formatDate(fromDate));
        txtToDate.setText(Utils.formatDate(fromDate));
        txtFrmTime.setText(format2.format(today));
        txtToTime.setText(format2.format(today));
        activityList=new ArrayList<>();
        activityList.add(0, new ActivityMaster(0, "Select"));
        txtFrmDate.setOnClickListener(this);
        txtFrmTime.setOnClickListener(this);
        txtToDate.setOnClickListener(this);
        txtToTime.setOnClickListener(this);
        rvActivity.setLayoutManager(new LinearLayoutManager(context));
        bind();
        btnSave.setOnClickListener(this);
        Call<ActivityResp> call= RetrofitClient.getInstance().getApi().getPatientActivityMaster(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<ActivityResp>() {
            @Override
            public void onResponse(Call<ActivityResp> call, Response<ActivityResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        activityList.addAll(1, response.body().getActivityMaster());
                        //Log.v("activities", String.valueOf(response.body().getActivityMaster()));
                    }
                }
                activityAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, activityList);
                spnActivity.setAdapter(activityAdapter);
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ActivityResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
        spnActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spnActivity.getSelectedItem().toString().equalsIgnoreCase("Mood"))
                    rating.setVisibility(View.VISIBLE);
                else rating.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
    private void bind(){
        Utils.showRequestDialog(context);
        Call<ActivityDataResp> call= RetrofitClient.getInstance().getApi().getPatientPhysicalActivityList(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<ActivityDataResp>() {
            @Override
            public void onResponse(Call<ActivityDataResp> call, Response<ActivityDataResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        ActivityDisplayAdp displayAdp=new ActivityDisplayAdp(context, response.body().getActivityList());
                        rvActivity.setAdapter(displayAdp);
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ActivityDataResp> call, Throwable t) {
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
    public void delete(int id){
        Utils.showRequestDialog(context);
        Call<ResponseBody> call= RetrofitClient.getInstance().getApi().deletePatientPhysicalActivity(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), id, SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    bind();
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideDialog();
            }
        });
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtFrmDate) {
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
        } else if (view.getId() == R.id.txtToDate) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
                    (view12, year, monthOfYear, dayOfMonth) -> {
                        tYear = year;
                        tMonth = monthOfYear;
                        tDay = dayOfMonth;
                        toDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        toToday.setDate(dayOfMonth);
                        toToday.setMonth(monthOfYear);
                        toToday.setYear(year - 1900);
                        txtToDate.setText(Utils.formatDate(toDate));
                    }, tYear, tMonth, tDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(toToday.getTime());
        } else if (view.getId() == R.id.txtToTime) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, i, i1) -> {
                mHour = i;
                mMinute = i1;
                toToday.setHours(mHour);
                toToday.setMinutes(mMinute);
                txtToTime.setText(format2.format(toToday));
            }, mHour, mMinute, false);
            timePickerDialog.updateTime(toToday.getHours(), toToday.getMinutes());
            timePickerDialog.show();
        }
        else if(view.getId()==R.id.btnSave){
            if(spnActivity.getSelectedItemPosition()!=0){
                saveActivity();
                /*@SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                JSONArray activityArray = new JSONArray();
                JSONObject object = new JSONObject();
                try {
                    object.put("activityID", activityList.get(spnActivity.getSelectedItemPosition()).getId().toString());
                    object.put("rating", String.valueOf(rating.getRating()));
                    object.put("timeFrom", format.format(today));
                    object.put("timeTo", format.format(toToday));
                    activityArray.put(object);
                    Log.v("hitApiArr", String.valueOf(object));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Call<ResponseBody> call= RetrofitClient.getInstance().getApi().savePatientPhysicalActivity(SharedPrefManager.getInstance(context).getUser().getAccessToken(), activityArray, pid, SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            bind();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });*/
            }
        }
    }
    private void saveActivity() {
        Utils.showRequestDialog(context);
        Log.v("hitApi:", RetrofitClient.BASE_URL + "PatientPhysicalActivity/SavePatientPhysicalActivity");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject object = new JSONObject();
            object.put("activityID", activityList.get(spnActivity.getSelectedItemPosition()).getId().toString());
            object.put("rating", String.valueOf(rating.getRating()));
            object.put("timeFrom", format.format(today));
            object.put("timeTo", format.format(toToday));
            array.put(object);
            jsonObject.put("PID", SharedPrefManager.getInstance(context).getPid());
            jsonObject.put("subDeptID", SharedPrefManager.getInstance(context).getSubDept().getId());
            jsonObject.put("userID", SharedPrefManager.getInstance(context).getUser().getUserid());
            jsonObject.put("ListPatientPhysicalActivity", array);
            Log.v("hitApiArr", String.valueOf(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(RetrofitClient.BASE_URL + "PatientPhysicalActivity/SavePatientPhysicalActivity")
                .addHeaders("accessToken", SharedPrefManager.getInstance(context).getUser().getAccessToken())
                .addHeaders("userID", SharedPrefManager.getInstance(context).getUser().getUserid().toString())
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Activity saved successfully", Toast.LENGTH_SHORT).show();
                        bind();
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
                        bind();
                        Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }
                });
    }
}
