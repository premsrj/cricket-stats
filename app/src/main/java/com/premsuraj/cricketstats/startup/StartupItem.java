package com.premsuraj.cricketstats.startup;


/**
 * This is the base class for any item that has to be managed by the the StartupManager
 *
 * @author Premsuraj
 */
public abstract class StartupItem {
    /**
     * This is the time stamp at which the StartupItem was created, it is also used as the item id for array management purposes
     */
    private long itemIdTimeStamp;

    public StartupItem() {
        itemIdTimeStamp = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StartupItem)) return false;

        StartupItem that = (StartupItem) o;

        return itemIdTimeStamp == that.itemIdTimeStamp;

    }

    @Override
    public int hashCode() {
        return (int) (itemIdTimeStamp ^ (itemIdTimeStamp >>> 32));
    }

    /**
     * This function is called when data loading is to be initiated. All child classes must implement this function and start their data load when it is called
     *
     * @param callback The callback that must be fired when data load fails or succeeds. The callbacks must be issued for the StartupManager to work properly.
     */
    public abstract void startDataLoad(StartupItemCallback callback);
}
