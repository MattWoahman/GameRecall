import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //String apiKey = System.getenv("APIKEY");
        Scanner scanner = new Scanner(System.in);
        System.out.println("What game do you want the info for?");
        String gameName = scanner.nextLine();
        String appId = searchForString(gameName);

        String url = "https://store.steampowered.com/api/appdetails?appids=" + appId;
        HttpClient client = HttpClient.newHttpClient();
        SteamApp game = new SteamApp();
        game.name = gameName;

        // info needed for getting what games an accoutn has
        String apiKeyH = ""; // hard coded my api key when i was doing this
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
                .thenAccept(SteamDataParse::jsonParser)
                .join();

        System.out.println();
        client.sendAsync(requestAcc, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> SteamDataParse.jsonParserAccount(responseBody))
                .join();
    }

    private static String searchForString(String targetString) {
        JsonElement fileJson;
        String appId = null;
        try (FileReader file = new FileReader("steam.txt")) {
            fileJson = JsonParser.parseReader(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JsonObject jsonObject = fileJson.getAsJsonObject();
        JsonObject appList = jsonObject.getAsJsonObject("applist");
        JsonArray appsArray = appList.getAsJsonArray("apps");

        for (JsonElement appElement : appsArray){
            JsonObject appObject = appElement.getAsJsonObject();
            String appName = appObject.get("name").getAsString();
            if (appName.equals(targetString)){
                appId = appObject.get("appid").getAsString();
                break;
            }
        }
        return appId;
    }

}

