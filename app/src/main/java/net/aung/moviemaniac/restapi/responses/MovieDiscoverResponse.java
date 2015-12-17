package net.aung.moviemaniac.restapi.responses;

import net.aung.moviemaniac.data.vos.MovieVO;

import java.util.ArrayList;

/**
 * To contain the response from Movie/Discover api.
 * Created by aung on 12/12/15.
 */
public class MovieDiscoverResponse {

    private int page;
    private ArrayList<MovieVO> results;
    private int total_results;
    private int total_pages;

    public int getPage() {
        return page;
    }

    public ArrayList<MovieVO> getResults() {
        return results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }
}
