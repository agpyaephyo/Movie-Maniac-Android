package net.aung.moviemaniac.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.activities.SearchActivity;
import net.aung.moviemaniac.adapters.MovieListAdapter;
import net.aung.moviemaniac.adapters.TVSeriesListAdapter;
import net.aung.moviemaniac.controllers.MovieItemController;
import net.aung.moviemaniac.controllers.TVSeriesItemController;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.data.vos.TVSeriesVO;
import net.aung.moviemaniac.mvp.presenters.SearchPresenter;
import net.aung.moviemaniac.mvp.views.SearchView;
import net.aung.moviemaniac.utils.GAUtils;
import net.aung.moviemaniac.views.components.recyclerview.AutofitRecyclerView;
import net.aung.moviemaniac.views.pods.ViewPodEmpty;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 3/21/16.
 */
public class SearchFragment extends BaseFragment implements SearchView {

    @Bind(R.id.vp_empty_search)
    ViewPodEmpty vpEmptySearch;

    @Bind(R.id.et_search)
    EditText etSearch;

    @Bind(R.id.input_layout_search)
    TextInputLayout tilSearch;

    @Bind(R.id.rv_search_results)
    AutofitRecyclerView rvSearchResults;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    private int mSearchType;
    private SearchPresenter mSearchPresenter;
    private MovieListAdapter mMovieListAdapter;
    private TVSeriesListAdapter mTvSeriesListAdapter;

    private MovieItemController mMovieItemController;
    private TVSeriesItemController mTVSeriesItemController;

    private View mRootView;

    private static final String BARG_SEARCH_TYPE = "BARG_SEARCH_TYPE";

    public static SearchFragment newInstance(int searchType) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BARG_SEARCH_TYPE, searchType);
        searchFragment.setArguments(bundle);
        return searchFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMovieItemController = (MovieItemController) context;
        mTVSeriesItemController = (TVSeriesItemController) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchPresenter = new SearchPresenter(mSearchType, this);
        mSearchPresenter.onCreate();

        mMovieListAdapter = MovieListAdapter.newInstance(mMovieItemController, false);
        mTvSeriesListAdapter = TVSeriesListAdapter.newInstance(false, mTVSeriesItemController);
    }

    @Override
    protected void readArguments(Bundle bundle) {
        super.readArguments(bundle);
        mSearchType = bundle.getInt(BARG_SEARCH_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, mRootView);

        vpEmptySearch.setEmptyLabel(getString(R.string.empty_search));

        if (mSearchType == SearchActivity.SEARCH_TYPE_MOVIE) {
            tilSearch.setHint(getString(R.string.search_hint_movies));
        } else if (mSearchType == SearchActivity.SEARCH_TYPE_TV_SERIES) {
            tilSearch.setHint(getString(R.string.search_hint_tv_series));
        }

        rvSearchResults.setGridColumnSpan(1);
        rvSearchResults.setEmptyView(vpEmptySearch);

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(etSearch.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    String query = etSearch.getText().toString();
                    mSearchPresenter.search(query);

                    swipeRefreshLayout.setEnabled(true);
                    swipeRefreshLayout.setRefreshing(true);

                    return true;
                }
                return false;
            }
        });

        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark);

        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mSearchPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mSearchPresenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSearchPresenter.onDestroy();
    }

    @Override
    protected void sendScreenHit() {
        if (mSearchType == SearchActivity.SEARCH_TYPE_MOVIE) {
            GAUtils.getInstance().sendScreenHit(GAUtils.SCREEN_NAME_MOVIE_SEARCH);
        } else if (mSearchType == SearchActivity.SEARCH_TYPE_TV_SERIES) {
            GAUtils.getInstance().sendScreenHit(GAUtils.SCREEN_NAME_TV_SERIES_SEARCH);
        }
    }

    @Override
    public void displaySearchResultMovie(List<MovieVO> movieSearchResult) {
        rvSearchResults.setGridColumnSpan(getResources().getInteger(R.integer.movieListGrid));
        mMovieListAdapter.setMovieList(movieSearchResult);
        rvSearchResults.setAdapter(mMovieListAdapter);

        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void displaySearchResultTVSeries(List<TVSeriesVO> tvSeriesSearchResult) {
        rvSearchResults.setGridColumnSpan(1);
        mTvSeriesListAdapter.setTVSeriesList(tvSeriesSearchResult);
        rvSearchResults.setAdapter(mTvSeriesListAdapter);

        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void displayFailInSearch(String message) {
        Snackbar.make(mRootView, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null).show();

        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setRefreshing(false);
    }
}
