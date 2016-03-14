package net.aung.moviemaniac.views.pods;

import android.animation.Animator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.listeners.AnimatorAdapter;
import net.aung.moviemaniac.utils.ScreenUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aungpaye on 21/3/15.
 */
public class ViewPodMovieStar extends FrameLayout{

    public static final int MOVIE_SAVED_ANIMATION_DELAY = 1500;

    @Bind(R.id.iv_star)
    ImageView ivStar;

    @Bind(R.id.lbl_saved)
    TextView lblSaved;

    private ControllerMovieSaved controller;

    public ViewPodMovieStar(Context context) {
        super(context);
    }

    public ViewPodMovieStar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodMovieStar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);

    }

    public void showMovieSaved(View triggerPoint, ControllerMovieSaved controller){
        int x = (int) triggerPoint.getX();
        int y = (int) triggerPoint.getY();
        this.controller = controller;

        ScreenUtils.startScaleAnimationFromPivot(
                x, y, this, null);

        Drawable drawable = ivStar.getDrawable();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
        } else {
            ivStar.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.movie_saved_star_rotate));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideMovieSaved();
            }

        }, MOVIE_SAVED_ANIMATION_DELAY);
    }

    public void hideMovieSaved(){
        ScreenUtils.hideViewByScaleY(ViewPodMovieStar.this, new AnimatorAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(controller != null) {
                    controller.onMovieSavedAnimationFinish();
                }
            }
        }, ScreenUtils.IMMEDIATELY);
    }

    public void setTextColor(int color) {
        lblSaved.setTextColor(color);
    }

    public void setStarColor(int color) {
        ivStar.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

    public interface ControllerMovieSaved {
        void onMovieSavedAnimationFinish();
    }
}
