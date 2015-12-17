package net.aung.moviemaniac.controllers;

import net.aung.moviemaniac.data.vos.MovieVO;

/**
 * Created by aung on 12/15/15.
 */
public interface MovieItemController extends BaseController {
    void onNavigateToDetail(MovieVO movie);
}
