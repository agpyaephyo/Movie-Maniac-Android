package net.aung.moviemaniac.utils;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import net.aung.moviemaniac.MovieManiacApp;

/**
 * Created by aung on 12/16/15.
 */
public class ScreenUtils {

    public static final float SCALE_START_ANCHOR = 0.3f;
    public static final int SCALE_DELAY = 200;
    public static final int IMMEDIATELY = 0;

    /**
     * Put the content below the status bar and make the status bar translucent.
     *
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusbarTranslucent(boolean isToTranslucent, Activity activity) {
        if (isToTranslucent) {
            Window w = activity.getWindow();
            w.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            WindowManager.LayoutParams attrs = activity.getWindow()
                    .getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setAttributes(attrs);
            activity.getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public static void startScaleAnimationFromPivot(int pivotX, int pivotY, final View view, final Animator.AnimatorListener animatorListener) {
        final AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        view.setScaleY(SCALE_START_ANCHOR);
        view.setPivotX(pivotX);
        view.setPivotY(pivotY);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);

                ViewPropertyAnimator viewPropertyAnimator = view.animate()
                        .setInterpolator(interpolator)
                        .scaleY(1).scaleX(1)
                        .setDuration(SCALE_DELAY);

                if (animatorListener != null)
                    viewPropertyAnimator.setListener(animatorListener);

                viewPropertyAnimator.start();

                return true;
            }
        });
    }

    public static ViewPropertyAnimator hideViewByScaleY(View view, Animator.AnimatorListener animatorListener, int animDelay) {
        ViewPropertyAnimator propertyAnimator = view.animate()
                .setStartDelay(SCALE_DELAY)
                .scaleY(0)
                .setStartDelay(animDelay);

        if (animatorListener != null)
            propertyAnimator.setListener(animatorListener);

        return propertyAnimator;
    }

    public static Point getScreenDimension() {
        WindowManager wm = (WindowManager) MovieManiacApp.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
    }

    /**
     * Show soft keyboard in some situation.
     */
    public static void showSoftKeyboard() {
        Context context = MovieManiacApp.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    /**
     * Hide soft keyboard based on the EditText which the focus is in.
     *
     * @param et
     */
    public static void hideSoftKeyboard(EditText et) {
        Context context = MovieManiacApp.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if (imm != null) {
            //imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(et.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
