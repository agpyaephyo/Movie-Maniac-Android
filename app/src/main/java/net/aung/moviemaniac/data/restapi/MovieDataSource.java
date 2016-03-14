package net.aung.moviemaniac.data.restapi;

/**
 * Created by aung on 12/15/15.
 */
public interface MovieDataSource {
    void loadDiscoverMovieList(int pageNumber, String sortBy, boolean isForce);
    void loadPopularMovies(int pageNumber, boolean isForce);
    void loadTopRatedMovies(int pageNumber, boolean isForce);
    void loadMovieTrailers(int movieId);
    void loadMovieDetail(int movieId);
    void loadGenreList();
}
