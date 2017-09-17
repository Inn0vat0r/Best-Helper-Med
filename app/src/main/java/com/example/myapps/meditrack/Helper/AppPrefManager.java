package com.example.myapps.meditrack.Helper;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class AppPrefManager {
    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "MediTrackInfo";

    // All Shared Preferences Keys
    private static final String KEY_SOS_MOB_NUM = "sos_mob_num";
    private static final String KEY_SOS_PERSON_NAME = "sos_person_name";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
  //  private static final String KEY_MOBILE = "mobile";

    public AppPrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setSOSMobileNumber(String mobileNumber) {
        editor.putString(KEY_SOS_MOB_NUM, mobileNumber);
        editor.commit();
    }

    public String getSOSMobileNumber() {
        return pref.getString(KEY_SOS_MOB_NUM, null);
    }
    public void setSOSPersonName(String personName) {
        editor.putString(KEY_SOS_PERSON_NAME, personName);
        editor.commit();
    }

    public String getSOSPersonName() {
        return pref.getString(KEY_SOS_PERSON_NAME, null);
    }
    public void createLogin(String name, String age, String mobile) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_AGE, age);
     //   editor.putString(KEY_MOBILE, mobile);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> profile = new HashMap<>();
        profile.put("name", pref.getString(KEY_NAME, null));
        profile.put("age", pref.getString(KEY_AGE, null));
//        profile.put("mobile", pref.getString(KEY_MOBILE, null));
        return profile;
    }

}