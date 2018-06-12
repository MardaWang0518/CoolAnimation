package com.mardawang.android.coolanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * author mardawang
 * <p>
 * email:wy363681759@163.com
 * <p>
 * date: 2018/5/29
 * <p>
 * desc: 简单的规律绘制直线构造神奇的视觉效果
 * <p>
 * 启发、原理:http://mp.weixin.qq.com/s/FieNhelCar1cZjhBS28ymQ
 * <p>
 */
public class AnimationView extends View {
    private static final String TAG = "AnimationView";
    //起点在x、y移动范围
    private float p1XLength = 350, p1YLength = 100, speedP1 = 0.15f;
    private float p2XLength = 100, p2YLength = 350, speedP2 = 0.05f;
    private double angleP1 = 0, angleP2 = 0;
    private int viewWidth, viewHeight;
    private Paint paint;
    private ValueAnimator valueAnimator;
    //记录移动过的所有点的数据
    private List<CorrdinateData> corrDatas;
    private DrawingListener drawingListener;
    //    private int[] colors = new int[]{Color.RED, Color.WHITE, Color.BLUE};
    //动画绘制的时间
    private int animDuration = 12 * 1000;

    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AnimationView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setBackgroundColor(Color.BLACK);

        paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(animDuration);
        valueAnimator.addUpdateListener(animatorUpdateListener);
        valueAnimator.addListener(animatorListenerAdapter);
        corrDatas = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < corrDatas.size(); i++) {
            CorrdinateData cd = corrDatas.get(i);
            canvas.drawLine(cd.p1X, cd.p1Y, cd.p2X, cd.p2Y, paint);
        }
    }

    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            calculate();
            invalidate();
        }
    };

    private AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            if (null != drawingListener)
                drawingListener.drawStart();
            corrDatas.clear();
            super.onAnimationStart(animation);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (null != drawingListener)
                drawingListener.drawOver();
            super.onAnimationEnd(animation);
        }
    };

    /**
     * 开始绘制
     */
    public void startDraw(int pos) {
        corrDatas.clear();
        setPaint(pos);
        valueAnimator.start();
    }

    /**
     * 计算坐标值
     */
    private void calculate() {
        angleP1 = angleP1 + speedP1;
        angleP2 = angleP2 + speedP2;

        //两个点的位置更新
        float nowP1X = (float) (p1XLength * Math.cos(angleP1) + viewWidth / 2f);
        float nowP1Y = (float) (p1YLength * Math.sin(angleP1) + viewHeight / 2f);
        float nowP2X = (float) (p2XLength * Math.cos(angleP2) + viewWidth / 2f);
        float nowP2Y = (float) (p2YLength * Math.sin(angleP2) + viewHeight / 2f);

        CorrdinateData corrdinataData = new CorrdinateData(nowP1X, nowP1Y, nowP2X, nowP2Y);
        corrDatas.add(corrdinataData);
    }

    private class CorrdinateData {
        float p1X, p1Y, p2X, p2Y;

        CorrdinateData(float p1X, float p1Y, float p2X, float p2Y) {
            this.p1X = p1X;
            this.p1Y = p1Y;
            this.p2X = p2X;
            this.p2Y = p2Y;
        }
    }

    /**
     * 设置参数
     */
    public void setParam(float p1XLength, float p1YLength, float p2XLength, float p2YLength, float speedP1, float speedP2) {
        this.p1XLength = p1XLength;
        this.p1YLength = p1YLength;
        this.p2XLength = p2XLength;
        this.p2YLength = p2YLength;
        this.speedP1 = speedP1;
        this.speedP2 = speedP2;
    }

    private void setPaint(int pos){
        if(paint!=null){
            switch (pos){
                case 0:
                    paint.setColor(Color.WHITE);
                    break;
                case 1:
                    paint.setColor(getResources().getColor(R.color.purple));
                    break;
                case 2:
                    paint.setColor(Color.BLUE);
                    break;
                case 3:
                    paint.setColor(Color.GREEN);
                    break;
                case 4:
                    paint.setColor(getResources().getColor(R.color.orange));
                    break;
                case 5:
                    paint.setColor(Color.RED);
                    break;
                default:
                    paint.setColor(Color.WHITE);
                    break;

            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        p1XLength = p2YLength = viewWidth * 0.8f;
        p2XLength = p1YLength = viewWidth * 0.04f;
    }

    public void setDrawingListener(DrawingListener drawingListener) {
        this.drawingListener = drawingListener;
    }

    public interface DrawingListener {
        void drawStart();
        void drawOver();
    }
}
