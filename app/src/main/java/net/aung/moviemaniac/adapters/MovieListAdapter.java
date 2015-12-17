package net.aung.moviemaniac.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.aung.moviemaniac.controllers.MovieItemController;
import net.aung.moviemaniac.views.viewholders.MovieViewHolder;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.data.vos.MovieVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aung on 12/12/15.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private List<MovieVO> movieList;
    private MovieItemController controller;

    public static MovieListAdapter newInstance(MovieItemController controller) {
        List<MovieVO> movieList = new ArrayList<>();
        return new MovieListAdapter(movieList, controller);
    }

    //Let's make sure movieList is never null. Ref: empty data pattern.
    private MovieListAdapter(@NonNull List<MovieVO> movieList, MovieItemController controller) {
        this.movieList = movieList;
        this.controller = controller;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View viMovie = inflater.inflate(R.layout.view_item_movie, parent, false);
        return new MovieViewHolder(viMovie, controller);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieVO movie = movieList.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void appendMovieList(@NonNull List<MovieVO> newMovieList) {
        movieList.addAll(newMovieList);
        notifyDataSetChanged();
    }

    public void setMovieList(List<MovieVO> movieList) {
        this.movieList.clear();
        appendMovieList(movieList);
    }
}
