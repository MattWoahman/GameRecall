package src;

class SteamApp {
    public String name;
    public String short_description;
    public String[] developers = new String[10];
    public ReleaseDate release_date;

    static class ReleaseDate {
        String date;
        boolean coming_soon;
    }
}