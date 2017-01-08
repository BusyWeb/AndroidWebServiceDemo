package com.busyweb.webservicedemo.classes;

import com.busyweb.webservicedemo.AppUtil;

import org.json.JSONObject;

/**
 * Created by BusyWeb on 1/7/2017.
 */

public class Device {
    public int Id;
    public String Name;
    public String Brand;

    public Device() {}
    public Device(JSONObject device) {
        try {
            if (device == null) {
                return;
            }
            Object objectId = device.get("Id");
            Object objectName = device.get("Name");
            Object objectBrand = device.get("Brand");

            Id = (!AppUtil.isObjectNullOrEmpty(objectId) ? (Integer)objectId : -1);
            Name = (!AppUtil.isObjectNullOrEmpty(objectName) ? (String)objectName : "");
            Brand = (!AppUtil.isObjectNullOrEmpty(objectBrand) ? (String)objectBrand : "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
