package net.aung.moviemaniac.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.aung.moviemaniac.controllers.TrailerItemController;
import net.aung.moviemaniac.data.vos.TrailerVO;
import net.aung.moviemaniac.views.viewholders.TrailerViewHolder;
import net.aung.moviemaniac.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aung on 12/12/15.
 */
public class TrailerListAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private List<TrailerVO> movieList;
    private TrailerItemController controller;

    public static TrailerListAdapter newInstance(TrailerItemController controller) {
        List<TrailerVO> movieList = new ArrayList<>();
        return new TrailerListAdapter(movieList, controller);
    }

    private TrailerListAdapter(@NonNull List<TrailerVO> movieList, TrailerItemController controller) {
        this.movieList = movieList;
        this.controller = controller;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View viMovie = inflater.inflate(R.layout.view_item_trailer, parent, false);
        return new TrailerViewHolder(viMovie, controller);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        TrailerVO trailer = movieList.get(position);
        holder.bind(trailer);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setTrailerList(List<TrailerVO> movieList) {
        this.movieList.clear();
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }
}
