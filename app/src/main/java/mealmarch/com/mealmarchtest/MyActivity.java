package mealmarch.com.mealmarchtest;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.net.URL;

        import javax.net.ssl.HttpsURLConnection;

public class MyActivity extends AppCompatActivity {

    private static String APIKey = "AIzaSyDsc00rP6Gf0HLpoVmz_onQMynA_Z5ErmA";
    Button searchBtn;



    private String response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        searchBtn  = (Button)findViewById(R.id.searchFoodBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            //creates button calling functionality
            @Override
            public void onClick(View v) {
                searchFood(v);
            }
        });


    }

    public void searchFood(View v){
        //function called on button press
        //calls places API and receives location data

        String location, radius;
        //temporary dummy data
        radius = "2000";
        location = "41.981950, -91.663183";

        try{
            String searchString = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?location="+location+"&radius="+radius+"&type=restaurant&key="+APIKey;
            URL url = new URL(searchString);
            HttpsURLConnection connection = (HttpsURLConnection)(url.openConnection());
            //int responseCode = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer responseBuffer = new StringBuffer();
            while ((line = in.readLine()) != null){
                responseBuffer.append(line);
            }
            response = responseBuffer.toString();
        }
        catch(Exception e){
            //some exception handling
        }
    }


}
