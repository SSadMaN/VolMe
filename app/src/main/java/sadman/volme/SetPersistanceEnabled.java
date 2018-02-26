package sadman.volme;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sadman on 27/02/18.
 */

public class SetPersistanceEnabled extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
    /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
