package mealmarch.com.mealmarchtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        final TextView mDistance = findViewById(R.id.Distance_Info);
        final TextView mTime = findViewById(R.id.Time_Info);
        TextView mCalorie = findViewById(R.id.Calorie_Info);
        TextView mThreshold = findViewById(R.id.Threshold_Info);
        Button mShareButton = findViewById(R.id.ShareButton);
        Button mShareHCButton = findViewById(R.id.shareHCButton);

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
}
