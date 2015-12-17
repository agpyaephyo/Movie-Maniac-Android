package net.aung.moviemaniac.utils;

import android.content.Context;

import net.aung.moviemaniac.PopularMoviesApplication;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by aung on 12/12/15.
 */
public class JsonUtils {

    private static final String PATH_DUMMY_DATA = "dummy_data";

    private static JsonUtils objInstace;

    private Context context;

    public static JsonUtils getInstance() {
        if (objInstace == null) {
            objInstace = new JsonUtils();
        }

        return objInstace;
    }

    private JsonUtils() {
        context = PopularMoviesApplication.getContext();
    }

    /**
     * Read text from assets folder.
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    private byte[] readJsonFile(String fileName) throws IOException {
        InputStream inStream = context.getAssets().open(fileName);
        int size = inStream.available();
        byte[] buffer = new byte[size];
        inStream.read(buffer);
        inStream.close();
        return buffer;
    }

    /**
     * @param fileName - name of Json File.
     * @return JSONObject from loaded file.
     * @throws IOException
     * @throws JSONException
     */
    public String loadDummyData(String fileName) throws IOException, JSONException {
        byte[] buffer = readJsonFile(PATH_DUMMY_DATA + "/" + fileName);
        return new String(buffer, "UTF-8").toString();
    }
}
