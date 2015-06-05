package com.suicune.poketools.view.fragments.teambuilder;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suicune.poketools.R;
import com.suicune.poketools.model.PokemonTeam;
import com.suicune.poketools.model.gen6.Gen6Team;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.suicune.poketools.view.fragments.teambuilder.TeamMainFragment.OnTeamEditedListener} interface
 * to handle interaction events.
 * Use the {@link TeamMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TeamMainFragment extends Fragment {
    private OnTeamEditedListener mListener;
	private Gen6Team mTeam;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TeamMainFragment.
     */
    public static TeamMainFragment newInstance() {
        return new TeamMainFragment();
    }
    public TeamMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_main, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTeamEditedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTeamEditedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTeamEditedListener {
		void onTeamChanged(PokemonTeam team);
    }

}
