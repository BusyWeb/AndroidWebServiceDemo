package com.busyweb.webservicedemo.classes;

import android.os.ParcelFileDescriptor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.busyweb.webservicedemo.AppShared;
import com.busyweb.webservicedemo.R;

import java.util.ArrayList;

/**
 * Created by BusyWeb on 1/7/2017.
 */

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceItemHolder> {

    public ArrayList<Device> mDevices;

    public DeviceListAdapter(ArrayList<Device> devices) {
        mDevices = devices;
    }

    @Override
    public DeviceItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(AppShared.gContext).inflate(R.layout.cardview_device_item, parent, false);
        DeviceItemHolder holder = new DeviceItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DeviceItemHolder holder, int position) {
        try {
            final Device device = mDevices.get(position);
            holder.textViewName.setText(device.Name);
            holder.textViewBrand.setText(device.Brand);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
