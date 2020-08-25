package com.trueform.era.his.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trueform.era.his.Fragment.InputSupplement;
import com.trueform.era.his.Model.SupplimentDetail;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.UnitResp;
import com.trueform.era.his.Utils.InputFilterMinMax;
import com.trueform.era.his.Utils.RetrofitClient1;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplimentIntakeAdp extends RecyclerView.Adapter<SupplimentIntakeAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<SupplimentDetail> supplimentDetails;
    InputSupplement supplement=new InputSupplement();
    public SupplimentIntakeAdp(Context mCtx, List<SupplimentDetail> supplimentDetails) {
        this.mCtx = mCtx;
        this.supplimentDetails = supplimentDetails;
    }

    @NonNull
    @Override
    public SupplimentIntakeAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_display_input_fluid, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplimentIntakeAdp.RecyclerViewHolder holder, int i) {
        holder.txtFluid.setText(String.valueOf(supplimentDetails.get(i).getTypeName()));
        holder.txtQty.setText(String.valueOf(supplimentDetails.get(i).getQuantity()));
        holder.txtUnit.setText(String.valueOf(supplimentDetails.get(i).getUnitname()));
        holder.txtDateTime.setText(supplimentDetails.get(i).getIntakeDate());
        holder.txtEdit.setOnClickListener(view -> {
            holder.txtEdit.setVisibility(View.GONE);
            holder.txtSave.setVisibility(View.VISIBLE);
            holder.edtQty.setVisibility(View.VISIBLE);
            holder.txtPer.setVisibility(View.VISIBLE);
            holder.edtQty.setEnabled(true);
            holder.edtQty.requestFocus();
            InputMethodManager imm = (InputMethodManager) mCtx.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(holder.edtQty, InputMethodManager.SHOW_IMPLICIT);
        });
        holder.txtSave.setOnClickListener(view -> {
                Utils.showRequestDialog(mCtx);
                Call<UnitResp> call = RetrofitClient1.getInstance().getApi().UpdateConsumptionPercentage("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", holder.edtQty.getText().toString().trim(), String.valueOf(SharedPrefManager.getInstance(mCtx).getMemberId().getUserLoginId()), SharedPrefManager.getInstance(mCtx).getMemberId().getMemberId().toString(), String.valueOf(SharedPrefManager.getInstance(mCtx).getUser().getUserid()), String.valueOf(supplimentDetails.get(i).getId()));
                call.enqueue(new Callback<UnitResp>() {
                    @Override
                    public void onResponse(Call<UnitResp> call, Response<UnitResp> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null && response.body().getResponseCode() == 1) {
                                holder.txtEdit.setVisibility(View.VISIBLE);
                                holder.txtSave.setVisibility(View.GONE);
                                holder.edtQty.setVisibility(View.GONE);
                                holder.txtPer.setVisibility(View.GONE);
                                holder.edtQty.setEnabled(false);
                                supplement.bind();
                                Utils.hideDialog();
                            } else if (response.body() != null) {
                                Toast.makeText(mCtx, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UnitResp> call, Throwable t) {
                        Utils.hideDialog();
                    }
                });
        });
    }

    @Override
    public int getItemCount() {
        return supplimentDetails.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtFluid,txtQty,txtUnit, txtDateTime, txtEdit, txtSave, txtPer;
        EditText edtQty;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFluid =itemView.findViewById(R.id.txtFluid);
            txtQty=itemView.findViewById(R.id.txtQty);
            txtUnit=itemView.findViewById(R.id.txtUnit);
            txtDateTime=itemView.findViewById(R.id.txtDateTime);
            txtEdit=itemView.findViewById(R.id.txtEdit);
            edtQty=itemView.findViewById(R.id.edtQty);
            txtSave=itemView.findViewById(R.id.txtSave);
            txtPer=itemView.findViewById(R.id.txtPer);
            edtQty.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 100)});
        }
    }
}
