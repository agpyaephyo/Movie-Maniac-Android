package net.aung.moviemaniac.data.restapi.responses;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.data.vos.TVSeriesVO;

import java.util.ArrayList;

/**
 * Created by aung on 3/19/16.
 */
public class TVSeriesListResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private ArrayList<TVSeriesVO> tvSeriesList;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public ArrayList<TVSeriesVO> getTvSeriesList() {
        return tvSeriesList;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
