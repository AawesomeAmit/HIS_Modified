package com.his.android.Fragment;

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

import com.his.android.Adapter.RecommendedDietAdapter;
import com.his.android.Model.GetRequiredSupplementByFoodTimeId;
import com.his.android.R;
import com.his.android.Response.GetRequiredSupplementByFoodTimeIdResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.his.android.Fragment.NutriAnalyserFragment.NUTRI_TOKEN;

public class NutriAnalyserIntakeDiet extends BaseFragment {
    static RecyclerView consumeMealList;
    View view;
    List<GetRequiredSupplementByFoodTimeId> getRequiredSupplementByFoodTimeIds;
    private static SharedPrefManager sharedPrefManager;
   // static ProgressDialog progressDialog;

    static Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_nutri_analyser_intake_diet, container, false);

        context = mActivity;

        sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        getRequiredSupplementByFoodTimeIds = new ArrayList<>();
        consumeMealList = view.findViewById(R.id.consumeMealList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        consumeMealList.setHasFixedSize(true);
        consumeMealList.setLayoutManager(layoutManager);

     //   progressDialog = new ProgressDialog(getContext());
     //   progressDialog.setMessage("Loading...");

    //    progressDialog.show();

        if (ConnectivityChecker.checker(getActivity())) {

            hitGetRequiredSupplement(getActivity());
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    public static void hitGetRequiredSupplement(final Activity activity){

        Utils.showRequestDialog(activity);

        Call<GetRequiredSupplementByFoodTimeIdResp> call = RetrofitClient1.getInstance().getApi().GetRequiredSupplementByFoodTimeId(
                NUTRI_TOKEN,
                SharedPrefManager.getInstance(context).getMemberId().getMemberId().toString(),
               // sharedPrefManager.getString("foodTimeId"),
                "0",
                SharedPrefManager.getInstance(context).getIntakeDate(),
                SharedPrefManager.getInstance(context).getMemberId().getUserLoginId().toString());

        call.enqueue(new Callback<GetRequiredSupplementByFoodTimeIdResp>() {
            @Override
            public void onResponse(Call<GetRequiredSupplementByFoodTimeIdResp> call, Response<GetRequiredSupplementByFoodTimeIdResp> response) {

                GetRequiredSupplementByFoodTimeIdResp getRequiredSupplementByFoodTimeIdResp = response.body();
                if (getRequiredSupplementByFoodTimeIdResp != null && getRequiredSupplementByFoodTimeIdResp.getResponseCode() == 1) {

                    RecommendedDietAdapter dietAdapter = new RecommendedDietAdapter(getRequiredSupplementByFoodTimeIdResp, activity);
                    consumeMealList.setAdapter(dietAdapter);

                }

                Utils.hideDialog();
                //    progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetRequiredSupplementByFoodTimeIdResp> call, Throwable t) {
                //  progressDialog.dismiss();
                Utils.hideDialog();

                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
