package com.white.dot;

import android.os.Bundle;

import com.secrethq.ads.PTAdAppLovinBridge;
import com.secrethq.ads.PTAdChartboostBridge;
import com.secrethq.ads.PTAdLeadBoltBridge;
import com.secrethq.ads.PTAdMoPubBridge;
import com.secrethq.ads.PTAdRevMobBridge;
import com.secrethq.ads.PTAdUpsightBridge;
import com.secrethq.ads.PTAdVungleBridge;
import com.secrethq.store.PTStoreBridge;
import com.secrethq.utils.PTJniHelper;
import com.secrethq.utils.PTServicesBridge;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxGLSurfaceView;

public class PTPlayer extends Cocos2dxActivity {

	private static native void loadModelController();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onNativeInit(){
			initBridges();				
	}

	private void initBridges(){
		PTStoreBridge.initBridge( this );
		PTServicesBridge.initBridge(this, "0");

		if (PTJniHelper.isAdNetworkActive("kChartboost")) {
			PTAdChartboostBridge.initBridge(this);
		}

		if (PTJniHelper.isAdNetworkActive("kRevMob")) {
			PTAdRevMobBridge.initBridge(this);
		}

		if (PTJniHelper.isAdNetworkActive("kAppLovin")) {
			PTAdAppLovinBridge.initBridge(this);
		}

		if (PTJniHelper.isAdNetworkActive("kLeadBolt")) {
			PTAdLeadBoltBridge.initBridge(this);
		}

		if (PTJniHelper.isAdNetworkActive("kVungle")) {
			PTAdVungleBridge.initBridge(this);
		}

		if (PTJniHelper.isAdNetworkActive("kPlayhaven")) {
			PTAdUpsightBridge.initBridge(this);
		}

		if (PTJniHelper.isAdNetworkActive("kMoPub")) {
			PTAdMoPubBridge.initBridge(this);
		}
	}

	@Override
	public Cocos2dxGLSurfaceView onCreateView() {
		Cocos2dxGLSurfaceView glSurfaceView = new Cocos2dxGLSurfaceView(this);
		glSurfaceView.setEGLConfigChooser(8, 8, 8, 0, 0, 0);

		return glSurfaceView;
	}

	static {
		System.loadLibrary("player");
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (PTJniHelper.isAdNetworkActive("kChartboost")) {
			PTAdChartboostBridge.onResume( this );
		}
		PTServicesBridge.onResume( this );
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (PTJniHelper.isAdNetworkActive("kChartboost")) {
			PTAdChartboostBridge.onStart( this );
		}
	}
	
	@Override
	protected void onPause() {
	    super.onPause();
	    PTServicesBridge.onPause( this );
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (PTJniHelper.isAdNetworkActive("kChartboost")) {
			PTAdChartboostBridge.onStop( this );
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
