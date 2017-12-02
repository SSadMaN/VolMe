package sadman.volme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;



public class home extends AppCompatActivity {

    private ListView mEventsListView ;
    private EventcardsAdapter mEventcardsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Initialize references to views
        mEventsListView = (ListView) findViewById(R.id.event_card_list);


        final List<EventCard> eventCards = new ArrayList<>();
        mEventcardsAdapter = new EventcardsAdapter(this, R.layout.item_event, eventCards);
        mEventsListView.setAdapter(mEventcardsAdapter);
    }
}
