package net.aung.moviemaniac.views.components.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by aung on 12/12/15.
 */
public class LobsterRegularTextView extends TextView {

    public LobsterRegularTextView(Context context) {
        super(context);
        if (!isInEditMode())
            init(context);
    }

    public LobsterRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init(context);
    }

    public LobsterRegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            init(context);
    }

    private void init(Context context) {
        Typeface t = Typeface.createFromAsset(context.getAssets(), "fonts/Lobster-Regular.ttf");
        this.setTypeface(t);
    }
}
