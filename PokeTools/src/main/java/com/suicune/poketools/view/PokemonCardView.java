package com.suicune.poketools.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.Stats;
import com.suicune.poketools.model.factories.PokemonFactory;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lapuente on 07.11.14.
 */
public class PokemonCardView {
	private final Context mContext;
	public final PokemonCardHolder cardHolder;
	public final CardView view;
	private HashMap<Stats.Stat, TextView> mBaseStatsViews;
	public Pokemon mPokemon;

	public PokemonCardView(Context context, PokemonCardHolder holder, CardView view) {
		this.mContext = context;
		this.cardHolder = holder;
		this.view = view;
		setupView(view);
	}

	public void setupView(CardView view) {
		EditText levelView = (EditText) view.findViewById(R.id.level);
		AutoCompleteTextView nameTextView = (AutoCompleteTextView) view
				.findViewById(R.id.name);
		prepareNameAutoComplete(nameTextView);
		prepareBaseStatsViews(view);
	}

	private void prepareNameAutoComplete(AutoCompleteTextView view) {
		final String[] objects = mContext.getResources().getStringArray(R.array.pokemon_names);
		final List<String> names = Pokemon.parseAllNames(objects);
		ArrayAdapter<String> adapter =
				new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, names);
		view.setAdapter(adapter);
		view.setThreshold(1);
		view.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override public boolean onEditorAction(TextView textView, int actionId,
													KeyEvent keyEvent) {
				switch (actionId) {
					case EditorInfo.IME_ACTION_DONE:
					case EditorInfo.IME_ACTION_GO:
					case EditorInfo.IME_NULL:
						selectPokemon(textView.getText().toString());

						InputMethodManager ime = (InputMethodManager) mContext
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						ime.hideSoftInputFromWindow(textView.getWindowToken(), 0);
						return true;
					default:
						return false;
				}
			}
		});
		view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override public void onItemClick(AdapterView<?> adapterView, View view, int position,
											  long id) {
				selectPokemon(((TextView) view).getText().toString());
			}
		});
	}

	private void selectPokemon(String name) {
		final String[] objects = mContext.getResources().getStringArray(R.array.pokemon_names);
		int dexNumber = 0;
		int form = 0;
		for (int i = 0; i < objects.length; i++) {
			if (objects[i].contains(name)) {
				dexNumber = i;
				form = Pokemon.getForm(objects[i], name);
			}
		}
		try {
			mPokemon = PokemonFactory
					.createPokemon(mContext, 6, dexNumber, form, Pokemon.DEFAULT_LEVEL);
			updatePokemon();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}

	private void updatePokemon() {
		for (Stats.Stat stat : mPokemon.stats().base().keySet()) {
			mBaseStatsViews.get(stat).setText("" + mPokemon.stats().base().get(stat));
		}
	}

	private void prepareBaseStatsViews(View v) {
		mBaseStatsViews = new HashMap<>();
		mBaseStatsViews
				.put(Stats.Stat.HP, (TextView) v.findViewById(R.id.team_member_base_stats_hp));
		mBaseStatsViews.put(Stats.Stat.ATTACK,
				(TextView) v.findViewById(R.id.team_member_base_stats_attack));
		mBaseStatsViews.put(Stats.Stat.DEFENSE,
				(TextView) v.findViewById(R.id.team_member_base_stats_defense));
		mBaseStatsViews.put(Stats.Stat.SPECIAL_ATTACK,
				(TextView) v.findViewById(R.id.team_member_base_stats_special_attack));
		mBaseStatsViews.put(Stats.Stat.SPECIAL_DEFENSE,
				(TextView) v.findViewById(R.id.team_member_base_stats_special_defense));
		mBaseStatsViews.put(Stats.Stat.SPEED,
				(TextView) v.findViewById(R.id.team_member_base_stats_speed));
	}
}
