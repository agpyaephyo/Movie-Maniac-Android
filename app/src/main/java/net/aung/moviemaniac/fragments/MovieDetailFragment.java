package net.aung.moviemaniac.fragments;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
import net.aung.moviemaniac.utils.MovieManiacConstants;
import net.aung.moviemaniac.views.components.recyclerview.TrailerItemDecoration;
import net.aung.moviemaniac.views.pods.ViewPodGenreListDetail;
import net.aung.moviemaniac.views.pods.ViewPodMoviePopularityDetail;
import net.aung.moviemaniac.views.pods.ViewPodMovieStar;
import net.aung.moviemaniac.views.pods.ViewPodReviews;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aung on 12/15/15.
 */
public class MovieDetailFragment extends BaseFragment
        implements MovieDetailView,
        Palette.PaletteAsyncListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String ARG_MOVIE_ID = "ARG_MOVIE_ID";

    private int mMovieId;
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

    @Bind(R.id.ib_movie_star)
    ImageButton ibMovieStar;

    @Bind(R.id.vp_movie_star)
    ViewPodMovieStar vpMovieStar;

    public static MovieDetailFragment newInstance(int movieId) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_MOVIE_ID, movieId);
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
        Palette.from(poster).generate(this);

        trailerAdapter = TrailerListAdapter.newInstance(controller);
    }

    @Override
    protected void readArguments(Bundle bundle) {
        super.readArguments(bundle);
        mMovieId = bundle.getInt(ARG_MOVIE_ID);
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

        ibMovieStar.setImageResource(movie.isStar() ? R.drawable.ic_favorite_white_24dp : R.drawable.ic_fab_star);
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
            setPaletteForStarFab(vibrantSwatch);
            setPaletteForStarView(vibrantSwatch);
            //setVibrantColor(vibrantSwatch);
        }
    }

    private void setPaletteForStarFab(Palette.Swatch swatch) {
        if (swatch != null) {
            ibMovieStar.getBackground().setColorFilter(swatch.getRgb(), PorterDuff.Mode.MULTIPLY);
        }
    }

    private void setPaletteForStarView(Palette.Swatch swatch) {
        if (swatch != null) {
            vpMovieStar.getBackground().setColorFilter(swatch.getRgb(), PorterDuff.Mode.MULTIPLY);
        }
    }

    private void setPaletteForRootContainer(Palette.Swatch colorDarkVaient) {
        svContainerTrailer.setBackgroundColor(colorDarkVaient.getRgb());
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
                MovieContract.MovieEntry.buildMovieUriWithMovieId(mMovieId),
                null, null, null, null
        );
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
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @OnClick(R.id.ib_movie_star)
    public void onTapStarMovie(View view) {
        if (mMovie.isStar()) {
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.remove_movie_from_favourite_confirmation_msg)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            mMovie.setStar(false);
                            mMovie.updateMovieStarStatus(); //TODO On Main Thread ?
                            ibMovieStar.setImageResource(R.drawable.ic_fab_star);
                        }})
                    .setNegativeButton(R.string.no, null).show();
        } else {
            mMovie.setStar(true);
            mMovie.updateMovieStarStatus(); //TODO On Main Thread ?
            vpMovieStar.showMovieSaved(view, new ViewPodMovieStar.ControllerMovieSaved() {
                @Override
                public void onMovieSavedAnimationFinish() {
                    ibMovieStar.setImageResource(R.drawable.ic_favorite_white_24dp);
                }
            });
        }
    }
}
