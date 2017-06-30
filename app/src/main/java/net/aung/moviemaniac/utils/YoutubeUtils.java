package net.aung.moviemaniac.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by aungpyae on 25/4/15.
 */
public class YoutubeUtils {

    private static YoutubeUtils objInstance;

    private YoutubeUtils() {

    }

    public static YoutubeUtils getObjInstance() {
        if (objInstance == null) {
            objInstance = new YoutubeUtils();
        }

        return objInstance;
    }

    public void playYoutubeVideo(Activity activity, String videoId) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
            intent.putExtra("force_fullscreen", true);
            activity.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + videoId));
            activity.startActivity(intent);
        }
    }

    public static String getFullUrlFromYoutubeVideo(String videoId) {
        return "http://www.youtube.com/watch?v=" + videoId;
    }
}
