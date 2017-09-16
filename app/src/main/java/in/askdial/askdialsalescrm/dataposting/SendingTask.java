package in.askdial.askdialsalescrm.dataposting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import in.askdial.askdialsalescrm.values.FunctionCalls;

/**
 * Created by Admin on 18-Jul-17.
 */

public class SendingTask {
    String BASE_URL = DataAPI.BASE_URL;
    FunctionCalls functionCalls = new FunctionCalls();

    public String PostLogin(String Username, String Password, String lat, String lon) {
        String responsestr = "";
        HashMap<String, String> datamap = new HashMap<>();
        datamap.put("user_mobile_email", Username);
        datamap.put("user_password", Password);
        datamap.put("user_latitude", lat);
        datamap.put("user_longitude", lon);
        try {
            responsestr = UrlPostConnection("Check_login", datamap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responsestr;
    }

    public String CoustomerLogin(String User_ID, String Name, String Mobile, String Email, String Address, String Plan_Id, String Notes, String Notes2, String Date) {
        String responsestr = "";
        // HashMap<String, String> datamap = new HashMap<>();
        HashMap<String, String> datamap = new HashMap<>();
        try {
            datamap.put("customer_name", Name);
            datamap.put("customer_mobile", Mobile);
            datamap.put("customer_email", Email);
            datamap.put("customer_address", Address);
            datamap.put("notes", Notes);
            datamap.put("notes2", Notes2);
            datamap.put("date", Date);
            datamap.put("membership_plan_id", Plan_Id);
            datamap.put("user_id", User_ID);
            responsestr = UrlPostConnection("Add_customer_details", datamap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responsestr;
    }

    public String RequestCustomerDetails(String Organization_id) {
        String response = "";
        HashMap<String, String> datamap = new HashMap<>();
        datamap.put("user_id", Organization_id);
        try {
            response = UrlPostConnection("My_customer_details", datamap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private String UrlPostConnection(String Post_Url, HashMap<String, String> datamap) throws IOException {
        String response = "";
        functionCalls.LogStatus("Connecting URL: " + BASE_URL + Post_Url);
        URL url = new URL(BASE_URL + Post_Url);
        functionCalls.LogStatus("URL Connection 1");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        functionCalls.LogStatus("URL Connection 2");
        conn.setReadTimeout(15000);
        functionCalls.LogStatus("URL Connection 3");
        conn.setConnectTimeout(15000);
        functionCalls.LogStatus("URL Connection 4");
        conn.setRequestMethod("POST");
        functionCalls.LogStatus("URL Connection 5");
        conn.setDoInput(true);
        functionCalls.LogStatus("URL Connection 6");
        conn.setDoOutput(true);
        functionCalls.LogStatus("URL Connection 7");

        OutputStream os = conn.getOutputStream();
        functionCalls.LogStatus("URL Connection 8");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        functionCalls.LogStatus("URL Connection 9");
        writer.write(getPostDataString(datamap));
        functionCalls.LogStatus("URL Connection 10");
        writer.flush();
        functionCalls.LogStatus("URL Connection 11");
        writer.close();
        functionCalls.LogStatus("URL Connection 12");
        os.close();
        functionCalls.LogStatus("URL Connection 13");
        int responseCode = conn.getResponseCode();
        functionCalls.LogStatus("URL Connection 14");
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            functionCalls.LogStatus("URL Connection 15");
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            functionCalls.LogStatus("URL Connection 16");
            while ((line = br.readLine()) != null) {
                response += line;
            }
            functionCalls.LogStatus("URL Connection 17");
        } else {
            response = "";
            functionCalls.LogStatus("URL Connection 18");
        }
        functionCalls.LogStatus("URL Connection Response: " + response);
        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            functionCalls.LogStatus(result.toString());
        }

        return result.toString();
    }

    private String UrlGetConnection(String Get_Url) throws IOException {
        String response = "";
        functionCalls.LogStatus("Connecting URL: " + BASE_URL + Get_Url);
        URL url = new URL(BASE_URL + Get_Url);
        functionCalls.LogStatus("URL Get Connection 1");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        functionCalls.LogStatus("URL Get Connection 2");
        conn.setReadTimeout(15000);
        functionCalls.LogStatus("URL Get Connection 3");
        conn.setConnectTimeout(15000);
        functionCalls.LogStatus("URL Get Connection 4");
        int responseCode = conn.getResponseCode();
        functionCalls.LogStatus("URL Get Connection 5");
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            functionCalls.LogStatus("URL Get Connection 6");
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            functionCalls.LogStatus("URL Get Connection 7");
            while ((line = br.readLine()) != null) {
                response += line;
            }
            functionCalls.LogStatus("URL Get Connection 8");
        } else {
            response = "";
            functionCalls.LogStatus("URL Get Connection 9");
        }
        functionCalls.LogStatus("URL Get Connection Response: " + response);
        return response;
    }

    //Send GPS location

    public String PostLocation(String UserID, String lat, String lon) {
        String responsestr = "";
        HashMap<String, String> datamap = new HashMap<>();
        datamap.put("user_id", UserID);
        datamap.put("user_latitude", lat);
        datamap.put("user_longitude", lon);
        try {
            responsestr = UrlPostConnection("Update_location", datamap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responsestr;
    }

}
