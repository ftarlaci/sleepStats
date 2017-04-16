package hackoverflow.sleepstats;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
        System.out.println("Initializing database");
        //rounds totalTimeVal to nearest bracket
        int roundedTimeVal = (int) Math.round(totalTimeVal);
        if (roundedTimeVal > MAX_SLEEP) roundedTimeVal = MAX_SLEEP;
        if (roundedTimeVal < MIN_SLEEP) roundedTimeVal = MIN_SLEEP;

        //retrieves information from database
        String overviewStr;
        String symptomStr;
        String adviceStr;

        System.out.println("Rounded time val is: "+roundedTimeVal);
        String cmd = "SELECT * FROM effects WHERE bracket ="+roundedTimeVal+";";

        Cursor cur = db.rawQuery(cmd, null);

        cur.moveToFirst();
        overviewStr = cur.getString(cur.getColumnIndex("overview"));
        System.out.println(overviewStr);
        symptomStr = cur.getString(cur.getColumnIndex("symptom"));
        adviceStr = cur.getString(cur.getColumnIndex("advice"));

        //adds information to view
        TextView overView = (TextView) findViewById(R.id.overviewField);
        overView.setText(overviewStr);
        TextView symptonView = (TextView) findViewById(R.id.symptomsField);
        symptonView.setText(symptomStr);
        TextView adviceView = (TextView) findViewById(R.id.adviceField);
        adviceView.setText(adviceStr);
    }

    /**
     * Helper function used one time to initialize the database tables
     */
    private void setUpDatabase(){
        //creates table
        String setupStr = "CREATE TABLE IF NOT EXISTS effects ("
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
                System.out.println("query: " + query);
                db.execSQL(query);
                query = "";
            }
        }
    }
}
