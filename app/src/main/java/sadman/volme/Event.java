package sadman.volme;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sadman on 05/12/17.
 */

public class Event {
    private String organization_name;
    private String event_title;
    private String event_description;
    private String event_data;
    private String event_location;
    private String event_tag;

    public Event() {
    }

    public Event(String organization_name, String event_title, String event_description, String event_data, String event_location, String event_tag) {
        this.organization_name = organization_name;
        this.event_title = event_title;
        this.event_description = event_description;
        this.event_data = event_data;
        this.event_location = event_location;
        this.event_tag = event_tag;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }

    public String getEvent_title() {
        return event_title;
    }

    public String getEvent_data() {return event_data;}
    public void setEvent_data(String event_data) {this.event_data = event_data;}

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getEvent_data() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_tag() {
        return event_tag;
    }

    public void setEvent_tag(String event_tag) {
        this.event_tag = event_tag;
    }



    public class event extends AppCompatActivity {
        public void openEvent(View v) {
            Intent i = new Intent(this, EventActivity.class);
            startActivity(i);


        }
    }
}
