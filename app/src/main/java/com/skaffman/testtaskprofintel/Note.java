package com.skaffman.testtaskprofintel;

public class Note {
    private int id;
    private String title;
    private String text;
    private String date;
    private int color;

    public Note(int id, String title, String text, String date, int color) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
