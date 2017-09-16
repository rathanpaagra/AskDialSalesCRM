package in.askdial.askdialsalescrm.values;

/**
 * Created by Admin on 18-Jul-17.
 */

public class DetailsValue {

    String C_Name;
    String C_Email;
    String C_Addres;
    String C_Notes;
    String C_Notes2;
    String C_Date;

    public String getC_Notes2() {
        return C_Notes2;
    }

    public void setC_Notes2(String c_Notes2) {
        C_Notes2 = c_Notes2;
    }

    public String getC_Date() {
        return C_Date;
    }

    public void setC_Date(String c_Date) {
        C_Date = c_Date;
    }

    String C_Mobile;
    String C_PlanID;
    String C_Price;

    public String getC_Mobile() {
        return C_Mobile;
    }

    public String getC_Price() {
        return C_Price;
    }

    public void setC_Price(String c_Price) {
        C_Price = c_Price;
    }

    public String getC_AddedOn_Date() {
        return C_AddedOn_Date;
    }

    public void setC_AddedOn_Date(String c_AddedOn_Date) {
        C_AddedOn_Date = c_AddedOn_Date;
    }

    String C_AddedOn_Date;


    public String getC_PlanID() {
        return C_PlanID;
    }

    public void setC_PlanID(String c_PlanID) {
        C_PlanID = c_PlanID;
    }


    String UserID,UserName;

    public String getC_Name() {
        return C_Name;
    }

    public void setC_Name(String c_Name) {
        C_Name = c_Name;
    }

    public String getC_Email() {
        return C_Email;
    }

    public void setC_Email(String c_Email) {
        C_Email = c_Email;
    }

    public String getC_Addres() {
        return C_Addres;
    }

    public void setC_Addres(String c_Addres) {
        C_Addres = c_Addres;
    }

    public String getC_Notes() {
        return C_Notes;
    }

    public void setC_Notes(String c_Notes) {
        C_Notes = c_Notes;
    }

    public String getC_Mobile(String mobile) {
        return C_Mobile;
    }

    public void setC_Mobile(String c_Mobile) {
        C_Mobile = c_Mobile;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    private boolean LoginSuccess,LognFailure;
    private boolean c_LocationSentSuccess,c_LocationSentFailure;

    public boolean isC_LocationSentSuccess() {
        return c_LocationSentSuccess;
    }

    public void setC_LocationSentSuccess(boolean c_LocationSentSuccess) {
        this.c_LocationSentSuccess = c_LocationSentSuccess;
    }

    public boolean isC_LocationSentFailure() {
        return c_LocationSentFailure;
    }

    public void setC_LocationSentFailure(boolean c_LocationSentFailure) {
        this.c_LocationSentFailure = c_LocationSentFailure;
    }

    private boolean c_DetailsSentSuccess,c_DetailsSentFailure;

    private boolean c_DetailsReceivedSuccess,isC_DetailsReceivedFailure;

    public boolean isC_DetailsSentSuccess() {
        return c_DetailsSentSuccess;
    }

    public void setC_DetailsSentSuccess(boolean c_DetailsSentSuccess) {
        this.c_DetailsSentSuccess = c_DetailsSentSuccess;
    }

    public boolean isC_DetailsSentFailure() {
        return c_DetailsSentFailure;
    }

    public void setC_DetailsSentFailure(boolean c_DetailsSentFailure) {
        this.c_DetailsSentFailure = c_DetailsSentFailure;
    }

    public boolean isC_DetailsReceivedSuccess() {
        return c_DetailsReceivedSuccess;
    }

    public void setC_DetailsReceivedSuccess(boolean c_DetailsReceivedSuccess) {
        this.c_DetailsReceivedSuccess = c_DetailsReceivedSuccess;
    }

    public boolean isC_DetailsReceivedFailure() {
        return isC_DetailsReceivedFailure;
    }

    public void setC_DetailsReceivedFailure(boolean c_DetailsReceivedFailure) {
        isC_DetailsReceivedFailure = c_DetailsReceivedFailure;
    }

    public boolean isLoginSuccess() {
        return LoginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        LoginSuccess = loginSuccess;
    }

    public boolean isLognFailure() {
        return LognFailure;
    }

    public void setLognFailure(boolean lognFailure) {
        LognFailure = lognFailure;
    }
}
