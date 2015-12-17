package net.aung.moviemaniac.views.pods;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.aung.moviemaniac.R;

/**
 * Created by aung on 12/12/15.
 */
public class ViewPodMoviePopularityDetail extends LinearLayout {

    public ViewPodMoviePopularityDetail(Context context) {
        super(context);
    }

    public ViewPodMoviePopularityDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodMoviePopularityDetail(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void drawPopularityIcons(float popularity) {
        int popularityCount = (int) (popularity / 10);
        if(popularityCount > 0) {
            setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < popularityCount; i++) {
            ImageView iv = new ImageView(getContext());
            iv.setImageResource(R.drawable.movie_popularity_icon);
            iv.setPadding((int) getContext().getResources().getDimension(R.dimen.margin_small), 0, 0, 0);
            addView(iv);
        }
    }
}
