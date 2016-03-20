package net.aung.moviemaniac.data.vos;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.utils.MovieManiacConstants;

/**
 * Created by aung on 3/19/16.
 */
public class TVSeriesVO {

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("popularity")
    private float popularity;

    @SerializedName("id")
    private int tvSerieId;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("overview")
    private String overview;

    @SerializedName("first_air_date")
    private String firstAirDateText;

    @SerializedName("origin_country")
    private String[] originCountries;

    @SerializedName("genre_ids")
    private int[] genreIds;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("name")
    private String name;

    @SerializedName("original_name")
    private String orignalName;

    public String getPosterPath() {
        return MovieManiacConstants.IMAGE_BASE_PATH + MovieManiacConstants.IMAGE_SIZE_W500 + posterPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public int getTvSerieId() {
        return tvSerieId;
    }

    public String getBackdropPath() {
        return MovieManiacConstants.IMAGE_BASE_PATH + MovieManiacConstants.IMAGE_SIZE_W500 + backdropPath;
    }

    public String getVoteAverage() {
        return String.format("%.1f", voteAverage);
    }

    public String getOverview() {
        return overview;
    }

    public String getFirstAirDateText() {
        return MovieManiacApp.getContext().getString(R.string.format_first_air_date, firstAirDateText);
    }

    public String[] getOriginCountries() {
        return originCountries;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getName() {
        return name;
    }

    public String getOrignalName() {
        return orignalName;
    }
}
