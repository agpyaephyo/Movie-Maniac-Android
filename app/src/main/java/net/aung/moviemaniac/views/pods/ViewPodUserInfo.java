package net.aung.moviemaniac.views.pods;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import net.aung.moviemaniac.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 12/17/15.
 */
public class ViewPodUserInfo extends FrameLayout {

    @Bind(R.id.iv_profile)
    ImageView ivProfile;

    public ViewPodUserInfo(Context context) {
        super(context);
    }

    public ViewPodUserInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodUserInfo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);

        Glide.with(ivProfile.getContext()).load(R.drawable.profile).asBitmap().centerCrop().into(new BitmapImageViewTarget(ivProfile) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(ivProfile.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                ivProfile.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
}
