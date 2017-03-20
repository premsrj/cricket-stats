package com.premsuraj.cricketstats.statistics;

import android.util.Pair;

import com.google.firebase.crash.FirebaseCrash;
import com.premsuraj.cricketstats.InningsData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.premsuraj.cricketstats.statistics.InningsHelper.isInnings;
import static com.premsuraj.cricketstats.statistics.InningsHelper.isNullOrEmpty;

/**
 * Created by Premsuraj
 */

public class StatisticsEngine {
    int totalScore;
    Pair<Integer, Boolean> highScoreAndOut;
    int ballsFaced;
    int numOuts;
    int notOuts;
    int totalFours;
    int totalSixes;
    int teamScore, runsWithTeamScore;
    Set<String> opposingTeams;


    public void process(List<InningsData> inningsDatas) {
        totalScore = totalFours = totalSixes = numOuts = notOuts = ballsFaced = 0;
        teamScore = runsWithTeamScore = 0;
        opposingTeams = new HashSet<String>();
        highScoreAndOut = Pair.create(0, false);
        for (InningsData data : inningsDatas) {
            if (isInnings(data)) {
                totalScore += getInt(data.getRunsTaken());
                ballsFaced += getInt(data.getBallsFaced());
                totalFours += getInt(data.getFours());
                totalSixes += getInt(data.getSixes());

                if (isOut(data)) {
                    numOuts++;
                } else {
                    notOuts++;
                }

                if (!isNullOrEmpty(data.getTeamScore())) {
                    teamScore += getInt(data.getTeamScore());
                    runsWithTeamScore += getInt(data.getRunsTaken());
                }

                if (getInt(data.getRunsTaken()) > highScoreAndOut.first) {
                    highScoreAndOut = Pair.create(getInt(data.getRunsTaken()), isOut(data));
                } else if (getInt(data.getRunsTaken()) == highScoreAndOut.first) {
                    if (!isOut(data) && highScoreAndOut.second) {
                        highScoreAndOut = Pair.create(getInt(data.getRunsTaken()), isOut(data));
                    }
                }

                if (!isNullOrEmpty(data.getOpposingTeam())) {
                    opposingTeams.add(data.getOpposingTeam());
                }
            }
        }
    }

    private boolean isOut(InningsData data) {
        if (isNullOrEmpty(data.getOut()))
            return false;
        if (data.getOut().equalsIgnoreCase("Not Out"))
            return false;
        else
            return true;
    }

    private int getInt(String runsTaken) {
        try {
            return Integer.parseInt(runsTaken);
        } catch (Exception ex) {
            FirebaseCrash.report(ex);
            return 0;
        }
    }

    public String getHighScore() {
        if (highScoreAndOut.second)
            return highScoreAndOut.first + "";
        else
            return highScoreAndOut.first + "*";
    }

    public String getAverage() {
        return String.format("%.2f", ((float) totalScore / ((float) numOuts)));
    }

    public String getStrikeRate() {
        return String.format("%.2f", ((((float) totalScore) * 100.0f) / ((float) ballsFaced)));
    }

    public String getTeamScorePercent() {
        return String.format("%.2f", (((float) runsWithTeamScore) * 100.0f) / ((float) teamScore));
    }

    public String getFours() {
        return "" + totalFours;
    }

    public String getSixes() {
        return "" + totalSixes;
    }

    public Set<String> getOpposingTeams() {
        return opposingTeams;
    }
}