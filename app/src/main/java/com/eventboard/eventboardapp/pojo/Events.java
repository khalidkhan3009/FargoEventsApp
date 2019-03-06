package com.eventboard.eventboardapp.pojo;

import java.util.List;

/**
 * Created by Khalid Khan on 03-04-2019.
 */

public class Events {
    private int id;
    private String title;
    private String image_url;
    private String event_description;
    private String start_date_time;
    private String end_date_time;
    private String location;
    private boolean featured;
    private List<Speaker> speakers;

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(String start_date_time) {
        this.start_date_time = start_date_time;
    }

    public String getEnd_date_time() {
        return end_date_time;
    }

    public void setEnd_date_time(String end_date_time) {
        this.end_date_time = end_date_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }
}
