package com.example.eyenach.badbadmaru.favorite;

public class Favorite {

    String menu;
    String writer;

    public Favorite(String menu, String writer) {
        this.menu = menu;
        this.writer = writer;
    }

    public String getType() {
        return writer;
    }

    public String getMenu() {
        return menu;
    }
}
