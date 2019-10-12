package com.lyg.tools.joke;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.erning.common.utils.FileUtil;
import com.erning.common.utils.LogUtils;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.lyg.tools.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;


/**
 * 看图页面
 */
@SuppressWarnings("FieldCanBeLocal")
public class ImageActivity extends AppCompatActivity {
    public static final String TAG = "ImageActivity";
    public static final int PERMISSIONS_REQUEST_CODE = 111;

    private LinearLayout linear_image;
    private ViewPager viewPager_image;
    private TextView text_image;
    private String[] imgs;
    private String text;
    private List<View> viewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
//        QMUIStatusBarHelper.translucent(this);
        viewPager_image = findViewById(R.id.viewPager_image);
        linear_image = findViewById(R.id.linear_image);
        text_image = findViewById(R.id.text_image);

        imgs = getIntent().getStringArrayExtra("imgs");
        text = getIntent().getStringExtra("text");
        int index = getIntent().getIntExtra("index", 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,},PERMISSIONS_REQUEST_CODE);
        }else{
            initPath();
        }

        if (text==null || text.isEmpty()){
            ViewGroup.LayoutParams params = text_image.getLayoutParams();
            params.height = 0;
            text_image.setLayoutParams(params);
        }else{
            text_image.setText(text);
        }
        for (final String item : imgs){
            View view = getLayoutInflater().inflate(R.layout.item_image,null,false);

            final PhotoDraweeView img_image = view.findViewById(R.id.img_image);

            if (item.endsWith(".gif") || item.endsWith(".GIF")){
                PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
                controller.setUri(item);//设置图片url
                controller.setAutoPlayAnimations(true);
                controller.setOldController(img_image.getController());
                controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (imageInfo != null) {
                            img_image.update(imageInfo.getWidth(), imageInfo.getHeight());
                        }
                    }
                });
                img_image.setController(controller.build());
            }else{
                img_image.setPhotoUri(Uri.parse(item));
            }

            //点击返回
            img_image.setOnViewTapListener(new OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    finish();
                }
            });

            img_image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (item.endsWith(".gif") || item.endsWith(".GIF")) {
                        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Tools/saveImage/");
                        if(!file.exists()){
                            file.mkdirs();
                        }
                        final String path = Environment.getExternalStorageDirectory().getPath()+"/Tools/saveImage/"+System.currentTimeMillis()+".gif";

                        boolean save = saveGifFromMainFileCache(item, path);
                        if (!save)
                            explanationPermission();
                        else
                            Toast.makeText(getApplicationContext(), "保存到" + path, Toast.LENGTH_LONG).show();
                    }
                    else
                        downLoadImg(item);
                    return true;
                }
            });
            viewList.add(view);
        }
        viewPager_image.setAdapter(new PageAdapter());

        if (imgs.length>1) {
            viewPager_image.addOnPageChangeListener(new ViewPagerIndicator(this, linear_image, imgs.length));
        }else{
            ViewGroup.LayoutParams params = linear_image.getLayoutParams();
            params.height = 0;
            linear_image.setLayoutParams(params);
        }
        viewPager_image.setCurrentItem(index);
    }

    class PageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initPath(){
        try {
            File file = new File(Environment.getExternalStorageDirectory().getPath()+"/QBK/");
            if (!file.exists())
                file.mkdir();
            file = new File(Environment.getExternalStorageDirectory().getPath()+"/QBK/saveImage/");
            if (!file.exists())
                file.mkdir();
        }catch (Exception e){
            LogUtils.e(TAG,"残忍的人类");
        }

    }

    /**
     * 申请权限
     */
    private void requestPermission(final String[] permissions , final int PERMISSIONS_CODE){
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(this, permissions[0])!= PackageManager.PERMISSION_GRANTED) {
            LogUtils.d(TAG,"还没有权限");
            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[0])) {
                LogUtils.d(TAG,"曾今被拒绝过");
                explanationPermission();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(ImageActivity.this,permissions,PERMISSIONS_CODE);
            }
        }
        else{
            LogUtils.d(TAG,"以前就授权了");
            initPath();
        }
    }

    /**
     * 获取应用详情页面intent
     */
    @SuppressLint("ObsoleteSdkInt")
    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", this.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.FROYO) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", this.getPackageName());
        }
        return localIntent;
    }

    /**
     * 授权结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],@NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LogUtils.d(TAG,"有权限了");
                    initPath();
                } else {
                    LogUtils.d(TAG,"没有权限");
                    explanationPermission();
                }
                break;
            }
        }
    }

    /**
     * 向用户解释权限
     */
    private void explanationPermission(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ImageActivity.this);
                builder.setTitle("没有权限");
                builder.setMessage("没有权限无法保存图片");
                builder.setPositiveButton("取消",null);
                builder.setNegativeButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(getAppDetailSettingIntent());
                    }
                });
                builder.show();
            }
        });
    }

    private void downLoadImg(final String uri) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri)).setProgressiveRenderingEnabled(true).build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, this);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(@Nullable Bitmap bitmap) {
                //bitmap即为下载所得图片
                String hzm = ".png";
                String[] hzms = uri.split("\\.");
                if (hzms.length > 0){
                    hzm = "." + hzms[hzms.length - 1];
                }
                File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Tools/saveImage/");
                if(!file.exists()){
                    file.mkdirs();
                }
                final String path = Environment.getExternalStorageDirectory().getPath()+"/Tools/saveImage/"+System.currentTimeMillis()+hzm;
                if (!FileUtil.saveBitmap(bitmap,path)) {
                    explanationPermission();
                    return;
                }
                //toast必须在主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "图片已保存到" + path, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {

            }
        }, CallerThreadExecutor.getInstance());

        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                .setFadeDuration(300)
//                .setPlaceholderImage(defaultDrawable)
//                .setFailureImage(defaultDrawable)
                .setProgressBarImage(new ProgressBarDrawable())
                .build();
        DraweeHolder<GenericDraweeHierarchy> draweeHolder = DraweeHolder.create(hierarchy, this);

        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(draweeHolder.getController())
                .setImageRequest(imageRequest)
                .build();
        controller.onClick();
    }

    private boolean saveGifFromMainFileCache(String url, String localSavePath) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setProgressiveRenderingEnabled(true).build();
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(imageRequest, this);
        if (ImagePipelineFactory.getInstance().getMainFileCache().hasKey(cacheKey)) {
            BinaryResource resource = ImagePipelineFactory.getInstance().getMainFileCache().getResource(cacheKey);
            File cacheFile = ((FileBinaryResource) resource).getFile();
//          ImageFormat imageFormat = ImageFormatChecker.getImageFormat(new FileInputStream(cacheFile));
            return FileUtil.copyFile(cacheFile, localSavePath);//把缓存文件复制到要保存的位置上  返回保存是否成功
        } else {
            //保存失败处理
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        try{
            fileScan(Environment.getExternalStorageDirectory().getPath()+"/QBK/saveImage/");
        }catch (Exception e){
            LogUtils.e(TAG,"没有权限");
        }
        super.onDestroy();
    }

    /**
     * 通知手机扫描媒体文件
     * @param file 要扫描的文件夹
     */
    public void fileScan(String file){
        Uri data = Uri.parse("file://" + file);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }
}