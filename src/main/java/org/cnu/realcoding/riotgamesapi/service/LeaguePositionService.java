package org.cnu.realcoding.riotgamesapi.service;

import org.cnu.realcoding.riotgamesapi.api.RiotGamesApiClient;
import org.cnu.realcoding.riotgamesapi.domain.LeaguePositionDTO;
import org.cnu.realcoding.riotgamesapi.repository.CurrentSummonerScoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Set;

@Service
public class LeaguePositionService {
    @Autowired
    private RiotGamesApiClient riotGamesApiClient;
    @Autowired
    private SummonerService summonerService;
    @Autowired
    private CurrentSummonerScoreRepo currentSummonerScoreRepo;

    private LinkedList<String> encryptedSummonerIdQueue = new LinkedList<>();

    public Set<LeaguePositionDTO> getLeaguePosition(String summonerName){
        if(){ //todo Is there summonerName in database?
            //todo Then return database information.
        } else {
            String encryptedSummonerId = summonerService.getEncryptedSummonerId(summonerName);
            Set<LeaguePositionDTO> currentLeaguePositionDTO = riotGamesApiClient.getCurrentLeaguePositionDTO(encryptedSummonerId);
            currentSummonerScoreRepo.insertCurrentSummonerScore(currentLeaguePositionDTO);
            return currentLeaguePositionDTO;
        }
    }

    public void setEncryptedSummonerIdQueue(){
        this.encryptedSummonerIdQueue = currentSummonerScoreRepo.findAllEncryptedSummonerId();
    }

    @Scheduled(initialDelay = 5000L, fixedDelay = 2000L)
    public void updateLeaguePositionPeriodically(){
        if(encryptedSummonerIdQueue.isEmpty()){
            this.setEncryptedSummonerIdQueue();
        }

        String target = encryptedSummonerIdQueue.pop();
        encryptedSummonerIdQueue.add(target);

        Set<LeaguePositionDTO> updatedLeaguePositionDTO = riotGamesApiClient.getCurrentLeaguePositionDTO(target);
        currentSummonerScoreRepo.updateCurrentSummonerScore(updatedLeaguePositionDTO);
    }
}
