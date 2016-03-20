package net.aung.moviemaniac.views.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.controllers.BaseController;
import net.aung.moviemaniac.controllers.ViewController;
import net.aung.moviemaniac.data.vos.MenuVO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 3/19/16.
 */
public class ViewItemMenu extends FrameLayout implements ViewController {

    @Bind(R.id.tv_menu_label)
    TextView tvMenuLabel;

    private MenuItemController controller;
    private MenuVO mMenuVO;

    public ViewItemMenu(Context context) {
        super(context);
    }

    public ViewItemMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewItemMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onTapMenu(mMenuVO);
            }
        });
    }

    public void setData(MenuVO menu) {
        this.mMenuVO = menu;
        tvMenuLabel.setText(menu.getMenuLabel());
    }

    @Override
    public void setController(BaseController controller) {
        this.controller = (MenuItemController) controller;
    }

    public interface MenuItemController extends BaseController {
        void onTapMenu(MenuVO menu);
    }
}
