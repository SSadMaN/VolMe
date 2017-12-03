package sadman.volme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sadman on 03/12/17.
 */

public class Organizator_main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizator_main);

        ImageView swither_org = (ImageView) findViewById(R.id.switch_button);
        swither_org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent org_mainIntent = new Intent(Organizator_main.this, home.class);
                startActivity(org_mainIntent);
                overridePendingTransition(0, 0);
            }
        });


        final List<EventCard> eventCards = new ArrayList<EventCard>();
        ListView listView = (ListView) findViewById(R.id.jungle);
        EventcardsAdapter adapter = new EventcardsAdapter(this, R.layout.item_event, eventCards );
        listView.setAdapter(adapter);
    }
}
