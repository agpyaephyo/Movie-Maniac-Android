package net.aung.moviemaniac.views.components.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import net.aung.moviemaniac.R;

/**
 * Created by aung on 12/16/15.
 */
public class SmallDashUnderlineTextView extends TextView {

    private Rect mRect;
    private Paint mPaint;

    public SmallDashUnderlineTextView(Context context, int textColor) {
        super(context);
        init(textColor);
    }

    private void init(int textColor) {
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setColor(textColor);
        //mPaint.setARGB(255, 255, 255, 255);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        //mPaint.setPathEffect(new DashPathEffect(new float[]{50, 50}, 100));

        setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        setTextColor(textColor);
        setTextSize(14);
        setPadding(0,0,0, (int) getResources().getDimension(R.dimen.margin_medium));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = getLineCount();
        Rect r = mRect;
        Paint paint = mPaint;

        for (int i = 0; i < count; i++) {
            int baseline = getLineBounds(i, r);
            canvas.drawLine(r.left, baseline + 10, r.right, baseline + 10, paint);
        }

        super.onDraw(canvas);
    }
}
