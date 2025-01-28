
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        //String apiKey = System.getenv("APIKEY");
        String appId = "39210";
        String url = "https://store.steampowered.com/api/appdetails?appids=" + appId;
        HttpClient client = HttpClient.newHttpClient();
        Game game = new Game();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> SteamDataParse.jsonParser(responseBody, appId, game))
                .join();

        }

}

