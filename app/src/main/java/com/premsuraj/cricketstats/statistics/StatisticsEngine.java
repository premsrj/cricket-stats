package com.premsuraj.cricketstats.statistics;

import android.util.Pair;

import com.google.firebase.crash.FirebaseCrash;
import com.premsuraj.cricketstats.InningsData;

import java.util.List;

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


    public void process(List<InningsData> inningsDatas) {
        totalScore = totalFours = totalSixes = numOuts = notOuts = ballsFaced = 0;
        teamScore = runsWithTeamScore = 0;
        highScoreAndOut = Pair.create(0, false);
        for (InningsData data : inningsDatas) {
            if (isInnings(data)) {
                totalScore += getInt(data.runsTaken);
                ballsFaced += getInt(data.ballsFaced);
                totalFours += getInt(data.fours);
                totalSixes += getInt(data.sixes);

                if (isOut(data)) {
                    numOuts++;
                } else {
                    notOuts++;
                }

                if (!isNullOrEmpty(data.teamScore)) {
                    teamScore += getInt(data.teamScore);
                    runsWithTeamScore += getInt(data.runsTaken);
                }

                if (getInt(data.runsTaken) > highScoreAndOut.first) {
                    highScoreAndOut = Pair.create(getInt(data.runsTaken), isOut(data));
                } else if (getInt(data.runsTaken) == highScoreAndOut.first) {
                    if (!isOut(data) && highScoreAndOut.second) {
                        highScoreAndOut = Pair.create(getInt(data.runsTaken), isOut(data));
                    }
                }
            }
        }
    }

    private boolean isOut(InningsData data) {
        if (isNullOrEmpty(data.out))
            return false;
        if (data.out.equalsIgnoreCase("Not Out"))
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
}