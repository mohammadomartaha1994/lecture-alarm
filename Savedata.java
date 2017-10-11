package com.lecture.mohammad.alarmLecture;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by mohammad on 2/2/2017.
 */

public class Savedata {
    Context context;
    SharedPreferences ShredRef;
    DBManager dbManager;
    AlarmManager alarmManager;
    Intent alertIntent;

    public Savedata(Context context){
        this.context=context;
        dbManager=new DBManager(context);
        ShredRef=context.getSharedPreferences("saveTime",Context.MODE_PRIVATE);
    }

    public  void SaveData(int day , int hour , int min , String lectureName ){
        SharedPreferences.Editor editor=ShredRef.edit();
        editor.putInt("hour",hour);
        editor.putInt("min",min);
        editor.putInt("day",day);
        editor.putString("lName",lectureName);
        editor.commit();
    }




    ArrayList<AdapterItems> list = new ArrayList<AdapterItems>();
    void Element() {
        list.clear();
        Cursor cursor = dbManager.queryALARM(null,null, null, DBManager.ColIDE);
        if (cursor.moveToFirst()) {
            do {

                setAlarm(cursor.getInt( cursor.getColumnIndex(DBManager.DAY)),
                        cursor.getInt( cursor.getColumnIndex(DBManager.HOUR)),
                        cursor.getInt( cursor.getColumnIndex(DBManager.MIN)),
                        cursor.getString( cursor.getColumnIndex(DBManager.LECTURENAME)));

            } while (cursor.moveToNext());
        }
    }




    void setAlarm(int day , int hour , int min , String lectureName){
        Calendar c = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        int id=SharedPreferencesHelper.getIntSharedPref(context,"the_ID2");
        Intent alertIntent = new Intent(context,AlertReceier.class);
        alertIntent.setAction("com.lecture.alarm");
        alertIntent.putExtra("lectureName",lectureName);
        alertIntent.putExtra("alert",context.getResources().getString(R.string.alert));
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);

        if(c.getTimeInMillis() > calendar.getTimeInMillis()){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+AlarmManager.INTERVAL_DAY*7
                    ,AlarmManager.INTERVAL_DAY*7,
                    PendingIntent.getBroadcast(context, id,alertIntent,PendingIntent.FLAG_UPDATE_CURRENT));
        }
        else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,
                    PendingIntent.getBroadcast(context, id,alertIntent,PendingIntent.FLAG_UPDATE_CURRENT));

        }

        ContentValues values=new ContentValues();
        values.put(DBManager.ColLectName,lectureName);
        values.put(DBManager.ID2,id);
        dbManager.InsertID(values);
        id++;
        SharedPreferencesHelper.setSharePref(context,"the_ID2",id);
    }



    public void LoadData(){
        Element();
    }
}
