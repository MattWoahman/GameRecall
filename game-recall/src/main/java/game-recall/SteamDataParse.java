package java;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class SteamDataParse {
   static void jsonParser(String jsonResponse, String appId, SteamApp game) {
       /*JsonFactory factory = new JsonFactory();
        boolean nameSet=false;
        boolean release=false;

        try (JsonParser parser = factory.createParser(jsonResponse))  {
            while (!parser.isClosed()) {
                JsonToken token = parser.nextToken();
                if (token == null) {
                    break;
                }
                if (token == JsonToken.FIELD_NAME && parser.getCurrentName().equals(appId)) {
                    parser.nextToken();

                    while (!"ratings".equals(parser.getCurrentName())) {
                        parser.nextToken();
                        String dataFieldName = parser.getCurrentName();

                        parser.nextToken();
                        if ("name".equals(dataFieldName) && nameSet == false) {
                            game.name = parser.getValueAsString();
                            nameSet = true;
                        } else if ("short_description".equals(dataFieldName)) {
                            game.short_description = parser.getValueAsString();
                        } else if ("developers".equals(dataFieldName)) {
                            int i = 0;
                            while (!"publishers".equals(parser.getValueAsString())) {
                                game.developers[i] = parser.getValueAsString();
                                parser.nextToken();
                                i++;
                            }
                        } else if ("release_date".equals(dataFieldName) && release == false){
                            parser.nextToken();
                            parser.nextToken();
                            parser.nextToken();
                            game.release_date = parser.getValueAsString();
                            release = true;
                        }
                    }
                    System.out.println("Name: " + game.name);
                    System.out.println("Description: " + game.short_description);
                    System.out.println("Developers: ");
                    for (int i = 0; i < game.developers.length; i++) {
                        if (game.developers[i] != null) {
                            System.out.println("    " + game.developers[i]);
                        }
                    }
                    System.out.println("Release Date: " + game.release_date);
                    return;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
  */
       JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
       String id = jsonObject.keySet().iterator().next();
       JsonObject appData = jsonObject.getAsJsonObject(id).getAsJsonObject("data");
       SteamApp app = new Gson().fromJson(appData, SteamApp.class);
       JsonObject dateObject = jsonObject.getAsJsonObject("release_date");
       System.out.println(app.name);
       System.out.println(app.short_description);
       System.out.println(app.release_date.date);
       for (int i=0; i < app.developers.length; i++){
           System.out.println(app.developers[i]);
       }
   }
}
