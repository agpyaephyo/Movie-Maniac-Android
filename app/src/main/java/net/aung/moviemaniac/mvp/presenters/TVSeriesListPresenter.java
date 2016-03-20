package net.aung.moviemaniac.mvp.presenters;

import android.support.annotation.NonNull;

import net.aung.moviemaniac.data.model.MovieModel;
import net.aung.moviemaniac.events.DataEvent;
import net.aung.moviemaniac.mvp.views.TVSeriesListView;
import net.aung.moviemaniac.utils.MovieManiacConstants;
import net.aung.moviemaniac.utils.SettingsUtils;

/**
 * Created by aung on 12/12/15.
 */
public class TVSeriesListPresenter extends BasePresenter {

    private TVSeriesListView tvSeriesListView;
    private int tvSeriesCategory;

    private int pageNumber;

    public TVSeriesListPresenter(@NonNull TVSeriesListView tvSeriesListView, int tvSeriesCategory) {
        this.tvSeriesListView = tvSeriesListView;
        this.tvSeriesCategory = tvSeriesCategory;
        pageNumber = SettingsUtils.retrievePageNumber(tvSeriesCategory);
    }

    @Override
    public void onStart() {
        loadMovieListFromNetwork();
    }

    @Override
    public void onStop() {

    }

    public void loadMovieListFromNetwork() {
        loadNewMovieList(false);
    }

    public void onEventMainThread(DataEvent.ShowPopularTVSeriesListEvent event) {
        if (tvSeriesCategory == MovieManiacConstants.CATEGORY_MOST_POPULAR_TV_SERIES) {
            pageNumber = event.getPageNumber() + 1;
            SettingsUtils.saveTVSeriesPageNumber(tvSeriesCategory, pageNumber);

            tvSeriesListView.displayTVSeriesList(event.getTvSeriesList(), false);
        }
    }

    /*
    public void onEventMainThread(DataEvent.ShowTopRatedMovieListEvent event) {
        if (tvSeriesCategory == MovieManiacConstants.CATEGORY_TOP_RATED_MOVIES) {
            pageNumber = event.getPageNumber() + 1;
            SettingsUtils.saveMoviePageNumber(tvSeriesCategory, pageNumber);
        }
    }

    public void onEventMainThread(DataEvent.ShowNowPlayingMovieListEvent event) {
        if (tvSeriesCategory == MovieManiacConstants.CATEGORY_NOW_PLAYING_MOVIES) {
            pageNumber = event.getPageNumber() + 1;
            SettingsUtils.saveMoviePageNumber(tvSeriesCategory, pageNumber);
        }
    }

    public void onEventMainThread(DataEvent.ShowUpcomingMovieListEvent event) {
        if (tvSeriesCategory == MovieManiacConstants.CATEGORY_UPCOMING_MOVIES) {
            pageNumber = event.getPageNumber() + 1;
            SettingsUtils.saveMoviePageNumber(tvSeriesCategory, pageNumber);
        }
    }

    public void onEventMainThread(DataEvent.FailedToLoadDataEvent event) {
        tvSeriesListView.displayFailToLoadData(event.getMessage());
    }
    */

    private void loadNewMovieList(boolean isForce) {
        if (tvSeriesCategory == MovieManiacConstants.CATEGORY_MOST_POPULAR_TV_SERIES) {
            MovieModel.getInstance().loadMostPopularTVSeriesList(pageNumber, isForce);
        }
    }

    public void forceRefresh() {
        pageNumber = MovieModel.INITIAL_PAGE_NUMBER;
        SettingsUtils.resetPageNumber(tvSeriesCategory);
        loadNewMovieList(true);
    }

    public void loadMoreData() {
        if (pageNumber != MovieModel.INITIAL_PAGE_NUMBER) {
            loadNewMovieList(false);
        }
    }
}
