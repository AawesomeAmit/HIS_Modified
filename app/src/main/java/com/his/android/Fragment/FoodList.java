package com.his.android.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Activity.ViewNutriAnlzrFoodDtl;
import com.his.android.Model.FoodListModel;
import com.his.android.R;
import com.his.android.Response.FoodListRes;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.his.android.Fragment.NutriAnalyserFragment.NUTRI_TOKEN;

public class FoodList extends BaseFragment {

    static SharedPrefManager sharedPrefManager;

    static RecyclerView recyclerViewFoodList;

    private static Activity context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_food_list, container, false);

       // NutriAnalyserFragment.check = 4;

        init(view);

        return view;
    }

    private void init(View view){

        context = mActivity;

        sharedPrefManager = SharedPrefManager.getInstance(getActivity());

        recyclerViewFoodList = view.findViewById(R.id.recyclerViewFoodList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewFoodList.setHasFixedSize(true);
        recyclerViewFoodList.setLayoutManager(layoutManager);

        if (ConnectivityChecker.checker(mActivity)){
            hitFoodListApi(mActivity);
        }else {
            Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    public static void hitFoodListApi(Activity activity){
        Utils.showRequestDialog(activity);

     //   progressDialog.show();
        Call<FoodListRes> call = RetrofitClient1.getInstance().getApi().getFoodListFoodTimeId(
                NUTRI_TOKEN,
                SharedPrefManager.getInstance(context).getMemberId().getMemberId().toString(),
               // sharedPrefManager.getString("foodTimeId"),
                "0",
                SharedPrefManager.getInstance(context).getIntakeDate(),
                SharedPrefManager.getInstance(context).getMemberId().getUserLoginId().toString());

        call.enqueue(new Callback<FoodListRes>() {
            @Override
            public void onResponse(Call<FoodListRes> call, Response<FoodListRes> response) {
                FoodListRes getRequiredSupplementByFoodTimeIdResp = response.body();
                if (getRequiredSupplementByFoodTimeIdResp != null && getRequiredSupplementByFoodTimeIdResp.getResponseCode() == 1) {


                    List<FoodListModel> tableResponses = response.body().getResponseValue();
                    FoodListAdapter prescriptionHisAdapter = new FoodListAdapter(context, tableResponses);
                    recyclerViewFoodList.setAdapter(prescriptionHisAdapter);
                }
                //progressDialog.dismiss();
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<FoodListRes> call, Throwable t) {

                Utils.hideDialog();
               // progressDialog.dismiss();

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }


    public static class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

        private Context context;
        private List<FoodListModel> intkFoodLists;

        public FoodListAdapter(Context context, List<FoodListModel> intkFoodLists) {
            this.context = context;
            this.intkFoodLists = intkFoodLists;
        }

        @NonNull
        @Override
        public FoodListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.innerview_analyser_foodlist, viewGroup, false);
            return new FoodListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FoodListAdapter.ViewHolder viewHolder, final int i) {

            try {
                viewHolder.tvSno.setText((i+1)+"");
                viewHolder.tvFoodName.setText(intkFoodLists.get(i).getFoodName());
                viewHolder.tvQuantity.setText(String.valueOf(intkFoodLists.get(i).getFoodQuantity()));
                viewHolder.tvUnit.setText(intkFoodLists.get(i).getUnitName());

                viewHolder.llMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent a = new Intent(context, ViewNutriAnlzrFoodDtl.class);
                        a.putExtra("foodName",intkFoodLists.get(i).getFoodName());
                        a.putExtra("foodQuantity",intkFoodLists.get(i).getFoodQuantity());
                        a.putExtra("unitName",intkFoodLists.get(i).getUnitName());
                        a.putExtra("dietId",intkFoodLists.get(i).getDietID());
                        a.putExtra("dietType",intkFoodLists.get(i).getDietType());

                        context.startActivity(a);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return intkFoodLists .size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvSno,tvFoodName,tvQuantity,tvUnit;
            LinearLayout llMain;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                tvSno = itemView.findViewById(R.id.tvSno);
                tvFoodName = itemView.findViewById(R.id.tvFoodName);
                tvQuantity = itemView.findViewById(R.id.tvQuantity);
                tvUnit = itemView.findViewById(R.id.tvUnit);
                llMain = itemView.findViewById(R.id.llMain);



            }
        }
    }

}
