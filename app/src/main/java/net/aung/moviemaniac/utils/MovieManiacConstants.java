package net.aung.moviemaniac.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by aung on 3/7/16.
 */
public class MovieManiacConstants {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SORT_ORDER_MOST_POPULAR, SORT_ORDER_TOP_RATED})
    public @interface SortOrder {}

    public static final int SORT_ORDER_MOST_POPULAR = 1;
    public static final int SORT_ORDER_TOP_RATED = 2;

    public static final int CATEGORY_MOST_POPULAR_MOVIES = 1;
    public static final int CATEGORY_TOP_RATED_MOVIES = 2;
    public static final int CATEGORY_MY_FAVOURITES_MOVIES = 3;
    public static final int CATEGORY_NOW_PLAYING_MOVIES = 4;
    public static final int CATEGORY_UPCOMING_MOVIES = 5;
    public static final int CATEGORY_MOST_POPULAR_TV_SERIES = 6;
    public static final int CATEGORY_TOP_RATED_TV_SERIES = 7;
    public static final int CATEGORY_MY_FAVOURITES_TV_SERIES = 8;

    public static final String MOVIE_CATEGORY_PREFIX = "MOVIE_CATEGORY_";
    public static final String TV_SERIES_CATEGORY_PREFIX = "TV_SERIES_CATEGORY_";

    //Loader IDs
    public static final int MOVIE_LIST_LOADER = 0;
    public static final int MOVIE_DETAIL_LOADER = 1;
    public static final int TV_SERIES_LIST_LOADER = 2;
    public static final int TV_SERIES_DETAIL_LOADER = 3;

    //Movie Type
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MOVIE_TYPE_MOST_POPULAR, MOVIE_TYPE_TOP_RATED, MOVIE_TYPE_NOW_PLAYING, MOVIE_TYPE_UPCOMING})
    public @interface MovieType {}

    public static final int MOVIE_TYPE_MOST_POPULAR = 100;
    public static final int MOVIE_TYPE_TOP_RATED = 200;
    public static final int MOVIE_TYPE_NOW_PLAYING = 300;
    public static final int MOVIE_TYPE_UPCOMING = 400;

    //Movie Type
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TV_SERIES_TYPE_MOST_POPULAR, TV_SERIES_TYPE_TOP_RATED})
    public @interface TVSeriesType {}

    public static final int TV_SERIES_TYPE_MOST_POPULAR = 500;
    public static final int TV_SERIES_TYPE_TOP_RATED = 600;

    public static final String IS_GENRE_LIST_LOADED_KEY = "IS_GENRE_LIST_LOADED_KEY";

    public static final String IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/";
    public static final String IMAGE_SIZE_W185 = "w185";
    public static final String IMAGE_SIZE_W500 = "w500";

    public static final String APP_VERSION = "1.0";
    public static final String APP_VERSION_POST_FIX = "Alpha";
}
