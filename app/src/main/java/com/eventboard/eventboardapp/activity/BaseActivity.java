package com.eventboard.eventboardapp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eventboard.eventboardapp.R;
import com.eventboard.eventboardapp.retrofit.APIEndpoints;
import com.eventboard.eventboardapp.retrofit.EventsAPI;
import com.eventboard.eventboardapp.utils.Constant;
import com.eventboard.eventboardapp.utils.DataPreference;

/**
 * Created by Khalid Khan on 03-04-2019.
 */

public class BaseActivity extends AppCompatActivity {

    public Context context;
    public static final String TAG = "EventsAPI";
    public APIEndpoints service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

         /*Create handle for the RetrofitInstance interface*/
       service = EventsAPI.getRetrofitInstance().create(APIEndpoints.class);

    }

    public void logoutDialog() {

        final Dialog dd = new Dialog(context);
        try {
            dd.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dd.setContentView(R.layout.dialog_iphone_confirm);
            dd.getWindow().setLayout(-1, -2);
            dd.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            ((TextView) dd.findViewById(R.id.tvConfirmOk)).setText("Logout");
            ((TextView) dd.findViewById(R.id.tvConfirmOk)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dd.dismiss();
                    DataPreference.setPref(context, Constant.LOGIN_FLAG, false);
                    DataPreference.setPref(context, Constant.LOGIN_TOKEN, "");
                    startActivity(new Intent(context, LoginActivity.class));
                    finish();
                }
            });

            ((TextView) dd.findViewById(R.id.tvConfirmCancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dd.dismiss();
                }
            });
            dd.show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Exception: " + e.getMessage());
        }
    }
}
