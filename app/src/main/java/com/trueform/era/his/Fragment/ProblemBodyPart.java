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
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.trueform.era.his.Model.GetBodyRegionModel;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.GetBodyOrganRegionRes;
import com.trueform.era.his.Utils.RetrofitClientOrgan;
import com.trueform.era.his.Utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProblemBodyPart.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProblemBodyPart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProblemBodyPart extends Fragment implements View.OnClickListener {
    private TextView tvSelectBodyPart;
    private ImageView ivChestClick, ivHeadClick, ivHandClickLeft, ivHandClickRight, ivLegClickRight, ivLegClickLeft, ivAbdomenClick, ivImage;
    Context context;
    private int activityID = 0;
    private TextView txtFrmDate;
    private TextView txtFrmTime;
    private TextView txtToDate;
    private TextView txtToTime;
    private static String fromDate = "";
    private static String toDate = "";
    private SimpleDateFormat format2;
    private TextView btnProceed;
    private int mYear = 0, mMonth = 0, mDay = 0, tYear = 0, tMonth = 0, tDay = 0, mHour = 0, mMinute = 0;
    private TranslateAnimation animateLeftEnter, animateRightEnter, animateLeftExit, animateRightExit;
    private ConstraintLayout clLeftAnim, clRightAnim;
    private RecyclerView recyclerViewLeft, recyclerViewRight;
    static String selectedRegionId = "";
    private List<GetBodyRegionModel> getBodyRegionModelList = new ArrayList<>();
    private AdapterLeft adapterLeft;
    private AdapterRight adapterRight;
    private Date today = new Date();
    private Date toToday = new Date();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProblemBodyPart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProblemBodyPart.
     */
    // TODO: Rename and change types and number of parameters
    public static ProblemBodyPart newInstance(String param1, String param2) {
        ProblemBodyPart fragment = new ProblemBodyPart();
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
        View v = inflater.inflate(R.layout.fragment_problem_body_part, container, false);
        ivChestClick = v.findViewById(R.id.ivChestClick);
        ivHeadClick = v.findViewById(R.id.ivHeadClick);
        clLeftAnim = v.findViewById(R.id.clLeftAnim);
        clRightAnim = v.findViewById(R.id.clRightAnim);
        context = v.getContext();
        btnProceed = v.findViewById(R.id.btnProceed);
        TextView txtSkip = v.findViewById(R.id.txtSkip);
        ivHandClickRight = v.findViewById(R.id.ivHandClickRight);
        ivHandClickLeft = v.findViewById(R.id.ivHandClickLeft);
        ivLegClickRight = v.findViewById(R.id.ivLegClickRight);
        tvSelectBodyPart = v.findViewById(R.id.tvSelectBodyPart);
        ivLegClickLeft = v.findViewById(R.id.ivLegClickLeft);
        ivAbdomenClick = v.findViewById(R.id.ivAbdomenClick);
        recyclerViewLeft = v.findViewById(R.id.recyclerViewLeft);
        recyclerViewRight = v.findViewById(R.id.recyclerViewRight);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewLeft.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewRight.setLayoutManager(new LinearLayoutManager(context));
        ivImage = v.findViewById(R.id.ivImage);
        txtFrmDate = v.findViewById(R.id.txtDateFrom);
        txtToTime = v.findViewById(R.id.txtTimeTo);
        txtToDate = v.findViewById(R.id.txtDateTo);
        txtFrmTime = v.findViewById(R.id.txtTimeFrom);
        ConstraintLayout clLeftAnimCenter = v.findViewById(R.id.clLeftAnimCenter);
        ConstraintLayout clLeftAnimHead = v.findViewById(R.id.clLeftAnimHead);
        ConstraintLayout clRightAnimCenter = v.findViewById(R.id.clRightAnimCenter);
        ConstraintLayout clRightAnimHead = v.findViewById(R.id.clRightAnimHead);
        clRightAnim = v.findViewById(R.id.clRightAnim);
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
        btnProceed.setOnClickListener(this);
        txtSkip.setOnClickListener(this);
        format2 = new SimpleDateFormat("hh:mm a");
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        tYear = c.get(Calendar.YEAR);
        tMonth = c.get(Calendar.MONTH);
        tDay = c.get(Calendar.DAY_OF_MONTH);
        fromDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        toDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtFrmDate.setText(Utils.formatDate(fromDate));
        txtToDate.setText(Utils.formatDate(toDate));
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
        public HolderRight onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderRight(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_organs, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderRight holder, final int position) {
            final GetBodyRegionModel getBodyRegionModel = data.get(position);
            setAdapterImage(holder.ivImage, getBodyRegionModel,
                    getResId(getBodyRegionModel.getOrganImagePath().toLowerCase() + "_selected"),
                    getResId(getBodyRegionModel.getOrganImagePath().toLowerCase()));
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

    private class AdapterLeft extends RecyclerView.Adapter<AdapterLeft.HolderLeft> {
        List<GetBodyRegionModel> data;

        AdapterLeft(List<GetBodyRegionModel> favList) {
            data = favList;
        }

        @NonNull
        public HolderLeft onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderLeft(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_organs, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderLeft holder, final int position) {
            final GetBodyRegionModel getBodyRegionModel = data.get(position);
            setAdapterImage(holder.ivImage, getBodyRegionModel,
                    getResId(getBodyRegionModel.getOrganImagePath().toLowerCase() + "_selected"),
                    getResId(getBodyRegionModel.getOrganImagePath().toLowerCase()));
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

    private void setAdapterImage(final ImageView imageView, final GetBodyRegionModel getBodyRegionModel,
                                 final int selectedResId, final int unselectedResId) {
        imageView.setImageResource(getBodyRegionModel.isSelected() ? selectedResId : unselectedResId);
        imageView.setOnClickListener(v -> {
            getBodyRegionModel.setSelected(!getBodyRegionModel.isSelected());
            imageView.setImageResource(getBodyRegionModel.isSelected() ? selectedResId : unselectedResId);
            if (isOrganSelected()) {
                btnProceed.setVisibility(View.VISIBLE);
            } else {
                btnProceed.setVisibility(View.GONE);
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

    @Override
    public void onClick(View v) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
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
                activityID = chest;
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
                activityID = abdomen;
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
                activityID = head;
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
                activityID = right_Hand_Movement;
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
                activityID = left_Hand_Movement;
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
                activityID = right_Leg_Movement;
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
                activityID = left_Leg_Movement;
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
                break;
            case R.id.btnProceed:
                //selectedRegionId = "";
                for (int i = 0; i < getBodyRegionModelList.size(); i++) {
                    if (getBodyRegionModelList.get(i).isSelected()) {
                        if (selectedRegionId.equalsIgnoreCase("")) {
                            selectedRegionId = String.valueOf(getBodyRegionModelList.get(i).getId()).trim();
                        } else {
                            if (!selectedRegionId.contains(String.valueOf(getBodyRegionModelList.get(i).getId()))) {
                                selectedRegionId = selectedRegionId.concat(",").trim() + String.valueOf(getBodyRegionModelList.get(i).getId()).trim();
                            }
                        }
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Fragment fragment=new ProblemSymptomsSelect();
                        Bundle args = new Bundle();
                        args.putString("key", "1");
                        args.putString("to", String.valueOf(format.format(toToday)));
                        args.putString("from", String.valueOf(format.format(today)));
                        fragment.setArguments(args);
                        fragmentTransaction.replace(R.id.content_frame, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
                Log.v("regionId", selectedRegionId);
                break;
            case R.id.txtSkip:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment=new ProblemSymptomsSelect();
                Bundle args = new Bundle();
                args.putString("key", "0");
                args.putString("to", String.valueOf(format.format(toToday)));
                args.putString("from", String.valueOf(format.format(today)));
                fragment.setArguments(args);
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
        if (v.getId() != R.id.txtTimeTo && v.getId() != R.id.txtDateTo && v.getId() != R.id.txtTimeFrom && v.getId() != R.id.txtDateFrom && v.getId() != R.id.btnProceed && v.getId() != R.id.txtSkip) {
            tvSelectBodyPart.setText(v.getContentDescription());
            hitGetBodyOrganRegion(String.valueOf(activityID));
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
