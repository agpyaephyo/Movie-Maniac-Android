package net.aung.moviemaniac.views.pods;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.aung.moviemaniac.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 3/21/16.
 */
public class ViewPodEmpty extends RelativeLayout {

    @Bind(R.id.lbl_empty)
    TextView lblEmpty;

    @Bind(R.id.iv_empty)
    ImageView ivEmpty;

    public ViewPodEmpty(Context context) {
        super(context);
    }

    public ViewPodEmpty(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodEmpty(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    public void setEmptyLabel(String label) {
        lblEmpty.setText(label);
    }
}
