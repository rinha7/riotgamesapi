package org.cnu.realcoding.riotgamesapi.repository;

import org.cnu.realcoding.riotgamesapi.domain.LeaguePositionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


import java.lang.reflect.Array;
import java.util.*;

@Repository
public class CurrentSummonerScoreRepo {

    @Autowired
    MongoTemplate mongoTemplate;

    // This private method makes HashSet to LeaguePositionDTO
    // because API get Set data from web site
    private LeaguePositionDTO makeToDTO(LinkedHashMap leaguePositionDTO){

        LeaguePositionDTO insertdata = new LeaguePositionDTO(); // new Object to insert Data

        insertdata.setFreshBlood((Boolean)   leaguePositionDTO.get("freshBlood"));
        insertdata.setSummonerName((String)  leaguePositionDTO.get("summonerName"));
        insertdata.setHotStreak((Boolean)    leaguePositionDTO.get("hotStreak"));
        insertdata.setInactive((Boolean)     leaguePositionDTO.get("inactive"));
        insertdata.setLeagueId((String)      leaguePositionDTO.get("leagueId"));
        insertdata.setLeagueName((String)    leaguePositionDTO.get("leagueName"));
        insertdata.setLeaguePoints((Integer) leaguePositionDTO.get("leaguePoints"));
        insertdata.setLosses((Integer)       leaguePositionDTO.get("losses"));
        insertdata.setPosition((String)      leaguePositionDTO.get("position"));
        insertdata.setRank((String)          leaguePositionDTO.get("rank"));
        insertdata.setSummonerId((String)    leaguePositionDTO.get("summonerId"));
        insertdata.setTier((String)          leaguePositionDTO.get("tier"));
        insertdata.setVeteran((Boolean)      leaguePositionDTO.get("veteran"));
        insertdata.setWins((Integer)         leaguePositionDTO.get("wins"));
        insertdata.setQueueType((String)     leaguePositionDTO.get("queueType"));

        return insertdata;
    }

    //Do insert SummonerScore to MongoDB
    public void insertCurrentSummonerScore(Set<LeaguePositionDTO> leagueDTOdata) {
        List<LinkedHashMap> data = new LinkedList(leagueDTOdata);
        for (int i = 0; i < data.size(); i++) {
            LeaguePositionDTO insertdata = makeToDTO(data.get(i));
            mongoTemplate.insert(insertdata);
        }
    }

//    Update Method in MongoDB
//     need to change it
    public void updateCurrentSummonerScore(Set<LeaguePositionDTO> leagueDTOdata) {
        LeaguePositionDTO leaguePositionDTO = makeToDTO(leagueDTOdata);
        Iterator iterator = leagueDTOdata.iterator();
        String updateName;
        int idx=0;
        while(iterator.hasNext()){
            LeaguePositionDTO leaguePositionDTO = makeToDTO(iterator.next());
            LeaguePositionDTO chageObj = leaguePositionDTO.getArray().get(idx);
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
    public LeaguePositionDTO findLeaguePostionDTO(String summonerName){
        Criteria criteria = new Criteria("summonerName");
        criteria.is(summonerName);

        Query query = new Query(criteria);

        LeaguePositionDTO returnDTO = mongoTemplate.findOne(query, LeaguePositionDTO.class);


        return returnDTO;
    }

    // find All Summoner's Name and return List of All summoner's name
    public LinkedList<String> findAllEncryptedSummonerQueue(){
        LinkedList<String> nameList = new LinkedList<>();

        List<LeaguePositionDTO> dataList = mongoTemplate.findAll(LeaguePositionDTO.class);

        for(int idx=0;idx<dataList.size();idx++){
            nameList.set(idx,dataList.get(idx).getSummonerName());
        }

        return nameList;

    }

}
