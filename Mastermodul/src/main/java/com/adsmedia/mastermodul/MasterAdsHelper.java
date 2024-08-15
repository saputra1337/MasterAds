package com.adsmedia.mastermodul;

import static com.adsmedia.mastermodul.RestApi.getJSON;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.BannerListener;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;


public class MasterAdsHelper {
    private static StartAppAd startAppAd;
    public static void initializeAds(Activity activity, String packName) {
        getJSON(activity, packName);
    }

    public static void debugMode(Boolean debug) {
        StartAppSDK.setTestAdsEnabled(debug);
    }


    public static void showBanner(Activity activity, RelativeLayout layout) {
        Banner startAppBanner = new Banner(activity, new BannerListener() {
            @Override
            public void onReceiveAd(View view) {
            }

            @Override
            public void onFailedToReceiveAd(View view) {
                layout.setVisibility(View.GONE);
            }

            @Override
            public void onImpression(View view) {
            }

            @Override
            public void onClick(View view) {
            }
        });
        RelativeLayout.LayoutParams bannerParameters =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layout.addView(startAppBanner, bannerParameters);
    }

    public static void loadInterstitial(Activity activity) {
        startAppAd = new StartAppAd(activity);
        startAppAd.loadAd();
    }

    public static void showInterstitial(Activity activity) {
        startAppAd.showAd();
    }
}
