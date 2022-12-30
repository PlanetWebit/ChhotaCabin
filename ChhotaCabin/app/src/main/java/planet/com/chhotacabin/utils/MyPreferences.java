package planet.com.chhotacabin.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class MyPreferences {
    private static MyPreferences preferences = null;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;
    private String flag = "flag";
    private String isLogedIn = "isLogedIn";
    private String checkloginorNot = "checkloginorNot";

    private String userId = "userId";
    private String mobile = "mobile";
    private String name = "name";
    private String custid = "custid";
    private String email = "email";
    private String profileImage = "profileImage";
    private String refer_point = "refer_point";
    private int balance = 0;


    public MyPreferences(Context context) {
        setmPreferences(PreferenceManager.getDefaultSharedPreferences(context));
    }


    public SharedPreferences getmPreferences() {
        return mPreferences;
    }

    public void setmPreferences(SharedPreferences mPreferences) {
        this.mPreferences = mPreferences;
    }


    public static MyPreferences getActiveInstance(Context context) {
        if (preferences == null) {
            preferences = new MyPreferences(context);
        }
        return preferences;
    }

    public boolean getIsLoggedIn() {
        return mPreferences.getBoolean(this.isLogedIn, false);
    }

    public void setIsLoggedIn(boolean isLoggedin) {
        editor = mPreferences.edit();
        editor.putBoolean(this.isLogedIn, isLoggedin);
        editor.commit();
    } public boolean getCheckloginorNot() {
        return mPreferences.getBoolean(this.checkloginorNot, false);
    }

    public void setCheckloginorNot(boolean checkloginorNot) {
        editor = mPreferences.edit();
        editor.putBoolean(this.checkloginorNot, checkloginorNot);
        editor.commit();
    }


    public boolean getflag() {
        return mPreferences.getBoolean(this.flag, false);
    }

    public void setflag(boolean flag) {
        editor = mPreferences.edit();
        editor.putBoolean(this.flag, flag);
        editor.commit();
    }


    public String getUserId() {
        return mPreferences.getString(this.userId, "");

    }


    public void setUserId(String userId) {
        editor = mPreferences.edit();
        editor.putString(this.userId, userId);
        editor.commit();
    }


    public String getmobile() {
        return mPreferences.getString(this.mobile, "");

    }


    public void setmobile(String mobile) {
        editor = mPreferences.edit();
        editor.putString(this.mobile, mobile);
        editor.commit();
    }

    public String getcustid() {
        return mPreferences.getString(this.custid, "");

    }


    public void setcustid(String custid) {
        editor = mPreferences.edit();
        editor.putString(this.custid, custid);
        editor.commit();
    }

    public String getname() {
        return mPreferences.getString(this.name, "");

    }


    public void setname(String name) {
        editor = mPreferences.edit();
        editor.putString(this.name, name);
        editor.commit();
    }

    public String getEmail() {
        return mPreferences.getString(this.email, "");

    }


    public void setEmail(String email) {
        editor = mPreferences.edit();
        editor.putString(this.email, email);
        editor.commit();
    }

    public String getProfileImage() {
        return mPreferences.getString(this.profileImage, "");

    }


    public void setProfileImage(String profileImage) {
        editor = mPreferences.edit();
        editor.putString(this.profileImage, profileImage);
        editor.commit();
    }

    public String getRefer_point() {
        return mPreferences.getString(refer_point,"");

    }


    public void setRefer_point(String refer_point) {
        editor = mPreferences.edit();
        editor.putString(this.refer_point, refer_point);
        editor.commit();
    }

    public int getBalance() {
        return mPreferences.getInt("", balance);

    }


    public void setBalance(int balance) {
        editor = mPreferences.edit();
        editor.putInt("", balance);
        editor.commit();
    }
}
