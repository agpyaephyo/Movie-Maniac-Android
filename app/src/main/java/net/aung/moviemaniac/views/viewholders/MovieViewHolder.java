package net.aung.moviemaniac.views.viewholders;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import net.aung.moviemaniac.controllers.MovieItemController;
import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.data.vos.GenreVO;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.databinding.ViewItemMovieBinding;
import net.aung.moviemaniac.views.pods.ViewPodMoviePopularity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 12/12/15.
 */
public class MovieViewHolder extends BaseViewHolder<MovieVO>
        implements Palette.PaletteAsyncListener {

    private ViewItemMovieBinding binding;
    private MovieItemController controller;

    @Bind(R.id.vp_movie_popularity)
    ViewPodMoviePopularity vpMoviePopularity;

    @Bind(R.id.iv_poster)
    ImageView ivPoster;

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.tv_genre_list)
    TextView tvGenreList;

    private View itemView;

    public MovieViewHolder(View itemView, MovieItemController controller) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);

        binding = DataBindingUtil.bind(itemView);
        this.controller = controller;

        ivPoster.setDrawingCacheEnabled(true);
    }

    @Override
    public void bind(MovieVO movie) {
        binding.setMovie(movie);
        int popularityCount = (int) (movie.getPopularity() / 10);
        vpMoviePopularity.drawPopularityIcons(movie.getPopularity());

        Glide.with(ivPoster.getContext())
                .load(movie.getPosterPath())
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(ivPoster) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        Palette.from(resource).generate(MovieViewHolder.this);
                    }
                });

        List<GenreVO> genreList = movie.getGenreList();
        StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append("<font face='sans-serif-light'>");
        int count = 0;
        for (GenreVO genre : genreList) {
            if (genre != null) {
                stringBuilder.append("<b><u>" + genre.getName() + "</u></ b>");
                if (count < genreList.size() - 1) {
                    stringBuilder.append(" , ");
                }
                count++;
            }
        }
        //stringBuilder.append("</font>");
        tvGenreList.setText(Html.fromHtml(stringBuilder.toString()));

    }

    @Override
    public void onClick(View view) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ivPoster.getDrawable();
        MovieManiacApp.sPosterCache.put(0, bitmapDrawable.getBitmap());

        controller.onNavigateToDetail(binding.getMovie());
    }

    @Override
    public void onGenerated(Palette palette) {
        if (palette != null) {

            final Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
            final Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
            final Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
            final Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();

            //-- start here.
            final Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();

            final Palette.Swatch colorDarkVaient = (darkVibrantSwatch != null)
                    ? darkVibrantSwatch : darkMutedSwatch;

            final Palette.Swatch colorLightVarient = (darkVibrantSwatch != null)
                    ? lightVibrantSwatch : lightMutedSwatch;

            //setPaletteForRootContainer(vibrantSwatch);
            //setPaletteforTitle(colorDarkVaient, colorLightVarient);
            //setVibrantColor(vibrantSwatch);
        }
    }

    private void setPaletteForRootContainer(Palette.Swatch colorDarkVaient) {
        if (colorDarkVaient != null) {
            itemView.setBackgroundColor(colorDarkVaient.getRgb());
        }
    }

    private void setPaletteforTitle(Palette.Swatch colorDarkVaient, Palette.Swatch colorLightVarient) {
        if (colorDarkVaient != null && colorLightVarient != null) {
            tvTitle.setTextColor(colorDarkVaient.getRgb());
        }
    }
}
