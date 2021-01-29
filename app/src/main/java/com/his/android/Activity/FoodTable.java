package com.his.android.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import com.his.android.R;
import com.his.android.view.BaseActivity;

public class FoodTable extends BaseActivity {
    Spinner spnTable;
    RecyclerView rvFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_table);
        spnTable=findViewById(R.id.spnTable);
        rvFood=findViewById(R.id.rvFood);

    }
}