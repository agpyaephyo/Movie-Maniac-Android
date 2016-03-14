package net.aung.moviemaniac.views.viewholders;

import android.databinding.DataBindingUtil;
import android.view.View;

import net.aung.moviemaniac.data.vos.MovieReviewVO;
import net.aung.moviemaniac.databinding.ViewItemReviewBinding;

/**
 * Created by aung on 3/14/16.
 */
public class ReviewViewHolder {

    private ViewItemReviewBinding binding;

    public ReviewViewHolder(View view) {
        binding = DataBindingUtil.bind(view);
    }

    public void bind(MovieReviewVO review) {
        binding.setReview(review);
    }
}
