package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.adapter.ContentAdapter;
import com.example.adapter.MenuAdapter;
import com.example.constant.Normal;
import com.example.slidingmenu.ContentView;
import com.example.slidingmenu.MySlidingMenu;
import com.example.slidingmenu.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.LogRecord;


public class MainActivity extends Activity {

    private int screenHeight = 0;
    private int screenWidth = 0;
    private DisplayMetrics dm;
    private MySlidingMenu slidingMenu;
    private LinearLayout menuLinear, contentTitle, contentBottom;
    private ContentView contentLinear;
    private ListView menuListView, contentListView;
    private MenuAdapter menuAdapter;
    private ContentAdapter contentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);

        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;

        slidingMenu = (MySlidingMenu)this.findViewById(R.id.slidingmenu);
        menuLinear = (LinearLayout)this.findViewById(R.id.menu_linear);
        contentLinear = (ContentView)this.findViewById(R.id.main_linear);
        menuListView = (ListView)this.findViewById(R.id.menu_listview);
        contentListView = (ListView)this.findViewById(R.id.content_listview);
        contentTitle = (LinearLayout)this.findViewById(R.id.content_title_linear);
        contentBottom = (LinearLayout)this.findViewById(R.id.content_bottom_button);

        menuAdapter = new MenuAdapter(this, screenHeight, screenWidth);
        menuListView.setAdapter(menuAdapter);

        ContentView.LayoutParams titleParams = new ContentView.LayoutParams(screenWidth, screenHeight/10);
        contentTitle.setLayoutParams(titleParams);

        ContentView.LayoutParams listParams = new ContentView.LayoutParams(screenWidth, 4*screenHeight/5);
        contentListView.setLayoutParams(listParams);
        contentAdapter = new ContentAdapter(this, screenHeight, screenWidth);
        contentListView.setAdapter(contentAdapter);

        ContentView.LayoutParams bottomParams = new ContentView.LayoutParams(screenWidth, screenHeight/10);
        contentBottom.setLayoutParams(bottomParams);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
