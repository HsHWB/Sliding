package com.example.until;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.slidingmenu.ContentView;

import java.io.File;

/**
 * Created by Hs on 2015/3/14.
 */
public class SDCardFile {

    private Context context;
    private File file;
    private String path;

    public SDCardFile(Context context, String path){
        this.context = context;
        this.path = path;

    }

    /**
     * 获取图片
     * @return
     */
    private Bitmap getImage(){
        return null;
    }

    /**
     * 获取文件的文字内容
     * @return
     */
    private StringBuffer getText(){
        return null;
    }

    /**
     * 获取音频
     */

    /**
     * 获取视频
     */
}
