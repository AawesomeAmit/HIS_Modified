package com.his.android.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Model.UserMedicationReport;
import com.his.android.R;
import com.his.android.Response.UserMedicationReportResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicineSidePathway extends AppCompatActivity {
    RecyclerView recyclerMedSidePathway;
    RadioGroup rdGrp;
    EditText edtSearch;
    MedSidePathAdp medSidePathAdp;
    RadioButton rdCurrent, rdAll;
    String searchText;
    List<UserMedicationReport> userMedicationReport;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_medicine_side_pathway);
        rdAll = findViewById(R.id.rdAll);
        rdCurrent = findViewById(R.id.rdCurrent);
        edtSearch = findViewById(R.id.edtSearch);
        rdGrp = findViewById(R.id.rdGrp);
        userMedicationReport=new ArrayList<>();
        recyclerMedSidePathway=findViewById(R.id.recyclerMedSidePathway);
        recyclerMedSidePathway.setHasFixedSize(true);
        recyclerMedSidePathway.setLayoutManager(new LinearLayoutManager(this));
        bindData("Current");
        rdGrp.setOnCheckedChangeListener((radioGroup, i) -> {
            if (rdAll.isChecked()) bindData("All");
            else if (rdCurrent.isChecked()) bindData("Current");
        });
        edtSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                medSidePathAdp.getFilter().filter(s.toString());

            }
        });
    }
    private void bindData(String value) {
        Utils.showRequestDialog(MedicineSidePathway.this);
        Call<UserMedicationReportResp> call= RetrofitClient.getInstance().getApi().getUserMedicationReport(SharedPrefManager.getInstance(this).getUser().getAccessToken(), SharedPrefManager.getInstance(this).getUser().getUserid().toString(), SharedPrefManager.getInstance(this).getPid(), SharedPrefManager.getInstance(this).getUser().getUserid().toString(), SharedPrefManager.getInstance(this).getPid(), SharedPrefManager.getInstance(this).getHeadID(), value);
        call.enqueue(new Callback<UserMedicationReportResp>() {
            @Override
            public void onResponse(Call<UserMedicationReportResp> call, Response<UserMedicationReportResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        userMedicationReport=response.body().getUserMedicationReport();
                        medSidePathAdp=new MedSidePathAdp(MedicineSidePathway.this, userMedicationReport);
                        recyclerMedSidePathway.setAdapter(medSidePathAdp);
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<UserMedicationReportResp> call, Throwable t) {
                Toast.makeText(MedicineSidePathway.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Utils.hideDialog();
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    public class MedSidePathAdp extends RecyclerView.Adapter<MedSidePathAdp.RecyclerViewHolder> implements Filterable {
        private Context mCtx;
        List<UserMedicationReport> listResp;
        MedSidePathAdp(Context mCtx, List<UserMedicationReport> userMedicationReports) {
            this.mCtx = mCtx;
            this.listResp=userMedicationReports;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_medicine_side_pathway, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final MedSidePathAdp.RecyclerViewHolder holder, final int i) {
            holder.txtSno.setText((i + 1) + ". ");
            holder.txtName.setText(userMedicationReport.get(i).getMedicineName());
            String desc = userMedicationReport.get(i).getSideEffect();
            if (searchText != null) {
                if (searchText.length() > 0) {
                    int index = desc.toLowerCase().indexOf(searchText.toLowerCase());
                    SpannableStringBuilder sb = null;
                    while (index > 0) {
                        sb = new SpannableStringBuilder(desc);
                        BackgroundColorSpan fcs = new BackgroundColorSpan(Color.YELLOW);
                        sb.setSpan(fcs, index, index+searchText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        index = desc.indexOf(searchText, index + 1);
                    }
                    holder.txtSide.setText(sb);
                } else holder.txtSide.setText(Html.fromHtml(userMedicationReport.get(i).getSideEffect()));
            } else holder.txtSide.setText(Html.fromHtml(userMedicationReport.get(i).getSideEffect()));
            holder.txtDInteract.setText(userMedicationReport.get(i).getMedicineInteraction());
            if (userMedicationReport.get(i).getMedicinePathway().equalsIgnoreCase(""))
                holder.txtPathway.setText("-");
            else holder.txtPathway.setText(userMedicationReport.get(i).getMedicinePathway());
            holder.txtMoI.setText(userMedicationReport.get(i).getMechanismOfAction());
        }

        @Override
        public int getItemCount() {
            return userMedicationReport.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        userMedicationReport = listResp;
                    } else {
                        List<UserMedicationReport> filteredList = new ArrayList<>();
                        for (UserMedicationReport row : listResp) {
                            if (row.getSideEffect().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                        userMedicationReport = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = userMedicationReport;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    //userMedicationReport = (ArrayList<UserMedicationReport>) filterResults.values;
                    Log.v("searchText", String.valueOf(charSequence));
                    searchText= String.valueOf(charSequence);
                    Log.v("status1", String.valueOf(filterResults.values));
                    notifyDataSetChanged();
                }
            };
        }
        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSno, txtName, txtSide, txtDInteract, txtPathway, txtMoI;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSno =itemView.findViewById(R.id.txtSno);
                txtName =itemView.findViewById(R.id.txtName);
                txtSide =itemView.findViewById(R.id.txtSide);
                txtDInteract =itemView.findViewById(R.id.txtDInteract);
                txtPathway =itemView.findViewById(R.id.txtPathway);
                txtMoI =itemView.findViewById(R.id.txtMoI);
            }
        }
    }
}