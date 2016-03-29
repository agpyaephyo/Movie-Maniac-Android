package net.aung.moviemaniac.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.aung.moviemaniac.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aung on 3/29/16.
 */
public class ExpandedPosterDialog extends Dialog {

    @Bind(R.id.iv_expanded_poster)
    ImageView ivExpandedPoster;

    public ExpandedPosterDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialog();
    }

    private void initDialog() {
        getWindow();
        Resources resources = getContext().getResources();
        setContentView(R.layout.dialog_expaned_poster);
        ButterKnife.bind(this, this);
    }

    public void show(String posterUrl) {
        Glide.with(ivExpandedPoster.getContext())
                .load(posterUrl)
                .placeholder(R.drawable.place_holder_movie_maniac)
                .error(R.drawable.place_holder_movie_maniac)
                .into(ivExpandedPoster);

        super.show();
    }

    @OnClick(R.id.iv_dismiss)
    public void onTapDismissExpandedPoster(View view) {
        dismiss();
    }
}
