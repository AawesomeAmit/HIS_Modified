package com.trueform.era.his.Activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Model.GetIcdCodeModel;
import com.trueform.era.his.R;
import com.trueform.era.his.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class UpdateVital extends BaseActivity {
    AutoCompleteTextView etConsultant;
    RecyclerView recyclerViewDiagnosis;
    LinearLayout rlConsultantDiagnosis;
    private AdapterNutrient adapterNutrient;
    private List<GetIcdCodeModel> getIcdCodeModelListMain = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vital);
        etConsultant = findViewById(R.id.etConsultant);
        recyclerViewDiagnosis = findViewById(R.id.recyclerViewDiagnosis);
        rlConsultantDiagnosis = findViewById(R.id.rlConsultantDiagnosis);
        etConsultant.setThreshold(3);

        rlConsultantDiagnosis.setOnClickListener(view1 -> {
            try {
                if(!etConsultant.getText().toString().isEmpty()) {
                    if (!containsDetail(getIcdCodeModelListMain, etConsultant.getText().toString().trim())) {
                        getIcdCodeModelListMain.add(0, new GetIcdCodeModel());
                        getIcdCodeModelListMain.get(0).setDetailID(0);
                        getIcdCodeModelListMain.get(0).setDetails(etConsultant.getText().toString().trim());
                        getIcdCodeModelListMain.get(0).setPdmID(4);
                        etConsultant.setText("");
                        if (adapterNutrient != null) {
                            adapterNutrient.notifyItemInserted(0);
                        } else {
                            adapterNutrient = new AdapterNutrient(getIcdCodeModelListMain);
                            recyclerViewDiagnosis.setAdapter(adapterNutrient);
                        }
                    } else {
                        Toast.makeText(mActivity, "Diagnosis already added", Toast.LENGTH_SHORT).show();
                        etConsultant.setText("");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private boolean containsDetail(List<GetIcdCodeModel> list, String name) {

        for (GetIcdCodeModel item : list) {
            if (item.getDetails().equals(name)) {

                return true;
            }
        }

        return false;
    }


    private class AdapterNutrient extends RecyclerView.Adapter<AdapterNutrient.HolderNutrient> {
        List<GetIcdCodeModel> data;
        private int lastPosition = -1;

        public AdapterNutrient(List<GetIcdCodeModel> favList) {
            data = favList;
        }

        public HolderNutrient onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderNutrient(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_nutrient, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderNutrient holder, final int position) {

            final GetIcdCodeModel getAllSuggestedDiseaseModel = data.get(position);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.tvNutrient.setText(Html.fromHtml(getAllSuggestedDiseaseModel.getDetails().trim(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.tvNutrient.setText(Html.fromHtml(getAllSuggestedDiseaseModel.getDetails().trim()));
            }

            holder.ivRemove.setOnClickListener(view -> {
                getIcdCodeModelListMain.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getIcdCodeModelListMain.size());

            });


        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public int getItemCount() {
            return data.size();
        }

        private class HolderNutrient extends RecyclerView.ViewHolder {

            TextView tvNutrient;

            ImageView ivRemove;

            public HolderNutrient(View itemView) {
                super(itemView);
                tvNutrient = itemView.findViewById(R.id.tvNutrient);
                ivRemove = itemView.findViewById(R.id.ivRemove);
            }
        }
    }
}