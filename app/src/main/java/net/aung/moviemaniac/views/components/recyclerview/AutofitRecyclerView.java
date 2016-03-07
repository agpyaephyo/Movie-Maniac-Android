package net.aung.moviemaniac.views.components.recyclerview;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by aung on 12/12/15.
 */
public class AutofitRecyclerView extends RecyclerView {

    private GridLayoutManager layoutManager;
    private int columnWidth = -1;

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

        layoutManager = new GridLayoutManager(getContext(), DEFAULT_COLUMN_SPAN_COUNT); //
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
}
