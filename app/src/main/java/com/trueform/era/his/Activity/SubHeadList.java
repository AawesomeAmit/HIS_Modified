package com.trueform.era.his.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Adapter.RecyclerTouchListener;
import com.trueform.era.his.Adapter.SubHeadAdp;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.SubHeadIDResp;
import com.trueform.era.his.Utils.ClickListener;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubHeadList extends AppCompatActivity {
    RecyclerView recycler;
    ImageView close;
    TextView txtDept;
    RelativeLayout relLayout;
    SubHeadIDResp subHeadIDResp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_head_list);
        txtDept=findViewById(R.id.txtDept);
        recycler=findViewById(R.id.recycler);
        close=findViewById(R.id.close);
        relLayout=findViewById(R.id.relLayout);
        txtDept.setText(SharedPrefManager.getInstance(SubHeadList.this).getHead().getHeadName());
        recycler.setLayoutManager(new LinearLayoutManager(this));
        relLayout.setBackgroundColor(Color.parseColor(SharedPrefManager.getInstance(SubHeadList.this).getHead().getColor()));
        Call<SubHeadIDResp> call= RetrofitClient.getInstance().getApi().getsubDepertmentByHID(SharedPrefManager.getInstance(SubHeadList.this).getUser().getAccessToken(), SharedPrefManager.getInstance(SubHeadList.this).getUser().getUserid().toString(), SharedPrefManager.getInstance(SubHeadList.this).getHeadID().toString(), SharedPrefManager.getInstance(SubHeadList.this).getUser().getUserid());
        call.enqueue(new Callback<SubHeadIDResp>() {
            @Override
            public void onResponse(Call<SubHeadIDResp> call, Response<SubHeadIDResp> response) {
                if (response.isSuccessful()) {
                    subHeadIDResp=response.body();
                    if(subHeadIDResp.getSubDept().size()>1)
                    recycler.setAdapter(new SubHeadAdp(SubHeadList.this, subHeadIDResp));
                    else {
                        if((SharedPrefManager.getInstance(SubHeadList.this).getHeadID()==2) || (SharedPrefManager.getInstance(SubHeadList.this).getHeadID()==3)||(SharedPrefManager.getInstance(SubHeadList.this).getHeadID()==4)) {
                            Intent intent = new Intent(SubHeadList.this, PatientList.class);
                            SharedPrefManager.getInstance(SubHeadList.this).setSubHead(subHeadIDResp.getSubDept().get(0));
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(SubHeadList.this, EnterPID.class);
                            SharedPrefManager.getInstance(SubHeadList.this).setSubHead(subHeadIDResp.getSubDept().get(0));
                            SharedPrefManager.getInstance(SubHeadList.this).setHeadID(SharedPrefManager.getInstance(SubHeadList.this).getHeadID(), SharedPrefManager.getInstance(SubHeadList.this).getHead().getHeadName(), SharedPrefManager.getInstance(SubHeadList.this).getHead().getColor());
                            startActivity(intent);
                        }
                    }
                } else Toast.makeText(SubHeadList.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SubHeadIDResp> call, Throwable t) {
                Toast.makeText(SubHeadList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        recycler.addOnItemTouchListener(new RecyclerTouchListener(this, recycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(subHeadIDResp.getSubDept().get(position).getHeadID()==1) {
                    SharedPrefManager.getInstance(SubHeadList.this).setSubHead(subHeadIDResp.getSubDept().get(position));
                    Intent intent = new Intent(SubHeadList.this, EnterPID.class);
                    SharedPrefManager.getInstance(SubHeadList.this).setHeadID(SharedPrefManager.getInstance(SubHeadList.this).getHeadID(), SharedPrefManager.getInstance(SubHeadList.this).getHead().getHeadName(), SharedPrefManager.getInstance(SubHeadList.this).getHead().getColor());
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SubHeadList.this, PatientList.class);
                    SharedPrefManager.getInstance(SubHeadList.this).setSubHead(subHeadIDResp.getSubDept().get(position));
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}
