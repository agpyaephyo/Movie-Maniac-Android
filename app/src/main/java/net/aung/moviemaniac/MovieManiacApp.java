package net.aung.moviemaniac;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by aung on 12/9/15.
 */
public class MovieManiacApp extends Application {

    public static final String TAG = "MovieManiacApp";
    public static SparseArray<Bitmap> sPosterCache = new SparseArray<Bitmap>(1);

    private static Context sContext;
    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        sContext = null;
    }

    public void startTracking() {
        if(mTracker == null) {
            GoogleAnalytics ga = GoogleAnalytics.getInstance(this);
            mTracker = ga.newTracker(R.xml.ga_config);
            ga.enableAutoActivityReports(this);
            ga.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        }
    }

    public Tracker getTracker() {
        startTracking();
        return mTracker;
    }

    public static Context getContext() {
        return sContext;
    }
}
