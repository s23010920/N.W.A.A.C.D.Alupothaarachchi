package com.s23010920.studypal;

import android.content.Intent;

import android.os.Bundle;

import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        LinearLayout cardClassLocations = findViewById(R.id.cardClassLocations);

        LinearLayout cardSubjects = findViewById(R.id.cardSubjects);

        LinearLayout cardTimeBlocks = findViewById(R.id.cardTimeBlocks);

        LinearLayout cardGoals = findViewById(R.id.cardGoals);

        LinearLayout cardProgress = findViewById(R.id.cardProgress);

        LinearLayout cardSummaries = findViewById(R.id.cardSummaries);

        cardClassLocations.setOnClickListener(v -> {

            // Start Class Locations activity

            startActivity(new Intent(this, MapActivity.class));

        });
        cardSummaries.setOnClickListener(v -> {

            // Start View Summaries activity

            startActivity(new Intent(this, SummaryActivity.class));

        });


        /*

        cardSubjects.setOnClickListener(v -> {

            // Start Add/Edit/Delete Subjects activity

            startActivity(new Intent(this, SubjectsActivity.class));

        });

        cardTimeBlocks.setOnClickListener(v -> {

            // Start Create Time Blocks activity

            startActivity(new Intent(this, TimeBlocksActivity.class));

        });

        cardGoals.setOnClickListener(v -> {

            // Start Set Goals activity

            startActivity(new Intent(this, GoalsActivity.class));

        });

        cardProgress.setOnClickListener(v -> {

            // Start Track Progress activity

            startActivity(new Intent(this, ProgressActivity.class));

        });

        cardSummaries.setOnClickListener(v -> {

            // Start View Summaries activity

            startActivity(new Intent(this, SummariesActivity.class));

        });

        */

    }

}

