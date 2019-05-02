package org.cnu.realcoding.riotgamesapi.controller;

import org.cnu.realcoding.riotgamesapi.domain.LeaguePositionDTO;
import org.cnu.realcoding.riotgamesapi.service.LeaguePositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class RiotGamesApiController {
    @Autowired
    private LeaguePositionService leaguePositionService;

    @GetMapping("/summoner")
    public Set<LeaguePositionDTO> getLeaguePositionDTO(@RequestParam String summonerName){
        return leaguePositionService.getLeaguePosition(summonerName);
    }
}
