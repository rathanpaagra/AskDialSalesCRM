package in.askdial.askdialsalescrm.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import in.askdial.askdialsalescrm.dataposting.ReceivingTask;
import in.askdial.askdialsalescrm.dataposting.SendingTask;
import in.askdial.askdialsalescrm.values.DetailsValue;
import in.askdial.askdialsalescrm.values.GPSTracker;

/**
 * Created by Admin on 29-Jul-16.
 */
public class LocationService extends Service {
    Timer timer = new Timer();
    private final int TIME_INTERVAL = 10000;
    GPSTracker GPSTracker;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 5000; // 2 minute

    //private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 2 minute

    // Declaring a Location Manager
    private LocationManager locationManager;
    GPSTracker gps;
    String user_ID, user_Name, user_ID1 = "2";

    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    SendingTask sendingData = new SendingTask();
    ReceivingTask receivingData = new ReceivingTask();

    //Thread
    Thread locationthread;
    DetailsValue detailsValue;
    Context context;
    boolean GpsStatus;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        GPSTracker = new GPSTracker(LocationService.this);
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();
        detailsValue = new DetailsValue();
        context = getApplicationContext();

        user_ID = settings.getString("UserID", "");
        user_Name = settings.getString("UserName", "");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        doTimerThings();
        return super.onStartCommand(intent, flags, startId);
    }

    private void doTimerThings() {
        timer.scheduleAtFixedRate(new TimerTask() {

            @SuppressLint("DefaultLocale")
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void run() {
                CheckGpsStatus();

                if (GpsStatus == true) {
                    latitude = GPSTracker.getLatitude();
                    longitude = GPSTracker.getLongitude();
                    new GPSData(user_ID, latitude, longitude).execute();

                    // Toast.makeText(LocationService.this, "Please Turn On GPS", Toast.LENGTH_SHORT).show();
                } else {
                    getLocation();
                    latitude = GPSTracker.getLatitude();
                    longitude = GPSTracker.getLongitude();
                    new GPSData(user_ID, latitude, longitude).execute();
                }

                /*if (turnGPSOn()) {
                    // you get the lat and lng , do your server stuff here-----
                    // System.out.println("lat------ "+latitude);
                    // System.out.println("lng-------- "+longitude);
                    // getLocation();
                   *//* gps = new GPSTracker(LocationService.this);
                    // check if GPS enabled
                    if (gps.canGetLocation()) {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();
                        // \n is for new line
                        //  Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    } else {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }*//*
                    latitude = GPSTracker.getLatitude();
                    longitude = GPSTracker.getLongitude();
                    new GPSData(user_ID, latitude, longitude).execute();
                    *//*locationthread = null;
                    Runnable runnable = new StaffData();
                    locationthread = new Thread(runnable);
                    locationthread.start();*//*

                } else {
                    Toast.makeText(LocationService.this, "Please Turn On GPS", Toast.LENGTH_SHORT).show();

                }*/
            }

        }, 10000, TIME_INTERVAL);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private Location getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext()
                    .getSystemService(Context.LOCATION_SERVICE);


            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            //new GPSData(user_ID,latitude,longitude).execute();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        if (ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return location;
                        }
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                //new GPSData(user_ID,latitude,longitude).execute();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    //Location tracking using longitude and Latitude
    public class GPSData extends AsyncTask<String, String, String> {
        String result = "", UserID, Password, Organization;
        DetailsValue details;
        Double longitude, latitude;

        public GPSData(String userID, Double lat, Double lon/*,DetailsValue detail*/) {
            this.UserID = userID;
            //this.details = detail;
            this.latitude = lat;
            this.longitude = lon;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String latitude1 = Double.toString(latitude);
                String longitude1 = Double.toString(longitude);
                result = sendingData.PostLocation(UserID, latitude1, longitude1);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("debug", "Result: " + result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            receivingData.ReceiveLocationrDetails(result, details);
        }
    }

    public void CheckGpsStatus() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}

 /*class StaffData implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Fetchstaff();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                }
            }
        }
    }

    private void Fetchstaff() {
        if (detailsValue.isC_LocationSentSuccess()) {
            detailsValue.setC_LocationSentSuccess(false);
            locationthread.interrupt();

            this.stopSelf();
        }
        if (detailsValue.isC_LocationSentFailure()) {
            detailsValue.setC_LocationSentFailure(false);
            //  turnGPSOn(isGPSEnabled);
            locationthread.interrupt();
            this.stopSelf();
        }
    }

    @Override
    public void onDestroy() {
        if (locationthread.isAlive()) {
            locationthread.interrupt();
            this.stopSelf();
        }
    }*/

/*
    public boolean turnGPSOn() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent1);
        }
        */
/*if (!enabled) {
            Intent callGPSSettingIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(callGPSSettingIntent);
        }*//*

           */
/* AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder
                    .setMessage("GPS is disabled in your device. Enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Enable GPS",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                }
                            });*//*

            */
/*alertDialogBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();*//*


        return enabled;
    }
*/

 /*public boolean turnGPSOn(boolean isGPSEnabled) {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        sendBroadcast(intent);
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Check if enabled and if not send user to the GPS settings
        if (!enabled) {
            Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent1);

        }
        return enabled;
    }*/


