package com.s23010920.studypal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.CalendarView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {
    public static List<ReminderModel> reminders = new ArrayList<>();
    CalendarView calendarView;
    RecyclerView recyclerView;
    FloatingActionButton createRem;
    long selectedDateMillis;
    ReminderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        calendarView = findViewById(R.id.calendarView);
        recyclerView = findViewById(R.id.recyclerView);
        createRem = findViewById(R.id.create_reminder);

        selectedDateMillis = calendarView.getDate();
        adapter = new ReminderAdapter(getRemindersForDate(selectedDateMillis), reminder -> {
            ReminderActivity.cancelAlarm(this, reminder);
            reminders.remove(reminder);
            adapter.notifyDataSetChanged();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, dayOfMonth, 0, 0, 0);
            selectedDateMillis = cal.getTimeInMillis();
            adapter = new ReminderAdapter(getRemindersForDate(selectedDateMillis), reminder -> {
                ReminderActivity.cancelAlarm(this, reminder);
                reminders.remove(reminder);
                adapter.notifyDataSetChanged();
            });
            recyclerView.setAdapter(adapter);
        });

        createRem.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReminderActivity.class);
            intent.putExtra("date", selectedDateMillis);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new ReminderAdapter(getRemindersForDate(selectedDateMillis), reminder -> {
            ReminderActivity.cancelAlarm(this, reminder);
            reminders.remove(reminder);
            adapter.notifyDataSetChanged();
        });
        recyclerView.setAdapter(adapter);
    }

    private List<ReminderModel> getRemindersForDate(long dateMillis) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(dateMillis);
        List<ReminderModel> list = new ArrayList<>();
        for (ReminderModel r : reminders) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTimeInMillis(r.dateTimeMillis);
            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                list.add(r);
            }
        }
        return list;
    }
}
