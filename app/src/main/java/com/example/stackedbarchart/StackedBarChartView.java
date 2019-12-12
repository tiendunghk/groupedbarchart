package com.example.stackedbarchart;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StackedBarChartView extends View {

    private Paint p;
    private int soNhom, soBar;
    private Map<String, float[]> data;
    private ArrayList<Animator> animators;
    private AnimatorSet animSet;
    private int ii = 0;
    private ArrayList<Float> arr = new ArrayList<Float>();
    private int cl[];

    public StackedBarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        data = null;
        p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.RED);
        p.setStrokeWidth(1.5f);
        animators = new ArrayList<Animator>();
        animSet = new AnimatorSet();

    }

    public Point getOrigin() {
        Point p = new Point();
        p.x = 50;
        p.y = 50;
        return p;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Point orgin = new Point(100, 700);
        canvas.drawLine(orgin.x, orgin.y, orgin.x, orgin.y - 500, p);
        canvas.drawLine(orgin.x, orgin.y, orgin.x + 500, orgin.y, p);
        Double d = (Double) 500.0 / 9;
        int key = orgin.x;




 //////////

        for (int i = 0; i < arr.size(); i+=soBar) {
            //System.out.println("phan tu thu " + i + " la: " + arr.get(i));
            RectF rect = new RectF();

            if(i%soBar==0)
            {

                int x=i/soBar;
                int start=i;
                int end=i+soBar-1;
                System.out.println("i la: "+i+" start :"+start+"  end: "+end);
                for(int j=end;j>=start;j--)
                {
                    System.out.println("j la: "+j);
                    rect.left = key + (i + 1) * 20;
                    rect.top = (float) (700.0 - arr.get(j) * d);
                    rect.right = rect.left + 20;
                    rect.bottom = orgin.y;
                    p.setColor(cl[j%soBar]);
                    canvas.drawRect(rect, p);
                }
            }



        }
    }
    private int getRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;
    }

    public float xuLy(float[] f,int i){
        if(i==0)
            return f[0];
        return f[i]+xuLy(f,i-1);
    }
    public void update(Map<String, float[]> da, int soNhom, int soBar) {
        data = da;
        this.soNhom = soNhom;
        this.soBar = soBar;
        if (data == null) return;
        else {
            animSet.cancel();
            animators.clear();
            arr.clear();

            //items.clear();
            float[] d1 = data.get("2000");
            float[] d2 = data.get("2005");
            float[] d3 = data.get("2010");
            System.out.println("lol dkm : "+xuLy(d1,1));
            for(int i=d1.length-1;i>0;i--)
            d1[i]=xuLy(d1,i);
            for(int i=d2.length-1;i>0;i--)
            d2[i]=xuLy(d2,i);
            for(int i=d3.length-1;i>0;i--)
            d3[i]=xuLy(d3,i);

            for (int i = 0; i < d1.length; i++)
                arr.add(d1[i]);
            for (int i = 0; i < d2.length; i++)
                arr.add(d2[i]);
            for (int i = 0; i < d3.length; i++)
                arr.add(d3[i]);
        }

        System.out.println("hahahah "+arr.toString());

        cl=new int[soBar];
        for(int j=0;j<cl.length;j++)
            cl[j]=getRandomColor();


        ArrayList<Float> arr22 = new ArrayList<Float>();
        arr22=(ArrayList<Float>)arr.clone();

        for (int i = 0; i < arr.size(); i++) {
            final int iii=i;
            ValueAnimator anim = ValueAnimator.ofFloat(0, arr.get(iii));
            anim.setDuration(1000);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    arr.set(iii,(float)valueAnimator.getAnimatedValue());
                    invalidate();
                }
            });
            animators.add(anim);

        }
        animSet.playTogether(animators);
        animSet.start();

    }
}