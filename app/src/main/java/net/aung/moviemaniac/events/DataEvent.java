package net.aung.moviemaniac.events;

import net.aung.moviemaniac.data.restapi.responses.GenreListResponse;
import net.aung.moviemaniac.data.restapi.responses.MovieListResponse;
import net.aung.moviemaniac.data.restapi.responses.MovieReviewResponse;
import net.aung.moviemaniac.data.restapi.responses.TrailerResponse;
import net.aung.moviemaniac.data.restapi.responses.TVSeriesListResponse;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.data.vos.TVSeriesVO;

import java.util.ArrayList;

/**
 * Created by aung on 12/12/15.
 */
public class DataEvent {

    public static class LoadedMovieListEvent {
        private MovieListResponse response;
        private boolean isForce;

        public LoadedMovieListEvent(MovieListResponse response, boolean isForce) {
            this.response = response;
            this.isForce = isForce;
        }

        public MovieListResponse getResponse() {
            return response;
        }

        public boolean isForce() {
            return isForce;
        }
    }

    public static class LoadedNowPlayingMovieListEvent extends LoadedMovieListEvent {

        public LoadedNowPlayingMovieListEvent(MovieListResponse response, boolean isForce) {
            super(response, isForce);
        }
    }

    public static class LoadedUpcomingMovieListEvent extends LoadedMovieListEvent {

        public LoadedUpcomingMovieListEvent(MovieListResponse response, boolean isForce) {
            super(response, isForce);
        }
    }

    public static class LoadedMostPopularMovieListEvent extends LoadedMovieListEvent {

        public LoadedMostPopularMovieListEvent(MovieListResponse response, boolean isForce) {
            super(response, isForce);
        }
    }

    public static class LoadedTopRatedMovieListEvent extends LoadedMovieListEvent {

        public LoadedTopRatedMovieListEvent(MovieListResponse response, boolean isForce) {
            super(response, isForce);
        }
    }

    public static class LoadedTVSerieListEvent {
        private TVSeriesListResponse response;
        private boolean isForce;

        public LoadedTVSerieListEvent(TVSeriesListResponse response, boolean isForce) {
            this.response = response;
            this.isForce = isForce;
        }

        public TVSeriesListResponse getResponse() {
            return response;
        }

        public boolean isForce() {
            return isForce;
        }
    }

    public static class LoadedPopularTVSeriesListEvent extends LoadedTVSerieListEvent {

        public LoadedPopularTVSeriesListEvent(TVSeriesListResponse response, boolean isForce) {
            super(response, isForce);
        }
    }

    public static class LoadedTopRatedTVSeriesListEvent extends LoadedTVSerieListEvent {

        public LoadedTopRatedTVSeriesListEvent(TVSeriesListResponse response, boolean isForce) {
            super(response, isForce);
        }
    }

    public static class ShowMovieListEvent {
        private ArrayList<MovieVO> movieList;
        private boolean isForce;
        private int pageNumber;

        public ShowMovieListEvent(ArrayList<MovieVO> movieList, boolean isForce, int pageNumber) {
            this.movieList = movieList;
            this.isForce = isForce;
            this.pageNumber = pageNumber;
        }

        public ArrayList<MovieVO> getMovieList() {
            return movieList;
        }

        public boolean isForce() {
            return isForce;
        }

        public int getPageNumber() {
            return pageNumber;
        }
    }

    public static class ShowMostPopularMovieListEvent extends ShowMovieListEvent {

        public ShowMostPopularMovieListEvent(ArrayList<MovieVO> movieList, boolean isForce, int pageNumber) {
            super(movieList, isForce, pageNumber);
        }
    }

    public static class ShowUpcomingMovieListEvent extends ShowMovieListEvent {

        public ShowUpcomingMovieListEvent(ArrayList<MovieVO> movieList, boolean isForce, int pageNumber) {
            super(movieList, isForce, pageNumber);
        }
    }

    public static class ShowTopRatedMovieListEvent extends ShowMovieListEvent {

        public ShowTopRatedMovieListEvent(ArrayList<MovieVO> movieList, boolean isForce, int pageNumber) {
            super(movieList, isForce, pageNumber);
        }
    }

    public static class ShowNowPlayingMovieListEvent extends ShowMovieListEvent {

        public ShowNowPlayingMovieListEvent(ArrayList<MovieVO> movieList, boolean isForce, int pageNumber) {
            super(movieList, isForce, pageNumber);
        }
    }

    public static class FailedToLoadDataEvent {
        private String message;

        public FailedToLoadDataEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class LoadedMovieDetailEvent {
        private MovieVO movie;

        public LoadedMovieDetailEvent(MovieVO movie) {
            this.movie = movie;
        }

        public MovieVO getMovie() {
            return movie;
        }
    }

    public static class LoadedMovieTrailerEvent {
        private TrailerResponse response;
        private int movieId;

        public LoadedMovieTrailerEvent(TrailerResponse response, int movieId) {
            this.response = response;
            this.movieId = movieId;
        }

        public TrailerResponse getResponse() {
            return response;
        }

        public int getMovieId() {
            return movieId;
        }
    }

    public static class LoadedGenreListEvent {
        private GenreListResponse response;

        public LoadedGenreListEvent(GenreListResponse response) {
            this.response = response;
        }

        public GenreListResponse getResponse() {
            return response;
        }
    }

    public static class LoadedMovieReviewEvent {
        private MovieReviewResponse response;

        public LoadedMovieReviewEvent(MovieReviewResponse response) {
            this.response = response;
        }

        public MovieReviewResponse getResponse() {
            return response;
        }
    }

    public static class ShowTVSeriesListEvent {
        private ArrayList<TVSeriesVO> tvSeriesList;
        private boolean isForce;
        private int pageNumber;

        public ShowTVSeriesListEvent(ArrayList<TVSeriesVO> tvSeriesList, boolean isForce, int pageNumber) {
            this.tvSeriesList = tvSeriesList;
            this.isForce = isForce;
            this.pageNumber = pageNumber;
        }

        public ArrayList<TVSeriesVO> getTvSeriesList() {
            return tvSeriesList;
        }

        public boolean isForce() {
            return isForce;
        }

        public int getPageNumber() {
            return pageNumber;
        }
    }

    public static class ShowPopularTVSeriesListEvent extends ShowTVSeriesListEvent {

        public ShowPopularTVSeriesListEvent(ArrayList<TVSeriesVO> tvSeriesList, boolean isForce, int pageNumber) {
            super(tvSeriesList, isForce, pageNumber);
        }
    }

    public static class ShowTopRatedTVSeriesListEvent extends ShowTVSeriesListEvent {

        public ShowTopRatedTVSeriesListEvent(ArrayList<TVSeriesVO> tvSeriesList, boolean isForce, int pageNumber) {
            super(tvSeriesList, isForce, pageNumber);
        }
    }

    public static class LoadedTVSeriesDetailEvent {
        private TVSeriesVO tvSeries;

        public LoadedTVSeriesDetailEvent(TVSeriesVO tvSeries) {
            this.tvSeries = tvSeries;
        }

        public TVSeriesVO getTvSeries() {
            return tvSeries;
        }
    }

    public static class LoadedTVSeriesTrailerEvent {
        private TrailerResponse response;
        private int tvSeriesId;

        public LoadedTVSeriesTrailerEvent(TrailerResponse response, int tvSeriesId) {
            this.response = response;
            this.tvSeriesId = tvSeriesId;
        }

        public TrailerResponse getResponse() {
            return response;
        }

        public int getTvSeriesId() {
            return tvSeriesId;
        }
    }

    public static class SearchedMovieEvent {
        private MovieListResponse response;
        private String query;

        public SearchedMovieEvent(MovieListResponse response, String query) {
            this.response = response;
            this.query = query;
        }

        public MovieListResponse getResponse() {
            return response;
        }

        public String getQuery() {
            return query;
        }
    }

    public static class SearchedTVSeriesEvent {
        private TVSeriesListResponse response;
        private String query;

        public SearchedTVSeriesEvent(TVSeriesListResponse response, String query) {
            this.response = response;
            this.query = query;
        }

        public TVSeriesListResponse getResponse() {
            return response;
        }

        public String getQuery() {
            return query;
        }
    }
}
