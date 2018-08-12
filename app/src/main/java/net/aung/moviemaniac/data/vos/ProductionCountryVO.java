package net.aung.moviemaniac.data.vos;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.data.persistence.MovieContract;

import java.util.ArrayList;

/**
 * Created by aung on 12/16/15.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = MovieVO.class, parentColumns = "id", childColumns = "movieId"),
}, tableName = "production_country")
public class ProductionCountryVO {

    @SerializedName("iso_3166_1")
    @PrimaryKey
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

    public static ProductionCountryVO parseFromCursor(Cursor productionCountryCursor) {
        ProductionCountryVO productionCountry = new ProductionCountryVO();
        productionCountry.iso_3166_1 = productionCountryCursor.getString(productionCountryCursor.getColumnIndex(MovieContract.ProductionCountryEntry.COLUMN_ISO_3166_1));
        productionCountry.name = productionCountryCursor.getString(productionCountryCursor.getColumnIndex(MovieContract.ProductionCountryEntry.COLUMN_NAME));
        return productionCountry;
    }

    public static ArrayList<ProductionCountryVO> loadProductionCountryListByMovieId(int movieId) {
        Context context = MovieManiacApp.getContext();
        ArrayList<ProductionCountryVO> productionCountryList = new ArrayList<>();
        Cursor productionCountryCursor = context.getContentResolver().query(MovieContract.MovieProductionCountryEntry.CONTENT_URI,
                null,
                MovieContract.MovieProductionCountryEntry.COLUMN_MOVIE_ID + " = ?",
                new String[]{String.valueOf(movieId)},
                null);

        if (productionCountryCursor != null && productionCountryCursor.moveToFirst()) {
            do {
                productionCountryList.add(ProductionCountryVO.parseFromCursor(productionCountryCursor));

            } while (productionCountryCursor.moveToNext());
            productionCountryCursor.close();
        }

        return productionCountryList;
    }
}
