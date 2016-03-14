package net.aung.moviemaniac.data.vos;

import android.content.ContentValues;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.data.persistence.MovieContract;

import java.util.ArrayList;

/**
 * Created by aung on 12/16/15.
 */
public class ProductionCountryVO {

    @SerializedName("iso_3166_1")
    private String iso_3166_1;

    @SerializedName("name")
    private String name;

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public String getName() {
        return name;
    }

    private ContentValues parseToContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.ProductionCountryEntry.COLUMN_ISO_3166_1, iso_3166_1);
        contentValues.put(MovieContract.ProductionCountryEntry.COLUMN_NAME, name);
        return contentValues;
    }

    /**
     * for production_company table.
     * @param productionCountryList
     * @return
     */
    public static ContentValues[] parseToContentValueArray(ArrayList<ProductionCountryVO> productionCountryList) {
        ContentValues[] contentValuesArray = new ContentValues[productionCountryList.size()];

        for (int index = 0; index < contentValuesArray.length; index++) {
            ProductionCountryVO productionCountry = productionCountryList.get(index);
            contentValuesArray[index] = productionCountry.parseToContentValues();
        }

        return contentValuesArray;
    }

    /**
     * for movie_production_company table.
     * @param productionCountryList
     * @param movieId
     * @return
     */
    public static ContentValues[] parseToContentValueArray(ArrayList<ProductionCountryVO> productionCountryList, int movieId) {
        ContentValues[] contentValuesArray = new ContentValues[productionCountryList.size()];

        for (int index = 0; index < contentValuesArray.length; index++) {
            ProductionCountryVO productionCountry = productionCountryList.get(index);

            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.MovieProductionCountryEntry.COLUMN_ISO_3166_1, productionCountry.getIso_3166_1());
            contentValues.put(MovieContract.MovieProductionCountryEntry.COLUMN_MOVIE_ID, movieId);

            contentValuesArray[index] = contentValues;
        }

        return contentValuesArray;
    }
}
