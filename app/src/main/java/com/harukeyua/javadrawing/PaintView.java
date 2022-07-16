package com.harukeyua.javadrawing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;

public class PaintView extends View {

    enum PaintMode {STROKE, OBJECT}

    private static final int DEFAULT_COLOR = Color.BLACK;
    private static final int TEXT_SIZE_SP = 36;

    private final Path path = new Path();
    private Canvas extraCanvas;
    private Bitmap extraBitmap;
    private Paint backgroundPaint;
    private Paint strokePaint;
    private Paint textPaint;

    private final int touchTolerance = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    private float currentX = 0f;
    private float currentY = 0f;

    private float motionTouchEventX = 0f;
    private float motionTouchEventY = 0f;

    private String currentEmoji = null;
    private PaintMode currentPaintMode = PaintMode.STROKE;

    public PaintView(Context context) {
        super(context);
        initPaint();
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    private void initPaint() {
        strokePaint = new Paint();
        strokePaint.setColor(DEFAULT_COLOR);
        strokePaint.setAntiAlias(true);
        strokePaint.setDither(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeJoin(Paint.Join.ROUND);
        strokePaint.setStrokeCap(Paint.Cap.ROUND);
        strokePaint.setStrokeWidth(12f);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(spToPx(TEXT_SIZE_SP));

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
    }

    private float spToPx(int sp) {
        return sp * Resources.getSystem().getDisplayMetrics().density;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (extraBitmap != null) extraBitmap.recycle();
        extraBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        extraCanvas = new Canvas(extraBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
        canvas.drawBitmap(extraBitmap, 0f, 0f, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        motionTouchEventX = event.getX();
        motionTouchEventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (currentPaintMode == PaintMode.STROKE)
                    strokeTouchStart();
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentPaintMode == PaintMode.STROKE)
                    strokeTouchMove();
                break;
            case MotionEvent.ACTION_UP:
                if (currentPaintMode == PaintMode.STROKE)
                    strokeTouchUp();
                else if (currentPaintMode == PaintMode.OBJECT)
                    objectTouchUp();
                break;
        }
        return true;
    }

    private void strokeTouchStart() {
        path.reset();
        path.moveTo(motionTouchEventX, motionTouchEventY);
        currentX = motionTouchEventX;
        currentY = motionTouchEventY;
    }

    private void strokeTouchMove() {
        float dx = Math.abs(motionTouchEventX - currentX);
        float dy = Math.abs(motionTouchEventY - currentY);
        if (dx > touchTolerance || dy > touchTolerance) {
            path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2, (motionTouchEventY + currentY) / 2);
            currentX = motionTouchEventX;
            currentY = motionTouchEventY;
            extraCanvas.drawPath(path, strokePaint);
        }
        invalidate();
    }

    private void strokeTouchUp() {
        path.reset();
    }

    private void objectTouchUp() {
        if (currentEmoji != null) {
            float width = textPaint.measureText(currentEmoji);
            extraCanvas.drawText(currentEmoji, motionTouchEventX - (width / 2), motionTouchEventY, textPaint);
            invalidate();
        }
    }

    public void setPaintColor(@ColorRes int colorRes) {
        currentPaintMode = PaintMode.STROKE;
        strokePaint.setColor(getContext().getColor(colorRes));
    }

    public void setEmoji(String emoji) {
        currentPaintMode = PaintMode.OBJECT;
        currentEmoji = emoji;
    }
}
