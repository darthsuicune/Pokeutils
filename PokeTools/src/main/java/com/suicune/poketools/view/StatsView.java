package com.suicune.poketools.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Nature;
import com.suicune.poketools.model.Stats;
import com.suicune.poketools.model.Stats.Stat;
import com.suicune.poketools.model.Stats.StatType;

import java.util.HashMap;
import java.util.Map;

import static com.suicune.poketools.model.Stats.Stat.ATTACK;
import static com.suicune.poketools.model.Stats.Stat.DEFENSE;
import static com.suicune.poketools.model.Stats.Stat.HP;
import static com.suicune.poketools.model.Stats.Stat.SPECIAL_ATTACK;
import static com.suicune.poketools.model.Stats.Stat.SPECIAL_DEFENSE;
import static com.suicune.poketools.model.Stats.Stat.SPEED;

public class StatsView extends LinearLayout {
	@IdRes public static final int BASE_STATS_ID = 100;
	@IdRes public static final int IVS_VIEW_ID = 101;
	@IdRes public static final int EVS_VIEW_ID = 102;
	@IdRes public static final int VALUES_VIEW_ID = 103;

	Map<Stat, TextView> baseStatsViews = new HashMap<>();
	Map<Stat, EditText> ivsViews = new HashMap<>();
	Map<Stat, EditText> evsViews = new HashMap<>();
	Map<Stat, EditText> valuesViews = new HashMap<>();

	Stats stats;
	OnStatsChangedListener listener;
	boolean isExternalUpdate;

	public StatsView(Context context) {
		super(context);
		createSubViews();
	}

	public StatsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		createSubViews();
	}

	public StatsView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		createSubViews();
	}

	private void createSubViews() {
		setOrientation(VERTICAL);
		prepareBaseStatsViews();
		prepareViews(StatType.IV, ivsViews, R.layout.evs_ivs_view, IVS_VIEW_ID);
		prepareViews(StatType.EV, evsViews, R.layout.evs_ivs_view, EVS_VIEW_ID);
		prepareViews(StatType.VALUE, valuesViews, R.layout.evs_ivs_view, VALUES_VIEW_ID);
	}

	private void prepareBaseStatsViews() {
		View v = LayoutInflater.from(getContext()).inflate(R.layout.base_stats_view, null);
		v.setId(BASE_STATS_ID);
		addView(v);
		baseStatsViews.put(HP, (TextView) v.findViewById(R.id.base_hp));
		baseStatsViews.put(ATTACK, (TextView) v.findViewById(R.id.base_attack));
		baseStatsViews.put(DEFENSE, (TextView) v.findViewById(R.id.base_defense));
		baseStatsViews.put(SPECIAL_ATTACK, (TextView) v.findViewById(R.id.base_special_attack));
		baseStatsViews.put(SPECIAL_DEFENSE, (TextView) v.findViewById(R.id.base_special_defense));
		baseStatsViews.put(SPEED, (TextView) v.findViewById(R.id.base_speed));
	}

	private void prepareViews(final StatType statType, Map<Stat, EditText> viewsMap, int layoutId,
							  int viewId) {
		View v = LayoutInflater.from(getContext()).inflate(layoutId, null);
		v.setId(viewId);
		addView(v);
		((TextView) v.findViewById(R.id.label)).setText(statType.resId());
		viewsMap.put(HP, (EditText) v.findViewById(R.id.hp));
		viewsMap.put(ATTACK, (EditText) v.findViewById(R.id.attack));
		viewsMap.put(DEFENSE, (EditText) v.findViewById(R.id.defense));
		viewsMap.put(SPECIAL_ATTACK, (EditText) v.findViewById(R.id.special_attack));
		viewsMap.put(SPECIAL_DEFENSE, (EditText) v.findViewById(R.id.special_defense));
		viewsMap.put(SPEED, (EditText) v.findViewById(R.id.speed));
		for (final Stat stat : viewsMap.keySet()) {
			final EditText editText = viewsMap.get(stat);
			editText.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
					if (i2 > 0 && editText.getError() != null) {
						editText.setError(null);
					}
				}

				@Override
				public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

				}

				@Override public void afterTextChanged(Editable editable) {
					if(isExternalUpdate) {
						return;
					}
					try {
						listener.onStatChanged(statType, stat,
								parseNewValue(statType, stat, editable));
					} catch (NumberFormatException e) {
						//The thing is empty or incorrect. Show error
						editText.setError(getContext().getString(R.string.error_invalid_value));
					}
				}

				private int parseNewValue(StatType statType, Stat stat, Editable editable) {
					int result = Integer.parseInt(editable.toString());
					if (!Stats.isValid(6, statType, stat, result)) {
						throw new NumberFormatException();
					}
					return result;
				}
			});
		}
	}

	public void setup(OnStatsChangedListener listener) {
		this.listener = listener;
	}

	public void setStats(Stats stats) {
		this.isExternalUpdate = true;
		this.stats = stats;
		updateViews();
		this.isExternalUpdate = false;
	}

	private void updateViews() {
		for (Stat stat : Stat.values(6)) {
			TextView base = baseStatsViews.get(stat);
			EditText iv = ivsViews.get(stat);
			EditText ev = evsViews.get(stat);
			EditText value = valuesViews.get(stat);
			base.setText(Integer.toString(stats.get(stat, StatType.BASE)));
			iv.setText(Integer.toString(stats.get(stat, StatType.IV)));
			ev.setText(Integer.toString(stats.get(stat, StatType.EV)));
			value.setText(Integer.toString(stats.get(stat, StatType.VALUE)));
		}
	}

	public void setNature(Nature nature) {
		for(Stat stat : baseStatsViews.keySet()) {
			View v = baseStatsViews.get(stat);
			if(stat == nature.decreasedStat()) {
				v.setBackgroundColor(getResources().getColor(R.color.decreased_stat));
			} else if(stat == nature.increasedStat()) {
				v.setBackgroundColor(getResources().getColor(R.color.increased_stat));
			} else {
				v.setBackgroundColor(Color.TRANSPARENT);
			}
		}
	}

	public void enableMods() {

	}

	public void disableMods() {

	}

	public interface OnStatsChangedListener {
		void onStatChanged(StatType type, Stat stat, int newValue);
	}
}

/**
 *

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
 baseStatsViews.put(Stat.HP, (TextView) findViewById(R.id.base_hp));
 baseStatsViews.put(Stat.ATTACK, (TextView) findViewById(R.id.base_attack));
 baseStatsViews.put(Stat.DEFENSE, (TextView) findViewById(R.id.base_defense));
 baseStatsViews.put(Stat.SPECIAL_ATTACK, (TextView) findViewById(R.id.base_special_attack));
 baseStatsViews.put(Stat.SPECIAL_DEFENSE, (TextView) findViewById(R.id.base_special_defense));
 baseStatsViews.put(Stat.SPEED, (TextView) findViewById(R.id.base_speed));
 }
 *

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
 for (Stat stat : statModifiersView.keySet()) {
 pokemon.setStatModifier(stat,
 Integer.parseInt((String) statModifiersView.get(stat).getSelectedItem()));
 }
 */
