package mealmarch.com.mealmarchtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Results extends AppCompatActivity {
    private String restaurant;
    private double dist;
    private int time;
    private Boolean check;
    Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        restaurant = getIntent().getStringExtra("name");
        String time2 = getIntent().getStringExtra("time");
        time = (Integer.parseInt(time2)/60);
        String distance2 = getIntent().getStringExtra("distance");
        dist = Double.parseDouble(distance2);
        String check2 = getIntent().getStringExtra("check");
        check = Boolean.parseBoolean(check2);

        final TextView mDistance = findViewById(R.id.Distance_Info);
        mDistance.setText(distance2);
        TextView mTDistance = findViewById(R.id.DISTANCE);
        final TextView mTime = findViewById(R.id.Time_Info);
        mTime.setText(time2);
        TextView mTTime = findViewById(R.id.TIME);
        TextView mCalorie = findViewById(R.id.Calorie_Info);
        double cal = dist * 0.06215040397;
        mCalorie.setText(Double.toString(cal));
        TextView mTCalorie = findViewById(R.id.calorie);
        TextView mThreshold = findViewById(R.id.Threshold_Info);
        TextView mTThreshold = findViewById(R.id.Threshold);
        TextView mCoupon = findViewById(R.id.Coupon_Info);
        TextView mTCoupon = findViewById(R.id.Coupon);
        Button mShareButton = findViewById(R.id.ShareButton);
        Button mShareHCButton = findViewById(R.id.shareHCButton);
        finishButton = (Button)findViewById(R.id.Finish);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Finish(v);
            }
        });
        finishButton.setVisibility(View.VISIBLE);

        if (check == false)
        {
            mThreshold.setVisibility(View.GONE);
            mTThreshold.setVisibility(View.GONE);
            mCoupon.setVisibility(View.GONE);
            mTCoupon.setVisibility(View.GONE);
            mShareHCButton.setVisibility(View.GONE);
            mCalorie.setVisibility(View.VISIBLE);
            mTCalorie.setVisibility(View.VISIBLE);

        }

        mShareButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                String shareString = ("I covered " + mDistance.getText().toString() + " in " + mTime.getText().toString() + " minutes on my way to eat at ");
                Intent mSharingIntent = new Intent(Intent.ACTION_SEND);
                mSharingIntent.setType("text/plain");
                mSharingIntent.putExtra(Intent.EXTRA_SUBJECT,  "Write your subject here");
                mSharingIntent.putExtra(Intent.EXTRA_TEXT, shareString);
                startActivity(Intent.createChooser(mSharingIntent, "Share text via"));
            }
        });

        mShareHCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String shareString = ("I challenged the hot and cold mode and covered " + mDistance.getText().toString() + " in " + mTime.getText().toString() + " minutes and got a coupon to eat at ");
                Intent mSharingIntent = new Intent(Intent.ACTION_SEND);
                mSharingIntent.setType("text/plain");
                mSharingIntent.putExtra(Intent.EXTRA_SUBJECT,  "Write your subject here");
                mSharingIntent.putExtra(Intent.EXTRA_TEXT, shareString);
                startActivity(Intent.createChooser(mSharingIntent, "Share text via"));
            }
        });



    }


    private void Finish(View v)
    {
        Intent i = new Intent(this, Feedback.class);
        i.putExtra("name", restaurant);
        startActivity(i);
    }
}
