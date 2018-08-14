package com.haddad.dhaker.radarchart2;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class RadarChart extends View {
    private int mLinesColor;
    private int mChart1BackgroundStarColor;
    private int mChart1BackgroundEndColor;
    private int mChart2BackgroundStarColor;
    private int mChart2BackgroundEndColor;
    private int mChart1DotsColor;
    private int mChart2DotsColor;
    private int mDotsRadius;
    private float mLinesStrokeWidth;
    private float mLineLAbelSize;
    private int mLineLabelColor;

    private static final int ANIMATION_DURATION = 300;
    private static final int DEFAULT_LINES_COLOR = Color.BLACK;
    private static final int DEFAULT_CHART1_BACKGROUND_START_COLOR = Color.GREEN;
    private static final int DEFAULT_CHART1_BACKGROUND_END_COLOR = Color.BLACK;
    private static final int DEFAULT_CHART2_BACKGROUND_START_COLOR = Color.BLUE;
    private static final int DEFAULT_CHART2_BACKGROUND_END_COLOR = Color.BLACK;
    private static final int DEFAULT_CHART1_DOTS_COLOR = Color.BLACK;
    private static final int DEFAULT_CHART2_DOTS_COLOR = Color.BLUE;
    private static final int DEFAULT_DOTS_RADIUS = 10;
    private static final float DEFAULT_STROKE_WIDTH = 2f;
    private static final float DEFAULT_LABEL_TEXT_SIZE = 30;
    private static final float LABEL_OFFSET = 20;
    private static final int DEFAULT_LABEL_TEXT_COLOR = Color.BLACK;

    private static float LINE_LENGTH ;

    //Axes Angles
    private static final int A = -45;
    private static final int B = -45 * 2;
    private static final int C = -45 * 3;
    private static final int D = 45 * 3;
    private static final int E = 45;
    private static final int[] mAngleArray = new int[]{A, B, C, D, E};

    private static final String ANGLE_A = "A";
    private static final String ANGLE_B = "B";
    private static final String ANGLE_C = "C";
    private static final String ANGLE_D = "D";
    private static final String ANGLE_E = "E";
    private static final String[] mAngleArrayLabels = new String[]{ANGLE_A, ANGLE_B, ANGLE_C, ANGLE_D, ANGLE_E};

    private int[] mChart1CharacteristicArray = new int[]{0, 0, 0, 0, 0};
    private int[] mChart2CharacteristicArray = new int[]{0, 0, 0, 0, 0};

    public RadarChart(Context context) {
        super(context);
        init(context, null);
    }

    public RadarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RadarChart);
        initAttributes(a);
        a.recycle();
    }

    private void initAttributes(TypedArray a) {
        mLinesColor = a.getColor(R.styleable.RadarChart_linesColor, DEFAULT_LINES_COLOR);
        mChart1BackgroundStarColor = a.getColor(R.styleable.RadarChart_chart1BackgroundStarColor, DEFAULT_CHART1_BACKGROUND_START_COLOR);
        mChart1BackgroundEndColor = a.getColor(R.styleable.RadarChart_chart1backgroundEndColor, DEFAULT_CHART1_BACKGROUND_END_COLOR);
        mChart2BackgroundStarColor = a.getColor(R.styleable.RadarChart_chart2BackgroundStarColor, DEFAULT_CHART2_BACKGROUND_START_COLOR);
        mChart2BackgroundEndColor = a.getColor(R.styleable.RadarChart_chart2backgroundEndColor, DEFAULT_CHART2_BACKGROUND_END_COLOR);
        mChart1DotsColor = a.getColor(R.styleable.RadarChart_chart1DotsColor, DEFAULT_CHART1_DOTS_COLOR);
        mChart2DotsColor = a.getColor(R.styleable.RadarChart_chart2DotsColor, DEFAULT_CHART2_DOTS_COLOR);
        mDotsRadius = a.getInteger(R.styleable.RadarChart_dotsRadius, DEFAULT_DOTS_RADIUS);
        mLinesStrokeWidth = a.getFloat(R.styleable.RadarChart_linesStroke, DEFAULT_STROKE_WIDTH);
        mLineLAbelSize = a.getDimension(R.styleable.RadarChart_labelTextSize, DEFAULT_LABEL_TEXT_SIZE);
        mLineLabelColor = a.getColor(R.styleable.RadarChart_labelTextColor, DEFAULT_LABEL_TEXT_COLOR);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int mMinDimension = Math.min(mWidth, mHeight);
        LINE_LENGTH = mMinDimension / 2 - 20;
        setMeasuredDimension(mMinDimension, mMinDimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLines(canvas);
        drawShape(canvas, mChart1CharacteristicArray, mChart1BackgroundStarColor, mChart1BackgroundEndColor, mChart1DotsColor);
        drawShape(canvas, mChart2CharacteristicArray, mChart2BackgroundStarColor, mChart2BackgroundEndColor, mChart2DotsColor);
    }

    private void drawDot(Canvas canvas, float x, float y, int dotColor) {
        Paint dotsPaint = new Paint();
        dotsPaint.setAntiAlias(true);
        dotsPaint.setColor(dotColor);
        dotsPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawCircle(x, y,mDotsRadius, dotsPaint);
    }

    private void drawLineLabel(Canvas canvas, String labelText, float x, float y) {
        Paint labelPaint = new Paint();
        labelPaint.setAntiAlias(true);
        labelPaint.setTextSize(mLineLAbelSize);
        labelPaint.setColor(mLineLabelColor);
        labelPaint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(labelText, x, y, labelPaint);
    }

    private void drawShape(Canvas canvas, int[] valuesArray, int startColor, int endColor, int dotsColor) {
        Paint polygonPaint = new Paint();
        polygonPaint.setAntiAlias(true);
        Rect rect = canvas.getClipBounds();
        LinearGradient gradient = new LinearGradient(rect.top, rect.left, rect.bottom, rect.right, startColor, endColor, Shader.TileMode.CLAMP);
        polygonPaint.setShader(gradient);

        Path polygonPath = new Path();
        polygonPath.reset();
        float startX = getWidth() / 2;
        float startY = getHeight() / 2;

        for(int i = 0; i< valuesArray.length; i++) {
            float lineLength;
            if (mAngleArray[i] == B) {
                lineLength = (float) (LINE_LENGTH * Math.cos(Math.toRadians(mAngleArray[i] / 2)));
            } else {
                lineLength = LINE_LENGTH;
            }
            int currentCharacteristic = (int) ((valuesArray[i] * lineLength) / 100);
            int currentAngle = mAngleArray[i];
            float x = getEndX(startX, currentCharacteristic, currentAngle);
            float y = getEndY(startY, currentCharacteristic, currentAngle);
            if (i > 0) {
                polygonPath.lineTo(x, y);
            } else {
                polygonPath.moveTo(x, y);
            }
            drawDot(canvas, x, y, dotsColor);
        }

        canvas.drawPath(polygonPath, polygonPaint);
    }

    private void drawLines(Canvas canvas) {
        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(mLinesColor);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setStrokeWidth(mLinesStrokeWidth);
        //starting point for all lines
        float startX = getWidth() / 2;
        float startY = getHeight() / 2;

        for (int i = 0; i< mAngleArrayLabels.length; i++) {
            drawLineWithAngle(startX, startY, mAngleArray[i], mAngleArrayLabels[i], linePaint, canvas);
        }
    }

    private void drawLineWithAngle(float startX, float startY, int angle, String label, Paint linePaint, Canvas canvas) {
        float lineLength;
        if (angle == B) {
            lineLength = (float) (LINE_LENGTH * Math.cos(Math.toRadians(angle / 2)));
        } else {
            lineLength = LINE_LENGTH;
        }
        float endX = getEndX(startX, lineLength, angle);
        float endY = getEndY(startY, lineLength, angle);
        canvas.drawLine(startX, startY, endX, endY, linePaint);
        switch (label) {
            case ANGLE_A:
                endX = endX + LABEL_OFFSET;
                endY = endY - LABEL_OFFSET;
                break;
            case ANGLE_C:
                endX = endX - LABEL_OFFSET;
                endY = endY - LABEL_OFFSET;
                break;
            case ANGLE_B:
                endY = endY - LABEL_OFFSET;
                break;
            case ANGLE_D:
                endX = endX - LABEL_OFFSET;
                endY = endY + LABEL_OFFSET;
                break;
            case ANGLE_E:
                endX = endX + LABEL_OFFSET;
                endY = endY + LABEL_OFFSET;
                break;

        }
        drawLineLabel(canvas, label, endX, endY);

    }

    private float getEndY(float startY, float lineLength, int angle) {
        return (float) (startY + lineLength * Math.sin(Math.toRadians(angle)));
    }

    private float getEndX(float startX, float lineLength, int angle) {
        return (float) (startX + lineLength * Math.cos(Math.toRadians(angle)));
    }

    public void setChart1CharacteristicArray(int[] characteristicArray) {
        animateValues(characteristicArray, mChart1CharacteristicArray);
    }

    private void animateValues(final int[] characteristicArray, final int[] currentChartCharacteristicArray) {
        ValueAnimator valueAnimator = getValueAnimator();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentAnimationValue = (int) animation.getAnimatedValue();

                for(int i=0; i< characteristicArray.length; i++) {
                    if (currentAnimationValue <= characteristicArray[i]) {
                        currentChartCharacteristicArray[i]  = currentAnimationValue;
                    }
                    invalidate();
                }
            }
        });
        valueAnimator.start();

    }

    public void setChart2CharacteristicArray(int[] characteristicArray) {
        animateValues(characteristicArray, mChart2CharacteristicArray);
    }

    private ValueAnimator getValueAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setDuration(ANIMATION_DURATION);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        return valueAnimator;
    }

    public void setLinesColor(int mLinesColor) {
        this.mLinesColor = mLinesColor;
    }
}
