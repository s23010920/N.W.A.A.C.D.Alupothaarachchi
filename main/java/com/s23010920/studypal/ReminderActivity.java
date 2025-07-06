package com.s23010920.studypal;



import android.app.AlarmManager;

import android.app.PendingIntent;

import android.app.TimePickerDialog;

import android.content.Context;

import android.content.Intent;

import android.os.Bundle;

import android.widget.Button;

import android.widget.EditText;

import android.widget.TextView;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    EditText editSubject, editNote;

    Button btnPickTime, btnSave;

    TextView tvTime;

    long selectedDateMillis, selectedTimeMillis = -1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reminder); // Ensure this XML exists and has correct IDs

        // Initialize UI components

        editSubject = findViewById(R.id.edit_subject);

        editNote = findViewById(R.id.edit_note);

        btnPickTime = findViewById(R.id.btn_pick_time);

        btnSave = findViewById(R.id.btn_save);

        tvTime = findViewById(R.id.tv_time);

        // Receive selected date from SummaryActivity

        selectedDateMillis = getIntent().getLongExtra("date", System.currentTimeMillis());

        // Time picker dialog

        btnPickTime.setOnClickListener(v -> {

            Calendar c = Calendar.getInstance();

            int hour = c.get(Calendar.HOUR_OF_DAY);

            int minute = c.get(Calendar.MINUTE);

            new TimePickerDialog(this, (view, hourOfDay, minute1) -> {

                Calendar cal = Calendar.getInstance();

                cal.setTimeInMillis(selectedDateMillis);

                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);

                cal.set(Calendar.MINUTE, minute1);

                cal.set(Calendar.SECOND, 0);

                cal.set(Calendar.MILLISECOND, 0);

                selectedTimeMillis = cal.getTimeInMillis();

                tvTime.setText(DateFormat.getTimeInstance().format(cal.getTime()));

            }, hour, minute, true).show();

        });

        // Save reminder

        btnSave.setOnClickListener(v -> {

            String subject = editSubject.getText().toString().trim();

            String note = editNote.getText().toString().trim();

            if (subject.isEmpty() || selectedTimeMillis == -1) {

                Toast.makeText(this, "Please enter subject and pick time", Toast.LENGTH_SHORT).show();

                return;

            }

            long id = System.currentTimeMillis(); // Unique ID

            ReminderModel model = new ReminderModel(id, selectedTimeMillis, subject, note);

            // Add to shared list (update as per your app structure)

            SummaryActivity.reminders.add(model); // Use correct reference to reminders list

            setAlarm(this, model);

            Toast.makeText(this, "Reminder Saved", Toast.LENGTH_SHORT).show();

            finish();

        });

    }

    // Set reminder alarm

    public static void setAlarm(Context context, ReminderModel model) {

        Intent intent = new Intent(context, ReminderReceiver.class);

        intent.putExtra("subject", model.subject);

        intent.putExtra("note", model.note);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(

                context,

                (int) model.id,

                intent,

                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE

        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        if (alarmManager != null) {

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, model.dateTimeMillis, pendingIntent);

        }

    }

    // Cancel alarm if needed

    public static void cancelAlarm(Context context, ReminderModel model) {

        Intent intent = new Intent(context, ReminderReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(

                context,

                (int) model.id,

                intent,

                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE

        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        if (alarmManager != null) {

            alarmManager.cancel(pendingIntent);

        }

    }

}



