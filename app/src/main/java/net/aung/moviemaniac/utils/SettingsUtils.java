package net.aung.moviemaniac.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;

/**
 * Created by aung on 3/7/16.
 */
public class SettingsUtils {

    public static int getSortOrder() {
        Context context = MovieManiacApp.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPref.getInt(context.getString(R.string.pref_sort_order_key), MovieManiacConstants.SORT_ORDER_MOST_POPULAR);
    }

    public static void saveSortOrder(@MovieManiacConstants.SortOrder int sortOrder) {
        Context context = MovieManiacApp.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPref.edit().putInt(context.getString(R.string.pref_sort_order_key), sortOrder).commit();
    }
}
