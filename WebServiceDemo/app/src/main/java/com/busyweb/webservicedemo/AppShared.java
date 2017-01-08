package com.busyweb.webservicedemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.busyweb.webservicedemo.classes.Device;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by BusyWeb on 1/7/2017.
 */

public class AppShared {

    private static AppShared appShared = null;
    private static AppShared getInstance() {
        if (appShared == null) {
            appShared = new AppShared();
        }
        return appShared;
    }

    public static Activity gActivity;
    public static Context gContext;

    private static final boolean Debug = true;
    private static final String ServerUrl = "http://www.busywww.com/mycodes";
    private static final String ServerUrlDebug = "http://localhost:8080/mycodes";
    private static String GetServerUrl() {
        return (Debug ? ServerUrlDebug : ServerUrl);
    }
    private static final String ServiceName = "WebService.asmx";
    private static final Integer ServicePort = 80;
    private static final String LoadDevicesUrl = String.format("%s/%s/LoadDevices", GetServerUrl(), ServiceName);

    public static String LoadDeviceFromWebService(String brand) {
        JSONObject params = new JSONObject();
        try {
            params.put("brand", brand);
        } catch (Exception e) {
        }
        return SendWebServiceRequest(LoadDevicesUrl, params);
    }

    public static String SendWebServiceRequest(String url, JSONObject params) {
        String response = "";
        try {

            URL endpoint = new URL(url);
            Uri.Builder builder = new Uri.Builder();
            Iterator<String> iterator = params.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = params.getString(key);
                builder.appendQueryParameter(key, value);
            }
            String body = builder.build().getEncodedQuery();

            HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
            outputStream.write(body.getBytes());
            outputStream.close();

            int status = connection.getResponseCode();
            if (status == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
    public static Bitmap LoadBitmapFromInternet(String path) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static ArrayList<Device> Devices = new ArrayList<Device>();

    public static ArrayList<Device> LoadDevices(String devicesJson) {
        ArrayList<Device> devices = new ArrayList<Device>();
        try {
            JSONArray deviceArray = new JSONArray(devicesJson);
            if (deviceArray == null || deviceArray.length() < 1) {
                return devices;
            }
            for(int i = 0; i < deviceArray.length(); i++) {
                JSONObject deviceObject = deviceArray.getJSONObject(i);
                if (deviceObject != null) {
                    Device device = new Device(deviceObject);
                    if (device != null) {
                        devices.add(device);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return devices;
    }


}
