package com.premsuraj.expensemanager.startup;

/**
 * The callback class that is used to notify the StartupManager of success and failure of individual StartupItems
 *
 * @author Premsuraj
 */
public interface StartupItemCallback {

    /**
     * This callback must be fired after the data load succeeds so that the StartupManager can move to the next item in the queue
     *
     * @param item The actual instance of the StartupItem that passed
     */
    void onItemLoaded(StartupItem item);

    /**
     * This callback must be fired after the data load fails so that the StartupManager can move to the next item in the queue
     *
     * @param item The actual instance of the StartupItem that failed
     */
    void onItemLoadFailed(StartupItem item);
}
