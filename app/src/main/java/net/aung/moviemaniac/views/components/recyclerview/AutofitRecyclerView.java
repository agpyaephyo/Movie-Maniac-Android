package net.aung.moviemaniac.views.components.recyclerview;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import net.aung.moviemaniac.R;

/**
 * Created by aung on 12/12/15.
 */
public class AutofitRecyclerView extends RecyclerView {

    private GridLayoutManager layoutManager;
    private int columnWidth = -1;

    private View vEmptyView;

    private static final int DEFAULT_COLUMN_SPAN_COUNT = 2;

    public AutofitRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public AutofitRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutofitRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        /*
        if (attrs != null) {
            int[] attrsArray = {
                    android.R.attr.columnWidth //the column width of the list item.
            };

            TypedArray array = context.obtainStyledAttributes(attrs, attrsArray); //Retrieve styled attribute information
            columnWidth = array.getDimensionPixelSize(0, -1); //this part is tricky.
            array.recycle();
        }
        */

        addItemDecoration(new DividerItemDecoration(getContext()));
        setHasFixedSize(true);

        int gridColumnSpanCount = getResources().getInteger(R.integer.movieListGrid);

        layoutManager = new GridLayoutManager(getContext(), gridColumnSpanCount); //
        setLayoutManager(layoutManager);

        setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        /*
        if (columnWidth > 0) {
            int spanCount = Math.max(1, getMeasuredWidth() / columnWidth); //if the container's width is large enough to fit in more than one column, will use multiple column.
            layoutManager.setSpanCount(spanCount);
        }
        */
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if(oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(dataObserver);
        }
        super.setAdapter(adapter);
        if(adapter != null) {
            adapter.registerAdapterDataObserver(dataObserver);
        }
        checkIfEmpty();
    }

    public void setEmptyView(View view) {
        this.vEmptyView = view;
        checkIfEmpty();
    }

    protected void checkIfEmpty() {
        if(vEmptyView != null && getAdapter() != null) {
            final boolean isEmpty = getAdapter().getItemCount() == 0;
            vEmptyView.setVisibility(isEmpty ? VISIBLE : GONE);
            setVisibility(isEmpty ? GONE : VISIBLE);
        }
    }

    private AdapterDataObserver dataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkIfEmpty();
        }
    };
}
