package net.aung.moviemaniac.views.components.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by aung on 12/12/15.
 */
public class SmartScrollListener extends RecyclerView.OnScrollListener {

    private int visibleItemCount, pastVisibleItems, totalItemCount;
    private ControllerSmartScroll controller;
    private boolean isListEndReached = false;
    private int previousDy, currentDy;

    public SmartScrollListener(ControllerSmartScroll controller) {
        this.controller = controller;
    }

    @Override
    public void onScrolled(RecyclerView rv, int dx, int dy) {
        super.onScrolled(rv, dx, dy);

        currentDy = dy;
        if(currentDy > previousDy) {
            //from top to bottom
        } else if (currentDy < previousDy) {
            //from bottom to top
            isListEndReached = false;
        }

        visibleItemCount = rv.getLayoutManager().getChildCount();
        totalItemCount = rv.getLayoutManager().getItemCount();
        pastVisibleItems = ((GridLayoutManager) rv.getLayoutManager()).findFirstVisibleItemPosition();

        previousDy = currentDy;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
        super.onScrollStateChanged(recyclerView, scrollState);
        if(scrollState == RecyclerView.SCROLL_STATE_IDLE){
            if((visibleItemCount + pastVisibleItems) >= totalItemCount && !isListEndReached) {
                isListEndReached = true;
                controller.onListEndReached();
            }
        }
    }

    public interface ControllerSmartScroll {
        void onListEndReached();
    }
}
