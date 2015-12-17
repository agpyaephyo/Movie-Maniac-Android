package net.aung.moviemaniac.restapi;

import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.restapi.responses.MovieDiscoverResponse;
import net.aung.moviemaniac.restapi.responses.MovieTrailerResponse;
import net.aung.moviemaniac.restapi.responses.GenreListResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by aung on 12/15/15.
 */
public interface TheMovieApi {

    @GET("discover/movie")
    Call<MovieDiscoverResponse> discoverMovieList(
            @Query("api_key") String apiKey,
            @Query("page") int pageNumber,
            @Query("sort_by") String sortBy
    );

    @GET("movie/{movieId}/videos")
    Call<MovieTrailerResponse> getTrailersByMovieId(
            @Path("movieId") int movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movieId}")
    Call<MovieVO> getMovieDetailByMovieId(
            @Path("movieId") int movieId,
            @Query("api_key") String apiKey
    );

    @GET("genre/movie/list")
    Call<GenreListResponse> getGenreList(
            @Query("api_key") String apiKey
    );
}
