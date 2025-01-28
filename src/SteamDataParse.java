import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

public class SteamDataParse {
    static void jsonParser(String jsonResponse, String appId, Game game) {
        JsonFactory factory = new JsonFactory();
        boolean nameSet=false;

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
                        if ("name".equals(dataFieldName) && nameSet==false) {
                            game.name = parser.getValueAsString();
                            nameSet=true;
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
                            int i = 0;
                            while(parser.nextToken() != JsonToken.END_ARRAY){
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
                            System.out.println("    " + game.developers[i]);
                        }
                    return;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
