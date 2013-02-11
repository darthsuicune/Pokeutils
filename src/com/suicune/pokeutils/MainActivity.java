package com.suicune.pokeutils;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.suicune.pokeutils.compat.CompatTab;
import com.suicune.pokeutils.compat.CompatTabListener;
import com.suicune.pokeutils.compat.TabCompatActivity;
import com.suicune.pokeutils.compat.TabHelper;

public class MainActivity extends TabCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void setTabs(){
		addTab(null, null, 0);
	}
	
	private void addTab(String name, TabChangeListener listener, int textResId) {
		TabHelper tabHelper = getTabHelper();
		
		CompatTab tab = tabHelper.newTab(name);
		tab.setText(textResId);
		tab.setTabListener(listener);
		
		tabHelper.addTab(tab);
		
	}
	
	private class TabChangeListener implements CompatTabListener{

		@Override
		public void onTabUnselected(CompatTab mLastTab, FragmentTransaction ft) {
			
		}

		@Override
		public void onTabSelected(CompatTab newTab, FragmentTransaction ft) {
			
		}

		@Override
		public void onTabReselected(CompatTab newTab, FragmentTransaction ft) {
		}
	}
}
