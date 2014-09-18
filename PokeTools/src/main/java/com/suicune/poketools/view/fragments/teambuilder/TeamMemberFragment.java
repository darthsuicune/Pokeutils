package com.suicune.poketools.view.fragments.teambuilder;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suicune.poketools.R;

/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnSomethingSomethingListener}
 * interface.
 */
public class TeamMemberFragment extends Fragment  {


    public static TeamMemberFragment newInstance(int position) {
        TeamMemberFragment fragment = new TeamMemberFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TeamMemberFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Create Adapter to display your content
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teambuilderfragment, container, false);

        return view;
    }

	public interface OnSomethingSomethingListener {

	}
}
