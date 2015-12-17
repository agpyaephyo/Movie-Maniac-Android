package net.aung.moviemaniac.utils;

import com.google.gson.Gson;

/**
 * Created by aung on 12/12/15.
 */
public class CommonInstances {

    private static Gson gson = new Gson();

    public static Gson getGsonInstance() {
        return gson;
    }
}
