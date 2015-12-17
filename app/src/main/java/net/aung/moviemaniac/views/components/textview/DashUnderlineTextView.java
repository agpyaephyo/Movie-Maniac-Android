package net.aung.moviemaniac.views.components.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by aung on 12/16/15.
 */
public class DashUnderlineTextView extends TextView {

    private Rect mRect;
    private Paint mPaint;

    public DashUnderlineTextView(Context context) {
        super(context);
        init();
    }

    public DashUnderlineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DashUnderlineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setARGB(255, 255, 255, 255);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        //mPaint.setPathEffect(new DashPathEffect(new float[]{50, 50}, 100));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = getLineCount();
        Rect r = mRect;
        Paint paint = mPaint;

        for (int i = 0; i < count; i++) {
            int baseline = getLineBounds(i, r);
            canvas.drawLine(r.left, baseline + 20, r.right, baseline + 20, paint);
        }

        super.onDraw(canvas);
    }
}
