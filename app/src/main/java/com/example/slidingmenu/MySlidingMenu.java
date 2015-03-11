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
    private int menuRightPadding = 100;
    private int halfMenuWidth;
    private int screenWidth;
    private int screenHeight;
    private Context context;
    private DisplayMetrics dm;
    private WindowManager wm;
    private ViewGroup menu;
//    private ViewGroup content;
    private ContentView content;
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
            menu = (ViewGroup)ll.getChildAt(0);//获取ll布局的第一个子view
//            content = (ViewGroup)ll.getChildAt(1);//ll布局的第二个子view
            content = (ContentView)ll.getChildAt(1);

            /*
             *dp转成px
             */

            menuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    menuRightPadding, content.getResources().getDisplayMetrics());

            //获得菜单的宽度和一半的宽度，使menu宽度为菜单宽度，content宽度为屏幕宽度
            //屏幕宽度减去menuRightPadding = 100，得到了menu出现的时候应该显示的宽度，如此一来，menu布局的左边100dp是被遮住的。
            //真正变化在onLayout中，此处只是赋值,在layout中，this对象向左移动了menuWidth的宽度，则content布局显示在整个屏幕
            //否则如果没有this.scrollTo(menuWidth, 0);显示应该处于出现了侧滑菜单，content只看到100dp的情况
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
            case MotionEvent.ACTION_UP://UP时，用户（轻触触摸屏后）松开，或者按下屏幕快速移动后松开，判断，如果显示区域大于菜单宽度的一半，则隐藏
                int scrollX = getScrollX();//在content全屏的情况下，X无论点哪里都是最大值，当侧滑结束的情况下，无论点哪里都是0，在滑动过程中X动态变化
                System.out.println("scroollX =="+scrollX);
                if(content.getClick() && scrollX == 0){
                    System.out.println("MySlidingMenu is visible andr content is clicked");
                    this.smoothScrollTo(menuWidth, 0);
                    return true;
                }
                if(scrollX > halfMenuWidth)
                    this.smoothScrollTo(menuWidth, 0);
                else
                    this.smoothScrollTo(0,0);
                return true;
        }
        return super.onTouchEvent(ev);
    }
}
