package com.example.xuh.shopping;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xuh.shopping.adapter.ReviewAdapter;
import com.example.xuh.shopping.bean.Collection;
import com.example.xuh.shopping.bean.Review;
import com.example.xuh.shopping.util.MyCollectionDbHelper;
import com.example.xuh.shopping.util.ReviewDbHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * 商品信息评论/留言类
 * @author autumn_leaf
 */
public class ReviewCommodityActivity extends AppCompatActivity {

    TextView title,description,nicheng;
    ImageView ivCommodity;
    ImageView ivCommodity1;
    ListView lvReview;
    LinkedList<Review> reviews = new LinkedList<>();
    EditText etComment;
    int position;
    byte[] picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_commodity);
        ivCommodity = findViewById(R.id.iv_commodity);
        ivCommodity1 = findViewById(R.id.iv_commodity1);
        final Bundle bundle2 = new Bundle();
        ivCommodity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle2.putString("nicheng",nicheng.getText().toString());
                Intent intent = new Intent(ReviewCommodityActivity.this,personal.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });
        title = findViewById(R.id.tv_title);
        description = findViewById(R.id.tv_description);
        nicheng = findViewById(R.id.tv_nicheng);
        Bundle b = getIntent().getExtras();
        if( b != null) {
            picture = b.getByteArray("picture");
            Bitmap img = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            ivCommodity.setImageBitmap(img);
            title.setText(b.getString("title"));
            description.setText(b.getString("description"));
            nicheng.setText(b.getString("stuId"));
            position = b.getInt("position");
        }
        //返回
        TextView tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //点击收藏按钮
        ImageButton ibMyLove = findViewById(R.id.ib_my_love);
        ibMyLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCollectionDbHelper dbHelper = new MyCollectionDbHelper(getApplicationContext(),MyCollectionDbHelper.DB_NAME,null,1);
                Collection collection = new Collection();
                collection.setTitle(title.getText().toString());
                //String price1 = price.getText().toString().substring(0,price.getText().toString().length()-1);
                //collection.setPrice(Float.parseFloat(price1));
                //collection.setPhone(phone.getText().toString());
                String nicheng1 = nicheng.getText().toString();
                collection.setNicheng(nicheng1);
                collection.setDescription(description.getText().toString());
                collection.setPicture(picture);
                String stuId = getIntent().getStringExtra("reviewer");
                collection.setStuId(stuId);
                dbHelper.addMyCollection(collection);
                Toast.makeText(getApplicationContext(),"已添加至我的收藏!",Toast.LENGTH_SHORT).show();
            }
        });

        etComment = findViewById(R.id.et_comment);
        lvReview = findViewById(R.id.list_comment);
        //提交评论点击事件
        Button btnReview = findViewById(R.id.btn_submit);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先检查是否为空
                if(CheckInput()) {
                    ReviewDbHelper dbHelper = new ReviewDbHelper(getApplicationContext(),ReviewDbHelper.DB_NAME,null,1);
                    Review review = new Review();
                    review.setContent(etComment.getText().toString());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
                    //获取当前时间
                    Date date = new Date(System.currentTimeMillis());
                    review.setCurrentTime(simpleDateFormat.format(date));
                    String stuId = getIntent().getStringExtra("reviewer");
                    review.setStuId(stuId);
                    review.setPosition(position);
                    dbHelper.addReview(review);
                    //评论置为空
                    etComment.setText("");
                    Toast.makeText(getApplicationContext(),"评论成功!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        final ReviewAdapter adapter = new ReviewAdapter(getApplicationContext());
        final ReviewDbHelper dbHelper = new ReviewDbHelper(getApplicationContext(),ReviewDbHelper.DB_NAME,null,1);
        reviews = dbHelper.readReviews(position);
        adapter.setData(reviews);
        //设置适配器
        lvReview.setAdapter(adapter);
        //刷新页面
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviews = dbHelper.readReviews(position);
                adapter.setData(reviews);
                lvReview.setAdapter(adapter);
            }
        });
    }

    /**
     * 检查输入评论是否为空
     * @return true
     */
    public boolean CheckInput() {
        String comment = etComment.getText().toString();
        if (comment.trim().equals("")) {
            Toast.makeText(this,"评论内容不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
