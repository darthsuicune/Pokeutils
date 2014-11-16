package com.suicune.poketools.view;

import android.content.Context;
import android.graphics.Color;
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

import static com.suicune.poketools.model.Stats.Stat;

/**
 * Created by lapuente on 07.11.14.
 */
public class PokemonCardView extends CardView {
	private final Context mContext;
	public PokemonCardHolder cardHolder;
	private Map<Stat, TextView> mBaseStatsViews = new HashMap<>();
	private Map<Stat, EditText> mIvsView = new HashMap<>();
	private Map<Stat, EditText> mEvsView = new HashMap<>();
	private Map<Stat, EditText> mStatsView = new HashMap<>();
	private Map<Integer, Spinner> mAttackViews = new HashMap<>();
	private AutoCompleteTextView mNameAutoCompleteView;
	private TextView mNameView;
	private EditText mLevelView;
	private Spinner mAbilityView;
	private View mCardDetailsView;
	public Pokemon mPokemon;
	private Nature selectedNature;
	private boolean isShown = true;
	private AttackAdapter mAttackAdapter;
	private Ability selectedAbility;

	public PokemonCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	public void setup(PokemonCardHolder holder, Pokemon pokemon) {
		this.cardHolder = holder;
		this.mPokemon = pokemon;
		setupSubViews();
		if (mPokemon != null) {
			setupAttacks();
		}
	}

	public boolean isReady() {
		return mPokemon != null;
	}

	public void setupSubViews() {
		mCardDetailsView = findViewById(R.id.pokemon_card_details);
		mLevelView = (EditText) findViewById(R.id.level);
		mNameView = (TextView) findViewById(R.id.pokemon_name_title);
		mNameAutoCompleteView = (AutoCompleteTextView) findViewById(R.id.name);
		mAbilityView = (Spinner) findViewById(R.id.ability);

		prepareHeaderView();
		prepareLevelView();
		prepareBaseStatsViews();
		prepareAbilitySpinner();
		prepareStatsView(mIvsView, findViewById(R.id.pokemon_ivs), R.string.ivs);
		prepareStatsView(mEvsView, findViewById(R.id.pokemon_evs), R.string.evs);
		prepareStatsView(mStatsView, findViewById(R.id.pokemon_stats), R.string.values);
		prepareNatureView((Spinner) findViewById(R.id.nature));
		prepareAttackViews();
		prepareNameAutoComplete(mNameAutoCompleteView);
		if (mPokemon != null) {
			showPokemonInfo();
		}
	}

	private void prepareHeaderView() {
		mNameView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				mCardDetailsView.setVisibility((isShown) ? View.GONE : View.VISIBLE);
				isShown = !isShown;
			}
		});
	}

	private void prepareLevelView() {
		mLevelView.addTextChangedListener(new TextWatcher() {
			@Override public void beforeTextChanged(CharSequence cs, int i, int i2, int i3) {
			}

			@Override public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			}

			@Override public void afterTextChanged(Editable editable) {
				if (mPokemon == null) {
					return;
				}
				try {
					int newLevel = Integer.parseInt(editable.toString());
					if (newLevel != mPokemon.level() && newLevel >= Pokemon.MIN_LEVEL &&
						newLevel <= Pokemon.MAX_LEVEL) {
						mPokemon.setLevel(newLevel);
						updateStats();
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
		mBaseStatsViews.put(Stat.HP, (TextView) findViewById(R.id.hp));
		mBaseStatsViews.put(Stat.ATTACK, (TextView) findViewById(R.id.attack));
		mBaseStatsViews.put(Stat.DEFENSE, (TextView) findViewById(R.id.defense));
		mBaseStatsViews.put(Stat.SPECIAL_ATTACK, (TextView) findViewById(R.id.special_attack));
		mBaseStatsViews.put(Stat.SPECIAL_DEFENSE, (TextView) findViewById(R.id.special_defense));
		mBaseStatsViews.put(Stat.SPEED, (TextView) findViewById(R.id.speed));
	}

	private void prepareAbilitySpinner() {
		List<String> abilityList = Arrays.asList(getResources().getStringArray(R.array.abilities));
		mAbilityView.setAdapter(
				new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
						abilityList));
		mAbilityView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override public void onItemSelected(AdapterView<?> adapterView, View view, int i,
												 long l) {
				setAbility(i);
			}

			@Override public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
		if(mPokemon != null) {
			mAbilityView.setSelection(mPokemon.currentAbility().id());
		}

	}

	private void prepareNatureView(Spinner natureView) {
		natureView.setAdapter(
				new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
						getResources().getStringArray(R.array.natures)));
		natureView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override public void onItemSelected(AdapterView<?> adapterView, View view, int i,
												 long l) {
				setNature(i);
			}

			@Override public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
		if (mPokemon != null) {
			natureView.setSelection(mPokemon.nature().save());
		}
	}

	private void setNature(int index) {
		Nature nature = NatureFactory.get(6, index);
		if (selectedNature != nature) {
			selectedNature = nature;
		}
		if (mPokemon != null) {
			mPokemon.setNature(selectedNature);
			showPokemonInfo();
		}
		for (Stat stat : Stat.values(6)) {
			if (stat.equals(nature.increasedStat())) {
				mBaseStatsViews.get(stat).setBackgroundColor(Color.GREEN);
			} else if (stat.equals(nature.decreasedStat())) {
				mBaseStatsViews.get(stat).setBackgroundColor(Color.RED);
			} else {
				mBaseStatsViews.get(stat).setBackgroundColor(Color.TRANSPARENT);
			}
		}
	}

	private void setAbility(int index) {
		Ability ability = AbilityFactory.createAbility(getContext(), 6, index);
		if (selectedAbility != ability) {
			selectedAbility = ability;
		}
		if (mPokemon != null) {
			mPokemon.setAbility(selectedAbility);
		}
	}

	private void prepareAttackViews() {
		mAttackViews.put(1, (Spinner) findViewById(R.id.attack_1));
		mAttackViews.put(2, (Spinner) findViewById(R.id.attack_2));
		mAttackViews.put(3, (Spinner) findViewById(R.id.attack_3));
		mAttackViews.put(4, (Spinner) findViewById(R.id.attack_4));
		mAttackAdapter = new AttackAdapter(getContext(), new ArrayList<Attack>());
		for (int i = 1; i <= 4; i++) {
			Spinner attackSpinner = mAttackViews.get(i);
			attackSpinner.setAdapter(mAttackAdapter);
			final int element = i;
			attackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override public void onItemSelected(AdapterView<?> adapterView, View view,
													 int position, long id) {
					mPokemon.addAttack((Attack) adapterView.getAdapter().getItem(position),
							element);
				}

				@Override public void onNothingSelected(AdapterView<?> adapterView) {
				}
			});
		}
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
				break;
			}
		}
		try {
			setPokemon(dexNumber, form, Pokemon.DEFAULT_LEVEL);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}

	public void showPokemonInfo() {
		if (!mNameView.getText().equals(mPokemon.nickname())) {
			mNameView.setText(mPokemon.nickname());
			mNameAutoCompleteView.setText(mPokemon.name());
		}
		if (!mLevelView.getText().toString().equals(Integer.toString(mPokemon.level()))) {
			mLevelView.setText("" + mPokemon.level());
		}
		updateStats();
	}

	public void updateStats() {
		for (Stat stat : Stat.values(6)) {
			mBaseStatsViews.get(stat).setText("" + mPokemon.baseStats().get(stat));
			mEvsView.get(stat).setText("" + mPokemon.evs().get(stat));
			mIvsView.get(stat).setText("" + mPokemon.ivs().get(stat));
		}
		updateStatValues();
	}

	private void updateStatValues() {
		for (Stat stat : Stat.values(6)) {
			mStatsView.get(stat).setText("" + mPokemon.currentStats().get(stat));
		}
	}

	public void setPokemon(int dexNumber, int form, int level) throws IOException, JSONException {
		setPokemon(PokemonFactory.createPokemon(mContext, 6, dexNumber, form, level));
	}

	public void setPokemon(Pokemon pokemon) {
		mPokemon = pokemon;
		if (selectedNature != null) {
			mPokemon.setNature(selectedNature);
		}
		mPokemon.setAbility(mPokemon.ability1());
		mAbilityView.setSelection(AbilityFactory.find(getContext(), mPokemon.currentAbility()));
		setupAttacks();
		showPokemonInfo();
		cardHolder.updatePokemon(pokemon);
	}

	private void setupAttacks() {
		mAttackAdapter.addAll(mPokemon.attackList());
		if (mPokemon.currentAttacks().size() > 0) {
			for (Integer index : mPokemon.currentAttacks().keySet()) {
				Spinner spinner = mAttackViews.get(index);
				spinner.setSelection(mAttackAdapter.find(mPokemon.currentAttacks().get(index)));
			}
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
		final Stat mStat;
		final int mTagId;

		public StatChangedListener(Stat stat, int tagId) {
			this.mStat = stat;
			this.mTagId = tagId;
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
			switch (mTagId) {
				case R.string.ivs:
					mPokemon.setIv(mStat, value);
					updateStatValues();
					break;
				case R.string.evs:
					mPokemon.setEv(mStat, value);
					updateStatValues();
					break;
				case R.string.values:
					mPokemon.setValue(mStat, value);
					break;
			}
		}
	}
}
