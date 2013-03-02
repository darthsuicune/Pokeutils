package com.suicune.pokeutils.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
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

import com.suicune.pokeutils.EditTeamPokemonActivity;
import com.suicune.pokeutils.R;
import com.suicune.pokeutils.TeamPokemon;

/**
 * This Fragment will be automatically opened when the user selects to open a
 * file with a team definition from storage.
 */
public class TeamBuilderFragment extends Fragment implements OnClickListener {
	private static final int REQUEST_EDIT_POKEMON = 1;

	private boolean isLandscape;

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
		return inflater.inflate(R.layout.team_builder, container, false);
	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		isLandscape = getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		setViews();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_EDIT_POKEMON:
			switch (resultCode) {
			case Activity.RESULT_OK:
				break;
			}
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_team_builder, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.load_team:
			loadTeam();
			return true;
		case R.id.save_team:
			saveTeam();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setViews() {
		// mPokemonButton1 = (Button) getActivity().findViewById(
		// R.id.team_builder_pokemon_1);
		// mPokemonButton2 = (Button) getActivity().findViewById(
		// R.id.team_builder_pokemon_2);
		// mPokemonButton3 = (Button) getActivity().findViewById(
		// R.id.team_builder_pokemon_3);
		// mPokemonButton4 = (Button) getActivity().findViewById(
		// R.id.team_builder_pokemon_4);
		// mPokemonButton5 = (Button) getActivity().findViewById(
		// R.id.team_builder_pokemon_5);
		// mPokemonButton6 = (Button) getActivity().findViewById(
		// R.id.team_builder_pokemon_6);
		//
		// mPokemonButton1.setOnClickListener(this);
		// mPokemonButton2.setOnClickListener(this);
		// mPokemonButton3.setOnClickListener(this);
		// mPokemonButton4.setOnClickListener(this);
		// mPokemonButton5.setOnClickListener(this);
		// mPokemonButton6.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.team_builder_pokemon_1:
			editPokemon(1);
			break;
		case R.id.team_builder_pokemon_2:
			editPokemon(2);
			break;
		case R.id.team_builder_pokemon_3:
			editPokemon(3);
			break;
		case R.id.team_builder_pokemon_4:
			editPokemon(4);
			break;
		case R.id.team_builder_pokemon_5:
			editPokemon(5);
			break;
		case R.id.team_builder_pokemon_6:
			editPokemon(6);
			break;
		}
	}

	private void editPokemon(int number) {
		if (isLandscape) {

		} else {
			Intent intent = new Intent(getActivity(),
					EditTeamPokemonActivity.class);
			startActivityForResult(intent, REQUEST_EDIT_POKEMON);
		}
	}

	protected void setPokemonIcon(int number, int pokemon) {
		switch (number) {
		case 1:
			mPokemonButton1.setCompoundDrawablesWithIntrinsicBounds(0, pokemon,
					0, 0);
			break;
		case 2:
			mPokemonButton2.setCompoundDrawablesWithIntrinsicBounds(0, pokemon,
					0, 0);
			break;
		case 3:
			mPokemonButton3.setCompoundDrawablesWithIntrinsicBounds(0, pokemon,
					0, 0);
			break;
		case 4:
			mPokemonButton4.setCompoundDrawablesWithIntrinsicBounds(0, pokemon,
					0, 0);
			break;
		case 5:
			mPokemonButton5.setCompoundDrawablesWithIntrinsicBounds(0, pokemon,
					0, 0);
			break;
		case 6:
			mPokemonButton6.setCompoundDrawablesWithIntrinsicBounds(0, pokemon,
					0, 0);
			break;
		}
	}

	protected void setPokemonName(int number) {
		switch (number) {
		case 1:
			mPokemonButton1.setText(mPokemon1.mNickname);
			break;
		case 2:
			mPokemonButton2.setText(mPokemon2.mNickname);
			break;
		case 3:
			mPokemonButton3.setText(mPokemon3.mNickname);
			break;
		case 4:
			mPokemonButton4.setText(mPokemon4.mNickname);
			break;
		case 5:
			mPokemonButton5.setText(mPokemon5.mNickname);
			break;
		case 6:
			mPokemonButton6.setText(mPokemon6.mNickname);
			break;
		}
	}

	private void loadTeam() {

	}

	private void saveTeam() {

	}
}
