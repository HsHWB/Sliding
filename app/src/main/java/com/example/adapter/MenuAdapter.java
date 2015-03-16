package com.example.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.slidingmenu.R;

/**
 * Created by Administrator on 2015/3/12.
 */
public class MenuAdapter extends BaseAdapter {

    private Context context;
    private int screenHeight;
    private int screenWidth;

    public MenuAdapter(Context context, int screenHeight, int screenWidth){
        super();
        this.context = context;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout ll = new LinearLayout(context);//item的大布局
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(3*screenWidth/5, screenHeight/10);
        ll.setLayoutParams(params);

        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(3*screenWidth/5,
                ViewGroup.LayoutParams.WRAP_CONTENT);//tv的布局
        TextView tv = new TextView(context);
        tv.setText(String.format("the number %d item",position));
        tv.setTextSize(16);
        tv.setTextColor(context.getResources().getColor(R.color.white));
        tvParams.setMargins(30,0,0,0);

        ll.addView(tv, tvParams);

        return ll;
    }
}
