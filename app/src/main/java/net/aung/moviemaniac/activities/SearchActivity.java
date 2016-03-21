package net.aung.moviemaniac.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.controllers.MovieItemController;
import net.aung.moviemaniac.controllers.TVSeriesItemController;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.data.vos.TVSeriesVO;
import net.aung.moviemaniac.fragments.MovieDetailFragment;
import net.aung.moviemaniac.fragments.SearchFragment;
import net.aung.moviemaniac.fragments.TVSeriesDetailFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 3/21/16.
 */
public class SearchActivity extends BaseActivity implements
        MovieItemController,
        TVSeriesItemController {

    private static final String INTENT_EXTRA_SEARCH_TYPE = "INTENT_EXTRA_SEARCH_TYPE";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SEARCH_TYPE_MOVIE, SEARCH_TYPE_TV_SERIES})
    public @interface SearchType {}

    public static final int SEARCH_TYPE_MOVIE = 1;
    public static final int SEARCH_TYPE_TV_SERIES = 2;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    public static Intent createNewIntent(@SearchType int searchType) {
        Intent intentToSearch = new Intent(MovieManiacApp.getContext(), SearchActivity.class);
        intentToSearch.putExtra(INTENT_EXTRA_SEARCH_TYPE, searchType);
        return intentToSearch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this, this);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            int searchType = intent.getIntExtra(INTENT_EXTRA_SEARCH_TYPE, -1);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_container, SearchFragment.newInstance(searchType))
                    .commit();
        }
    }

    @Override
    public void onNavigateToDetail(MovieVO movie) {
        if (getResources().getBoolean(R.bool.isTablet)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_tablet_detail, MovieDetailFragment.newInstance(movie.getId(), movie.getMovieType()))
                    .commit();

            mDrawerLayout.openDrawer(Gravity.RIGHT);
        } else {
            Intent intentToDetail = MMDetailActivity.createMovieIntent(movie.getId(), movie.getMovieType());
            startActivity(intentToDetail);
        }
    }

    @Override
    public void onNavigateToDetail(TVSeriesVO tvSeries) {
        if (getResources().getBoolean(R.bool.isTablet)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_tablet_detail, TVSeriesDetailFragment.newInstance(tvSeries.getTvSerieId(), tvSeries.getTvSeriesType()))
                    .commit();

            mDrawerLayout.openDrawer(Gravity.RIGHT);
        } else {
            Intent intentToDetail = MMDetailActivity.createTVSeriesIntent(tvSeries.getTvSerieId(), tvSeries.getTvSeriesType());
            startActivity(intentToDetail);
        }
    }
}
