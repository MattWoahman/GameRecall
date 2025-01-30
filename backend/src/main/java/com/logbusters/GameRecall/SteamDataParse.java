package com.logbusters.GameRecall;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SteamDataParse {
   static SteamApp jsonParser(String jsonResponse) {


       JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
       String id = jsonObject.keySet().iterator().next();

       JsonObject appData = jsonObject.getAsJsonObject(id).getAsJsonObject("data");
       SteamApp app = new Gson().fromJson(appData, SteamApp.class);
       app.name = appData.get("name").getAsString();
       app.short_description = appData.get("short_description").getAsString();

       JsonObject releaseDateObj = appData.getAsJsonObject("release_date");
       if (releaseDateObj != null) {
           app.release_date = new SteamApp.ReleaseDate();
           app.release_date.date = releaseDateObj.get("date").getAsString();
           app.release_date.coming_soon = releaseDateObj.get("coming_soon").getAsBoolean();
       }

       JsonArray categoriesArray = appData.getAsJsonArray("categories");
       if (categoriesArray != null) {
           app.categories = new SteamApp.Categories[categoriesArray.size()];
           for (int i = 0; i < categoriesArray.size(); i++) {
               JsonObject categoryObj = categoriesArray.get(i).getAsJsonObject();
               SteamApp.Categories category = new SteamApp.Categories();
               category.id = categoryObj.get("id").getAsInt();
               category.description = categoryObj.get("description").getAsString();
               app.categories[i] = category;
           }
       }

       JsonArray genresArray = appData.getAsJsonArray("genres");
       if (genresArray != null) {
           app.genres = new SteamApp.Genres[genresArray.size()];
           for (int i = 0; i < genresArray.size(); i++) {
               JsonObject genreObj = genresArray.get(i).getAsJsonObject();
               SteamApp.Genres genre = new SteamApp.Genres();
               genre.id = genreObj.get("id").getAsString();
               genre.description = genreObj.get("description").getAsString();
               app.genres[i] = genre;
           }
       }
       return app;
   }
}
