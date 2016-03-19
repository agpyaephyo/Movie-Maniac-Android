package net.aung.moviemaniac.utils;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;

import java.util.concurrent.TimeUnit;

/**
 * Created by aung on 3/16/16.
 */
public class GAUtils {

    //Screens
    public static final String SCREEN_NAME_MOVIE_DETAIL = "Movie Detail";
    public static final String SCREEN_NAME_MOVIE_LIST = "Movie List";

    //Event Categories
    public static final String EVENT_CATEGORY_USER_ACTION = "User Action";

    //Event Actions - Detail
    public static final String EVENT_ACTION_TAP_FAB_PLUS = "Tap FAB Plus on Detail Screen";
    public static final String EVENT_ACTION_TAP_STAR = "Tap Star on Detail Screen";
    public static final String EVENT_ACTION_TAP_SHARE = "Tap Share on Detail Screen";
    public static final String EVENT_ACTION_TAP_SHARE_FACEBOOK = "Tap Share on Detail Screen";
    public static final String EVENT_ACTION_PLAY_TRAILER = "Play Trailer on Detail Screen";
    public static final String EVENT_ACTION_SCROLL_FOR_REVIEWS = "Scroll for reviews on Detail Screen";
    public static final String EVENT_ACTION_TAP_REMOVE_STAR_DETAIL = "Tap remove star on Detail Screen";

    //Event Actions - List
    public static final String EVENT_ACTION_TAP_REMOVE_STAR_LIST = "Tap remove star on List Screen";
    public static final String EVENT_ACTION_TAP_MOVIE_ITEM = "Tap movie item";

    //Keys in GTM Container
    private static final String CONTAINER_KEY_DAILY_SPECIAL = "daily-special";

    private static GAUtils objInstance;

    private Tracker mTracker;

    private ContainerHolder mContainerHolder;
    private TagManager mTagManager;

    private GAUtils() {
        initTracking();
        initTagManager();
    }

    public static GAUtils getInstance() {
        if (objInstance == null) {
            objInstance = new GAUtils();
        }

        return objInstance;
    }

    private void initTracking() {
        if (mTracker == null) {
            GoogleAnalytics ga = GoogleAnalytics.getInstance(MovieManiacApp.getContext());
            mTracker = ga.newTracker(R.xml.ga_config);
            ga.enableAutoActivityReports(MovieManiacApp.INSTANCE);
            ga.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        }
    }

    private void initTagManager() {
        if (mTagManager == null) {
            mTagManager = TagManager.getInstance(MovieManiacApp.getContext());
            mTagManager.setVerboseLoggingEnabled(true);

            loadGTMContainer();
        }
    }

    private void loadGTMContainer() {
        PendingResult pendingResult = mTagManager.loadContainerPreferFresh("GTM-PST3T3", R.raw.gtm_container);
        pendingResult.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(ContainerHolder containerHolder) {

                //if unsuccessful, return.
                if (!containerHolder.getStatus().isSuccess()) {
                    //deal with failure.
                    return;
                }

                //Manually refresh the container holder
                //can only do this once every 15 minutes or so.
                containerHolder.refresh();

                //Set the container holder, only want one per running app
                //We'll retrieve it later as needed
                mContainerHolder = containerHolder;
            }
        }, 2, TimeUnit.SECONDS);
    }

    public void sendScreenHit(String screenName) {
        mTracker.setScreenName(screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void sendUserEventHit(String action) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(EVENT_CATEGORY_USER_ACTION)
                .setAction(action)
                .build());
    }

    /**
     * Get daily-special from GTM.
     * @return
     */
    public String getDailySpecial() {
        if (mContainerHolder != null) {
            return mContainerHolder.getContainer().getString(CONTAINER_KEY_DAILY_SPECIAL);
        }

        return null;
    }
}
