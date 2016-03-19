package net.aung.moviemaniac;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import io.fabric.sdk.android.Fabric;

/**
 * Created by aung on 12/9/15.
 */
public class MovieManiacApp extends Application {

    public static final String TAG = "MovieManiacApp";
    public static SparseArray<Bitmap> sPosterCache = new SparseArray<Bitmap>(1);

    private static Context sContext;
    public static MovieManiacApp INSTANCE; //For GA->enableAutoActivityReports


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        sContext = getApplicationContext();
        INSTANCE = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        sContext = null;
        INSTANCE = null;
    }

    public static Context getContext() {
        return sContext;
    }
}
