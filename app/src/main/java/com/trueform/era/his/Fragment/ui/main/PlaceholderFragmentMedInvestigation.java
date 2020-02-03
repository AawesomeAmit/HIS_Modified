package com.trueform.era.his.Fragment.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.trueform.era.his.R;

public class PlaceholderFragmentMedInvestigation extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModelMedInvestigation pageViewModel;

    public static PlaceholderFragmentMedInvestigation newInstance(int index) {
        PlaceholderFragmentMedInvestigation fragment = new PlaceholderFragmentMedInvestigation();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModelMedInvestigation.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_prescription, container, false);
        final TextView textView = root.findViewById(R.id.section_labelMedInvestigation);
        pageViewModel.getText().observe(this, textView::setText);
        return root;
    }
}