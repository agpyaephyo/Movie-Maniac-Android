package net.aung.moviemaniac.views.pods;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import net.aung.moviemaniac.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aung on 12/17/15.
 */
public class ViewPodUserActions extends LinearLayout {

    public ViewPodUserActions(Context context) {
        super(context);
    }

    public ViewPodUserActions(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodUserActions(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    @OnClick(R.id.ll_like)
    public void onTapLike() {

    }

    @OnClick(R.id.ll_save)
    public void onTapSave() {

    }

    @OnClick(R.id.ll_share)
    public void onTapShare() {

    }
}
