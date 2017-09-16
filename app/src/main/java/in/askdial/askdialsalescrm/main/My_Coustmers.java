package in.askdial.askdialsalescrm.main;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import in.askdial.askdialsalescrm.Adapters.My_CustomerAdapters;
import in.askdial.askdialsalescrm.R;
import in.askdial.askdialsalescrm.dataposting.ConnectingTask;
import in.askdial.askdialsalescrm.values.DetailsValue;
import in.askdial.askdialsalescrm.values.FunctionCalls;

public class My_Coustmers extends AppCompatActivity implements View.OnClickListener{
    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    static ProgressDialog dialog = null;
    Thread mythread;
    ConnectingTask task;
    DetailsValue detailsvalue;
    FunctionCalls functionCalls;
    String user_ID = "", user_Name = "";
    RecyclerView my_Customer_Details;
    ArrayList<DetailsValue> CustomerList;
    My_CustomerAdapters my_CustomerAdapters;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__coustmers);

        detailsvalue = new DetailsValue();
        task = new ConnectingTask();
        functionCalls = new FunctionCalls();
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();

        user_ID = settings.getString("UserID", "");
        user_Name = settings.getString("UserName", "");

        //region RecyclerView with Adapter
        my_Customer_Details = (RecyclerView) findViewById(R.id.recyclerView_my_customer);
        CustomerList = new ArrayList<DetailsValue>();
        my_CustomerAdapters = new My_CustomerAdapters(My_Coustmers.this, CustomerList, user_ID);
        my_Customer_Details.setHasFixedSize(true);
        my_Customer_Details.setLayoutManager(layoutManager);
        my_Customer_Details.setLayoutManager(new LinearLayoutManager(My_Coustmers.this));
        my_Customer_Details.setAdapter(my_CustomerAdapters);
        //endregion


        ConnectingTask.MyCustomer checkAppointments = task.new MyCustomer(CustomerList, my_CustomerAdapters,
                detailsvalue, My_Coustmers.this, user_ID);
        checkAppointments.execute();
        dialog = ProgressDialog.show(My_Coustmers.this, "", "Searching for a MyCustomer Details..", true);
        dialog.setCancelable(true);
        mythread = null;
        Runnable runnable = new MyCustomerTimer();
        mythread = new Thread(runnable);
        mythread.start();
    }

    @Override
    public void onClick(View view) {

    }

    class MyCustomerTimer implements Runnable {

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
                    if (detailsvalue.isC_DetailsReceivedSuccess()) {
                        detailsvalue.setC_DetailsReceivedSuccess(false);
                        dialog.dismiss();
                        Toast.makeText(My_Coustmers.this, "Customer details", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    }
                    if (detailsvalue.isC_DetailsReceivedFailure()) {
                        detailsvalue.setC_DetailsReceivedFailure(false);
                        dialog.dismiss();
                        Toast.makeText(My_Coustmers.this, "Customer details not available", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
