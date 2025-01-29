import java.util.ArrayList;

class SteamAccount {
    public String accName;
    public int game_count;
    //public ArrayList<Game> games = new ArrayList<Game>();
    public Game[] games = new Game[1000];

    static class Game {
        int appid;
        String name;
        int playtime_forever;
        int rtime_last_played;
    }
}
