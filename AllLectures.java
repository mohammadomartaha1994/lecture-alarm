package com.lecture.mohammad.alarmLecture;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AllLectures extends AppCompatActivity {
    DBManager dbManager;
    TimePicker Ftime , Stime;
    AlarmManager alarmManager;
    Intent alertIntent;
    EditText Lname;
    int countx;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_lectures);
        alertIntent = new Intent(getApplicationContext(),AlertReceier.class);
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbManager=new DBManager(this);
        LoadElement();

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

    ArrayList<AdapterItems> listnewsData = new ArrayList<AdapterItems>();
    MyCustomAdapter myadapter;
    void LoadElement() {
        listnewsData.clear();
        Cursor cursor = dbManager.query(null, null, null, DBManager.hour);
        if (cursor.moveToFirst()) {
            do {
                listnewsData.add(new AdapterItems(cursor.getString(cursor.getColumnIndex(DBManager.ColID)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColLectName)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColLectFromTime)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColLectToTime)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColLectNum)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColSortTime)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.hour)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.min)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.Ehour)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.Emin)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.ColSat)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.ColSun)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.ColMon)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.ColTus)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.ColWen)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.ColThe)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.ColFri))));
            } while (cursor.moveToNext());
        }
        myadapter = new MyCustomAdapter(listnewsData);
        ListView lsNews = (ListView) findViewById(R.id.LVNews);
        lsNews.setAdapter(myadapter);
    }
    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<AdapterItems>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }
        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.layout_ticket, null);
            final   AdapterItems s = listnewsDataAdpater.get(position);
            TextView tvID=(TextView)myView.findViewById(R.id.NameLecture);
            TextView tvUserName=(TextView)myView.findViewById(R.id.FromL);
            TextView tvPassword=(TextView)myView.findViewById(R.id.ToL);
            Button buDelet=(Button)myView.findViewById(R.id.buDelet);
            TextView sat =(TextView)myView.findViewById(R.id.sat);
            TextView sun =(TextView)myView.findViewById(R.id.sun);
            TextView mon =(TextView)myView.findViewById(R.id.mon);
            TextView tue =(TextView)myView.findViewById(R.id.tue);
            TextView wed =(TextView)myView.findViewById(R.id.wed);
            TextView fri =(TextView)myView.findViewById(R.id.fri);
            final TextView thu =(TextView)myView.findViewById(R.id.thu);
            if(s.ColThe==1){
                thu.setTextSize(15);
                thu.setTextColor(Color.RED);
                thu.invalidate();
            }
            if(s.ColWen == 1){
                wed.setTextSize(15);
                wed.setTextColor(Color.RED);
                wed.invalidate();
            }
            if(s.ColTus== 1){
                tue.setTextSize(15);
                tue.setTextColor(Color.RED);
                tue.invalidate();
            }
            if(s.ColMon== 1){
                mon.setTextSize(15);
                mon.setTextColor(Color.RED);
                mon.invalidate();
            }
            if(s.ColSun== 1){
                sun.setTextSize(15);
                sun.setTextColor(Color.RED);
                sun.invalidate();
            }
            if(s.ColSat== 1){
                sat.setTextSize(15);
                sat.setTextColor(Color.RED);
                sat.invalidate();
            }
            if(s.ColFri== 1){
                fri.setTextSize(15);
                fri.setTextColor(Color.RED);
                fri.invalidate();
            }
            tvID.setText(s.LectureName);
            tvUserName.setText(s.FromTime);
            tvPassword.setText(s.ToTime);
            buDelet.setOnClickListener(new View.OnClickListener() {  // delete lucture from database
                @Override
                public void onClick(View v) {
                    android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(AllLectures.this);
                    builder1.setMessage(R.string.DeleteL);
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            R.string.Delete,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Element(s.LectureName);
                                    String[] SelectionArgs={s.ID};
                                    int count=dbManager.Delete("ID=?", SelectionArgs);
                                    Toast.makeText( getApplicationContext(), R.string.DeletedLECT, Toast.LENGTH_SHORT).show();

                                    if(count > 0){
                                        LoadElement();
                                    }
                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton(
                            R.string.Cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    android.app.AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            });
            LayoutInflater layoutInflater = getLayoutInflater();
            final View promptView = layoutInflater.inflate(R.layout.lecturedialog, null);
            final AlertDialog alertD = new AlertDialog.Builder(AllLectures.this).create();
            Button buUpdate = (Button)myView.findViewById(R.id.buUpdate);
            buUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     Lname = (EditText)promptView.findViewById(R.id.LN);
                    final EditText Lnumber = (EditText)promptView.findViewById(R.id.Lnum);
                    final CheckBox Saturday = (CheckBox)promptView.findViewById(R.id.Saturday);
                    final CheckBox Sunday = (CheckBox)promptView.findViewById(R.id.Sunday);
                    final CheckBox Monday = (CheckBox)promptView.findViewById(R.id.Monday);
                    final CheckBox Tuesday = (CheckBox)promptView.findViewById(R.id.Tuesday);
                    final CheckBox Wednesday = (CheckBox)promptView.findViewById(R.id.Wednesday);
                    final CheckBox Thursday = (CheckBox)promptView.findViewById(R.id.Thursday);
                    final CheckBox Friday = (CheckBox)promptView.findViewById(R.id.fri);
                    Lname.setText(s.LectureName);
                    Lnumber.setText(s.LectureNumber);
                    Saturday.setChecked(s.ColSat==1?true:false);
                    Sunday.setChecked(s.ColSun==1?true:false);
                    Monday.setChecked(s.ColMon==1?true:false);
                    Tuesday.setChecked(s.ColTus==1?true:false);
                    Wednesday.setChecked(s.ColWen==1?true:false);
                    Thursday.setChecked(s.ColThe==1?true:false);
                    Friday.setChecked(s.ColFri==1?true:false);
                    final Button setTime = (Button) promptView.findViewById(R.id.theTime);
                    final Button done = (Button) promptView.findViewById(R.id.done);
                    setTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LayoutInflater layoutInflater = LayoutInflater.from(AllLectures.this);
                            final View promptView = layoutInflater.inflate(R.layout.dialogtime, null);
                            final AlertDialog alertD = new AlertDialog.Builder(AllLectures.this).create();
                            Ftime = (TimePicker)promptView.findViewById(R.id.timePicker);
                            TextView textView2 =(TextView)promptView.findViewById(R.id.textView2);
                            textView2.setText(R.string.from);
                            Button toTime = (Button) promptView.findViewById(R.id.toTime);
                            toTime.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    LayoutInflater layoutInflater1 = LayoutInflater.from(AllLectures.this);
                                    final View promptView1 = layoutInflater1.inflate(R.layout.dialogtime, null);
                                    final AlertDialog alertD1 = new AlertDialog.Builder(AllLectures.this).create();
                                    TextView textView2 =(TextView)promptView1.findViewById(R.id.textView2);
                                    textView2.setText(R.string.to);
                                    Button toTime = (Button) promptView1.findViewById(R.id.toTime);
                                    toTime.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Stime = (TimePicker)promptView1.findViewById(R.id.timePicker);
                                            alertD.dismiss();
                                            alertD1.dismiss();
                                        }
                                    });
                                    alertD1.setView(promptView1);
                                    alertD1.show();
                                }
                            });
                            alertD.setView(promptView);
                            alertD.show();
                        }
                    });
                    alertD.setView(promptView);
                    alertD.show();
                    done.setOnClickListener(new View.OnClickListener() {   //// UPDATE THE LECTURE
                        @Override
                        public void onClick(View v) {
                            if(Ftime == null) {
                                Toast.makeText(getApplicationContext(),R.string.TimeLECT,Toast.LENGTH_LONG).show();
                            }else{


                                Element(Lname.getText().toString());

                                int hourPlus,hourEnd,hour,min,hour2,min2;
                                if((int) Build.VERSION.SDK_INT>=23){
                                    hourPlus=Ftime.getHour();
                                    hourEnd =Stime.getHour();
                                    hour = Ftime.getHour();
                                    min = Ftime.getMinute();
                                    hour2 = Stime.getHour();
                                    min2 = Stime.getMinute();
                                }
                                else {

                                    hourPlus=Ftime.getCurrentHour();
                                    hourEnd =Stime.getCurrentHour();
                                    hour = Ftime.getCurrentHour();
                                    min = Ftime.getCurrentMinute();
                                    hour2 = Stime.getCurrentHour();
                                    min2 = Stime.getCurrentMinute();
                                }

                            if(hour > 12){
                                hour = hour-12;
                            }

                            if(hour2 > 12){
                                hour2 = hour2-12;
                            }






                            String starttime = String.valueOf(hour + ":" + min);
                                String the_sort_time;
                                if((int) Build.VERSION.SDK_INT>=23) {
                                     the_sort_time = String.valueOf(Ftime.getHour()+""+ Ftime.getMinute());

                                }
                                else {
                                    the_sort_time = String.valueOf(Ftime.getCurrentHour()+""+ Ftime.getCurrentMinute());

                                }
                            String endtime = String.valueOf(hour2 + ":" + min2);
                            ContentValues values=new ContentValues();                                  // store to database
                            values.put(DBManager.ColLectName,Lname.getText().toString());
                            values.put(DBManager.ColLectFromTime,starttime);
                            values.put(DBManager.ColLectToTime,endtime);
                            values.put(DBManager.ColSortTime,the_sort_time);
                            values.put(DBManager.ColLectNum,Lnumber.getText().toString());
                            values.put(DBManager.hour,hourPlus);
                            values.put(DBManager.min,min);
                            values.put(DBManager.Ehour,hourEnd);
                            values.put(DBManager.Emin,min2);
                            values.put(DBManager.ColSat,Saturday.isChecked()? 1 : 0 );
                            values.put(DBManager.ColSun,Sunday.isChecked()? 1 : 0 );
                            values.put(DBManager.ColMon,Monday.isChecked()? 1 : 0 );
                            values.put(DBManager.ColTus,Tuesday.isChecked()? 1 : 0 );
                            values.put(DBManager.ColWen,Wednesday.isChecked()? 1 : 0 );
                            values.put(DBManager.ColThe,Thursday.isChecked()? 1 : 0 );
                            values.put(DBManager.ColFri,Friday.isChecked()? 1 : 0);
                                countx=0;
                                if(Saturday.isChecked()){
                                    int x=Calendar.SATURDAY;
                                    setCalenderAlarm(x,hourPlus,min);
                                    countx++;
                                }
                                if(Sunday.isChecked()){
                                    int x=Calendar.SUNDAY;
                                    setCalenderAlarm(x,hourPlus,min);
                                    countx++;
                                }
                                if(Monday.isChecked()){
                                    int x=Calendar.MONDAY;
                                    setCalenderAlarm(x,hourPlus,min);
                                    countx++;
                                }
                                if(Tuesday.isChecked()){
                                    int x=Calendar.TUESDAY;
                                    setCalenderAlarm(x,hourPlus,min);
                                    countx++;
                                }
                                if(Wednesday.isChecked()){
                                    int x=Calendar.WEDNESDAY;
                                    setCalenderAlarm(x,hourPlus,min);
                                    countx++;
                                }
                                if(Thursday.isChecked()){
                                    int x=Calendar.THURSDAY;
                                    setCalenderAlarm(x,hourPlus,min);
                                    countx++;
                                }
                                if(Friday.isChecked()){
                                    int x=Calendar.FRIDAY;
                                    setCalenderAlarm(x,hourPlus,min);
                                    countx++;
                                }
                            String[] SelectionArgs={String.valueOf(s.ID)};
                            int xx =dbManager.Update(values,"ID=?",SelectionArgs);
                            if (xx > 0){Toast.makeText(getApplicationContext(),R.string.Updated,Toast.LENGTH_SHORT).show();}
                            else {Toast.makeText(getApplicationContext(),R.string.NUpdated,Toast.LENGTH_SHORT).show();}
                            Intent intent =new Intent(AllLectures.this,AllLectures.class);
                            startActivity(intent);
                            finish();
                            alertD.dismiss();
                        }}
                    });
                }
            });
            return myView;
        }
    }

    //ArrayList<AdapterItems> list = new ArrayList<AdapterItems>();
    void Element(String x) {
       // list.clear();
        Cursor cursor = dbManager.queryID(null, " LectureName = '"+x+"' ", null, DBManager.ColIDE);
        alertIntent = new Intent(getBaseContext(),AlertReceier.class);
        AlarmManager alarmManager = (AlarmManager)getBaseContext().getSystemService(ALARM_SERVICE);
        alertIntent.setAction("com.lecture.alarm");
        int idNumber;
        if (cursor.moveToFirst()) {
            do {
                idNumber = cursor.getInt(cursor.getColumnIndex(DBManager.ID2));
                 alarmManager.cancel(PendingIntent.getBroadcast(getBaseContext(),idNumber , alertIntent,PendingIntent.FLAG_CANCEL_CURRENT));
                Log.i("add-id",idNumber+" "+ x);
                String[] SelectionArgs={cursor.getString( cursor.getColumnIndex(DBManager.ColIDE))};
                dbManager.DeleteID("ID=?", SelectionArgs);
            } while (cursor.moveToNext());
        }


        Cursor cursor2 = dbManager.queryALARM(null, " lectureName = '"+x+"' ", null, DBManager.ColIDE);
        if (cursor2.moveToFirst()) {
            do {
                String[] SelectionArgs={cursor2.getString( cursor2.getColumnIndex(DBManager.ColIDE))};
                long idx = dbManager.DeleteALARM("ID=?", SelectionArgs);
            } while (cursor2.moveToNext());
        }


        Cursor cursor3 = dbManager.queryGRADES(null, " lectureName = '"+x+"' ", null, DBManager.ColIDE);
        if (cursor3.moveToFirst()) {
            do {
                String[] SelectionArgs={cursor3.getString( cursor3.getColumnIndex(DBManager.ColIDE))};
                long idx = dbManager.DeleteGRADES("ID=?", SelectionArgs);
            } while (cursor3.moveToNext());
        }

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
        alertIntent.putExtra("alert",getResources().getString(R.string.alert));
        alertIntent.putExtra("lectureName",Lname.getText().toString());
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);


        if(c.getTimeInMillis() > calendar.getTimeInMillis()){

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+AlarmManager.INTERVAL_DAY*7
                    ,AlarmManager.INTERVAL_DAY*7,
                    PendingIntent.getBroadcast(getApplicationContext(), id,alertIntent,PendingIntent.FLAG_UPDATE_CURRENT));
        }
        else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,
                    PendingIntent.getBroadcast(getApplicationContext(), id,alertIntent,PendingIntent.FLAG_UPDATE_CURRENT));


        }

        ContentValues v=new ContentValues();
        v.put(DBManager.LECTURENAME,Lname.getText().toString());
        v.put(DBManager.DAY,x);
        v.put(DBManager.HOUR,y);
        v.put(DBManager.MIN,z);
        dbManager.InsertALARM(v);


        ContentValues values=new ContentValues();
        values.put(DBManager.ColLectName,Lname.getText().toString());
        values.put(DBManager.ID2,id);
        dbManager.InsertID(values);
        id++;
        SharedPreferencesHelper.setSharePref(getBaseContext(),"the_ID2",id);
    }
}