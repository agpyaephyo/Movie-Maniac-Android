package net.aung.moviemaniac.views.pods;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.aung.moviemaniac.data.vos.GenreVO;
import net.aung.moviemaniac.views.components.textview.SmallDashUnderlineTextView;

import java.util.List;

/**
 * Created by aung on 12/16/15.
 */
public class ViewPodGenreList extends LinearLayout {

    public ViewPodGenreList(Context context) {
        super(context);
    }

    public ViewPodGenreList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodGenreList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setGenreList(List<GenreVO> genreList) {
        if (genreList == null || genreList.size() == 0)
            return;

        if (getChildCount() == 0) {
            for (int position = 0; position < genreList.size(); position++) {
                TextView tv = new SmallDashUnderlineTextView(getContext());
                GenreVO genre = genreList.get(position);
                if(genre != null) {
                    tv.setText(genre.getName());
                    addView(tv);
                }
            }
        }
    }
}
