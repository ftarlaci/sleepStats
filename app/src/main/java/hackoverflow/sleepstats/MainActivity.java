package hackoverflow.sleepstats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
}
