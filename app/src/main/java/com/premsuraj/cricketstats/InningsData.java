package com.premsuraj.cricketstats;

import java.io.Serializable;

/**
 * Created by Premsuraj
 */

public class InningsData implements Serializable {
    public String opposingTeam;
    public String date;
    public String runsTaken;
    public String ballsFaced;
    public String teamScore;
    public String fours;
    public String sixes;
    public String out;
    public String fieldingCatches;
    public String fieldingRunouts;

    public String getOpposingTeam() {
        return opposingTeam;
    }

    public void setOpposingTeam(String opposingTeam) {
        this.opposingTeam = opposingTeam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRunsTaken() {
        return runsTaken;
    }

    public void setRunsTaken(String runsTaken) {
        this.runsTaken = runsTaken;
    }

    public String getBallsFaced() {
        return ballsFaced;
    }

    public void setBallsFaced(String ballsFaced) {
        this.ballsFaced = ballsFaced;
    }

    public String getTeamScore() {
        return teamScore;
    }

    public void setTeamScore(String teamScore) {
        this.teamScore = teamScore;
    }

    public String getFours() {
        return fours;
    }

    public void setFours(String fours) {
        this.fours = fours;
    }

    public String getSixes() {
        return sixes;
    }

    public void setSixes(String sixes) {
        this.sixes = sixes;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getFieldingCatches() {
        return fieldingCatches;
    }

    public void setFieldingCatches(String fieldingCatches) {
        this.fieldingCatches = fieldingCatches;
    }

    public String getFieldingRunouts() {
        return fieldingRunouts;
    }

    public void setFieldingRunouts(String fieldingRunouts) {
        this.fieldingRunouts = fieldingRunouts;
    }
}
