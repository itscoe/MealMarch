package mealmarch.com.mealmarchtest;

import android.content.Intent;
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
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class MyActivity extends AppCompatActivity {

    private static String APIKey = "AIzaSyDqDkgbIeELCnh6ek8QCm-ROu_rd1SkydU";
    Button searchBtn, hardBtn, easyBtn;
    TextView result;
    private String searchResult, toReturn, searchUrl;
    private String[][] places;
    private int selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        places = new String[20][3];
        searchBtn  = (Button)findViewById(R.id.searchFoodBtn);
        easyBtn = (Button)findViewById(R.id.directBtn);
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyBtn(v);
            }
        });
        hardBtn = (Button)findViewById(R.id.hotColdbtn);
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
        try{

            //searchResult = toReturn;
            //result.setText(searchResult);
            searchResult = findFood(searchUrl);
            String[] lines = searchResult.split("location\":", 0);
            result.setText((lines[0]));
            if(searchResult != null) {
            }
            else{
                result.setText("Null pointer wow.");
            }
            //String[] lining = (result.getText()).toString().split("\n", 0);
            //result.setText(lines.length);
            for (int i = 1; i < lines.length && i < 21; i++){
                //result.setText(lines[i]);
                int idx1, idx2, fromIdx;
                String loc, lat, lng, name, foodType, rating;
                idx1 = lines[i].indexOf(':');
                idx2 = lines[i].indexOf(',');
                lat = lines[i].substring(idx1+1, idx2);
                fromIdx = idx2;
                idx1 = lines[i].indexOf(':', fromIdx);
                idx2 = lines[i].indexOf('}', idx1);
                lng = lines[i].substring(idx1+1,idx2);
                loc = lat+","+lng;
                String sub1, sub2;
                String[] splitIt = lines[i].split("\"name\":\"");
                sub1 = splitIt[1];
                idx1 = sub1.indexOf('\"');
                name = sub1.substring(0, idx1);
                String[] splitAgain = lines[i].split("\"rating\":");
                sub2 = splitAgain[1];
                rating = sub2.substring(0,3);
                places[i-1][0] = loc;
                places[i-1][1] = name;
                places[i-1][2] = rating;
            }
            Random rand = new Random();
            int min = lines.length;
            if (lines.length > 20){
                min = 20;
            }
            selection = rand.nextInt(min);
            result.setText(places[selection][1]);
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

        return toReturn;
    }

    private void easyBtn(View v){
        Intent i = new Intent(this, Directions.class);
        i.putExtra(places[selection][0], "loc");
        i.putExtra(places[selection][1], "name");
        i.putExtra(places[selection][2], "rating");
        //startActivity(i);
    }
}