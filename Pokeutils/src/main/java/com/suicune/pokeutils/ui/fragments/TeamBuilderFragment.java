package com.suicune.pokeutils.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.app.TeamPokemon;
import com.suicune.pokeutils.ui.activities.EditTeamPokemonActivity;
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
				if (team.containsKey("0")) {
					mPokemon1 = new TeamPokemon(team.getBundle("0"));
					setPokemonName(1);
				}
				if (team.containsKey("1")) {
					mPokemon2 = new TeamPokemon(team.getBundle("1"));
					setPokemonName(2);
				}
				if (team.containsKey("2")) {
					mPokemon3 = new TeamPokemon(team.getBundle("2"));
					setPokemonName(3);
				}
				if (team.containsKey("3")) {
					mPokemon4 = new TeamPokemon(team.getBundle("3"));
					setPokemonName(4);
				}
				if (team.containsKey("4")) {
					mPokemon5 = new TeamPokemon(team.getBundle("4"));
					setPokemonName(5);
				}
				if (team.containsKey("5")) {
					mPokemon6 = new TeamPokemon(team.getBundle("5"));
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
			if (mPokemon1 != null) {
				Bundle pokemon = new Bundle();
				mPokemon1.saveStatus(pokemon);
				editPokemon(0, pokemon);
			} else {
				editPokemon(0, null);
			}
			break;
		case R.id.team_builder_pokemon_2:
			if (mPokemon2 != null) {
				Bundle pokemon = new Bundle();
				mPokemon2.saveStatus(pokemon);
				editPokemon(1, pokemon);
			} else {
				editPokemon(1, null);
			}
			break;
		case R.id.team_builder_pokemon_3:
			if (mPokemon3 != null) {
				Bundle pokemon = new Bundle();
				mPokemon3.saveStatus(pokemon);
				editPokemon(2, pokemon);
			} else {
				editPokemon(2, null);
			}
			break;
		case R.id.team_builder_pokemon_4:
			if (mPokemon4 != null) {
				Bundle pokemon = new Bundle();
				mPokemon4.saveStatus(pokemon);
				editPokemon(3, pokemon);
			} else {
				editPokemon(3, null);
			}
			break;
		case R.id.team_builder_pokemon_5:
			if (mPokemon5 != null) {
				Bundle pokemon = new Bundle();
				mPokemon5.saveStatus(pokemon);
				editPokemon(4, pokemon);
			} else {
				editPokemon(4, null);
			}
			break;
		case R.id.team_builder_pokemon_6:
			if (mPokemon6 != null) {
				Bundle pokemon = new Bundle();
				mPokemon6.saveStatus(pokemon);
				editPokemon(5, pokemon);
			} else {
				editPokemon(5, null);
			}
			break;
		default:
			break;
		}
	}

	private void editPokemon(int pokemonNumber, Bundle pokemon) {
		if (isTwoPane) {

		} else {
			Intent intent = new Intent(getActivity(),
					EditTeamPokemonActivity.class);
			intent.putExtra(EditTeamPokemonActivity.EXTRA_TEAM_NUMBER,
					pokemonNumber);
			if (pokemon != null) {
				intent.putExtra(EditTeamPokemonActivity.EXTRA_POKEMON, pokemon);
			}
			startActivityForResult(intent, REQUEST_EDIT_POKEMON);
		}
	}

	// protected void setPokemonIcon(int number, int pokemon) {
	// switch (number) {
	// case 1:
	// mPokemonButton1.setCompoundDrawablesWithIntrinsicBounds(0, pokemon,
	// 0, 0);
	// break;
	// case 2:
	// mPokemonButton2.setCompoundDrawablesWithIntrinsicBounds(0, pokemon,
	// 0, 0);
	// break;
	// case 3:
	// mPokemonButton3.setCompoundDrawablesWithIntrinsicBounds(0, pokemon,
	// 0, 0);
	// break;
	// case 4:
	// mPokemonButton4.setCompoundDrawablesWithIntrinsicBounds(0, pokemon,
	// 0, 0);
	// break;
	// case 5:
	// mPokemonButton5.setCompoundDrawablesWithIntrinsicBounds(0, pokemon,
	// 0, 0);
	// break;
	// case 6:
	// mPokemonButton6.setCompoundDrawablesWithIntrinsicBounds(0, pokemon,
	// 0, 0);
	// break;
	// }
	// }

	protected void setPokemonName(int number) {
		switch (number) {
		case 1:
			mPokemonButton1.setText(mPokemon1.mName + " (" + mPokemon1.mNickname
					+ " - " + getString(R.string.lead) + ")");
			break;
		case 2:
			mPokemonButton2.setText(mPokemon2.mName + " (" + mPokemon2.mNickname
					+ ")");
			break;
		case 3:
			mPokemonButton3.setText(mPokemon3.mName + " (" + mPokemon3.mNickname
					+ ")");
			break;
		case 4:
			mPokemonButton4.setText(mPokemon4.mName + " (" + mPokemon4.mNickname
					+ ")");
			break;
		case 5:
			mPokemonButton5.setText(mPokemon5.mName + " (" + mPokemon5.mNickname
					+ ")");
			break;
		case 6:
			mPokemonButton6.setText(mPokemon6.mName + " (" + mPokemon6.mNickname
					+ ")");
			break;
		}
	}

	private void viewTeamStats() {
		// Handle view stats
		if (isTwoPane) {

		} else {

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
		Bundle pokemon1 = new Bundle();
		if (mPokemon1 != null) {
			mPokemon1.saveStatus(pokemon1);
			team.putBundle(FileTools.TAG_POKEMON_1, pokemon1);
		}

		Bundle pokemon2 = new Bundle();
		if (mPokemon2 != null) {
			mPokemon2.saveStatus(pokemon2);
			team.putBundle(FileTools.TAG_POKEMON_2, pokemon2);
		}

		Bundle pokemon3 = new Bundle();
		if (mPokemon3 != null) {
			mPokemon3.saveStatus(pokemon3);
			team.putBundle(FileTools.TAG_POKEMON_3, pokemon3);
		}

		Bundle pokemon4 = new Bundle();
		if (mPokemon4 != null) {
			mPokemon4.saveStatus(pokemon4);
			team.putBundle(FileTools.TAG_POKEMON_4, pokemon4);
		}

		Bundle pokemon5 = new Bundle();
		if (mPokemon5 != null) {
			mPokemon5.saveStatus(pokemon5);
			team.putBundle(FileTools.TAG_POKEMON_5, pokemon5);
		}

		Bundle pokemon6 = new Bundle();
		if (mPokemon6 != null) {
			mPokemon6.saveStatus(pokemon6);
			team.putBundle(FileTools.TAG_POKEMON_6, pokemon6);
		}
		return team;
	}
}
