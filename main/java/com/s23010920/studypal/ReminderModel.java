package com.s23010920.studypal;


public class ReminderModel {
    public long id;
    public long dateTimeMillis;
    public String subject;
    public String note;

    public ReminderModel(long id, long dateTimeMillis, String subject, String note) {
        this.id = id;
        this.dateTimeMillis = dateTimeMillis;
        this.subject = subject;
        this.note = note;
    }
}