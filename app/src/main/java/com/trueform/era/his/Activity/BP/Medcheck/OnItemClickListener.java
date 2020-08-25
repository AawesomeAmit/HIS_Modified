package com.trueform.era.his.Activity.BP.Medcheck;

import android.view.View;

public interface OnItemClickListener<T> {
    void onItemClick(View view, T object, int position);
}