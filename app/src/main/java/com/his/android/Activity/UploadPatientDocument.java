package com.his.android.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.his.android.R;


import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class UploadPatientDocument extends AppCompatActivity {


    String[] document = {"Select Document Type", "ECG Report", "Patient Activity Image", "Patient Consent", "Patient Image", "Patient Video"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_patient_document);

        Spinner niceSpinner = (Spinner) findViewById(R.id.nice_spinner);
        EditText DateET = (EditText) findViewById(R.id.editTextDate);
        EditText TimeET = (EditText) findViewById(R.id.editTextTime);
        Button filechoose = (Button) findViewById(R.id.button3);
        Button uploaddoc = (Button) findViewById(R.id.button4);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, document) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }

            }

        };


        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        niceSpinner.setAdapter(aa);

        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        DateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Datedialog();
            }
        });

        uploaddoc.setOnClickListener(v -> startActivity(new Intent(UploadPatientDocument.this, UploadDocument.class)));
        TimeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimeDialog();
            }
        });

    }

    private void TimeDialog() {


    }

    public void Datedialog() {


    }


}