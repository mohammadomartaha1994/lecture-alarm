package com.lecture.mohammad.alarmLecture;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class GradesLectures extends AppCompatActivity {
    DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grades_lectures);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1661339085652575/5490512641");


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
    GradesLectures.MyCustomAdapter myadapter;
    void LoadElement() {
        listnewsData.clear();
        Cursor cursor = dbManager.query(null, null, null, DBManager.ColID);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(DBManager.ColLectName));
                float sum=0 , total =0 , totalW =0 ;
                String resutlt ="";
                int co=0;
                Cursor cursor3 = dbManager.queryGRADES(null, " lectureName = '"+name+"' ", null, DBManager.ColIDE);
                if (cursor3.moveToFirst()) {
                    do {
                        co++;
                        sum=(float)cursor3.getInt(cursor3.getColumnIndex(DBManager.GRADUE)) / (float)cursor3.getInt(cursor3.getColumnIndex(DBManager.FFROM)) ;
                        total = total+( sum * (float) cursor3.getInt(cursor3.getColumnIndex(DBManager.WIGHTGRADUE)));
                        totalW = totalW + (float)cursor3.getInt(cursor3.getColumnIndex(DBManager.WIGHTGRADUE));
                    } while (cursor3.moveToNext());
                }
                if (co==0){
                     resutlt = "0/0" ;
                }
                else {
                     resutlt = (int) total + "/" + (int) totalW ;
                }

                listnewsData.add(new AdapterItems(cursor.getString( cursor.getColumnIndex(DBManager.ColID)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColLectName)) ,resutlt
                ));
            } while (cursor.moveToNext());
        }
        myadapter = new GradesLectures.MyCustomAdapter(listnewsData);
        ListView lsNews = (ListView) findViewById(R.id.listGradue);
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
            View myView = mInflater.inflate(R.layout.grades_lectures_item, null);
            final AdapterItems s = listnewsDataAdpater.get(position);
            final TextView LectureName = (TextView) myView.findViewById(R.id.NameLecture);
            final TextView result = (TextView) myView.findViewById(R.id.percentage);
            result.setText(s.result);
            LectureName.setText(s.LectureName);
            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GradesLectures.this,Grades.class);
                    intent.putExtra("lectureName",s.LectureName);
                    startActivity(intent);
                    finish();
                }
            });
            return myView;
        }
    }
}
