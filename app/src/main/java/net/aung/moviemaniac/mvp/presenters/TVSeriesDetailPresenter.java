package net.aung.moviemaniac.mvp.presenters;

import net.aung.moviemaniac.data.model.MovieModel;
import net.aung.moviemaniac.data.vos.TVSeriesVO;
import net.aung.moviemaniac.events.DataEvent;
import net.aung.moviemaniac.mvp.views.TVSeriesDetailView;

/**
 * Created by aung on 12/15/15.
 */
public class TVSeriesDetailPresenter extends BasePresenter {

    private TVSeriesDetailView tvSeriesDetailView;
    private MovieModel movieModel;

    public TVSeriesDetailPresenter(TVSeriesDetailView tvSeriesDetailView) {
        this.tvSeriesDetailView = tvSeriesDetailView;
        movieModel = MovieModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void loadTVSeriesDetailFromNetwork(TVSeriesVO tvSeries) {
        movieModel.loadTrailerListByTVSeriesId(tvSeries.getTvSerieId());
        movieModel.loadTVSeriesDetailByTVSeriesId(tvSeries);
    }

    public void loadTVSeriesDetailFromNetwork(int tVSeriesId) {
        movieModel.loadTrailerListByTVSeriesId(tVSeriesId);
        movieModel.loadTVSeriesDetailByTVSeriesId(tVSeriesId);
    }

    public void onEventMainThread(DataEvent.LoadedTVSeriesDetailEvent event) {
        //MovieVO movieWithDetail = event.getTvSeries();
        //tvSeriesDetailView.displayMovieDetail(movieWithDetail);
    }

    public void onEventMainThread(DataEvent.LoadedMovieTrailerEvent event) {
        //tvSeriesDetailView.displayTrailerList(event.getResponse().getTrailerList());
    }

    public void onEventMainThread(DataEvent.LoadedMovieReviewEvent event) {

    }
}
