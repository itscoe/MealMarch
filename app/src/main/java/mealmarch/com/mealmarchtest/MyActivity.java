package mealmarch.com.mealmarchtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

public class MyActivity extends AppCompatActivity {

    private static String APIKey = "AIzaSyDqDkgbIeELCnh6ek8QCm-ROu_rd1SkydU";
    Button searchBtn;
    TextView result;
    private String searchResult, toReturn, searchUrl;
    private String[][] places;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        places = new String[20][10];
        searchBtn  = (Button)findViewById(R.id.searchFoodBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            //creates button calling functionality
            @Override
            public void onClick(View v) {
                searchFood(v);
            }
        });
        result = (TextView)(findViewById(R.id.pageText));
        String location, radius;
        radius = "5000";
        location = "41.981950,-91.663183";
        searchUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+location+"&radius="+radius+"&type=restaurant&key="+APIKey;
        findFood(searchUrl);



    }

    public void searchFood(View v){
        //function called on button press
        //calls places API and receives location data
        //String location, radius;
        //temporary dummy data
        //radius = "5000";
        //location = "41.981950,-91.663183";
        //result.setText("Hello");
        //searchUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+location+"&radius="+radius+"&type=restaurant&key="+APIKey;


        try{



            //String searchString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+location+"&radius="+radius+"&type=restaurant&key="+APIKey;
//            URL url = new URL(searchString);
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, searchString,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            result.setText("Hello there");
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    result.setText("Bad bad bad");
//                }
//            });
//            int i = 0;
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//            JsonObjectRequest jsonObjectRequest;
//            do{
//                i = i+1;
//            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, searchString, null, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    searchResult = response.toString();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    result.setText("Bad bad bad");
//                }
//
//            });
//            Thread.sleep(500);
//        } while(searchResult == null && i < 10);
//            requestQueue.add(jsonObjectRequest);
//            int i = 0;
//            while(searchResult == null && i < 20){
//                searchResult = findFood( searchString);
//                Thread.sleep(300);
//                TimerTask timer = new TimerTask() {
//                    @Override
//                    public void run() {
//
//                    }
//                }
//                i++;
//            }
            //searchResult = toReturn;
            //result.setText(searchResult);
            searchResult = findFood(searchUrl);
            String[] lines = searchResult.split("location", 0);
            result.setText((lines[0]));
            if(searchResult != null) {
            }
            else{
                result.setText("Null pointer wow.");
            }
            //String[] lining = (result.getText()).toString().split("\n", 0);
            //result.setText(lines.length);

        }
        catch(Exception e){
            result.setText(e.toString());
        }
    }



    private String findFood( String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                toReturn = response.toString();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result.setText("Bad bad bad");
            }

        });
        requestQueue.add(jsonObjectRequest);

        //requestQueue.add(jsonObjectRequest);

        return toReturn;
    }


}