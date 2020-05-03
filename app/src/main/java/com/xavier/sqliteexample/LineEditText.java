package com.xavier.sqliteexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

public class LineEditText extends AppCompatEditText {
    private Rect rect;
    private Paint paint;

    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        int black = ContextCompat.getColor(context, R.color.black);
        paint.setColor(black);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = ((View)this.getParent()).getHeight();
        int lineHeight = getLineHeight();
        int number_of_lines = height / lineHeight;

        Rect r = rect;
        Paint p = paint;

        int baseLine = getLineBounds(0, r);
        for (int i = 0; i < number_of_lines; i++) {
            canvas.drawLine(r.left, baseLine + 1, r.right, baseLine + 1, paint);

            baseLine += lineHeight;
        }

        super.onDraw(canvas);
    }
}
