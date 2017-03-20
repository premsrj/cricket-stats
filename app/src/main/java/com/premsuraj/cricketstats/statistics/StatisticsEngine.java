package com.premsuraj.cricketstats.statistics;

import android.util.Pair;

import com.google.firebase.crash.FirebaseCrash;
import com.premsuraj.cricketstats.InningsData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.premsuraj.cricketstats.statistics.InningsHelper.isInnings;
import static com.premsuraj.cricketstats.statistics.InningsHelper.isNullOrEmpty;

/**
 * Created by Premsuraj
 */

public class StatisticsEngine {
    int totalScore, ballsFaced;
    Pair<Integer, Boolean> highScoreAndOut;
    int fifties, hundreds;
    int numOuts, notOuts;
    int totalFours, totalSixes;
    int teamScore, runsWithTeamScore;
    int catchesTaken, runoutsMade;
    Set<String> opposingTeams;
    HashMap<String, Integer> outTypes;


    public void process(List<InningsData> inningsDatas) {
        totalScore = totalFours = totalSixes = numOuts = notOuts = ballsFaced =
                fifties = hundreds = teamScore = runsWithTeamScore = catchesTaken = runoutsMade = 0;
        opposingTeams = new HashSet<String>();
        outTypes = new HashMap<>();
        highScoreAndOut = Pair.create(0, false);

        for (InningsData data : inningsDatas) {
            if (isInnings(data)) {
                updateTotals(data);
                updateMilestones(data);
                updateOuts(data);
                updateTeamScoreData(data);
                updateHighScore(data);
                updateOpposingTeams(data);
            }
            updateFieldingInfo(data);
        }
    }

    private void updateFieldingInfo(InningsData data) {
        if (!isNullOrEmpty(data.getFieldingCatches()))
            catchesTaken += getInt(data.getFieldingCatches());

        if (!isNullOrEmpty(data.getFieldingRunouts()))
            runoutsMade += getInt(data.getFieldingRunouts());
    }

    private void updateOpposingTeams(InningsData data) {
        if (!isNullOrEmpty(data.getOpposingTeam())) {
            opposingTeams.add(data.getOpposingTeam());
        }
    }

    private void updateHighScore(InningsData data) {
        if (getInt(data.getRunsTaken()) > highScoreAndOut.first) {
            highScoreAndOut = Pair.create(getInt(data.getRunsTaken()), isOut(data));
        } else if (getInt(data.getRunsTaken()) == highScoreAndOut.first) {
            if (!isOut(data) && highScoreAndOut.second) {
                highScoreAndOut = Pair.create(getInt(data.getRunsTaken()), isOut(data));
            }
        }
    }

    private void updateTeamScoreData(InningsData data) {
        if (!isNullOrEmpty(data.getTeamScore())) {
            teamScore += getInt(data.getTeamScore());
            runsWithTeamScore += getInt(data.getRunsTaken());
        }
    }

    private void updateOuts(InningsData data) {
        if (isOut(data)) {
            numOuts++;
        } else {
            notOuts++;
        }

        Integer outCount = outTypes.get(data.getOut());
        if (outCount == null) outCount = 0;
        outCount++;
        outTypes.put(data.getOut(), outCount);
    }

    private void updateMilestones(InningsData data) {
        if (getInt(data.getRunsTaken()) >= 100)
            hundreds++;
        else if (getInt(data.getRunsTaken()) >= 50)
            fifties++;
    }

    private void updateTotals(InningsData data) {
        totalScore += getInt(data.getRunsTaken());
        ballsFaced += getInt(data.getBallsFaced());
        totalFours += getInt(data.getFours());
        totalSixes += getInt(data.getSixes());
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

    public String getFifites() {
        return "" + fifties;
    }

    public String getHundreds() {
        return "" + hundreds;
    }

    public String getCatchesTaken() {
        return "" + catchesTaken;
    }

    public String getRunoutsMade() {
        return "" + runoutsMade;
    }
}