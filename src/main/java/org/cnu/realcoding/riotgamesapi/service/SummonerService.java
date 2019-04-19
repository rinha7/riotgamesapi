package org.cnu.realcoding.riotgamesapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummonerService {
    @Autowired
    private RiotGamesApiClient riotGamesApiClient;

    public String getEncryptedSummonerId(String summonerName){
        return this.riotGamesApiClient.getEncryptedSummonerId(summonerName);
    }
}
