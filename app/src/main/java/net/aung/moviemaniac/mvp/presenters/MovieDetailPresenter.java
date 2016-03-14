package net.aung.moviemaniac.mvp.presenters;

import net.aung.moviemaniac.data.model.MovieModel;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.events.DataEvent;
import net.aung.moviemaniac.mvp.views.MovieDetailView;

/**
 * Created by aung on 12/15/15.
 */
public class MovieDetailPresenter extends BasePresenter {

    private MovieDetailView movieDetailView;
    private MovieModel movieModel;

    public MovieDetailPresenter(MovieDetailView movieDetailView) {
        this.movieDetailView = movieDetailView;
        movieModel = MovieModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void loadMovieDetailFromNetwork(MovieVO movie) {
        movieModel.loadMovieDetailByMovieId(movie);
        movieModel.loadTrailerListByMovieId(movie.getId());
    }

    public void onEventMainThread(DataEvent.LoadedMovieTrailerEvent event) {
        //movieDetailView.displayTrailerList(event.getResponse().getTrailerList());
    }

    public void onEventMainThread(DataEvent.LoadedMovieDetailEvent event) {
        MovieVO movieWithDetail = event.getMovie();
        //movieDetailView.displayMovieDetail(movieWithDetail);
    }
}
