package com.suicune.poketools.view.fragments.teambuilder;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.suicune.poketools.R;

public class TeamBuilderFragment extends Fragment {
	OnTeamBuilderInteractionListener listener;
	RecyclerView teamList;

	public static TeamBuilderFragment newInstance() {
		return new TeamBuilderFragment();
	}

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (OnTeamBuilderInteractionListener) activity;
	}

	@Override public void onDetach() {
		super.onDetach();
		listener = null;
	}

	@Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
												 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_team_builder, container, false);
		prepareViews(v);
		return v;
	}

	private void prepareViews(View v) {
		teamList = (RecyclerView) v.findViewById(R.id.team_builder_current_teams);
	}

	@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.team_builder, menu);
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.team_builder_create_new:
				listener.onNewTeamRequested();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public interface OnTeamBuilderInteractionListener {
		void onNewTeamRequested();
		void onTeamSelected();
	}
}
