package net.aung.moviemaniac.data.vos;

import android.content.ContentValues;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.utils.MovieManiacConstants;

import java.util.ArrayList;

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

    private int tvSeriesType;

    @SerializedName("genres")
    private ArrayList<GenreVO> genreList; //detail

    @SerializedName("homepage")
    private String homepage; //detail

    @SerializedName("last_air_date")
    private String lastAirDateText;

    @SerializedName("networks")
    private ArrayList<TVNetworkVO> networkList;

    @SerializedName("number_of_episodes")
    private int numberOfEpisodes;

    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    @SerializedName("production_companies")
    private ArrayList<ProductionCompanyVO> productionCompanyList; //detail

    @SerializedName("seasons")
    private ArrayList<TVSeasonVO> seasonList;

    @SerializedName("status")
    private String status;

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

    public int getTvSeriesType() {
        return tvSeriesType;
    }

    public void setTvSeriesType(int tvSeriesType) {
        this.tvSeriesType = tvSeriesType;
    }

    public ArrayList<GenreVO> getGenreList() {
        return genreList;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getLastAirDateText() {
        return lastAirDateText;
    }

    public ArrayList<TVNetworkVO> getNetworkList() {
        return networkList;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<ProductionCompanyVO> getProductionCompanyList() {
        return productionCompanyList;
    }

    public ArrayList<TVSeasonVO> getSeasonList() {
        return seasonList;
    }

    public String getStatus() {
        return status;
    }

    public static void saveTVSeriesFromList(ArrayList<TVSeriesVO> loadedTVSeriesList, @MovieManiacConstants.TVSeriesType int tvSeriesType) {
        ContentValues[] tvSeriesCVs = new ContentValues[loadedTVSeriesList.size()];
        for (int index = 0; index < tvSeriesCVs.length; index++) {
            TVSeriesVO tvSeries = loadedTVSeriesList.get(index);
            tvSeries.setTvSeriesType(tvSeriesType);

            tvSeriesCVs[index] = tvSeries.parseToContentValues();
        }
    }

    private ContentValues parseToContentValues() {
        ContentValues tvSeriesCV = new ContentValues();
        return tvSeriesCV;
    }
}
