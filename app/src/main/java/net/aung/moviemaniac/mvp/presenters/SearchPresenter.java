package net.aung.moviemaniac.mvp.presenters;

import net.aung.moviemaniac.activities.SearchActivity;
import net.aung.moviemaniac.data.model.MovieModel;
import net.aung.moviemaniac.events.DataEvent;
import net.aung.moviemaniac.mvp.views.SearchView;

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
        if (mSearchCategory == SearchActivity.SEARCH_TYPE_MOVIE) {
            //search movies
            MovieModel.getInstance().searchOnMovies(query);
        } else if (mSearchCategory == SearchActivity.SEARCH_TYPE_TV_SERIES) {
            //search tv series
            MovieModel.getInstance().searchOnTVSeries(query);
        }
    }

    public void onEventMainThread(DataEvent.SearchedMovieEvent event) {
        mSearchView.displaySearchResultMovie(event.getResponse().getResults());
    }

    public void onEventMainThread(DataEvent.SearchedTVSeriesEvent event) {
        mSearchView.displaySearchResultTVSeries(event.getResponse().getTvSeriesList());
    }
}
