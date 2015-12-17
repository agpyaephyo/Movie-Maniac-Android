package net.aung.moviemaniac.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.aung.moviemaniac.adapters.TrailerListAdapter;
import net.aung.moviemaniac.data.vos.TrailerVO;
import net.aung.moviemaniac.mvp.presenters.MovieDetailPresenter;
import net.aung.moviemaniac.mvp.views.MovieDetailView;
import net.aung.moviemaniac.views.pods.ViewPodGenreListDetail;
import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.controllers.TrailerItemController;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.databinding.FragmentMovieDetailBinding;
import net.aung.moviemaniac.views.components.recyclerview.TrailerItemDecoration;
import net.aung.moviemaniac.views.pods.ViewPodMoviePopularityDetail;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 12/15/15.
 */
public class MovieDetailFragment extends BaseFragment
        implements MovieDetailView,
        Palette.PaletteAsyncListener {

    private static final String ARG_MOVIE_ID = "ARG_MOVIE_ID";

    private int movieId;
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
        presenter = new MovieDetailPresenter(this, movieId);
        presenter.onCreate();

        poster = MovieManiacApp.sPosterCache.get(0);
        Palette.from(poster).generate(this);

        trailerAdapter = TrailerListAdapter.newInstance(controller);
    }

    @Override
    protected void readArguments(Bundle bundle) {
        super.readArguments(bundle);
        movieId = bundle.getInt(ARG_MOVIE_ID);
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
        if (!movie.isDetailLoaded()) {
            vpMoviePopularity.drawPopularityIcons(movie.getPopularity());
        }
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
            setPaletteforTagLine(colorDarkVaient, colorLightVarient);
            //setVibrantColor(vibrantSwatch);
        }
    }

    private void setPaletteForRootContainer(Palette.Swatch colorDarkVaient) {
        svContainerTrailer.setBackgroundColor(colorDarkVaient.getRgb());
    }

    private void setPaletteforTagLine(Palette.Swatch colorDarkVaient, Palette.Swatch colorLightVarient) {
        if (colorDarkVaient != null && colorLightVarient != null) {
            tvTitle.setTextColor(colorDarkVaient.getRgb());
            tvTitle.setBackgroundColor(colorLightVarient.getRgb());
        }
    }
}
