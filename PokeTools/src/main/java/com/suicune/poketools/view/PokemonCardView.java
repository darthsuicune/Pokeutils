package com.suicune.poketools.view;

import android.content.Context;
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
import com.suicune.poketools.model.Nature;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.Stats;
import com.suicune.poketools.model.factories.PokemonFactory;
import com.suicune.poketools.model.gen6.Gen6Nature;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lapuente on 07.11.14.
 */
public class PokemonCardView extends CardView {
	private final Context mContext;
	public PokemonCardHolder cardHolder;
	private Map<Stats.Stat, TextView> mBaseStatsViews = new HashMap<>();
	private Map<Stats.Stat, EditText> mIvsView = new HashMap<>();
	private Map<Stats.Stat, EditText> mEvsView = new HashMap<>();
	private Map<Stats.Stat, EditText> mStatsView = new HashMap<>();
	private AutoCompleteTextView mNameAutoCompleteView;
	private TextView mNameView;
	private EditText mLevelView;
	private View mCardDetailsView;
	public Pokemon mPokemon;
	private Nature selectedNature;
	private boolean isShown = true;

	public PokemonCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	public void setup(PokemonCardHolder holder, Pokemon pokemon) {
		this.cardHolder = holder;
		this.mPokemon = pokemon;
		setupSubViews();
	}

	public boolean isReady() {
		return mPokemon != null;
	}

	public void setupSubViews() {
		mCardDetailsView = findViewById(R.id.pokemon_card_details);
		mLevelView = (EditText) findViewById(R.id.level);
		mNameView = (TextView) findViewById(R.id.pokemon_name_title);
		mNameAutoCompleteView = (AutoCompleteTextView) findViewById(R.id.name);

		prepareHeaderView();
		prepareLevelView();
		prepareBaseStatsViews();
		prepareStatsView(mIvsView, findViewById(R.id.pokemon_ivs), R.string.ivs);
		prepareStatsView(mEvsView, findViewById(R.id.pokemon_evs), R.string.evs);
		prepareStatsView(mStatsView, findViewById(R.id.pokemon_stats), R.string.values);
		prepareNatureView((Spinner)findViewById(R.id.nature));
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
						showPokemonInfo();
					}
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					//Nothing else to do here, leave everything as is.
				}
			}
		});
	}

	private void prepareStatsView(Map<Stats.Stat, EditText> statViews, View view, int tagId) {
		((TextView) view.findViewById(R.id.label)).setText(tagId);
		statViews.put(Stats.Stat.HP, (EditText) view.findViewById(R.id.hp));
		statViews.put(Stats.Stat.ATTACK, (EditText) view.findViewById(R.id.attack));
		statViews.put(Stats.Stat.DEFENSE, (EditText) view.findViewById(R.id.defense));
		statViews.put(Stats.Stat.SPECIAL_ATTACK, (EditText) view.findViewById(R.id.special_attack));
		statViews.put(Stats.Stat.SPECIAL_DEFENSE,
				(EditText) view.findViewById(R.id.special_defense));
		statViews.put(Stats.Stat.SPEED, (EditText) view.findViewById(R.id.speed));
	}

	private void prepareBaseStatsViews() {
		mBaseStatsViews.put(Stats.Stat.HP, (TextView) findViewById(R.id.team_member_base_stats_hp));
		mBaseStatsViews.put(Stats.Stat.ATTACK,
				(TextView) findViewById(R.id.team_member_base_stats_attack));
		mBaseStatsViews.put(Stats.Stat.DEFENSE,
				(TextView) findViewById(R.id.team_member_base_stats_defense));
		mBaseStatsViews.put(Stats.Stat.SPECIAL_ATTACK,
				(TextView) findViewById(R.id.team_member_base_stats_special_attack));
		mBaseStatsViews.put(Stats.Stat.SPECIAL_DEFENSE,
				(TextView) findViewById(R.id.team_member_base_stats_special_defense));
		mBaseStatsViews
				.put(Stats.Stat.SPEED, (TextView) findViewById(R.id.team_member_base_stats_speed));
	}

	private void prepareNatureView(Spinner natureView) {
		natureView.setAdapter(new ArrayAdapter<>(
				getContext(),
				android.R.layout.simple_spinner_dropdown_item,
				getResources().getStringArray(R.array.natures)
		));
		natureView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override public void onItemSelected(AdapterView<?> adapterView, View view, int i,
												 long l) {
				Nature nature = Gen6Nature.get(i);
				if(selectedNature != nature) {
					selectedNature = nature;
				}
				if (mPokemon != null) {
					mPokemon.setNature(selectedNature);
					showPokemonInfo();
				}
			}

			@Override public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
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
		mNameView.setText(mPokemon.nickname());
		mNameAutoCompleteView.setText(mPokemon.name());
		mLevelView.setText("" + mPokemon.level());
		for (Stats.Stat stat : mPokemon.baseStats().keySet()) {
			mBaseStatsViews.get(stat).setText("" + mPokemon.baseStats().get(stat));
			mEvsView.get(stat).setText("" + mPokemon.evs().get(stat));
			mIvsView.get(stat).setText("" + mPokemon.ivs().get(stat));
			mStatsView.get(stat).setText("" + mPokemon.currentStats().get(stat));
		}
	}

	public void setPokemon(int dexNumber, int form, int level) throws IOException, JSONException {
		setPokemon(PokemonFactory.createPokemon(mContext, 6, dexNumber, form, level));
	}

	public void setPokemon(Pokemon pokemon) {
		mPokemon = pokemon;
		if(selectedNature != null) {
			mPokemon.setNature(selectedNature);
		}
		cardHolder.updatePokemon(pokemon);
		showPokemonInfo();
	}
}
