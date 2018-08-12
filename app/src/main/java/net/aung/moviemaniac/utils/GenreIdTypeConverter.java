package net.aung.moviemaniac.utils;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by aung on 7/30/17.
 */
public class GenreIdTypeConverter {

    @TypeConverter
    public static String toString(int [] genreIds) {
        StringBuilder genreIdsString = new StringBuilder();
        for(int genreId : genreIds) {
            genreIdsString.append(genreId);
            genreIdsString.append(",");
        }
        genreIdsString.deleteCharAt(genreIdsString.length()-1);
        return genreIdsString.toString();
    }

    @TypeConverter
    public static int[] toIntegerArray(String genreIdsString) {
        String [] genreIdsStringArray = genreIdsString.split(",");
        int [] genreIds = new int[genreIdsStringArray.length];
        int index = 0;
        for(String genreIdString : genreIdsStringArray) {
            genreIds[index++] = Integer.parseInt(genreIdString);
        }
        return genreIds;
    }
}
