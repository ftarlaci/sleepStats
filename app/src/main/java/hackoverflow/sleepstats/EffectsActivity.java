package hackoverflow.sleepstats;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EffectsActivity extends AppCompatActivity {

    SQLiteDatabase db;

    /**
     * FUNCTION: onCreate
     * Overrides the onCreate method to initialize the database
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);
        openOrCreateDatabase("")
    }
}
