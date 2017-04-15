package hackoverflow.sleepstats;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EffectsActivity extends AppCompatActivity {

    SQLiteDatabase db;
    private static final int MAX_SLEEP = 13;
    private static final int MIN_SLEEP = 2;
    /**
     * FUNCTION: onCreate
     * Overrides the onCreate method to initialize the database
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);
        openOrCreateDatabase("sleepDB", MODE_PRIVATE, null);
        System.out.println("Opening up new database");

        //initializes database
        db = openOrCreateDatabase("My Cities", MODE_PRIVATE, null);
        Cursor tablesCursor = db.rawQuery(
                "SELECT * FROM sqlite_master WHERE type='table' AND name='effects';",
                null);
        if (tablesCursor.getCount() == 0){
            setUpDatabase();
        }
        System.out.println("Initializing database");

        //retrieves and interprets hours slept
        Intent intent = getIntent();
        double totalTimeVal = intent.getDoubleExtra("totalTimeVal", 0.0);

        //updates activity based on totalTimeVal
        updateActivity(totalTimeVal);
    }

    /**
     * FUNCTION: updateActivity
     * Private helper function that updates the activity by parsing bracket given by passed in
     * value and adding relevant information from database
     */
    private void updateActivity (double totalTimeVal){
        //rounds totalTimeVal to nearest bracket
        int roundedTimeVal = (int) Math.round(totalTimeVal);
        if (roundedTimeVal > MAX_SLEEP) roundedTimeVal = MAX_SLEEP;
        if (roundedTimeVal < MIN_SLEEP) roundedTimeVal = MIN_SLEEP;

        //retrieves information from database
        String overviewStr;
        String symptomStr;
        String adviceStr;

        String cmd = "SELECT * FROM effects WHERE bracket = ?;";

        Cursor cur = db.rawQuery(cmd, new String[]{Integer.toString(roundedTimeVal)});
        overviewStr = cur.getString(cur.getColumnIndex("overview"));
        symptomStr = cur.getString(cur.getColumnIndex("symptom"));
        adviceStr = cur.getString(cur.getColumnIndex("advice"));

        //adds information to view
        //TODO: update using textview ids.
    }

    /**
     * Helper function used one time to initialize the database tables
     */
    private void setUpDatabase(){
        //creates table
        String setupStr = "CREATE TABLE effects ("
                + "bracket INTEGER, overview TEXT, symptom TEXT, advice TEXT,"
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT"
                + ");";
        db.execSQL(setupStr);

        //initialize values in table
        //TODO: run insert commands here
    }
}
