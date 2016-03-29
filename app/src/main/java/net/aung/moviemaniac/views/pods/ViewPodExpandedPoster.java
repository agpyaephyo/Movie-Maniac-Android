package net.aung.moviemaniac.views.pods;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.controllers.BaseController;
import net.aung.moviemaniac.controllers.ViewController;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aung on 3/29/16.
 */
public class ViewPodExpandedPoster extends FrameLayout implements ViewController {

    @Bind(R.id.iv_expanded_poster)
    ImageView ivExpandedPoster;

    private ControllerExpandedPoster mController;

    public ViewPodExpandedPoster(Context context) {
        super(context);
    }

    public ViewPodExpandedPoster(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodExpandedPoster(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    @OnClick(R.id.iv_dismiss)
    public void onTapDismissExpandedPoster(View view) {
        mController.dismissExpandedMode();
    }

    @Override
    public void setController(BaseController controller) {
        mController = (ControllerExpandedPoster) controller;
    }

    public interface ControllerExpandedPoster extends BaseController {
        void dismissExpandedMode();
    }
}
