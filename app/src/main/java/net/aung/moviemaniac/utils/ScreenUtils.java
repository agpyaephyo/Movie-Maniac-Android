package net.aung.moviemaniac.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by aung on 12/16/15.
 */
public class ScreenUtils {

    /**
     * Put the content below the status bar and make the status bar translucent.
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusbarTranslucent (boolean isToTranslucent, Activity activity) {
        if(isToTranslucent){
            Window w = activity.getWindow();
            w.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else{
            WindowManager.LayoutParams attrs = activity.getWindow()
                    .getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setAttributes(attrs);
            activity.getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }
}
