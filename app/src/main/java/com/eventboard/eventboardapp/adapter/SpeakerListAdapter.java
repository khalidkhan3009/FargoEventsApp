package com.eventboard.eventboardapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eventboard.eventboardapp.R;
import com.eventboard.eventboardapp.activity.BaseActivity;
import com.eventboard.eventboardapp.customview.CircleImageView;
import com.eventboard.eventboardapp.pojo.Speaker;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Khalid Khan on 03-06-2019.
 */

public class SpeakerListAdapter extends RecyclerView.Adapter<SpeakerListAdapter.MyViewHolder> {

    private Context context;
    private List<Speaker> speakerList;
    private final String TAG = BaseActivity.TAG;

    public SpeakerListAdapter(Context context, List<Speaker> speakerList) {
        this.context = context;
        this.speakerList = speakerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.speaker_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Speaker speaker = speakerList.get(position);
        holder.text_speaker_name.setText(speaker.getFirst_name() + " " + speaker.getFirst_name());
        holder.text_speaker_description.setText(speaker.getBio());
        Picasso.with(context).load(speaker.getImage_url()).into(holder.img_speaker);
    }

    @Override
    public int getItemCount() {
        return speakerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView text_speaker_name, text_speaker_description;
        private CircleImageView img_speaker;
        private RelativeLayout item_layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            item_layout = (RelativeLayout) itemView.findViewById(R.id.item_layout) ;
            text_speaker_name = (TextView)itemView.findViewById(R.id.text_speaker_name);
            text_speaker_description = (TextView)itemView.findViewById(R.id.text_speaker_description);
            img_speaker = (CircleImageView) itemView.findViewById(R.id.img_speaker);
        }
    }
}
