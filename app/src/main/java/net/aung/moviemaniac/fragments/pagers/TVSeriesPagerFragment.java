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
import net.aung.moviemaniac.fragments.TVSeriesListFragment;
import net.aung.moviemaniac.utils.MovieManiacConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 3/19/16.
 */
public class TVSeriesPagerFragment extends Fragment {

    @Bind(R.id.pager_tv_series)
    ViewPager pagerTVSeries;

    @Bind(R.id.tl_tv_series)
    TabLayout tlTVSeries;

    private MoviePagerAdapter mMoviePagerAdapter;

    public static TVSeriesPagerFragment newInstance() {
        return new TVSeriesPagerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMoviePagerAdapter = new MoviePagerAdapter(getActivity().getSupportFragmentManager());
        mMoviePagerAdapter.addTab(TVSeriesListFragment.newInstance(MovieManiacConstants.CATEGORY_MOST_POPULAR_TV_SERIES), getString(R.string.most_popular_tv_series));
        mMoviePagerAdapter.addTab(TVSeriesListFragment.newInstance(MovieManiacConstants.CATEGORY_TOP_RATED_TV_SERIES), getString(R.string.top_rated_tv_series));
        mMoviePagerAdapter.addTab(TVSeriesListFragment.newInstance(MovieManiacConstants.CATEGORY_MY_FAVOURITES_TV_SERIES), getString(R.string.my_favourites));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tv_series_pager, container, false);
        ButterKnife.bind(this, rootView);

        pagerTVSeries.setAdapter(mMoviePagerAdapter);
        pagerTVSeries.setOffscreenPageLimit(mMoviePagerAdapter.getCount());
        tlTVSeries.setupWithViewPager(pagerTVSeries);

        return rootView;
    }
}
