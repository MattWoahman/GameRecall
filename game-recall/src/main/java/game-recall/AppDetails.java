package java;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Dictionary;

@RestController
public class AppDetails {

    @GetMapping("/appdetails")
    public Object getAppDetails(String appId) {
        //String apiKey = System.getenv("APIKEY");
        // String appId = "10";
        // MATT: this is your Main.java
        String url = "https://store.steampowered.com/api/appdetails?appids=" + appId;
        HttpClient client = HttpClient.newHttpClient();
        SteamApp game = new SteamApp();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        Object result = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> SteamDataParse.jsonParser(responseBody, appId, game))
                .join();

        return result;
    }
}
