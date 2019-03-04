package com.eventboard.eventboardapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Khalid Khan on 03-04-2019.
 */

public class BaseActivity extends AppCompatActivity {

    public Context context;
    public final String TAG = "EventsAPI";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }
}
