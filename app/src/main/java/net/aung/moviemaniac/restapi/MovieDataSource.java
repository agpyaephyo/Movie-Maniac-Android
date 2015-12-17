package net.aung.moviemaniac.restapi;

/**
 * Created by aung on 12/15/15.
 */
public interface MovieDataSource {
    void discoverMovieList(int pageNumber, String sortBy, boolean isForce);
    void getMovieTrailers(int movieId);
    void getMovieDetail(int movieId);
    void getGenreList();
}
