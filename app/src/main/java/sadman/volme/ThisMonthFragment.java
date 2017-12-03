package sadman.volme;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ThisMonthFragment extends Fragment {

    public ThisMonthFragment(){
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.eventcard_list, container, false);


        final List<EventCard> eventCards = new ArrayList<>();
        EventcardsAdapter adapter = new EventcardsAdapter(getActivity(), R.layout.item_event, eventCards );
        ListView listView = (ListView) rootView.findViewById(R.id.eventcard_list);
        listView.setAdapter(adapter);
        return rootView;
    }
}