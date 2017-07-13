

package com.secrethq.ads;

import android.app.Activity;
import android.util.Log;

import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.ChartboostDelegate;
import com.chartboost.sdk.Libraries.CBLogging.Level;
import com.chartboost.sdk.Model.CBError.CBClickError;
import com.chartboost.sdk.Model.CBError.CBImpressionError;

import org.cocos2dx.lib.Cocos2dxActivity;

import java.lang.ref.WeakReference;

public class PTAdChartboostBridge {
	private static final String TAG = "PTAdChartboostBridge";
	private static WeakReference<Cocos2dxActivity> s_activity;
	private static Cocos2dxActivity activity;
	
	private static native String appId();
	private static native String appSignature();

	private static boolean initComplete;
	
    
	// the method must be called in main thread, before any other method
	public static void initBridge(Cocos2dxActivity activity){
		Log.v(TAG, "PTAdChartboostBridge --- INIT");
		PTAdChartboostBridge.s_activity = new WeakReference<Cocos2dxActivity>(activity);		
		PTAdChartboostBridge.activity = activity;

		initComplete = false;
		
		PTAdChartboostBridge.s_activity.get().runOnUiThread(new Runnable() {
			public void run() {
				if(PTAdChartboostBridge.appId() == null || PTAdChartboostBridge.appSignature() == null){
					return;
				}
				
				Chartboost.startWithAppId(PTAdChartboostBridge.activity, PTAdChartboostBridge.appId(), PTAdChartboostBridge.appSignature());
				Chartboost.setLoggingLevel(Level.ALL);
				Chartboost.setDelegate(delegate);
				Chartboost.onCreate(PTAdChartboostBridge.activity);
				
				initComplete = true;
				
				Chartboost.onStart(PTAdChartboostBridge.activity);
			}
		});
	}
	
	public static void cacheInterstitial(final String location) {
		Log.v(TAG, "cacheInterstitial(\"" + location + "\") is called...");
		
//		if (PTAdChartboostBridge.s_chartBoost != null) {
//			PTAdChartboostBridge.s_activity.get().runOnUiThread(new Runnable() {
//				public void run() {
//					s_chartBoost.cacheInterstitial(location);
//				}
//			});
//		} else {
//			Log.w(TAG, "Abort! cannot get shared Chartboost!");
//		}
	}
	
	public static void showInterstitial() {
		if(initComplete == false){
			return;
		}
		Log.v(TAG, "showInterstitial() is called...");
		PTAdChartboostBridge.s_activity.get().runOnUiThread(new Runnable() {
			public void run() {
				Chartboost.showInterstitial( CBLocation.LOCATION_DEFAULT );
			}
		});
	}
	
	public static void showInterstitial(final String location) {
		if(initComplete == false){
			return;
		}

		Log.v(TAG, "showInterstitial(\"" + location + "\") is called...");
		
		PTAdChartboostBridge.s_activity.get().runOnUiThread(new Runnable() {
			public void run() {
				Chartboost.showInterstitial( CBLocation.LOCATION_DEFAULT );
			}
		});
	}
	
	public static void hasCachedInterstitial() {
		Log.v(TAG, "hasCachedInterstitial() is called...");
		
//		if (s_chartBoost != null) {
//			PTAdChartboostBridge.s_activity.get().runOnUiThread(new Runnable() {
//				public void run() {
//					if (s_chartBoost.hasCachedInterstitial()) {
//						PTAdChartboostBridge.s_activity.get().runOnGLThread(new Runnable() {
//							public void run() {
////								PTAdChartboostBridge.didCacheInterstitial("Default");
//							}
//						});
//					}
//				}
//			});
//		} else {
//			Log.w(TAG, "Abort! cannot get shared Chartboost!");
//		}
	}
	
	public static void hasCachedInterstitial(final String location) {
		Log.v(TAG, "hasCachedInterstitial(\"" + location + "\") is called...");
		
//		if (s_chartBoost != null) {
//			PTAdChartboostBridge.s_activity.get().runOnUiThread(new Runnable() {
//				public void run() {
//					if (s_chartBoost.hasCachedInterstitial(location)) {
//						PTAdChartboostBridge.s_activity.get().runOnGLThread(new Runnable() {
//							public void run() {
////								PTAdChartboostBridge.didCacheInterstitial(location);
//							}
//						});
//					}
//				}
//			});
//		} else {
//			Log.w(TAG, "Abort! cannot get shared Chartboost!");
//		}
	}
	
	public static void onResume( Activity act){
		Chartboost.onResume( act );
	}

	public static void onStart( Activity act){
		Chartboost.onStart( act );
	}

	public static void onStop( Activity act){
		Chartboost.onStop( act );
	}

	private static ChartboostDelegate delegate = new ChartboostDelegate() {

		@Override
		public boolean shouldRequestInterstitial(String location) {
			Log.i(TAG, "SHOULD REQUEST INTERSTITIAL '"+ (location != null ? location : "null"));		
			return true;
		}
	
		@Override
		public boolean shouldDisplayInterstitial(String location) {
			Log.i(TAG, "SHOULD DISPLAY INTERSTITIAL '"+ (location != null ? location : "null"));
			return true;
		}
	
		@Override
		public void didCacheInterstitial(String location) {
			Log.i(TAG, "DID CACHE INTERSTITIAL '"+ (location != null ? location : "null"));
		}
	
		@Override
		public void didFailToLoadInterstitial(String location, CBImpressionError error) {
			Log.i(TAG, "DID FAIL TO LOAD INTERSTITIAL '"+ (location != null ? location : "null")+ " Error: " + error.name());
//			Toast.makeText(getApplicationContext(), "INTERSTITIAL '"+location+"' REQUEST FAILED - " + error.name(), Toast.LENGTH_SHORT).show();
		}
	
		@Override
		public void didDismissInterstitial(String location) {
			Log.i(TAG, "DID DISMISS INTERSTITIAL: "+ (location != null ? location : "null"));
		}
	
		@Override
		public void didCloseInterstitial(String location) {
			Log.i(TAG, "DID CLOSE INTERSTITIAL: "+ (location != null ? location : "null"));
		}
	
		@Override
		public void didClickInterstitial(String location) {
			Log.i(TAG, "DID CLICK INTERSTITIAL: "+ (location != null ? location : "null"));
		}
	
		@Override
		public void didDisplayInterstitial(String location) {
			Log.i(TAG, "DID DISPLAY INTERSTITIAL: " +  (location != null ? location : "null"));
		}
	
		@Override
		public boolean shouldRequestMoreApps(String location) {
			Log.i(TAG, "SHOULD REQUEST MORE APPS: " +  (location != null ? location : "null"));
			return true;
		}
	
		@Override
		public boolean shouldDisplayMoreApps(String location) {
			Log.i(TAG, "SHOULD DISPLAY MORE APPS: " +  (location != null ? location : "null"));
			return true;
		}
	
		@Override
		public void didFailToLoadMoreApps(String location, CBImpressionError error) {
			Log.i(TAG, "DID FAIL TO LOAD MOREAPPS " +  (location != null ? location : "null")+ " Error: "+ error.name());
//			Toast.makeText(getApplicationContext(), "MORE APPS REQUEST FAILED - " + error.name(), Toast.LENGTH_SHORT).show();
		}
	
		@Override
		public void didCacheMoreApps(String location) {
			Log.i(TAG, "DID CACHE MORE APPS: " +  (location != null ? location : "null"));
		}
	
		@Override
		public void didDismissMoreApps(String location) {
			Log.i(TAG, "DID DISMISS MORE APPS " +  (location != null ? location : "null"));
		}
	
		@Override
		public void didCloseMoreApps(String location) {
			Log.i(TAG, "DID CLOSE MORE APPS: "+  (location != null ? location : "null"));
		}
	
		@Override
		public void didClickMoreApps(String location) {
			Log.i(TAG, "DID CLICK MORE APPS: "+  (location != null ? location : "null"));
		}
	
		@Override
		public void didDisplayMoreApps(String location) {
			Log.i(TAG, "DID DISPLAY MORE APPS: " +  (location != null ? location : "null"));
		}
	
		@Override
		public void didFailToRecordClick(String uri, CBClickError error) {
			Log.i(TAG, "DID FAILED TO RECORD CLICK " + (uri != null ? uri : "null") + ", error: " + error.name());
//			Toast.makeText(getApplicationContext(), "FAILED TO RECORD CLICK " + (uri != null ? uri : "null") + ", error: " + error.name(), Toast.LENGTH_SHORT).show();
		}
	
		@Override
		public boolean shouldDisplayRewardedVideo(String location) {
			Log.i(TAG, String.format("SHOULD DISPLAY REWARDED VIDEO: '%s'",  (location != null ? location : "null")));
			return true;
		}
	
		@Override
		public void didCacheRewardedVideo(String location) {
			Log.i(TAG, String.format("DID CACHE REWARDED VIDEO: '%s'",  (location != null ? location : "null")));
		}
	
		@Override
		public void didFailToLoadRewardedVideo(String location,
				CBImpressionError error) {
			Log.i(TAG, String.format("DID FAIL TO LOAD REWARDED VIDEO: '%s', Error:  %s",  (location != null ? location : "null"), error.name()));
//			Toast.makeText(getApplicationContext(), String.format("DID FAIL TO LOAD REWARDED VIDEO '%s' because %s", location, error.name()), Toast.LENGTH_SHORT).show();
		}
	
		@Override
		public void didDismissRewardedVideo(String location) {
			Log.i(TAG, String.format("DID DISMISS REWARDED VIDEO '%s'",  (location != null ? location : "null")));
		}
	
		@Override
		public void didCloseRewardedVideo(String location) {
			Log.i(TAG, String.format("DID CLOSE REWARDED VIDEO '%s'",  (location != null ? location : "null")));
		}
	
		@Override
		public void didClickRewardedVideo(String location) {
			Log.i(TAG, String.format("DID CLICK REWARDED VIDEO '%s'",  (location != null ? location : "null")));
		}
	
		@Override
		public void didCompleteRewardedVideo(String location, int reward) {
			Log.i(TAG, String.format("DID COMPLETE REWARDED VIDEO '%s' FOR REWARD %d",  (location != null ? location : "null"), reward));
		}
		
		@Override
		public void didDisplayRewardedVideo(String location) {
			Log.i(TAG, String.format("DID DISPLAY REWARDED VIDEO: '%s'",  (location != null ? location : "null")));
		}
	};
}
