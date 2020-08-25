package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Activity.PreDashboard;
import com.trueform.era.his.Adapter.SubHeadAdp;
import com.trueform.era.his.Model.PrescriptionList;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.GetPrescriptionResponse;
import com.trueform.era.his.Response.SubHeadIDResp;
import com.trueform.era.his.Response.VentilatorDataResp;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescribedMedicine extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<PrescriptionList> prescriptionList;
    RecyclerView rView;
    ProgressDialog dialog;
    Context context;
    private String mParam1;
    private String mParam2;

    TextView tvGivenByHead;

    LinearLayout llMain;

    private OnFragmentInteractionListener mListener;

    public PrescribedMedicine() {
        // Required empty public constructor
    }

    public static PrescribedMedicine newInstance(String param1, String param2) {
        PrescribedMedicine fragment = new PrescribedMedicine();
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
        View view = inflater.inflate(R.layout.fragment_prescribed_mediine, container, false);
        rView = view.findViewById(R.id.rView);
        //llMain = view.findViewById(R.id.llMain);
        //llMain.setWeightSum(12);
        //tvGivenByHead = view.findViewById(R.id.tvGivenByHead);
        //tvGivenByHead.setVisibility(View.GONE);
        context = view.getContext();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        //dialog.show();
        rView.setLayoutManager(new LinearLayoutManager(context));
        bind();
        return view;
    }
private void bind(){
    Call<GetPrescriptionResponse> call = RetrofitClient.getInstance().getApi().getintakePrescription(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getHeadID(), SharedPrefManager.getInstance(context).getIpNo(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
    call.enqueue(new Callback<GetPrescriptionResponse>() {
        @Override
        public void onResponse(Call<GetPrescriptionResponse> call, Response<GetPrescriptionResponse> response) {
            dialog.show();
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    prescriptionList = response.body().getPrescriptionList();
                    if (prescriptionList.size() > 0) {
                        rView.setAdapter(new PrescribedMedicationAdp(prescriptionList));
                    }
                }
            }
            dialog.dismiss();
        }

        @Override
        public void onFailure(Call<GetPrescriptionResponse> call, Throwable t) {
            dialog.dismiss();
        }
    });
}

    public class PrescribedMedicationAdp extends RecyclerView.Adapter<PrescribedMedicationAdp.RecyclerViewHolder> {
        private List<PrescriptionList> prescriptionList;

        public PrescribedMedicationAdp(List<PrescriptionList> prescriptionList) {
            this.prescriptionList = prescriptionList;
        }

        @NonNull
        @Override
        public PrescribedMedicationAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.inner_view_prescription, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new PrescribedMedicationAdp.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PrescribedMedicationAdp.RecyclerViewHolder holder, int i) {
            PrescriptionList prescriptionLists = prescriptionList.get(i);
            holder.tvGivenBy.setVisibility(View.VISIBLE);
            //holder.llMain.setWeightSum(12);
            if (i != 0) {
                if (prescriptionLists.getCreatedDate().equalsIgnoreCase(prescriptionList.get(i - 1).getCreatedDate())) {
                    holder.txtDate.setVisibility(View.GONE);
                } else {
                    holder.txtDate.setText(prescriptionLists.getCreatedDate());
//                    holder.txtDate.setVisibility(View.VISIBLE);
                    holder.txtDate.setVisibility(View.GONE);
                }
            } else {
                holder.txtDate.setText(prescriptionLists.getCreatedDate());
//                holder.txtDate.setVisibility(View.VISIBLE);
                holder.txtDate.setVisibility(View.GONE);
            }

            if (!prescriptionLists.getCreatedDate().equalsIgnoreCase(Utils.getCurrentDate())) {
                holder.txtDate.setVisibility(View.GONE);
            }

            if (prescriptionLists.getIsStop() == 0 && prescriptionLists.getStatus() == 1) {
                holder.llMain.setVisibility(View.VISIBLE);
                holder.txtMed.setText(prescriptionLists.getDrugName().trim().toUpperCase());
                holder.txtStr.setText(prescriptionLists.getDoseStrength() + " " + prescriptionLists.getDoseUnit());
                holder.txtFreq.setText(prescriptionLists.getDoseFrequency());
                holder.txtDosage.setText(prescriptionLists.getDosageForm());
                holder.txtRemark.setText(prescriptionLists.getRemark());
                holder.tvGivenBy.setText(prescriptionLists.getPrescribeBy());
                // String[] time = prescriptionLists.getIntakeDateTime().split("T");
                // holder.tvGivenTime.setText(Utils.formatTimeNew(time[1]));
                holder.tvGivenTime.setText(prescriptionLists.getDuration());
                if (prescriptionLists.getColorStatus() == null)
                    holder.txtGive.setBackgroundResource(R.drawable.ic_check_blue);
                else {
                    if (prescriptionLists.getColorStatus().equalsIgnoreCase("blue"))
                        holder.txtGive.setBackgroundResource(R.drawable.ic_check_blue);
                    else if (prescriptionLists.getColorStatus().equalsIgnoreCase("green"))
                        holder.txtGive.setBackgroundResource(R.drawable.ic_check_green);
                    else if (prescriptionLists.getColorStatus().equalsIgnoreCase("red"))
                        holder.txtGive.setBackgroundResource(R.drawable.ic_check_red);
                }
                holder.txtComment.setOnClickListener(view -> showPopup(prescriptionLists.getId(), prescriptionLists.getPmID()));
                holder.txtGive.setOnClickListener(view -> new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                        .setMessage("Are you sure?")
                        .setCancelable(true)
                        .setPositiveButton(
                                "Yes",
                                (dialog, id) -> action(prescriptionLists.getId(), prescriptionLists.getPmID(), "", 0))

                        .setNegativeButton(
                                "No",
                                (dialog, id) -> dialog.cancel())
                        .show());
            } else {
                holder.llMain.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return prescriptionList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtMed, txtStr, txtFreq, txtDate, txtDosage, txtRemark, tvGivenTime, tvGivenBy, txtGive, txtComment;
            ConstraintLayout llMain;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtMed = itemView.findViewById(R.id.txtMed);
                txtStr = itemView.findViewById(R.id.txtStr);
                txtFreq = itemView.findViewById(R.id.txtFreq);
                txtDate = itemView.findViewById(R.id.txtDate);
                txtDosage = itemView.findViewById(R.id.txtDosage);
                txtRemark = itemView.findViewById(R.id.txtRemark);
                tvGivenTime = itemView.findViewById(R.id.tvGivenTime);
                tvGivenBy = itemView.findViewById(R.id.tvGivenBy);
                llMain = itemView.findViewById(R.id.llMain);
                txtGive = itemView.findViewById(R.id.txtGive);
                txtComment = itemView.findViewById(R.id.txtComment);
            }
        }
    }

    private void action(int prescriptionID, int pmID, String comment, int status){
        Utils.showRequestDialog(context);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().saveIntakePrescription(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), comment, pmID, prescriptionID, status, String.valueOf(SharedPrefManager.getInstance(context).getUser().getUserid()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Saved Successfully!", Toast.LENGTH_SHORT).show();
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

    private void showPopup(int prescriptionID, int pmID) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_medication_staff_comment, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        LinearLayout lLayout = popupView.findViewById(R.id.lLayout);
        EditText edtComment = popupView.findViewById(R.id.edtComment);
        CheckBox chkMedicine = popupView.findViewById(R.id.chkMedicine);
        TextView btnSave = popupView.findViewById(R.id.btnSave);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        lLayout.getLocationOnScreen(location);
        popupWindow.showAtLocation(lLayout, Gravity.CENTER, 0, 0);
        btnSave.setOnClickListener(view -> {
            if(chkMedicine.isChecked())
            action(prescriptionID, pmID, edtComment.getText().toString().trim(), 2);
            else action(prescriptionID, pmID, edtComment.getText().toString().trim(), 0);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
