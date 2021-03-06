package com.his.android.Fragment.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.his.android.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragmentIntake extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModelIntake pageViewModel;

    public static PlaceholderFragmentIntake newInstance(int index) {
        PlaceholderFragmentIntake fragment = new PlaceholderFragmentIntake();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModelIntake.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_intake_input, container, false);
        final TextView textView = root.findViewById(R.id.section_labels);
        pageViewModel.getText().observe(this, textView::setText);
        return root;
    }
}