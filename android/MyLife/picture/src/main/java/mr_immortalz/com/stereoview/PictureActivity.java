package mr_immortalz.com.stereoview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Mr_immortalZ on 2016/7/15.
 * email : mr_immortalz@qq.com
 */
public class PictureActivity extends AppCompatActivity {

    //private Button btnLogin;
    private Button btnSetting;
    private Button btnImage;
    private Button btnImportImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        //btnLogin = (Button) findViewById(R.id.btn_login);
        btnSetting = (Button) findViewById(R.id.btn_setting);
        btnImage = (Button) findViewById(R.id.btn_image);
        btnImportImage = (Button) findViewById(R.id.btn_import_image);

        /*btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PictureActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });*/
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PictureActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PictureActivity.this, ImageActivity.class);
                startActivity(intent);
            }
        });
        btnImportImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PictureActivity.this, ImportImageActivity.class);
                startActivity(intent);
            }
        });
    }
}
