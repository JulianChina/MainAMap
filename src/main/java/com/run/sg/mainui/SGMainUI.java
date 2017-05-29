package com.run.sg.mainui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.run.sg.aboutus.SGAboutUsActivity;
import com.run.sg.amap3d.R;
import com.run.sg.find.SGFindListViewAdapter;
import com.run.sg.util.SGMapConstants;

/**
 * Created by yq on 2017/5/28.
 */
public class SGMainUI {

    //广告轮播相关
    private ViewPager mAdvertiseViewPager; // 显示轮播图
    private LinearLayout mAdvertiseImgTipsLayout; // 显示小圆点
    private List<ImageView> mAdvertiseImageViewList = new ArrayList<ImageView>(); // 装载轮播图
    private List<ImageView> mAdvertiseImageViewTips; // 装载小圆点
    private String[] mAdvertiseImageUrls = null; // 网络图片资源
    private ImageLoader mAdvertiseImageLoader; // 图片加载器
    private DisplayImageOptions mAdvertiseOptions;// 图片展示配置
    private ScheduledExecutorService mAdvertiseScheduled; // 实例化线程池对象
    private ScheduledExecutorService mAdvertiseTimeScheduled; // 实例化线程池对象
    private TimerTask mAdvertiseTask; // 定时器任务
    private int mAdvertiseOldPage = 0;  //前一页
    private int mAdvertiseNextPage = 1;  //下一页
    private boolean mAdvertiseIsPaused = false; //是否触发暂停

    //发现列表
    private ListView mFoundDialogListview;

    private Context mContext;
    private RelativeLayout mRootView;
    private SGMainUICallback mSGMainUICallBack;

    public SGMainUI(Context context,RelativeLayout relativeLayout,SGMainUICallback sgMainUICallback){
        mContext = context;
        mRootView = relativeLayout;
        mSGMainUICallBack = sgMainUICallback;
    }

    public void initMainUI(){
        initAdvertise();
        initBottomClickListener();
    }

    /**
     * 初始化广告区域轮播
     */
    private void initAdvertise(){
        mAdvertiseViewPager = (ViewPager) mRootView.findViewById(R.id.advertiseViewPager);
        mAdvertiseImgTipsLayout = (LinearLayout) mRootView.findViewById(R.id.imgTipsLayout);
        // 初始化图片资源
        initImageViewList();
        // 初始化圆点
        initImageViewTips();
        // 初始化ImageLoader
        initImageLoader();
        mAdvertiseViewPager.setFocusable(true); // 设置焦点
        mAdvertiseViewPager.setAdapter(new AdvertiseViewPagerAdapter(mAdvertiseImageViewList));
        mAdvertiseViewPager.setOnPageChangeListener(new MyViewPagerChangeListener());
        // 设置默认从1开始
        mAdvertiseViewPager.setCurrentItem(1);

        // 开启定时器，每隔2秒自动播放下一张（通过调用线程实现）（与Timer类似，可使用Timer代替）
        mAdvertiseScheduled = Executors.newSingleThreadScheduledExecutor();
        // 设置一个线程，该线程用于通知UI线程变换图片
        mAdvertiseScheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // 发送消息给UI线程
                System.out.println(mAdvertiseNextPage);
                if (!mAdvertiseIsPaused) { //不是暂停状态
                    handler.sendEmptyMessage(mAdvertiseNextPage);
                }
            }
        }, 2, 4, TimeUnit.SECONDS);

        mAdvertiseTimeScheduled = Executors.newSingleThreadScheduledExecutor();
        mAdvertiseTask = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                mAdvertiseIsPaused = false;
            }
        };
    }

    private void initBottomClickListener(){
        mRootView.findViewById(R.id.found_layout).setOnClickListener(mButtomBarClickListener);
        mRootView.findViewById(R.id.good_you_layout).setOnClickListener(mButtomBarClickListener);
        mRootView.findViewById(R.id.about_us_layout).setOnClickListener(mButtomBarClickListener);
        mFoundDialogListview = (ListView)mRootView.findViewById(R.id.found_dialog_listview);
        mFoundDialogListview.setAdapter(new SGFindListViewAdapter(mContext));
        mFoundDialogListview.setOnItemClickListener(mItemClickListener);
        mRootView.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SGMapConstants.SEARCH_ACTION);
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 初始化图片list
     */
    private void initImageViewList() {

        /*mAdvertiseImageUrls = new String[] {
                "http://h.hiphotos.baidu.com/image/pic/item/9825bc315c6034a8d141851dce1349540823768e.jpg",
                "http://e.hiphotos.baidu.com/image/pic/item/f9198618367adab49eb71beb8ed4b31c8701e437.jpg",
                "http://d.hiphotos.baidu.com/image/pic/item/9345d688d43f8794675c75b2d71b0ef41ad53a8e.jpg",
                "http://a.hiphotos.baidu.com/image/pic/item/55e736d12f2eb938d3de795ad0628535e4dd6fe2.jpg",
                "http://h.hiphotos.baidu.com/image/pic/item/9825bc315c6034a8d141851dce1349540823768e.jpg",
                "http://e.hiphotos.baidu.com/image/pic/item/f9198618367adab49eb71beb8ed4b31c8701e437.jpg" };*/

        mAdvertiseImageUrls = new String[] {
                "advertise_pic_6",
                "advertise_pic_1","advertise_pic_2","advertise_pic_3",
                "advertise_pic_4","advertise_pic_5","advertise_pic_6"
                ,"advertise_pic_1"
        };

        Resources res = mContext.getResources();

        Integer[] drawableId = new Integer[]{
                R.drawable.advertise_pic_6,
                R.drawable.advertise_pic_1,R.drawable.advertise_pic_2,
                R.drawable.advertise_pic_3,R.drawable.advertise_pic_4,
                R.drawable.advertise_pic_5,R.drawable.advertise_pic_6,
                R.drawable.advertise_pic_1
        };

        for (int i = 0; i < mAdvertiseImageUrls.length; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setTag(imageUrls[i]);
            Drawable draw = res.getDrawable(drawableId[i],null);
            imageView.setImageDrawable(draw);
            mAdvertiseImageViewList.add(imageView);
        }
    }

    /**
     * 初始化圆点
     */
    private void initImageViewTips() {

        mAdvertiseImageViewTips = new ArrayList<ImageView>();

        for (int i = 0; i < mAdvertiseImageUrls.length; i++) {
            ImageView imageViewTip = new ImageView(mContext);
            imageViewTip.setLayoutParams(new ViewGroup.LayoutParams(10, 10)); // 设置圆点宽高
            mAdvertiseImageViewTips.add(imageViewTip);
            if (i == 0 || i == mAdvertiseImageUrls.length - 1) {
                imageViewTip.setVisibility(View.GONE);

            } else if (i == 1) {
                imageViewTip
                        .setBackgroundResource(R.drawable.ic_sg_page_indicator_focused);
            } else {
                imageViewTip
                        .setBackgroundResource(R.drawable.ic_sg_page_indicator_unfocused);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            params.leftMargin = 5;
            params.rightMargin = 5;
            mAdvertiseImgTipsLayout.addView(imageViewTip, params);
        }
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        mAdvertiseImageLoader = ImageLoader.getInstance();
        mAdvertiseImageLoader.init(ImageLoaderConfiguration
                .createDefault(mContext));

        mAdvertiseOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.advertise_pic_6) // 加载中的默认图片
                .showImageForEmptyUri(R.drawable.advertise_pic_6) // 加载错误的默认图片
                .showImageOnFail(R.drawable.advertise_pic_6) // 加载失败时的默认图片
                .cacheInMemory(true)// 开启内存缓存
                .cacheOnDisk(true) // 开启硬盘缓存
                .resetViewBeforeLoading(false).build();
    }

    /**
     * 更新当前广告页的Handler
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 接收到消息后，更新页面
            mAdvertiseViewPager.setCurrentItem(msg.what);
        }
    };

    /**
     * ViewPager事件监听器
     */
    private class MyViewPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            // TODO Auto-generated method stub
            switch (state) {
                case 1: //1表示手动触摸
                    mAdvertiseIsPaused = true;
                    if (!mAdvertiseTimeScheduled.isTerminated()) {
                        mAdvertiseTimeScheduled.schedule(mAdvertiseTask, 6, TimeUnit.SECONDS); //让自动循环暂停6秒
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            // TODO Auto-generated method stub
            if (position == mAdvertiseImageViewList.size() - 1 && positionOffset == 0.0f) {
                mAdvertiseViewPager.setCurrentItem(1, false);

            } else if (position == 0 && positionOffset == 0.0f) {
                mAdvertiseViewPager.setCurrentItem(mAdvertiseImageViewList.size() - 2, false);
            }
        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            if (position == mAdvertiseImageViewList.size() - 1) {
                // 设置当前位置小红点的背景
                mAdvertiseNextPage = 2;
                mAdvertiseImageViewTips.get(1).setBackgroundResource(
                        R.drawable.ic_sg_page_indicator_focused);
                // 改变前一个位置小红点的背景
                mAdvertiseImageViewTips.get(mAdvertiseOldPage).setBackgroundResource(
                        R.drawable.ic_sg_page_indicator_unfocused);
                mAdvertiseOldPage = 1;
            } else if (position == 0) {
                mAdvertiseNextPage = mAdvertiseImageViewList.size() - 2;
                // 改变前一个位置小红点的背景
                mAdvertiseImageViewTips.get(mAdvertiseImageViewTips.size() - 2)
                        .setBackgroundResource(
                                R.drawable.ic_sg_page_indicator_focused);
                // 改变前一个位置小红点的背景
                mAdvertiseImageViewTips.get(mAdvertiseOldPage).setBackgroundResource(
                        R.drawable.ic_sg_page_indicator_unfocused);
                mAdvertiseOldPage = mAdvertiseImageViewTips.size() - 2;
            } else {
                // 改变前一个位置小红点的背景
                mAdvertiseImageViewTips.get(position).setBackgroundResource(
                        R.drawable.ic_sg_page_indicator_focused);
                if (position != mAdvertiseOldPage) {
                    mAdvertiseImageViewTips.get(mAdvertiseOldPage).setBackgroundResource(
                            R.drawable.ic_sg_page_indicator_unfocused);
                }
                mAdvertiseNextPage = position + 1;
                mAdvertiseOldPage = position;
            }
        }

    }

    private View.OnClickListener mButtomBarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.found_layout:
                    if (mFoundDialogListview != null){
                        mFoundDialogListview.setVisibility(
                                (mFoundDialogListview.getVisibility() == View.VISIBLE)
                                        ? View.GONE : View.VISIBLE);
                    }
                    break;
                case R.id.good_you_layout:
                    hideFindList();
                    break;
                case R.id.about_us_layout:
                    hideFindList();
                    Intent intent = new Intent(mContext, SGAboutUsActivity.class);
                    mContext.startActivity(intent);
                    break;
                default:
                    hideFindList();
                    break;
            }

        }
    };

    private ListView.OnItemClickListener mItemClickListener = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    mSGMainUICallBack.showDonatePoint();
                    break;
                case 1:
                    Toast.makeText(mContext,"敬老院\n Clicked",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mContext,"失物招领处\n Clicked",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(mContext,"福利院\n Clicked",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(mContext,"onItemClick\n Clicked",Toast.LENGTH_SHORT).show();
                    break;
            }
            hideFindList();
        }
    };

    public void hideFindList(){
        if (mFoundDialogListview != null && mFoundDialogListview.getVisibility() == View.VISIBLE){
            mFoundDialogListview.setVisibility(View.GONE);
        }
    }

    public interface SGMainUICallback {
        void showDonatePoint();
    }
}
