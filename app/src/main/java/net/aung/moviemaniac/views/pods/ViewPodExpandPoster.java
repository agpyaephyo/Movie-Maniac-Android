package net.aung.moviemaniac.views.pods;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.dialogs.ExpandedPosterDialog;
import net.aung.moviemaniac.utils.GAUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aung on 4/1/16.
 */
public class ViewPodExpandPoster extends ImageView {

    private String mImageUrl;

    public ViewPodExpandPoster(Context context) {
        super(context);
    }

    public ViewPodExpandPoster(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodExpandPoster(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }

    @OnClick(R.id.iv_expand_poster)
    public void onTapExpandMoviePoster(View view) {
        GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_POSTER_EXPAND);
        Context context = view.getContext();
        ExpandedPosterDialog expandedPosterDialog = new ExpandedPosterDialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        expandedPosterDialog.show(mImageUrl);
    }
}
