package com.premsuraj.cricketstats;

import android.databinding.ObservableField;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Premsuraj
 */

public class InningsData implements Serializable {
    private ObservableField<String> opposingTeam = new ObservableField<>();
    private ObservableField<String> date = new ObservableField<>();
    private ObservableField<String> runsTaken = new ObservableField<>();
    private ObservableField<String> ballsFaced = new ObservableField<>();
    private ObservableField<String> teamScore = new ObservableField<>();
    private ObservableField<String> fours = new ObservableField<>();
    private ObservableField<String> sixes = new ObservableField<>();
    private ObservableField<String> out = new ObservableField<>();
    private ObservableField<String> fieldingCatches = new ObservableField<>();
    private ObservableField<String> fieldingRunouts = new ObservableField<>();

    public String getOpposingTeam() {
        return opposingTeam.get();
    }

    public void setOpposingTeam(String opposingTeam) {
        this.opposingTeam.set(opposingTeam);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getRunsTaken() {
        return runsTaken.get();
    }

    public void setRunsTaken(String runsTaken) {
        this.runsTaken.set(runsTaken);
    }

    public String getBallsFaced() {
        return ballsFaced.get();
    }

    public void setBallsFaced(String ballsFaced) {
        this.ballsFaced.set(ballsFaced);
    }

    public String getTeamScore() {
        return teamScore.get();
    }

    public void setTeamScore(String teamScore) {
        this.teamScore.set(teamScore);
    }

    public String getFours() {
        return fours.get();
    }

    public void setFours(String fours) {
        this.fours.set(fours);
    }

    public String getSixes() {
        return sixes.get();
    }

    public void setSixes(String sixes) {
        this.sixes.set(sixes);
    }

    public String getOut() {
        return out.get();
    }

    public void setOut(String out) {
        this.out.set(out);
    }

    public String getFieldingCatches() {
        return fieldingCatches.get();
    }

    public void setFieldingCatches(String fieldingCatches) {
        this.fieldingCatches.set(fieldingCatches);
    }

    public String getFieldingRunouts() {
        return fieldingRunouts.get();
    }

    public void setFieldingRunouts(String fieldingRunouts) {
        this.fieldingRunouts.set(fieldingRunouts);
    }

    public void setDateFromCalendar(Calendar date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        this.setDate(format.format(date.getTime()));
    }
}
