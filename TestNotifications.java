package com.lecture.mohammad.alarmLecture;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TestNotifications extends AppCompatActivity {
    DBManager dbManager;
    DatePicker Exam_Date;
    AlarmManager alarmManager;
    Intent alertIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_notifications);
        alertIntent = new Intent(getApplicationContext(),AlertReceier.class);
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        dbManager=new DBManager(this);
        LoadElement();

    }
    ArrayList<AdapterItems> listnewsData = new ArrayList<AdapterItems>();
    TestNotifications.MyCustomAdapter myadapter;
    void LoadElement() {
        listnewsData.clear();
        Cursor cursor = dbManager.queryID(null, null, null, DBManager.ColIDE);
        if (cursor.moveToFirst()) {
            do {
                listnewsData.add(new AdapterItems(cursor.getString( cursor.getColumnIndex(DBManager.ColIDE)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.ID2)),
                        cursor.getString( cursor.getColumnIndex(DBManager.ColLectName))));
            } while (cursor.moveToNext());
        }
        myadapter = new TestNotifications.MyCustomAdapter(listnewsData);
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
            View myView = mInflater.inflate(R.layout.notificationslist, null);
            final   AdapterItems s = listnewsDataAdpater.get(position);
            TextView tvID=(TextView)myView.findViewById(R.id.NameLecture);
            tvID.setText(s.LectureName);
            TextView tvPassword=(TextView)myView.findViewById(R.id.ToL);
            tvPassword.setText(s.ID2+"");
            Button buDelet=(Button)myView.findViewById(R.id.buDelet);// delete the exam

            buDelet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alarmManager.cancel(PendingIntent.getBroadcast(getApplicationContext(),s.ID2,alertIntent,PendingIntent.FLAG_UPDATE_CURRENT));

                }
            });

            return myView;
        }
    }
}