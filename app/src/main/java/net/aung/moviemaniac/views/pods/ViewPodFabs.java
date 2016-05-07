package net.aung.moviemaniac.views.pods;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.controllers.BaseController;
import net.aung.moviemaniac.controllers.ViewController;
import net.aung.moviemaniac.listeners.AnimatorAdapter;
import net.aung.moviemaniac.utils.GAUtils;


/**
 * Created by aung on 2/25/16.
 */
public class ViewPodFabs extends FrameLayout implements ViewController {

    private ImageButton ibPlus;
    private ImageButton ibFavourite;
    private ImageButton ibFacebook;
    private ImageButton ibShare;

    private ControllerFabs controller;

    private boolean isOpen = false;

    private static final float F310 = MovieManiacApp.getContext().getResources().getDimension(R.dimen.dimen_310);
    private static final float F280 = MovieManiacApp.getContext().getResources().getDimension(R.dimen.dimen_280);
    private static final float F180 = MovieManiacApp.getContext().getResources().getDimension(R.dimen.dimen_180);
    private static final float F160 = MovieManiacApp.getContext().getResources().getDimension(R.dimen.dimen_160);
    private static final float F30 = MovieManiacApp.getContext().getResources().getDimension(R.dimen.dimen_30);
    private static final float F20 = MovieManiacApp.getContext().getResources().getDimension(R.dimen.dimen_20);

    public ViewPodFabs(Context context) {
        super(context);
    }

    public ViewPodFabs(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodFabs(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void openAnim() {
        ObjectAnimator objAnimRotation = ObjectAnimator.ofFloat(ibPlus, "rotation", 540f, 0f);
        objAnimRotation.setDuration(600);
        objAnimRotation.setInterpolator(new AccelerateInterpolator());
        objAnimRotation.start();
        objAnimRotation.addListener(new AnimatorAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ibPlus.setEnabled(true);
            }
        });

        ObjectAnimator objAnimCallFW = ObjectAnimator.ofFloat(ibFavourite, "y", ibFavourite.getY(), ibFavourite.getY() - F310);
        objAnimCallFW.setDuration(500);
        objAnimCallFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimCallBW = ObjectAnimator.ofFloat(ibFavourite, "y", ibFavourite.getY() - F310, ibFavourite.getY() - F280);
        objAnimCallBW.setDuration(100);
        objAnimCallBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asCall = new AnimatorSet();
        asCall.play(objAnimCallBW).after(objAnimCallFW);
        asCall.start();

        ObjectAnimator objAnimFacebookFW = ObjectAnimator.ofFloat(ibShare, "x", ibShare.getX(), ibShare.getX() - F310);
        objAnimFacebookFW.setDuration(500);
        objAnimFacebookFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimFacebookBW = ObjectAnimator.ofFloat(ibShare, "x", ibShare.getX() - F310, ibShare.getX() - F280);
        objAnimFacebookBW.setDuration(100);
        objAnimFacebookBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asFacebook = new AnimatorSet();
        asFacebook.play(objAnimFacebookBW).after(objAnimFacebookFW);
        asFacebook.start();

        ObjectAnimator objAnimMapXFW = ObjectAnimator.ofFloat(ibFacebook, "x", ibFacebook.getX(), ibFacebook.getX() - F180);
        objAnimMapXFW.setDuration(500);
        objAnimMapXFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimMapYFW = ObjectAnimator.ofFloat(ibFacebook, "y", ibFacebook.getY(), ibFacebook.getY() - F180);
        objAnimMapYFW.setDuration(500);
        objAnimMapYFW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asMapFW = new AnimatorSet();
        asMapFW.play(objAnimMapYFW).with(objAnimMapXFW);
        asMapFW.start();

        ObjectAnimator objAnimMapXBW = ObjectAnimator.ofFloat(ibFacebook, "x", ibFacebook.getX() - F180, ibFacebook.getX() - F160);
        objAnimMapXBW.setDuration(100);
        objAnimMapXBW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimMapYBW = ObjectAnimator.ofFloat(ibFacebook, "y", ibFacebook.getY() - F180, ibFacebook.getY() - F160);
        objAnimMapYBW.setDuration(100);
        objAnimMapYBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asMapBW = new AnimatorSet();
        asMapBW.play(objAnimMapYBW).with(objAnimMapXBW);
        asMapBW.setStartDelay(500);
        asMapBW.start();
    }

    private void closeAnim() {
        ObjectAnimator objAnimRotation = ObjectAnimator.ofFloat(ibPlus, "rotation", 0, 540f);
        objAnimRotation.setDuration(600);
        objAnimRotation.setInterpolator(new AccelerateInterpolator());
        objAnimRotation.start();
        objAnimRotation.addListener(new AnimatorAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ibPlus.setEnabled(true);
            }
        });

        ObjectAnimator objAnimCallFW = ObjectAnimator.ofFloat(ibFavourite, "y", ibFavourite.getY(), ibFavourite.getY() - F30);
        objAnimCallFW.setDuration(100);
        objAnimCallFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimCallBW = ObjectAnimator.ofFloat(ibFavourite, "y", ibFavourite.getY() - F30, ibFavourite.getY() + F280);
        objAnimCallBW.setDuration(500);
        objAnimCallBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asCall = new AnimatorSet();
        asCall.play(objAnimCallBW).after(objAnimCallFW);
        asCall.start();

        ObjectAnimator objAnimFacebookFW = ObjectAnimator.ofFloat(ibShare, "x", ibShare.getX(), ibShare.getX() - F30);
        objAnimFacebookFW.setDuration(100);
        objAnimFacebookFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimFacebookBW = ObjectAnimator.ofFloat(ibShare, "x", ibShare.getX() - F30, ibShare.getX() + F280);
        objAnimFacebookBW.setDuration(500);
        objAnimFacebookBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asFacebook = new AnimatorSet();
        asFacebook.play(objAnimFacebookBW).after(objAnimFacebookFW);
        asFacebook.start();

        ObjectAnimator objAnimMapXFW = ObjectAnimator.ofFloat(ibFacebook, "x", ibFacebook.getX(), ibFacebook.getX() - F20);
        objAnimMapXFW.setDuration(100);
        objAnimMapXFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimMapYFW = ObjectAnimator.ofFloat(ibFacebook, "y", ibFacebook.getY(), ibFacebook.getY() - F20);
        objAnimMapYFW.setDuration(100);
        objAnimMapYFW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asMapFW = new AnimatorSet();
        asMapFW.play(objAnimMapYFW).with(objAnimMapXFW);
        asMapFW.start();

        ObjectAnimator objAnimMapXBW = ObjectAnimator.ofFloat(ibFacebook, "x", ibFacebook.getX() - F20, ibFacebook.getX() + F160);
        objAnimMapXBW.setDuration(500);
        objAnimMapXBW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimMapYBW = ObjectAnimator.ofFloat(ibFacebook, "y", ibFacebook.getY() - F20, ibFacebook.getY() + F160);
        objAnimMapYBW.setDuration(500);
        objAnimMapYBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asMapBW = new AnimatorSet();
        asMapBW.play(objAnimMapYBW).with(objAnimMapXBW);
        asMapBW.setStartDelay(100);
        asMapBW.start();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ibPlus = (ImageButton) findViewById(R.id.ib_plus);
        ibPlus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ibPlus.setEnabled(false);
                if (!isOpen) {
                    openAnim();
                    isOpen = true;
                    GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_TAP_FAB_PLUS);
                } else {
                    closeAnim();
                    isOpen = false;
                }
            }
        });

        ibFavourite = (ImageButton) findViewById(R.id.ib_favourite);
        ibFavourite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onTapFavourite();
            }
        });

        ibFacebook = (ImageButton) findViewById(R.id.ib_facebook);
        ibFacebook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onTapFacebook();
            }
        });

        ibShare = (ImageButton) findViewById(R.id.ib_share);
        ibShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onTapShare();
            }
        });
    }

    @Override
    public void setController(BaseController controller) {
        this.controller = (ControllerFabs) controller;
    }

    public void setPalette(Palette.Swatch swatch) {
        ibPlus.getBackground().setColorFilter(swatch.getRgb(), PorterDuff.Mode.MULTIPLY);
        ibFavourite.getBackground().setColorFilter(swatch.getRgb(), PorterDuff.Mode.MULTIPLY);
        ibFacebook.getBackground().setColorFilter(swatch.getRgb(), PorterDuff.Mode.MULTIPLY);
        ibShare.getBackground().setColorFilter(swatch.getRgb(), PorterDuff.Mode.MULTIPLY);
    }

    public void updateStarStatus(boolean isStar) {
        ibFavourite.setImageResource(isStar ? R.drawable.ic_favorite_white_24dp : R.drawable.ic_favorite_border_white_24dp);

        int waitTime = isStar ? 2000 : 100;
        if(isOpen) {
            ibPlus.setEnabled(false);
            isOpen = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeAnim();
                }
            }, waitTime);
        }
    }

    public interface ControllerFabs extends BaseController {
        void onTapFavourite();

        void onTapFacebook();

        void onTapShare();
    }
}
