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
import android.view.ViewTreeObserver;
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
    public static boolean shouldIntercept = false;//记录是否应该把listview触碰事件给截取下来
    private boolean isListView = false;//记录点击的是否是listview
    private boolean once;
    private boolean state = false;//记录侧滑状态，防止onTough的对应分发两个方法重复调用shouldclose。false
                                  // 为不显示侧滑

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

    /**
     *
     * @param ev
     * @return
     */

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){


//        if (isFocusOnListView(contentListView, ev) && shouldIntercept){
//
//            System.out.println("点击listview");
//            if(state) {
//                System.out.println("点击listview state");
//                isListView = true;
//            }
//            return true;
//        }

        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP://UP时，用户（轻触触摸屏后）松开，或者按下屏幕快速移动后松开，判断，如果显示区域大于菜单宽度的一半，则隐藏

                /**
                 * scrollX代表UP点而不是Down的按下点
                 */
                int scrollX = getScrollX();//在content全屏的情况下，X无论点哪里都是最大值，当侧滑结束的情况下，无论点哪里都是0，在滑动过程中X动态变化

//                if (isListView){
//                    state = false;//准备关掉侧滑栏,状态设为false
//                    isListView = false;
//                    shouldIntercept = false;
//                    super.smoothSc rollTo(menuWidth, 0);
//                    return true;
//                }

                if(scrollX > halfMenuWidth*1.5 && !state){//侧滑右拉的宽度达到一定程度(scrollX越大代表拉的宽度越小，反弹)，
                                                         // 而且 state = false 表示侧滑栏无显示
                    shouldIntercept = false;
                    state = false;
                    isListView = false;
                    super.smoothScrollTo(menuWidth, 0);
                    return true;
                }else if (scrollX <= halfMenuWidth*1.5 && !state){//右拉一定程度，显示侧边栏，state改为true
                    shouldIntercept = true;
                    state = true;
                    isListView = false;
                    super.smoothScrollTo(0, 0);
                    return true;
                }else if (scrollX <= halfMenuWidth/2 && state){//侧滑左移宽度小于一定程度，而且state表示侧滑栏有显示，则继续显示侧滑
                    shouldIntercept = true;
                    state = true;
                    super.smoothScrollTo(0, 0);
                    return true;
                }else if (scrollX > halfMenuWidth/2 && state){//侧滑左移宽度大于一定程度，而且侧滑栏有显示，则关闭侧滑栏
                    shouldIntercept = false;
                    state = false;
                    isListView = false;
                    super.smoothScrollTo(menuWidth, 0);
                    return true;
                }
//                super.smoothScrollTo(menuWidth, 0);
                return true;

        }
        return super.onTouchEvent(ev);
    }

    public void shouldOpen(){
        super.smoothScrollTo(0, 0);
    }

//    public void shouldClose(){
//        state = false;
//        super.smoothScrollTo(menuWidth, 0);
//    }


    /**
     * 完成坐标变化，还可以用setPolyToPoly
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
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

    /**
     * http://blog.csdn.net/centralperk/article/details/7949900
     *
     * 获取view尺寸还可以用这个：
     * View view1 = new View(context);
         view1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
         @Override
            public void onGlobalLayout() {
            //获取view尺寸
            }
            });
     *
     * @param view
     * @param ev
     * @return
     */
    private boolean isFocusOnListView(View view, MotionEvent ev){

        //相对于父布局的坐标
        int X = view.getLeft();
        int bottomY = view.getBottom();
        int topY = view.getTop();

        float evx = ev.getX();
        float evy = ev.getY();
//        System.out.println("x == "+X+"          bottom == "+bottomY+"            top == "+topY);
//        System.out.println("evx == "+evx+"          evy == "+evy);
        if (evx > X && evx <= view.getWidth() && evy < bottomY && evy >= topY){
            System.out.println("yes   withoutMenu");
            return true;
        }
        return  false;

    }

}
