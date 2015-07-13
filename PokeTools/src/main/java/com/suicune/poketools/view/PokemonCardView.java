package com.suicune.poketools.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
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
import com.suicune.poketools.model.Ability;
import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.Nature;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.Stats;
import com.suicune.poketools.model.factories.PokemonFactory;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import static android.widget.AdapterView.OnItemClickListener;
import static com.suicune.poketools.model.Stats.Stat;

public class PokemonCardView extends CardView {
	public PokemonCardHolder cardHolder;

	AutoCompleteTextView nameAutoCompleteView;
	TextView nameHeaderView;
	EditText levelView;
	View cardDetailsView;
	AbilityView abilityView;
	NatureView natureView;
	AttacksView attacksView;
	StatsView statsView;

	boolean isShown = true;
	int level = Pokemon.DEFAULT_LEVEL;

	public Pokemon pokemon;
	boolean areAttacksEnabled = true;
	boolean areModsEnabled = true;

	public PokemonCardView(Context context) {
		super(context);
	}

	public PokemonCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PokemonCardView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setup(PokemonCardHolder holder) {
		this.cardHolder = holder;
		setupSubViews();
	}

	public void setupSubViews() {
		nameHeaderView = (TextView) findViewById(R.id.pokemon_name_title);
		cardDetailsView = findViewById(R.id.pokemon_card_details);
		nameAutoCompleteView = (AutoCompleteTextView) cardDetailsView.findViewById(R.id.name);
		levelView = (EditText) cardDetailsView.findViewById(R.id.level);
		abilityView = (AbilityView) cardDetailsView.findViewById(R.id.ability);
		natureView = (NatureView) cardDetailsView.findViewById(R.id.nature);
		statsView = (StatsView) cardDetailsView.findViewById(R.id.pokemon_stats_view);
		attacksView = (AttacksView) cardDetailsView.findViewById(R.id.attacks);

		prepareHeaderView();
		prepareNameAutoComplete(nameAutoCompleteView);
		prepareLevelView();
		prepareAbilitySpinner();
		prepareNatureView();
		prepareStatsView();
		prepareAttackViews();

		if (hasAValidPokemon()) {
			showPokemonInfo();
		}
	}

	private void prepareHeaderView() {
		nameHeaderView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				cardDetailsView.setVisibility((isShown) ? View.GONE : View.VISIBLE);
				isShown = !isShown;
			}
		});
	}

	private void prepareNameAutoComplete(AutoCompleteTextView view) {
		final String[] objects = getContext().getResources().getStringArray(R.array.pokemon_names);
		final List<String> names = Pokemon.parseAllNames(objects);
		ArrayAdapter<String> adapter =
				new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line,
						names);
		view.setAdapter(adapter);
		view.setThreshold(1);
		view.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
				switch (actionId) {
					case EditorInfo.IME_ACTION_DONE:
					case EditorInfo.IME_ACTION_GO:
					case EditorInfo.IME_NULL:
						selectPokemon(textView.getText().toString());

						InputMethodManager ime = (InputMethodManager) getContext()
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						ime.hideSoftInputFromWindow(textView.getWindowToken(), 0);
						return true;
					default:
						return false;
				}
			}
		});
		view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				selectPokemon(((TextView) view).getText().toString());
			}
		});
	}

	private void selectPokemon(String name) {
		try {
			setPokemon(PokemonFactory.createPokemon(getContext(), 6, name, level));
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
		if (hasAValidPokemon()) {
			updatePokemonData();
			showPokemonInfo();
			cardHolder.updatePokemon(pokemon);
		}
	}

	private void updatePokemonData() {
		if (natureView.nature() != null) {
			this.pokemon.setNature(natureView.nature());
		}
	}

	public void showPokemonInfo() {
		updateAttacks();
		displayAbility();
		displayName();
		displayNature();
		displayStats();
		displayLevel();
	}

	private void updateAttacks() {
		if (areAttacksEnabled && hasAValidPokemon()) {
			attacksView.setAttacks(pokemon.attackList());
			attacksView.setCurrentAttacks(pokemon.currentAttacks());
		}
	}

	private void displayAbility() {
		abilityView.setAsCurrent(this.pokemon.currentAbility());
	}

	private void displayName() {
		if (!nameHeaderView.getText().equals(pokemon.nickname())) {
			nameHeaderView.setText(pokemon.nickname());
			nameAutoCompleteView.setText(pokemon.name());
		}
	}

	private void displayNature() {
		natureView.setNature(pokemon.nature());
	}

	private void displayStats() {
		statsView.setStats(pokemon.stats());
	}

	private void displayLevel() {
		if (Integer.parseInt(levelView.getText().toString()) != pokemon.level()) {
			levelView.setText(Integer.toString(pokemon.level()));
		}
	}

	private void prepareLevelView() {
		levelView.addTextChangedListener(new TextWatcher() {
			@Override public void beforeTextChanged(CharSequence cs, int i, int i2, int i3) {
			}

			@Override public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			}

			@Override public void afterTextChanged(Editable editable) {
				setLevel(editable);
			}
		});
	}

	private void setLevel(Editable editable) {
		try {
			level = Integer.parseInt(editable.toString());
			if (hasAValidPokemon() && level != pokemon.level() &&
				level >= Pokemon.MIN_LEVEL && level <= Pokemon.MAX_LEVEL) {
				pokemon.setLevel(level);
				displayStats();
				cardHolder.updatePokemon(pokemon);
			}
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
	}

	private void prepareAbilitySpinner() {
		abilityView.setup(new AbilityView.OnAbilitySelectedListener() {
			@Override public void onAbilitySelected(Ability ability) {
				selectNewAbility(ability);
			}
		});
		if (hasAValidPokemon()) {
			abilityView.setSelection(pokemon.currentAbility().id());
		}
	}

	private void selectNewAbility(Ability ability) {
		if (hasAValidPokemon()) {
			pokemon.setAbility(ability);
			displayAbility();
			cardHolder.updatePokemon(pokemon);
		}
	}

	private void prepareNatureView() {
		natureView.setup(new NatureView.OnNatureSelectedListener() {
			@Override public void onNatureSelected(Nature nature) {
				selectNewNature(nature);
			}
		});
		if (hasAValidPokemon()) {
			displayNature();
			displayStats();
		}
	}

	private void selectNewNature(Nature nature) {
		statsView.setNature(nature);
		if (hasAValidPokemon()) {
			pokemon.setNature(nature);
			displayNature();
			displayStats();
			cardHolder.updatePokemon(pokemon);
		}
	}

	private void prepareStatsView() {
		statsView.setup(new StatsView.OnStatsChangedListener() {
			@Override public void onStatChanged(Stats.StatType type, Stat stat, int newValue) {
				if(hasAValidPokemon()) {
					pokemon.updateStat(type, stat, newValue);
				}
			}
		});

		if (hasAValidPokemon()) {
			displayStats();
		}
	}

	private void prepareAttackViews() {
		attacksView.setup(new AttacksView.OnAttacksChangedListener() {
			@Override public void onAttackChanged(int index, Attack attack) {
				pokemon.addAttack(attack, index);
				cardHolder.updatePokemon(pokemon);
			}
		});
		updateAttacks();
	}

	public boolean hasAValidPokemon() {
		return pokemon != null;
	}

	public void saveState(Bundle outState, String index) {
		if (hasAValidPokemon()) {
			outState.putBundle(index, pokemon.save());
		}
	}

	public void enableAttacks() {
		areAttacksEnabled = true;
		if (attacksView != null) {
			attacksView.setVisibility(VISIBLE);
		}
		updateAttacks();
	}

	public void disableAttacks() {
		areAttacksEnabled = false;
		if (attacksView != null) {
			attacksView.setVisibility(View.GONE);
		}
	}

	public void enableMods() {
		areModsEnabled = true;
		if (statsView != null) {
			statsView.enableMods();
		}
	}

	public void disableMods() {
		areModsEnabled = false;
		if (statsView != null) {
			statsView.disableMods();
		}

	}
}
