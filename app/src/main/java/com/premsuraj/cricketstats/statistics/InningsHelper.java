package com.premsuraj.cricketstats.statistics;

import com.premsuraj.cricketstats.InningsData;

/**
 * Created by Premsuraj
 */

public abstract class InningsHelper {

    public static boolean isInnings(InningsData data) {
        return !(isNullOrEmpty(data.runsTaken) ||
                isNullOrEmpty(data.ballsFaced));
    }

    public static boolean isNullOrEmpty(String runsTaken) {
        return runsTaken == null || runsTaken.isEmpty();
    }
}
