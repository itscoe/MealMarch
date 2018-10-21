package mealmarch.com.mealmarchtest;

import com.opencsv.CSVWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class HeatMap extends AppCompatActivity {

    // Declare the rm variable (the RouteManager)
    GeoCoordinate restaurant;
    double initialDistance;
    double previousDistance;
    boolean gotInitalLocation = false;
    double distanceLeft;
    //Declare the timer
    Timer myTimer = new Timer();





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map);
        // Search for the Map Fragment
        final MapFragment mapFragment = (MapFragment)
                getFragmentManager().findFragmentById(R.id.mapfragment);
        // initialize the Map Fragment and
        // retrieve the map that is associated to the fragment
        mapFragment.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(
                    OnEngineInitListener.Error error) {
                if (error == OnEngineInitListener.Error.NONE) {
                    PositioningManager.getInstance().addListener(
                            new WeakReference<>(positionListener));
                } else {
                    System.out.println("ERROR: Cannot initialize MapFragment");
                }
            }
        });
        this.restaurant = restaurant;
        //Set the schedule function and rate
        myTimer.scheduleAtFixedRate(new TimerTask() {
                                        @Override
                                        public void run() {
                                            //Called at every 1000 milliseconds (1 second)
                                            Log.i("MainActivity", "Repeated task");
                                        }
                                    },
                //set the amount of time in milliseconds before first execution
                0,
                //Set the amount of time between each execution (in milliseconds)
                1000);
    }

    private PositioningManager.OnPositionChangedListener positionListener = new
            PositioningManager.OnPositionChangedListener() {
                @Override
                public void onPositionUpdated(PositioningManager.LocationMethod locationMethod, GeoPosition geoPosition, boolean b) {
                    if(!gotInitalLocation){
                        initialDistance = geoPosition.getCoordinate().distanceTo(restaurant);
                        previousDistance = initialDistance;
                    } else previousDistance = distanceLeft;
                    distanceLeft = geoPosition.getCoordinate().distanceTo(restaurant);
                    TextView Change = findViewById(R.id.textView4);
                    TextView Distance = findViewById(R.id.textView5);
                    Distance.setText(String.format(Double.toString(distanceLeft), "f.ff"));
                    if(distanceLeft < previousDistance){
                        Change.setText("Getting Warmer");
                    } else if(distanceLeft > previousDistance){
                        Change.setText("Getting Colder");
                    } else {
                        Change.setText(" ");
                    }

                }

                @Override
                public void onPositionFixChanged(PositioningManager.LocationMethod locationMethod, PositioningManager.LocationStatus locationStatus) {

                }
            };
}

