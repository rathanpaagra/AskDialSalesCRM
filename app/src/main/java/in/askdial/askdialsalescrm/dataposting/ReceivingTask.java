package in.askdial.askdialsalescrm.dataposting;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.askdial.askdialsalescrm.Adapters.My_CustomerAdapters;
import in.askdial.askdialsalescrm.values.DetailsValue;

/**
 * Created by Admin on 18-Jul-17.
 */

public class ReceivingTask {


    public void LoginDetails(String result, DetailsValue details) {
        Log.d("debug", result);
        try {
            JSONObject jo = new JSONObject(result);
            if (jo != null) {
                String Response = jo.getString("message");
                if (Response.equals("Success")) {
                    //details= new DetailsValue();
                    String GuardID = jo.getString("user_name");
                    details.setUserName(GuardID);
                    String ID = jo.getString("user_id");
                    details.setUserID(ID);
                    details.setLoginSuccess(true);

                } else if (Response.equals("Failure")) {
                    details.setLognFailure(true);
                } /*else if (Response.equals("Account Blocked")) {
                    details.setAccountblocked(true);
                } else {
                    details.setLoginExist(true);
                }*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void RegisterDetails(String result, DetailsValue details) {
        try {
            JSONObject jo = new JSONObject(result);
            if (jo != null) {
                String Status = jo.getString("message");
                if (Status.equals("Success")) {
                    details.setC_DetailsSentSuccess(true);

                } else {
                    details.setC_DetailsSentFailure(true);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*  public void RecivedCustomerDetails(String result, DetailsValue details,ArrayList<DetailsValue> arrayList,
                                         My_CustomerAdapters adapters) {
          Log.d("debug", result);
          try {
              JSONObject jo = new JSONObject(result);
              if (jo != null) {
                  String Status = jo.getString("message");
                  if (Status.equals("Success")) {
                      details.setC_DetailsReceivedSuccess(true);
                      details=new DetailsValue();
                      String Fname = jo.getString("customer_name");
                      details.setC_Name(Fname);
                      String Mobile = jo.getString("customer_mobile");
                      details.setC_Mobile(Mobile);
                      String Email = jo.getString("customer_email");
                      details.setC_Email(Email);
                      String Adrr = jo.getString("customer_address");
                      details.setC_Addres(Adrr);
                      String Notes = jo.getString("notes");
                      details.setC_Notes(Notes);
                      String plan_id = jo.getString("membership_plan");
                      details.setC_PlanID(plan_id);
                      String price=jo.getString("price");
                      details.setC_Price(price);
                      String cust_added_date=jo.getString("added_on");
                      details.setC_AddedOn_Date(cust_added_date);
                      arrayList.add(details);
                      adapters.notifyDataSetChanged();

                  } else {
                      details.setC_DetailsReceivedFailure(true);
                  }

              }
          } catch (JSONException e) {
              e.printStackTrace();
          }
      }
  */
    public void RecivedCustomerDetails(String result, DetailsValue details, ArrayList<DetailsValue> arrayList,
                                       My_CustomerAdapters adapters) {
        try {
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                if (jo != null) {
                    String Status = jo.getString("message");
                    if (Status.equals("Success")) {
                        details.setC_DetailsReceivedSuccess(true);
                        details = new DetailsValue();
                        details.setC_Name(jo.getString("customer_name"));
                        details.setC_Mobile(jo.getString("customer_mobile"));
                        details.setC_Email(jo.getString("customer_email"));
                        details.setC_Addres(jo.getString("customer_address"));
                        details.setC_Notes(jo.getString("notes"));
                        details.setC_Notes2(jo.getString("notes2"));
                        details.setC_Date(jo.getString("date"));
                        details.setC_PlanID(jo.getString("membership_plan"));
                        details.setC_Price(jo.getString("price"));
                        details.setC_AddedOn_Date(jo.getString("added_on"));
                        arrayList.add(details);
                        adapters.notifyDataSetChanged();
                    } else {
                        details.setC_DetailsReceivedFailure(true);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void ReceiveLocationrDetails(String result, DetailsValue details) {
        try {
            JSONObject jo = new JSONObject(result);
            if (jo != null) {
                String Status = jo.getString("message");
                details= new DetailsValue();
                if (Status.equals("Success")) {
                    details.setC_LocationSentSuccess(true);

                } else {
                    details.setC_LocationSentFailure(true);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
