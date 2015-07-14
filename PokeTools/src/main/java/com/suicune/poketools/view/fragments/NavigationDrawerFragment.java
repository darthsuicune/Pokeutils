package com.suicune.poketools.view.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.suicune.poketools.R;

public class NavigationDrawerFragment extends Fragment {

	// Per the design guidelines, you should show the drawer on launch until the user manually
	// expands it. This shared preference tracks this.
	static final String PREF_USER_LEARNED_DRAWER = "main_navigation_drawer_learned";

	View fragmentContainerView;
	DrawerLayout drawerLayout;
	ActionBarDrawerToggle drawerToggle;

	public SharedPreferences prefs;
	boolean userLearnedDrawer;

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Read in the flag indicating whether or not the user has demonstrated awareness of the
		// drawer. See PREF_USER_LEARNED_DRAWER for details.
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		userLearnedDrawer = prefs.getBoolean(PREF_USER_LEARNED_DRAWER, false);
	}

	/**
	 * Users of this fragment must call this method to set up the navigation drawer interactions.
	 *
	 * @param fragmentView The view of this fragment in its activity's layout.
	 * @param layout The DrawerLayout containing this fragment's UI.
	 */
	public void setUp(DrawerLayout layout, View fragmentView, Toolbar toolbar) {
		this.drawerLayout = layout;
		fragmentContainerView = fragmentView;

		// set a custom shadow that overlays the main content when the drawer opens
		this.drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the navigation drawer and the action bar app icon.
		drawerToggle = new ActionBarDrawerToggle(getActivity(), this.drawerLayout, toolbar,
				R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
			@Override public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (isAdded()) {
					getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
				}
			}

			@Override public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (isAdded()) {
					getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
					if (!userLearnedDrawer) {
						userLearnedDrawer = true;
						prefs.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
					}
				}
			}
		};
//
		// If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
		// per the navigation drawer design guidelines.
		if (!userLearnedDrawer) {
			this.drawerLayout.openDrawer(fragmentContainerView);
		}

		// Defer code dependent on restoration of previous instance state.
		this.drawerLayout.post(new Runnable() {
			@Override public void run() {
				drawerToggle.syncState();
			}
		});

		this.drawerLayout.setDrawerListener(drawerToggle);
	}

	@Override public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Forward the new configuration the drawer toggle component.
		drawerToggle.onConfigurationChanged(newConfig);
	}

	public boolean isDrawerOpen() {
		return drawerLayout != null && drawerLayout.isDrawerOpen(fragmentContainerView);
	}

	public void closeDrawer() {
		if (drawerLayout != null) {
			drawerLayout.closeDrawer(fragmentContainerView);
		}
	}
	@Override public boolean onOptionsItemSelected(MenuItem item) {
		return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
	}

}
