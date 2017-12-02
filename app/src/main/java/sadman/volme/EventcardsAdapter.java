package sadman.volme;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.bumptech.glide.Glide;


import java.util.List;

/**
 * Created by sadman on 02/12/17.
 */

public class EventcardsAdapter extends ArrayAdapter<EventCard> {
    public EventcardsAdapter(Context context, int resource, List<EventCard> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_event, parent, false);
        }
        TextView event_oranisator_name = (TextView) convertView.findViewById(R.id.event_oranisator_name);
        TextView event_title_name = (TextView) convertView.findViewById(R.id.event_title_name);
        TextView event_quick_description = (TextView) convertView.findViewById(R.id.event_quick_description);
        TextView event_date_textview = (TextView) convertView.findViewById(R.id.event_date_textview);
        TextView location_textview = (TextView) convertView.findViewById(R.id.location_textview);
        TextView tag_small_textview = (TextView) convertView.findViewById(R.id.tag_small_textview);

        EventCard eventview = getItem(position);

        event_oranisator_name.setText(eventview.getOrganizator_name());
        event_title_name.setText(eventview.getEvent_title());
        event_quick_description.setText(eventview.getEvent_quick_description());
        event_date_textview.setText(eventview.getEvent_data());
        location_textview.setText(eventview.getEvent_location());
        tag_small_textview.setText(eventview.getEvent_tag());


        return convertView;

    }
}