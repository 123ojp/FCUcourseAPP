package com.example.o123ojp.appprojet;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class CheckService extends Service {
    final int notiId = 10000;
    int c = 0;
    NotificationManager notimanger;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Notification noti = new NotificationCompat.Builder(getApplicationContext(), "default")
                    .setContentTitle("c")
                    .setContentText(Integer.toString(c))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            c++;
            notimanger.notify(notiId, noti);
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "onCreate......", Toast.LENGTH_SHORT).show();
        notimanger = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "ChannelName", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("ChannelDescription");
            notimanger.createNotificationChannel(channel);
        }

        Notification noti = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setContentTitle("c")
                .setContentText(Integer.toString(c))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Service start")
                .build();
        startForeground(notiId, noti);
        handler.postDelayed(runnable,1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "onStartCommand......", Toast.LENGTH_SHORT).show();
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
