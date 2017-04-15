package hackoverflow.sleepstats;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Scanner;

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

        String cmd = "SELECT * FROM effects WHERE bracket = "+roundedTimeVal+";";

        Cursor cur = db.rawQuery(cmd, null);
        overviewStr = cur.getString(1);
        System.out.println("Got overview string")
        symptomStr = cur.getString(2);
        adviceStr = cur.getString(3);

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
        Scanner scan = new Scanner(getResources()
                .openRawResource(R.raw.initialize));
        String query = "";
        while (scan.hasNextLine()) { // build and execute queries
            query += scan.nextLine() + "\n";
            if (query.trim().endsWith(";")) {
                db.execSQL(query);
                query = "";
            }
        }
    }
}
