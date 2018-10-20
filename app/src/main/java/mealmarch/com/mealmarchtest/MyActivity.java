package mealmarch.com.mealmarchtest;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

        import java.net.URL;

public class MyActivity extends AppCompatActivity {

    private static String APIKey = "AIzaSyDsc00rP6Gf0HLpoVmz_onQMynA_Z5ErmA";
    private static String searchString = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?location=41.981950, -91.663183&radius=2000&type=restaurant&key="+APIKey;

    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        try{
            URL url = new URL(searchString);
        }
        catch(Exception e){

        }
    }
}
