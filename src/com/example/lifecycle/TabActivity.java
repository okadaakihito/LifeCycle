package com.example.lifecycle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.Log;

public class TabActivity extends FragmentActivity implements TabHost.OnTabChangeListener {

	// tabにアイコンを設定するとき用
    //private int imageIds[] = { R.drawable.bt_tab_menu,
    //        R.drawable.bt_tab_menu, R.drawable.bt_tab_menu };
    // Tabの名前
    private Class<?> fragments[] = { ScanFragment.class, MypageFragment.class,
    		InformationFragment.class, OtherFragment.class };

    /** TabHost */
    private TabHost mTabHost;

    /** 直近で選択されているタブ情報 */
    private TabInfo mLastTabInfo;

    /** タブTagとタブ情報のMap */
    private Map<String, TabInfo> mMapTabInfo;

    private LayoutInflater layoutInflater;

    private final String TAG = "SampleQrActivity";
    private View view;
    private static final int MIN_PREVIEW_PIXELS = 470 * 320;
    private static final int MAX_PREVIEW_PIXELS = 1280 * 720;
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private Point mPreviewSize;
    private float mPreviewWidthRatio;
    private float mPreviewHeightRatio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tab);

    	initTabs();
    }

    @Override
    public void onBackPressed() {
        if (!getCurrentFragment().popBackStack()) {
            // タブ内FragmentのBackStackがない場合は終了
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTabHost = null;
    }

    /*
     * android:id/tabcontent のダミーコンテンツ
     */
    private static class DummyTabFactory implements TabContentFactory {

        /* Context */
        private final Context mContext;

        DummyTabFactory(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            return v;
        }
    }

    /**
     * タブ初期化
     */
    private void initTabs() {

    	String[] tabLabels = getResources().getStringArray(R.array.tab_labels);
    	String[] tabIds = getResources().getStringArray(R.array.tab_ids);

    	mTabHost = (TabHost) findViewById(android.R.id.tabhost);
    	mTabHost.setup();

    	layoutInflater = LayoutInflater.from(this);

        mMapTabInfo = new HashMap<String, TabInfo>();

        for (int i = 0; i < 4; i++) {
	        addTab(new TabInfo(tabIds[i], tabLabels[i], fragments[i].getName()), i);
        }

        // タブ変更時イベントハンドラ
        mTabHost.setOnTabChangedListener(this);

        // 初期タブ選択
        onTabChanged(tabIds[0]);
    }

    /**
     * タブ追加
     * @param tabInfo タブ情報
     */
    private void addTab(TabInfo tabInfo, int index) {

        mMapTabInfo.put(tabInfo.tag, tabInfo);

        Log.w("/////",tabInfo.tag);
        Log.w("/////",tabInfo.label);

        TabSpec tabSpec = mTabHost
                .newTabSpec(tabInfo.tag)
                .setIndicator(getTabItemView(tabInfo.label, index))
                .setContent(new DummyTabFactory(this));

        mTabHost.addTab(tabSpec);
    }

    /**
     * TabViewの設定
     */
    private View getTabItemView(String tabString, int index){

        View view = layoutInflater.inflate(R.layout.tab_item_view, null);
        TextView tabTextView = (TextView) view.findViewById(R.id.tab_textview);
        tabTextView.setText(tabString);

        return view;
    }

    @Override
    public void onTabChanged(String tabId) {

    	Log.w("//////", tabId);

        TabInfo newTab = mMapTabInfo.get(tabId);

        if (mLastTabInfo != newTab) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (mLastTabInfo != null) {
                fragmentTransaction.detach(mLastTabInfo.fragment);
            }

            if ( tabId != "scan" ) {
	            if (newTab.fragment == null) {
	                newTab.fragment = createTabRootFragment(newTab);
	                fragmentTransaction.add(R.id.realtabcontent, newTab.fragment);
	            } else {
	                fragmentTransaction.attach(newTab.fragment);
	            }

	            fragmentTransaction.commit();
            }

            mLastTabInfo = newTab;

        }
    }

    /**
     * カレントFragment取得.
     * @return TabRootFragment
     */
    private TabRootFragment getCurrentFragment() {
        return (TabRootFragment) getSupportFragmentManager().findFragmentById(R.id.realtabcontent);
    }

    /**
     * 各タブのルートとなるFragment生成
     * @param tabInfo タブ情報
     * @return Fragment
     */
    private Fragment createTabRootFragment(TabInfo tabInfo) {

        // ルートFragmentで初期表示するFragmentクラス名を引数にする
        Bundle args = new Bundle();
        args.putString("root", tabInfo.className);

        TabRootFragment fragment = new TabRootFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * タブ情報
     *
     */
    private class TabInfo {

        /** タグ */
        private String tag;
        /** タブラベル */
        private String label;
        /** タブ初期Fragmentクラス名 */
        private String className;
        /** ルートFragmentインスタンス */
        private Fragment fragment;

        /**
         * コンストラクタ
         * @param tag タグ
         * @param lable タブラベル
         * @param className タブ初期Fragmentクラス名
         */
        TabInfo(String tag, String lable, String className) {
            this.tag = tag;
            this.label = lable;
            this.className = className;
        }
    }
}
