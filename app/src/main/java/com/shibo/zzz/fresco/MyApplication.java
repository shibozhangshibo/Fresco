package com.shibo.zzz.fresco;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;

/**
 * Created by zzz on 2016/9/7.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //基本的方法
        //Fresco.initialize(this);
        //渐进式JPEG图的方法
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .build();
        Fresco.initialize(this,config);
    }

}
