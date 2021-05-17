package com.his.android.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.his.android.R;
import com.his.android.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class WardRound extends BaseFragment {
    EditText edtBpSys, edtBpDias, edtPulseF, edtPulseB, edtFlowRate, edtFio2, edtPeep, edtVentilatorRr, edtTidal, edtInfusionRate;
    Spinner spnInfusion, spnO2PositionType, spnPositionType;
    private List<Item> positionList;
    private List<Item> o2SupportList;
    private List<Item> infusionList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ward_round, container, false);
        edtBpSys=view.findViewById(R.id.edtBpSys);
        edtBpDias=view.findViewById(R.id.edtBpDias);
        edtPulseF=view.findViewById(R.id.edtPulseF);
        edtPulseB=view.findViewById(R.id.edtPulseB);
        edtFlowRate=view.findViewById(R.id.edtFlowRate);
        edtFio2=view.findViewById(R.id.edtFio2);
        edtPeep=view.findViewById(R.id.edtPeep);
        edtVentilatorRr=view.findViewById(R.id.edtVentilatorRr);
        edtTidal=view.findViewById(R.id.edtTidal);
        edtInfusionRate=view.findViewById(R.id.edtInfusionRate);
        spnInfusion=view.findViewById(R.id.spnInfusion);
        spnO2PositionType=view.findViewById(R.id.spnO2PositionType);
        spnPositionType=view.findViewById(R.id.spnPositionType);
        positionList=new ArrayList<>();
        o2SupportList=new ArrayList<>();
        infusionList=new ArrayList<>();
        positionList.add(0, new Item("-Select Position Type-", 0));
        o2SupportList.add(0, new Item("-Select Life Support-", 0));
        infusionList.add(0, new Item("-Select Infusion-", 0));
        ArrayAdapter<Item> positionAdp = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, positionList);
        ArrayAdapter<Item> o2SupportAdp = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, o2SupportList);
        ArrayAdapter<Item> infusionAdp = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, infusionList);
        spnPositionType.setAdapter(positionAdp);
        spnO2PositionType.setAdapter(o2SupportAdp);
        spnInfusion.setAdapter(infusionAdp);
        return view;
    }
    private class Item {
        String value;
        int id;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return value;
        }

        public Item(String value, int id) {
            this.value = value;
            this.id = id;
        }
    }
}