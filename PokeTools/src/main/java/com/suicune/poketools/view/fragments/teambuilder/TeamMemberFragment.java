package com.suicune.poketools.view.fragments.teambuilder;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.factories.PokemonFactory;

import org.json.JSONException;

import java.io.IOException;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link com.suicune.poketools.view.fragments.teambuilder.TeamMemberFragment.OnTeamMemberChangedListener}
 * interface.
 */
public class TeamMemberFragment extends Fragment {
	private static final int DEFAULT_LEVEL = 50;
	private static final String ARG_POSITION = "position";

	private OnTeamMemberChangedListener mCallbacks;
	private AutoCompleteTextView mNameTextView;

	private Pokemon mPokemon;

	private int mPosition;

	public static TeamMemberFragment newInstance(int position) {
		TeamMemberFragment fragment = new TeamMemberFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_POSITION, position);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public TeamMemberFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (OnTeamMemberChangedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("Activity must implement OnTeamMemberChangedListener.");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mPosition = getArguments().getInt(ARG_POSITION);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_team_builder_member, container, false);
		mNameTextView = (AutoCompleteTextView) view
				.findViewById(R.id.team_builder_member_name_autocomplete);
		prepareAutoComplete();
		return view;
	}

	private void prepareAutoComplete() {
		String[] objects = {"asdf1", "asdf2", "asdf3"};
		ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, objects);
		mNameTextView.setAdapter(adapter);
		mNameTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override public void onItemClick(AdapterView<?> adapterView, View view, int position,
											  long id) {
				selectPokemon(position);
			}
		});
	}

	private void selectPokemon(int position) {
		try {
			mPokemon = PokemonFactory.createPokemon(getActivity(), 6, position, 0, DEFAULT_LEVEL);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void notifyChange() {
		mCallbacks.onTeamMemberChanged(mPosition, mPokemon);
	}

	public interface OnTeamMemberChangedListener {
		public void onTeamMemberChanged(int position, Pokemon pokemon);
	}
}
