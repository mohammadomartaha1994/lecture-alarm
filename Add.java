package com.lecture.mohammad.alarmLecture;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Add extends AppCompatActivity {
    DBManager dbManager;
    TimePicker Ftime , Stime;
    EditText lecture_name;
    int id;
    int countx;
    SharedPreferencesHelper sharedPreferencesHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbManager=new DBManager(this);
        final Button theTime = (Button)findViewById(R.id.theTime);
        theTime.setOnClickListener(new View.OnClickListener() {  // open  dialog to choose start and end the lucture
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(Add.this);
                final View promptView = layoutInflater.inflate(R.layout.dialogtime, null);
                final AlertDialog alertD = new AlertDialog.Builder(Add.this).create();
                Ftime = (TimePicker)promptView.findViewById(R.id.timePicker);
                TextView textView2 =(TextView)promptView.findViewById(R.id.textView2);
                textView2.setText(R.string.from);
                Button toTime = (Button) promptView.findViewById(R.id.toTime);
                toTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater layoutInflater1 = LayoutInflater.from(Add.this);
                        final View promptView1 = layoutInflater1.inflate(R.layout.dialogtime, null);
                        final AlertDialog alertD1 = new AlertDialog.Builder(Add.this).create();
                        TextView textView2 =(TextView)promptView1.findViewById(R.id.textView2);
                        textView2.setText(R.string.to);
                        Button toTime = (Button) promptView1.findViewById(R.id.toTime);
                        toTime.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Stime = (TimePicker)promptView1.findViewById(R.id.timePicker);
                                alertD.dismiss();                                                   /// end the first and second dialog
                                alertD1.dismiss();
                            }
                        });
                        alertD1.setView(promptView1);
                        alertD1.show();                   // show the dialog

                    }
                });
                alertD.setView(promptView);
                alertD.show();                           /// show the dialog
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void setCalenderAlarm( int x, int y ,int z){
        if(z>=10){
            z=z-10;
        }
        else {
            y=y-1;
            z=z+50;
        }

        Calendar c = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK,x);
        calendar.set(Calendar.HOUR_OF_DAY,y);
        calendar.set(Calendar.MINUTE,z);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        id=SharedPreferencesHelper.getIntSharedPref(getBaseContext(),"the_ID2");
        Intent alertIntent = new Intent(getApplicationContext(),AlertReceier.class);
        alertIntent.setAction("com.lecture.alarm");
        alertIntent.putExtra("lectureName",lecture_name.getText().toString());
        alertIntent.putExtra("alert",getResources().getString(R.string.alert));
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        if(c.getTimeInMillis() > calendar.getTimeInMillis()){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+AlarmManager.INTERVAL_DAY*7
                    ,AlarmManager.INTERVAL_DAY*7,
                    PendingIntent.getBroadcast(getApplicationContext(), id,alertIntent,PendingIntent.FLAG_CANCEL_CURRENT));
        }
        else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,
                    PendingIntent.getBroadcast(getApplicationContext(), id,alertIntent,PendingIntent.FLAG_CANCEL_CURRENT));

        }


        ContentValues v=new ContentValues();
        v.put(DBManager.LECTURENAME,lecture_name.getText().toString());
        v.put(DBManager.DAY,x);
        v.put(DBManager.HOUR,y);
        v.put(DBManager.MIN,z);
        dbManager.InsertALARM(v);


        ContentValues values=new ContentValues();
        values.put(DBManager.ColLectName,lecture_name.getText().toString());
        values.put(DBManager.ID2,id);
        dbManager.InsertID(values);
        Log.i("add-id",id+" "+lecture_name.getText().toString());
        id++;
        SharedPreferencesHelper.setSharePref(getBaseContext(),"the_ID2",id);
    }


    public void buSave(View view){                                        /// function that call to store and test if the element is
         lecture_name = (EditText)findViewById(R.id.LN);           // exist or not
         EditText lecture_number = (EditText)findViewById(R.id.Lnum);
         TimePicker time = (TimePicker)findViewById(R.id.timePicker);
        if(Ftime == null ) {


////////////////////////////////////////////////



        Toast.makeText(getApplicationContext(),R.string.TimeLECT,Toast.LENGTH_LONG).show();
        }else{
            CheckBox sat =(CheckBox)findViewById(R.id.Saturday);
            CheckBox sun =(CheckBox)findViewById(R.id.Sunday);
            CheckBox mon =(CheckBox)findViewById(R.id.Monday);
            CheckBox tus =(CheckBox)findViewById(R.id.Tuesday);
            CheckBox wen =(CheckBox)findViewById(R.id.Wednesday);
            CheckBox thr =(CheckBox)findViewById(R.id.Thursday);
            CheckBox fri = (CheckBox)findViewById(R.id.fri);
            int hourPlus,min,min2,hourEnd;
            if((int) Build.VERSION.SDK_INT>=23){
                 hourPlus=Ftime.getHour();
                 min = Ftime.getMinute();
                 min2 = Stime.getMinute();
                 hourEnd =Stime.getHour();
            }
            else {
                 hourPlus = Ftime.getCurrentHour();
                 min = Ftime.getCurrentMinute();
                 min2 = Stime.getCurrentMinute();
                 hourEnd = Stime.getCurrentHour();
            }
            if(hourPlus > hourEnd || hourPlus == hourEnd && min >= min2 ){

                Toast.makeText(getApplicationContext(),R.string.CorrectTIME,Toast.LENGTH_LONG).show();
            }
            else{

                int hour;
                if((int) Build.VERSION.SDK_INT>=23){hour = Ftime.getHour();}
                else {hour = Ftime.getCurrentHour();}


         if(hour > 12){

             hour = hour-12;
         }
                int hour2;
                if((int) Build.VERSION.SDK_INT>=23){hour2 = Stime.getHour();}
                else {hour2 = Stime.getCurrentHour();}

         if(hour2 > 12){
            hour2 = hour2-12;
        }



         String starttime = String.valueOf(hour + ":" + min);
                String the_sort_time;
                if((int) Build.VERSION.SDK_INT>=23)   {the_sort_time = String.valueOf(Ftime.getHour()+""+ Ftime.getMinute());

                }
                else {the_sort_time = String.valueOf(Ftime.getCurrentHour()+""+ Ftime.getCurrentMinute());
                }

         String endtime = String.valueOf(hour2 + ":" + min2);


        ContentValues values=new ContentValues();                                  // store to database
        values.put(DBManager.ColLectName,lecture_name.getText().toString());
        values.put(DBManager.ColLectFromTime,starttime);
        values.put(DBManager.ColLectToTime,endtime);
        values.put(DBManager.ColSortTime,the_sort_time);
        values.put(DBManager.ColLectNum,lecture_number.getText().toString());
        values.put(DBManager.hour,hourPlus);
        values.put(DBManager.min,min);
        values.put(DBManager.Ehour,hourEnd);
        values.put(DBManager.Emin,min2);
        values.put(DBManager.ColSat,sat.isChecked()? 1 : 0 );
        values.put(DBManager.ColSun,sun.isChecked()? 1 : 0 );
        values.put(DBManager.ColMon,mon.isChecked()? 1 : 0 );
        values.put(DBManager.ColTus,tus.isChecked()? 1 : 0 );
        values.put(DBManager.ColWen,wen.isChecked()? 1 : 0 );
        values.put(DBManager.ColThe,thr.isChecked()? 1 : 0 );
        values.put(DBManager.ColFri,fri.isChecked()? 1 : 0 );
        final Intent myintent =new Intent(this,MainActivity.class);

                countx=0;
                if(sun.isChecked()){
                    countx++;
                }
                if(mon.isChecked()){
                    countx++;
                }
                if(tus.isChecked()){
                    countx++;
                }
                if(wen.isChecked()){
                    countx++;
                }
                if(thr.isChecked()){
                    countx++;
                }
                if(fri.isChecked()){
                    countx++;
                }
                if(sat.isChecked()){
                    countx++;
                }

        if(lecture_name.getText().toString().isEmpty()){


            Toast.makeText(getApplicationContext(),R.string.lectName,Toast.LENGTH_LONG).show();
        }
        else if (lecture_number.getText().toString().isEmpty()){

            Toast.makeText(getApplicationContext(),R.string.LectureRoomNumber,Toast.LENGTH_LONG).show();
        }
        else if (countx == 0){


             Toast.makeText(getApplicationContext(),R.string.RemDays,Toast.LENGTH_LONG).show();

        }
        else{

            if(sun.isChecked()){
                int x=Calendar.SUNDAY;
                setCalenderAlarm(x,hourPlus,min);
            }
            if(mon.isChecked()){
                int x=Calendar.MONDAY;
                setCalenderAlarm(x,hourPlus,min);
            }
            if(tus.isChecked()){
                int x=Calendar.TUESDAY;
                setCalenderAlarm(x,hourPlus,min);
            }
            if(wen.isChecked()){
                int x=Calendar.WEDNESDAY;
                setCalenderAlarm(x,hourPlus,min);
            }
            if(thr.isChecked()){
                int x=Calendar.THURSDAY;
                setCalenderAlarm(x,hourPlus,min);
            }
            if(fri.isChecked()){
                int x = Calendar.FRIDAY;
                setCalenderAlarm(x,hourPlus,min);
            }
            if(sat.isChecked()){
                int x=Calendar.SATURDAY;
                setCalenderAlarm(x,hourPlus,min);
            }



            long id=dbManager.Insert(values);
            if(id>0)
                Toast.makeText(getApplicationContext(), R.string.addLecture, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(),R.string.notaddLecture,Toast.LENGTH_LONG).show();

            startActivity(myintent);
            finish();
        }
        }}
    }
}