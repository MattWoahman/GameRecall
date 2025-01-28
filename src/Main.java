
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;


public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        String apiKey = System.getenv("APIKEY");
        String appId = "550";
        String url = "https://store.steampowered.com/api/appdetails?appids=" + appId;
        HttpClient client = HttpClient.newHttpClient();
        Game game = new Game();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> {
                    jsonParser(responseBody, appId, game);
                })
                .join();

        }
    private static void jsonParser(String jsonResponse, String appId, Game game) {
        JsonFactory factory = new JsonFactory();

        try (JsonParser parser = factory.createParser(jsonResponse))  {
            while (!parser.isClosed()) {
                JsonToken token = parser.nextToken();
                if (token == null) {
                    break;
                }
                if (token == JsonToken.FIELD_NAME && parser.getCurrentName().equals(appId)){
                    parser.nextToken();

                    while (parser.nextToken() != JsonToken.END_OBJECT){
                        String dataFieldName = parser.getCurrentName();

                        parser.nextToken();
                        if ("name".equals(dataFieldName)) {
                            game.name = parser.getValueAsString();
                        } else if ("short_description".equals(dataFieldName)) {
                            game.description = parser.getValueAsString();
                        }  else if ("pc_requirements".equals(dataFieldName)) {
                            while (parser.nextToken() != JsonToken.END_OBJECT){
                                parser.nextToken();
                            }
                        }  else if ("mac_requirements".equals(dataFieldName)) {
                            while (parser.nextToken() != JsonToken.END_OBJECT){
                                parser.nextToken();
                            }
                        }  else if ("linux_requirements".equals(dataFieldName)) {
                            while (parser.nextToken() != JsonToken.END_OBJECT){
                                parser.nextToken();
                            }
                        }
                        else if ("developers".equals(dataFieldName)) {
                           while(parser.nextToken() != JsonToken.END_ARRAY){
                               int i = 0;
                               game.developers[i] = parser.getValueAsString();
                               i++;
                           }
                        }
                    }
                    System.out.println("Name: " + game.name);
                    System.out.println("Description: " + game.description);
                    System.out.println("Developers: ");
                    for (int i = 0; i < game.developers.length; i++ )
                        if (game.developers[i] != null) {
                            System.out.println(game.developers[i]);
                        }
                    return;
                }
            }
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

