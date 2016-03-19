package net.aung.moviemaniac.views.pods;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.controllers.BaseController;
import net.aung.moviemaniac.controllers.ViewController;
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

        ObjectAnimator objAnimCallFW = ObjectAnimator.ofFloat(ibFavourite, "y", ibFavourite.getY(), ibFavourite.getY() - 310f);
        objAnimCallFW.setDuration(500);
        objAnimCallFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimCallBW = ObjectAnimator.ofFloat(ibFavourite, "y", ibFavourite.getY() - 310, ibFavourite.getY() - 280f);
        objAnimCallBW.setDuration(100);
        objAnimCallBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asCall = new AnimatorSet();
        asCall.play(objAnimCallBW).after(objAnimCallFW);
        asCall.start();

        ObjectAnimator objAnimFacebookFW = ObjectAnimator.ofFloat(ibShare, "x", ibShare.getX(), ibShare.getX() - 310f);
        objAnimFacebookFW.setDuration(500);
        objAnimFacebookFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimFacebookBW = ObjectAnimator.ofFloat(ibShare, "x", ibShare.getX() - 310, ibShare.getX() - 280f);
        objAnimFacebookBW.setDuration(100);
        objAnimFacebookBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asFacebook = new AnimatorSet();
        asFacebook.play(objAnimFacebookBW).after(objAnimFacebookFW);
        asFacebook.start();

        ObjectAnimator objAnimMapXFW = ObjectAnimator.ofFloat(ibFacebook, "x", ibFacebook.getX(), ibFacebook.getX() - 180f);
        objAnimMapXFW.setDuration(500);
        objAnimMapXFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimMapYFW = ObjectAnimator.ofFloat(ibFacebook, "y", ibFacebook.getY(), ibFacebook.getY() - 180f);
        objAnimMapYFW.setDuration(500);
        objAnimMapYFW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asMapFW = new AnimatorSet();
        asMapFW.play(objAnimMapYFW).with(objAnimMapXFW);
        asMapFW.start();

        ObjectAnimator objAnimMapXBW = ObjectAnimator.ofFloat(ibFacebook, "x", ibFacebook.getX() - 180f, ibFacebook.getX() - 160f);
        objAnimMapXBW.setDuration(100);
        objAnimMapXBW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimMapYBW = ObjectAnimator.ofFloat(ibFacebook, "y", ibFacebook.getY() - 180f, ibFacebook.getY() - 160f);
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

        ObjectAnimator objAnimCallFW = ObjectAnimator.ofFloat(ibFavourite, "y", ibFavourite.getY(), ibFavourite.getY() - 30f);
        objAnimCallFW.setDuration(100);
        objAnimCallFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimCallBW = ObjectAnimator.ofFloat(ibFavourite, "y", ibFavourite.getY() - 30, ibFavourite.getY() + 280f);
        objAnimCallBW.setDuration(500);
        objAnimCallBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asCall = new AnimatorSet();
        asCall.play(objAnimCallBW).after(objAnimCallFW);
        asCall.start();

        ObjectAnimator objAnimFacebookFW = ObjectAnimator.ofFloat(ibShare, "x", ibShare.getX(), ibShare.getX() - 30f);
        objAnimFacebookFW.setDuration(100);
        objAnimFacebookFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimFacebookBW = ObjectAnimator.ofFloat(ibShare, "x", ibShare.getX() - 30, ibShare.getX() + 280f);
        objAnimFacebookBW.setDuration(500);
        objAnimFacebookBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asFacebook = new AnimatorSet();
        asFacebook.play(objAnimFacebookBW).after(objAnimFacebookFW);
        asFacebook.start();

        ObjectAnimator objAnimMapXFW = ObjectAnimator.ofFloat(ibFacebook, "x", ibFacebook.getX(), ibFacebook.getX() - 20);
        objAnimMapXFW.setDuration(100);
        objAnimMapXFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimMapYFW = ObjectAnimator.ofFloat(ibFacebook, "y", ibFacebook.getY(), ibFacebook.getY() - 20);
        objAnimMapYFW.setDuration(100);
        objAnimMapYFW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asMapFW = new AnimatorSet();
        asMapFW.play(objAnimMapYFW).with(objAnimMapXFW);
        asMapFW.start();

        ObjectAnimator objAnimMapXBW = ObjectAnimator.ofFloat(ibFacebook, "x", ibFacebook.getX() - 20f, ibFacebook.getX() + 160f);
        objAnimMapXBW.setDuration(500);
        objAnimMapXBW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimMapYBW = ObjectAnimator.ofFloat(ibFacebook, "y", ibFacebook.getY() - 20f, ibFacebook.getY() + 160f);
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
    }

    public interface ControllerFabs extends BaseController {
        void onTapFavourite();

        void onTapFacebook();

        void onTapShare();
    }
}
