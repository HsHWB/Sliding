package com.example.slidingmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by Administrator on 2015/3/11.
 */
public class ContentView extends LinearLayout {

    private int state = 0;
    private Context context;
    private boolean shouldIntercept = false;
    private MySlidingMenu mySlidingMenu;

    public ContentView(Context context, AttributeSet attrs){

        super(context, attrs);
        this.context = context;
    }

    /**
     * 可以这样理解。每一个触屏事件都必须是以ACTION_DOWN作为开头，后面跟一系列的ACTION_MOVE，
     * 最后再有一个ACTION_UP（或ACTION_CANCEL），标识触屏事件结束。所以Android就在ACTION_DOWN的时候做文章，
     * 官方文档对dispatchTouchEvent的返回值的解释是：True if the event was handled by the view, false otherwise。
     * 我们可以简单的理解为如果返回true，就说明它需要处理这个事件，就让它接收所有的触屏事件，否则，说明它不用处理，
     * 也就不让它接收后续的触屏事件了。
     * @param ev
     * @return
     */



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        this.shouldIntercept = MySlidingMenu.shouldIntercept;
//        System.out.println("action id == "+action);
//        System.out.println("MotionEvent.ACTION_UP == "+MotionEvent.ACTION_UP);
//        System.out.println("MotionEvent.ACTION_DOWN == "+MotionEvent.ACTION_DOWN);
        switch (action){
            case MotionEvent.ACTION_UP:
                state = 1;
            case MotionEvent.ACTION_DOWN:
                state = 1;
//            case MotionEvent.ACTION_MOVE:
//                System.out.println("move == "+(++move));


        }
//        if (shouldIntercept){
////            this.mySlidingMenu = (MySlidingMenu)getParent();
////            System.out.println("(MySlidingMenu)getParent() == "+getParent().);
////            mySlidingMenu.shouldClose();
//            return true;
//        }
        return false;
    }

    public boolean getClick(){

        if (state == 1){
            this.state = 0;
            return true;
        }else{
            return false;
        }

    }
}
