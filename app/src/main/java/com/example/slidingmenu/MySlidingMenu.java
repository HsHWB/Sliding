package com.example.slidingmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.activity.MainActivity;
import com.example.constant.Normal;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Hs on 2015/3/9.
 */
public class MySlidingMenu extends HorizontalScrollView {

    private int menuWidth;
    private int menuRightPadding = 100;
    private int halfMenuWidth;
    private int screenWidth;
    private Context context;
    private DisplayMetrics dm;
    private WindowManager wm;
    private ViewGroup menu;
    private ContentView content;
    private ListView contentListView;
    public static boolean shouldIntercept = false;
    private boolean isListView = false;
    private boolean once;

    public MySlidingMenu(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        dm = new DisplayMetrics();
        wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!once){//如果没有隐藏
            LinearLayout ll = (LinearLayout)getChildAt(0);//parent布局的第1个子布局
            menu = (ViewGroup)ll.getChildAt(0);//获取ll布局的第一个子view
            content = (ContentView)ll.getChildAt(1);//ll布局的第二个子view
            this.contentListView = (ListView)content.findViewById(R.id.content_listview);

            /**
             *  dp转成px
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
            System.out.println("change");
            this.scrollTo(menuWidth, 0);
            shouldIntercept = false;
            once = true;
        }
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev){
//
//        View v = getRootView().findFocus();
//        System.out.println("v == "+v);
//        if ( (v instanceof ContentView || v instanceof ListView) && shouldIntercept){
//            isListView = true;
//            return true;
//        }
//        return super.dispatchTouchEvent(ev);
//    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP://UP时，用户（轻触触摸屏后）松开，或者按下屏幕快速移动后松开，判断，如果显示区域大于菜单宽度的一半，则隐藏
                int scrollX = getScrollX();//在content全屏的情况下，X无论点哪里都是最大值，当侧滑结束的情况下，无论点哪里都是0，在滑动过程中X动态变化
//                System.out.println("content.getClick() == "+content.getClick());
//                if(content.getClick() && scrollX == 0){
//                    System.out.println("in");
//                    super.smoothScrollTo(menuWidth, 0);
//                    shouldIntercept = false;
//                    return true;
//                }
                if (isListView){
                    super.smoothScrollTo(menuWidth, 0);
                    isListView = false;
                    return true;
                }

                if(scrollX > halfMenuWidth*1.5){
                    shouldIntercept = false;
                    super.smoothScrollTo(menuWidth, 0);
                }
                else {
                    shouldIntercept = true;
                    super.smoothScrollTo(0, 0);
                }
                isListView = false;
                return true;

        }
        return super.onTouchEvent(ev);
    }

    public void shouldOpen(){
        this.smoothScrollTo(0, 0);
    }

    public void shouldClose(){
        smoothScrollTo(menuWidth, 0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt){

        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / menuWidth;//1、如果content从左向右即显示侧栏滑动的话，menuWidth会越来越小，scale会越来越大
        float leftScale = 1 - 0.3f * scale;
        float rightScale = 0.8f + scale * 0.2f;

        menu.setScaleX(leftScale);
        menu.setScaleY(leftScale);
        menu.setAlpha(0.4f + 0.4f * (1 - scale));//2、若scale越来越大，则表达式越来越接近 0.6 ，透明度越来越小
        menu.setTranslationX(menuWidth * scale * 0.6f);

        content.setPivotX(0);
        content.setPivotY(content.getHeight() / 2);
        content.setScaleX(rightScale);
        content.setScaleY(rightScale);

    }


}
