package net.aung.moviemaniac.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aung on 12/16/15.
 */
public class SpokenLanguageVO {

    @SerializedName("iso_639_1")
    private String iso_639_1;

    @SerializedName("name")
    private String name;

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getName() {
        return name;
    }
}
