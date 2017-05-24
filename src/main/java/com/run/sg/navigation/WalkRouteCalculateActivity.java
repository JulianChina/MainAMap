package com.run.sg.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.run.sg.amap3d.R;


public class WalkRouteCalculateActivity extends BaseActivity {

    private Intent mNaviIntent;
    private double mStartPointLat;
    private double mStartPointLon;
    private double mEndPointLat;
    private double mEndPointLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
        mAMapNaviView.setNaviMode(AMapNaviView.NORTH_UP_MODE);
        mNaviIntent = getIntent();
        if (mNaviIntent != null) {
            mStartPointLat = mNaviIntent.getDoubleExtra("startPointLat", 0.0);
            mStartPointLon = mNaviIntent.getDoubleExtra("startPointLon", 0.0);
            mEndPointLat = mNaviIntent.getDoubleExtra("endPointLat", 0.0);
            mEndPointLon = mNaviIntent.getDoubleExtra("endPointLon", 0.0);
        }

    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        if (mStartPointLat > 0.0 && mStartPointLon > 0.0 && mEndPointLat > 0.0 && mEndPointLon > 0.0) {
            mAMapNavi.calculateWalkRoute(new NaviLatLng(mStartPointLat, mStartPointLon), new NaviLatLng(mEndPointLat, mEndPointLon));
        } else {
            Toast.makeText(WalkRouteCalculateActivity.this, "Latitude or longitude error", Toast.LENGTH_LONG);
        }

    }

    @Override
    public void onCalculateRouteSuccess() {
        super.onCalculateRouteSuccess();
        mAMapNavi.startNavi(NaviType.GPS);
    }
}
