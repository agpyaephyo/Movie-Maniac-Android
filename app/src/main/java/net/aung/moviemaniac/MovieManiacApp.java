package net.aung.moviemaniac;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;

/**
 * Created by aung on 12/9/15.
 */
public class MovieManiacApp extends Application {

    public static final String TAG = MovieManiacApp.class.getSimpleName();
    public static SparseArray<Bitmap> sPosterCache = new SparseArray<Bitmap>(1);

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
