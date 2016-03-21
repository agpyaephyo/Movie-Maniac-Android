package net.aung.moviemaniac.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
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
import net.aung.moviemaniac.adapters.TrailerListAdapter;
import net.aung.moviemaniac.controllers.TrailerItemController;
import net.aung.moviemaniac.data.persistence.MovieContract;
import net.aung.moviemaniac.data.vos.CollectionVO;
import net.aung.moviemaniac.data.vos.GenreVO;
import net.aung.moviemaniac.data.vos.MovieReviewVO;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.data.vos.ProductionCompanyVO;
import net.aung.moviemaniac.data.vos.ProductionCountryVO;
import net.aung.moviemaniac.data.vos.SpokenLanguageVO;
import net.aung.moviemaniac.data.vos.TrailerVO;
import net.aung.moviemaniac.databinding.FragmentMovieDetailBinding;
import net.aung.moviemaniac.mvp.presenters.MovieDetailPresenter;
import net.aung.moviemaniac.mvp.views.MovieDetailView;
import net.aung.moviemaniac.utils.GAUtils;
import net.aung.moviemaniac.utils.MovieManiacConstants;
import net.aung.moviemaniac.utils.ScreenUtils;
import net.aung.moviemaniac.utils.YoutubeUtils;
import net.aung.moviemaniac.views.components.recyclerview.TrailerItemDecoration;
import net.aung.moviemaniac.views.pods.ViewPodFabs;
import net.aung.moviemaniac.views.pods.ViewPodGenreListDetail;
import net.aung.moviemaniac.views.pods.ViewPodMoviePopularityDetail;
import net.aung.moviemaniac.views.pods.ViewPodMovieStar;
import net.aung.moviemaniac.views.pods.ViewPodReviews;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 12/15/15.
 */
public class MovieDetailFragment extends BaseFragment
        implements MovieDetailView,
        Palette.PaletteAsyncListener,
        LoaderManager.LoaderCallbacks<Cursor>,
        ViewPodFabs.ControllerFabs {

    private static final String ARG_MOVIE_ID = "ARG_MOVIE_ID";
    private static final String ARG_MOVIE_TYPE = "ARG_MOVIE_TYPE";

    private int mMovieId;
    private int mMovieType;
    private MovieVO mMovie;
    private FragmentMovieDetailBinding binding;
    private MovieDetailPresenter presenter;
    private Bitmap poster;
    private TrailerListAdapter trailerAdapter;
    private TrailerItemController controller;

    @Bind(R.id.vp_movie_popularity)
    ViewPodMoviePopularityDetail vpMoviePopularity;

    @Bind(R.id.iv_poster)
    ImageView ivPoster;

    @Bind(R.id.sv_container_trailer)
    NestedScrollView svContainerTrailer;

    @Bind(R.id.rv_trailers)
    RecyclerView rvTrailers;

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.vp_container_genre)
    ViewPodGenreListDetail vpContainerGenre;

    @Bind(R.id.vp_reviews)
    ViewPodReviews vpReviews;

    @Bind(R.id.lbl_reviews)
    TextView lblReviews;

    @Bind(R.id.vp_movie_star)
    ViewPodMovieStar vpMovieStar;

    @Bind(R.id.vp_fabs)
    ViewPodFabs vpFabs;

    public static MovieDetailFragment newInstance(int movieId, int movieType) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_MOVIE_ID, movieId);
        bundle.putInt(ARG_MOVIE_TYPE, movieType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controller = (TrailerItemController) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MovieDetailPresenter(this);
        presenter.onCreate();

        poster = MovieManiacApp.sPosterCache.get(0);

        if(poster != null)
            Palette.from(poster).generate(this);

        trailerAdapter = TrailerListAdapter.newInstance(controller);
    }

    @Override
    protected void readArguments(Bundle bundle) {
        super.readArguments(bundle);
        mMovieId = bundle.getInt(ARG_MOVIE_ID);
        mMovieType = bundle.getInt(ARG_MOVIE_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);
        binding = DataBindingUtil.bind(rootView);

        ivPoster.setImageBitmap(poster);

        rvTrailers.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvTrailers.setLayoutManager(layoutManager);
        rvTrailers.addItemDecoration(new TrailerItemDecoration(getContext()));

        rvTrailers.setAdapter(trailerAdapter);

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

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(MovieManiacConstants.MOVIE_DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void sendScreenHit() {
        GAUtils.getInstance().sendScreenHit(GAUtils.SCREEN_NAME_MOVIE_DETAIL);
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
    public void displayMovieDetail(MovieVO movie) {
        binding.setMovie(movie);
        vpContainerGenre.setGenreList(movie.getGenreList());
        if (movie.isDetailLoaded()) {
            vpMoviePopularity.drawPopularityIcons(movie.getPopularity());
        }

        if (movie.getTrailerList() != null && movie.getTrailerList().size() > 0) {
            displayTrailerList(movie.getTrailerList());
        }

        if (movie.getReviewList() != null && movie.getReviewList().size() > 0) {
            lblReviews.setVisibility(View.VISIBLE);
            displayReviewList(movie.getReviewList());
        } else {
            lblReviews.setVisibility(View.GONE);
        }

        //ibMovieStar.setImageResource(movie.isStar() ? R.drawable.ic_favorite_white_24dp : R.drawable.ic_fab_star);
        vpFabs.updateStarStatus(movie.isStar());
    }

    @Override
    public void displayTrailerList(List<TrailerVO> trailerList) {
        trailerAdapter.setTrailerList(trailerList);
    }

    @Override
    public void displayReviewList(List<MovieReviewVO> reviewList) {
        vpReviews.displayReviewList(reviewList);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ? AND "+ MovieContract.MovieEntry.COLUMN_MOVIE_TYPE + " = ?",
                new String[]{String.valueOf(mMovieId), String.valueOf(mMovieType)},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            mMovie = MovieVO.parseFromDetailCursor(data);

            if (!mMovie.isDetailLoaded()) {
                presenter.loadMovieDetailFromNetwork(mMovie);
            } else {
                mMovie.setGenreList(GenreVO.loadGenreListByMovieId(mMovie.getId()));
                if (mMovie.getCollectionId() != 0) {
                    mMovie.setCollection(CollectionVO.loadCollectionById(mMovie.getCollectionId()));
                }
                mMovie.setProductionCompanyList(ProductionCompanyVO.loadProductionCompanyListByMovieId(mMovie.getId()));
                mMovie.setProductionCountryList(ProductionCountryVO.loadProductionCountryListByMovieId(mMovie.getId()));
                mMovie.setSpokenLanguageList(SpokenLanguageVO.loadSpokenLanguageListByMovieId(mMovie.getId()));
                mMovie.setTrailerList(TrailerVO.loadTrailerListByMovieId(mMovie.getId()));
                mMovie.setReviewList(MovieReviewVO.loadReviewListByMovieId(mMovie.getId()));
            }

            Log.d(MovieManiacApp.TAG, "Displaying movies detail for movie_id " + mMovieId);
            displayMovieDetail(mMovie);
        } else {
            //Load from network via movieId
            presenter.loadMovieDetailFromNetwork(mMovieId);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onTapFavourite() {
        if (mMovie.isStar()) {
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.remove_movie_from_favourite_confirmation_msg)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_TAP_REMOVE_STAR_DETAIL);
                            mMovie.setStar(false);
                            mMovie.updateMovieStarStatus(); //TODO On Main Thread ?
                            vpFabs.updateStarStatus(false);
                        }
                    })
                    .setNegativeButton(R.string.no, null).show();
        } else {
            GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_TAP_STAR);
            mMovie.setStar(true);
            mMovie.updateMovieStarStatus(); //TODO On Main Thread ?
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
        Toast.makeText(getContext(), "Nothing happen ! Facebook integration is not there yet.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTapShare() {
        GAUtils.getInstance().sendUserEventHit(GAUtils.EVENT_ACTION_TAP_SHARE);
        if (mMovie.getTrailerList() != null && mMovie.getTrailerList().size() > 0) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, mMovie.getTitle() + " : " + YoutubeUtils.getFullUrlFromYoutubeVideo(mMovie.getTrailerList().get(0).getKey()));
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
        } else {
            Toast.makeText(getContext(), "No trailer released or this movie yet. We'll let you know when there is a new trailer coming.", Toast.LENGTH_SHORT).show();
        }

    }
}
