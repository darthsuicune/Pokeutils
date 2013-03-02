package com.suicune.pokeutils.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.compat.ActionBarActivity;

public class EditTeamPokemonActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_edit_team_pokemon, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
