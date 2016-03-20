package net.aung.moviemaniac.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.data.persistence.MovieContract;
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
    private boolean isStar;
    private boolean isDetailLoaded;

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

    public void setGenreList(ArrayList<GenreVO> genreList) {
        this.genreList = genreList;
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

    public boolean isStar() {
        return isStar;
    }

    public boolean isDetailLoaded() {
        return isDetailLoaded;
    }

    public static void saveTVSeriesFromList(ArrayList<TVSeriesVO> loadedTVSeriesList, @MovieManiacConstants.TVSeriesType int tvSeriesType) {
        ContentValues[] tvSeriesCVs = new ContentValues[loadedTVSeriesList.size()];
        for (int index = 0; index < tvSeriesCVs.length; index++) {
            TVSeriesVO tvSeries = loadedTVSeriesList.get(index);
            tvSeries.setTvSeriesType(tvSeriesType);

            tvSeriesCVs[index] = tvSeries.parseToContentValues();

            if (tvSeries.genreIds != null && tvSeries.genreIds.length > 0) {
                ContentValues[] tvSeriesGenreListCVs = new ContentValues[tvSeries.genreIds.length];
                for (int index_two = 0; index_two < tvSeriesGenreListCVs.length; index_two++) {
                    ContentValues tvSeriesGenreCV = new ContentValues();
                    tvSeriesGenreCV.put(MovieContract.TVSeriesGenreEntry.COLUMN_GENRE_ID, tvSeries.genreIds[index_two]);
                    tvSeriesGenreCV.put(MovieContract.TVSeriesGenreEntry.COLUMN_TV_SERIES_ID, tvSeries.getTvSerieId());

                    tvSeriesGenreListCVs[index_two] = tvSeriesGenreCV;
                }

                Context context = MovieManiacApp.getContext();
                int insertedCount = context.getContentResolver().bulkInsert(MovieContract.TVSeriesGenreEntry.CONTENT_URI, tvSeriesGenreListCVs);
            }
        }

        //Bulk Insert movie.
        Context context = MovieManiacApp.getContext();
        int insertedCount = context.getContentResolver().bulkInsert(MovieContract.TVSeriesEntry.CONTENT_URI, tvSeriesCVs);

        Log.d(MovieManiacApp.TAG, "Bulk inserted into tv_series table with tv series type - " + tvSeriesType + " : " + insertedCount);
    }

    private ContentValues parseToContentValues() {
        ContentValues tvSeriesCV = new ContentValues();
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_ID, tvSerieId);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_POSTER_PATH, posterPath);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_OVERVIEW, overview);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_FIRST_AIR_DATE, firstAirDateText);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_NAME, name);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_ORIGINAL_LANGUAGE, originalLanguage);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_ORIGINAL_NAME, orignalName);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_BACKDROP_PATH, backdropPath);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_POPULARITY, popularity);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_VOTE_COUNT, voteCount);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_VOTE_AVERAGE, voteAverage);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_TYPE, tvSeriesType);

        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_HOMEPAGE, homepage);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_LAST_AIR_DATE, lastAirDateText);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_NUMBER_OF_EPISODES, numberOfEpisodes);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_NUMBER_OF_SEASON, numberOfSeasons);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_STATUS, status);

        return tvSeriesCV;
    }

    public static TVSeriesVO parseFromListCursor(Cursor data) {
        TVSeriesVO tvSeries = new TVSeriesVO();

        tvSeries.tvSerieId = data.getInt(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_ID));
        tvSeries.posterPath = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_POSTER_PATH));
        tvSeries.overview = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_OVERVIEW));
        tvSeries.popularity = data.getFloat(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_POPULARITY));
        tvSeries.backdropPath = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_BACKDROP_PATH));
        tvSeries.voteAverage = data.getDouble(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_VOTE_AVERAGE));
        tvSeries.firstAirDateText = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_FIRST_AIR_DATE));
        tvSeries.originalLanguage = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_ORIGINAL_LANGUAGE));
        tvSeries.voteCount = data.getInt(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_VOTE_COUNT));
        tvSeries.name = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_NAME));
        tvSeries.orignalName = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_ORIGINAL_NAME));
        tvSeries.isStar = data.getInt(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_IS_STAR)) == 1;
        tvSeries.tvSeriesType = data.getInt(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_TYPE));

        return tvSeries;
    }
}
