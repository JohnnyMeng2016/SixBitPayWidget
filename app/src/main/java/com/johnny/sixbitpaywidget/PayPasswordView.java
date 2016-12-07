package com.johnny.sixbitpaywidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * 项目名称：ABook
 * 类描述：六位支付密码表单
 * 创建人：孟忠明
 * 创建时间：2016/11/28
 */
public class PayPasswordView extends TextView {

    private int textLength;
    private int passwordLength = 6;
    private int border = 3;
    private int passwordWidth = 16;
    private int bolderColor = 0xFFE6E6E6;
    private Paint paint = new Paint(ANTI_ALIAS_FLAG);
    private Paint passwordPaint = new Paint(ANTI_ALIAS_FLAG);
    private OnCompleteLinstener onCompleteLinstener;

    public PayPasswordView(Context context) {
        super(context);
    }

    public PayPasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnCompleteLinstener(OnCompleteLinstener onCompleteLinstener) {
        this.onCompleteLinstener = onCompleteLinstener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        //第一层
        paint.setColor(bolderColor);
        RectF rect = new RectF(0, 0, width, height);
        canvas.drawRect(rect, paint);

        //第二层
        paint.setColor(0xFFFFFFFF);
        rect = new RectF(border, border, width - border, height - border);
        canvas.drawRect(rect, paint);

        //画分割线
        paint.setColor(bolderColor);
        for (int i = 1; i < passwordLength; i++) {
            int startX = width / passwordLength * i;
            canvas.drawLine(startX, border, startX, height - border, paint);
        }

        // 密码
        passwordPaint.setStrokeWidth(passwordWidth);
        passwordPaint.setStyle(Paint.Style.FILL);
        passwordPaint.setColor(0xFF000000);
        float cx, cy = height / 2;
        float half = width / passwordLength / 2;
        for (int i = 0; i < textLength; i++) {
            cx = width * i / passwordLength + half;
            canvas.drawCircle(cx, cy, passwordWidth, passwordPaint);
        }

        if (this.textLength == 6) {
            onCompleteLinstener.onComplete();
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textLength = text.toString().length();
        invalidate();
    }

    public interface OnCompleteLinstener {
        void onComplete();
    }
}
