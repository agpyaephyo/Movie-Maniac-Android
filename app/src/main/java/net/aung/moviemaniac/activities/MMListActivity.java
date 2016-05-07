package net.aung.moviemaniac.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.controllers.MovieItemController;
import net.aung.moviemaniac.controllers.TVSeriesItemController;
import net.aung.moviemaniac.data.vos.MenuVO;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.data.vos.TVSeriesVO;
import net.aung.moviemaniac.fragments.MovieDetailFragment;
import net.aung.moviemaniac.fragments.TVSeriesDetailFragment;
import net.aung.moviemaniac.fragments.pagers.MoviePagerFragment;
import net.aung.moviemaniac.fragments.pagers.TVSeriesPagerFragment;
import net.aung.moviemaniac.menus.LeftMenuFragment;
import net.aung.moviemaniac.utils.GAUtils;
import net.aung.moviemaniac.views.items.ViewItemMenu;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MMListActivity extends BaseActivity implements
        MovieItemController,
        TVSeriesItemController,
        ViewItemMenu.MenuItemController {

    private static final String OUT_STAGE_MENU_INDEX = "OUT_STAGE_MENU_INDEX";

    private LeftMenuFragment mLeftMenu;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    /*
    @Bind(R.id.pager_movies)
    ViewPager pagerMovies;

    @Bind(R.id.tl_movies)
    TabLayout tlMovies;

    private MoviePagerAdapter mMoviePagerAdapter;
    */

    private int mCurrentMenuIndex;
    private TextView tvScreenTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvScreenTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                SortMovieDialog dialog = new SortMovieDialog(MovieListActivity.this);
                dialog.show();
                */

                /*
                String dailySpecial = GAUtils.getInstance().getDailySpecial();
                Toast.makeText(getApplicationContext(), "Daily special : " + dailySpecial, Toast.LENGTH_SHORT).show();
                */

                launchSearch();
            }
        });

        mLeftMenu = (LeftMenuFragment) getSupportFragmentManager().findFragmentById(R.id.left_meu);
        mLeftMenu.setUp(R.id.left_meu, mDrawerLayout, mCallbackManager);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftMenu.openMenu();
            }
        });

        /*
        mMoviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        mMoviePagerAdapter.addTab(MovieListFragment.newInstance(MovieManiacConstants.CATEGORY_NOW_PLAYING_MOVIES), getString(R.string.now_playing_movies));
        mMoviePagerAdapter.addTab(MovieListFragment.newInstance(MovieManiacConstants.CATEGORY_UPCOMING_MOVIES), getString(R.string.upcoming_movies));
        mMoviePagerAdapter.addTab(MovieListFragment.newInstance(MovieManiacConstants.CATEGORY_MOST_POPULAR_MOVIES), getString(R.string.most_popular_movies));
        mMoviePagerAdapter.addTab(MovieListFragment.newInstance(MovieManiacConstants.CATEGORY_TOP_RATED_MOVIES), getString(R.string.top_rated_movies));
        mMoviePagerAdapter.addTab(MovieListFragment.newInstance(MovieManiacConstants.CATEGORY_MY_FAVOURITES_MOVIES), getString(R.string.my_favourites));

        pagerMovies.setAdapter(mMoviePagerAdapter);
        pagerMovies.setOffscreenPageLimit(mMoviePagerAdapter.getCount());
        tlMovies.setupWithViewPager(pagerMovies);
        */

        if (savedInstanceState == null) {
            showMovieShelf();
            mCurrentMenuIndex = MenuVO.MENU_INDEX_MOVIE_SHELF;

            /*
            showTVSeriesShelf();
            mCurrentMenuIndex = MenuVO.MENU_INDEX_TV_SERIES_SHELF;
            */
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(OUT_STAGE_MENU_INDEX, mCurrentMenuIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentMenuIndex = savedInstanceState.getInt(OUT_STAGE_MENU_INDEX);

        switch (mCurrentMenuIndex) {
            case MenuVO.MENU_INDEX_MOVIE_SHELF:
                tvScreenTitle.setText(R.string.section_movie);
                break;
            case MenuVO.MENU_INDEX_TV_SERIES_SHELF:
                tvScreenTitle.setText(R.string.section_tv_series);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_about) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
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

    @Override
    public void onTapMenu(MenuVO menu) {
        switch (menu.getMenuId()) {
            case MenuVO.MENU_INDEX_MOVIE_SHELF:
                mDrawerLayout.closeDrawers();
                if (mCurrentMenuIndex != MenuVO.MENU_INDEX_MOVIE_SHELF) {
                    mCurrentMenuIndex = MenuVO.MENU_INDEX_MOVIE_SHELF;
                    showMovieShelf();
                }
                break;
            case MenuVO.MENU_INDEX_TV_SERIES_SHELF:
                mDrawerLayout.closeDrawers();
                if (mCurrentMenuIndex != MenuVO.MENU_INDEX_TV_SERIES_SHELF) {
                    mCurrentMenuIndex = MenuVO.MENU_INDEX_TV_SERIES_SHELF;
                    showTVSeriesShelf();
                }
                break;
            default:
                throw new UnsupportedOperationException("Menu Id is not supported.");
        }
    }

    private void showMovieShelf() {
        tvScreenTitle.setText(R.string.section_movie);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_pager, MoviePagerFragment.newInstance())
                .commit();
    }

    private void showTVSeriesShelf() {
        tvScreenTitle.setText(R.string.section_tv_series);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_pager, TVSeriesPagerFragment.newInstance())
                .commit();
    }

    private void launchSearch() {
        @SearchActivity.SearchType int searchType = -1;
        if (mCurrentMenuIndex == MenuVO.MENU_INDEX_MOVIE_SHELF) {
            searchType = SearchActivity.SEARCH_TYPE_MOVIE;
        } else if(mCurrentMenuIndex == MenuVO.MENU_INDEX_TV_SERIES_SHELF) {
            searchType = SearchActivity.SEARCH_TYPE_TV_SERIES;
        }

        Intent intentToSearch = SearchActivity.createNewIntent(searchType);
        startActivity(intentToSearch);
    }
}
