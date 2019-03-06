package com.eventboard.eventboardapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.eventboard.eventboardapp.R;
import com.eventboard.eventboardapp.adapter.EventListAdapter;
import com.eventboard.eventboardapp.pojo.Events;
import com.eventboard.eventboardapp.utils.Constant;
import com.eventboard.eventboardapp.utils.DataPreference;
import com.eventboard.eventboardapp.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsActivity extends BaseActivity {

    Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EventListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Events");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        initRecyclerView();

        getEventsData();
    }

    private void getEventsData() {
        Utils.initLoading(this);
        Utils.showLoading("");
       /*  *//*Create handle for the RetrofitInstance interface*//*
        APIEndpoints service = EventsAPI.getRetrofitInstance().create(APIEndpoints.class);*/

        Call<List<Events>> call = service.getEvents(DataPreference.getPref(context, Constant.LOGIN_TOKEN, ""));

        call.enqueue(new Callback<List<Events>>() {
            @Override
            public void onResponse(Call<List<Events>> call, Response<List<Events>> response) {
                Utils.hideLoading();
                List<Events> eventList = response.body();
                adapter = new EventListAdapter(context, eventList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Events>> call, Throwable t) {
                Utils.hideLoading();
                Utils.showToast(context, "Something went wrong...Please try later!");
            }
        });

    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_events_list);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logoutDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}