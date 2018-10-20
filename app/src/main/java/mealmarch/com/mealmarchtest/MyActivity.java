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

import javax.net.ssl.HttpsURLConnection;

public class MyActivity extends AppCompatActivity {

    private static String APIKey = "AIzaSyDqDkgbIeELCnh6ek8QCm-ROu_rd1SkydU";
    Button searchBtn;
    TextView result;
    private String searchResult;
    private String[][] places;



    private String response;
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


    }

    public void searchFood(View v){
        //function called on button press
        //calls places API and receives location data

        String location, radius;
        //temporary dummy data
        radius = "5000";
        location = "41.981950,-91.663183";
        result.setText("Hello");
        try{



            String searchString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+location+"&radius="+radius+"&type=restaurant&key="+APIKey;
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
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, searchString, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    searchResult = response.toString();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    result.setText("Bad bad bad");
                }
            });

            requestQueue.add(jsonObjectRequest);
            result.setText(searchResult);


        }
        catch(Exception e){
            result.setText(e.toString());
        }
    }


}