package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.trueform.era.his.Model.EmployeeList;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.DepartmentResp;
import com.trueform.era.his.Response.EmployeeShiftResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeDetail extends BaseFragment {
    private Spinner spnDept;
    private List<DepartmentList> departmentList;
    private ArrayAdapter<DepartmentList> departmentListAdp;
    private RecyclerView rvEmployee;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_detail, container, false);
        spnDept = view.findViewById(R.id.spnDept);
        rvEmployee = view.findViewById(R.id.rvEmployee);
        rvEmployee.setLayoutManager(new LinearLayoutManager(mActivity));
        departmentList = new ArrayList<>();
        departmentList.add(new DepartmentList(0, "Select Department"));
        bind();
        spnDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bindEmp();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    private void bindEmp() {
        Call<EmployeeShiftResp> call = RetrofitClient.getInstance().getApi().getEmployeeList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), departmentList.get(spnDept.getSelectedItemPosition()).getDeptID(), departmentList.get(spnDept.getSelectedItemPosition()).getDeptName());
        call.enqueue(new Callback<EmployeeShiftResp>() {
            @Override
            public void onResponse(Call<EmployeeShiftResp> call, Response<EmployeeShiftResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        rvEmployee.setAdapter(new EmployeeListAdp(response.body().getEmployeeList()));
                    }
                }
            }

            @Override
            public void onFailure(Call<EmployeeShiftResp> call, Throwable t) {

            }
        });
    }

    private void bind() {
        Call<DepartmentResp> call = RetrofitClient.getInstance().getApi().getDepartmentList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<DepartmentResp>() {
            @Override
            public void onResponse(Call<DepartmentResp> call, Response<DepartmentResp> response) {
                if (response.isSuccessful()) {
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
        List<EmployeeList> employeeList;

        public EmployeeListAdp(List<EmployeeList> employeeList) {
            this.employeeList = employeeList;
        }

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
            holder.txtSno.setText(String.valueOf(i + 1));
            holder.txtName.setText(employeeList.get(i).getEmpName());
            holder.txtMob.setText(employeeList.get(i).getMobileno());
            holder.lLayout.setOnClickListener(view -> {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
                alertDialog.setMessage("Do you want to Call?");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setPositiveButton("Yes", (dialog, which) -> startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", employeeList.get(i).getMobileno(), null))));
            alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            alertDialog.show();
            });
        }

        @Override
        public int getItemCount() {
            return employeeList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSno, txtName, txtMob;
            LinearLayout lLayout;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSno = itemView.findViewById(R.id.txtSno);
                txtName = itemView.findViewById(R.id.txtName);
                txtMob = itemView.findViewById(R.id.txtMob);
                lLayout = itemView.findViewById(R.id.lLayout);
            }
        }
    }
}