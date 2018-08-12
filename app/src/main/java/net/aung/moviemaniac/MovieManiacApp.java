package net.aung.moviemaniac;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import net.aung.moviemaniac.data.model.MovieModel;

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
        Stetho.initializeWithDefaults(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

        Fabric.with(this, new Crashlytics());
        sContext = getApplicationContext();
        INSTANCE = this;

        MovieModel.initMovieModel(getApplicationContext());
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
