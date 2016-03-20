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
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this, this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
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

                String dailySpecial = GAUtils.getInstance().getDailySpecial();
                Toast.makeText(getApplicationContext(), "Daily special : " + dailySpecial, Toast.LENGTH_SHORT).show();
            }
        });

        mLeftMenu = (LeftMenuFragment) getSupportFragmentManager().findFragmentById(R.id.left_meu);
        mLeftMenu.setUp(R.id.left_meu, mDrawerLayout, mCallbackManager);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
            /*
            showMovieShelf();
            mCurrentMenuIndex = MenuVO.MENU_INDEX_MOVIE_SHELF;
            */

            showTVSeriesShelf();
            mCurrentMenuIndex = MenuVO.MENU_INDEX_TV_SERIES_SHELF;
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

        /*
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fl_container, MovieDetailFragment.newInstance(movie.getId()))
                .addToBackStack(null)
                .commit();
                */
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
        getSupportActionBar().setTitle(R.string.section_movie);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_pager, MoviePagerFragment.newInstance())
                .commit();
    }

    private void showTVSeriesShelf() {
        getSupportActionBar().setTitle(R.string.section_tv_series);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_pager, TVSeriesPagerFragment.newInstance())
                .commit();
    }
}
