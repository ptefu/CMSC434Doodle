package course.examples.cmsc434doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DoodleView extends View {

    private Path mPath;
    private Paint mPaintDoodle, canvasPaint;
    private Canvas canvas;
    private Bitmap canvasBitmap;

    public DoodleView(Context context) {
        super(context);
        init(null, 0);
    }

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        mPath = new Path(); //
        mPaintDoodle = new Paint(); //
        canvasPaint = new Paint(Paint.DITHER_FLAG); //
        mPaintDoodle.setColor(Color.BLACK);
        mPaintDoodle.setAntiAlias(true);
        mPaintDoodle.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(canvasBitmap);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, mPaintDoodle);
        canvas.drawPath(mPath, mPaintDoodle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(mPath, mPaintDoodle);
                mPath.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void clear() {
        canvas.drawColor(Color.WHITE);
        invalidate();
    }

    public void setColor(int color) {
        mPaintDoodle.setColor(color);
        invalidate();
    }

    public void fillBackground() {
        canvas.drawColor(mPaintDoodle.getColor());
        invalidate();
    }

    public void setBrushSize(float f) {
        mPaintDoodle.setStrokeWidth(f);
    }
}
