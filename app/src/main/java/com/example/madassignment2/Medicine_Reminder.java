package com.example.madassignment2;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class Medicine_Reminder extends AppCompatActivity {

    TimePicker timePicker;
    Button bt_add_reminder;
    EditText et_med;
    int HOUR, MIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_receiver);

        timePicker=findViewById(R.id.timePicker);
        bt_add_reminder=findViewById(R.id.bt_add_reminder);
        et_med=findViewById(R.id.et_med_name);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                HOUR = hourOfDay;
                MIN = minute;
            }
        });

        bt_add_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Medicine_Reminder.this, "Notification Set on " +HOUR+":"+MIN+"!", Toast.LENGTH_SHORT).show();
                setReminder();
                sendNotification();
            }
        });
    }
    private void sendNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Medication Reminders";
            String description = "Its the time to consume "+et_med.getText().toString()+"!";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel  = new NotificationChannel("Notify", name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setReminder() {
        AlarmManager alarmManager  = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Date date = new Date();

        Calendar cal_reminder = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();

        cal_now.setTime(date);
        cal_reminder.setTime(date);

        cal_reminder.set(Calendar.HOUR_OF_DAY, HOUR);
        cal_reminder.set(Calendar.MINUTE, MIN);
        cal_reminder.set(Calendar.SECOND, 0);

        if(cal_reminder.before(cal_now)){
            cal_reminder.add(Calendar.DATE, 1);
        }

        Intent i = new Intent(Medicine_Reminder.this, Reminder_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Medicine_Reminder.this, 0, i, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal_reminder.getTimeInMillis(),pendingIntent);
    }
}