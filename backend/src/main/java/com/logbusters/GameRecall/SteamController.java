package com.logbusters.GameRecall;
import com.google.gson.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

import static com.logbusters.GameRecall.SteamDataParse.jsonParser;

@RestController
@RequestMapping("/steam")
public class SteamController {

    private final SteamService steamService;

    public SteamController(SteamService steamService) {
        this.steamService = steamService;
    }

    @GetMapping("/game")
    public SteamApp getGameInfo(@RequestParam String name) throws FileNotFoundException {

        SteamApp app;
        String gameInfo = SteamService.getGameInfo(name);
        System.out.println(gameInfo);
        app = jsonParser(gameInfo);
        return app;
    }
   /* @GetMapping("/owned-games")
    public Optional<String> getOwnedGames(@RequestParam String userId) {
        return steamService.getOwnedGames(userId);
    }
    */
}
