package sadman.volme;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ThisWeekFragment extends Fragment {

    public ThisWeekFragment(){
        //requires empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.eventcard_list, container, false);

        final List<Event> eventCards = new ArrayList<Event>();

        eventCards.add(new Event("Українська академія лідерства","Екологічна толока в Брюховецькому лісі", "Екологічна акція від студентів Української академії лідерства. УАл - це десятимісячна програма неакадемічного навчання ...","Брюховецький ліс", "14-15 червня", "Екологія"));

        eventCards.add(new Event ("Українська академія лідерства","Екологічна толока в Брюховецькому лісі", "Екологічна акція від студентів Української академії лідерства. УАл - це десятимісячна програма неакадемічного навчання ...","Брюховецький ліс", "14-15 червня", "Екологія"));

        eventCards.add(new Event ("Українська академія лідерства","Екологічна толока в Брюховецькому лісі", "Екологічна акція від студентів Української академії лідерства. УАл - це десятимісячна програма неакадемічного навчання ...","Брюховецький ліс", "14-15 червня", "Екологія"));


        ListView listView = (ListView) rootView.findViewById(R.id.eventcard_list);
        EventsAdapter adapter = new EventsAdapter(getActivity(), R.layout.item_event, eventCards );
        listView.setAdapter(adapter);
        return rootView;
    }



}