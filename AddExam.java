package com.lecture.mohammad.alarmLecture;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddExam extends AppCompatActivity {
    DBManager dbManager;
    Calendar calander;
    String Date;
    DatePicker Exam_Date;
    SimpleDateFormat simpledateformat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbManager=new DBManager(this);

        Button Edate = (Button)findViewById(R.id.Edate);
        Edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                   /// choose the date of the exam


                LayoutInflater layoutInflater = LayoutInflater.from(AddExam.this);
                final View promptView = layoutInflater.inflate(R.layout.dialogdate, null);
                final AlertDialog alertD = new AlertDialog.Builder(AddExam.this).create();
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



   // public void startDua(View view) { startActivity(new Intent(this, MainActivity.class)); }


    public void buSave1(View view){                                          // test if the course name is enterd or not

        Spinner Exam_Type=(Spinner)findViewById(R.id.planets_spinner);
        EditText Exam_name = (EditText)findViewById(R.id.Ename);

        if(Exam_Date == null){


            Toast.makeText(getApplicationContext(),R.string.DOE,Toast.LENGTH_LONG).show();


        }
        else{



         if (Exam_name.getText().toString().isEmpty()){


             Toast.makeText(getApplicationContext(),R.string.CourseName,Toast.LENGTH_LONG).show();

         }
        else {


             String type = Exam_Type.getSelectedItem().toString();

             int x = Exam_Date.getDayOfMonth() + Exam_Date.getMonth() * 100;

             ContentValues values = new ContentValues();               // store to database

             values.put(DBManager.ColExamName, Exam_name.getText().toString());
             values.put(DBManager.ColExamDay, Exam_Date.getDayOfMonth());
             values.put(DBManager.ColExamMonth, Exam_Date.getMonth() + 1);
             values.put(DBManager.ColExamDayAndMonth, x);
             values.put(DBManager.ColExamType, type);
             final Intent myintent = new Intent(this, MainActivity.class);

             long id = dbManager.InsertE(values);
             if (id > 0)
                 Toast.makeText(getApplicationContext(),R.string.addExam , Toast.LENGTH_LONG).show();
             else
                 Toast.makeText(getApplicationContext(), R.string.NotaddExam, Toast.LENGTH_LONG).show();

             startActivity(myintent);

             finish();

         }
    }
    }
}
