package mr_immortalz.com.stereoview;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import mr_immortalz.com.stereoview.custom.MyImageView;
import mr_immortalz.com.stereoview.custom.OnDoubleClickListener;
import mr_immortalz.com.stereoview.custom.StereoView;

import android.util.Log;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.provider.MediaStore;
import android.widget.Toast;
import android.widget.RelativeLayout;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import android.graphics.Matrix;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.PixelFormat;

import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {
    Bitmap bitmap;
    private String imageStoragePath = Environment.getExternalStorageDirectory() + "/take_photo/";
    private ArrayList<MyImageView> imgArray = new ArrayList<MyImageView>();
    private ArrayList<Uri> imagePathArray = getAllImage(imageStoragePath);
    private Uri mCutUri;
     private ImageView img;
    private MyImageView imageView;
    private String TAG = "ImageActivity";
    private static final int REQUEST_CROP = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //布局管理器添加到StereoView中
        setContentView(R.layout.activity_image);
        img = new ImageView(ImageActivity.this);
        //通过id查找View
        StereoView stereoView = (StereoView) findViewById(R.id.stereoView);
        if (imagePathArray.size() == 0) {
            Toast.makeText(this, "请先导入图片再展示", Toast.LENGTH_SHORT).show();
            return;
        }
        for (final Uri uriPath:imagePathArray) {
            //new ImageView对象
            imgArray.add(new MyImageView(ImageActivity.this));
            int indexOfLast =imgArray.size() - 1;
            final MyImageView myImageView = imgArray.get(indexOfLast);
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriPath);
            } catch(IOException ioEx) {
                ioEx.printStackTrace();
            }
            myImageView.setImageBitmap(ImageActivity.this, bitmap);
            stereoView.addView(myImageView.getImageView());
//            myImageView.setLongClick(new Handler(), myImageView.getImageView(), 6000, new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    //todo:补充长按事件的处理逻辑
//                    Log.e(TAG, "print onLongClick");
//                    cropPhoto(uriPath);
//                    return true;
//                }
//            });
            try {
            myImageView.getImageView().setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback(){
                @Override
                public void onDoubleClick() {
                    // todo:补充长按事件的处理逻辑
                    Log.e(TAG, "print onLongClick");
                    // cropPhoto(uriPath);
                }
            }));

        } catch (Exception Ex) {
                Ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 裁剪后设置图片
                case REQUEST_CROP:
                    img.setImageURI(mCutUri);
                    Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + mCutUri.toString());
                    break;
                default:
                    break;
            }
        }
    }

    private static Uri getUriForFile( File file) {
        return Uri.fromFile(file);
    }

    private  ArrayList<Uri>  getAllImage(String directory) {
        ArrayList<Uri> imageUriArray = new ArrayList<>();
        File file = new File(directory);    //获取其file对象
        File[] fs = file.listFiles();	    //遍历path下的文件和目录，放在File数组中
        if (fs == null) {
            return new ArrayList<>();
        }
        for(File f:fs){					    //遍历File[]数组
            if(!f.isDirectory()) {           //若非目录(即文件)，则放到imageUriArray
                imageUriArray.add(getUriForFile(f));
                System.out.println(getUriForFile(f));
            }
        }
        return imageUriArray;
    }

    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP"); //打开系统自带的裁剪图片的intent
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");

        // 注意一定要添加该项权限，否则会提示无法裁剪
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.putExtra("scale", true);

        // 设置裁剪区域的宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // 设置裁剪区域的宽度和高度
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);

        // 取消人脸识别
        intent.putExtra("noFaceDetection", true);
        // 图片输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        // 若为false则表示不返回数据
        intent.putExtra("return-data", false);

        // 指定裁剪完成以后的图片所保存的位置,pic info显示有延时
        // 从相册中选择，那么裁剪的图片保存在take_photo中
            String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
            String fileName = "photo_" + time;
            File mCutFile = new File(Environment.getExternalStorageDirectory() + "/take_photo/", fileName + ".jpeg");
            if (!mCutFile.getParentFile().exists()) {
                mCutFile.getParentFile().mkdirs();
            }
            mCutUri = Uri.fromFile(mCutFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCutUri);
        Toast.makeText(this, "剪裁图片", Toast.LENGTH_SHORT).show();
        // 以广播方式刷新系统相册，以便能够在相册中找到刚刚所拍摄和裁剪的照片
//        Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        intentBc.setData(uri);
//        this.sendBroadcast(intentBc);
//        startActivity(intent);
        img.setImageURI(uri);
        //  startActivityForResult(intent, REQUEST_CROP); //设置裁剪参数显示图片至ImageVie //设置裁剪参数显示图片至ImageVie
//        try {
//            FileOutputStream out = new FileOutputStream(mCutFile);
//            boolean ret = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            if(ret == false)
//                Log.e(TAG,"onActivityResult: CAN_OPEN_PHONE: bitmap.compress return false");
//            out.flush();
//            out.close();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}

