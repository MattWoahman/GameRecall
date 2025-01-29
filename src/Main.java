package src;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        String apiKey = System.getenv("APIKEY");
        String appId = "10";
        String url = "https://store.steampowered.com/api/appdetails?appids=" + appId;
        HttpClient client = HttpClient.newHttpClient();
        SteamApp game = new SteamApp();

        // info needed for getting what games an accoutn has
        String apiKeyH = "FD4C9BC4DE59D04EB6D81DCBEDC96CB0"; // hard coded my api key when i was doing this
        String userId = "76561198080896495"; // garrett's steam id
        String urlAcc = "https://api.steampowered.com/IPlayerService/GetOwnedGames/v1/?key=" + apiKeyH + "&steamid=" + userId + "&include_appinfo=true";

        HttpRequest requestAcc = HttpRequest.newBuilder()
                .uri(URI.create(urlAcc))
                .GET()
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> SteamDataParse.jsonParser(responseBody, appId, game))
                .join();

        System.out.println();
        client.sendAsync(requestAcc, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> SteamDataParse.jsonParserAccount(responseBody))
                .join();
    }
}

