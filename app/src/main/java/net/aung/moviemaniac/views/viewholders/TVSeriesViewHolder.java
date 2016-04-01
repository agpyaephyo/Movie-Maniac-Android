package net.aung.moviemaniac.views.viewholders;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.graphics.Palette;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.controllers.TVSeriesItemController;
import net.aung.moviemaniac.data.vos.GenreVO;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.data.vos.TVSeriesVO;
import net.aung.moviemaniac.databinding.ViewItemTvSeriesBinding;
import net.aung.moviemaniac.dialogs.ExpandedPosterDialog;
import net.aung.moviemaniac.utils.GAUtils;
import net.aung.moviemaniac.views.pods.ViewPodExpandPoster;
import net.aung.moviemaniac.views.pods.ViewPodMoviePopularity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aung on 12/12/15.
 */
public class TVSeriesViewHolder extends BaseViewHolder<TVSeriesVO>
        implements Palette.PaletteAsyncListener {

    private ViewItemTvSeriesBinding binding;

    @Bind(R.id.vp_movie_popularity)
    ViewPodMoviePopularity vpMoviePopularity;

    @Bind(R.id.iv_poster)
    ImageView ivPoster;

    @Bind(R.id.tv_genre_list)
    TextView tvGenreList;

    @Bind(R.id.iv_cancel_star)
    ImageView ivCancelStar;

    @Bind(R.id.tv_rating)
    TextView tvRating;

    @Bind(R.id.vp_expand_poster)
    ViewPodExpandPoster vpExpandPoster;

    private View itemView;

    private TVSeriesItemController controller;

    public TVSeriesViewHolder(View itemView, boolean isFavouriteSection, TVSeriesItemController controller) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);

        binding = DataBindingUtil.bind(itemView);
        this.controller = controller;

        ivPoster.setDrawingCacheEnabled(true);
        ivCancelStar.setVisibility(isFavouriteSection ? View.VISIBLE : View.GONE);

        setRatingColor();
    }

    @Override
    public void bind(TVSeriesVO tvSeries) {
        binding.setTvSeries(tvSeries);
        int popularityCount = (int) (tvSeries.getPopularity() / 10);
        vpMoviePopularity.drawPopularityIcons(tvSeries.getPopularity());

        Glide.with(ivPoster.getContext())
                .load(tvSeries.getPosterPath())
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.place_holder_movie_maniac)
                .into(new BitmapImageViewTarget(ivPoster) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        Palette.from(resource).generate(TVSeriesViewHolder.this);
                    }
                });

        List<GenreVO> genreList = tvSeries.getGenreList();
        if (genreList != null && genreList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            //stringBuilder.append("<font face='sans-serif-light'>");
            stringBuilder.append("(");
            int count = 0;
            for (GenreVO genre : genreList) {
                if (genre != null) {
                    stringBuilder.append("<b>" + genre.getName() + "</ b>");
                    if (count < genreList.size() - 1) {
                        stringBuilder.append(" , ");
                    }
                    count++;
                }
            }
            stringBuilder.append(")");
            //stringBuilder.append("</font>");
            tvGenreList.setText(Html.fromHtml(stringBuilder.toString()));
        } else {
            tvGenreList.setVisibility(View.GONE);
        }

        vpExpandPoster.setImageUrl(tvSeries.getPosterPath());
    }

    @Override
    public void onClick(View view) {
        GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_TAP_TV_SERIES_ITEM);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ivPoster.getDrawable();
        MovieManiacApp.sPosterCache.put(0, bitmapDrawable.getBitmap());

        controller.onNavigateToDetail(binding.getTvSeries());
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

    private void setRatingColor() {
        Context context = MovieManiacApp.getContext();
        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = context.getResources().getColor(R.color.accent, context.getTheme());
        } else {
            color = context.getResources().getColor(R.color.accent);
        }
        tvRating.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

    @OnClick(R.id.iv_cancel_star)
    public void onTapCancelStar(View view) {
        new AlertDialog.Builder(view.getContext())
                .setMessage(R.string.remove_tv_series_from_favourite_confirmation_msg)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_TAP_REMOVE_STAR_LIST);
                        TVSeriesVO tvSeries = binding.getTvSeries();
                        tvSeries.setStar(false);
                        tvSeries.updateStarStatus(); //TODO On Main Thread ?
                    }})
                .setNegativeButton(R.string.no, null).show();
    }

    @OnClick(R.id.btn_tv_series_overview)
    public void onTapMovieOverview(View view) {
        GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_MOVIE_OVERVIEW);
        TVSeriesVO tvSeries = binding.getTvSeries();
        String overview = tvSeries.getOverview();
        if(TextUtils.isEmpty(overview)) {
            overview = view.getContext().getResources().getString(R.string.msg_empty_overview);
        }

        AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                .setTitle(tvSeries.getName())
                .setMessage(overview)
                .setIcon(R.drawable.ic_movie_maniac)
                .setPositiveButton(android.R.string.ok, null).show();

        TextView tvMsg = (TextView) dialog.findViewById(android.R.id.message);
        tvMsg.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        tvMsg.setLineSpacing(1.2f, 1.2f);

    }

    /*
    private void setPaletteforTitle(Palette.Swatch colorDarkVaient, Palette.Swatch colorLightVarient) {
        if (colorDarkVaient != null && colorLightVarient != null) {
            tvTitle.setTextColor(colorDarkVaient.getRgb());
        }
    }
    */
}
