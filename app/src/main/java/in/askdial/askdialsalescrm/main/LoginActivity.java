package in.askdial.askdialsalescrm.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.askdial.askdialsalescrm.R;
import in.askdial.askdialsalescrm.dataposting.ConnectingTask;
import in.askdial.askdialsalescrm.values.DetailsValue;
import in.askdial.askdialsalescrm.values.FunctionCalls;
import in.askdial.askdialsalescrm.values.GPSTracker;

import static in.askdial.askdialsalescrm.R.id.btn_login;
import static in.askdial.askdialsalescrm.R.id.edt_password;

public class LoginActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    EditText email, password;
    Button Login;
    String EmailMob, Pass;
    FunctionCalls functionCalls;
    DetailsValue details;
    ConnectingTask task;
    static ProgressDialog dialog = null;
    Thread mythread;
    // GPSTracker class
    GPSTracker gps;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_login);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        functionCalls = new FunctionCalls();
        task = new ConnectingTask();
        details = new DetailsValue();

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();
        editor.apply();

        email = (EditText) findViewById(R.id.edt_email);
        password = (EditText) findViewById(edt_password);
        Login = (Button) findViewById(btn_login);

       /* Intent service = new Intent(LoginActivity.this, GPSTracker.class);
        startService(service);*/

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    // create class object
                    if (turnGPSOn()) {
                        gps = new GPSTracker(LoginActivity.this);

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
                        }
                        Logindetails();
                    } else {
                        Toast.makeText(LoginActivity.this, "Please Turn On GPS", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create class object
                if (turnGPSOn()) {
                    gps = new GPSTracker(LoginActivity.this);
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
                    }
                    Logindetails();
                } else {
                    Toast.makeText(LoginActivity.this, "Please Turn On GPS", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean turnGPSOn() {
      /*  Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        sendBroadcast(intent);*/
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Check if enabled and if not send user to the GPS settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);

        }
        return enabled;
    }

    public void Logindetails() {
        if (functionCalls.isInternetOn(LoginActivity.this)) {
            EmailMob = email.getText().toString();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (!EmailMob.equals("") && EmailMob.matches(emailPattern)) {
                Pass = password.getText().toString();
                if (!Pass.equals("") && Pass.length() >= 4) {
                    ConnectingTask.LoginData login = task.new LoginData(EmailMob, Pass, latitude, longitude, details);
                    login.execute();
                    dialog = ProgressDialog.show(LoginActivity.this, "", "Logging In please wait..", true);
                    dialog.setCancelable(true);
                    mythread = null;
                    Runnable runnable = new LoginTimer();
                    mythread = new Thread(runnable);
                    mythread.start();
                } else {
                    password.setError("Please Enter Correct Password");
                }
            } else {
                email.setError("Please Enter Email/Mobile");
            }
        } else {
            Toast.makeText(LoginActivity.this, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
        }

    }

    class LoginTimer implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (details.isLoginSuccess()) {
                        details.setLoginSuccess(false);
                        mythread.interrupt();
                        String User_ID = details.getUserID();
                        functionCalls.LogStatus("ID: " + User_ID);
                        // functionCalls.showToast(LoginActivity.this, "ID: " + User_ID);
                        Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                        String User_Name = details.getUserName();
                        functionCalls.LogStatus("SecurityID: " + User_Name);
                        editor.putString("UserID", User_ID);
                        editor.putString("UserName", User_Name);
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (details.isLognFailure()) {
                        details.setLognFailure(false);
                        mythread.interrupt();
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login / Password incorrect", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
