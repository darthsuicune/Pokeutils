package com.suicune.poketools.view.fragments.teambuilder;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Pokemon;

/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the {@link com.suicune.poketools.view.fragments.teambuilder.TeamMemberFragment.OnTeamMemberChangedListener}
 * interface.
 */
public class TeamMemberFragment extends Fragment  {

	private static final String ARG_POSITION = "position";

	private OnTeamMemberChangedListener mCallbacks;

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
			throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
		}
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		if(getArguments() != null) {
			mPosition = getArguments().getInt(ARG_POSITION);
		}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teambuilderfragment, container, false);

        return view;
    }

	public void notifyChange() {
		mCallbacks.onTeamMemberChanged(mPokemon);
	}

	public interface OnTeamMemberChangedListener {
		public void onTeamMemberChanged(Pokemon pokemon);
	}
}
