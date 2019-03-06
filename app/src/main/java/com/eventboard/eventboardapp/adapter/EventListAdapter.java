package com.eventboard.eventboardapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eventboard.eventboardapp.R;
import com.eventboard.eventboardapp.activity.BaseActivity;
import com.eventboard.eventboardapp.activity.EventDetailsActivity;
import com.eventboard.eventboardapp.pojo.Events;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Khalid Khan on 03-04-2019.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder>  {

    private Context context;
    private List<Events> eventsList;
    private final String TAG = BaseActivity.TAG;

    public EventListAdapter(Context context, List<Events> eventsList) {
        this.context = context;
        this.eventsList = eventsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.events_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Events event = eventsList.get(position);
        holder.text_event_name.setText(event.getTitle());

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
            holder.text_event_time.setText(finalTime);
        }catch (ParseException ex) {
            Log.d(TAG, ex.getLocalizedMessage());
            holder.text_event_time.setText("00/00/00 00:00AM - 00:00PM");
        }

        Picasso.with(context).load(event.getImage_url()).into(holder.img_event);

        holder.item_layout.setTag(event);
        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout layout = (RelativeLayout)v;
                Events event = (Events) layout.getTag();

                Intent intent= new Intent(context, EventDetailsActivity.class);
                intent.putExtra("EVENT_ID",event.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView text_event_name, text_event_time;
        private ImageView img_event;
        private RelativeLayout item_layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            item_layout = (RelativeLayout) itemView.findViewById(R.id.item_layout) ;
            text_event_name = (TextView)itemView.findViewById(R.id.text_event_name);
            text_event_time = (TextView)itemView.findViewById(R.id.text_event_time);
            img_event = (ImageView) itemView.findViewById(R.id.img_event);
;
        }
    }
}
