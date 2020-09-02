package com.his.android.Activity.UploadMultipleImg;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.his.android.R;

public class FragmentSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_sample);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, SampleFragment.newInstance("", ""))
                .commit();
    }
}
