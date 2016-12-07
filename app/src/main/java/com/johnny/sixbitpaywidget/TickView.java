package com.johnny.sixbitpaywidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * 项目名称：SixBitPayWidget
 * 类描述：打钩打叉空间
 * 创建人：孟忠明
 * 创建时间：2016/12/6
 */
public class TickView extends View {

    private int paintWidth = 10;
    private int color = 0xFF4285F4;
    private Paint paint = new Paint(ANTI_ALIAS_FLAG);
    private Paint linePaint = new Paint(ANTI_ALIAS_FLAG);
    private boolean isSuccess;

    private int circleInterrupt;
    private int lineOneInterrupt;
    private int lineTwoInterrupt;

    public TickView(Context context) {
        super(context);
    }

    public TickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.tickView, 0, 0);
        paintWidth = (int) ta.getDimension(R.styleable.tickView_paintWidth, 10);
        isSuccess = ta.getBoolean(R.styleable.tickView_isSuccess, false);
        color = ta.getColor(R.styleable.tickView_paintColor, 0xFF4285F4);
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        paint.setStrokeWidth(paintWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);

        canvas.drawColor(Color.TRANSPARENT);
        RectF rectF = new RectF(paintWidth, paintWidth, width - paintWidth, height - paintWidth);
        canvas.drawArc(rectF, -180, 360 * circleInterrupt / 100, false, paint);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (circleInterrupt < 100) {
                    circleInterrupt += 4;
                    invalidate();
                }
            }
        }, 10);

        if (isSuccess) {
            linePaint.setStrokeWidth(paintWidth);
            linePaint.setStyle(Paint.Style.FILL);
            linePaint.setColor(color);
            int endOneX = 0;
            int endOneY = 0;
            if (circleInterrupt >= 100) {
                int unitWidth = width / 7;
                int unitHeight = width / 2;
                int startX = unitWidth * 2;
                int startY = unitHeight;
                canvas.drawCircle(startX, startY, paintWidth / 2 - 1, linePaint);
                endOneX = startX + (int) (1.3 * (float) unitWidth * (float) lineOneInterrupt / 100.0);
                endOneY = startY + (int) (Math.tan(45 * Math.PI / 180) * (endOneX - startX));
                canvas.drawLine(startX, startY, endOneX, endOneY, paint);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (lineOneInterrupt < 100) {
                            lineOneInterrupt += 10;
                            invalidate();
                        }
                    }
                }, 10);
            }
            int endTwoX = 0;
            int endTwoY = 0;
            if (lineOneInterrupt >= 100) {
                int unitWidth = width / 7;
                int startX = endOneX;
                int startY = endOneY;
                canvas.drawCircle(startX, startY, paintWidth / 2 - 1, linePaint);
                endTwoX = startX + (int) (2.2 * (float) unitWidth * (float) lineTwoInterrupt / 100.0);
                endTwoY = startY + (int) (Math.tan(-45 * Math.PI / 180) * (endTwoX - startX));
                canvas.drawLine(startX, startY, endTwoX, endTwoY, paint);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (lineTwoInterrupt < 100) {
                            lineTwoInterrupt += 10;
                            invalidate();
                        }
                    }
                }, 10);
            }
            if (lineTwoInterrupt >= 100) {
                canvas.drawCircle(endTwoX, endTwoY, paintWidth / 2 - 1, linePaint);
            }
        } else {
            linePaint.setStrokeWidth(paintWidth);
            linePaint.setStyle(Paint.Style.FILL);
            linePaint.setColor(color);
            int endOneX = 0;
            int endOneY = 0;
            if (circleInterrupt >= 100) {
                int unitWidth = width / 7;
                int unitHeight = width / 7;
                int startX = unitWidth * 2;
                int startY = unitHeight * 2;
                canvas.drawCircle(startX, startY, paintWidth / 2 - 1, linePaint);
                endOneX = startX + (int) (3.2 * (float) unitWidth * (float) lineOneInterrupt / 100.0);
                endOneY = startY + (int) (Math.tan(45 * Math.PI / 180) * (endOneX - startX));
                canvas.drawLine(startX, startY, endOneX, endOneY, paint);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (lineOneInterrupt < 100) {
                            lineOneInterrupt += 10;
                            invalidate();
                        }
                    }
                }, 10);
            }
            int endTwoX = 0;
            int endTwoY = 0;
            if (lineOneInterrupt >= 100) {
                int unitWidth = width / 7;
                int startX = unitWidth * 2;
                int startY = endOneY;
                canvas.drawCircle(endOneX, endOneY, paintWidth / 2 - 1, linePaint);
                canvas.drawCircle(startX, startY, paintWidth / 2 - 1, linePaint);
                endTwoX = startX + (int) (3.2 * (float) unitWidth * (float) lineTwoInterrupt / 100.0);
                endTwoY = startY + (int) (Math.tan(-45 * Math.PI / 180) * (endTwoX - startX));
                canvas.drawLine(startX, startY, endTwoX, endTwoY, paint);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (lineTwoInterrupt < 100) {
                            lineTwoInterrupt += 10;
                            invalidate();
                        }
                    }
                }, 10);
            }
            if (lineTwoInterrupt >= 100) {
                canvas.drawCircle(endTwoX, endTwoY, paintWidth / 2 - 1, linePaint);
            }
        }
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
