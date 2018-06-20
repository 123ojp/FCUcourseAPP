package com.example.o123ojp.appprojet;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class CheckService extends Service {
    final int notiId = 10000;
    static int notiIdAdd = 20000;
    Cheakcourse course = null;
    int counter = 0;
    NotificationManager notimanger;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            counter++;
            String nitiText = "實收名額/開放名額:\n" + course.getReal_quota() + "/" + course.getOpen_quota() + "\n已查詢次數:" + Integer.toString(counter);
            Intent inetnt = new Intent(getApplicationContext(), CheckActivity.class);
            inetnt.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, inetnt, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification noti = new NotificationCompat.Builder(getApplicationContext(), "default")
                    .setContentTitle(course.getSub_name())
                    .setContentText(nitiText)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(nitiText))
                    .setTicker("開始查詢")
                    .setContentIntent(contentIntent)
                    .build();
            notimanger.notify(notiId, noti);
            if (course.whether_if_OKadd()) {
                String notiText = "目前可以加選\n餘額:" + Float.toString(Float.parseFloat(course.getOpen_quota()) - Float.parseFloat(course.getReal_quota()));
                noti = new NotificationCompat.Builder(getApplicationContext(), "default")
                        .setContentTitle(course.getSub_name())
                        .setContentText(notiText)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000})
                        .setTicker(course.getSub_name() + " 目前可以加選")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notiText))
                        .build();
                notimanger.notify(notiIdAdd++, noti);
                stopSelf();
            }
            course.renew_course();
            handler.postDelayed(this, 10000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        notimanger = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "ChannelName", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("ChannelDescription");
            notimanger.createNotificationChannel(channel);
        }

        Notification noti = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setContentTitle("選課提醒")
                .setContentText("服務開始")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("服務開始")
                .build();
        startForeground(notiId, noti);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (course != null) {
            handler.removeCallbacks(runnable);
            course.setCourse_num(intent.getStringExtra("courseNum"));
            course.renew_course();
            counter = 0;
        }
        else {
            if (intent.hasExtra("courseNum")){
                course = new Cheakcourse(intent.getStringExtra("courseNum"));
            }
        }
        handler.postDelayed(runnable,1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        stopForeground(true);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
