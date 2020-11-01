package com.example.orcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orcode.util.Constant;
import com.example.orcode.zxing.android.CaptureActivity;
import com.google.zxing.WriterException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QRcodeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SCAN_GALLERY = 100;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;

    private Button button1, button2,button3;
    private TextView textView;
    private CheckBox check;
    private EditText editText;
    private ImageView image;
    public static final int CHOOSE_PHOTO = 2;
    private Bitmap Bit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        button1 = (Button) findViewById(R.id.btnsao);
        button1.getBackground().setAlpha(120);
        button2 = (Button) findViewById(R.id.create);
        button2.getBackground().setAlpha(100);
        button3 = (Button)findViewById(R.id.create1);
        button3.getBackground().setAlpha(100);
        textView = (TextView) findViewById(R.id.text);
        editText = (EditText) findViewById(R.id.et_input);
        check = (CheckBox)findViewById(R.id.checkbox);
        image = (ImageView) findViewById(R.id.img);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               camera();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contentString = editText.getText().toString().trim();
                if (!contentString.equals("")) {
                    // 根据字符串生成条形码图片并显示在界面上，第二个参数为图片的大小（350*350）
                    Bit= QRCodeUtil.creatBarcode(contentString, 600, 300);
                        image.setImageBitmap(Bit);
                } else {
                    Toast.makeText(QRcodeActivity.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                             Bit = QRCodeUtil.createQRImage(editText.getText().toString().trim(),650,650,
                                    check.isChecked() ? BitmapFactory.decodeResource(getResources(),R.drawable .qr_logo): null);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        image.setImageBitmap(Bit);
                                    }
                                });
                        } catch (WriterException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imgChooseDialog();
                return true;
            }
        });
    }

    private void camera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(QRcodeActivity.this, "请到应用中心打开相机权限", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(QRcodeActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
            return;
        }
        Intent intent = new Intent(QRcodeActivity.this, CaptureActivity.class);
        startActivityForResult(intent,REQUEST_CODE_SCAN);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    camera();
                }
                else{
                    Toast.makeText(QRcodeActivity.this,"请打开相机权限",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public static boolean isUrl(String urls) {
        boolean isurl = false;
        String regex ="(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式
        Pattern pat = Pattern.compile(regex.trim());//比对
        Matcher mat = pat.matcher(urls.trim());
        isurl = mat.matches();//判断是否匹配
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SCAN &&resultCode ==RESULT_OK){
            Bundle bundle = data.getExtras();
            String content = data.getStringExtra(DECODED_CONTENT_KEY);
            Bitmap bitmap =data.getParcelableExtra(DECODED_BITMAP_KEY);
            String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
            if(scanResult ==null) {
                boolean isurl = isUrl(content);
                if(isurl) {
                    //openTyoe : true 用浏览器打开网址      false 用安卓阅读器打开
                        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(content));
                        intent.setData(Uri.parse(content));
                        startActivity(intent);
                        content = null;
                }
                else{
                    textView.setText("你扫描到的内容为：" + content);
                    content = null;
                }
            }
            else {
                boolean isurl = isUrl(scanResult);
                if(isurl) {
                    //openTyoe : true 用浏览器打开网址      false 用安卓阅读器打开
                        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(scanResult));
                        intent.setData(Uri.parse(scanResult));
                        startActivity(intent);
                        scanResult = null;
                }
               else{
                    textView.setText("你扫描到的内容为：" + scanResult);
                    scanResult = null;
                }
            }
        }
        }

        private String getFileRoot(Context context){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));{
            File external = context.getExternalFilesDir(null);
            if(external != null){
                return external.getAbsolutePath();
            }
        }
        return context.getFilesDir().getAbsolutePath();
    }
    private void imgChooseDialog(){
        AlertDialog.Builder choiceBuilder = new AlertDialog.Builder(QRcodeActivity.this);
        choiceBuilder.setCancelable(false);
        choiceBuilder
                .setTitle("选择")
                .setSingleChoiceItems(new String[]{"存储至手机", "分享"}, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0://存储
                                        saveImg(Bit);
                                        break;
                                    case 1:// 分享
                                        shareImg(Bit);
                                        break;
                                    default:
                                        break;
                                }
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        choiceBuilder.create();
        choiceBuilder.show();
    }

    private void shareImg(Bitmap bitmap){
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");//设置分享内容的类型
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent = Intent.createChooser(intent, "分享");
        startActivity(intent);
    }
    private void saveImg(Bitmap bitmap){
        String fileName = "qr_"+System.currentTimeMillis() + ".jpg";
        boolean isSaveSuccess = ImageUtil.saveImageToGallery(QRcodeActivity.this,bitmap ,fileName);
        if (isSaveSuccess) {
            Toast.makeText(QRcodeActivity.this, "图片已保存至本地", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(QRcodeActivity.this, "保存图片失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }
    }


}
