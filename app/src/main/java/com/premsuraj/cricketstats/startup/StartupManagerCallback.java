package com.premsuraj.cricketstats.startup;

import java.util.ArrayList;


/**
 * Interface to get callbacks for StartupManager related events
 *
 * @author Premsuraj
 *         <p>
 */
public interface StartupManagerCallback {
    /**
     * The callback event that is fired after StartupManager has finished processing all items given to it
     *
     * @param passedItems The items that have successfully loaded data. This depends on the objects correctly firing {@link StartupItemCallback}
     * @param failedItems The items that have failed to load data. This depends on the objects correctly firing {@link StartupItemCallback}
     */
    void onFinishedProcessing(ArrayList<StartupItem> passedItems, ArrayList<StartupItem> failedItems);
}
