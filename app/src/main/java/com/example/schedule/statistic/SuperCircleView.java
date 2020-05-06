package com.example.schedule.statistic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.example.schedule.R;

public class SuperCircleView extends View {
    private final String TAG = "SuperCircleView";
    private int mViewCenterX;
    private int mViewCenterY;

    private int mMinRadio;
    private float mRingWidth;
    private int mMinCircleColor;
    private int mRingNormalColor;
    private Paint mPaint;
    private int color[] = new int[3];

    private RectF mRectF;
    private int mSelectRing = 0;
    private int mMaxValue;
    private float goodPercent = 0.12f;
    private float ordinaryPercent =0.55f;
    private float badPercent = 0.33f;
    public SuperCircleView(Context context) {
        this(context, null);
    }

    public SuperCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SuperCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SuperCircleView);
        mMinRadio = a.getInteger(R.styleable.SuperCircleView_min_circle_radio, 400);
        mRingWidth = a.getFloat(R.styleable.SuperCircleView_ring_width, 40);
        mMinCircleColor = a.getColor(R.styleable.SuperCircleView_circle_color, context.getResources().getColor(R.color.white));
        mRingNormalColor = a.getColor(R.styleable.SuperCircleView_ring_normal_color, context.getResources().getColor(R.color.gray));
        mSelectRing = a.getInt(R.styleable.SuperCircleView_ring_color_select, 0);

        mMaxValue = a.getInt(R.styleable.SuperCircleView_maxValue, 100);

        a.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        this.setWillNotDraw(false);
        color[0] = getResources().getColor(R.color.green); //good
        color[2] = getResources().getColor(R.color.gray); //ord
        color[1] = getResources().getColor(R.color.red); //bad
    }

    //确定位置
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();
        mViewCenterX = viewWidth / 2;
        mViewCenterY = viewHeight / 2;
        mRectF = new RectF(mViewCenterX - mMinRadio - mRingWidth / 2, mViewCenterY - mMinRadio - mRingWidth / 2, mViewCenterX + mMinRadio + mRingWidth / 2, mViewCenterY + mMinRadio + mRingWidth / 2);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mMinCircleColor);
        canvas.drawCircle(mViewCenterX, mViewCenterY, mMinRadio, mPaint);
        drawNormalRing(canvas);
        canvas.drawCircle(mViewCenterX, mViewCenterY, mMinRadio-100, mPaint);
    }

    private void drawNormalRing(Canvas canvas) {
        float goodAngle=360*goodPercent;
        float ordinaryAngle=360*ordinaryPercent;
        float badAngle=360*badPercent;
        Paint ringNormalPaint = new Paint(mPaint);
        ringNormalPaint.setStyle(Paint.Style.FILL);
        ringNormalPaint.setColor(color[0]);
        canvas.drawArc(mRectF, -90, goodAngle, true, ringNormalPaint);
        ringNormalPaint.setColor(color[1]);
        canvas.drawArc(mRectF, goodAngle-90, badAngle, true, ringNormalPaint);
        ringNormalPaint.setColor(color[2]);
        canvas.drawArc(mRectF,  badAngle+goodAngle-90, ordinaryAngle, true, ringNormalPaint);
    }
    public void setValue(TextView textView) {
        String evaluation= String.format("完成率为%.1f\n失败率为%.1f\n未记录率为%.1f",goodPercent,badPercent,ordinaryPercent);
        textView.setText(evaluation);
    }


}
