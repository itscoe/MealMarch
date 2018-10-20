package mealmarch.com.mealmarchtest;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import java.net.URL;

public class MyActivity extends AppCompatActivity {

    private static String APIKey = "AIzaSyDsc00rP6Gf0HLpoVmz_onQMynA_Z5ErmA";
    Button searchBtn = (Button)findViewById(R.id.searchFoodBtn);



    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public void searchFood(View v){

        String location, radius;
        location = "41.981950, -91.663183";

        try{
            String searchString = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?location="+location+"&radius=2000&type=restaurant&key="+APIKey;
            URL url = new URL(searchString);
        }
        catch(Exception e){

        }
    }


}
