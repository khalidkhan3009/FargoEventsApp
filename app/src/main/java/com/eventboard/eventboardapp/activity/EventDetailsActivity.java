package com.eventboard.eventboardapp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eventboard.eventboardapp.R;
import com.eventboard.eventboardapp.adapter.SpeakerListAdapter;
import com.eventboard.eventboardapp.pojo.Events;
import com.eventboard.eventboardapp.pojo.Speaker;
import com.eventboard.eventboardapp.utils.Constant;
import com.eventboard.eventboardapp.utils.DataPreference;
import com.eventboard.eventboardapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailsActivity extends BaseActivity {

    private int event_id;
    private ImageView expanded_event_image;
    private Menu menu;
    private Toolbar mToolbar;
    private TextView txt_event_title, txt_event_time, txt_event_description, txt_event_location;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SpeakerListAdapter adapter;
    private List<Speaker> speakerList;
    private LinearLayout layout_speaker, layout_event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Event Details");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        event_id = getIntent().getIntExtra("EVENT_ID",0);

        speakerList = new ArrayList<>();

       // setAppBarLayout();
        initView();
        initRecyclerView();
        getEventDetails();

    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_speakers_list);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

   /* private void setAppBarLayout() {
        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    showOption(R.id.action_info);
                } else if (isShow) {
                    isShow = false;
                    hideOption(R.id.action_info);
                }
            }
        });
    }*/

    private void initView() {
        layout_speaker = (LinearLayout)findViewById(R.id.layout_speaker);
        layout_event = (LinearLayout)findViewById(R.id.layout_event);

        expanded_event_image = (ImageView)findViewById(R.id.expanded_event_image);

        txt_event_title = (TextView) findViewById(R.id.txt_event_title);
        txt_event_time = (TextView) findViewById(R.id.txt_event_time);
        txt_event_description = (TextView) findViewById(R.id.txt_event_description);
        txt_event_location = (TextView) findViewById(R.id.txt_event_location);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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

    private void getEventDetails() {
        Utils.initLoading(this);
        Utils.showLoading("");

        Call<Events> call = service.getEventDetails(DataPreference.getPref(context, Constant.LOGIN_TOKEN, ""), event_id);

        call.enqueue(new Callback<Events>() {
            @Override
            public void onResponse(Call<Events> call, Response<Events> response) {
                Events event = response.body();
                setEventDetails(event);
            }

            @Override
            public void onFailure(Call<Events> call, Throwable t) {
                Utils.hideLoading();
                Utils.showToast(context, "Something went wrong...Please try later!");
                Log.d(TAG, t.getMessage());
            }
        });
    }

    private void setEventDetails(final Events event) {

        Picasso.with(context).load(event.getImage_url()).into(expanded_event_image, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                txt_event_title.setText(event.getTitle());
                setEventTime(event);
                txt_event_description.setText(event.getEvent_description());
                txt_event_location.setText(event.getLocation());

                for(int i=0; i<event.getSpeakers().size();i++){
                    getSpeakerDetails(event.getSpeakers().get(i).getId());
                }
                Utils.hideLoading();
                adapter = new SpeakerListAdapter(context, speakerList);
                recyclerView.setAdapter(adapter);
                layout_event.setVisibility(View.VISIBLE);
                layout_speaker.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                Utils.hideLoading();
                Utils.showToast(context, "Something went wrong...Please try later!");
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getSpeakerDetails(int speakerId) {

        Call<Speaker> call = service.getSpeakerDetails(DataPreference.getPref(context, Constant.LOGIN_TOKEN, ""), speakerId);

        call.enqueue(new Callback<Speaker>() {
            @Override
            public void onResponse(Call<Speaker> call, Response<Speaker> response) {
                Speaker speaker = response.body();
                 speakerList.add(speaker);
                 adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Speaker> call, Throwable t) {
                Utils.hideLoading();
                Utils.showToast(context, "Something went wrong...Please try later!");

            }
        });

    }

    private void setEventTime(Events event) {

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        DateFormat timeFormat = new SimpleDateFormat("hh:mma");
        try{
            Date d1 = sdf.parse(event.getStart_date_time());
            Date d2 = sdf.parse(event.getEnd_date_time());

            String date = dateFormat.format(d1);
            String startTime = timeFormat.format(d1);
            String endTime = timeFormat.format(d2);

            String finalTime = date + " " + startTime + " - " + endTime;
            txt_event_time.setText(finalTime);
        }catch (ParseException ex) {
            Log.d(TAG, ex.getLocalizedMessage());
            txt_event_time.setText("00/00/00 00:00AM - 00:00PM");
        }
    }
}
