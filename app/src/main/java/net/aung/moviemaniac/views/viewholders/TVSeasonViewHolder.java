package net.aung.moviemaniac.views.viewholders;

import android.databinding.DataBindingUtil;
import android.view.View;

import net.aung.moviemaniac.controllers.SeasonItemController;
import net.aung.moviemaniac.data.vos.TVSeasonVO;
import net.aung.moviemaniac.databinding.ViewItemSeasonBinding;

/**
 * Created by aung on 3/20/16.
 */
public class TVSeasonViewHolder extends BaseViewHolder<TVSeasonVO> {

    private ViewItemSeasonBinding binding;
    private SeasonItemController controller;

    public TVSeasonViewHolder(View itemView, SeasonItemController controller) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        this.controller = controller;
    }

    @Override
    public void bind(TVSeasonVO data) {
        binding.setSeason(data);
    }

    @Override
    public void onClick(View v) {

    }
}
