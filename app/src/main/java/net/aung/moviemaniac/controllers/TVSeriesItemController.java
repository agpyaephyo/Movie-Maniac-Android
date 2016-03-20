package net.aung.moviemaniac.controllers;

import net.aung.moviemaniac.data.vos.TVSeriesVO;

/**
 * Created by aung on 12/15/15.
 */
public interface TVSeriesItemController extends BaseController {
    void onNavigateToDetail(TVSeriesVO tvSeries);
}
