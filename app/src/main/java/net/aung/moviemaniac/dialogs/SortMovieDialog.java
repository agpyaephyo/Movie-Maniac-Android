package net.aung.moviemaniac.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.utils.MovieManiacConstants;
import net.aung.moviemaniac.utils.SettingsUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 3/7/16.
 */
public class SortMovieDialog extends Dialog {

    @Bind(R.id.rdo_movie_sort_most_popular)
    RadioButton rdoMostPopular;

    @Bind(R.id.rdo_movie_sort_highest_rating)
    RadioButton rdoHighestRated;

    public SortMovieDialog(Context context) {
        super(context);
        initDialog();
    }

    private void initDialog() {
        getWindow();
        setContentView(R.layout.dialog_sort_movie);
        ButterKnife.bind(this, this);

        @MovieManiacConstants.SortOrder int sortOrder = SettingsUtils.getSortOrder();
        rdoMostPopular.setChecked(sortOrder == MovieManiacConstants.SORT_ORDER_MOST_POPULAR);
        rdoHighestRated.setChecked(sortOrder == MovieManiacConstants.SORT_ORDER_HIGHEST_RATED);

        rdoMostPopular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SettingsUtils.saveSortOrder(MovieManiacConstants.SORT_ORDER_MOST_POPULAR);
                    Toast.makeText(getContext(), "Sort by most popular", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });

        rdoHighestRated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SettingsUtils.saveSortOrder(MovieManiacConstants.SORT_ORDER_HIGHEST_RATED);
                    Toast.makeText(getContext(), "Sort by highest rating", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });
    }
}
