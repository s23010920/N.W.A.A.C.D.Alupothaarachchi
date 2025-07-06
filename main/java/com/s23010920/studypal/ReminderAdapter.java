package com.s23010920.studypal;


import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private List<ReminderModel> reminders;

    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {

        void onDelete(ReminderModel reminder);

    }

    public ReminderAdapter(List<ReminderModel> reminders, OnDeleteClickListener listener) {

        this.reminders = reminders;

        this.onDeleteClickListener = listener;

    }

    @NonNull

    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);

        return new ViewHolder(v);

    }

    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ReminderModel reminder = reminders.get(position);

        holder.subject.setText(reminder.subject);

        holder.note.setText(DateFormat.getDateTimeInstance().format(reminder.dateTimeMillis) + " - " + reminder.note);

        holder.itemView.setOnLongClickListener(v -> {

            onDeleteClickListener.onDelete(reminder);

            return true;

        });

    }

    @Override

    public int getItemCount() {

        return reminders.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView subject, note;

        ViewHolder(View v) {

            super(v);

            subject = v.findViewById(android.R.id.text1);

            note = v.findViewById(android.R.id.text2);

        }

    }

}

