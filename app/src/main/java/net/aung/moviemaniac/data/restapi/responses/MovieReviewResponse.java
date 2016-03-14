package net.aung.moviemaniac.data.restapi.responses;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.data.vos.MovieReviewVO;

import java.util.ArrayList;

/**
 * Created by aung on 3/14/16.
 */
public class MovieReviewResponse {

    @SerializedName("id")
    private int movieId;

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private ArrayList<MovieReviewVO> reviewList;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public int getMovieId() {
        return movieId;
    }

    public int getPage() {
        return page;
    }

    public ArrayList<MovieReviewVO> getReviewList() {
        return reviewList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
