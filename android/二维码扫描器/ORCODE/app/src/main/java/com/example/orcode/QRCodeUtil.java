package com.example.orcode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {
    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static BarcodeFormat barcodeFormat= BarcodeFormat.CODE_128;
    public  static Bitmap creatBarcode(String contents, int desiredWidth,int desiredHeight) {
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result=null;
        try {
            result = writer.encode(contents, barcodeFormat, desiredWidth,
                    desiredHeight);
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static Bitmap createQRImage(String content, int widthPix, int heightPix, Bitmap logoBm) throws WriterException, FileNotFoundException {
        if(content == null ||"".equals(content)){
            return null;
        }
        Map<EncodeHintType,Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN,2);
        BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE,widthPix,heightPix,hints);
        int [] pi =  new int[widthPix * heightPix];
        for (int i = 0;i<heightPix;i++){
            for(int j = 0;j<widthPix;j++){
                if(bitMatrix.get(j,i)){
                    pi[i*widthPix+j] = 0xff000000;
                }
                else {
                    pi[i*widthPix+j] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(widthPix,heightPix,Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pi,0,widthPix,0,0,widthPix,heightPix);
        if(logoBm !=null){
            bitmap = addLogo(bitmap,logoBm);
        }
        return bitmap;
    }
    private static Bitmap addLogo(Bitmap bm_QR, Bitmap bm_login) {
        if (bm_QR == null) {
            return null;
        }
        if (bm_login == null) {
            return bm_QR ;
        }

        //获取图片的宽高
        int bm_QR_Width = bm_QR.getWidth() ;
        int bm_QR_Height = bm_QR.getHeight();
        int bm_login_Width = bm_login.getWidth() ;
        int bm_login_Height = bm_login.getHeight();

        float scale_login = bm_QR_Width*1.0f /5/bm_login_Width ;
        Bitmap bitmap = Bitmap.createBitmap(bm_QR_Width, bm_QR_Height, Bitmap.Config.ARGB_8888);

        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(bm_QR, 0, 0, null);
            canvas.scale(scale_login, scale_login, bm_QR_Width / 2, bm_QR_Height / 2);
            canvas.drawBitmap(bm_login, (bm_QR_Width - bm_login_Width) / 2, (bm_QR_Height - bm_login_Height) / 2, null);

            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;

    }
}
