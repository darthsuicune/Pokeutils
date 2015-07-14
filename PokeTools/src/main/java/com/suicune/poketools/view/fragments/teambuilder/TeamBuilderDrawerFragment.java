package com.suicune.poketools.view.fragments.teambuilder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.PokemonTeam;
import com.suicune.poketools.view.fragments.NavigationDrawerFragment;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class TeamBuilderDrawerFragment extends NavigationDrawerFragment {

	private static final String PREF_SELECTED_POSITION = "selected_navigation_drawer_position";

	private TeamBuilderDrawerCallbacks listener;


	private ListView drawerListView;

	private ListAdapter adapter;

	private int currentSelectedPosition = 0;

	private String[] names = new String[7];

	public TeamBuilderDrawerFragment() {
	}

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentSelectedPosition = prefs.getInt(PREF_SELECTED_POSITION, 0);
		selectItem(currentSelectedPosition);
	}

	@Override public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Indicate that this fragment would like to influence the set of actions in the action bar.
		setHasOptionsMenu(true);
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
									   Bundle savedInstanceState) {
		drawerListView = (ListView) inflater
				.inflate(R.layout.fragment_team_builder_navigation_drawer, container, false);
		drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectItem(position);
			}
		});
		names = getActivity().getResources().getStringArray(R.array.team_builder_drawer_defaults);
		adapter = new PokemonTeamAdapter(getActivity());
		drawerListView.setAdapter(adapter);
		drawerListView.setItemChecked(currentSelectedPosition, true);
		return drawerListView;
	}

	private void selectItem(int position) {
		if (currentSelectedPosition == position) {
			closeDrawer();
			return;
		}
		if (drawerListView != null) {
			drawerListView.setItemChecked(position, true);
		}
		currentSelectedPosition = position;
		closeDrawer();
		if (listener != null) {
			if (position == 0) {
				listener.onMainScreenSelected();
			} else {
				listener.onPokemonSelected(position);
			}
		}
		prefs.edit().putInt(PREF_SELECTED_POSITION, currentSelectedPosition).apply();
	}

	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (TeamBuilderDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("Activity must implement TeamBuilderDrawerCallbacks.");
		}
	}

	@Override public void onDetach() {
		super.onDetach();
		listener = null;
	}

	public void onTeamChanged(PokemonTeam team) {
		names[0] = team.getName();
		adapter = new PokemonTeamAdapter(getActivity());
		drawerListView.setAdapter(adapter);
	}

	public void onMemberChanged(int position, Pokemon pokemon) {
		names[position] = pokemon.nickname();
		adapter = new PokemonTeamAdapter(getActivity());
		drawerListView.setAdapter(adapter);
	}


	public interface TeamBuilderDrawerCallbacks {
		void onPokemonSelected(int position);

		void onMainScreenSelected();
	}

	private class PokemonTeamAdapter extends ArrayAdapter<String> {
		public PokemonTeamAdapter(Context context) {
			super(context, android.R.layout.simple_list_item_activated_1, android.R.id.text1,
					names);
		}
	}
}
