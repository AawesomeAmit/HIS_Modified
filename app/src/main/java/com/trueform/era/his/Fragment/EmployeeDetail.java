package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.trueform.era.his.Model.DepartmentList;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.DepartmentResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeDetail extends BaseFragment {
    Spinner spnDept;
    private List<DepartmentList> departmentList;
    ArrayAdapter<DepartmentList> departmentListAdp;
    public EmployeeDetail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_employee_detail, container, false);
        spnDept=view.findViewById(R.id.spnDept);
        departmentList=new ArrayList<>();
        departmentList.add(new DepartmentList(0, "Select Department"));
        bind();
        spnDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }
    private void bindEmp(){

    }
    private void bind(){
        Call<DepartmentResp> call= RetrofitClient.getInstance().getApi().getDepartmentList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<DepartmentResp>() {
            @Override
            public void onResponse(Call<DepartmentResp> call, Response<DepartmentResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        departmentList.addAll(1, response.body().getDepartmentList());
                    }
                    departmentListAdp = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, departmentList);
                    spnDept.setAdapter(departmentListAdp);
                }
            }

            @Override
            public void onFailure(Call<DepartmentResp> call, Throwable t) {

            }
        });
    }

    public class EmployeeListAdp extends RecyclerView.Adapter<EmployeeListAdp.RecyclerViewHolder> {
        EmployeeListAdp() {}
        @NonNull
        @Override
        public EmployeeListAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_employee_list, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull EmployeeListAdp.RecyclerViewHolder holder, int i) {
            //holder.tvMedName.setText(medicineList.get(i).getDrugName());
        }

        @Override
        public int getItemCount() {
            return 0;//medicineList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSno, txtName, txtMob;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSno = itemView.findViewById(R.id.txtSno);
                txtName = itemView.findViewById(R.id.txtName);
                txtMob = itemView.findViewById(R.id.txtMob);
            }
        }
    }
}
