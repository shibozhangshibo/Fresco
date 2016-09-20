package com.shibo.zzz.fresco;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.my_image_view)
    private SimpleDraweeView draweeView;
    private String img_url0 = "http://gb.cri.cn/mmsource/images/2014/08/22/38/10202051462353342906.jpg";
    private String img_url1 = "http://heilongjiang.sinaimg.cn/2015/0326/U10061P1274DT20150326104659.jpg";
    private String img_url2 = "http://img4q.duitang.com/uploads/item/201411/20/20141120132318_3eAuc.thumb.700_0.jpg";
    private String img_url3 = "http://hiphotos.baidu.com/%CC%EC%C9%BD%B6%FE%CF%C0%B5%C4%D0%A1%CE%DD/pic/item/70c553e736d12f2e5b0614d64fc2d5628535682a.jpg";
    private String img_url4 = "http://img4.douban.com/view/photo/raw/public/p1773847919.jpg";
    private String img_url5 = "http://7mno4h.com2.z0.glb.qiniucdn.com/3859deb07d4647cf9183f8ea3f5aa165.jpg";//大图
    private String img_url6 = img_url5+"/test";//小图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewUtils.inject(this);
        Uri uri = Uri.parse(img_url0);

        draweeView.setImageURI(uri);
//        requestImage();
        moreImages();


    }
    private void requestImage(){
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(
                Uri.parse(img_url1))
                .setProgressiveRenderingEnabled(true)
                .build();
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setImageRequest(request)
//                .setOldController(draweeView.getController())
//                .build();
        PipelineDraweeController controller= (PipelineDraweeController) Fresco
                .newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .build();




draweeView.setController(controller);
    }


    private void moreImages(){
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                QualityInfo qualityInfo = imageInfo.getQualityInfo();
                FLog.d("Final image received! " +
                                "Size %d x %d",
                        "Quality level %d, good enough: %s, full quality: %s",
                        imageInfo.getWidth(),
                        imageInfo.getHeight(),
                        qualityInfo.getQuality(),
                        qualityInfo.isOfGoodEnoughQuality(),
                        qualityInfo.isOfFullQuality());
                Log.e("------------->","Final image received! " +
                                "Size :"+ imageInfo.getWidth()+
                        ",Quality :"+  qualityInfo.getQuality()+
                       " ,good enough: "+ qualityInfo.isOfGoodEnoughQuality()
                        +", full quality: "+  qualityInfo.isOfFullQuality());
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                FLog.d("","Intermediate image received");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                FLog.e(getClass(), throwable, "Error loading %s", id);
            }
        };




        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setLowResImageRequest(ImageRequest.fromUri(img_url6))
                .setImageRequest(ImageRequest.fromUri(img_url5))
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }
}
