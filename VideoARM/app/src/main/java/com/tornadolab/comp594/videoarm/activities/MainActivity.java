package com.tornadolab.comp594.videoarm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tornadolab.comp594.videoarm.R;
import com.tornadolab.comp594.videoarm.imageTracker.ImageTrackerActivity;
import com.tornadolab.comp594.videoarm.instantTracker.InstantTrackerActivity;

public class MainActivity extends Activity implements View.OnClickListener{

//    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_mode_one).setOnClickListener(this);
        findViewById(R.id.button_mode_three).setOnClickListener(this);
        findViewById(R.id.settings).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_mode_one:
                startActivity(new Intent(MainActivity.this, ImageTrackerActivity.class));
                break;
            case R.id.button_mode_three:
                startActivity(new Intent(MainActivity.this, InstantTrackerActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));

        }
    }
}
