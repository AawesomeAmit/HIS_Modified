package com.his.android.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.his.android.Model.GetNutrientByPrefixTextModel;
import com.his.android.R;
import com.his.android.Response.GetNutrientByPrefixTextResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.his.android.Fragment.NutriAnalyserFragment.NUTRI_TOKEN;

public class AchievedNutrientGraphActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvFrom, tvTo;

    int mDay = 0, mMonth = 0, mYear = 0, hour = 0, minutes = 0;

    Calendar c;

    String fromDate, toDate;

    RecyclerView recyclerViewNutrient;

    AutoCompleteTextView etNutrient;

    AdapterNutrient adapterNutrient;

    List<GetNutrientByPrefixTextModel> getNutrientByPrefixTextModelListMain = new ArrayList<>();

    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieved_nutrient_graph);

        init();

        setListeners();
    }

    private void init() {
        ivBack = findViewById(R.id.ivBack);

        tvFrom = findViewById(R.id.tvFrom);
        tvTo = findViewById(R.id.tvTo);

        etNutrient = findViewById(R.id.etNutrient);
        etNutrient.setThreshold(2);

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);

        recyclerViewNutrient = findViewById(R.id.recyclerViewNutrient);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getApplicationContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        recyclerViewNutrient.setLayoutManager(layoutManager);
        recyclerViewNutrient.setNestedScrollingEnabled(false);
        recyclerViewNutrient.setHasFixedSize(true);
    }

    private void setListeners() {
        tvFrom.setOnClickListener(this);
        tvTo.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        etNutrient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    if (ConnectivityChecker.checker(getApplicationContext())) {
                        hitGetNutrientByPrefixText(s.toString());
                    } else {
                        Toast.makeText(AchievedNutrientGraphActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvFrom:

                selectDate(tvFrom, 1);

                break;

            case R.id.tvTo:

                selectDate(tvTo, 2);

                break;

            case R.id.ivBack:

                onBackPressed();

                break;
        }
    }

    private void hitGetNutrientByPrefixText(String prefixText) {

        Utils.showRequestDialog(AchievedNutrientGraphActivity.this);

        Call<GetNutrientByPrefixTextResp> call = RetrofitClient1.getInstance().getApi().getNutrientByPrefixText(
                NUTRI_TOKEN,
                prefixText);

        call.enqueue(new Callback<GetNutrientByPrefixTextResp>() {
            @Override
            public void onResponse(Call<GetNutrientByPrefixTextResp> call, Response<GetNutrientByPrefixTextResp> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        List<GetNutrientByPrefixTextModel> getNutrientByPrefixTextModelList = response.body().getResponseValue();

                        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout_new, getNutrientByPrefixTextModelList);
                        //arrayAdapter.setDropDownViewResource(R.layout.inflate_auto_complete_text);
                        etNutrient.setAdapter(arrayAdapter);

                        etNutrient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                try {

                                    if (!containsNutrient(getNutrientByPrefixTextModelListMain, getNutrientByPrefixTextModelList.get(position).getNutrientID().toString())) {

                                        getNutrientByPrefixTextModelListMain.add(0, new GetNutrientByPrefixTextModel());
                                        getNutrientByPrefixTextModelListMain.get(0).setNutrientID(getNutrientByPrefixTextModelList.get(position).getNutrientID()); // get item
                                        getNutrientByPrefixTextModelListMain.get(0).setNutrientName(getNutrientByPrefixTextModelList.get(position).getNutrientName());
                                        //getNutrientByPrefixTextModelListMain.get(0).setSelected(true);

                                        etNutrient.setText("");

                                        if (adapterNutrient != null) {
                                            adapterNutrient.notifyItemInserted(0);
                                            // adapterNutrient.smoothScrollToPosition(0);
                                        } else {
                                            adapterNutrient = new AdapterNutrient(getNutrientByPrefixTextModelListMain);
                                            recyclerViewNutrient.setAdapter(adapterNutrient);
                                        }

                                    } else {
                                        //AppUtils.hideSoftKeyboard(mActivity);

                                        Toast.makeText(AchievedNutrientGraphActivity.this, "Nutrient already selected", Toast.LENGTH_SHORT).show();

                                        etNutrient.setText("");
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetNutrientByPrefixTextResp> call, Throwable t) {
                Log.e("onFailure:", t.getMessage());

                Toast.makeText(AchievedNutrientGraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                Utils.hideDialog();
            }
        });
    }

    boolean containsNutrient(List<GetNutrientByPrefixTextModel> list, String name) {

        for (GetNutrientByPrefixTextModel item : list) {
            if (item.getNutrientID().toString().equals(name)) {

                return true;
            }
        }

        return false;
    }

    private class AdapterNutrient extends RecyclerView.Adapter<HolderNutrient> {
        List<GetNutrientByPrefixTextModel> data = new ArrayList<>();
        private int lastPosition = -1;

        public AdapterNutrient(List<GetNutrientByPrefixTextModel> favList) {
            data = favList;
        }

        public HolderNutrient onCreateViewHolder(ViewGroup parent, int viewType) {
            // return new HolderNutrient(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_selected_problems, parent, false));
            return new HolderNutrient(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_nutrient, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderNutrient holder, final int position) {

            final GetNutrientByPrefixTextModel getAllSuggestedDiseaseModel = data.get(position);

            holder.tvNutrient.setText(getAllSuggestedDiseaseModel.getNutrientName());

            holder.ivRemove.setOnClickListener(view -> {

                getNutrientByPrefixTextModelListMain.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getNutrientByPrefixTextModelListMain.size());

//                    alternateNumberList.remove(position);
//                    adapterAlternateNumber.notifyItemRemoved(position);


            });


            /*holder.cvMain.setCardBackgroundColor(getAllSuggestedDiseaseModel.isSelected() ? getResources().getColor(R.color.green) : getResources().getColor(R.color.yellow_light));
            holder.tvProblem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getAllSuggestedDiseaseModel.setSelected(!getAllSuggestedDiseaseModel.isSelected());
                    holder.cvMain.setCardBackgroundColor(getAllSuggestedDiseaseModel.isSelected() ? getResources().getColor(R.color.green) : getResources().getColor(R.color.yellow_light));

                }
            });*/

            //AppUtils.setRecyclerViewAnimation(mActivity,holder.itemView, position, lastPosition);
        }

        @Override
        public int getItemViewType(int position) {
            //return super.getItemViewType(position);
            return position;
        }

        public int getItemCount() {
            return data.size();
        }
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

    private void selectDate(final TextView textView, int type) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(AchievedNutrientGraphActivity.this,
                (view, year, monthOfYear, dayOfMonth) -> {

                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;

                    if (type == 1) {

                        fromDate = year + "-"
                                + (monthOfYear + 1) + "-" + dayOfMonth;
                        textView.setText(Utils.formatDateNew(fromDate));
                    } else if (type == 2) {

                        toDate = year + "-"
                                + (monthOfYear + 1) + "-" + dayOfMonth;
                        textView.setText(Utils.formatDateNew(toDate));
                    }

                    c.set(Calendar.YEAR, year);
                    c.set(Calendar.MONTH, monthOfYear);
                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                }, mYear, mMonth, mDay);
        // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
    }

}
