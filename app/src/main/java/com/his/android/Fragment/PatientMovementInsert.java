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
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.snackbar.Snackbar;
import com.his.android.Model.GetBodyRegionModel;
import com.his.android.R;
import com.his.android.Response.GetBodyOrganRegionRes;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.RetrofitClientOrgan;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientMovementInsert extends Fragment implements View.OnClickListener {
    private TextView tvSelectBodyPart;
    private ImageView ivChestClick, ivHeadClick, ivHandClickLeft, ivHandClickRight, ivLegClickRight, ivLegClickLeft, ivAbdomenClick, ivImage;
    Context context;
    private int activityID=0;
    private TextView txtFrmDate;
    private TextView txtFrmTime;
    private TextView txtToDate;
    private TextView txtToTime;
    private static String fromDate = "";
    private static String toDate = "";
    private SimpleDateFormat format2;
    private AdapterLeft adapterLeft;
    private AdapterRight adapterRight;
    private TranslateAnimation animateLeftEnter, animateRightEnter, animateLeftExit, animateRightExit;
    private ConstraintLayout clLeftAnim, clRightAnim;
    private RecyclerView recyclerViewLeft, recyclerViewRight;
    private List<GetBodyRegionModel> getBodyRegionModelList = new ArrayList<>();
    private int mYear = 0, mMonth = 0, mDay = 0, tYear = 0, tMonth = 0, tDay = 0, mHour=0, mMinute=0;
    private Date today = new Date();
    private Date toToday = new Date();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PatientMovementInsert() {
        // Required empty public constructor
    }

    public static PatientMovementInsert newInstance(String param1, String param2) {
        PatientMovementInsert fragment = new PatientMovementInsert();
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
        View v=inflater.inflate(R.layout.fragment_patient_movement_insert, container, false);
        ivChestClick = v.findViewById(R.id.ivChestClick);
        ivHeadClick = v.findViewById(R.id.ivHeadClick);
        ivHandClickRight = v.findViewById(R.id.ivHandClickRight);
        ivHandClickLeft = v.findViewById(R.id.ivHandClickLeft);
        ivLegClickRight = v.findViewById(R.id.ivLegClickRight);
        tvSelectBodyPart = v.findViewById(R.id.tvSelectBodyPart);
        ivLegClickLeft = v.findViewById(R.id.ivLegClickLeft);
        ivAbdomenClick = v.findViewById(R.id.ivAbdomenClick);
        ivImage = v.findViewById(R.id.ivImage);
        txtFrmDate=v.findViewById(R.id.txtDateFrom);
        txtToTime=v.findViewById(R.id.txtTimeTo);
        txtToDate=v.findViewById(R.id.txtDateTo);
        txtFrmTime=v.findViewById(R.id.txtTimeFrom);
        clLeftAnim = v.findViewById(R.id.clLeftAnim);
        clRightAnim = v.findViewById(R.id.clRightAnim);
        recyclerViewLeft = v.findViewById(R.id.recyclerViewLeft);
        recyclerViewRight = v.findViewById(R.id.recyclerViewRight);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewLeft.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewRight.setLayoutManager(new LinearLayoutManager(context));
        ivChestClick.setOnClickListener(this);
        ivHeadClick.setOnClickListener(this);
        ivHandClickRight.setOnClickListener(this);
        ivHandClickLeft.setOnClickListener(this);
        ivLegClickRight.setOnClickListener(this);
        ivLegClickLeft.setOnClickListener(this);
        ivAbdomenClick.setOnClickListener(this);
        txtFrmDate.setOnClickListener(this);
        txtFrmTime.setOnClickListener(this);
        txtToDate.setOnClickListener(this);
        txtToTime.setOnClickListener(this);
        format2 = new SimpleDateFormat("hh:mm a");
        context=v.getContext();
        Calendar c = Calendar.getInstance();
        Utils.showRequestDialog(context);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        tYear = c.get(Calendar.YEAR);
        tMonth = c.get(Calendar.MONTH);
        tDay = c.get(Calendar.DAY_OF_MONTH);
        fromDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        toDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtFrmDate.setText(Utils.formatDate(fromDate));
        txtToDate.setText(Utils.formatDate(fromDate));
        txtFrmTime.setText(format2.format(today));
        txtToTime.setText(format2.format(today));
        return v;
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

    @Override
    public void onClick(View v) {
        int left_Hand_Movement = 4;
        int left_Leg_Movement = 5;
        int right_Hand_Movement = 4;
        int right_Leg_Movement = 5;
        int head = 1;
        int abdomen = 3;
        int chest = 2;
        animateLeftEnter = new TranslateAnimation(0, 0, getView().getWidth(), 0);
        animateRightEnter = new TranslateAnimation(0, 0, getView().getWidth(), 0);
        animateLeftExit = new TranslateAnimation(0, 0, 0, -getView().getWidth());
        animateRightExit = new TranslateAnimation(0, 0, 0, -getView().getWidth());
        switch (v.getId()) {
            case R.id.ivChestClick:
                ivImage.setImageResource(R.mipmap.man_chest);
                ivChestClick.setVisibility(View.INVISIBLE);
                ivHandClickRight.setVisibility(View.VISIBLE);
                ivHandClickLeft.setVisibility(View.VISIBLE);
                ivHeadClick.setVisibility(View.VISIBLE);
                ivLegClickRight.setVisibility(View.VISIBLE);
                ivLegClickLeft.setVisibility(View.VISIBLE);
                ivAbdomenClick.setVisibility(View.VISIBLE);
                activityID= chest;
                break;
            case R.id.ivAbdomenClick:
                ivImage.setImageResource(R.mipmap.man_abdomen);
                ivAbdomenClick.setVisibility(View.INVISIBLE);
                ivChestClick.setVisibility(View.VISIBLE);
                ivHandClickRight.setVisibility(View.VISIBLE);
                ivHandClickLeft.setVisibility(View.VISIBLE);
                ivHeadClick.setVisibility(View.VISIBLE);
                ivLegClickRight.setVisibility(View.VISIBLE);
                ivLegClickLeft.setVisibility(View.VISIBLE);
                activityID= abdomen;
                break;
            case R.id.ivHeadClick:
                ivImage.setImageResource(R.mipmap.man_head);
                ivHeadClick.setVisibility(View.INVISIBLE);
                ivChestClick.setVisibility(View.VISIBLE);
                ivHandClickRight.setVisibility(View.VISIBLE);
                ivHandClickLeft.setVisibility(View.VISIBLE);
                ivLegClickRight.setVisibility(View.VISIBLE);
                ivLegClickLeft.setVisibility(View.VISIBLE);
                ivAbdomenClick.setVisibility(View.VISIBLE);
                activityID= head;
                break;
            case R.id.ivHandClickRight:
                ivImage.setImageResource(R.mipmap.man_right_hand);
                ivHeadClick.setVisibility(View.VISIBLE);
                ivChestClick.setVisibility(View.VISIBLE);
                ivHandClickRight.setVisibility(View.INVISIBLE);
                ivHandClickLeft.setVisibility(View.VISIBLE);
                ivLegClickRight.setVisibility(View.VISIBLE);
                ivLegClickLeft.setVisibility(View.VISIBLE);
                ivAbdomenClick.setVisibility(View.VISIBLE);
                activityID= right_Hand_Movement;
                break;
            case R.id.ivHandClickLeft:
                ivImage.setImageResource(R.mipmap.man_left_hand);
                ivHeadClick.setVisibility(View.VISIBLE);
                ivChestClick.setVisibility(View.VISIBLE);
                ivHandClickRight.setVisibility(View.VISIBLE);
                ivHandClickLeft.setVisibility(View.INVISIBLE);
                ivLegClickRight.setVisibility(View.VISIBLE);
                ivLegClickLeft.setVisibility(View.VISIBLE);
                ivAbdomenClick.setVisibility(View.VISIBLE);
                activityID= left_Hand_Movement;
                break;
            case R.id.ivLegClickRight:
                ivImage.setImageResource(R.mipmap.man_right_leg);
                ivHeadClick.setVisibility(View.VISIBLE);
                ivChestClick.setVisibility(View.VISIBLE);
                ivHandClickRight.setVisibility(View.VISIBLE);
                ivHandClickLeft.setVisibility(View.VISIBLE);
                ivLegClickRight.setVisibility(View.INVISIBLE);
                ivLegClickLeft.setVisibility(View.VISIBLE);
                ivAbdomenClick.setVisibility(View.VISIBLE);
                activityID= right_Leg_Movement;
                break;
            case R.id.ivLegClickLeft:
                ivImage.setImageResource(R.mipmap.man_left_leg);
                ivHeadClick.setVisibility(View.VISIBLE);
                ivChestClick.setVisibility(View.VISIBLE);
                ivHandClickRight.setVisibility(View.VISIBLE);
                ivHandClickLeft.setVisibility(View.VISIBLE);
                ivLegClickRight.setVisibility(View.VISIBLE);
                ivLegClickLeft.setVisibility(View.INVISIBLE);
                ivAbdomenClick.setVisibility(View.VISIBLE);
                activityID= left_Leg_Movement;
                break;
            case R.id.txtDateFrom:
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
            case R.id.txtTimeFrom:
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
            case R.id.txtDateTo:
                DatePickerDialog toDatePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
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
                toDatePickerDialog.show();
                toDatePickerDialog.getDatePicker().setMaxDate(toToday.getTime());
                break;
            case R.id.txtTimeTo:
                TimePickerDialog toTimePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, i, i1) -> {
                    mHour = i;
                    mMinute = i1;
                    toToday.setHours(mHour);
                    toToday.setMinutes(mMinute);
                    txtToTime.setText(format2.format(toToday));
                }, mHour, mMinute, false);
                toTimePickerDialog.updateTime(toToday.getHours(), toToday.getMinutes());
                toTimePickerDialog.show();
        }
        if(v.getId()!=R.id.txtTimeTo&&v.getId()!=R.id.txtDateTo&&v.getId()!=R.id.txtTimeFrom&&v.getId()!=R.id.txtDateFrom){
            tvSelectBodyPart.setText(v.getContentDescription());
            hitGetBodyOrganRegion(String.valueOf(activityID));
        }
    }
    private void confirmation(int activityID){
        new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to submit the movement?")
                .setCancelable(true)
                .setPositiveButton(
                        "Yes",
                        (dialog, id) -> saveActivity(activityID))
                .setNegativeButton(
                        "No",
                        (dialog, id) -> dialog.cancel())
                .show();
    }
    private void saveActivity(int activityID) {
        Utils.showRequestDialog(context);
        Log.v("hitApi:", RetrofitClient.BASE_URL + "PatientPhysicalActivity/SavePatientPhysicalActivity");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject object = new JSONObject();
            object.put("activityID", activityID);
            object.put("rating", "");
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

    private void hitGetBodyOrganRegion(String regionId) {
        Utils.showRequestDialog(context);
        Call<GetBodyOrganRegionRes> call = RetrofitClientOrgan.getInstance().getApi().getBodyOrganRegion("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiZmlyc3ROYW1lIjoic2FkZGFtIiwibGFzdE5hbWUiOm51bGwsImVtYWlsSWQiOm51bGwsIm1vYmlsZU5vIjoiODk2MDI1MzEzMyIsImNvdW50cnkiOiJJTkRJQSIsInppcENvZGUiOiIyMjYwMjAiLCJvY2N1cGF0aW9uSWQiOjEsImFnZSI6bnVsbCwiZ2VuZGVyIjpudWxsLCJoZWlnaHRJbkZlZXQiOm51bGwsImhlaWdodEluSW5jaCI6bnVsbCwid2VpZ2h0IjpudWxsLCJwYWNrYWdlTmFtZSI6IkZyZWUiLCJpYXQiOjE1NjMwMTM4MDUsImV4cCI6MTU5NDU0OTgwNX0.l220lljQyTXmDPD-gyU53H4vV-I1GDPociKcp2qrWe8", "2", regionId);
        call.enqueue(new Callback<GetBodyOrganRegionRes>() {
            @Override
            public void onResponse(Call<GetBodyOrganRegionRes> call, Response<GetBodyOrganRegionRes> response) {
                if (response != null && response.body().getResponseCode() == 1) {
                    getBodyRegionModelList = response.body().getResponseValue();
                    adapterLeft = new AdapterLeft(getBodyRegionModelList);
                    recyclerViewLeft.setAdapter(adapterLeft);
                    adapterRight = new AdapterRight(getBodyRegionModelList);
                    recyclerViewRight.setAdapter(adapterRight);
                    if (clLeftAnim.getVisibility() == View.VISIBLE) {
                        animateLeftExit.setDuration(500);
                        animateRightExit.setDuration(500);
                        clLeftAnim.startAnimation(animateLeftExit);
                        clRightAnim.startAnimation(animateRightExit);
                        animateLeftExit.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                clLeftAnim.setVisibility(View.VISIBLE);
                                clRightAnim.setVisibility(View.VISIBLE);
                                animateLeftEnter.setDuration(1500);
                                animateLeftEnter.setInterpolator(new OvershootInterpolator(1));
                                animateLeftEnter.setFillAfter(true);
                                clLeftAnim.startAnimation(animateLeftEnter);
                                animateRightEnter.setDuration(1500);
                                animateRightEnter.setInterpolator(new OvershootInterpolator(1));
                                animateRightEnter.setFillAfter(true);
                                clRightAnim.startAnimation(animateRightEnter);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    } else {
                        clLeftAnim.setVisibility(View.VISIBLE);
                        clRightAnim.setVisibility(View.VISIBLE);
                        animateLeftEnter.setDuration(1500);
                        animateLeftEnter.setInterpolator(new OvershootInterpolator(1));
                        animateLeftEnter.setFillAfter(true);
                        clLeftAnim.startAnimation(animateLeftEnter);
                        animateRightEnter.setDuration(1500);
                        animateRightEnter.setInterpolator(new OvershootInterpolator(1));
                        animateRightEnter.setFillAfter(true);
                        clRightAnim.startAnimation(animateRightEnter);
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetBodyOrganRegionRes> call, Throwable t) {
                Utils.hideDialog();
                Snackbar.make(tvSelectBodyPart, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private class AdapterRight extends RecyclerView.Adapter<AdapterRight.HolderRight> {
        List<GetBodyRegionModel> data;

        AdapterRight(List<GetBodyRegionModel> favList) {
            data = favList;
        }

        @NonNull
        public AdapterRight.HolderRight onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AdapterRight.HolderRight(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_organs, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final AdapterRight.HolderRight holder, final int position) {
            final GetBodyRegionModel getBodyRegionModel = data.get(position);
            setAdapterImage(holder.ivImage, getBodyRegionModel,
                    getResId(getBodyRegionModel.getRegionName().toLowerCase() + "_selected"),
                    getResId(getBodyRegionModel.getRegionName().toLowerCase()));
        }

        private class HolderRight extends RecyclerView.ViewHolder {
            ImageView ivImage;
            HolderRight(View itemView) {
                super(itemView);
                ivImage = itemView.findViewById(R.id.ivImage);
            }
        }

        public int getItemCount() {
            Collections.reverse(data);
            if (data.size() > 5) {
                List<GetBodyRegionModel> secondHalf = new ArrayList<>();
                for (int i = data.size() / 2; i < data.size(); i++)
                    secondHalf.add(data.get(i));

                return secondHalf.size();
            } else {
                return 0;
            }
        }
    }

    private void setAdapterImage(final ImageView imageView, final GetBodyRegionModel getBodyRegionModel,
                                 final int selectedResId, final int unselectedResId) {
        imageView.setImageResource(getBodyRegionModel.isSelected() ? selectedResId : unselectedResId);
        imageView.setOnClickListener(v -> {
            getBodyRegionModel.setSelected(!getBodyRegionModel.isSelected());
            imageView.setImageResource(getBodyRegionModel.isSelected() ? selectedResId : unselectedResId);
            if (isOrganSelected()) {
                confirmation(activityID);
            } else {
                //btnProceed.setVisibility(View.GONE);
            }
        });
    }

    private boolean isOrganSelected() {
        for (int i = 0; i < getBodyRegionModelList.size(); i++) {
            if (getBodyRegionModelList.get(i).isSelected()) {
                return true;
            }
        }
        return false;
    }

    private static int getResId(String pDrawableName) {
        int id = 0;
        try {
            id = R.mipmap.class.getField(pDrawableName).getInt(null);
            return id;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return id;
    }


    private class AdapterLeft extends RecyclerView.Adapter<AdapterLeft.HolderLeft> {
        List<GetBodyRegionModel> data;

        AdapterLeft(List<GetBodyRegionModel> favList) {
            data = favList;
        }

        @NonNull
        public AdapterLeft.HolderLeft onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AdapterLeft.HolderLeft(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_organs, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final AdapterLeft.HolderLeft holder, final int position) {
            final GetBodyRegionModel getBodyRegionModel = data.get(position);
            setAdapterImage(holder.ivImage, getBodyRegionModel,
                    getResId(getBodyRegionModel.getRegionName().toLowerCase() + "_selected"),
                    getResId(getBodyRegionModel.getRegionName().toLowerCase()));
        }

        private class HolderLeft extends RecyclerView.ViewHolder {
            ImageView ivImage;

            HolderLeft(View itemView) {
                super(itemView);
                ivImage = itemView.findViewById(R.id.ivImage);
            }
        }

        public int getItemCount() {
            if (data.size() > 5) {
                List<GetBodyRegionModel> firstHalf = new ArrayList<>();
                for (int i = 0; i < data.size() / 2; i++)
                    firstHalf.add(data.get(i));
                return firstHalf.size();
            } else {
                return data.size();
            }
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
