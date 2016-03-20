package net.aung.moviemaniac.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.controllers.SeasonItemController;
import net.aung.moviemaniac.controllers.TrailerItemController;
import net.aung.moviemaniac.data.vos.TVSeasonVO;
import net.aung.moviemaniac.data.vos.TrailerVO;
import net.aung.moviemaniac.views.viewholders.TVSeasonViewHolder;
import net.aung.moviemaniac.views.viewholders.TrailerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aung on 12/12/15.
 */
public class SeasonListAdapter extends RecyclerView.Adapter<TVSeasonViewHolder> {

    private List<TVSeasonVO> seasonList;
    private SeasonItemController controller;

    public static SeasonListAdapter newInstance(SeasonItemController controller) {
        List<TVSeasonVO> seasonList = new ArrayList<>();
        return new SeasonListAdapter(seasonList, controller);
    }

    private SeasonListAdapter(@NonNull List<TVSeasonVO> seasonList, SeasonItemController controller) {
        this.seasonList = seasonList;
        this.controller = controller;
    }

    @Override
    public TVSeasonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View viSeason = inflater.inflate(R.layout.view_item_season, parent, false);
        return new TVSeasonViewHolder(viSeason, controller);
    }

    @Override
    public void onBindViewHolder(TVSeasonViewHolder holder, int position) {
        TVSeasonVO season = seasonList.get(position);
        holder.bind(season);
    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }

    public void setSeasonList(List<TVSeasonVO> seasonList) {
        this.seasonList.clear();
        this.seasonList.addAll(seasonList);
        notifyDataSetChanged();
    }
}
