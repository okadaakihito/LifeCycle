package com.example.lifecycle;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements View.OnClickListener {

		private Button button;

	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        Log.v("LifeCycle", "onCreate");
	        setContentView(R.layout.activity_main);

	        button = (Button)findViewById(R.id.button);
	        button.setOnClickListener(this);
	    }

	    @Override
	    public void onClick(View v) {
	        switch(v.getId()) {
		    case R.id.button:
	                Toast.makeText(MainActivity.this, "Hello! Android!!", Toast.LENGTH_LONG).show();

	    	        Intent intent = new Intent(MainActivity.this,
	    	        		NextActivity.class);
	    	        startActivity(intent);
			break;
	        }
	    }

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			super.onCreateOptionsMenu(menu);
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.optionsmenu, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			super.onOptionsItemSelected(item);
			switch(item.getItemId()){
			case R.id.menuitem1:
				showMessage("Hello! Item1");
				return true;
			case R.id.menuitem2:
				showMessage("Hello! Item2");
				return true;
			case R.id.menuitem3:
				showMessage("Hello! Item3");
				return true;
			}
			return false;
		}

		protected void showMessage(String msg){
			Toast.makeText(
				this,
				msg, Toast.LENGTH_SHORT).show();
		}

	    @Override
	    public void onStart(){
	        super.onStart();
	        Log.v("LifeCycle", "onStart");
	    }

	    @Override
	    public void onResume(){
	        super.onResume();
	        Log.v("LifeCycle", "onResume");
	    }

	    @Override
	    public void onPause(){
	        super.onPause();
	        Log.v("LifeCycle", "onPause");
	    }

	    @Override
	    public void onRestart(){
	        super.onRestart();
	        Log.v("LifeCycle", "onRestart");
	    }

	    @Override
	    public void onStop(){
	        super.onStop();
	        Log.v("LifeCycle", "onStop");
	    }

	    @Override
	    public void onDestroy(){
	        super.onDestroy();
	        Log.v("LifeCycle", "onDestroy");
	    }

}
