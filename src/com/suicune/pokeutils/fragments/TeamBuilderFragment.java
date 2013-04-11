package com.suicune.pokeutils.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.TeamPokemon;
import com.suicune.pokeutils.activities.EditTeamPokemonActivity;
import com.suicune.pokeutils.tools.FileTools;

/**
 * This Fragment will be automatically opened when the user selects to open a
 * file with a team definition from storage.
 */
public class TeamBuilderFragment extends Fragment implements OnClickListener {
	private static final int REQUEST_EDIT_POKEMON = 1;
	private static final int REQUEST_LOAD_TEAM = 2;

	public static final String ARGUMENT_TEAM = "team";

	private boolean isTwoPane;
	
	private Button mPokemonButton1;
	private Button mPokemonButton2;
	private Button mPokemonButton3;
	private Button mPokemonButton4;
	private Button mPokemonButton5;
	private Button mPokemonButton6;

	private TeamPokemon mPokemon1;
	private TeamPokemon mPokemon2;
	private TeamPokemon mPokemon3;
	private TeamPokemon mPokemon4;
	private TeamPokemon mPokemon5;
	private TeamPokemon mPokemon6;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		return inflater.inflate(R.layout.team_builder_basic, container, false);
	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		isTwoPane = getActivity().findViewById(
				R.id.team_builder_pokemon_fragment_holder) != null;
		setViews();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_EDIT_POKEMON:
				Bundle team = data.getBundleExtra(ARGUMENT_TEAM);
				if (team.containsKey(FileTools.TAG_POKEMON_1)) {
					mPokemon1 = new TeamPokemon(
							team.getBundle(FileTools.TAG_POKEMON_1));
					setPokemonName(1);
				}
				if (team.containsKey(FileTools.TAG_POKEMON_2)) {
					mPokemon2 = new TeamPokemon(
							team.getBundle(FileTools.TAG_POKEMON_2));
					setPokemonName(2);
				}
				if (team.containsKey(FileTools.TAG_POKEMON_3)) {
					mPokemon3 = new TeamPokemon(
							team.getBundle(FileTools.TAG_POKEMON_3));
					setPokemonName(3);
				}
				if (team.containsKey(FileTools.TAG_POKEMON_4)) {
					mPokemon4 = new TeamPokemon(
							team.getBundle(FileTools.TAG_POKEMON_4));
					setPokemonName(4);
				}
				if (team.containsKey(FileTools.TAG_POKEMON_5)) {
					mPokemon5 = new TeamPokemon(
							team.getBundle(FileTools.TAG_POKEMON_5));
					setPokemonName(5);
				}
				if (team.containsKey(FileTools.TAG_POKEMON_6)) {
					mPokemon6 = new TeamPokemon(
							team.getBundle(FileTools.TAG_POKEMON_6));
					setPokemonName(6);
				}
				break;
			case REQUEST_LOAD_TEAM:
				loadTeam(data.getData());
			default:
				// Activities such as "Settings" could end up here. Placeholder
				// if we want to do any change.
				break;
			}
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_team_builder, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.team_builder_view_team_stats:
			viewTeamStats();
			return true;
		case R.id.load_team:
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("application/xml");
			startActivityForResult(intent, REQUEST_LOAD_TEAM);
			return true;
		case R.id.save_team:
			saveTeam();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setViews() {
		mPokemonButton1 = (Button) getActivity().findViewById(
				R.id.team_builder_pokemon_1);
		if (mPokemonButton1 == null) {
			return;
		}
		mPokemonButton2 = (Button) getActivity().findViewById(
				R.id.team_builder_pokemon_2);
		mPokemonButton3 = (Button) getActivity().findViewById(
				R.id.team_builder_pokemon_3);
		mPokemonButton4 = (Button) getActivity().findViewById(
				R.id.team_builder_pokemon_4);
		mPokemonButton5 = (Button) getActivity().findViewById(
				R.id.team_builder_pokemon_5);
		mPokemonButton6 = (Button) getActivity().findViewById(
				R.id.team_builder_pokemon_6);

		mPokemonButton1.setOnClickListener(this);
		mPokemonButton2.setOnClickListener(this);
		mPokemonButton3.setOnClickListener(this);
		mPokemonButton4.setOnClickListener(this);
		mPokemonButton5.setOnClickListener(this);
		mPokemonButton6.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.team_builder_pokemon_1:
			editTeam(0);
			break;
		case R.id.team_builder_pokemon_2:
			editTeam(1);
			break;
		case R.id.team_builder_pokemon_3:
			editTeam(2);
			break;
		case R.id.team_builder_pokemon_4:
			editTeam(3);
			break;
		case R.id.team_builder_pokemon_5:
			editTeam(4);
			break;
		case R.id.team_builder_pokemon_6:
			editTeam(5);
			break;
		default:
			break;
		}
	}

	private void editTeam(int pokemonNumber) {
		Bundle team = createTeamBundle();
		if (isTwoPane) {

		} else {
			Intent intent = new Intent(getActivity(),
					EditTeamPokemonActivity.class);
			intent.putExtra(EditTeamPokemonActivity.EXTRA_TEAM_NUMBER,
					pokemonNumber);
			if (team != null) {
				intent.putExtra(EditTeamPokemonActivity.EXTRA_TEAM, team);
			}
			startActivityForResult(intent, REQUEST_EDIT_POKEMON);
		}
	}

	private void setPokemonIcon(int number, int pokemon) {
		switch (number) {
		case 1:
			mPokemonButton1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			break;
		case 2:
			mPokemonButton2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			break;
		case 3:
			mPokemonButton3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			break;
		case 4:
			mPokemonButton4.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			break;
		case 5:
			mPokemonButton5.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			break;
		case 6:
			mPokemonButton6.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			break;
		}
	}

	private void setPokemonName(int number) {
		switch (number) {
		case 1:
			mPokemonButton1.setText(mPokemon1.mName + " ("
					+ mPokemon1.mNickname + " - " + getString(R.string.lead)
					+ ")");
			break;
		case 2:
			mPokemonButton2.setText(mPokemon2.mName + " ("
					+ mPokemon2.mNickname + ")");
			break;
		case 3:
			mPokemonButton3.setText(mPokemon3.mName + " ("
					+ mPokemon3.mNickname + ")");
			break;
		case 4:
			mPokemonButton4.setText(mPokemon4.mName + " ("
					+ mPokemon4.mNickname + ")");
			break;
		case 5:
			mPokemonButton5.setText(mPokemon5.mName + " ("
					+ mPokemon5.mNickname + ")");
			break;
		case 6:
			mPokemonButton6.setText(mPokemon6.mName + " ("
					+ mPokemon6.mNickname + ")");
			break;
		}
	}

	private void viewTeamStats() {
		// Handle view stats
		if (isTwoPane) {

		} else {
			Toast.makeText(getActivity(), "EEEOOOO", Toast.LENGTH_LONG).show();
		}
	}

	private void loadTeam(Uri data) {
		FileTools.importTeamFromXml(data);
	}

	private void saveTeam() {
		Bundle team = createTeamBundle();
		if (team != null) {
			FileTools.exportTeamToXml(team);
		}
	}

	private Bundle createTeamBundle() {
		if (mPokemon1 == null && mPokemon2 == null && mPokemon3 == null
				&& mPokemon4 == null && mPokemon5 == null && mPokemon6 == null) {
			return null;
		}

		Bundle team = new Bundle();
		if (mPokemon1 != null) {
			Bundle pokemon1 = new Bundle();
			mPokemon1.saveStatus(pokemon1);
			team.putBundle(FileTools.TAG_POKEMON_1, pokemon1);
		}

		if (mPokemon2 != null) {
			Bundle pokemon2 = new Bundle();
			mPokemon2.saveStatus(pokemon2);
			team.putBundle(FileTools.TAG_POKEMON_2, pokemon2);
		}

		if (mPokemon3 != null) {
			Bundle pokemon3 = new Bundle();
			mPokemon3.saveStatus(pokemon3);
			team.putBundle(FileTools.TAG_POKEMON_3, pokemon3);
		}

		if (mPokemon4 != null) {
			Bundle pokemon4 = new Bundle();
			mPokemon4.saveStatus(pokemon4);
			team.putBundle(FileTools.TAG_POKEMON_4, pokemon4);
		}

		if (mPokemon5 != null) {
			Bundle pokemon5 = new Bundle();
			mPokemon5.saveStatus(pokemon5);
			team.putBundle(FileTools.TAG_POKEMON_5, pokemon5);
		}

		if (mPokemon6 != null) {
			Bundle pokemon6 = new Bundle();
			mPokemon6.saveStatus(pokemon6);
			team.putBundle(FileTools.TAG_POKEMON_6, pokemon6);
		}
		return team;
	}
}
