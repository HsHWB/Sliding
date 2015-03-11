package com.example.slidingmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2015/3/11.
 */
public class ContentView extends LinearLayout {

    private Context context;
    private int state = 0;

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

        System.out.println("Enter in onInterceptTouchEvent");
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                System.out.println("Enter into Content ACTION_DOWN");
//                int scrollX = getScrollX();//本身不是ScrollView类型的，得到的都是0
//                System.out.println("Content.scrollX == "+scrollX);
                state = 1;
                System.out.println("Content is clicked");
                return true;
        }
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
