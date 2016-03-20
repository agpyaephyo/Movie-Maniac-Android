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
import net.aung.moviemaniac.adapters.TVSeriesListAdapter;
import net.aung.moviemaniac.controllers.MovieItemController;
import net.aung.moviemaniac.data.persistence.MovieContract;
import net.aung.moviemaniac.data.vos.GenreVO;
import net.aung.moviemaniac.data.vos.TVSeriesVO;
import net.aung.moviemaniac.mvp.presenters.TVSeriesListPresenter;
import net.aung.moviemaniac.mvp.views.TVSeriesListView;
import net.aung.moviemaniac.utils.GAUtils;
import net.aung.moviemaniac.utils.MovieManiacConstants;
import net.aung.moviemaniac.views.components.recyclerview.AutofitRecyclerView;
import net.aung.moviemaniac.views.components.recyclerview.SmartScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class TVSeriesListFragment extends BaseFragment
        implements TVSeriesListView,
        SwipeRefreshLayout.OnRefreshListener,
        SmartScrollListener.ControllerSmartScroll,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String BARG_CATEGORY = "BARG_CATEGORY";

    @Bind(R.id.rv_tv_series)
    AutofitRecyclerView rvTVSeries;

    @Bind(R.id.vp_empty_favourite)
    View vEmptyFavourite;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    private View rootView;

    private TVSeriesListAdapter tvSeriesListAdapter;
    private TVSeriesListPresenter tvSeriesListPresenter;

    private SmartScrollListener smartScrollListener;
    //private MovieItemController controller;

    private int mCategory;
    private List<TVSeriesVO> mTVSeriesList = new ArrayList<>();

    public static TVSeriesListFragment newInstance(int category) {
        TVSeriesListFragment fragment = new TVSeriesListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BARG_CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    public TVSeriesListFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //controller = (MovieItemController) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvSeriesListPresenter = new TVSeriesListPresenter(this, mCategory);
        tvSeriesListPresenter.onCreate();

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
        rootView = inflater.inflate(R.layout.fragment_tv_series_list, container, false);
        ButterKnife.bind(this, rootView);

        tvSeriesListAdapter = TVSeriesListAdapter.newInstance(mCategory == MovieManiacConstants.CATEGORY_MY_FAVOURITES_MOVIES);

        rvTVSeries.setGridColumnSpan(1);
        rvTVSeries.setAdapter(tvSeriesListAdapter);
        rvTVSeries.setEmptyView(vEmptyFavourite);
        rvTVSeries.addOnScrollListener(smartScrollListener);

        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark);

        /*
        if (mCategory == MovieManiacConstants.CATEGORY_MY_FAVOURITES_MOVIES) {
            swipeContainer.setEnabled(false);
        }
        */

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(MovieManiacConstants.TV_SERIES_LIST_LOADER, null, this);
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
        tvSeriesListPresenter.onStart();
    }

    @Override
    protected void sendScreenHit() {
        GAUtils.getInstance().sendScreenHit(GAUtils.SCREEN_NAME_TV_SERIES_LIST);
    }

    @Override
    public void onStop() {
        super.onStop();
        tvSeriesListPresenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvSeriesListPresenter.onDestroy();
    }

    @Override
    public boolean isTVSeriesListEmpty() {
        return tvSeriesListAdapter == null || tvSeriesListAdapter.getItemCount() == 0;
    }

    @Override
    public void displayTVSeriesList(List<TVSeriesVO> tvSeriesList, boolean isToAppend) {
        if (swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false);
        }

        if (isToAppend) {
            tvSeriesListAdapter.appendMovieList(tvSeriesList);
        } else {
            tvSeriesListAdapter.setMovieList(tvSeriesList);
        }
    }

    @Override
    public void displayFailToLoadData(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null).show();
    }


    @Override
    public void onRefresh() {
        tvSeriesListPresenter.forceRefresh();
    }

    @Override
    public void onListEndReached() {
        Snackbar.make(rootView, getString(R.string.loading_more_tv_series), Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();

        tvSeriesListPresenter.loadMoreData();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            String sortedBy = null;
            String[] selectionArgs = null;
            if (mCategory == MovieManiacConstants.CATEGORY_MOST_POPULAR_TV_SERIES) {
                //sortedBy = MovieContract.MovieEntry.TABLE_NAME + "." + MovieContract.MovieEntry.COLUMN_POPULARITY + " DESC";
                selectionArgs = new String[]{String.valueOf(MovieManiacConstants.TV_SERIES_TYPE_MOST_POPULAR)};
            }

            return new CursorLoader(getActivity(),
                    MovieContract.TVSeriesEntry.CONTENT_URI,
                    null,
                    MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_TYPE + " = ? ",
                    selectionArgs,
                    sortedBy
            );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<TVSeriesVO> tvSeriesList = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            do {
                TVSeriesVO tvSeries = TVSeriesVO.parseFromListCursor(data);
                tvSeries.setGenreList(GenreVO.loadGenreListByTVSeriesId(tvSeries.getTvSerieId()));
                tvSeriesList.add(tvSeries);

            } while (data.moveToNext());
        }

        if(mTVSeriesList.size() != tvSeriesList.size()) { //To prevent refreshing the recyclerView when coming back from detail screen.
            mTVSeriesList = tvSeriesList;

            Log.d(MovieManiacApp.TAG, "Displaying tv series for category " + mCategory + " : " + tvSeriesList.size());
            displayTVSeriesList(tvSeriesList, false);
        } else {
            if (swipeContainer.isRefreshing()) {
                swipeContainer.setRefreshing(false);
            }
        }

        if (tvSeriesList.size() == 0) {
            tvSeriesListPresenter.loadMovieListFromNetwork();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        List<TVSeriesVO> movieList = new ArrayList<>();
        displayTVSeriesList(movieList, false);
    }
}
