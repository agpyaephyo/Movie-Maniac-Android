package net.aung.moviemaniac.views.viewholders;

import android.databinding.DataBindingUtil;
import android.view.View;

import net.aung.moviemaniac.controllers.TrailerItemController;
import net.aung.moviemaniac.data.vos.TrailerVO;
import net.aung.moviemaniac.databinding.ViewItemTrailerBinding;

import butterknife.ButterKnife;

/**
 * Created by aung on 12/16/15.
 */
public class TrailerViewHolder extends BaseViewHolder<TrailerVO> {

    private ViewItemTrailerBinding binding;
    private TrailerItemController controller;

    public TrailerViewHolder(View itemView, TrailerItemController controller) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        binding = DataBindingUtil.bind(itemView);
        this.controller = controller;
    }

    @Override
    public void bind(TrailerVO data) {
        binding.setTrailer(data);
    }

    @Override
    public void onClick(View view) {
        controller.onShowTrailer(binding.getTrailer());
    }
}
