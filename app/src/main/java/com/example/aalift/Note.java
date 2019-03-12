package com.example.aalift;

public class Note {
    private int id;

    private String title;

    private String description;

    public Note(String title) {
        this.title = title;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }



}