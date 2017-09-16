package in.askdial.askdialsalescrm.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import in.askdial.askdialsalescrm.R;
import in.askdial.askdialsalescrm.values.GPSTracker;

public class MyLocation extends AppCompatActivity {
    Double lon;
    Double lat;
    TextView txtlon,txtlat;
    // GPSTracker class
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);

        Button btnL = (Button) findViewById(R.id.btn_location);
         txtlon = (TextView) findViewById(R.id.textView_lon);
         txtlat = (TextView) findViewById(R.id.textView_lat);
        // show location button click event
        btnL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(MyLocation.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });

    }
}
