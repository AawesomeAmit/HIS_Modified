package com.his.android.Activity.BP.Medcheck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.getmedcheck.lib.model.BleDevice;
import com.his.android.R;

public class ScanResultAdapter extends BaseAdapter<BleDevice, ScanResultAdapter.ViewHolder> {

    public ScanResultAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_device, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BleDevice bleDevice = getListItem(position);
        holder.tvDeviceName.setText(bleDevice.getDeviceName());
        holder.tvDeviceAddress.setText(bleDevice.getMacAddress());
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvDeviceName;
        TextView tvDeviceAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDeviceName = itemView.findViewById(R.id.tvDeviceName);
            tvDeviceAddress = itemView.findViewById(R.id.tvDeviceAddress);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getListItem(getAdapterPosition()), getAdapterPosition());
            }
        }
    }
}
