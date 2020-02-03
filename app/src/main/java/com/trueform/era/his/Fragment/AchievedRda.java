package com.trueform.era.his.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Adapter.GraphColorcodeAdp;
import com.trueform.era.his.Adapter.Intake1pgTopRecyclerViewAdp;
import com.trueform.era.his.Adapter.ViewRdaAdapter;
import com.trueform.era.his.Model.GraphColorcodeList;
import com.trueform.era.his.Model.NutriAnalyze;
import com.trueform.era.his.Model.ViewIntkAllPriortyNutriList;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.GraphColorcodeRes;
import com.trueform.era.his.Response.NutriAnalyzerDataRes;
import com.trueform.era.his.Response.NutriAnalyzerResp;
import com.trueform.era.his.Response.ViewIntkAllPriortyNutriRes;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient1;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;
import com.trueform.era.his.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.trueform.era.his.Fragment.NutriAnalyserFragment.NUTRI_TOKEN;

public class AchievedRda extends BaseFragment {
    static RecyclerView rdaAnalyser,rvColorCode, recyclerViewTop,recyclerViewMain;
    static List<NutriAnalyze> nutriAnalyzers;
    TextView tv;
    static Context context;
    static Activity activity;
    private static SharedPrefManager sharedPrefManager;

    ImageView imgInfo;
 //   static ProgressDialog progressDialog;

    static EditText etSearch;

    static NutriAnalyzerResp analyzerResp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_achieved_rda_nutrianalyser, container, false);

        NutriAnalyserFragment.check = 1;

        activity = mActivity;

        sharedPrefManager = SharedPrefManager.getInstance(getActivity());

        nutriAnalyzers = new ArrayList<>();
        context = container != null ? container.getContext() : null;
        rdaAnalyser = view.findViewById(R.id.rdaAnalyser);
        recyclerViewMain = view.findViewById(R.id.recyclerViewMain);
        recyclerViewTop = view.findViewById(R.id.recyclerViewTop);
        recyclerViewTop.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));

        imgInfo = view.findViewById(R.id.infoIcon);
        etSearch = view.findViewById(R.id.etSearch);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rdaAnalyser.setHasFixedSize(true);
        rdaAnalyser.setLayoutManager(layoutManager);
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorInfo();

            }
        });

        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(context));

        if (ConnectivityChecker.checker(mActivity)) {

            hitGetAllNutrient(getActivity());
            getNutrientValues();

        } else {
            Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    // dialouge view for graph colour
    private void colorInfo() {

        final Dialog builder1 = new Dialog(context);
        builder1.setTitle("");
        builder1.setContentView(R.layout.dialoge_for_graph_color_info);
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder1.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        // builder1.getWindow().setBackgroundDrawableResource(R.drawable.dialouge_box_design);
        builder1.setCancelable(true);


        builder1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        rvColorCode = builder1.findViewById(R.id.list);

        hitgetColorCode(mActivity);

        builder1.show();

    }

    // get color Code

    private void hitgetColorCode(Activity activity) {

        Utils.showRequestDialog(activity);

        Call<GraphColorcodeRes> call = RetrofitClient1.getInstance().getApi().getColor();

        call.enqueue(new Callback<GraphColorcodeRes>() {
            @Override
            public void onResponse(Call<GraphColorcodeRes> call, Response<GraphColorcodeRes> response) {

                GraphColorcodeRes waterIntakeRes = (GraphColorcodeRes) response.body();

                if (waterIntakeRes != null && waterIntakeRes.getResponseCode() == 1) {

                    final List<GraphColorcodeList> tableResponses = ((GraphColorcodeRes) response.body()).getResponseValue();
                    GraphColorcodeAdp prescriptionHisAdapter = new GraphColorcodeAdp(context, tableResponses);

                    rvColorCode.setLayoutManager(new LinearLayoutManager(context));

                    rvColorCode.setAdapter(prescriptionHisAdapter);
                }

                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GraphColorcodeRes> call, Throwable t) {
                //progressDialog.cancel();
                Utils.hideDialog();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    public static void hitGetAllNutrient(Activity activity){
        //progressDialog.show();
        Utils.showRequestDialog(activity);

        Call<NutriAnalyzerResp> call = RetrofitClient1.getInstance().getApi().GetAllNutrientValuesCombinedByFoodTimeId(
                NUTRI_TOKEN,
                SharedPrefManager.getInstance(context).getMemberId().getMemberId().toString(),
               // sharedPrefManager.getString("foodTimeId"),
                "0",
                SharedPrefManager.getInstance(context).getIntakeDate(),
                SharedPrefManager.getInstance(context).getMemberId().getUserLoginId().toString());

        Log.v("hitGetAllNutrient", call.toString());

        call.enqueue(new Callback<NutriAnalyzerResp>() {
            @Override
            public void onResponse(Call<NutriAnalyzerResp> call, Response<NutriAnalyzerResp> response) {

               // progressDialog.cancel();

               // nutriAnalyzers.add(analyzerResp.getNutriAnalyzeValue());

                analyzerResp = response.body();

                List<NutriAnalyzerDataRes> list = analyzerResp.getResponseValue();

           //     NutriAnalyze nutriAnalyze = nutriAnalyzerResp.getNutriAnalyzeValue().get(i);
             //   analyzerRespSort = response.body();

                if (analyzerResp != null && analyzerResp.getResponseCode() == 1) {

                    final ViewRdaAdapterMain adapter = new ViewRdaAdapterMain(context,list);
                    //rdaAnalyser.setAdapter(adapter);
                    recyclerViewMain.setAdapter(adapter);

                    /*etSearch.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            adapter.getFilter().filter(s.toString());

                        }
                    });*/
                }

                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<NutriAnalyzerResp> call, Throwable t) {
                //progressDialog.cancel();
                Utils.hideDialog();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getNutrientValues(){

        Utils.showRequestDialog(activity);

       /* if (firstTime){
            foodTimeId = "0";
            tvMealTime.setText("Whole Day Meals");
        }
*/

        Call<ViewIntkAllPriortyNutriRes> call1 = RetrofitClient1.getInstance().getApi().viewIntkPriortyNutri(
                NUTRI_TOKEN,
                SharedPrefManager.getInstance(context).getMemberId().getMemberId().toString(),
              //  sharedPrefManager.getString("foodTimeId"),
                "0",
                SharedPrefManager.getInstance(context).getIntakeDate(),
                SharedPrefManager.getInstance(context).getMemberId().getUserLoginId().toString()
        );

        call1.enqueue(new Callback<ViewIntkAllPriortyNutriRes>() {

            @Override
            public void onResponse(Call<ViewIntkAllPriortyNutriRes> call, Response<ViewIntkAllPriortyNutriRes> response) {

                // Log.v("emergencycon========",new Gson(response.body()))
                ViewIntkAllPriortyNutriRes prescriptionRes = (ViewIntkAllPriortyNutriRes) response.body();

                if (prescriptionRes != null && prescriptionRes.getResponseCode() == 1) {

                    List<ViewIntkAllPriortyNutriList> tableResponses = ((ViewIntkAllPriortyNutriRes) response.body()).getResponseValue();
                    Intake1pgTopRecyclerViewAdp prescriptionHisAdapter = new Intake1pgTopRecyclerViewAdp(context, tableResponses);

                    recyclerViewTop.setAdapter(prescriptionHisAdapter);
                }

                Utils.hideDialog();

            }

            @Override
            public void onFailure(Call<ViewIntkAllPriortyNutriRes> call, Throwable t) {
                Utils.hideDialog();

                 Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class ViewRdaAdapterMain extends RecyclerView.Adapter<ViewRdaAdapterMain.MyViewHolder> {//implements Filterable {
        List<NutriAnalyzerDataRes> listResp;
        List<NutriAnalyzerDataRes> listRespFiltered;

        int NutrientID;

        private Context ctx;

        public ViewRdaAdapterMain(Context ctx, List<NutriAnalyzerDataRes> list) {
            this.listResp = list;
            this.listRespFiltered = list;
            this.ctx = ctx;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.inflate_achieved_rda_details, viewGroup, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

            final NutriAnalyzerDataRes nutriAnalyzerDataRes = listRespFiltered.get(i);

            myViewHolder.tvHeader.setText(nutriAnalyzerDataRes.getNutrientCategory());

            List<NutriAnalyze> nutriAnalyzeList = nutriAnalyzerDataRes.getNutrientList();

            final ViewRdaAdapter adapter = new ViewRdaAdapter(context,nutriAnalyzeList);
            //rdaAnalyser.setAdapter(adapter);
            myViewHolder.recyclerViewDetails.setAdapter(adapter);

            etSearch.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    adapter.getFilter().filter(s.toString());

                }
            });


        }

        @Override
        public int getItemCount() {
            return listRespFiltered.size();
        }

       /* @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        listRespFiltered = listResp;
                    } else {
                        List<NutriAnalyzerDataRes> filteredList = new ArrayList<>();
                        for (NutriAnalyzerDataRes row : listResp) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getNutrientCategory().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        listRespFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = listRespFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    listRespFiltered = (ArrayList<NutriAnalyzerDataRes>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }*/

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvHeader;

            RecyclerView recyclerViewDetails;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvHeader = itemView.findViewById(R.id.tvHeader);
                recyclerViewDetails = itemView.findViewById(R.id.recyclerViewDetails);

                recyclerViewDetails.setLayoutManager(new LinearLayoutManager(ctx));
            }
        }
    }

}