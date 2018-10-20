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
    GeoCoordinate restaurant;
    boolean hasRoute = false;

    Directions(GeoCoordinate restaurant){
        this.restaurant = restaurant;
        RouteOptions routeOptions = new RouteOptions();
        routeOptions.setTransportMode(RouteOptions.TransportMode.PEDESTRIAN);
        routeOptions.setRouteType(RouteOptions.Type.FASTEST);
        routePlan.setRouteOptions(routeOptions);
    }

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
                System.out.println("Could not find route");
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
                    if(!hasRoute) {
                        routePlan.addWaypoint(geoPosition.getCoordinate());
                        routePlan.addWaypoint(restaurant);
                        rm.calculateRoute(routePlan, new RouteListener());
                        hasRoute = true;
                    }
                }

                @Override
                public void onPositionFixChanged(PositioningManager.LocationMethod locationMethod, PositioningManager.LocationStatus locationStatus) {

                }
            };
}
