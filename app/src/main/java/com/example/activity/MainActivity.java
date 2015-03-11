package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.constant.Normal;
import com.example.slidingmenu.ContentView;
import com.example.slidingmenu.MySlidingMenu;
import com.example.slidingmenu.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.LogRecord;


public class MainActivity extends Activity {

    private MySlidingMenu slidingMenu;
    private LinearLayout menuLinear;
    private ContentView contentLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);
        slidingMenu = (MySlidingMenu)this.findViewById(R.id.slidingmenu);
        menuLinear = (LinearLayout)this.findViewById(R.id.menu_linear);
        contentLinear = (ContentView)this.findViewById(R.id.main_linear);
    }

//    private Handler tweenHandler = slidingMenu.tweenHandler;

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
