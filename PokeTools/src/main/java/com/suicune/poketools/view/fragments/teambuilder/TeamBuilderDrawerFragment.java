package com.suicune.poketools.view.fragments.teambuilder;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.PokemonTeam;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class TeamBuilderDrawerFragment extends Fragment {

	/**
	 * Remember the position of the selected item.
	 */
	private static final String PREF_SELECTED_POSITION = "selected_navigation_drawer_position";

	/**
	 * Per the design guidelines, you should show the drawer on launch until the user manually
	 * expands it. This shared preference tracks this.
	 */
	private static final String PREF_USER_LEARNED_DRAWER = "team_builder_drawer_learned";

	/**
	 * A pointer to the current callbacks instance (the Activity).
	 */
	private TeamBuilderDrawerCallbacks mCallbacks;

	/**
	 * Helper component that ties the action bar to the navigation drawer.
	 */
	private ActionBarDrawerToggle mDrawerToggle;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerListView;
	private View mFragmentContainerView;

	private ListAdapter mAdapter;

	private int mCurrentSelectedPosition = 0;
	private boolean mUserLearnedDrawer;

	private SharedPreferences prefs;
	private String[] mNames = new String[7];

	public TeamBuilderDrawerFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Read in the flag indicating whether or not the user has demonstrated awareness of the
		// drawer. See PREF_USER_LEARNED_DRAWER for details.
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mUserLearnedDrawer = prefs.getBoolean(PREF_USER_LEARNED_DRAWER, false);

		mCurrentSelectedPosition = prefs.getInt(PREF_SELECTED_POSITION, 0);

		// Select either the default item (0) or the last selected item.
		selectItem(mCurrentSelectedPosition);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Indicate that this fragment would like to influence the set of actions in the action bar.
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mDrawerListView = (ListView) inflater
				.inflate(R.layout.fragment_team_builder_navigation_drawer, container, false);
		mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectItem(position);
			}
		});
		mNames = getActivity().getResources().getStringArray(R.array.team_builder_drawer_defaults);
		mAdapter = new PokemonTeamAdapter(getActivity());
		mDrawerListView.setAdapter(mAdapter);
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
		return mDrawerListView;
	}

	public boolean isDrawerOpen() {
		return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
	}

	/**
	 * Users of this fragment must call this method to set up the navigation drawer interactions.
	 *
	 * @param fragmentId   The android:id of this fragment in its activity's layout.
	 * @param drawerLayout The DrawerLayout containing this fragment's UI.
	 */
	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.team_builder_toolbar);
		// ActionBarDrawerToggle ties together the the proper interactions
		// between the navigation drawer and the action bar app icon.
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar,
				R.string.navigation_drawer_open, R.string.navigation_drawer_close //
		) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (!isAdded()) {
					return;
				}

				getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (!isAdded()) {
					return;
				}

				if (!mUserLearnedDrawer) {
					// The user manually opened the drawer; store this flag to prevent auto-showing
					// the navigation drawer automatically in the future.
					mUserLearnedDrawer = true;
					prefs.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
				}

				getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
			}
		};

		// If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
		// per the navigation drawer design guidelines.
		if (!mUserLearnedDrawer) {
			mDrawerLayout.openDrawer(mFragmentContainerView);
		}

		// Defer code dependent on restoration of previous instance state.
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	private void selectItem(int position) {
		if (mCurrentSelectedPosition == position) {
			if (mDrawerLayout != null) {
				mDrawerLayout.closeDrawer(mFragmentContainerView);
			}
			return;
		}
		if (mDrawerListView != null) {
			mDrawerListView.setItemChecked(position, true);
		}
		mCurrentSelectedPosition = position;
		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if (mCallbacks != null) {
			if (position == 0) {
				mCallbacks.onMainScreenSelected();
			} else {
				mCallbacks.onPokemonSelected(position);
			}
		}
		prefs.edit().putInt(PREF_SELECTED_POSITION, mCurrentSelectedPosition).apply();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (TeamBuilderDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("Activity must implement TeamBuilderDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Forward the new configuration the drawer toggle component.
		//mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// If the drawer is open, show the global app actions in the action bar. See also
		// showGlobal1ContextActionBar, which controls the top-left area of the action bar.
		if (mDrawerLayout != null && isDrawerOpen()) {
			inflater.inflate(R.menu.global, menu);
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}*/
		switch (item.getItemId()) {
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void onTeamChanged(PokemonTeam team) {
		mNames[0] = team.getName();
		mAdapter = new PokemonTeamAdapter(getActivity());
		mDrawerListView.setAdapter(mAdapter);
	}

	public void onMemberChanged(int position, Pokemon pokemon) {
		mNames[position] = pokemon.nickname();
		mAdapter = new PokemonTeamAdapter(getActivity());
		mDrawerListView.setAdapter(mAdapter);
	}

	/**
	 * Callbacks interface that all activities using this fragment must implement.
	 */
	public static interface TeamBuilderDrawerCallbacks {
		/**
		 * Called when an item in the navigation drawer is selected.
		 */
		void onPokemonSelected(int position);

		void onMainScreenSelected();
	}

	private class PokemonTeamAdapter extends ArrayAdapter<String> {
		public PokemonTeamAdapter(Context context) {
			super(context, android.R.layout.simple_list_item_activated_1, android.R.id.text1,
					mNames);
		}
	}
}
