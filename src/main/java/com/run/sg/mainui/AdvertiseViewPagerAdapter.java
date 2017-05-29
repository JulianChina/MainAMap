package com.run.sg.mainui;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yq on 2017/5/29.
 */
public class AdvertiseViewPagerAdapter extends PagerAdapter {

    private List<ImageView> imageViewList = new ArrayList<ImageView>(); // 装载轮播图

    AdvertiseViewPagerAdapter(List<ImageView> imageViewList){
        this.imageViewList = imageViewList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView(imageViewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        // TODO Auto-generated method stub

        final ImageView imageView = imageViewList.get(position);
            /*imageLoader.displayImage(imageView.getTag().toString(), imageView,
                    options);*/

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(getApplicationContext(), imageView.toString(), 0).show();
            }
        });

        container.addView(imageViewList.get(position));
        return imageViewList.get(position);
    }

}
