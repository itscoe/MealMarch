package mealmarch.com.mealmarchtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

public class Feedback extends AppCompatActivity {

    RatingBar ratingBar;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ratingBar = findViewById(R.id.imageBar);
        tv = findViewById(R.id.Rating);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.d("uuu",ratingBar.getRating()+"");
                tv.setText(String.valueOf(ratingBar.getRating()));
            }
        });

    }
}
