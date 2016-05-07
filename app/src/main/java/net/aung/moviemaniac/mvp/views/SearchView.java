package net.aung.moviemaniac.mvp.views;

import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.data.vos.TVSeriesVO;

import java.util.List;

/**
 * Created by aung on 3/21/16.
 */
public interface SearchView {

    void showSearchResultMovie(List<MovieVO> movieSearchResult);

    void showSearchResultTVSeries(List<TVSeriesVO> tvSeriesSearchResult);

    void showMsgFailInSearch(String message);

    void showLoading();

    void showMsgSpecialCharacterDetected();

    void showMsgNoNetwork();
}
