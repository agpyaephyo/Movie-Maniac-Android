package net.aung.moviemaniac.data.vos;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.utils.DateFormatUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Immutable.
 * Created by aung on 12/12/15.
 */
public class MovieVO {

    public static final String IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/";
    public static final String IMAGE_SIZE_W185 = "w185";
    public static final String IMAGE_SIZE_W500 = "w500";

    private static final String RUNTIME_FORMAT = "%1$d hrs %2$d mins";

    @SerializedName("id")
    private int id;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDateText;

    @SerializedName("genre_ids")
    private int[] genreIds;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("popularity")
    private float popularity;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("vote_average")
    private float voteAverage;

    @SerializedName("adult")
    private boolean isAdult;

    @SerializedName("belongs_to_collection")
    private CollectionVO collection;

    @SerializedName("budget")
    private long budget;

    @SerializedName("genres")
    private ArrayList<GenreVO> genreList;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("production_companies")
    private ArrayList<ProductionCompanyVO> productionCompanyList;

    @SerializedName("production_countries")
    private ArrayList<ProductionCountryVO> productionCountryList;

    @SerializedName("revenue")
    private long revenue;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("spoken_languages")
    private ArrayList<SpokenLanguageVO> spokenLanguageList;

    @SerializedName("status")
    private String status;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("video")
    private boolean isVideo;

    private List<TrailerVO> trailerList;
    private boolean isDetailLoaded;
    private Date releaseDate;

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return IMAGE_BASE_PATH + IMAGE_SIZE_W500 + posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public Date getReleaseDate() {
        if (releaseDate == null) {
            try {
                releaseDate = DateFormatUtils.sdf_yyyy_mm_dd.parse(releaseDateText);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return releaseDate;
    }

    public String getReleaseDateDisplay() {
        if (releaseDate == null) {
            try {
                releaseDate = DateFormatUtils.sdf_yyyy_mm_dd.parse(releaseDateText);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return DateFormatUtils.sdf_MMMM_yyyy.format(releaseDate);
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return IMAGE_BASE_PATH + IMAGE_SIZE_W500 + backdropPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public String getVoteAverage() {
        return String.format("%.1f", voteAverage);
    }

    public CollectionVO getCollection() {
        return collection;
    }

    public long getBudget() {
        return budget;
    }

    public void setGenreList(ArrayList<GenreVO> genreList) {
        this.genreList = genreList;
    }

    public ArrayList<GenreVO> getGenreList() {
        return genreList;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public ArrayList<ProductionCompanyVO> getProductionCompanyList() {
        return productionCompanyList;
    }

    public ArrayList<ProductionCountryVO> getProductionCountryList() {
        return productionCountryList;
    }

    public long getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getRuntimeDisplay() {
        int hour = runtime / 60;
        int minute = runtime % 60;
        return String.format(RUNTIME_FORMAT, hour, minute);
    }

    public ArrayList<SpokenLanguageVO> getSpokenLanguageList() {
        return spokenLanguageList;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public List<TrailerVO> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<TrailerVO> trailerList) {
        this.trailerList = trailerList;
    }

    public boolean isDetailLoaded() {
        return isDetailLoaded;
    }

    public void setIsDetailLoaded(boolean isDetailLoaded) {
        this.isDetailLoaded = isDetailLoaded;
    }
}
