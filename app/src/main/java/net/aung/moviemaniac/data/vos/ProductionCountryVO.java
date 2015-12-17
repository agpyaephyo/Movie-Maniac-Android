package net.aung.moviemaniac.data.vos;

import com.google.gson.annotations.SerializedName;

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
}
