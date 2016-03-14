package net.aung.moviemaniac.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.data.model.MovieModel;

/**
 * Created by aung on 3/7/16.
 */
public class SettingsUtils {

    // -- Sort Order --
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

    // -- Page Number Related --
    public static int retrievePageNumber(int movieCategory) {
        Context context = MovieManiacApp.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        int loadedPageNumber = defaultSharedPref.getInt(MovieManiacConstants.MOVIE_CATEGORY_PREFIX+movieCategory, MovieModel.INITIAL_PAGE_NUMBER);

        return loadedPageNumber;
    }

    public static void savePageNumber(int movieCategory, int loadedPageNumber) {
        Context context = MovieManiacApp.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPref.edit().putInt(MovieManiacConstants.MOVIE_CATEGORY_PREFIX+movieCategory, loadedPageNumber).apply();
    }

    public static void resetPageNumber(int movieCategory) {
        Context context = MovieManiacApp.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPref.edit().putInt(MovieManiacConstants.MOVIE_CATEGORY_PREFIX+movieCategory, MovieModel.INITIAL_PAGE_NUMBER).apply();
    }
}
