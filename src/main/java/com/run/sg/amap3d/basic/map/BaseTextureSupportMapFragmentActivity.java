package com.run.sg.amap3d.basic.map;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.TextureSupportMapFragment;

/**
 * 基本地图（TextureSupportMapFragment）实现
 */
public class BaseTextureSupportMapFragmentActivity extends FragmentActivity {
	private AMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.run.sg.amap3d.R.layout.basemap_texture_support_fragment_activity);
		setUpMapIfNeeded();

		setTitle("基本地图（TextureSupportMapFragment）");
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
	}

	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((TextureSupportMapFragment) getSupportFragmentManager()
					.findFragmentById(com.run.sg.amap3d.R.id.map)).getMap();
		}
	}

}
