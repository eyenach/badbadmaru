package com.example.eyenach.badbadmaru.model;

public class Menu {

    String imgPath;
    String name;
    String desc;
    String type;
    String time;
    String ingre;
    String writer;

    public Menu() {}

    public Menu(String imgPath, String name, String desc, String type, String time, String ingre, String writer) {
        this.imgPath = imgPath;
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.time = time;
        this.ingre = ingre;
        this.writer = writer;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
