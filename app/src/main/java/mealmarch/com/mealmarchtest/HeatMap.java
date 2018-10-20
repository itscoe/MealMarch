package mealmarch.com.mealmarchtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import java.lang.ref.WeakReference;

public class HeatMap extends AppCompatActivity {

    // Declare the rm variable (the RouteManager)
    GeoCoordinate restaurant;
    boolean hasRoute = false;

    HeatMap(GeoCoordinate restaurant){
        this.restaurant = restaurant;
    }

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
                    // now the map is ready to be used
                    // now the map is ready to be used
                    Map map = mapFragment.getMap();
                    PositioningManager.getInstance().addListener(
                            new WeakReference<>(positionListener));
                    map.getPositionIndicator().setVisible(true);
                } else {
                    System.out.println("ERROR: Cannot initialize MapFragment");
                }
            }
        });
    }

    private PositioningManager.OnPositionChangedListener positionListener = new
            PositioningManager.OnPositionChangedListener() {
                final MapFragment mapFragment = (MapFragment)
                        getFragmentManager().findFragmentById(R.id.mapfragment);
                Map map = mapFragment.getMap();

                @Override
                public void onPositionUpdated(PositioningManager.LocationMethod locationMethod, GeoPosition geoPosition, boolean b) {
                    map.setCenter(geoPosition.getCoordinate(),
                            Map.Animation.LINEAR);
                }

                @Override
                public void onPositionFixChanged(PositioningManager.LocationMethod locationMethod, PositioningManager.LocationStatus locationStatus) {

                }
            };
}

