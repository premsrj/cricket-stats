package com.premsuraj.cricketstats.startup;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The main class that can be used to handle a queue of items that need to be loaded during app startup
 *
 * @author Premsuraj
 */
public class StartupManager {
    private static final String TAG = "Startup";
    private ArrayList<StartupItem> startupItems;
    private ArrayList<StartupItem> passedItems;
    private ArrayList<StartupItem> failedItems;

    public StartupManager() {
        startupItems = new ArrayList<>();
        passedItems = new ArrayList<>();
        failedItems = new ArrayList<>();
    }

    /**
     * Provide a list of instances of items that need to be loaded. All the passed objects must implement the {@link StartupItem} interface
     *
     * @param items The list of objects that will be processed in a queue by the StartupManager
     * @return An instance to itself so that other operations can be chained together
     */
    public StartupManager addStartupItems(StartupItem... items) {
        this.startupItems.addAll(Arrays.asList(items));
        return this;
    }

    /**
     * The call to start processing of all items in the queue
     *
     * @param startupManagerCallback The callback that will be provided to the calling object after all items in the queue have finished processing
     */
    public void start(StartupManagerCallback startupManagerCallback) {
        this.start(startupItems, startupManagerCallback);
    }

    private void start(List<StartupItem> items, final StartupManagerCallback startupManagerCallback) {
        if (startupItems == null || startupItems.isEmpty()) {
            Log.i(TAG, "All startup items processed notifying original caller");
            startupManagerCallback.onFinishedProcessing(passedItems, failedItems);
            return;
        }


        StartupItem item = items.get(0);
        Log.i(TAG, "Going to load item: " + item.getClass().toString());
        item.startDataLoad(new StartupItemCallback() {
            @Override
            public void onItemLoaded(StartupItem item) {
                Log.i(TAG, item.getClass().toString() + " succeeded in data loading");
                passedItems.add(item);
                startupItems.remove(item);
                start(startupItems, startupManagerCallback);
            }

            @Override
            public void onItemLoadFailed(StartupItem item) {
                Log.e(TAG, item.getClass().toString() + " failed to load data");
                failedItems.add(item);
                startupItems.remove(item);
                start(startupItems, startupManagerCallback);
            }
        });
    }
}
