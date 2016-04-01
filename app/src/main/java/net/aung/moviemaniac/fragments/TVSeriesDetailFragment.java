package net.aung.moviemaniac.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.adapters.SeasonListAdapter;
import net.aung.moviemaniac.adapters.TrailerListAdapter;
import net.aung.moviemaniac.controllers.SeasonItemController;
import net.aung.moviemaniac.controllers.TrailerItemController;
import net.aung.moviemaniac.data.persistence.MovieContract;
import net.aung.moviemaniac.data.vos.GenreVO;
import net.aung.moviemaniac.data.vos.ProductionCompanyVO;
import net.aung.moviemaniac.data.vos.TVNetworkVO;
import net.aung.moviemaniac.data.vos.TVSeasonVO;
import net.aung.moviemaniac.data.vos.TVSeriesVO;
import net.aung.moviemaniac.data.vos.TrailerVO;
import net.aung.moviemaniac.databinding.FragmentTvSeriesDetailBinding;
import net.aung.moviemaniac.mvp.presenters.TVSeriesDetailPresenter;
import net.aung.moviemaniac.mvp.views.TVSeriesDetailView;
import net.aung.moviemaniac.utils.GAUtils;
import net.aung.moviemaniac.utils.MovieManiacConstants;
import net.aung.moviemaniac.utils.ScreenUtils;
import net.aung.moviemaniac.utils.YoutubeUtils;
import net.aung.moviemaniac.views.components.ViewComponentLoader;
import net.aung.moviemaniac.views.components.recyclerview.TrailerItemDecoration;
import net.aung.moviemaniac.views.pods.ViewPodExpandPoster;
import net.aung.moviemaniac.views.pods.ViewPodFabs;
import net.aung.moviemaniac.views.pods.ViewPodGenreListDetail;
import net.aung.moviemaniac.views.pods.ViewPodMoviePopularityDetail;
import net.aung.moviemaniac.views.pods.ViewPodMovieStar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 12/15/15.
 */
public class TVSeriesDetailFragment extends BaseFragment
        implements TVSeriesDetailView,
        Palette.PaletteAsyncListener,
        LoaderManager.LoaderCallbacks<Cursor>,
        ViewPodFabs.ControllerFabs {

    private static final String ARG_TV_SERIES_ID = "ARG_TV_SERIES_ID";
    private static final String ARG_TV_SERIES_TYPE = "ARG_TV_SERIES_TYPE";

    private int mTVSeriesId;
    private int mTVSeriesType;
    private TVSeriesVO mTVSeries;

    private FragmentTvSeriesDetailBinding binding;
    private TVSeriesDetailPresenter presenter;
    private Bitmap poster;
    private TrailerListAdapter trailerAdapter;
    private SeasonListAdapter seasonAdapter;
    private TrailerItemController trailerItemController;
    private SeasonItemController seasonItemController;

    @Bind(R.id.vp_tv_series_popularity)
    ViewPodMoviePopularityDetail vpTVSeriesPopularity;

    @Bind(R.id.iv_poster)
    ImageView ivPoster;

    @Bind(R.id.sv_container_trailer)
    NestedScrollView svContainerTrailer;

    @Bind(R.id.lbl_trailers)
    TextView lblTrailers;

    @Bind(R.id.rv_trailers)
    RecyclerView rvTrailers;

    @Bind(R.id.lbl_seasons)
    TextView lblSeasons;

    @Bind(R.id.rv_seasons)
    RecyclerView rvSeasons;

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.vp_container_genre)
    ViewPodGenreListDetail vpContainerGenre;

    @Bind(R.id.vp_movie_star)
    ViewPodMovieStar vpMovieStar;

    @Bind(R.id.vp_fabs)
    ViewPodFabs vpFabs;

    @Bind(R.id.vc_loader)
    ViewComponentLoader vcLoader;

    @Bind(R.id.tv_rating_detail)
    TextView tvRating;

    @Bind(R.id.vp_expand_poster)
    ViewPodExpandPoster vpExpandPoster;

    public static TVSeriesDetailFragment newInstance(int tvSeriesId, int tvSeriesType) {
        TVSeriesDetailFragment fragment = new TVSeriesDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TV_SERIES_ID, tvSeriesId);
        bundle.putInt(ARG_TV_SERIES_TYPE, tvSeriesType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        trailerItemController = (TrailerItemController) context;
        seasonItemController = (SeasonItemController) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TVSeriesDetailPresenter(this);
        presenter.onCreate();

        poster = MovieManiacApp.sPosterCache.get(0);

        if(poster != null)
            Palette.from(poster).generate(this);

        trailerAdapter = TrailerListAdapter.newInstance(trailerItemController);
        seasonAdapter = SeasonListAdapter.newInstance(seasonItemController);
    }

    @Override
    protected void readArguments(Bundle bundle) {
        super.readArguments(bundle);
        mTVSeriesId = bundle.getInt(ARG_TV_SERIES_ID);
        mTVSeriesType = bundle.getInt(ARG_TV_SERIES_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tv_series_detail, container, false);
        ButterKnife.bind(this, rootView);
        binding = DataBindingUtil.bind(rootView);

        ivPoster.setImageBitmap(poster);

        rvTrailers.setHasFixedSize(true);
        rvSeasons.setHasFixedSize(true);

        rvTrailers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvTrailers.addItemDecoration(new TrailerItemDecoration(getContext()));

        rvSeasons.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvSeasons.addItemDecoration(new TrailerItemDecoration(getContext()));

        rvTrailers.setAdapter(trailerAdapter);
        rvSeasons.setAdapter(seasonAdapter);

        vpFabs.setController(this);

        svContainerTrailer.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            private boolean isScrolled = false;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (!isScrolled && scrollY > ScreenUtils.getScreenDimension().y) { //at least, the scroll position has exceed the screen height. So, the reviews are already appear.
                    //scroll from top to bottom
                    GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_SCROLL_FOR_REVIEWS);
                    isScrolled = true;
                }
            }
        });

        vcLoader.displayLoader();
        setRatingColor();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(MovieManiacConstants.TV_SERIES_DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void sendScreenHit() {
        GAUtils.getInstance().sendScreenHit(GAUtils.SCREEN_NAME_TV_SERIES_DETAIL);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void displayTVSeriesDetail(TVSeriesVO tvSeries) {
        binding.setTvSeries(tvSeries);
        vpContainerGenre.setGenreList(tvSeries.getGenreList(), MovieManiacApp.getContext().getResources().getColor(R.color.text_white));
        if (tvSeries.isDetailLoaded()) {
            vcLoader.dismissLoader();
            vpTVSeriesPopularity.drawPopularityIcons(tvSeries.getPopularity());
        }

        if (tvSeries.getTrailerList() != null && tvSeries.getTrailerList().size() > 0) {
            lblTrailers.setVisibility(View.VISIBLE);
            rvTrailers.setVisibility(View.VISIBLE);
            displayTrailerList(tvSeries.getTrailerList());
        } else {
            lblTrailers.setVisibility(View.GONE);
            rvTrailers.setVisibility(View.GONE);
        }

        if(tvSeries.getSeasonList() != null && tvSeries.getSeasonList().size() > 0) {
            lblSeasons.setVisibility(View.VISIBLE);
            rvSeasons.setVisibility(View.VISIBLE);
            displaySeasonList(tvSeries.getSeasonList());
        } else {
            lblSeasons.setVisibility(View.GONE);
            rvSeasons.setVisibility(View.GONE);
        }

        vpFabs.updateStarStatus(tvSeries.isStar());
        vpExpandPoster.setImageUrl(tvSeries.getPosterPath());
    }

    @Override
    public void displaySeasonList(List<TVSeasonVO> seasonList) {
        seasonAdapter.setSeasonList(seasonList);
    }

    @Override
    public void displayTrailerList(List<TrailerVO> trailerList) {
        trailerAdapter.setTrailerList(trailerList);
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

            setPaletteForRootContainer(colorDarkVaient);
            setPaletteForTagLine(colorDarkVaient, colorLightVarient);
            setPaletteForFab(vibrantSwatch);
            setPaletteForStarView(vibrantSwatch);
            //setVibrantColor(vibrantSwatch);
        }
    }

    private void setPaletteForFab(Palette.Swatch swatch) {
        if (swatch != null) {
            vpFabs.setPalette(swatch);
        }
    }

    private void setPaletteForStarView(Palette.Swatch swatch) {
        if (swatch != null) {
            vpMovieStar.getBackground().setColorFilter(swatch.getRgb(), PorterDuff.Mode.MULTIPLY);
        }
    }

    private void setPaletteForRootContainer(Palette.Swatch colorDarkVaient) {
        if (colorDarkVaient != null) {
            svContainerTrailer.setBackgroundColor(colorDarkVaient.getRgb());
        }
    }

    private void setPaletteForTagLine(Palette.Swatch colorDarkVaient, Palette.Swatch colorLightVarient) {
        if (colorDarkVaient != null && colorLightVarient != null) {
            tvTitle.setTextColor(colorDarkVaient.getRgb());
            tvTitle.setBackgroundColor(colorLightVarient.getRgb());
        }
    }

    private void setRatingColor() {
        Context context = MovieManiacApp.getContext();
        int color;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = context.getResources().getColor(R.color.accent, context.getTheme());
        } else {
            color = context.getResources().getColor(R.color.accent);
        }
        tvRating.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                MovieContract.TVSeriesEntry.CONTENT_URI,
                null,
                MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_ID + " = ? AND "+ MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_TYPE + " = ?",
                new String[]{String.valueOf(mTVSeriesId), String.valueOf(mTVSeriesType)},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            mTVSeries = TVSeriesVO.parseFromDetailCursor(data);

            if (!mTVSeries.isDetailLoaded()) {
                presenter.loadTVSeriesDetailFromNetwork(mTVSeries);
            } else {
                mTVSeries.setGenreList(GenreVO.loadGenreListByTVSeriesId(mTVSeries.getTvSerieId()));
                mTVSeries.setProductionCompanyList(ProductionCompanyVO.loadProductionCompanyListByTVSeriesId(mTVSeries.getTvSerieId()));
                mTVSeries.setNetworkList(TVNetworkVO.loadNetworkListByTVSeriesId(mTVSeries.getTvSerieId()));
                mTVSeries.setSeasonList(TVSeasonVO.loadSeasonListByTVSeriesId(mTVSeries.getTvSerieId()));
                mTVSeries.setTrailerList(TrailerVO.loadTrailerListByTVSeriesId(mTVSeries.getTvSerieId()));
            }

            Log.d(MovieManiacApp.TAG, "Displaying tv series detail for tv series id " + mTVSeriesId);
            displayTVSeriesDetail(mTVSeries);
        } else {
            //Load from network via movieId
            presenter.loadTVSeriesDetailFromNetwork(mTVSeriesId);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onTapFavourite() {
        if (mTVSeries.isStar()) {
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.remove_movie_from_favourite_confirmation_msg)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_TAP_REMOVE_STAR_DETAIL);
                            mTVSeries.setStar(false);
                            mTVSeries.updateStarStatus(); //TODO On Main Thread ?
                            vpFabs.updateStarStatus(false);
                        }
                    })
                    .setNegativeButton(R.string.no, null).show();
        } else {
            GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_TAP_STAR);
            mTVSeries.setStar(true);
            mTVSeries.updateStarStatus(); //TODO On Main Thread ?
            vpMovieStar.showMovieSaved(vpFabs, new ViewPodMovieStar.ControllerMovieSaved() {
                @Override
                public void onMovieSavedAnimationFinish() {
                    vpFabs.updateStarStatus(true);
                }
            });
        }
    }

    @Override
    public void onTapFacebook() {
        GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_TAP_SHARE_FACEBOOK);
        Toast.makeText(getContext(), "Facebook Integration is coming soon.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTapShare() {
        GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_TAP_SHARE);
        if (mTVSeries.getTrailerList() != null && mTVSeries.getTrailerList().size() > 0) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, mTVSeries.getName() + " : " + YoutubeUtils.getFullUrlFromYoutubeVideo(mTVSeries.getTrailerList().get(0).getKey()));
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
        } else {
            Toast.makeText(getContext(), "No trailer released or this movie yet. We'll let you know when there is a new trailer coming.", Toast.LENGTH_SHORT).show();
        }
    }
}
