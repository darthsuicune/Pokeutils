package com.suicune.poketools.view;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Ability;
import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.Nature;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.factories.PokemonFactory;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.AdapterView.OnItemClickListener;
import static android.widget.AdapterView.OnItemSelectedListener;
import static com.suicune.poketools.model.Stats.Stat;

public class PokemonCardView extends CardView {
	public PokemonCardHolder cardHolder;

	Map<Stat, TextView> baseStatsViews = new HashMap<>();
	Map<Stat, EditText> ivsView = new HashMap<>();
	Map<Stat, EditText> evsView = new HashMap<>();
	Map<Stat, EditText> statsView = new HashMap<>();
	Map<Integer, Spinner> attackViews = new HashMap<>();
	Map<Stat, Spinner> statModifiersView = new HashMap<>();

	AutoCompleteTextView nameAutoCompleteView;
	TextView nameView;
	EditText levelView;
	View cardDetailsView;
	AbilityView abilityView;
	NatureView natureView;
	AttacksView attacksView;

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
		cardDetailsView = findViewById(R.id.pokemon_card_details);
		levelView = (EditText) findViewById(R.id.level);
		nameView = (TextView) findViewById(R.id.pokemon_name_title);
		nameAutoCompleteView = (AutoCompleteTextView) findViewById(R.id.name);
		abilityView = (AbilityView) findViewById(R.id.ability);
		natureView = (NatureView) findViewById(R.id.nature);
		attacksView = (AttacksView) findViewById(R.id.attacks);

		prepareHeaderView();
		prepareNameAutoComplete(nameAutoCompleteView);
		prepareLevelView();
		prepareBaseStatsViews();
		prepareAbilitySpinner();
		prepareNatureView();
		prepareStatsView(ivsView, findViewById(R.id.pokemon_ivs), R.string.ivs);
		prepareStatsView(evsView, findViewById(R.id.pokemon_evs), R.string.evs);
		prepareStatsView(statsView, findViewById(R.id.pokemon_stats), R.string.values);
		prepareStatModifiersView(statModifiersView, findViewById(R.id.pokemon_stat_modifiers));
		prepareAttackViews();

		showPokemonInfo();
	}

	private void prepareHeaderView() {
		nameView.setOnClickListener(new OnClickListener() {
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
			@Override public boolean onEditorAction(TextView textView, int actionId,
													KeyEvent keyEvent) {
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
			@Override public void onItemClick(AdapterView<?> adapterView, View view, int position,
											  long id) {
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
		if (natureView.nature() != null) {
			this.pokemon.setNature(natureView.nature());
		}
		abilityView.setAsCurrent(this.pokemon.currentAbility());
		for (Stat stat : statModifiersView.keySet()) {
			pokemon.setStatModifier(stat,
					Integer.parseInt((String) statModifiersView.get(stat).getSelectedItem()));
		}
		setAttacks();
		setCurrentAttacks();
		showPokemonInfo();
		cardHolder.updatePokemon(pokemon);
	}

	private void setAttacks() {
		if (areAttacksEnabled && hasAValidPokemon()) {
			attacksView.setAttacks(pokemon.attackList());
		}
	}

	private void setCurrentAttacks() {
		if (areAttacksEnabled && hasAValidPokemon() && pokemon.currentAttacks().size() > 0) {
			attacksView.setCurrentAttacks(pokemon.currentAttacks());
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
				updateStats();
				cardHolder.updatePokemon(pokemon);
			}
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
	}

	private void prepareStatModifiersView(Map<Stat, Spinner> viewSet, View v) {
		viewSet.put(Stat.ATTACK, (Spinner) v.findViewById(R.id.attack_modifier));
		viewSet.put(Stat.DEFENSE, (Spinner) v.findViewById(R.id.defense_modifier));
		viewSet.put(Stat.SPECIAL_ATTACK, (Spinner) v.findViewById(R.id.special_attack_modifier));
		viewSet.put(Stat.SPECIAL_DEFENSE, (Spinner) v.findViewById(R.id.special_defense_modifier));
		viewSet.put(Stat.SPEED, (Spinner) v.findViewById(R.id.speed_modifier));
		for (final Stat stat : viewSet.keySet()) {
			viewSet.get(stat).setAdapter(
					new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
							getContext().getResources().getStringArray(R.array.stat_modifiers)));
			viewSet.get(stat).setSelection(6);
			viewSet.get(stat).setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override public void onItemSelected(AdapterView<?> adapterView, View view, int i,
													 long l) {
					if (hasAValidPokemon()) {
						pokemon.setStatModifier(stat,
								Integer.parseInt((String) adapterView.getItemAtPosition(i)));
						cardHolder.updatePokemon(pokemon);
					}
				}

				@Override public void onNothingSelected(AdapterView<?> adapterView) {
				}
			});
		}

	}

	private void prepareStatsView(Map<Stat, EditText> statViews, View view, int tagId) {
		((TextView) view.findViewById(R.id.label)).setText(tagId);
		statViews.put(Stat.HP, (EditText) view.findViewById(R.id.hp));
		statViews.put(Stat.ATTACK, (EditText) view.findViewById(R.id.attack));
		statViews.put(Stat.DEFENSE, (EditText) view.findViewById(R.id.defense));
		statViews.put(Stat.SPECIAL_ATTACK, (EditText) view.findViewById(R.id.special_attack));
		statViews.put(Stat.SPECIAL_DEFENSE, (EditText) view.findViewById(R.id.special_defense));
		statViews.put(Stat.SPEED, (EditText) view.findViewById(R.id.speed));
		for (Stat stat : Stat.values(6)) {
			statViews.get(stat).addTextChangedListener(new StatChangedListener(stat, tagId));
		}
	}

	private void prepareBaseStatsViews() {
		baseStatsViews.put(Stat.HP, (TextView) findViewById(R.id.hp));
		baseStatsViews.put(Stat.ATTACK, (TextView) findViewById(R.id.attack));
		baseStatsViews.put(Stat.DEFENSE, (TextView) findViewById(R.id.defense));
		baseStatsViews.put(Stat.SPECIAL_ATTACK, (TextView) findViewById(R.id.special_attack));
		baseStatsViews.put(Stat.SPECIAL_DEFENSE, (TextView) findViewById(R.id.special_defense));
		baseStatsViews.put(Stat.SPEED, (TextView) findViewById(R.id.speed));
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
			showPokemonInfo();
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
			natureView.setNature(pokemon.nature());
		}
	}

	private void selectNewNature(Nature nature) {
		if (hasAValidPokemon()) {
			pokemon.setNature(nature);
			showPokemonInfo();
			cardHolder.updatePokemon(pokemon);
		}
		for (Stat stat : Stat.values(6)) {
			if (stat.equals(nature.increasedStat())) {
				baseStatsViews.get(stat).setBackgroundColor(Color.GREEN);
			} else if (stat.equals(nature.decreasedStat())) {
				baseStatsViews.get(stat).setBackgroundColor(Color.RED);
			} else {
				baseStatsViews.get(stat).setBackgroundColor(Color.TRANSPARENT);
			}
		}
	}

	private void prepareAttackViews() {
		attacksView.setup(new AttacksView.OnAttacksChangedListener() {
			@Override public void onAttackChanged(int index, Attack attack) {
				pokemon.addAttack(attack, index);
				cardHolder.updatePokemon(pokemon);
			}
		});
		setAttacks();
	}

	public void showPokemonInfo() {
		if (hasAValidPokemon()) {
			if (!nameView.getText().equals(pokemon.nickname())) {
				nameView.setText(pokemon.nickname());
				nameAutoCompleteView.setText(pokemon.name());
			}
			if (!levelView.getText().toString().equals(Integer.toString(pokemon.level()))) {
				levelView.setText("" + pokemon.level());
			}
			updateStats();
		}
	}

	public void updateStats() {
		for (Stat stat : Stat.values(6)) {
			baseStatsViews.get(stat).setText("" + pokemon.baseStats().get(stat));
			evsView.get(stat).setText("" + pokemon.evs().get(stat));
			ivsView.get(stat).setText("" + pokemon.ivs().get(stat));
			statsView.get(stat).setText("" + pokemon.currentStats().get(stat));
		}
	}

	private void updateStatValues() {
		for (Stat stat : Stat.values(6)) {
			statsView.get(stat).setText("" + pokemon.currentStats().get(stat));
		}
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
		if(attacksView != null) {
			attacksView.setVisibility(VISIBLE);
		}
		setAttacks();
	}

	public void disableAttacks() {
		areAttacksEnabled = false;
		if (attacksView != null) {
			attacksView.setVisibility(View.GONE);
		}
	}

	public void enableMods() {
		areModsEnabled = true;
		for(Spinner mod : statModifiersView.values()) {
			mod.setVisibility(VISIBLE);
		}
	}

	public void disableMods() {
		areModsEnabled = false;
		for (Spinner mod : statModifiersView.values()) {
			mod.setVisibility(GONE);
		}

	}

	private class StatChangedListener implements TextWatcher {
		final Stat stat;
		final int tagId;

		public StatChangedListener(Stat stat, int tagId) {
			this.stat = stat;
			this.tagId = tagId;
		}

		@Override public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
		}

		@Override public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
		}

		@Override public void afterTextChanged(Editable editable) {
			int value;
			try {
				value = Integer.parseInt(editable.toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return;
			}
			switch (tagId) {
				case R.string.ivs:
					pokemon.setIv(stat, value);
					updateStatValues();
					break;
				case R.string.evs:
					pokemon.setEv(stat, value);
					updateStatValues();
					break;
				case R.string.values:
					pokemon.setValue(stat, value);
					break;
			}
			cardHolder.updatePokemon(pokemon);
		}
	}
}
