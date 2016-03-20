package net.aung.moviemaniac.fragments.pagers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.adapters.MoviePagerAdapter;
import net.aung.moviemaniac.fragments.MovieListFragment;
import net.aung.moviemaniac.utils.MovieManiacConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 3/19/16.
 */
public class MoviePagerFragment extends Fragment {

    @Bind(R.id.pager_movies)
    ViewPager pagerMovies;

    @Bind(R.id.tl_movies)
    TabLayout tlMovies;

    private MoviePagerAdapter mMoviePagerAdapter;

    public static MoviePagerFragment newInstance() {
        return new MoviePagerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMoviePagerAdapter = new MoviePagerAdapter(getActivity().getSupportFragmentManager());
        mMoviePagerAdapter.addTab(MovieListFragment.newInstance(MovieManiacConstants.CATEGORY_NOW_PLAYING_MOVIES), getString(R.string.now_playing_movies));
        mMoviePagerAdapter.addTab(MovieListFragment.newInstance(MovieManiacConstants.CATEGORY_UPCOMING_MOVIES), getString(R.string.upcoming_movies));
        mMoviePagerAdapter.addTab(MovieListFragment.newInstance(MovieManiacConstants.CATEGORY_MOST_POPULAR_MOVIES), getString(R.string.most_popular_movies));
        mMoviePagerAdapter.addTab(MovieListFragment.newInstance(MovieManiacConstants.CATEGORY_TOP_RATED_MOVIES), getString(R.string.top_rated_movies));
        mMoviePagerAdapter.addTab(MovieListFragment.newInstance(MovieManiacConstants.CATEGORY_MY_FAVOURITES_MOVIES), getString(R.string.my_favourites));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_pager, container, false);
        ButterKnife.bind(this, rootView);

        pagerMovies.setAdapter(mMoviePagerAdapter);
        pagerMovies.setOffscreenPageLimit(mMoviePagerAdapter.getCount());
        tlMovies.setupWithViewPager(pagerMovies);

        return rootView;
    }
}
