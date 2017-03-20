package com.premsuraj.cricketstats.statistics;

import com.premsuraj.cricketstats.InningsData;

/**
 * Created by Premsuraj
 */

public abstract class InningsHelper {

    public static boolean isInnings(InningsData data) {
        return !(isNullOrEmpty(data.getRunsTaken()) ||
                isNullOrEmpty(data.getBallsFaced()));
    }

    public static boolean isNullOrEmpty(String runsTaken) {
        return runsTaken == null || runsTaken.isEmpty();
    }
}
