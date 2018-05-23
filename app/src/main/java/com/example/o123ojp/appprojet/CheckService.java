package com.example.o123ojp.appprojet;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class CheckService extends Service {
    final int notiId = 10000;
    Cheakcourse course = null;
    NotificationManager notimanger;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Notification noti = new NotificationCompat.Builder(getApplicationContext(), "default")
                    .setContentTitle(course.getSub_name())
                    .setContentText("實收名額/開放名額:" + course.getReal_quota() + "/" + course.getOpen_quota())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            notimanger.notify(notiId, noti);
            if (course.whether_if_OKadd()) {
                noti = new NotificationCompat.Builder(getApplicationContext(), "default")
                        .setContentTitle(course.getSub_name())
                        .setContentText("目前可以加選 餘額:" + Float.toString(Float.parseFloat(course.getOpen_quota()) - Float.parseFloat(course.getReal_quota())))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000})
                        .build();
                notimanger.notify(notiId + 1, noti);
                stopSelf();
            }
            course.renew_course();
            handler.postDelayed(this, 5000);
        }
    };;

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "onCreate......", Toast.LENGTH_SHORT).show();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        notimanger = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "ChannelName", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("ChannelDescription");
            notimanger.createNotificationChannel(channel);
        }

        Notification noti = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setContentTitle("選課提醒")
                //.setContentText()
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("服務開始")
                .build();
        startForeground(notiId, noti);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "onStartCommand......", Toast.LENGTH_SHORT).show();
        if (course != null) {
            handler.removeCallbacks(runnable);
            course.setCourse_num(intent.getStringExtra("courseNum"));
            course.renew_course();
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
        Toast.makeText(getApplicationContext(), "onDestroy......", Toast.LENGTH_SHORT).show();
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
