package com.lecture.mohammad.alarmLecture;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DBManager dbManager;
    Calendar calander;
    String Date,Date1;
    String y;
    TextView today;
    SimpleDateFormat simpledateformat;
    SimpleDateFormat simpledateformat1;
    FloatingActionButton fab;
    Button s1,s2,s3,s4,s5,s6,s7;
    int sa,su,mo,tu,we,th,fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1661339085652575/5490512641");


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        today = (TextView)findViewById(R.id.test);
        todaytext();
        String y = todaynumber();
        dbManager = new DBManager(this);


        LoadElement(y,1);
        LoadExamElement();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater1 = LayoutInflater.from(MainActivity.this);
                final View promptView1 = layoutInflater1.inflate(R.layout.schedual_dialog, null);
                final AlertDialog alertD1 = new AlertDialog.Builder(MainActivity.this).create();
                 s1=(Button)promptView1.findViewById(R.id.s1);
                 s2=(Button)promptView1.findViewById(R.id.s2);
                 s3=(Button)promptView1.findViewById(R.id.s3);
                 s4=(Button)promptView1.findViewById(R.id.s4);
                 s5=(Button)promptView1.findViewById(R.id.s5);
                 s6=(Button)promptView1.findViewById(R.id.s6);
                 s7=(Button)promptView1.findViewById(R.id.s7);
                if(sa ==1){
                    s1.setBackgroundColor(Color.BLACK);
                    s1.setTextColor(Color.WHITE);
                    s1.setTextSize(15);
                }
                else if(su==1){s2.setBackgroundColor(Color.BLACK);
                    s2.setTextColor(Color.WHITE);
                    s2.setTextSize(15);
                }
                else if(mo==1){s3.setBackgroundColor(Color.BLACK);
                    s3.setTextSize(15);
                    s3.setTextColor(Color.WHITE);}
                else if(tu==1){s4.setBackgroundColor(Color.BLACK);
                    s4.setTextSize(15);
                    s4.setTextColor(Color.WHITE);}
                else if(we==1){s5.setBackgroundColor(Color.BLACK);
                    s5.setTextSize(15);
                    s5.setTextColor(Color.WHITE);}
                else if(th==1){s6.setBackgroundColor(Color.BLACK);
                    s6.setTextSize(15);
                    s6.setTextColor(Color.WHITE);}
                else if(fr==1){s7.setBackgroundColor(Color.BLACK);
                    s7.setTextSize(15);
                    s7.setTextColor(Color.WHITE);}
                s1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadElement(" sat = 1 ",sa);
                        today.setText(R.string.Saturday);
                        alertD1.dismiss();
                    }
                });
                s2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadElement("  sun = 1 ",su);
                        today.setText(R.string.Sunday);
                        alertD1.dismiss();
                    }
                });
                s3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadElement("  mon = 1 ",mo);
                        today.setText(R.string.Monday);
                        alertD1.dismiss();
                    }
                });
                s4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadElement("  tus = 1 ",tu);
                        today.setText(R.string.Tuesday);
                        alertD1.dismiss();
                    }
                });
                s5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadElement("  wen = 1 ",we);
                        today.setText(R.string.Wednesday);
                        alertD1.dismiss();
                    }
                });
                s6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadElement("  the = 1 ",th);
                        today.setText(R.string.Thursday);
                        alertD1.dismiss();
                    }
                });
                s7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        today.setText(R.string.friday);
                        LoadElement(" fri = 1 ",fr);
                        alertD1.dismiss();
                    }
                });
                alertD1.setView(promptView1);
                alertD1.show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        int id=0;
        id=SharedPreferencesHelper.getIntSharedPref(getBaseContext(),"star");
        if(id==0){
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            final View promptView = layoutInflater.inflate(R.layout.langialog, null);
            final AlertDialog alertD = new AlertDialog.Builder(this).create();
            Button btnAdd1 = (Button) promptView.findViewById(R.id.btnAdd1);
            btnAdd1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,Add.class);
                    startActivity(intent);
                }
            });
            alertD.setView(promptView);
            alertD.show();
            id++;
        }
        SharedPreferencesHelper.setSharePref(getBaseContext(),"star",id);
    }


    void todaytext(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SATURDAY:
                today.setText(getResources().getString(R.string.Today)+" "+getResources().getString(R.string.Saturday));
                sa=1;
                break;
            case Calendar.SUNDAY:
                today.setText(getResources().getString(R.string.Today)+" "+getResources().getString(R.string.Sunday));
                su=1;
                break;
            case Calendar.MONDAY:
                today.setText(getResources().getString(R.string.Today)+" "+getResources().getString(R.string.Monday));
                mo=1;
                break;
            case Calendar.TUESDAY:
                today.setText(getResources().getString(R.string.Today)+" "+getResources().getString(R.string.Tuesday));
                tu=1;
                break;
            case Calendar.WEDNESDAY:
                today.setText(getResources().getString(R.string.Today)+" "+getResources().getString(R.string.Wednesday));
                we=1;
                break;
            case Calendar.THURSDAY:
                today.setText(getResources().getString(R.string.Today)+" "+getResources().getString(R.string.Thursday));
                th=1;
                break;
            case Calendar.FRIDAY:
                fr=1;
                today.setText(getResources().getString(R.string.Today)+" "+getResources().getString(R.string.friday));
                break;
        }
    }
    String todaynumber(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SATURDAY:
                 y = " sat = 1";
                break;
            case Calendar.SUNDAY:
                y = " sun = 1";
                break;
            case Calendar.MONDAY:
                y = " mon = 1";
                break;
            case Calendar.TUESDAY:
                y = " tus = 1";
                break;
            case Calendar.WEDNESDAY:
                y = " wen = 1";
                break;
            case Calendar.THURSDAY:
                y = " the = 1";
                break;
        }
        return y;
    }
    ArrayList<AdapterItems> listnewsData = new ArrayList<AdapterItems>();
    ArrayList<AdapterItems> listnewsData1 = new ArrayList<AdapterItems>();
    MyCustomAdapter myadapter;
    MyCustomAdapter2 myadapter2;

    MyCustomAdapter1 myadapter1;
    void LoadElement(String x,int w) {
        listnewsData.clear();
        Cursor cursor = dbManager.query(null,x, null, DBManager.hour);  //cursor to get elements from database
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
        if(w==1){
            myadapter = new MyCustomAdapter(listnewsData);
            ListView lsNews = (ListView) findViewById(R.id.LVNews);
            lsNews.setAdapter(myadapter);
        }
        else{
            myadapter2 = new MyCustomAdapter2(listnewsData);
            ListView lsNews = (ListView) findViewById(R.id.LVNews);
            lsNews.setAdapter(myadapter2);
        }

    }
    void LoadExamElement() {
        listnewsData1.clear();
        todaynumber();
        Cursor cursor = dbManager.queryE(null,null, null, DBManager.ColExamDayAndMonth);
        if (cursor.moveToFirst()) {
            do {
                listnewsData1.add(new AdapterItems(cursor.getString( cursor.getColumnIndex(DBManager.ColIDE)),
                        cursor.getString( cursor.getColumnIndex(DBManager.ColExamName)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.ColExamDay)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.ColExamMonth)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.ColExamDayAndMonth)),
                        cursor.getString( cursor.getColumnIndex(DBManager.ColExamType))));
            } while (cursor.moveToNext());
        }
        myadapter1 = new MyCustomAdapter1(listnewsData1);
        ListView lsNews = (ListView) findViewById(R.id.exams);
        lsNews.setAdapter(myadapter1);
    }
    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater;
        public MyCustomAdapter(ArrayList<AdapterItems> listnewsDataAdpater) {
            this.listnewsDataAdpater = listnewsDataAdpater;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.layout_ticket2, null);
            final AdapterItems s = listnewsDataAdpater.get(position);
            final TextView tvID = (TextView) myView.findViewById(R.id.NameLecture);
            final TextView tvUserName = (TextView) myView.findViewById(R.id.FromL);
            final TextView tvPassword = (TextView) myView.findViewById(R.id.ToL);
            final TextView tvNum = (TextView) myView.findViewById(R.id.num);


            tvID.setText(s.LectureName);
            tvUserName.setText(s.FromTime);
            tvPassword.setText(s.ToTime);
            tvNum.setText(s.LectureNumber);

            calander = Calendar.getInstance();
            simpledateformat = new SimpleDateFormat("mm");
            simpledateformat1 = new SimpleDateFormat("HH");
            Date = simpledateformat.format(calander.getTime());
            Date1 = simpledateformat1.format(calander.getTime());
            int MIN = Integer.parseInt(Date);
            int HOUR = Integer.parseInt(Date1);
            int HourNow = HOUR;
            int MinutesNow = MIN;
            int StartLectureHour  = s.hour;
            int StartLectureMinutes = s.min;
            int EndLectureHour = s.Ehour;
            int EndLectureMinutes = s.Emin;
            int result=0,resutltM=0;
            int TimeNow = HourNow*100+MinutesNow;
            int TimeStart = StartLectureHour*100+StartLectureMinutes;
            int TimeEnd = EndLectureHour*100+EndLectureMinutes;
            if(HourNow < StartLectureHour || (HourNow == StartLectureHour && MinutesNow < StartLectureMinutes )){
               // result=StartLectureHour-HourNow;


            }


            else if(TimeNow >= TimeStart && TimeNow <= TimeEnd){
                //myView.setBackgroundColor(Color.parseColor("#FFE4E1"));

                myView.setBackgroundColor(Color.parseColor("#FFE4E1"));
                tvID.setTextSize(15);
                tvUserName.setTextSize(15);
                tvPassword.setTextSize(15);
                tvNum.setTextSize(15);
                //Toast.makeText(getApplicationContext()," The Lecture Started",Toast.LENGTH_LONG).show();

            }
            else {
                //myView.setBackgroundColor(Color.parseColor("#FFE4E1"));

                tvUserName.setTextColor(Color.BLACK);
                tvPassword.setTextColor(Color.BLACK);


                // Toast.makeText(getApplicationContext()," The Lecture is Over ",Toast.LENGTH_LONG).show();

            }



            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    calander = Calendar.getInstance();
                    simpledateformat = new SimpleDateFormat("mm");
                    simpledateformat1 = new SimpleDateFormat("HH");
                    Date = simpledateformat.format(calander.getTime());
                    Date1 = simpledateformat1.format(calander.getTime());
                    int MIN = Integer.parseInt(Date);
                    int HOUR = Integer.parseInt(Date1);
                    int HourNow = HOUR;
                    int MinutesNow = MIN;
                    int StartLectureHour  = s.hour;
                    int StartLectureMinutes = s.min;
                    int EndLectureHour = s.Ehour;
                    int EndLectureMinutes = s.Emin;
                    int result=0,resutltM=0;
                    int TimeNow = HourNow*100+MinutesNow;
                    int TimeStart = StartLectureHour*100+StartLectureMinutes;
                    int TimeEnd = EndLectureHour*100+EndLectureMinutes;
                    if(HourNow < StartLectureHour || (HourNow == StartLectureHour && MinutesNow < StartLectureMinutes )){
                        result=StartLectureHour-HourNow;

                        if(MinutesNow < StartLectureMinutes){
                            resutltM=StartLectureMinutes-MinutesNow;
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.After)+" " +result+
                                    " "+getResources().getString(R.string.Hourand)+" "+resutltM+" "+getResources().getString(R.string.Minutes),Toast.LENGTH_LONG).show();
                        }
                        else if (MinutesNow == StartLectureMinutes){
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.After)+" "+result+" "
                                    +getResources().getString(R.string.Hour),Toast.LENGTH_LONG).show();
                        }
                        else {
                            resutltM=MinutesNow-StartLectureMinutes;
                            resutltM=60-resutltM;
                            result=result-1;
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.After)+" "+result+
                                    " "+getResources().getString(R.string.Hourand)+" "+resutltM+" "+getResources().getString(R.string.Minutes),Toast.LENGTH_LONG).show();
                        }
                    }


                        else if(TimeNow >= TimeStart && TimeNow <= TimeEnd){


                        Toast.makeText(getApplicationContext(),R.string.LectureStarted,Toast.LENGTH_LONG).show();

                    }
                    else {

                        Toast.makeText(getApplicationContext(),R.string.LectureOver,Toast.LENGTH_LONG).show();

                    }

                }
            });
            return myView;
        }
    }
    private class MyCustomAdapter1 extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater;
        public MyCustomAdapter1(ArrayList<AdapterItems> listnewsDataAdpater) {
            this.listnewsDataAdpater = listnewsDataAdpater;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.examitem, null);
            final AdapterItems s = listnewsDataAdpater.get(position);
            TextView tvID=(TextView)myView.findViewById(R.id.NameLecture);
            tvID.setText(s.ExamName);
            TextView tvPassword=(TextView)myView.findViewById(R.id.ToL);
            tvPassword.setText(s.ExamType);
            TextView timeofexam=(TextView)myView.findViewById(R.id.timeofexam);
            timeofexam.setText(s.ExamMonth+"-"+s.ExamDay);
            calander = Calendar.getInstance();
            simpledateformat = new SimpleDateFormat("MM");
            simpledateformat1 = new SimpleDateFormat("dd");
            Date = simpledateformat.format(calander.getTime());
            Date1 = simpledateformat1.format(calander.getTime());
            int mounth = Integer.parseInt(Date);
            int day = Integer.parseInt(Date1);
            int the_day_to_delete = day - 1 ;
            if(s.ExamMonth==mounth && s.ExamDay==the_day_to_delete) {
                String[] SelectionArgs={s.ID};
                dbManager.DeleteE("ID=?", SelectionArgs);
            }



            calander = Calendar.getInstance();
            simpledateformat = new SimpleDateFormat("MM");
            simpledateformat1 = new SimpleDateFormat("dd");
            Date = simpledateformat.format(calander.getTime());
            Date1 = simpledateformat1.format(calander.getTime());
            int mounth1 = Integer.parseInt(Date);
            int day1 = Integer.parseInt(Date1);
            if(s.ExamMonth==mounth1){


                if(s.ExamDay==day){
                    myView.setBackgroundColor(Color.parseColor("#b00000"));
                    tvID.setTextColor(Color.WHITE);
                    tvPassword.setTextColor(Color.WHITE);
                    timeofexam.setTextColor(Color.WHITE);
                }
                else if(s.ExamDay > day){
                    int sum = s.ExamDay-day;

                    if(sum == 1){myView.setBackgroundColor(Color.parseColor("#b00000"));
                        tvID.setTextColor(Color.WHITE);
                        tvPassword.setTextColor(Color.WHITE);
                        timeofexam.setTextColor(Color.WHITE);

                    }
                    else if (sum <= 3){myView.setBackgroundColor(Color.parseColor("#F08080"));
                        tvPassword.setTextColor(Color.WHITE);
                    }
                    else if (sum <= 7){myView.setBackgroundColor(Color.parseColor("#FFC1C1"));}
                    else if (sum <=12){myView.setBackgroundColor(Color.parseColor("#FFE4E1"));}

                }
                else if (s.ExamDay < day){
                    tvID.setTextColor(Color.WHITE);
                    tvPassword.setText(R.string.Good_Luck);
                    timeofexam.setTextColor(Color.WHITE);
                }

            }
            else if(s.ExamMonth != mounth1){
                int i =mounth1;
                int sum=0;

                while(i != s.ExamMonth){
                    if(i==13){i=i-12;}
                    else{
                        if(i == 12 || i==2 || i==4 || i==6 || i==7 || i==9 || i==11){
                            sum=sum+31;
                        }
                        else if(i==3 || i==5 || i==8 || i==10){
                            sum=sum+30;
                        }
                        else {
                            sum=sum+28;
                        }
                        i++;
                    }
                }
                int time = s.ExamDay-day1;
                sum=sum+time;
                if(sum == 1){myView.setBackgroundColor(Color.parseColor("#b00000"));
                    tvID.setTextColor(Color.WHITE);
                    tvPassword.setTextColor(Color.WHITE);
                    timeofexam.setTextColor(Color.WHITE);

                }
                else if (sum <= 3){myView.setBackgroundColor(Color.parseColor("#F08080"));
                    tvPassword.setTextColor(Color.WHITE);
                }
                else if (sum <= 7){myView.setBackgroundColor(Color.parseColor("#FFC1C1"));}
                else if (sum <=12){myView.setBackgroundColor(Color.parseColor("#FFE4E1"));}
            }

            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calander = Calendar.getInstance();
                    simpledateformat = new SimpleDateFormat("MM");
                    simpledateformat1 = new SimpleDateFormat("dd");
                    Date = simpledateformat.format(calander.getTime());
                    Date1 = simpledateformat1.format(calander.getTime());
                    int mounth = Integer.parseInt(Date);
                    int day = Integer.parseInt(Date1);
                    if(s.ExamMonth==mounth){
                        if(s.ExamDay==day){
                            Toast.makeText(getApplicationContext(),R.string.TodayYourExam,Toast.LENGTH_LONG).show();
                        }
                        else if(s.ExamDay > day){
                            int time = s.ExamDay-day;
                            Toast.makeText(getApplicationContext(), ""+getResources().getString(R.string.After)
                                    +" "+time+" "+getResources().getString(R.string.Days)
                                    +" ",Toast.LENGTH_LONG).show();
                        }
                    }
                    else if(s.ExamMonth != mounth){
                        int i =mounth;
                        int sum=0;

                        while(i != s.ExamMonth){
                        if(i==13){i=i-12;}
                            else{
                        if(i == 12 || i==2 || i==4 || i==6 || i==7 || i==9 || i==11){
                                sum=sum+31;
                            }
                            else if(i==3 || i==5 || i==8 || i==10){
                                sum=sum+30;
                            }
                            else {
                                sum=sum+28;
                            }
                            i++;
                        }
                        }
                        int time = s.ExamDay-day;
                        sum=sum+time;
                        if(sum > 30){
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.After)+" "+sum+" "+
                                    getResources().getString(R.string.Days)+" "+"\n" +
                                    " "+getResources().getString(R.string.Aftermoremonth)+"",Toast.LENGTH_SHORT).show();

                        }
                        else{
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.After)+" "+sum+" "+
                                getResources().getString(R.string.Days)+" ",Toast.LENGTH_SHORT).show();
                    }}
                }
            });
            return myView;
        }
    }
    private class MyCustomAdapter2 extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater;
        public MyCustomAdapter2(ArrayList<AdapterItems> listnewsDataAdpater) {
            this.listnewsDataAdpater = listnewsDataAdpater;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.layout_ticket2, null);
            final AdapterItems s = listnewsDataAdpater.get(position);
            final TextView tvID = (TextView) myView.findViewById(R.id.NameLecture);
            final TextView tvUserName = (TextView) myView.findViewById(R.id.FromL);
            final TextView tvPassword = (TextView) myView.findViewById(R.id.ToL);
            final TextView tvNum = (TextView) myView.findViewById(R.id.num);


            tvID.setText(s.LectureName);
            tvUserName.setText(s.FromTime);
            tvPassword.setText(s.ToTime);
            tvNum.setText(s.LectureNumber);
            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    ////////////////////////////////////////////////////
                   Toast.makeText(getApplicationContext(),R.string.NotToday,Toast.LENGTH_LONG).show();
                }
            });
            return myView;
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_lang)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            final View promptView = layoutInflater.inflate(R.layout.dlf, null);
            final AlertDialog alertD = new AlertDialog.Builder(this).create();


            alertD.setView(promptView);
            alertD.show();

        }
                return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            final Intent myintent=new Intent(this,Add.class);
            startActivity(myintent);
            finish();
        } else if (id == R.id.nav_gallery) {
            final Intent myintent1 =new Intent(this,AddExam.class);
            startActivity(myintent1);
            finish();
        } else if (id == R.id.nav_slideshow) {
            final Intent myintent2 =new Intent(this,AllLectures.class);
            startActivity(myintent2);
            finish();
        } else if (id == R.id.nav_slideshow2) {
            final Intent myintent2 =new Intent(this,AllExams.class);
            startActivity(myintent2);
            finish();
        }
        else if (id == R.id.Lgrades){
            final Intent myintent2 = new Intent(this , GradesLectures.class);
            startActivity(myintent2);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
