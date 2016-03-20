package net.aung.moviemaniac.mvp.views;

import net.aung.moviemaniac.data.vos.TVSeriesVO;

import java.util.List;

/**
 * Created by aung on 12/12/15.
 */
public interface TVSeriesListView {

    boolean isTVSeriesListEmpty();

    void displayTVSeriesList(List<TVSeriesVO> tvSeriesList, boolean isToAppend);

    void displayFailToLoadData(String message);
}
