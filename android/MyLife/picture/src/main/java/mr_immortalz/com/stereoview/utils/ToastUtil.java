package mr_immortalz.com.stereoview.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void showInfo(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }
}
