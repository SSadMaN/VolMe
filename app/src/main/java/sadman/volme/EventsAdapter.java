package sadman.volme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sadman on 05/12/17.
 */

public class EventsAdapter extends ArrayAdapter<Event> {
    public EventsAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemview = convertView;
        if (listitemview == null) {
            listitemview = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_event, parent, false);
        }
        TextView event_oranisator_name = (TextView) listitemview.findViewById(R.id.event_oranisation_name);
        TextView event_title_name = (TextView) listitemview.findViewById(R.id.event_title_name);
        TextView event_description = (TextView) listitemview.findViewById(R.id.event_quick_description);
        TextView event_date_textview = (TextView) listitemview.findViewById(R.id.event_date_textview);
        TextView location_textview = (TextView) listitemview.findViewById(R.id.location_textview);
        TextView tag_small_textview = (TextView) listitemview.findViewById(R.id.tag_small_textview);

        Event eventview = getItem(position);

        event_oranisator_name.setText(eventview.getOrganization_name());
        event_title_name.setText(eventview.getEvent_title());
        event_description.setText(eventview.getEvent_description());
        event_date_textview.setText(eventview.getEvent_data());
        location_textview.setText(eventview.getEvent_location());
        tag_small_textview.setText(eventview.getEvent_tag());


        return listitemview;

    }
}
