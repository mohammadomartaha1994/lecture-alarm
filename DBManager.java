package com.lecture.mohammad.alarmLecture;

/**
 * Created by mohammad on 9/15/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class DBManager {
    private SQLiteDatabase sqlDB;
    static final String DBName="LectursAndExams";
    static final String TableName="Lecturs";

    static final String ColLectName="LectureName";
    static final String ColLectFromTime="FromTime";
    static final String ColLectToTime="ToTime";
    static final String ColLectdays="LectureDays";
    static final String ColLectNum="LectureNumber";
    static final String ColID="ID";
    static final String hour="hour";
    static final String Ehour="Ehour";
    static final String Emin="Emin";
    static final String ColSortTime="ColSortTime";
    static final String min="min";



    static final String TableName2="Exams";

    static final String ColExamName="ExamName";
    static final String ColExamDay="ExamDay";
    static final String ColExamMonth="ExamMonth";
    static final String ColExamType="ExamType";
    static final String ColExamDayAndMonth="DayAndMonth";
    static final String ColIDE="ID";


    static final String TableName4="Alarm";
    static final String DAY="day";
    static final String MIN="min";
    static final String HOUR="hour";
    static final String LECTURENAME="lectureName";





    static final String TableName3="IDes";
    static final String ID2="id2";

//    static final String TableName2="Exams";
//    static final String ColExamName="ExamName";
//    static final String ColExamDate="ExamDate";
//    static final String ColIDE="ID";
//




    //
    //static final String DBName="Students";
   // static final String TableName="Logins";
    static final String ColUserName="UserName";
    static final String ColPassWord="Password";
    static final String ColSat="sat";
    static final String ColSun="sun";
    static final String ColMon="mon";
    static final String ColTus="tus";
    static final String ColWen="wen";
    static final String ColThe="the";
    static final String ColFri="fri";



    static final int DBVersion=22;
//    static final String CreateTable="CREATE TABLE IF NOT EXISTS " +
//            TableName+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
//            ColUserName+ " TEXT, "+
//            ColPassWord+" TEXT);";

    static final String CreateTable="CREATE TABLE IF NOT EXISTS " +   // table from lucters
            TableName+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ColLectName+ " TEXT, "+
            ColLectFromTime+" TEXT,"+
            ColLectToTime+" TEXT,"+
            ColSat+" INTEGER,"+
            ColSun+" INTEGER,"+
            ColMon+" INTEGER,"+
            ColTus+" INTEGER,"+
            ColWen+" INTEGER,"+
            ColThe+" INTEGER,"+
            ColFri+" INTEGER,"+
            ColLectNum+" TEXT ,"+
            ColSortTime+" TEXT ,"+
            hour+" INTEGER , "+
            min+" INTEGER , "+
            Ehour+" INTEGER , "+
            Emin+" INTEGER );";

    static final String CreateTable2="CREATE TABLE IF NOT EXISTS " +  // table from exams
            TableName2+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ColExamName+ " TEXT, "+
            ColExamDay +" INTEGER,"+
            ColExamMonth+" INTEGER,"+
            ColExamDayAndMonth +" INTEGER,"+
            ColExamType+" TEXT);";


    static final String CreateTable3="CREATE TABLE IF NOT EXISTS " +  // table from exams
            TableName3+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ID2+" INTEGER,"+
            ColLectName+" TEXT);";



    static final String CreateTable4="CREATE TABLE IF NOT EXISTS " +  // table from ALARM
            TableName4+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DAY+" INTEGER,"+
            HOUR+" INTEGER,"+
            MIN+" INTEGER, "+
            LECTURENAME+" TEXT );";

    static final String TableName5="grades";
    static final String FFROM="ffrom";
    static final String WIGHTGRADUE="wight";
    static final String GRADUE="grade";
    static final String ExamType="examtype";
    static final String TEXTGRADUE="textgradue";


    static final String CreateTable5="CREATE TABLE IF NOT EXISTS " +  // table from ALARM
            TableName5+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
            WIGHTGRADUE+" INTEGER, "+
            GRADUE+" INTEGER, "+
            FFROM+" INTEGER, "+
            ExamType+" TEXT, "+
            TEXTGRADUE+" TEXT, "+
            LECTURENAME+" TEXT );";



//    static final String CreateTable2="CREATE TABLE IF NOT EXISTS "+TableName2+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+ColUserName+
//            " TEXT, "+ColPassWord+" TEXT);";
//
//



    static class DatabaseHelperUser extends SQLiteOpenHelper{   //database helper to execute create and upgrade the tabels
        Context context;
        DatabaseHelperUser(Context context){
            super(context,DBName,null,DBVersion);
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CreateTable);
            db.execSQL(CreateTable2);
            db.execSQL(CreateTable3);
            db.execSQL(CreateTable4);
            db.execSQL(CreateTable5);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop table IF EXISTS "+TableName);
            db.execSQL("Drop table IF EXISTS "+TableName2);
            db.execSQL("Drop table IF EXISTS "+TableName3);
            db.execSQL("Drop table IF EXISTS "+TableName4);
            db.execSQL("Drop table IF EXISTS "+TableName5);
            onCreate(db);

        }
    }

    public DBManager(Context context){
        DatabaseHelperUser db=new DatabaseHelperUser(context);
        sqlDB = db.getWritableDatabase();
    }



    public long Insert(ContentValues values){         // insert luctures
        long ID = sqlDB.insert(TableName,"",values);
        return ID;
    }


    public long InsertE(ContentValues values){       // insert exams
        long ID = sqlDB.insert(TableName2,"",values);
        return ID;
    }

    public long InsertID(ContentValues values){       // insert exams
        long ID = sqlDB.insert(TableName3,"",values);
        return ID;
    }

    public long InsertALARM(ContentValues values){       // insert exams
        long ID = sqlDB.insert(TableName4,"",values);
        return ID;
    }

    public long InsertGRADES(ContentValues values){       // insert exams
        long ID = sqlDB.insert(TableName5,"",values);
        return ID;
    }


    public Cursor query(String[] Projection,String Selection, String[] SelectionArgs,String SortOrder){  //cursor to display elements
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        qb.setTables(TableName);
        Cursor cursor=qb.query(sqlDB, Projection, Selection, SelectionArgs, null, null, SortOrder+" ASC");
        return cursor;
    }

    public Cursor queryE(String[] Projection,String Selection, String[] SelectionArgs,String SortOrder){  // cursor for exams
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        qb.setTables(TableName2);
        Cursor cursor=qb.query(sqlDB, Projection, Selection, SelectionArgs, null, null, SortOrder);
        return cursor;
    }

    public Cursor queryID(String[] Projection,String Selection, String[] SelectionArgs,String SortOrder){  // cursor for exams
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        qb.setTables(TableName3);
        Cursor cursor=qb.query(sqlDB, Projection, Selection, SelectionArgs, null, null, SortOrder);
        return cursor;
    }

    public Cursor queryALARM(String[] Projection,String Selection, String[] SelectionArgs,String SortOrder){  // cursor for exams
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        qb.setTables(TableName4);
        Cursor cursor=qb.query(sqlDB, Projection, Selection, SelectionArgs, null, null, SortOrder);
        return cursor;
    }

    public Cursor queryGRADES(String[] Projection,String Selection, String[] SelectionArgs,String SortOrder){  // cursor for exams
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        qb.setTables(TableName5);
        Cursor cursor=qb.query(sqlDB, Projection, Selection, SelectionArgs, null, null, SortOrder);
        return cursor;
    }


    public int Delete(String Selection,String[] SelectionArgs){        // delete lucturs
        int count = sqlDB.delete(TableName, Selection, SelectionArgs);
        return count;
    }
    public int DeleteE(String Selection,String[] SelectionArgs){  //delete exams
        int count = sqlDB.delete(TableName2,Selection,SelectionArgs);
        return count;
    }

    public int DeleteID(String Selection,String[] SelectionArgs){  //delete exams
        int count = sqlDB.delete(TableName3,Selection,SelectionArgs);
        return count;
    }


    public int DeleteALARM(String Selection,String[] SelectionArgs){  //delete exams
        int count = sqlDB.delete(TableName4,Selection,SelectionArgs);
        return count;
    }
    public int DeleteGRADES(String Selection,String[] SelectionArgs){  //delete exams
        int count = sqlDB.delete(TableName5,Selection,SelectionArgs);
        return count;
    }


    public  int Update(ContentValues values,String Selection,String[] SelectionArgs){
        int count=sqlDB.update(TableName,values,Selection,SelectionArgs);
        return count;

    }


    public  int UpdateE(ContentValues values,String Selection,String[] SelectionArgs){
        int count=sqlDB.update(TableName2,values,Selection,SelectionArgs);
        return count;

    }


    public  int UpdateID(ContentValues values,String Selection,String[] SelectionArgs){
        int count=sqlDB.update(TableName3,values,Selection,SelectionArgs);
        return count;

    }



    public  int UpdateALARM(ContentValues values,String Selection,String[] SelectionArgs){
        int count=sqlDB.update(TableName4,values,Selection,SelectionArgs);
        return count;

    }

    public  int UpdateGRADES(ContentValues values,String Selection,String[] SelectionArgs){
        int count=sqlDB.update(TableName5,values,Selection,SelectionArgs);
        return count;

    }


}
