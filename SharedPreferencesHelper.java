package com.lecture.mohammad.alarmLecture;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mohammad on 12/24/2016.
 */

public class SharedPreferencesHelper {

    public static String SHARED_PREFERENCES_FILE = "mySharedPref" ;


    public static void setSharePref(Context context, String key, int value){
        SharedPreferences sharedPref=context.getSharedPreferences(SHARED_PREFERENCES_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static void setSharePref(Context context, String key, String value){
        SharedPreferences sharedPref=context.getSharedPreferences(SHARED_PREFERENCES_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public  static int getIntSharedPref(Context context , String key)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE , Context.MODE_PRIVATE);
        int defaultValue = 0;
        return sharedPref.getInt(key, defaultValue);

    }
    public  static String getStringSharedPref(Context context , String key)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE , Context.MODE_PRIVATE);
        String defaultValue = "";
        return sharedPref.getString(key, defaultValue);

    }

}