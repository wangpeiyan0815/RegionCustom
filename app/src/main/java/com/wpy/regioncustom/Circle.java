package com.wpy.regioncustom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by dell on 2017/3/5.
 */

public class Circle extends View {
    //定义画笔
    private Paint circlePaint;
    private Paint rectPaint;
    private Paint smallPaint;
    private Paint textPaint;
    //定义Region
    private Region circleRegion;
    private Region rectRegion;
    private Region smallRegion;
    //定义Path
    private Path circlePaht;
    private Path rectPaht;
    private Path smallPaht;

    private int width;
    private int height;

    public Circle(Context context) {
        super(context);
    }

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);
        intiView();
    }

    //初始化操作
    private void intiView() {
        circlePaint = new Paint();
        rectPaint = new Paint();
        smallPaint = new Paint();
        textPaint = new Paint();

        circleRegion = new Region();
        rectRegion = new Region();
        smallRegion = new Region();

        circlePaht = new Path();
        rectPaht = new Path();
        smallPaht = new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        //绘矩形
        rectPaht.addRect(w / 2 - 350, h / 2 - 350, w / 2 + 350, h / 2 + 350, Path.Direction.CW);
        // ▼将剪裁边界设置为视图大小
        Region globalRegion = new Region(-w, -h, w, h);
        // ▼将 Path 添加到 Region 中
        rectRegion.setPath(rectPaht, globalRegion);

        //绘中间圆
        circlePaht.addCircle(w / 2, h / 2, 350, Path.Direction.CW);
        // ▼将剪裁边界设置为视图大小
        Region circle_Region = new Region(-w, -h, w, h);
        // ▼将 Path 添加到 Region 中
        circleRegion.setPath(circlePaht, circle_Region);

        //绘小圆
        smallPaht.addCircle(w / 2, h / 2, 200, Path.Direction.CW);
        // ▼将剪裁边界设置为视图大小
        Region small_Region = new Region(-w, -h, w, h);
        // ▼将 Path 添加到 Region 中
        smallRegion.setPath(smallPaht, small_Region);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path rect_Path = rectPaht;
        rectPaint.setColor(Color.GREEN);
        // 绘制矩形
        canvas.drawPath(rect_Path, rectPaint);
        //绘圆
        Path circle_Path = circlePaht;
        circlePaint.setColor(Color.YELLOW);
        canvas.drawPath(circle_Path, circlePaint);
        //绘小圆
        Path small_Path = smallPaht;
        smallPaint.setColor(Color.WHITE);
        canvas.drawPath(small_Path, smallPaint);
        //绘文字
        textPaint.setTextSize(60);
        textPaint.setColor(Color.BLACK);
        float yuan = textPaint.measureText("圆环");
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        //文字高度
        float ceil = (float) Math.ceil(metrics.descent - metrics.ascent);
        canvas.drawText("圆环", width/2-yuan/2,height/2+ceil/2,textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if(smallRegion.contains(x,y)){
                    Toast.makeText(getContext(), "在小圆内", Toast.LENGTH_SHORT).show();
                }else if(circleRegion.contains(x,y)){
                    Toast.makeText(getContext(), "在圆环内", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "在圆环外", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }
}
