
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        String apiKey = System.getenv("APIKEY");

        String appId = "1173800";
        String url = "https://store.steampowered.com/api/appdetails?appids=" + appId;
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> {
                    String jsonResponse = responseBody;
                    System.out.println(jsonResponse);
                })
                .join();
        // System.out.println("Ben has afk'd in " + gameInput + " for " + (random.nextInt(10000)+1000) + " hours!");
    }
}

