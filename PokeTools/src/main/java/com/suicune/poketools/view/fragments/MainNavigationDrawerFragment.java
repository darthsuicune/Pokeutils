package com.suicune.poketools.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suicune.poketools.R;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class MainNavigationDrawerFragment extends NavigationDrawerFragment {

	private static final String STATE_SELECTED_SECTION = "main_navigation_drawer_position";

	int currentSelectedSection;

	MainNavigationDrawerCallbacks listener;
	View drawerView;
	TextView teamBuilderView;
	TextView damageCalcView;
	TextView ivBreederCalcView;
	TextView ivCalcView;
	TextView pokedexView;

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentSelectedSection = prefs.getInt(STATE_SELECTED_SECTION, R.id.iv_calc);
		selectItem(currentSelectedSection);
	}

	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (MainNavigationDrawerCallbacks) activity;
	}

	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_SECTION, currentSelectedSection);
	}

	@Override public void onDetach() {
		super.onDetach();
		listener = null;
	}

	@Override public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Indicate that this fragment would like to influence the set of actions in the action bar.
		setHasOptionsMenu(true);
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
									   Bundle savedInstanceState) {
		drawerView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
		prepareViews(drawerView);
		return drawerView;
	}

	private void prepareViews(View drawerView) {
		teamBuilderView = (TextView) drawerView.findViewById(R.id.team_builder);
		damageCalcView = (TextView) drawerView.findViewById(R.id.damage_calc);
		ivBreederCalcView = (TextView) drawerView.findViewById(R.id.iv_breeder_calc);
		ivCalcView = (TextView) drawerView.findViewById(R.id.iv_calc);
		pokedexView = (TextView) drawerView.findViewById(R.id.pokedex);

		teamBuilderView.setOnClickListener(new OnSectionSelectedListener());
		damageCalcView.setOnClickListener(new OnSectionSelectedListener());
		ivBreederCalcView.setOnClickListener(new OnSectionSelectedListener());
		ivCalcView.setOnClickListener(new OnSectionSelectedListener());
		pokedexView.setOnClickListener(new OnSectionSelectedListener());
	}

	private void selectItem(int section) {
		currentSelectedSection = section;
		prefs.edit().putInt(STATE_SELECTED_SECTION, currentSelectedSection).apply();
		closeDrawer();
		notifySelectedSection(section);
	}

	private void notifySelectedSection(int position) {
		if (listener != null) {
			switch (position) {
				case R.id.team_builder:
					listener.onTeamBuilderRequested();
					break;
				case R.id.damage_calc:
					listener.onDamageCalcRequested();
					break;
				case R.id.iv_breeder_calc:
					listener.onIvBreederRequested();
					break;
				case R.id.iv_calc:
					listener.onIvCalcRequested();
					break;
				case R.id.pokedex:
					listener.onPokedexRequested();
			}
		}
	}

	private class OnSectionSelectedListener implements View.OnClickListener {
		@Override public void onClick(View view) {
			selectItem(view.getId());
		}
	}

	public interface MainNavigationDrawerCallbacks {
		void onTeamBuilderRequested();

		void onDamageCalcRequested();

		void onIvBreederRequested();

		void onIvCalcRequested();

		void onPokedexRequested();
	}
}
