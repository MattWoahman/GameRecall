package com.logbusters.GameRecall;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


@Service
public class SteamService {

    private static WebClient  webClient = null;;
            ;
    private final String apiKey = "not pulling a Garrett";

    public SteamService(WebClient.Builder webClientBuilder) {
        webClient = WebClient.create("https://store.steampowered.com");
    }

    public static String getGameInfo(String gameName) throws FileNotFoundException {
        String appId = searchForString(gameName);
        if (appId == null) {
            return "null";
        }
        System.out.println(appId);
        String url = "/api/appdetails?appids=" + appId;
        Mono<String> response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);

        System.out.println(appId);

        System.out.println(" ");
        System.out.println(response.block());
        System.out.println(" ");
        return response.block();
    }

  /*  public Optional<String> getOwnedGames(String userId) {
        String url = "https://api.steampowered.com/IPlayerService/GetOwnedGames/v1/?key=" + apiKey + "&steamid=" + userId + "&include_appinfo=true";
        return Optional.ofNullable(restTemplate.getForObject(url, String.class));
    }
*/

    private static String searchForString(String targetString) throws FileNotFoundException {
        JsonElement fileJson;
        String appId = "null";
        try (FileReader file = new FileReader("steam.txt")) {
            fileJson = JsonParser.parseReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JsonObject jsonObject = fileJson.getAsJsonObject();
        JsonObject appList = jsonObject.getAsJsonObject("applist");
        JsonArray appsArray = appList.getAsJsonArray("apps");

        for (JsonElement appElement : appsArray){
            JsonObject appObject = appElement.getAsJsonObject();
            String appName = appObject.get("name").getAsString();
            if (appName.equalsIgnoreCase(targetString)){
                appId = appObject.get("appid").getAsString();
                break;
            }
        }
        return appId;
    }

}

