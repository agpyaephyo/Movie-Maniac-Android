package net.aung.moviemaniac.activities;

import android.content.Intent;
import android.os.Bundle;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.fragments.MovieDetailFragment;
import net.aung.moviemaniac.fragments.TVSeriesDetailFragment;
import net.aung.moviemaniac.utils.ScreenUtils;

/**
 * Created by aung on 12/16/15.
 */
public class MMDetailActivity extends BaseActivity {

    private static final String INTENT_EXTRA_MOVIE_ID = "INTENT_EXTRA_MOVIE_ID";
    private static final String INTENT_EXTRA_MOVIE_TYPE = "INTENT_EXTRA_MOVIE_TYPE";

    private static final String INTENT_EXTRA_TV_SERIES_ID = "INTENT_EXTRA_TV_SERIES_ID";
    private static final String INTENT_EXTRA_TV_SERIES_TYPE = "INTENT_EXTRA_TV_SERIES_TYPE";

    private static final String INTENT_EXTRA_DETAIL_TYPE = "INTENT_EXTRA_DETAIL_TYPE";
    private static final int DETAIL_TYPE_MOVIE = 1;
    private static final int DETAIL_TYPE_TV_SERIES = 2;

    public static Intent createMovieIntent(int movieId, int movieType) {
        Intent intent = new Intent(MovieManiacApp.getContext(), MMDetailActivity.class);
        intent.putExtra(INTENT_EXTRA_MOVIE_ID, movieId);
        intent.putExtra(INTENT_EXTRA_MOVIE_TYPE, movieType);
        intent.putExtra(INTENT_EXTRA_DETAIL_TYPE, DETAIL_TYPE_MOVIE);
        return intent;
    }

    public static Intent createTVSeriesIntent(int tvSeriesId, int tvSeriesType) {
        Intent intent = new Intent(MovieManiacApp.getContext(), MMDetailActivity.class);
        intent.putExtra(INTENT_EXTRA_TV_SERIES_ID, tvSeriesId);
        intent.putExtra(INTENT_EXTRA_TV_SERIES_TYPE, tvSeriesType);
        intent.putExtra(INTENT_EXTRA_DETAIL_TYPE, DETAIL_TYPE_TV_SERIES);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ScreenUtils.setStatusbarTranslucent(true, this);

        int detailType = getIntent().getIntExtra(INTENT_EXTRA_DETAIL_TYPE, -1);
        if (detailType == DETAIL_TYPE_MOVIE) {
            int movieId = getIntent().getIntExtra(INTENT_EXTRA_MOVIE_ID, 0);
            int movieType = getIntent().getIntExtra(INTENT_EXTRA_MOVIE_TYPE, 0);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, MovieDetailFragment.newInstance(movieId, movieType))
                        .commit();
            }
        } else if (detailType == DETAIL_TYPE_TV_SERIES) {
            int tvSeriesId = getIntent().getIntExtra(INTENT_EXTRA_TV_SERIES_ID, 0);
            int tvSeriesType = getIntent().getIntExtra(INTENT_EXTRA_TV_SERIES_TYPE, 0);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, TVSeriesDetailFragment.newInstance(tvSeriesId, tvSeriesType))
                        .commit();
            }
        }
    }
}
