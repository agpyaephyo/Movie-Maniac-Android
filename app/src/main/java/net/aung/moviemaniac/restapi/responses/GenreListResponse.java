package net.aung.moviemaniac.restapi.responses;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.data.vos.GenreVO;

import java.util.ArrayList;

/**
 * Created by aung on 12/16/15.
 */
public class GenreListResponse {

    @SerializedName("genres")
    private ArrayList<GenreVO> genreList;

    public ArrayList<GenreVO> getGenreList() {
        return genreList;
    }
}
