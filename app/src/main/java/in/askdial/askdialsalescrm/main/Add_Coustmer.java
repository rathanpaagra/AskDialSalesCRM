package in.askdial.askdialsalescrm.main;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import in.askdial.askdialsalescrm.R;
import in.askdial.askdialsalescrm.dataposting.ConnectingTask;
import in.askdial.askdialsalescrm.values.DetailsValue;
import in.askdial.askdialsalescrm.values.FunctionCalls;

public class Add_Coustmer extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    String[] packages;
    ArrayAdapter<String> adapterPackage;
    private Spinner spinner;
    String c_plan_ID;
    String user_ID = "", user_Name = "", c_name, c_mobile, c_email, c_adress, c_notes, c_notes2, c_leadDate, str_package;
    EditText editText_Coustmer_Name, editText_Email, editText_Mobile, editText_Address, editText_Notes, editText_Notes2;
    TextView txtV_date_display;
    Button submit, btn_Date;
    private int mYear, mDay, mMonth;
    static ProgressDialog dialog = null;
    Thread mythread;
    ConnectingTask task;
    DetailsValue detailsvalue;
    FunctionCalls functionCalls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__coustmer);

        detailsvalue = new DetailsValue();
        task = new ConnectingTask();
        functionCalls = new FunctionCalls();
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();

        user_ID = settings.getString("UserID", "");
        user_Name = settings.getString("UserName", "");

        editText_Coustmer_Name = (EditText) findViewById(R.id.edt_coustmer_name);
        editText_Email = (EditText) findViewById(R.id.edt_email);
        editText_Mobile = (EditText) findViewById(R.id.edt_mobile);
        editText_Address = (EditText) findViewById(R.id.edt_address);
        editText_Notes = (EditText) findViewById(R.id.edt_notes);
        editText_Notes2 = (EditText) findViewById(R.id.edt_notes2);
        txtV_date_display = (TextView) findViewById(R.id.txt_date);
        btn_Date = (Button) findViewById(R.id.btn_leading_date);
        submit = (Button) findViewById(R.id.btn_submit);

        spinner = (Spinner) findViewById(R.id.spinner);
        packages = getResources().getStringArray(R.array.Packages);

        adapterPackage = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, packages);
        adapterPackage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterPackage);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_package = spinner.getSelectedItem().toString();
                if (str_package.equals("Select Packages")) {
                    c_plan_ID = "0";
                    // Toast.makeText(Add_Coustmer.this, "your Plan: "+c_plan_ID, Toast.LENGTH_SHORT).show();
                } else if (str_package.equals("Starter")) {
                    c_plan_ID = "1";
                    Toast.makeText(Add_Coustmer.this, "Starter Plan Selected", Toast.LENGTH_SHORT).show();
                } else if (str_package.equals("Premium")) {
                    c_plan_ID = "2";
                    Toast.makeText(Add_Coustmer.this, "Premium Plan Selected", Toast.LENGTH_SHORT).show();
                } else if (str_package.equals("Corporate")) {
                    c_plan_ID = "3";
                    Toast.makeText(Add_Coustmer.this, "Corporate Plan Selected", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Coustmer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String strMonth = String.valueOf(month), strDay = String.valueOf(day), strYear = String.valueOf(year);

                        if (month < 10) {
                            strMonth = "0" + strMonth;
                        }

                        if (day < 10) {
                            strDay = "0" + strDay;
                        }

                        // Handle the data here
                        txtV_date_display.setText(strDay + " - " + strMonth + " - " + strYear);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (functionCalls.isInternetOn(Add_Coustmer.this)) {
                    coustomerDetails();
                } else {
                    Toast.makeText(Add_Coustmer.this, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void coustomerDetails() {
        c_name = editText_Coustmer_Name.getText().toString();
        if (!editText_Coustmer_Name.getText().toString().equals("")) {
            if (c_plan_ID.equals("1") || c_plan_ID.equals("2") || c_plan_ID.equals("3")) {
                c_email = editText_Email.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (!editText_Email.getText().toString().equals("") && c_email.matches(emailPattern)) {
                    c_mobile = editText_Mobile.getText().toString();
                    if (!editText_Mobile.getText().toString().equals("") && c_mobile.length() >= 10) {
                        c_adress = editText_Address.getText().toString();
                        if (!editText_Address.getText().toString().equals("") && c_adress.length() > 5) {
                            c_notes = editText_Notes.getText().toString();
                            if (!editText_Notes.getText().toString().equals("") && c_notes.length() > 5) {
                                c_notes2 = editText_Notes2.getText().toString();
                                if (!editText_Notes2.getText().toString().equals("") && c_notes.length() > 5) {
                                    c_leadDate = txtV_date_display.getText().toString();
                                    if (!txtV_date_display.getText().toString().equals("")) {
                                        // if (c_plan_ID.equals("0")) {
                                        // if (((c_plan_ID != "1") || (c_plan_ID != "2") || (c_plan_ID != "3"))) {
                                        ConnectingTask.AddCoustomerDetails login = task.new AddCoustomerDetails(user_ID, c_name, c_email, c_mobile, c_adress, c_plan_ID, c_notes, c_notes2, c_leadDate, detailsvalue);
                                        login.execute();
                                        dialog = ProgressDialog.show(Add_Coustmer.this, "", "Registering please wait..", true);
                                        dialog.setCancelable(true);
                                        mythread = null;
                                        Runnable runnable = new LoginTimer();
                                        mythread = new Thread(runnable);
                                        mythread.start();
                                    } else {
                                        Toast.makeText(Add_Coustmer.this, "Please select the leading Date", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Add_Coustmer.this, "Enter the lead notes", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Add_Coustmer.this, "Enter the notes", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Add_Coustmer.this, "Enter the Currect Address", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Add_Coustmer.this, "Enter the Currect Mobile No", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Add_Coustmer.this, "Enter the Currect Email", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Add_Coustmer.this, "Please select the Package", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Add_Coustmer.this, "Enter the Full Name", Toast.LENGTH_SHORT).show();
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
                    if (detailsvalue.isC_DetailsSentSuccess()) {
                        detailsvalue.setC_DetailsSentSuccess(false);
                        dialog.dismiss();
                        //Date_Added=detailsvalue.getDate_added();
                        Intent intent = new Intent(Add_Coustmer.this, MainActivity.class);
                        // intent.putExtra("date_added", Date_Added);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Add_Coustmer.this, "Customer Added", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    }
                    if (detailsvalue.isC_DetailsSentFailure()) {
                        detailsvalue.setC_DetailsSentFailure(false);
                        dialog.dismiss();
                        Toast.makeText(Add_Coustmer.this, "Customer details adding failed", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
