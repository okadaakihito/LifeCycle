package com.example.lifecycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MypageFragment extends Fragment {

    WebView myWebView;

    @Override
    public void onSaveInstanceState(Bundle outState) {
       super.onSaveInstanceState(outState);
       myWebView.saveState(outState);
       Log.w("///////", "onSaveInstanceState");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
       super.onActivityCreated(savedInstanceState);
       myWebView.restoreState(savedInstanceState);
       Log.w("///////", "onActivityCreated");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	View view = inflater.inflate(R.layout.fragment_mypage, container, false);
    	myWebView = (WebView) view.findViewById(R.id.webview);

    	Log.w("///////", "onCreateView");

    	if( savedInstanceState != null ) {
        	Log.w("///////", savedInstanceState.toString());

    		myWebView.restoreState(savedInstanceState);
    	} else {

        	String MypageUrl = getString(R.string.mypage_url);

    		myWebView.setWebViewClient(new WebViewClient());

    		myWebView.loadUrl(MypageUrl);

    	}

    	return view;
    }

    /**
     * タブ内でFragment変更.

    private void changeView() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        //fragmentTransaction.replace(R.id.fragment, new Tab2Child2Fragment());
        fragmentTransaction.commit();
    }*/
}
