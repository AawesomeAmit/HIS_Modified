package com.trueform.era.his.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.trueform.era.his.Model.VitalMaster;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.RangeMasterResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VitalGraph extends Fragment implements View.OnClickListener {
    Context context;
    Calendar c;
    private TextView txtDate;
    String date = "";
    int mYear = 0, mMonth = 0, mDay = 0;
    private List<VitalMaster> vitalList;
    private MultiAutoCompleteTextView edtVital;
    private ArrayAdapter<VitalMaster> vitalAdp;
    static private StringBuilder itemId;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public VitalGraph() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VitalGraph.
     */
    // TODO: Rename and change types and number of parameters
    public static VitalGraph newInstance(String param1, String param2) {
        VitalGraph fragment = new VitalGraph();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_vital_graph, container, false);
        context=view.getContext();
        Utils.showRequestDialog(context);
        edtVital=view.findViewById(R.id.edtVital);
        txtDate=view.findViewById(R.id.txtDate);
        TextView btnResult = view.findViewById(R.id.btnSubmit);
        txtDate.setOnClickListener(this);
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        btnResult.setOnClickListener(this);
        vitalList=new ArrayList<>();
        itemId=new StringBuilder();
        c = Calendar.getInstance();
        date = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.DAY_OF_MONTH);
        txtDate.setText(Utils.formatDate(date));
        Call<RangeMasterResp> call= RetrofitClient.getInstance().getApi().getPageLoadMaster(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<RangeMasterResp>() {
            @Override
            public void onResponse(Call<RangeMasterResp> call, Response<RangeMasterResp> response) {
                if(response.isSuccessful()){
                    if((response.body() != null ? response.body().getVitalMaster().size() : 0) >0)
                        vitalList.addAll(0, response.body().getVitalMaster());
                }
                vitalAdp=new ArrayAdapter<>(context, R.layout.spinner_layout, vitalList);
                edtVital.setAdapter(vitalAdp);
                edtVital.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<RangeMasterResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
        edtVital.setOnItemClickListener((adapterView, view1, i, l) -> itemId.append(vitalAdp.getItem(i).getId()+","));
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

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnSubmit){
            if(itemId.length()>0){
                Call<RangeMasterResp> call= RetrofitClient.getInstance().getApi().getVitalGraphs(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), mYear+"-"+mMonth+"-"+mDay, itemId.toString(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());

            }
        } else if(view.getId()==R.id.txtDate){
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        txtDate.setText(Utils.formatDate(date));
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
