package com.appnext.sdktest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.appnext.ads.fullscreen.FullScreenVideo;
import com.appnext.ads.fullscreen.RewardedVideo;
import com.appnext.ads.fullscreen.Video;
import com.appnext.ads.interstitial.Interstitial;
import com.appnext.banners.BannerAdRequest;
import com.appnext.banners.BannerListener;
import com.appnext.banners.BannerView;
import com.appnext.core.AppnextError;
import com.appnext.core.callbacks.OnAdClicked;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;
import com.appnext.core.callbacks.OnVideoEnded;

public class MainActivity extends Activity implements View.OnClickListener {

    private Interstitial interstitial;
    private FullScreenVideo fullscreen;
    private RewardedVideo rewardedMulti;
    private RewardedVideo rewardedNormal;

    private OnVideoEnded onVideoEnded;
    private OnAdLoaded onAdLoaded;
    private OnAdError onAdError;
    private OnAdClosed onAdClosed;
    private OnAdClicked onAdClicked;

    private BannerView bannerView1, bannerView2, bannerView3;
    private Button interLoadButton, interShowButton, fullScreenLoadButton, fullScreenShowButton, multiRewardedLoadButton, multiRewardedShowButton, normalRewardedLoadButton, normalRewardedShowButton;
    private Spinner bannerSpinner;
    private String placementIDInter = "103029bd-5625-4ba2-9293-8a29461b8692";
    private String placementIDFullScreen = "faed8533-5061-418b-8ad1-7b19a066ef8a";
    private String placementIDRewardedNormal = "37d5b4a5-500b-44fb-b745-788084be2794";
    private String placementIDRewardedMulti = "0445b326-976b-4d49-99b6-ccc0c5284346";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVies();
        onVideoEnded = new OnVideoEnded() {
            @Override
            public void videoEnded() {
                Toast.makeText(MainActivity.this, "videoEnded", Toast.LENGTH_SHORT).show();
            }
        };

        onAdLoaded = new OnAdLoaded() {
            @Override
            public void adLoaded(String banner) {
                Toast.makeText(MainActivity.this, "adLoaded", Toast.LENGTH_SHORT).show();
            }
        };

        onAdError = new OnAdError() {
            @Override
            public void adError(String error) {
                Toast.makeText(MainActivity.this, "adError " + error, Toast.LENGTH_SHORT).show();
            }
        };

        onAdClosed = new OnAdClosed() {
            @Override
            public void onAdClosed() {
                Toast.makeText(MainActivity.this, "onAdClosed", Toast.LENGTH_SHORT).show();
            }
        };

        onAdClicked = new OnAdClicked() {
            @Override
            public void adClicked() {
                Toast.makeText(MainActivity.this, "adClicked", Toast.LENGTH_SHORT).show();
            }
        };

        interstitial = new Interstitial(this, placementIDInter);
        interstitial.setOnAdClickedCallback(onAdClicked);
        interstitial.setOnAdClosedCallback(onAdClosed);
        interstitial.setOnAdErrorCallback(onAdError);
        interstitial.setOnAdLoadedCallback(onAdLoaded);

        fullscreen = new FullScreenVideo(this, placementIDFullScreen);
        fullscreen.setOnAdClickedCallback(onAdClicked);
        fullscreen.setOnAdClosedCallback(onAdClosed);
        fullscreen.setOnAdErrorCallback(onAdError);
        fullscreen.setOnAdLoadedCallback(onAdLoaded);
        fullscreen.setOnVideoEndedCallback(onVideoEnded);
        fullscreen.setBackButtonCanClose(true);
        fullscreen.setShowClose(true);

        rewardedMulti = new RewardedVideo(this, placementIDRewardedMulti);
        rewardedMulti.setMode(RewardedVideo.VIDEO_MODE_MULTI);
        rewardedMulti.setVideoLength(Video.VIDEO_LENGTH_SHORT);
        rewardedMulti.setMultiTimerLength(9);
        rewardedMulti.setRollCaptionTime(-1);
        rewardedMulti.setOnAdClickedCallback(onAdClicked);
        rewardedMulti.setOnAdClosedCallback(onAdClosed);
        rewardedMulti.setOnAdErrorCallback(onAdError);
        rewardedMulti.setOnAdLoadedCallback(onAdLoaded);
        rewardedMulti.setOnVideoEndedCallback(onVideoEnded);
        rewardedMulti.setRewardedServerSidePostback("TransactionId", "UserId", "TypeCurrency", "Amount", "CustomParameter");

        rewardedNormal = new RewardedVideo(this, placementIDRewardedNormal);
        rewardedNormal.setMode(RewardedVideo.VIDEO_MODE_NORMAL);
        rewardedNormal.setOnAdClickedCallback(onAdClicked);
        rewardedNormal.setOnAdClosedCallback(onAdClosed);
        rewardedNormal.setOnAdErrorCallback(onAdError);
        rewardedNormal.setOnAdLoadedCallback(onAdLoaded);
        rewardedNormal.setOnVideoEndedCallback(onVideoEnded);
        rewardedNormal.setRewardedServerSidePostback("TransactionId", "UserId", "TypeCurrency", "Amount", "CustomParameter");

        Switch streamingSwitch = (Switch) findViewById(R.id.streamingSwitch);
        streamingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Video.setStreamingMode(isChecked);
            }
        });
    }

    private void setVies() {
        interLoadButton = (Button) findViewById(R.id.inter_load_button);
        interLoadButton.setOnClickListener(this);
        interShowButton = (Button) findViewById(R.id.inter_show_button);
        interShowButton.setOnClickListener(this);
        fullScreenLoadButton = (Button) findViewById(R.id.full_screen_load_button);
        fullScreenLoadButton.setOnClickListener(this);
        fullScreenShowButton = (Button) findViewById(R.id.full_screen_show_button);
        fullScreenShowButton.setOnClickListener(this);
        multiRewardedLoadButton = (Button) findViewById(R.id.multi_rewarded_load_button);
        multiRewardedLoadButton.setOnClickListener(this);
        multiRewardedShowButton = (Button) findViewById(R.id.multi_rewarded_show_button);
        multiRewardedShowButton.setOnClickListener(this);
        normalRewardedLoadButton = (Button) findViewById(R.id.normal_rewarded_load_button);
        normalRewardedLoadButton.setOnClickListener(this);
        normalRewardedShowButton = (Button) findViewById(R.id.normal_rewarded_show_button);
        normalRewardedShowButton.setOnClickListener(this);
        bannerSpinner = (Spinner) findViewById(R.id.spinner);
        bannerView1 = (BannerView) findViewById(R.id.banner1);
        bannerView1.setVisibility(View.INVISIBLE);
        bannerView2 = (BannerView) findViewById(R.id.banner2);
        bannerView2.setVisibility(View.INVISIBLE);
        bannerView3 = (BannerView) findViewById(R.id.banner3);
        bannerView3.setVisibility(View.INVISIBLE);
        String[] arraySpinner = new String[] {"Choose a banner size to show", "BANNER (320x50)", "LARGE BANNER (320x100)", "MEDIUM RECTANGLE (300x250)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        bannerSpinner.setAdapter(adapter);
        bannerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:

                    case 1:
                        bannerView1.loadAd(new BannerAdRequest());
                        bannerView1.setVisibility(View.VISIBLE);
                        bannerView1.setBannerListener(new BannerListener(){
                            @Override
                            public void onError(AppnextError appnextError) {
                                super.onError(appnextError);
                                Toast.makeText(getApplicationContext(), "No ads", Toast.LENGTH_SHORT).show();
                            }
                        });
                        bannerView2.setVisibility(View.INVISIBLE);
                        bannerView3.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        bannerView2.loadAd(new BannerAdRequest());
                        bannerView1.setVisibility(View.INVISIBLE);
                        bannerView2.setVisibility(View.VISIBLE);
                        bannerView2.setBannerListener(new BannerListener(){
                            @Override
                            public void onError(AppnextError appnextError) {
                                super.onError(appnextError);
                                Toast.makeText(getApplicationContext(), "No ads", Toast.LENGTH_SHORT).show();
                            }
                        });
                        bannerView3.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        bannerView3.loadAd(new BannerAdRequest().setMute(true).setAutoPlay(true));
                        bannerView1.setVisibility(View.INVISIBLE);
                        bannerView2.setVisibility(View.INVISIBLE);
                        bannerView3.setVisibility(View.VISIBLE);
                        bannerView3.setBannerListener(new BannerListener(){
                            @Override
                            public void onError(AppnextError appnextError) {
                                super.onError(appnextError);
                                Toast.makeText(getApplicationContext(), "No ads", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bannerView1.setVisibility(View.INVISIBLE);
                bannerView2.setVisibility(View.INVISIBLE);
                bannerView3.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inter_load_button:
                interstitial.loadAd();
                break;
            case R.id.inter_show_button:
                interstitial.showAd();
                break;
            case R.id.full_screen_load_button:
                fullscreen.loadAd();
                break;
            case R.id.full_screen_show_button:
                fullscreen.showAd();
                break;
            case R.id.multi_rewarded_load_button:
                rewardedMulti.loadAd();
                break;
            case R.id.multi_rewarded_show_button:
                rewardedMulti.showAd();
                break;
            case R.id.normal_rewarded_load_button:
                rewardedNormal.loadAd();
                break;
            case R.id.normal_rewarded_show_button:
                rewardedMulti.showAd();
                break;
            default:
        }
    }
}
