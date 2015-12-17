package net.aung.moviemaniac.mvp.views;

import net.aung.moviemaniac.data.vos.MovieVO;

import java.util.List;

/**
 * Created by aung on 12/12/15.
 */
public interface MovieListView {

    boolean isMovieListEmpty();
    void displayMovieList(List<MovieVO> movieList, boolean isToAppend);
    void displayFailToLoadData(String message);
}
