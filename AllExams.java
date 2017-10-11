package com.lecture.mohammad.alarmLecture;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
public class AllExams extends AppCompatActivity {
    DBManager dbManager;
    DatePicker Exam_Date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_exams);
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
        Cursor cursor = dbManager.queryE(null, null, null, DBManager.ColIDE);
        if (cursor.moveToFirst()) {
            do {
        listnewsData.add(new AdapterItems(cursor.getString( cursor.getColumnIndex(DBManager.ColIDE)),
        cursor.getString( cursor.getColumnIndex(DBManager.ColExamName)),
        cursor.getInt(cursor.getColumnIndex(DBManager.ColExamDay)),
        cursor.getInt(cursor.getColumnIndex(DBManager.ColExamMonth)),
        cursor.getInt(cursor.getColumnIndex(DBManager.ColExamDayAndMonth)),
        cursor.getString( cursor.getColumnIndex(DBManager.ColExamType))));
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
            View myView = mInflater.inflate(R.layout.layout_ticket1, null);
            final   AdapterItems s = listnewsDataAdpater.get(position);
            TextView tvID=(TextView)myView.findViewById(R.id.NameLecture);
            tvID.setText(s.ExamName);
            TextView tvPassword=(TextView)myView.findViewById(R.id.ToL);
            tvPassword.setText(s.ExamType);
            Button buDelet=(Button)myView.findViewById(R.id.buDelet);        // delete the exam
            buDelet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
            android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(AllExams.this);
            builder1.setMessage(R.string.DeleteE);
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    R.string.Delete,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String[] SelectionArgs={s.ID};
                            int count=dbManager.DeleteE("ID=?", SelectionArgs);
                            Toast.makeText(getApplicationContext(), R.string.DeletedExam, Toast.LENGTH_SHORT).show();
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
            final View promptView = layoutInflater.inflate(R.layout.updatedialogexam, null);
            final AlertDialog alertD = new AlertDialog.Builder(AllExams.this).create();
            Button buUpdate = (Button)myView.findViewById(R.id.buUpdate);
            buUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText Ename = (EditText)promptView.findViewById(R.id.Ename);
                    final Spinner spinnerExam = (Spinner)promptView.findViewById(R.id.planets_spinner);
                    final Button update = (Button) promptView.findViewById(R.id.Edate);
                    final Button done = (Button) promptView.findViewById(R.id.button);
                    Ename.setText(s.ExamName);
                    if(s.ExamType.equals("Final Exam")){
                        spinnerExam.setSelection(3);
                    }else if(s.ExamType.equals("Second Exam")){
                        spinnerExam.setSelection(2);
                    }else if(s.ExamType.equals("Midterm Exam")){
                        spinnerExam.setSelection(1);
                    }else if(s.ExamType.equals("First Exam")){
                        spinnerExam.setSelection(0);
                    }
                    else {
                        spinnerExam.setSelection(0);
                    }
                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {                   /// choose the date of the exam
                            LayoutInflater layoutInflater = LayoutInflater.from(AllExams.this);
                            final View promptView = layoutInflater.inflate(R.layout.dialogdate, null);
                            final AlertDialog alertD = new AlertDialog.Builder(AllExams.this).create();
                            TextView textView2 =(TextView)promptView.findViewById(R.id.textView2);
                            textView2.setText(R.string.Exam_Date);
                            Button toTime = (Button) promptView.findViewById(R.id.toTime);
                            toTime.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Exam_Date =(DatePicker)promptView.findViewById(R.id.datePicker);
                                    alertD.dismiss();
                                }
                            });
                            alertD.setView(promptView);
                            alertD.show();
                        }
                    });
                    alertD.setView(promptView);
                    alertD.show();
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(Exam_Date == null){
                                Toast.makeText(getApplicationContext(),R.string.DOE,Toast.LENGTH_LONG).show();
                            }
                            else{
                            int x = Exam_Date.getDayOfMonth() + Exam_Date.getMonth()*100 ;
                            String type = spinnerExam.getSelectedItem().toString();
                            ContentValues values=new ContentValues();
                            values.put(DBManager.ColExamName, Ename.getText().toString());
                            values.put(DBManager.ColExamDay,Exam_Date.getDayOfMonth());
                            values.put(DBManager.ColExamMonth,Exam_Date.getMonth()+1);
                            values.put(DBManager.ColExamDayAndMonth,x);
                            values.put(DBManager.ColExamType,type);
                            String[] SelectionArgs={String.valueOf(s.ID)};
                            int xx =dbManager.UpdateE(values,"ID=?",SelectionArgs);
                            if (xx > 0){Toast.makeText(getApplicationContext(),R.string.Updated,Toast.LENGTH_SHORT).show();}
                            else {Toast.makeText(getApplicationContext(),R.string.NUpdated,Toast.LENGTH_SHORT).show();}
                            final Intent myintent =new Intent(AllExams.this,AllExams.class);
                            startActivity(myintent);
                            Intent intent =new Intent(AllExams.this,AllExams.class);
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
}