package com.logbusters.GameRecall;

class SteamApp {
    public String name;
    public String imageSrc;
    public String short_description;
    public String[] developers = new String[10];
    public ReleaseDate release_date;
    public String[] publishers = new String[10];
    public Categories[] categories = new Categories[10];
    public Genres[] genres = new Genres[10];
    static class ReleaseDate {
        String date;
        boolean coming_soon;
    }
    static class Categories {
        int id;
        String description;
    }
    static class Genres {
        String id;
        String description;
    }
    static class Apps {
        static String id;
        static String name;
    }
}