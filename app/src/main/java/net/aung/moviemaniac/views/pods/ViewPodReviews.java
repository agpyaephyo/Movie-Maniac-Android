package net.aung.moviemaniac.views.pods;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.data.vos.MovieReviewVO;
import net.aung.moviemaniac.views.viewholders.ReviewViewHolder;

import java.util.List;

/**
 * Created by aung on 3/14/16.
 */
public class ViewPodReviews extends LinearLayout {

    public ViewPodReviews(Context context) {
        super(context);
    }

    public ViewPodReviews(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodReviews(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void displayReviewList(List<MovieReviewVO> reviewList) {
        removeAllViews();
        for (MovieReviewVO review : reviewList) {
            View reviewView = LayoutInflater.from(getContext()).inflate(R.layout.view_item_review, this, false);
            ReviewViewHolder viewHolder = new ReviewViewHolder(reviewView);
            viewHolder.bind(review);

            addView(reviewView);
        }
    }
}
