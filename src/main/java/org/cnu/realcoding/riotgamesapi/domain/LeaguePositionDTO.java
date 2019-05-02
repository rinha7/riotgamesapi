package org.cnu.realcoding.riotgamesapi.domain;

import lombok.Data;

@Data
public class LeaguePositionDTO {
    private String tier;
    private String summonerName;
    private boolean hotStreak;
    private MiniSeriesDTO miniSeries;
    private int wins;
    private boolean veteran;
    private int losses;
    private String rank;
    private String leagueName;
    private boolean inactive;
    private boolean freshBlood;
    private String position;
    private String leagueId;
    private String queueType;
    private String summonerId;
    private int leaguePoints;

    @Data
    public class MiniSeriesDTO{
        private String progress;
        private int losses;
        private int target;
        private int wins;
    }
}
