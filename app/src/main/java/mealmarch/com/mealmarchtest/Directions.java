package mealmarch.com.mealmarchtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.routing.RouteManager;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;

import java.lang.ref.WeakReference;
import java.util.List;


public class Directions extends AppCompatActivity {

    // Declare the rm variable (the RouteManager)
    RouteManager rm = new RouteManager();
    RoutePlan routePlan = new RoutePlan();
    //routePlan.addWaypoint(new GeoCoordinate(49.1966286, -123.0053635));
    //routePlan.addWaypoint(new GeoCoordinate(49.1947289, -123.1762924));
    //RouteOptions routeOptions = new RouteOptions();
    //routeOptions.setTransportMode(RouteOptions.TransportMode.PEDESTRIAN);
    //routeOptions.setRouteType(RouteOptions.Type.FASTEST);
    //routePlan.setRouteOptions(routeOptions);

    private class RouteListener implements RouteManager.Listener {

        // Method defined in Listener
        public void onProgress(int percentage) {
            // Display a message indicating calculation progress
        }

        // Method defined in Listener
        @Override
        public void onCalculateRouteFinished(RouteManager.Error error, List<RouteResult> routeResult) {
            // If the route was calculated successfully
            if (error == RouteManager.Error.NONE) {
                // Render the route on the map
                MapRoute mapRoute = new MapRoute(routeResult.get(0).getRoute());
                final MapFragment mapFragment = (MapFragment)
                        getFragmentManager().findFragmentById(R.id.mapfragment);
                Map map = mapFragment.getMap();
                map.addMapObject(mapRoute);
            } else {
                // Display a message indicating route calculation failure
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
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
                    Map map = mapFragment.getMap();
                    // ...
                } else {
                    System.out.println("ERROR: Cannot initialize MapFragment");
                }
            }
        });
        // now the map is ready to be used
        Map map = mapFragment.getMap();

        // Set the map center to Vancouver, Canada.
        map.setCenter(new GeoCoordinate(49.196261,
                -123.004773), Map.Animation.NONE);

        rm.calculateRoute(routePlan, new RouteListener());
        PositioningManager.getInstance().addListener(
                new WeakReference<>(positionListener));
        map.getPositionIndicator().setVisible(true);
    }

    private PositioningManager.OnPositionChangedListener positionListener = new
            PositioningManager.OnPositionChangedListener() {
                final MapFragment mapFragment = (MapFragment)
                        getFragmentManager().findFragmentById(R.id.mapfragment);
                Map map = mapFragment.getMap();

                @Override
                public void onPositionUpdated(PositioningManager.LocationMethod locationMethod, GeoPosition geoPosition, boolean b) {
                    map.setCenter(geoPosition.getCoordinate(),
                            Map.Animation.NONE);
                }

                @Override
                public void onPositionFixChanged(PositioningManager.LocationMethod locationMethod, PositioningManager.LocationStatus locationStatus) {

                }
            };
}
