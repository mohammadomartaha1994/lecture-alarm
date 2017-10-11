package com.lecture.mohammad.alarmLecture;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by mohammad on 12/17/2016.
 */
public class AlertReceier extends BroadcastReceiver{
        public AlertReceier(){
        }
    @Override
    public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equalsIgnoreCase("com.lecture.alarm")){
             Bundle b = intent.getExtras();
            int id = 0 ;
            id=SharedPreferencesHelper.getIntSharedPref(context,"botato1");
            PendingIntent notification = PendingIntent.getActivity(context, id,
                    new Intent(context,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
            id++;
            SharedPreferencesHelper.setSharePref(context,"botato1",id);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.small_notification)
                    .setContentTitle(b.getString("lectureName"))
                    .setContentText(b.getString("alert"));
            builder.setContentIntent(notification);
            builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
            builder.setAutoCancel(true);
            builder.setVibrate(new long[] { 1000, 1000});

            NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(id);
            nm.notify(id,builder.build());
            }
            else if (intent.getAction().equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")){

                    Savedata savedata = new Savedata(context);
                    savedata.LoadData();

            }
    }
}
