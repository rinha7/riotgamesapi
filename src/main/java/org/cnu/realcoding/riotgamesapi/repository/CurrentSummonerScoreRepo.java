package org.cnu.realcoding.riotgamesapi.repository;

import org.cnu.realcoding.riotgamesapi.domain.LeaguePositionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.LinkedList;
import java.util.List;

@Repository
public class CurrentSummonerScoreRepo {

    @Autowired
    MongoTemplate mongoTemplate;

    //Do insert SummonerScore to MongoDB
    public void insertCurrentSummonerScore(LeaguePositionDTO leaguePositionDTO) {
        mongoTemplate.insert(leaguePositionDTO);
    }

    //Update Method in MongoDB
    public void updateCurrentSummonerScore(LeaguePositionDTO leaguePositionDTO) {
        String updateName;
        for (int idx = 0; idx < leaguePositionDTO.getArray().size(); idx++) {
            LeaguePositionDTO.LeaguePosition chageObj = leaguePositionDTO.getArray().get(idx);
            updateName = chageObj.getSummonerName();

            Query query = new Query();
            query.addCriteria(Criteria.where("summonerId").is(updateName));
            Update update = new Update();
            update.set("tier",chageObj.getTier())
                    .set("wins",chageObj.getWins())
                    .set("losses",chageObj.getLosses())
                    .set("rank",chageObj.getRank())
                    .set("leagueName",chageObj.getLeagueName())
                    .set("leaguePoints",chageObj.getLeaguePoints());

            mongoTemplate.updateFirst(query,update,LeaguePositionDTO.class);
        }
    }

    // return LeaguePositionDTO that found by summoner's name
    public LeaguePositionDTO.LeaguePosition findLeaguePostionDTO(String summonerName){
        Criteria criteria = new Criteria("summonerName");
        criteria.is(summonerName);

        Query query = new Query(criteria);

        LeaguePositionDTO.LeaguePosition returnDTO = mongoTemplate.findOne(query, LeaguePositionDTO.LeaguePosition.class);


        return returnDTO;
    }

    // find All Summoner's Name and return List of All summoner's name
    public LinkedList<String> findAllEncryptedSummonerQueue(){
        LinkedList<String> nameList = new LinkedList<>();

        List<LeaguePositionDTO> dataList = mongoTemplate.findAll(LeaguePositionDTO.class);

        for(int idx=0;idx<dataList.size();idx++){
            nameList.set(idx,dataList.get(idx).getArray().get(0).getSummonerName());
        }

        return nameList;

    }

}
