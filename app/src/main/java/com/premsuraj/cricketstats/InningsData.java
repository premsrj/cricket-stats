package com.premsuraj.cricketstats;

import java.io.Serializable;

/**
 * Created by Premsuraj
 */

public class InningsData implements Serializable {
    String date;
    String opposingTeam;
    int runsTaken;
    int ballsFaced;
    int teamScore;
    int fours;
    int sixes;
    String out;
    int fieldingCatches;
    int fieldingRunouts;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpposingTeam() {
        return opposingTeam;
    }

    public void setOpposingTeam(String opposingTeam) {
        this.opposingTeam = opposingTeam;
    }

    public String getRunsTaken() {
        return "" + runsTaken;
    }

    public void setRunsTaken(String runsTaken) {
        this.runsTaken = Integer.parseInt(runsTaken);
    }

    public String getBallsFaced() {
        return "" + ballsFaced;
    }

    public void setBallsFaced(String ballsFaced) {
        this.ballsFaced = Integer.parseInt(ballsFaced);
    }

    public String getTeamScore() {
        return "" + teamScore;
    }

    public void setTeamScore(String teamScore) {
        this.teamScore = Integer.parseInt(teamScore);
    }

    public String getFours() {
        return "" + fours;
    }

    public void setFours(String fours) {
        this.fours = Integer.parseInt(fours);
    }

    public String getSixes() {
        return "" + sixes;
    }

    public void setSixes(String sixes) {
        this.sixes = Integer.parseInt(sixes);
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getFieldingCatches() {
        return "" + fieldingCatches;
    }

    public void setFieldingCatches(String fieldingCatches) {
        this.fieldingCatches = Integer.parseInt(fieldingCatches);
    }

    public String getFieldingRunouts() {
        return "" + fieldingRunouts;
    }

    public void setFieldingRunouts(String fieldingRunouts) {
        this.fieldingRunouts = Integer.parseInt(fieldingRunouts);
    }
}
