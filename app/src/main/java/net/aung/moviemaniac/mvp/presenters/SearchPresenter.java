package net.aung.moviemaniac.mvp.presenters;

import net.aung.moviemaniac.activities.SearchActivity;
import net.aung.moviemaniac.data.model.MovieModel;
import net.aung.moviemaniac.events.DataEvent;
import net.aung.moviemaniac.mvp.views.SearchView;
import net.aung.moviemaniac.utils.GAUtils;
import net.aung.moviemaniac.utils.MovieManiacConstants;
import net.aung.moviemaniac.utils.NetworkUtils;

/**
 * Created by aung on 3/21/16.
 */
public class SearchPresenter extends BasePresenter {

    private int mSearchCategory;
    private SearchView mSearchView;

    public SearchPresenter(int mSearchCategory, SearchView searchView) {
        this.mSearchCategory = mSearchCategory;
        this.mSearchView = searchView;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void search(String query) {
        if (NetworkUtils.getInstance().isOnline()) {
            if (query.matches(MovieManiacConstants.REG_VALID_SEARCH_QUERY_RANGE)) {
                mSearchView.showLoading();
                if (mSearchCategory == SearchActivity.SEARCH_TYPE_MOVIE) {
                    //search movies
                    GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_SEARCH_MOVIES, query);
                    MovieModel.getInstance().searchOnMovies(query);
                } else if (mSearchCategory == SearchActivity.SEARCH_TYPE_TV_SERIES) {
                    //search tv series
                    GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_SEARCH_TV_SERIES, query);
                    MovieModel.getInstance().searchOnTVSeries(query);
                } else {

                }
            } else {
                mSearchView.showMsgSpecialCharacterDetected();
            }
        } else {
            mSearchView.showMsgNoNetwork();
        }
    }

    public void onEventMainThread(DataEvent.SearchedMovieEvent event) {
        mSearchView.showSearchResultMovie(event.getResponse().getResults());
    }

    public void onEventMainThread(DataEvent.SearchedTVSeriesEvent event) {
        mSearchView.showSearchResultTVSeries(event.getResponse().getTvSeriesList());
    }

    public void onEventMainThread(DataEvent.FailedToLoadDataEvent event) {
        mSearchView.showMsgFailInSearch(event.getMessage());
    }
}
