package com.bawei.monththree.app;

import android.app.Application;

import com.bawei.monththree.BuildConfig;
import com.bawei.monththree.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;


/**
 * Created by Zhang on 2017/8/24.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoder();
        initxUtils();
    }
    private void initxUtils() {
        //初始化
        x.Ext.init(this);
        // 设置是否输出debug
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
    /**
     * 图片加载
      */
    private void initImageLoder() {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true)
                    .cacheInMemory(true)
                    .showImageForEmptyUri(R.mipmap.ic_launcher)
                    .showImageOnFail(R.mipmap.ic_launcher)
                    .showImageOnLoading(R.mipmap.ic_launcher)
                    .build();
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                    .defaultDisplayImageOptions(options)
                    .build();

            ImageLoader.getInstance().init(configuration);
        }
}
