package org.cnu.realcoding.riotgamesapi.api;

import org.cnu.realcoding.riotgamesapi.domain.LeaguePositionDTO;
import org.cnu.realcoding.riotgamesapi.domain.SummonerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Service
public class RiotGamesApiClient {

    @Autowired
    private RestTemplate restTemplate;
    private static String APIKEY = "RGAPI-c6f6a250-319e-4e17-9e5a-ed544393ea80";
    private String requestSummonerDTO = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/{summonerName}?api_key="+APIKEY;
    private String requestLeaguePositionDTO = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/{encryptedSummonerId}?api_key="+APIKEY;

    public SummonerDTO getCurrentSummonerDTO(String summonerName) {
        return restTemplate.exchange(requestSummonerDTO, HttpMethod.GET, null, SummonerDTO.class, summonerName).getBody();
    }

    public Set<LeaguePositionDTO> getCurrentLeaguePositionDTO(String encryptedSummonerId) {
        return restTemplate.exchange(requestLeaguePositionDTO, HttpMethod.GET, null, Set.class, encryptedSummonerId).getBody();
    }
}
