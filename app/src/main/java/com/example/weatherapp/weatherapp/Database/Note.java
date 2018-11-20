package com.example.weatherapp.weatherapp.Database;

public class Note {
    private long id;
    private String note;
    private String title;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    // это нужно для ArrayAdapter, чтобы правильно отображался текст
    public String toString() {
        return note;
    }
}
