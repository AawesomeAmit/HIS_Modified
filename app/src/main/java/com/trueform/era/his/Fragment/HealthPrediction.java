package com.trueform.era.his.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Adapter.HealthPredictionAdapter;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.GetNutrientLevelImmediateEffectByFoodTimeIdResp;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient1;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;
import com.trueform.era.his.view.BaseFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.trueform.era.his.Fragment.NutriAnalyserFragment.NUTRI_TOKEN;
import static com.trueform.era.his.Fragment.NutriAnalyserFragment.check;

public class HealthPrediction extends BaseFragment {

    static RecyclerView consumeMealList;
    private static SharedPrefManager sharedPrefManager;

   // static ProgressDialog progressDialog;

    static Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_health_prediction, container, false);

        context = mActivity;

        check = 3;

        sharedPrefManager = SharedPrefManager.getInstance(getActivity());

        consumeMealList = view.findViewById(R.id.consumeMealList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        consumeMealList.setHasFixedSize(true);
        consumeMealList.setLayoutManager(layoutManager);

        if (ConnectivityChecker.checker(getActivity())){
            hitGetNutrientLevel(getActivity());
        }else {
            Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Loading...");
//
//        progressDialog.show();


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    public static void hitGetNutrientLevel(final Activity activity){
        Utils.showRequestDialog(activity);

        Call<GetNutrientLevelImmediateEffectByFoodTimeIdResp> call = RetrofitClient1.getInstance().getApi().GetNutrientLevelImmediateEffectByFoodTimeId(
                NUTRI_TOKEN,
                SharedPrefManager.getInstance(context).getMemberId().getMemberId().toString(),
                //sharedPrefManager.getString("foodTimeId"),
                "0",
                SharedPrefManager.getInstance(context).getIntakeDate(),
                SharedPrefManager.getInstance(context).getMemberId().getUserLoginId().toString());

        call.enqueue(new Callback<GetNutrientLevelImmediateEffectByFoodTimeIdResp>() {
            @Override
            public void onResponse(Call<GetNutrientLevelImmediateEffectByFoodTimeIdResp> call, Response<GetNutrientLevelImmediateEffectByFoodTimeIdResp> response) {



                GetNutrientLevelImmediateEffectByFoodTimeIdResp getRequiredSupplementByFoodTimeIdResp = response.body();
                if (getRequiredSupplementByFoodTimeIdResp != null && getRequiredSupplementByFoodTimeIdResp.getResponseCode() == 1) {

                    HealthPredictionAdapter dietAdapter = new HealthPredictionAdapter(getRequiredSupplementByFoodTimeIdResp, activity);
                    consumeMealList.setAdapter(dietAdapter);
                }

                //progressDialog.dismiss();
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetNutrientLevelImmediateEffectByFoodTimeIdResp> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
