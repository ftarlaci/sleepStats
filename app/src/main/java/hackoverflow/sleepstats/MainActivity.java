package hackoverflow.sleepstats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private Date startTime;
    private Date endTime;
    private double totalTime = 0; //initialized to zero, is reset after 'stop' is pushed.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * FUNCTION: effectsButton
     * On-Click button that launches the effects activity
     * @param view
     */
    public void effectsButton(View view) {
        Intent intent = new Intent(this, EffectsActivity.class);
        intent.putExtra("totalTimeVal", totalTime);
        startActivity(intent);
    }

    /**
     * FUNCTION: startButton
     * Retrieves the current time from an instance of the calendar and adds it to the screen view
     * @param view
     */
    public void startButton(View view) {
        Calendar c = Calendar.getInstance();
        startTime = c.getTime();
    }

    /**
     * FUNCTION: stopButton
     * OnClick function that retrieves the current time from an instance of the calendar and updates
     * the main activity with this info
     * @param view
     */
    public void stopButton(View view) {
        Calendar c = Calendar.getInstance();
        endTime = c.getTime();
        totalTime = parseTotalTime();
    }

    private double parseTotalTime(){

        long different = endTime.getTime() - startTime.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        double totalTime = elapsedHours + (elapsedMinutes/60.0);

        return totalTime;
    }
}
