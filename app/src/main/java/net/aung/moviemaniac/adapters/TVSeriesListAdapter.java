package net.aung.moviemaniac.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.controllers.MovieItemController;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.data.vos.TVSeriesVO;
import net.aung.moviemaniac.views.viewholders.MovieViewHolder;
import net.aung.moviemaniac.views.viewholders.TVSeriesViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aung on 12/12/15.
 */
public class TVSeriesListAdapter extends RecyclerView.Adapter<TVSeriesViewHolder> {

    private List<TVSeriesVO> tvSeriesList;
    private boolean isFavouriteSection;

    public static TVSeriesListAdapter newInstance(boolean isFavouriteSection) {
        List<TVSeriesVO> tvSeriesList = new ArrayList<>();
        return new TVSeriesListAdapter(tvSeriesList, isFavouriteSection);
    }

    //Let's make sure tvSeriesList is never null. Ref: empty data pattern.
    private TVSeriesListAdapter(@NonNull List<TVSeriesVO> tvSeriesList, boolean isFavouriteSection) {
        this.tvSeriesList = tvSeriesList;
        this.isFavouriteSection = isFavouriteSection;
    }

    @Override
    public TVSeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View viMovie = inflater.inflate(R.layout.view_item_tv_series, parent, false);
        return new TVSeriesViewHolder(viMovie, isFavouriteSection);
    }

    @Override
    public void onBindViewHolder(TVSeriesViewHolder holder, int position) {
        TVSeriesVO tvSeries = tvSeriesList.get(position);
        holder.bind(tvSeries);
    }

    @Override
    public int getItemCount() {
        return tvSeriesList.size();
    }

    public void appendMovieList(@NonNull List<TVSeriesVO> newTVSeriesList) {
        tvSeriesList.addAll(newTVSeriesList);
        notifyDataSetChanged();
    }

    public void setMovieList(List<TVSeriesVO> tvSeriesList) {
        this.tvSeriesList.clear();
        appendMovieList(tvSeriesList);
    }
}
