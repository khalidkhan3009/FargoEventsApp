package com.eventboard.eventboardapp.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.eventboard.eventboardapp.R;
import com.eventboard.eventboardapp.pojo.Events;
import com.eventboard.eventboardapp.utils.Constant;
import com.eventboard.eventboardapp.utils.DataPreference;
import com.eventboard.eventboardapp.utils.Utils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailsActivity extends BaseActivity {

    private int event_id;
    private ImageView expanded_event_image;
    private Menu menu;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Event Details");
        setSupportActionBar(mToolbar);

        event_id = getIntent().getIntExtra("EVENT_ID",0);

       // setAppBarLayout();
        initView();
        getEventDetails();
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
        expanded_event_image = (ImageView)findViewById(R.id.expanded_event_image);
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
                Utils.hideLoading();
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

    private void setEventDetails(Events event) {
        Utils.initLoading(this);
        Utils.showLoading("");

        Picasso.with(context).load(event.getImage_url()).into(expanded_event_image, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                Utils.hideLoading();
            }

            @Override
            public void onError() {
                Utils.hideLoading();

            }
        });


    }
}
