package com.busyweb.webservicedemo.classes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.busyweb.webservicedemo.R;

/**
 * Created by BusyWeb on 1/7/2017.
 */
public class DeviceItemHolder extends RecyclerView.ViewHolder {

    public CardView cardViewItem;
    public TextView textViewName;
    public TextView textViewBrand;

    public int position;

    public DeviceItemHolder(View itemView) {
        super(itemView);

        cardViewItem = (CardView) itemView.findViewById(R.id.cardViewDeviceItem);
        textViewName = (TextView) itemView.findViewById(R.id.textViewDeviceName);
        textViewBrand = (TextView) itemView.findViewById(R.id.textViewDeviceBrand);
    }
}
