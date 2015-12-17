package net.aung.moviemaniac.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import net.aung.moviemaniac.MovieManiacApp;

/**
 * Created by aung on 12/17/15.
 */
public class NetworkUtils {

    private static NetworkUtils objInstance;

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private Context context;

    private NetworkUtils() {
        context = MovieManiacApp.getContext();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static NetworkUtils getInstance() {
        if (objInstance == null) {
            objInstance = new NetworkUtils();
        }

        return objInstance;
    }

    /**
     * Check if the device has connected to an active network. (Wifi / 3G)
     * @return true if connected. false if not connected.
     */
    public boolean isOnline(){
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }
}
