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
import com.suicune.poketools.model.factories.AbilityFactory;
import com.suicune.poketools.model.factories.NatureFactory;
import com.suicune.poketools.model.factories.PokemonFactory;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.AdapterView.*;
import static com.suicune.poketools.model.Stats.Stat;

/**
 * Created by lapuente on 07.11.14.
 */
public class PokemonCardView extends CardView {
	public PokemonCardHolder cardHolder;

	private Map<Stat, TextView> baseStatsViews = new HashMap<>();
	private Map<Stat, EditText> ivsView = new HashMap<>();
	private Map<Stat, EditText> evsView = new HashMap<>();
	private Map<Stat, EditText> statsView = new HashMap<>();
	private Map<Integer, Spinner> attackViews = new HashMap<>();
	private Map<Stat, Spinner> statModifiersView = new HashMap<>();

	private AutoCompleteTextView nameAutoCompleteView;
	private TextView nameView;
	private EditText levelView;
	private Spinner abilityView;
	private View cardDetailsView;

	private Nature selectedNature;
	private boolean isShown = true;
	private AttackAdapter attackAdapter;
	private Ability selectedAbility;
	int level = Pokemon.DEFAULT_LEVEL;

	public Pokemon pokemon;


	public PokemonCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setup(PokemonCardHolder holder, Pokemon pokemon) {
		this.cardHolder = holder;
		setupSubViews();
		this.pokemon = pokemon;
		if (this.hasAValidPokemon()) {
			setupAttacks();
			cardHolder.updatePokemon(pokemon);
		}
	}

	public void setupSubViews() {
		cardDetailsView = findViewById(R.id.pokemon_card_details);
		levelView = (EditText) findViewById(R.id.level);
		nameView = (TextView) findViewById(R.id.pokemon_name_title);
		nameAutoCompleteView = (AutoCompleteTextView) findViewById(R.id.name);
		abilityView = (Spinner) findViewById(R.id.ability);

		prepareHeaderView();
		prepareLevelView();
		prepareBaseStatsViews();
		prepareAbilitySpinner();
		prepareStatsView(ivsView, findViewById(R.id.pokemon_ivs), R.string.ivs);
		prepareStatsView(evsView, findViewById(R.id.pokemon_evs), R.string.evs);
		prepareStatsView(statsView, findViewById(R.id.pokemon_stats), R.string.values);
		prepareStatModifiersView(statModifiersView, findViewById(R.id.pokemon_stat_modifiers));
		prepareNatureView((Spinner) findViewById(R.id.nature));
		prepareAttackViews();
		prepareNameAutoComplete(nameAutoCompleteView);

		showPokemonInfo();
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

				@Override public void onNothingSelected(AdapterView<?> adapterView) {}
			});
		}

	}

	private void prepareHeaderView() {
		nameView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				cardDetailsView.setVisibility((isShown) ? View.GONE : View.VISIBLE);
				isShown = !isShown;
			}
		});
	}

	private void prepareLevelView() {
		levelView.addTextChangedListener(new TextWatcher() {
			@Override public void beforeTextChanged(CharSequence cs, int i, int i2, int i3) {
			}

			@Override public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			}

			@Override public void afterTextChanged(Editable editable) {
				try {
					level = Integer.parseInt(editable.toString());
					if (hasAValidPokemon() && level != pokemon.level() &&
						level >= Pokemon.MIN_LEVEL &&
						level <= Pokemon.MAX_LEVEL) {
						pokemon.setLevel(level);
						updateStats();
						cardHolder.updatePokemon(pokemon);
					}
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					//Nothing else to do here, leave everything as is.
				}
			}
		});
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
		List<String> abilityList = Arrays.asList(getResources().getStringArray(R.array.abilities));
		abilityView.setAdapter(
				new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
						abilityList));
		abilityView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override public void onItemSelected(AdapterView<?> adapterView, View view, int i,
												 long l) {
				setAbility(i);
			}

			@Override public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
		if (hasAValidPokemon()) {
			abilityView.setSelection(pokemon.currentAbility().id());
		}

	}

	private void prepareNatureView(Spinner natureView) {
		natureView.setAdapter(
				new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
						getResources().getStringArray(R.array.natures)));
		natureView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override public void onItemSelected(AdapterView<?> adapterView, View view, int i,
												 long l) {
				setNature(i);
			}

			@Override public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
		if (hasAValidPokemon()) {
			natureView.setSelection(pokemon.nature().save());
		}
	}

	private void setNature(int index) {
		Nature nature = NatureFactory.get(6, index);
		if (selectedNature != nature) {
			selectedNature = nature;
		}
		if (hasAValidPokemon()) {
			pokemon.setNature(selectedNature);
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

	private void setAbility(int index) {
		Ability ability = AbilityFactory.createAbility(getContext(), 6, index);
		if (selectedAbility != ability) {
			selectedAbility = ability;
		}
		if (hasAValidPokemon()) {
			pokemon.setAbility(selectedAbility);
			cardHolder.updatePokemon(pokemon);
		}
	}

	private void prepareAttackViews() {
		attackViews.put(1, (Spinner) findViewById(R.id.attack_1));
		attackViews.put(2, (Spinner) findViewById(R.id.attack_2));
		attackViews.put(3, (Spinner) findViewById(R.id.attack_3));
		attackViews.put(4, (Spinner) findViewById(R.id.attack_4));
		attackAdapter = new AttackAdapter(getContext(), new ArrayList<Attack>());
		for (int i = 1; i <= 4; i++) {
			Spinner attackSpinner = attackViews.get(i);
			attackSpinner.setAdapter(attackAdapter);
			final int element = i;
			attackSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override public void onItemSelected(AdapterView<?> adapterView, View view,
													 int position, long id) {
					Attack attack = (Attack) adapterView.getAdapter().getItem(position);
					pokemon.addAttack(attack, element);
					view.setBackgroundColor(getResources().getColor(attack.color()));
					cardHolder.updatePokemon(pokemon);
				}

				@Override public void onNothingSelected(AdapterView<?> adapterView) {
				}
			});
		}
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
		final String[] objects = getContext().getResources().getStringArray(R.array.pokemon_names);
		int dexNumber = 0;
		int form = 0;
		for (int i = 0; i < objects.length; i++) {
			if (objects[i].contains(name)) {
				dexNumber = i;
				form = Pokemon.getForm(objects[i], name);
				break;
			}
		}
		try {
			setPokemon(dexNumber, form);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
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
		}
		updateStatValues();
	}

	private void updateStatValues() {
		for (Stat stat : Stat.values(6)) {
			statsView.get(stat).setText("" + pokemon.currentStats().get(stat));
		}
	}

	public void setPokemon(int dexNumber, int form) throws IOException, JSONException {
		setPokemon(PokemonFactory.createPokemon(getContext(), 6, dexNumber, form, level));
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
		if (selectedNature != null) {
			this.pokemon.setNature(selectedNature);
		}
		this.pokemon.setAbility(this.pokemon.ability1());
		abilityView.setSelection(AbilityFactory.find(getContext(), this.pokemon.currentAbility()));
		for (Stat stat : statModifiersView.keySet()) {
			pokemon.setStatModifier(stat,
					Integer.parseInt((String) statModifiersView.get(stat).getSelectedItem()));
		}
		setupAttacks();
		showPokemonInfo();
		cardHolder.updatePokemon(pokemon);
	}

	private void setupAttacks() {
		attackAdapter.clear();
		attackAdapter.addAll(pokemon.attackList());
		if (pokemon.currentAttacks().size() > 0) {
			for (Integer index : pokemon.currentAttacks().keySet()) {
				Spinner spinner = attackViews.get(index);
				spinner.setSelection(attackAdapter.find(pokemon.currentAttacks().get(index)));
			}
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

	private class AttackAdapter extends ArrayAdapter<Attack> {
		List<Attack> mAttacks;

		public AttackAdapter(Context context, List<Attack> objects) {
			super(context, android.R.layout.simple_spinner_dropdown_item, objects);
			mAttacks = objects;
		}

		public int find(Attack attack) {
			int i = 0;
			for (Attack element : mAttacks) {
				if (attack.equals(element)) {
					return i;
				} else {
					i++;
				}
			}
			return 0;
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
