package com.lecture.mohammad.alarmLecture;

import android.app.AlertDialog;
import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class Grades extends AppCompatActivity {
    TextView totalGrade;
    DBManager dbManager;
    TextView name;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grades);
        dbManager=new DBManager(this);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1661339085652575/5490512641");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        totalGrade = (TextView)findViewById(R.id.total);
        message = intent.getStringExtra("lectureName");
        LoadElement(message);
        name = (TextView)findViewById(R.id.name);
        name.setText(message);
        Button button = (Button)findViewById(R.id.addMark);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(Grades.this);
                final View promptView = layoutInflater.inflate(R.layout.grades_dialog, null);
                final AlertDialog alert = new AlertDialog.Builder(Grades.this).create();
                final EditText grade = (EditText)promptView.findViewById(R.id.editText2);
                final EditText grade_from = (EditText)promptView.findViewById(R.id.editText3);
                final EditText grade_w = (EditText)promptView.findViewById(R.id.editText4);
                final Spinner Exam_Type=(Spinner)promptView.findViewById(R.id.planets_spinner);



                alert.setView(promptView);
                alert.show();
                final Button done = (Button)promptView.findViewById(R.id.done);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(grade.getText().toString().isEmpty() || grade_from.getText().toString().isEmpty() ||
                                grade_w.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(),R.string.Incorrect_values,Toast.LENGTH_LONG).show();
                        }
                        else {


                            int gr = Integer.parseInt(grade.getText().toString());
                            int gr_g = Integer.parseInt(grade_from.getText().toString());
                            int gr_w = Integer.parseInt(grade_w.getText().toString());

                            if (gr > gr_g){
                                Toast.makeText(getApplicationContext(),R.string.Incorrect_values,Toast.LENGTH_LONG).show();
                            }
                            else {

                                final String type = Exam_Type.getSelectedItem().toString();
                                ContentValues values = new ContentValues();
                                values.put(DBManager.LECTURENAME, message);
                                values.put(DBManager.GRADUE, grade.getText().toString());
                                values.put(DBManager.WIGHTGRADUE, grade_w.getText().toString());
                                values.put(DBManager.ExamType, type);
                                values.put(DBManager.TEXTGRADUE, grade.getText().toString() + "/" + grade_from.getText().toString());
                                values.put(DBManager.FFROM, grade_from.getText().toString());
                                dbManager.InsertGRADES(values);

                                LoadElement(message);
                                alert.dismiss();
                            }
                        }
                    }
                });

                final Button not_DONE = (Button)promptView.findViewById(R.id.not_done);
                not_DONE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });



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
            Intent intent = new Intent(this,GradesLectures.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }




    ArrayList<AdapterItems> listnewsData = new ArrayList<AdapterItems>();
    Grades.MyCustomAdapter myadapter;
    void LoadElement(String x) {
        listnewsData.clear();
        float sum=0 , total =0 , totalW =0 ;
        int co=0;
        Cursor cursor = dbManager.queryGRADES(null, " lectureName = '"+x+"'" , null, DBManager.ColID);
        if (cursor.moveToFirst()) {
            do {
                listnewsData.add(new AdapterItems(cursor.getString( cursor.getColumnIndex(DBManager.ColID)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.WIGHTGRADUE)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.GRADUE)),
                        cursor.getInt(cursor.getColumnIndex(DBManager.FFROM)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ExamType)),
                        cursor.getString(cursor.getColumnIndex(DBManager.TEXTGRADUE)),
                        cursor.getString(cursor.getColumnIndex(DBManager.LECTURENAME))

                ));
                co++;
                sum=(float)cursor.getInt(cursor.getColumnIndex(DBManager.GRADUE)) / (float)cursor.getInt(cursor.getColumnIndex(DBManager.FFROM)) ;
                total = total+( sum * (float) cursor.getInt(cursor.getColumnIndex(DBManager.WIGHTGRADUE)));
                totalW = totalW + (float)cursor.getInt(cursor.getColumnIndex(DBManager.WIGHTGRADUE));

            } while (cursor.moveToNext());


        }
        if (co==0){
            totalGrade.setText( "0/0" );
        }
        else {
            totalGrade.setText((int) total + "/" + (int) totalW);
        }
        myadapter = new Grades.MyCustomAdapter(listnewsData);
        ListView lsNews = (ListView) findViewById(R.id.botato);
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
            View myView = mInflater.inflate(R.layout.grades_item, null);
            final AdapterItems s = listnewsDataAdpater.get(position);
            final TextView type = (TextView) myView.findViewById(R.id.type);
            final TextView percentage = (TextView) myView.findViewById(R.id.percentage);


            percentage.setText(s.TEXTGRADUE);
            type.setText(s.ExamType);



            ImageButton delete = (ImageButton)myView.findViewById(R.id.imageButton);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                String[] SelectionArgs={s.ID};
                dbManager.DeleteGRADES("ID=?", SelectionArgs);
                LoadElement(message);
                }
            });

            return myView;
        }
    }



}
