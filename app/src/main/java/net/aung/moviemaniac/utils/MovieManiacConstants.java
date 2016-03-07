package net.aung.moviemaniac.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by aung on 3/7/16.
 */
public class MovieManiacConstants {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SORT_ORDER_MOST_POPULAR, SORT_ORDER_HIGHEST_RATED})
    public @interface SortOrder {}

    public static final int SORT_ORDER_MOST_POPULAR = 1;
    public static final int SORT_ORDER_HIGHEST_RATED = 2;

    public static final int CATEGORY_MOST_POPULAR_MOVIES = 1;
    public static final int CATEGORY_TOP_RATED_MOVIES = 2;
}
