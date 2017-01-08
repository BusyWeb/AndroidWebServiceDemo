package com.busyweb.webservicedemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.busyweb.webservicedemo.classes.DeviceListAdapter;

public class MainActivity extends AppCompatActivity {

    private DeviceListAdapter mDeviceListAdapter = null;
    private RecyclerView mRecyclerView = null;
    private LinearLayoutManager mLinearLayoutManager = null;
    private ProgressDialog progressDialog;
    private RadioGroup mRadioGroupBrand;
    private Button mButtonLoad;

    private Activity mActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppShared.gActivity = this;
        AppShared.gContext = this;
        mActivity = this;
        mContext = this;

        prepareApp();


    }

    private void prepareApp() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewDevices);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRadioGroupBrand = (RadioGroup) findViewById(R.id.radioGroupBrand);
        mButtonLoad = (Button) findViewById(R.id.buttonLoad);

        mButtonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String brand = "all";
                switch (mRadioGroupBrand.getCheckedRadioButtonId()) {
                    case R.id.radioButtonAll:
                        brand = "all";
                        break;
                    case R.id.radioButtonAndroid:
                        brand = "android";
                        break;
                    case R.id.radioButtonWindows:
                        brand = "microsoft";
                        break;
                    case R.id.radioButtonApple:
                        brand = "apple";
                        break;
                    default:
                        brand = "all";
                }

                new LoadDevicesTask().execute(brand);
            }
        });
    }



    private class LoadDevicesTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            showProgressDialog("Processing...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String data = "";
            try {
                String brand = strings[0];
                data = AppShared.LoadDeviceFromWebService(brand);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            if (data == null || data.equalsIgnoreCase("null") || data.length() < 1) {
                // no data
            } else {
                AppShared.Devices = AppShared.LoadDevices(data);
            }

            hideProgressDialog();

            displayDevices();
        }
    }

    private void displayDevices() {
        try {

            if (AppShared.Devices == null || AppShared.Devices.size() < 1) {
                if (mDeviceListAdapter != null) {
                    mDeviceListAdapter.notifyDataSetChanged();
                }
                return;
            }

//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//
//                }
//            });
//            thread.start();

            mDeviceListAdapter = new DeviceListAdapter(AppShared.Devices);
            mRecyclerView.setAdapter(mDeviceListAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showProgressDialog(final String message) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = ProgressDialog.show(mContext, null, message);
            }
        });
    }

    private void hideProgressDialog() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }
}
