package in.askdial.askdialsalescrm.dataposting;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import in.askdial.askdialsalescrm.Adapters.My_CustomerAdapters;
import in.askdial.askdialsalescrm.values.DetailsValue;

/**
 * Created by Admin on 18-Jul-17.
 */

public class ConnectingTask {

    SendingTask sendingData = new SendingTask();
    ReceivingTask receivingData = new ReceivingTask();


    public class LoginData extends AsyncTask<String, String, String> {
        String result = "", Username, Password, Organization;
        DetailsValue details;
        Double longitude, latitude;

        public LoginData(String username, String password, Double lat, Double lon, DetailsValue detail) {
            this.Username = username;
            this.Password = password;
            this.details = detail;
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
                result = sendingData.PostLogin(Username, Password, latitude1, longitude1);
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

            receivingData.LoginDetails(result, details);
        }
    }

    public class AddCoustomerDetails extends AsyncTask<String, String, String> {
        String Name, Email, Mobile, Address, Notes, Notes2, Date, Plan_Id, UserId;
        String results = "";
        DetailsValue detailsValue;

        public AddCoustomerDetails(String User_Id, String c_name, String c_email, String c_mobile, String c_adress, String c_plan_ID, String c_notes, String c_notes2, String c_date, DetailsValue detailsvalue) {
            Name = c_name;
            Email = c_email;
            Mobile = c_mobile;
            Address = c_adress;
            Notes = c_notes;
            Notes2 = c_notes2;
            Date = c_date;
            Plan_Id = c_plan_ID;
            UserId = User_Id;
            this.detailsValue = detailsvalue;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                results = sendingData.CoustomerLogin(UserId, Name, Mobile, Email, Address, Plan_Id, Notes, Notes2, Date);

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("debug", "Result: " + results);
            return results;
        }

        @Override
        protected void onPostExecute(String results) {
            receivingData.RegisterDetails(results, detailsValue);
        }
    }

    public class MyCustomer extends AsyncTask<String, String, String> {
        ArrayList<DetailsValue> arrayList;
        My_CustomerAdapters adapters;
        DetailsValue detailsValue;
        Context context;
        String Organization_ID, result = "";

        public MyCustomer(ArrayList<DetailsValue> arrayList, My_CustomerAdapters adapters, DetailsValue detailsValue, Context context,
                          String user_ID) {
            this.arrayList = arrayList;
            this.adapters = adapters;
            this.detailsValue = detailsValue;
            Organization_ID = user_ID;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                result = sendingData.RequestCustomerDetails(Organization_ID);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("debug", result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.RecivedCustomerDetails(result, detailsValue, arrayList, adapters);
        }
    }

    /*//Location tracking using longitude and Latitude
    public class GPSData extends AsyncTask<String, String, String> {
        String result = "", UserID, Password, Organization;
        DetailsValue details;
        Double longitude, latitude;

        public GPSData(String userID,Double lat,Double lon,DetailsValue detail) {
            this.UserID = userID;
            this.details = detail;
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
                String latitude1= Double.toString(latitude);
                String longitude1= Double.toString(longitude);
                result = sendingData.PostLocation(UserID,latitude1,longitude1);
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
    }*/
}
