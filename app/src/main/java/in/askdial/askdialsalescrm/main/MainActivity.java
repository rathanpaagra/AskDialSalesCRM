package in.askdial.askdialsalescrm.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import in.askdial.askdialsalescrm.R;
import in.askdial.askdialsalescrm.service.LocationService;

public class MainActivity extends AppCompatActivity {
    Button add_coustmer, my_cousterms, pricing_plans, my_Location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_coustmer = (Button) findViewById(R.id.Add_Costomer);
        my_cousterms = (Button) findViewById(R.id.My_Costomers);
        pricing_plans = (Button) findViewById(R.id.Pricing_Plans);

        Intent service = new Intent(MainActivity.this, LocationService.class);
        startService(service);

        add_coustmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, Add_Coustmer.class);
                startActivity(intent1);
            }
        });
        my_cousterms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, My_Coustmers.class);
                startActivity(intent1);
            }
        });

        pricing_plans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, Packages.class);
                startActivity(intent1);
            }
        });

        //Find Current Location using latitude and longitude
        //my_Location= (Button) findViewById(R.id.My_Location);
       /* my_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this, MyLocation.class);
                startActivity(intent1);
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
      /*  MenuItem bedMenuItem = menu.findItem(R.id.menu_user_name);
        bedMenuItem.setTitle(User);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean handled = false;
        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                handled = true;
                finish();
                break;
            case R.id.action_setting:
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                handled = true;
                break;
            case R.id.action_quit:
                handled = true;
                MainActivity.this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
