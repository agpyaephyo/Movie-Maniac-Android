package net.aung.moviemaniac.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.adapters.MovieListAdapter;
import net.aung.moviemaniac.controllers.MovieItemController;
import net.aung.moviemaniac.data.persistence.MovieContract;
import net.aung.moviemaniac.data.vos.GenreVO;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.mvp.presenters.MovieListPresenter;
import net.aung.moviemaniac.mvp.views.MovieListView;
import net.aung.moviemaniac.utils.GAUtils;
import net.aung.moviemaniac.utils.MovieManiacConstants;
import net.aung.moviemaniac.views.components.recyclerview.AutofitRecyclerView;
import net.aung.moviemaniac.views.components.recyclerview.SmartScrollListener;
import net.aung.moviemaniac.views.pods.ViewPodEmpty;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieListFragment extends BaseFragment
        implements MovieListView,
        SwipeRefreshLayout.OnRefreshListener,
        SmartScrollListener.ControllerSmartScroll,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String BARG_CATEGORY = "BARG_CATEGORY";

    @Bind(R.id.rv_movies)
    AutofitRecyclerView rvMovies;

    @Bind(R.id.vp_empty_favourite)
    ViewPodEmpty vpEmptyFavourite;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    private View rootView;

    private MovieListAdapter movieListAdapter;
    private MovieListPresenter movieListPresenter;
    private SmartScrollListener smartScrollListener;
    private MovieItemController controller;

    private int mCategory;
    private List<MovieVO> mMovieList = new ArrayList<>();

    public static MovieListFragment newInstance(int category) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BARG_CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    public MovieListFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controller = (MovieItemController) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieListPresenter = new MovieListPresenter(this, mCategory);
        movieListPresenter.onCreate();

        smartScrollListener = new SmartScrollListener(this);

        //setHasOptionsMenu(true);
    }

    @Override
    protected void readArguments(Bundle bundle) {
        super.readArguments(bundle);
        if (bundle != null) {
            mCategory = bundle.getInt(BARG_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, rootView);

        movieListAdapter = MovieListAdapter.newInstance(controller, mCategory == MovieManiacConstants.CATEGORY_MY_FAVOURITES_MOVIES);
        rvMovies.setAdapter(movieListAdapter);
        rvMovies.setEmptyView(vpEmptyFavourite);

        if(mCategory == MovieManiacConstants.CATEGORY_MY_FAVOURITES_MOVIES) {
            vpEmptyFavourite.setEmptyLabel(getString(R.string.empty_favourite_movies));
        }

        rvMovies.addOnScrollListener(smartScrollListener);

        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark);

        if (mCategory == MovieManiacConstants.CATEGORY_MY_FAVOURITES_MOVIES) {
            swipeContainer.setEnabled(false);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(MovieManiacConstants.MOVIE_LIST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Snackbar.make(rootView, "Search your favourite movies", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    @Override
    public void onStart() {
        super.onStart();
        movieListPresenter.onStart();
    }

    @Override
    protected void sendScreenHit() {
        GAUtils.getInstance().sendScreenHit(GAUtils.SCREEN_NAME_MOVIE_LIST);
    }

    @Override
    public void onStop() {
        super.onStop();
        movieListPresenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieListPresenter.onDestroy();
    }

    @Override
    public boolean isMovieListEmpty() {
        return movieListAdapter == null || movieListAdapter.getItemCount() == 0;
    }

    @Override
    public void displayMovieList(List<MovieVO> movieList, boolean isToAppend) {
        if (swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false);
        }

        if (isToAppend) {
            movieListAdapter.appendMovieList(movieList);
        } else {
            movieListAdapter.setMovieList(movieList);
        }
    }

    @Override
    public void displayFailToLoadData(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null).show();
    }


    @Override
    public void onRefresh() {
        movieListPresenter.forceRefresh();
    }

    @Override
    public void onListEndReached() {
        if (mCategory != MovieManiacConstants.CATEGORY_MY_FAVOURITES_MOVIES) {
            Snackbar.make(rootView, getString(R.string.loading_more_movies), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();

            movieListPresenter.loadMoreData();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mCategory == MovieManiacConstants.CATEGORY_MY_FAVOURITES_MOVIES) {
            return new CursorLoader(getActivity(),
                    MovieContract.MovieEntry.CONTENT_URI,
                    null,
                    MovieContract.MovieEntry.COLUMN_IS_STAR + " = ? ",
                    new String[]{String.valueOf(1)}, // 1 means starred the movie.
                    null
            );
        } else {
            String sortedBy = null;
            String[] selectionArgs = null;
            if (mCategory == MovieManiacConstants.CATEGORY_MOST_POPULAR_MOVIES) {
                //sortedBy = MovieContract.MovieEntry.TABLE_NAME + "." + MovieContract.MovieEntry.COLUMN_POPULARITY + " DESC";
                selectionArgs = new String[]{String.valueOf(MovieManiacConstants.MOVIE_TYPE_MOST_POPULAR)};
            } else if (mCategory == MovieManiacConstants.CATEGORY_TOP_RATED_MOVIES) {
                //sortedBy = MovieContract.MovieEntry.TABLE_NAME + "." + MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE + " DESC";
                selectionArgs = new String[]{String.valueOf(MovieManiacConstants.MOVIE_TYPE_TOP_RATED)};
            } else if (mCategory == MovieManiacConstants.CATEGORY_NOW_PLAYING_MOVIES) {
                //sortedBy = null;
                selectionArgs = new String[]{String.valueOf(MovieManiacConstants.MOVIE_TYPE_NOW_PLAYING)};
            } else if (mCategory == MovieManiacConstants.CATEGORY_UPCOMING_MOVIES) {
                //sortedBy = null;
                selectionArgs = new String[]{String.valueOf(MovieManiacConstants.MOVIE_TYPE_UPCOMING)};
            }

            return new CursorLoader(getActivity(),
                    MovieContract.MovieEntry.CONTENT_URI,
                    null,
                    MovieContract.MovieEntry.COLUMN_MOVIE_TYPE + " = ? ",
                    selectionArgs,
                    sortedBy
            );
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<MovieVO> movieList = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            do {
                MovieVO movie = MovieVO.parseFromListCursor(data);
                movie.setGenreList(GenreVO.loadGenreListByMovieId(movie.getId()));
                movieList.add(movie);

            } while (data.moveToNext());
        }

        if(mMovieList.size() != movieList.size()) { //To prevent refreshing the recyclerView when coming back from detail screen.
            mMovieList = movieList;

            Log.d(MovieManiacApp.TAG, "Displaying movies for category " + mCategory + " : " + movieList.size());
            displayMovieList(movieList, false);
        } else {
            if (swipeContainer.isRefreshing()) {
                swipeContainer.setRefreshing(false);
            }
        }

        if (movieList.size() == 0) {
            movieListPresenter.loadMovieListFromNetwork();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        List<MovieVO> movieList = new ArrayList<>();
        displayMovieList(movieList, false);
    }
}
