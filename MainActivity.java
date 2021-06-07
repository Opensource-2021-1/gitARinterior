package com.example.alert;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button bt;

    NotificationManager manager;
    NotificationCompat.Builder builder;

    private static String CHANNEL_ID = "ID";
    private static String CHANEL_NAME = "NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bt = findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoti();
            }
        });

    }

    public void showNoti(){
        builder = null;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            );

            builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        }

        //알림창 제목
        builder.setContentTitle("알림");

        //알림창 메시지
        builder.setContentText("알림 메시지");

        //알림창 아이콘
        builder.setSmallIcon(R.drawable.icon);

        Notification notification = builder.build();

        //알림창 실행
        manager.notify(1,notification);
    }

}