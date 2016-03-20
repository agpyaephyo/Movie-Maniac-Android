package net.aung.moviemaniac.mvp.views;

import net.aung.moviemaniac.data.vos.TVSeasonVO;
import net.aung.moviemaniac.data.vos.TVSeriesVO;
import net.aung.moviemaniac.data.vos.TrailerVO;

import java.util.List;

/**
 * Created by aung on 12/15/15.
 */
public interface TVSeriesDetailView {
    void displayTVSeriesDetail(TVSeriesVO tvSeries);

    void displaySeasonList(List<TVSeasonVO> seasonList);

    void displayTrailerList(List<TrailerVO> trailerList);

    //void displayReviewList(List<MovieReviewVO> reviewList);
}
