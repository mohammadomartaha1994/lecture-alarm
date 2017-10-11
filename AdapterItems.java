package com.lecture.mohammad.alarmLecture;

/**
 * Created by mohammad on 9/15/2016.
 */
public class AdapterItems
{
    public  String ID;
    public  String LectureName;
    public  String FromTime;
    public  String ToTime;
    public  int LectureDays;
    public  String LectureNumber;
    public  String EID;
    public  String ExamName;
    public  int ExamDay;
    public  int ExamMonth;
    public  String ExamType;
    public  int hour;
    public  int min;
    public  int ID2;
    public  int Ehour;
    public  int Emin;
    public  String ColSortTime;
    public  int ColSat;
    public  int ColSun;
    public  int day;
    public  int ColMon;
    public  int ColTus;
    public  String result;

    public  int WIGHTGRADUE;
    public  String TEXTGRADUE;
    public  String LECTURENAME;
    public  int GRADUE;
    public  int FFROM;

    public  int ColWen;
    public  int ColThe;
    public  int ColFri;
    public  int DayAndMonth;
    AdapterItems( String ID, String LectureName,String FromTime,
                  String ToTime, String LectureNumber,String ColSortTime,
                  int hour,int min,int Ehour,int Emin,
                  int ColSat,int ColSun,int ColMon,int ColTus,int ColWen,int ColThe,int ColFri)
    {
        this.ID=ID;
        this.LectureName=LectureName;
        this.FromTime=FromTime;
        this.ToTime=ToTime;
        this.LectureNumber=LectureNumber;
        this.ColSortTime=ColSortTime;
        this.hour=hour;
        this.min=min;
        this.Ehour=Ehour;
        this.Emin=Emin;
        this.ColSat=ColSat;
        this.ColSun=ColSun;
        this.ColMon=ColMon;
        this.ColTus=ColTus;
        this.ColWen=ColWen;
        this.ColThe=ColThe;
        this.ColFri=ColFri;
    }

    AdapterItems( String ID, String ExamName,int ExamDay, int ExamMonth,int DayAndMonth, String ExamType)
    {
        this.ID=ID;
        this.ExamName=ExamName;
        this.ExamDay=ExamDay;
        this.ExamMonth=ExamMonth;
        this.DayAndMonth=DayAndMonth;
        this.ExamType=ExamType;
    }

    AdapterItems(String ID,int ID2,String LectureName){
        this.ID=ID;
        this.ID2=ID2;
        this.LectureName=LectureName;

    }

    AdapterItems(String ID,String LectureName){
        this.ID=ID;
        this.LectureName=LectureName;

    }
    AdapterItems(String ID ,int day , int hour , int min , String LectureName ){
        this.ID=ID;
        this.hour=hour;
        this.min=min;
        this.LectureName=LectureName;
        this.day=day;
    }


    AdapterItems(String ID ,int WIGHTGRADUE , int GRADUE , int FFROM , String ExamType, String TEXTGRADUE, String LECTURENAME ){
        this.ID=ID;
        this.WIGHTGRADUE=WIGHTGRADUE;
        this.GRADUE=GRADUE;
        this.FFROM=FFROM;
        this.ExamType=ExamType;
        this.TEXTGRADUE=TEXTGRADUE;
        this.LECTURENAME=LECTURENAME;
    }


    AdapterItems(String ID,String LectureName,String result){
        this.ID=ID;
        this.result=result;
        this.LectureName=LectureName;

    }

}


