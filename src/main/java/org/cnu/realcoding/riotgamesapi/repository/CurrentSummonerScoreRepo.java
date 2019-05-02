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
    private LeaguePositionDTO makeToDTO(Set<LeaguePositionDTO> leaguePositionDTO){
        List<LinkedHashMap> list = new ArrayList(leaguePositionDTO);

        LeaguePositionDTO insertdata = new LeaguePositionDTO(); // new Object to insert Data

        insertdata.setFreshBlood((Boolean) list.get(0).get("freshBlood"));
        insertdata.setSummonerName((String) list.get(0).get("summonerName"));
        insertdata.setHotStreak((Boolean) list.get(0).get("hotStreak"));
        insertdata.setInactive((Boolean) list.get(0).get("inactive"));
        insertdata.setLeagueId((String) list.get(0).get("leagueId"));
        insertdata.setLeagueName((String) list.get(0).get("leagueName"));
        insertdata.setLeaguePoints((Integer) list.get(0).get("leaguePoints"));
        insertdata.setLosses((Integer) list.get(0).get("losses"));
        insertdata.setPosition((String) list.get(0).get("position"));
        insertdata.setRank((String) list.get(0).get("rank"));
        insertdata.setSummonerId((String) list.get(0).get("summonerId"));
        insertdata.setTier((String) list.get(0).get("tier"));
        insertdata.setVeteran((Boolean) list.get(0).get("veteran"));
        insertdata.setWins((Integer) list.get(0).get("wins"));
        insertdata.setQueueType((String) list.get(0).get("queueType"));

        return insertdata;
    }

    //Do insert SummonerScore to MongoDB
    public void insertCurrentSummonerScore(Set<LeaguePositionDTO> leagueDTOdata) {
        LeaguePositionDTO insertdata = makeToDTO(leagueDTOdata);
        mongoTemplate.insert(insertdata);
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
