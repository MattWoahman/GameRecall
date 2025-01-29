import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SteamDataParse {
    static void jsonParser(String jsonResponse) {

        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        String id = jsonObject.keySet().iterator().next();

        JsonObject appData = jsonObject.getAsJsonObject(id).getAsJsonObject("data");
        SteamApp app = new Gson().fromJson(appData, SteamApp.class);
        jsonObject.getAsJsonObject("release_date");
        jsonObject.getAsJsonObject("categories");
        jsonObject.getAsJsonObject("genres");

        System.out.println("Name: " + app.name);
        System.out.println("Description: " + app.short_description);
        System.out.println("Release Date: " + app.release_date.date);
        System.out.println("Developers: ");
        for (int i=0; i < app.developers.length; i++){
            System.out.println("     " + app.developers[i]);
        }
        System.out.println("Publishers: ");
        for (int i=0; i < app.publishers.length; i++){
            System.out.println("     " + app.publishers[i]);
        }
        System.out.println("Categories: ");
        for (int i=0; i < app.categories.length; i++){
            System.out.println("    " + app.categories[i].description);
        }
        System.out.println("Genres: ");
        for (int i=0; i < app.genres.length; i++){
            System.out.println("    " + app.genres[i].description);
        }
    }
}
