package net.aung.moviemaniac.mvp.views;

import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.data.vos.TrailerVO;

import java.util.List;

/**
 * Created by aung on 12/15/15.
 */
public interface MovieDetailView {
    void displayMovieDetail(MovieVO movie);
    void displayTrailerList(List<TrailerVO> trailerList);
}
