package com.example.slidingmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by Hs on 2015/3/9.
 */
public class MySlidingMenu extends HorizontalScrollView {

    private int menuWidth;
    private int menuRightPadding = 50;
    private int halfMenuWidth;
    private int screenWidth;
    private int screenHeight;
    private Context context;
    private DisplayMetrics dm;
    private WindowManager wm;
    private boolean once;

    public MySlidingMenu(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        dm = new DisplayMetrics();
        wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;

//        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defS);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!once){//如果没有隐藏
            LinearLayout ll = (LinearLayout)getChildAt(0);//parent布局的第1个子布局
            ViewGroup menu = (ViewGroup)ll.getChildAt(0);//获取ll布局的第一个子view
            ViewGroup content = (ViewGroup)ll.getChildAt(1);//ll布局的第二个子view

            /*
             *dp转成px
             */

            menuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    menuRightPadding, content.getResources().getDisplayMetrics());
            menuWidth = screenWidth - menuRightPadding;
            halfMenuWidth = menuWidth/2;
            menu.getLayoutParams().width = menuWidth;
            content.getLayoutParams().width = screenWidth;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            //隐藏菜单
            this.scrollTo(menuWidth, 0);
            once = true;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP://UP时，判断，如果显示区域大于菜单宽度的一半，则隐藏
                int scrollX = getScrollX();
                if(scrollX > halfMenuWidth)
                    this.smoothScrollTo(menuWidth, 0);
                else
                    this.smoothScrollTo(0,0);
                return true;
        }
        return super.onTouchEvent(ev);
    }
}
