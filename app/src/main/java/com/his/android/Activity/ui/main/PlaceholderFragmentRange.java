package com.his.android.Activity.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.his.android.R;

public class PlaceholderFragmentRange extends Fragment {

    private static final String ARG_SECTION_NUMBER_RANGE = "section_number";

    private PageViewModelRange pageViewModelRange;

    public static PlaceholderFragmentRange newInstance(int index) {
        PlaceholderFragmentRange fragment = new PlaceholderFragmentRange();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER_RANGE, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModelRange = ViewModelProviders.of(this).get(PageViewModelRange.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER_RANGE);
        }
        pageViewModelRange.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_intake_output_vital_range, container, false);
        final TextView textView = root.findViewById(R.id.sectionLabelIVRange);
        pageViewModelRange.getText().observe(this, textView::setText);
        return root;
    }
}