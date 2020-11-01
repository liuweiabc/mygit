package com.example.xuh.shopping;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SegmentTabLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


public class Index_home extends Fragment implements OnBannerListener {

    private String[] titles = {"定制服务","短租房源","民宿装饰","设计咨询","作品展览","商品拍卖","设计学院","商品预售","DIY专区","更多"};
    private ViewPager mPager;
    private List<View> mPagerList;
    private List<Model> mDatas;
    private LinearLayout mLlDot;
    private LayoutInflater inflater;
    private String[] content1 = {"木质家具与纯白墙壁结合，呈现出简约、安静的环境...","这是一个尝试文件"};
    private String[] content2 = {"这是材料1","这是材料2"};
    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 10;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;

    private RecyclerView rv;
    protected LinearLayout search_layout;
    private TextView saoyisao,didian,huiyuanma;
    private Banner banner;
    private ArrayList<Integer> list_path;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private View rootView;
    private SegmentTabLayout segmentTabLayout;
    private List<goodsModel>goodsList;
    //private IndicatorView entranceIndicatorView;
   // private PageMenuLayout<ModelHomeEntrance> mPageMenuLayout;

    public Index_home() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater1, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View rootView = inflater1.inflate(R.layout.fragment_index_home1, container, false);
            //rv = (RecyclerView) rootView.findViewById(R.id.rv);
            //initData();
            initView(rootView);

            initDatas();
            inflater =LayoutInflater.from(this.getActivity());

            GridLayoutManager layout1 = new GridLayoutManager(this.getContext(),2);
            //layout1.setOrientation(LinearLayout.HORIZONTAL);
            rv.setLayoutManager(layout1);
            goodsAdapter oneadapter = new goodsAdapter(goodsList);
            rv.setAdapter(oneadapter);

        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) inflater.inflate(R.layout.gridview, mPager, false);
            gridView.setAdapter(new GridViewAdapter(this.getActivity(), mDatas, i, pageSize));
            mPagerList.add(gridView);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    Toast.makeText(Index_home.this.getActivity(), mDatas.get(pos).getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        //设置适配器
        mPager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置圆点
        setOvalLayout(container);



        //initData();
            //initView1(rootView);
            //init();
        return rootView;
    }

    /**
     * 初始化数据源
     */
    private void initDatas() {
        goodsList = new ArrayList<>();
        for(int i = 0;i < content1.length;i++){
            int imageid = getResources().getIdentifier("goodspic","drawable",this.getActivity().getPackageName());
            goodsList.add(new goodsModel(imageid,content1[i]));
        }
        mDatas = new ArrayList<Model>();
        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", this.getActivity().getPackageName());
            mDatas.add(new Model(titles[i], imageId));
        }
    }

    /**
     * 设置圆点
     */
    public void setOvalLayout(ViewGroup container) {
        for (int i = 0; i < pageCount; i++) {
            mLlDot.addView(inflater.inflate(R.layout.dot, container,false));
        }
        // 默认显示第一页
        mLlDot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                mLlDot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                mLlDot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }


    /*private void initData() {
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < 38; i++) {
            Resources res = getResources();
            datas.add(res.getIdentifier("ic_category_" + i, "mipmap", this.getActivity().getPackageName()));
        }

         *用来确定每一个item如何进行排列摆放
         * LinearLayoutManager 相当于ListView的效果
         GridLayoutManager相当于GridView的效果
         StaggeredGridLayoutManager 瀑布流

        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                outRect.left = 10;
                outRect.top = 10;
                outRect.top = 10;
            }
        });
        rv.setAdapter(new RvAdpter(this.getActivity(), datas));
    }*/

    /*private void initData() {
        homeEntrances = new ArrayList<>();
        homeEntrances.add(new ModelHomeEntrance("美食", R.mipmap.ic_category_0));
        homeEntrances.add(new ModelHomeEntrance("电影", R.mipmap.ic_category_1));
        homeEntrances.add(new ModelHomeEntrance("酒店住宿", R.mipmap.ic_category_2));
        homeEntrances.add(new ModelHomeEntrance("生活服务", R.mipmap.ic_category_3));
        homeEntrances.add(new ModelHomeEntrance("KTV", R.mipmap.ic_category_4));
        homeEntrances.add(new ModelHomeEntrance("旅游", R.mipmap.ic_category_5));
        homeEntrances.add(new ModelHomeEntrance("学习培训", R.mipmap.ic_category_6));
        homeEntrances.add(new ModelHomeEntrance("汽车服务", R.mipmap.ic_category_7));
        homeEntrances.add(new ModelHomeEntrance("摄影写真", R.mipmap.ic_category_8));
        homeEntrances.add(new ModelHomeEntrance("休闲娱乐", R.mipmap.ic_category_10));
        homeEntrances.add(new ModelHomeEntrance("丽人", R.mipmap.ic_category_11));
        homeEntrances.add(new ModelHomeEntrance("运动健身", R.mipmap.ic_category_12));
        homeEntrances.add(new ModelHomeEntrance("大保健", R.mipmap.ic_category_13));
        homeEntrances.add(new ModelHomeEntrance("团购", R.mipmap.ic_category_14));
        homeEntrances.add(new ModelHomeEntrance("景点", R.mipmap.ic_category_16));
        homeEntrances.add(new ModelHomeEntrance("全部分类", R.mipmap.ic_category_15));
    }*/

    /*private void init() {
        mPageMenuLayout.setPageDatas(homeEntrances, new PageMenuViewHolderCreator() {
            @Override
            public AbstractHolder createHolder(View itemView) {
                return new AbstractHolder<ModelHomeEntrance>(itemView) {
                    private TextView entranceNameTextView;
                    private ImageView entranceIconImageView;

                    @Override
                    protected void initView(View itemView) {
                        entranceIconImageView = itemView.findViewById(R.id.entrance_image);
                        entranceNameTextView = itemView.findViewById(R.id.entrance_name);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) ((float) ScreenUtil.getScreenWidth() / 4.0f));
                        itemView.setLayoutParams(layoutParams);
                    }

                    @Override
                    public void bindView(RecyclerView.ViewHolder holder, final ModelHomeEntrance data, int pos) {
                        entranceNameTextView.setText(data.getName());
                        entranceIconImageView.setImageResource(data.getImage());
                    }
                };
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_home_entrance;
            }
        });
        //entranceIndicatorView.setIndicatorCount(mPageMenuLayout.getPageCount());
        System.out.println(mPageMenuLayout.getPageCount());
        //mPageMenuLayout.setOnPageListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                entranceIndicatorView.setCurrentIndicator(position);
            }
        });
    }*/




    public void initView(View view){
        //pager=view.findViewById(R.id.pages);
        //tabLayout=view.findViewById(R.id.tab_layouts);
        rv = view.findViewById(R.id.recyclerview);
        //segmentTabLayout = view.findViewById(R.id.tl_1);
        mPager = (ViewPager) view.findViewById(R.id.viewpager);
        mLlDot = (LinearLayout) view.findViewById(R.id.ll_dot);
        //search_layout = (LinearLayout)view.findViewById(R.id.search_layout);
        //saoyisao = (TextView)view.findViewById(R.id.saoyisao);
        //didian = (TextView)view.findViewById(R.id.didian);
        //huiyuanma = (TextView)view.findViewById(R.id.huiyuanma);
        //entranceIndicatorView = view.findViewById(R.id.main_home_entrance_indicator);
        //mPageMenuLayout = view.findViewById(R.id.pagemenu);
//        初始化搜索栏
       // search_layout.bringToFront();
//        初始化banner
        banner = view.findViewById(R.id.banner);
        list_path = new ArrayList<Integer>();
        list_path.add(R.drawable.banner1);
        list_path.add(R.drawable.banner2);
        list_path.add(R.drawable.banner3);
        list_path.add(R.drawable.banner4);
        list_path.add(R.drawable.banner5);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new MyLoader());
        banner.setBannerAnimation(Transformer.Default);
        banner.setDelayTime(3000);
        banner.isAutoPlay(true);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImages(list_path).setOnBannerListener(this);
        banner.start();
//        初始化图标
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"fonts/iconfont.ttf");
        //saoyisao.setTypeface(font);
        //didian.setTypeface(font);
        //huiyuanma.setTypeface(font);
//        初始化item
        //recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        //linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        //recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setNestedScrollingEnabled(false);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setFocusable(false);
        //final RecylerViewAdapter adapter = new RecylerViewAdapter(getActivity());

        //recyclerView.setAdapter(adapter);
        /*adapter.setOnItemClickListener(new RecylerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int id) {
                Intent intent = new Intent( getActivity(),Details.class);
                intent.putExtra( "id",id );
                startActivity( intent );
                getActivity().overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            }
            @Override
            public void onLongClick(int id) {
                Intent intent = new Intent( getActivity(),Details.class);
                intent.putExtra( "id",id );
                startActivity( intent );
                getActivity().overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            }
        });*/
    }

    @Override
    public void OnBannerClick(int position) {

    }

    private class MyLoader extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext()).load(path).into(imageView);
        }
    }
}
