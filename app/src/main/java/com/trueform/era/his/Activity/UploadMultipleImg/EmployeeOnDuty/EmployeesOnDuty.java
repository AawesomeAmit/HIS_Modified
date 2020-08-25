package com.trueform.era.his.Activity.UploadMultipleImg.EmployeeOnDuty;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trueform.era.his.Activity.UploadMultipleImg.Api;
import com.trueform.era.his.Activity.UploadMultipleImg.ApiUtils;
import com.trueform.era.his.Activity.UploadMultipleImg.ApiUtilsForNonMedicos;
import com.trueform.era.his.Activity.UploadMultipleImg.ViewPatientDoc;
import com.trueform.era.his.R;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;
import com.trueform.era.his.view.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmployeesOnDuty extends BaseFragment {

    TextView tvDate,tvDepartment;
    Calendar calendar;

    RadioGroup radioGroup;
    RadioButton rbMedico,rbNonMedicos;

    static SharedPrefManager sharedPrefManager;
    private static Activity context;


    static RecyclerView recyclerViewFoodList;


    ImageView imgBack,imgCalender;
    Dialog dialog;
    RecyclerView popuprecyclerView;
    EditText etSearch,popupEtSearch;
    public static int departmentId=0;

    RecyclerView recyclerView,recyclerViewNonMedico;

    List<MedicosDepartmentList> medicosDepartmentLists;
    MyMedGroupAdp myMedGroupAdp;

    List<NonMedicosDepartmentList> nonMedicosDepartmentLists;
    NonMedicosDepartmentAdp NonMedicosDepartmentAdp;

    List<MedicosDoctorList> medicosDoctorLists;
    MyStockAdp stockAdp;

    List<NonMedicoEmployeeList> nonMedicoEmployeeLists;
    NonMedEmpAdp nonMedEmpAdp;

    public static int categoryId=1;

    Spinner spnCasuality;
    List<CasualityAreaList> casualityAreaLists = new ArrayList<>();

    private String casualityAreaID="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employees_on_duty, container, false);
        context = mActivity;

        sharedPrefManager = SharedPrefManager.getInstance(getActivity());

        tvDate = view.findViewById(R.id.tvDate);
        tvDepartment = view.findViewById(R.id.tvDepartment);
        radioGroup = view.findViewById(R.id.radioGroup);
        rbMedico = view.findViewById(R.id.rbMedico);
        rbNonMedicos = view.findViewById(R.id.rbNonMedicos);
        spnCasuality = view.findViewById(R.id.spnCasuality);
        imgCalender = view.findViewById(R.id.imgCalender);
        etSearch = view.findViewById(R.id.etSearch);

        recyclerViewNonMedico = view.findViewById(R.id.recyclerViewNonMedico);
        recyclerViewNonMedico.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        calendar = Calendar.getInstance();

        Date date= Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dateString = df.format(date);
        tvDate.setText(dateString);

        // calender for Date
        imgCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                int mYear = c.get(java.util.Calendar.YEAR);
                int mMonth = c.get(java.util.Calendar.MONTH);
                int mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvDate.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);
                                if(categoryId==1){
                                if (ConnectivityChecker.checker(mActivity)) {
                                    hitMedicosEmployeeOnDutyList(mActivity);
                                } else {
                                    Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                                }
                                }else {
                                    if (ConnectivityChecker.checker(mActivity)) {
                                        hitNonMedicosEmployeeOnDutyList(mActivity);
                                    } else {
                                        Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        }, mYear, mMonth, mDay);

                //Use for current date
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //Use for one day before
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000));

                datePickerDialog.show();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(rbMedico.isChecked())
                {
                    tvDepartment.setText("Search by department");
                    tvDepartment.setTextColor(Color.parseColor("#B3B3B3"));
                    categoryId=1;
                    recyclerViewNonMedico.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    departmentId=0;

                }else if(rbNonMedicos.isChecked()){
                    categoryId=2;
                    tvDepartment.setText("Search by department");
                    tvDepartment.setTextColor(Color.parseColor("#B3B3B3"));
                    recyclerView.setVisibility(View.GONE);
                    recyclerViewNonMedico.setVisibility(View.VISIBLE);
                    departmentId=0;
                }

            }
        });


        tvDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDepartmentList();

            }
        });

        if (ConnectivityChecker.checker(mActivity)) {
            hitGetCasualityList(mActivity);
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    //Hit Get Casuality List
    private void hitGetCasualityList(Activity activity) {
        casualityAreaLists.clear();

        Utils.showRequestDialog(mActivity);

        Api iRestInterfaces = ApiUtils.getAPIService();
        Call<CasualityAreaRes> call = iRestInterfaces.getCasualityAreaList(
                SharedPrefManager.getInstance(context).getUser().getAccessToken(),
                String.valueOf(SharedPrefManager.getInstance(context).getUser().getUserid())
                );

        call.enqueue(new Callback<CasualityAreaRes>() {
            @Override
            public void onResponse(Call<CasualityAreaRes> call, Response<CasualityAreaRes> response) {

                if (response.isSuccessful()) {

                    // getTaskProjectLists.addAll(response.body().getProject());

                    //ArrayAdapter arrayAdapter = new ArrayAdapter(mActivity, R.layout.inflate_spinner_item, getTaskProjectLists);
                    //actvProject.setAdapter(arrayAdapter);
                    casualityAreaLists.add(new CasualityAreaList());
                    casualityAreaLists.get(0).setId(0);
                    casualityAreaLists.get(0).setCasualityArea("Select casuality area");

                    casualityAreaLists.addAll(response.body().getCasualityAreaList());

                    ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.inflate_spinner_item, casualityAreaLists);
                    spnCasuality.setAdapter(arrayAdapter);

                    spnCasuality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                           /* if (position == 0) {
                                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey));
                            }*/

                            casualityAreaID = String.valueOf(casualityAreaLists.get(spnCasuality.getSelectedItemPosition()).getId());

                            Log.v("asfasgtrhasb", String.valueOf(casualityAreaLists.get(spnCasuality.getSelectedItemPosition()).getId()));

                            //hitGetTaskProject();

                          /*  if (position != 0) {
                                hitGetTaskProject();
                                //hitGetDeptWiseEmployee();
                            }*/

                            if(categoryId==1) {
                                if (ConnectivityChecker.checker(mActivity)) {
                                    hitMedicosEmployeeOnDutyList(mActivity);
                                } else {
                                    Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                if (ConnectivityChecker.checker(mActivity)) {
                                    hitGetNonMedicosDep(mActivity);
                                } else {
                                    Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                                }
                            }

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
            public void onFailure(Call<CasualityAreaRes> call, Throwable t) {

                Utils.hideDialog();
                // progressDialog.dismiss();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

    //alert DepartmentList
    private void alertDepartmentList() {
        dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.alert_employee_department_list);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        popupEtSearch = dialog.findViewById(R.id.etSearch);
        popuprecyclerView = dialog.findViewById(R.id.recyclerView);
        popuprecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        if(categoryId==1) {
            if (ConnectivityChecker.checker(mActivity)) {
                hitGetMedicosDep(mActivity);
            } else {
                Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }
        }else {
            if (ConnectivityChecker.checker(mActivity)) {
                hitGetNonMedicosDep(mActivity);
            } else {
                Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }
        }

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    //Hit Get Non Medicos Department
    private void hitGetNonMedicosDep(Activity activity) {
        Utils.showRequestDialog(activity);

        Api iRestInterfaces = ApiUtilsForNonMedicos.getAPIService();
        Call<NonMedicosDepartmentRes> call = iRestInterfaces.getNonMedicosDepartment(
                "7D10A49B394846E695D95483DC5363EB-1"
        );

        call.enqueue(new Callback<NonMedicosDepartmentRes>() {
            @Override
            public void onResponse(Call<NonMedicosDepartmentRes> call, Response<NonMedicosDepartmentRes> response) {

                try {
                    if (response.isSuccessful()) {

                        nonMedicosDepartmentLists = response.body().getDept();
                        NonMedicosDepartmentAdp = new NonMedicosDepartmentAdp(context,nonMedicosDepartmentLists);
                        popuprecyclerView.setAdapter(NonMedicosDepartmentAdp);

                        popupEtSearch.addTextChangedListener(new TextWatcher() {

                            public void afterTextChanged(Editable s) {
                            }

                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                NonMedicosDepartmentAdp.getFilter().filter(s.toString());

                            }
                        });

                    } else {

                        Toast.makeText(mActivity, "Something wrong", Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


                Utils.hideDialog();

            }

            @Override
            public void onFailure(Call<NonMedicosDepartmentRes> call, Throwable t) {

                Utils.hideDialog();

                //Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Hit Get Medicos Department
    private void hitGetMedicosDep(Activity activity) {
        Utils.showRequestDialog(activity);

        Api iRestInterfaces = ApiUtils.getAPIService();
        Call<MedicosDepartmentRes> call = iRestInterfaces.getMedicosDepartment(
                SharedPrefManager.getInstance(context).getUser().getAccessToken(),
                String.valueOf(SharedPrefManager.getInstance(context).getUser().getUserid())

        );

        call.enqueue(new Callback<MedicosDepartmentRes>() {
            @Override
            public void onResponse(Call<MedicosDepartmentRes> call, Response<MedicosDepartmentRes> response) {

                try {
                    if (response.isSuccessful()) {

                        medicosDepartmentLists = response.body().getMedicosDepartmentList();
                        myMedGroupAdp = new MyMedGroupAdp(context,medicosDepartmentLists);
                        popuprecyclerView.setAdapter(myMedGroupAdp);

                        popupEtSearch.addTextChangedListener(new TextWatcher() {

                            public void afterTextChanged(Editable s) {
                            }

                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                myMedGroupAdp.getFilter().filter(s.toString());

                            }
                        });

                    } else {

                        Toast.makeText(mActivity, "Something wrong", Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


                Utils.hideDialog();

            }

            @Override
            public void onFailure(Call<MedicosDepartmentRes> call, Throwable t) {

                Utils.hideDialog();

                //Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Get Medicos Employee on duty
    private void hitMedicosEmployeeOnDutyList(Activity activity) {
        Utils.showRequestDialog(activity);

        Api iRestInterfaces = ApiUtils.getAPIService();
        Call<MedicosDoctorRes> call = iRestInterfaces.getMedicosDoctorList(
                SharedPrefManager.getInstance(context).getUser().getAccessToken(),
                String.valueOf(SharedPrefManager.getInstance(context).getUser().getUserid()),
                String.valueOf(departmentId),
                tvDate.getText().toString(),
                casualityAreaID

        );

        call.enqueue(new Callback<MedicosDoctorRes>() {
            @Override
            public void onResponse(Call<MedicosDoctorRes> call, Response<MedicosDoctorRes> response) {

                try {
                    if (response.isSuccessful()) {

                        medicosDoctorLists = response.body().getMedicosDoctorList();
                        stockAdp = new MyStockAdp(context,medicosDoctorLists);
                        recyclerView.setAdapter(stockAdp);

                        etSearch.addTextChangedListener(new TextWatcher() {

                            public void afterTextChanged(Editable s) {
                            }

                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                stockAdp.getFilter().filter(s.toString());

                            }
                        });

                    } else {

                        Toast.makeText(mActivity, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


                Utils.hideDialog();

            }

            @Override
            public void onFailure(Call<MedicosDoctorRes> call, Throwable t) {

                Utils.hideDialog();

                //Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Hit get non-medicos on duty employee list
    private void hitNonMedicosEmployeeOnDutyList(Activity activity) {
        Utils.showRequestDialog(activity);

        Api iRestInterfaces = ApiUtilsForNonMedicos.getAPIService();
        Call<NonMedicoEmployeeRes> call = iRestInterfaces.getNonMedicosEmployeeList(
                "7D10A49B394846E695D95483DC5363EB-1",
                String.valueOf(departmentId),
                "1",
                tvDate.getText().toString()

        );

        call.enqueue(new Callback<NonMedicoEmployeeRes>() {
            @Override
            public void onResponse(Call<NonMedicoEmployeeRes> call, Response<NonMedicoEmployeeRes> response) {

                try {
                    if (response.isSuccessful()) {

                        nonMedicoEmployeeLists = response.body().getDtAttendance();
                        nonMedEmpAdp = new NonMedEmpAdp( context,nonMedicoEmployeeLists);
                        recyclerViewNonMedico.setAdapter(nonMedEmpAdp);

                        etSearch.addTextChangedListener(new TextWatcher() {

                            public void afterTextChanged(Editable s) {
                            }

                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                stockAdp.getFilter().filter(s.toString());

                            }
                        });

                    } else {

                        Toast.makeText(mActivity, "Somthing went wrong", Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


                Utils.hideDialog();

            }

            @Override
            public void onFailure(Call<NonMedicoEmployeeRes> call, Throwable t) {

                Utils.hideDialog();

                //Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


  //Adapter to get medicos department

    public class MyMedGroupAdp extends RecyclerView.Adapter<MyMedGroupAdp.MyViewHolder> {

        private Context mContext;
        private List<MedicosDepartmentList> medicineGroupModels;
        List<MedicosDepartmentList> groupModels;

        public MyMedGroupAdp(Context mContext, List<MedicosDepartmentList> medicineGroupModels) {
            this.mContext = mContext;
            this.medicineGroupModels = medicineGroupModels;
            this.groupModels = medicineGroupModels;
        }

        @NonNull
        @Override
        public MyMedGroupAdp.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.innerview_select_department_name,parent,false);
            return new MyMedGroupAdp.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyMedGroupAdp.MyViewHolder holder,final int i) {

            final MedicosDepartmentList list = medicineGroupModels.get(i);
            holder.tvMedGroup.setText(list.getSubDepartmentName());
            //holder.tvSno.setText(i+1);
            holder.tvSno.setText((i + 1) + " " + ".");

            if(i %2 == 1)
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            else
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
                //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvDepartment.setText(list.getSubDepartmentName());
                    tvDepartment.setTextColor(Color.parseColor("#000000"));
                    departmentId = list.getId();
                    if (ConnectivityChecker.checker(mActivity)) {
                        hitMedicosEmployeeOnDutyList(mActivity);
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });


        }

        @Override
        public int getItemCount() {
            return medicineGroupModels.size();
        }

        //Use For Search
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        medicineGroupModels = groupModels;
                    } else {
                        List<MedicosDepartmentList> filteredList = new ArrayList<>();
                        for (MedicosDepartmentList row : groupModels) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getSubDepartmentName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        medicineGroupModels = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = medicineGroupModels;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    medicineGroupModels = (ArrayList<MedicosDepartmentList>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvMedGroup,tvSno;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvMedGroup = itemView.findViewById(R.id.tvMedGroup);
                tvSno = itemView.findViewById(R.id.tvSno);

            }
        }
    }

    //Adapter for Non Medicos department

    public class NonMedicosDepartmentAdp extends RecyclerView.Adapter<NonMedicosDepartmentAdp.MyViewHolder> {

        private Context mContext;
        private List<NonMedicosDepartmentList> medicineGroupModels;
        List<NonMedicosDepartmentList> groupModels;

        public NonMedicosDepartmentAdp(Context mContext, List<NonMedicosDepartmentList> medicineGroupModels) {
            this.mContext = mContext;
            this.medicineGroupModels = medicineGroupModels;
            this.groupModels = medicineGroupModels;
        }

        @NonNull
        @Override
        public NonMedicosDepartmentAdp.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.innerview_select_department_name,parent,false);
            return new NonMedicosDepartmentAdp.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NonMedicosDepartmentAdp.MyViewHolder holder,final int i) {

            final NonMedicosDepartmentList list = medicineGroupModels.get(i);
            holder.tvMedGroup.setText(list.getSubDepartmentName());
            //holder.tvSno.setText(i+1);
            holder.tvSno.setText((i + 1) + " " + ".");

            if(i %2 == 1)
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            else
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
                //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvDepartment.setText(list.getSubDepartmentName());
                    tvDepartment.setTextColor(Color.parseColor("#000000"));
                    departmentId = list.getId();
                    if (ConnectivityChecker.checker(mActivity)) {
                        hitNonMedicosEmployeeOnDutyList(mActivity);
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });


        }

        @Override
        public int getItemCount() {
            return medicineGroupModels.size();
        }

        //Use For Search
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        medicineGroupModels = groupModels;
                    } else {
                        List<NonMedicosDepartmentList> filteredList = new ArrayList<>();
                        for (NonMedicosDepartmentList row : groupModels) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getSubDepartmentName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        medicineGroupModels = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = medicineGroupModels;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    medicineGroupModels = (ArrayList<NonMedicosDepartmentList>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvMedGroup,tvSno;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvMedGroup = itemView.findViewById(R.id.tvMedGroup);
                tvSno = itemView.findViewById(R.id.tvSno);

            }
        }
    }



    //Adapter for set medicos On Duty Doctor List
    public class MyStockAdp extends RecyclerView.Adapter<MyStockAdp.MyViewHolder> {

        private Context mContext;
        private List<MedicosDoctorList> stockDtlModels;
        List<MedicosDoctorList> listResp;

        public MyStockAdp(Context mContext, List<MedicosDoctorList> stockDtlModels) {
            this.mContext = mContext;
            this.stockDtlModels = stockDtlModels;
            this.listResp = stockDtlModels;
        }

        @NonNull
        @Override
        public MyStockAdp.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.innerview_employee_on_duty,parent,false);
            return new MyStockAdp.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyStockAdp.MyViewHolder holder,final int i) {

            MedicosDoctorList list = stockDtlModels.get(i);
            holder.tvName.setText(list.getDoctorName());
            holder.tvDesignation.setText(list.getDesignation());
            holder.tvLocation.setText(list.getCasualityArea());
            holder.tvMobileNo.setText("+91"+""+list.getMobileNo());

            holder.imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse(list.getMobileNo()));
                    startActivity(callIntent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return stockDtlModels.size();
        }

        //Use For Search
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        stockDtlModels = listResp;
                    } else {
                        List<MedicosDoctorList> filteredList = new ArrayList<>();
                        for (MedicosDoctorList row : listResp) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getDoctorName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        stockDtlModels = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = stockDtlModels;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    stockDtlModels = (ArrayList<MedicosDoctorList>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvName,tvDesignation,tvLocation,tvMobileNo;
            ImageView imgCall;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvName);
                tvDesignation = itemView.findViewById(R.id.tvDesignation);
                tvLocation = itemView.findViewById(R.id.tvLocation);
                tvMobileNo = itemView.findViewById(R.id.tvMobileNo);
                imgCall = itemView.findViewById(R.id.imageView2);

            }
        }
    }

    //set non medicos employee on duty employee list
    //Adapter for set medicos On Duty Doctor List
    public class NonMedEmpAdp extends RecyclerView.Adapter<NonMedEmpAdp.MyViewHolder> {

        private Context mContext;
        private List<NonMedicoEmployeeList> stockDtlModels;
        List<NonMedicoEmployeeList> listResp;

        public NonMedEmpAdp(Context mContext, List<NonMedicoEmployeeList> stockDtlModels) {
            this.mContext = mContext;
            this.stockDtlModels = stockDtlModels;
            this.listResp = stockDtlModels;
        }

        @NonNull
        @Override
        public NonMedEmpAdp.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.innerview_employee_on_duty,parent,false);
            return new NonMedEmpAdp.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NonMedEmpAdp.MyViewHolder holder,final int i) {

            NonMedicoEmployeeList list = stockDtlModels.get(i);
            holder.tvName.setText(list.getEmpName());
            holder.tvDesignation.setText(list.getEmpDesig());
            holder.tvLocation.setText(list.getCovidLocation());
            holder.tvMobileNo.setText("+91"+""+list.getMobileNo());

            holder.imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse(list.getMobileNo()));
                    startActivity(callIntent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return stockDtlModels.size();
        }

        //Use For Search
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        stockDtlModels = listResp;
                    } else {
                        List<NonMedicoEmployeeList> filteredList = new ArrayList<>();
                        for (NonMedicoEmployeeList row : listResp) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getEmpName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        stockDtlModels = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = stockDtlModels;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    stockDtlModels = (ArrayList<NonMedicoEmployeeList>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvName,tvDesignation,tvLocation,tvMobileNo;
            ImageView imgCall;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvName);
                tvDesignation = itemView.findViewById(R.id.tvDesignation);
                tvLocation = itemView.findViewById(R.id.tvLocation);
                tvMobileNo = itemView.findViewById(R.id.tvMobileNo);
                imgCall = itemView.findViewById(R.id.imageView2);

            }
        }
    }


}