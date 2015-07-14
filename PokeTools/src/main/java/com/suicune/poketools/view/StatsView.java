package com.suicune.poketools.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
	@IdRes public static final int MODIFIERS_ID = 104;

	Map<StatType, View> rootViews = new HashMap<>();
	Map<StatType, Boolean> enabledViews = new HashMap<>();

	Map<Stat, TextView> baseStatsViews = new HashMap<>();
	Map<Stat, EditText> ivsViews = new HashMap<>();
	Map<Stat, EditText> evsViews = new HashMap<>();
	Map<Stat, EditText> valuesViews = new HashMap<>();
	Map<Stat, Spinner> modifiersViews = new HashMap<>();

	Stats stats;
	OnStatsChangedListener listener;
	boolean isExternalUpdate;

	public StatsView(Context context) {
		super(context);
	}

	public StatsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StatsView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setup(OnStatsChangedListener listener) {
		this.listener = listener;
		createSubViews();
	}

	private void createSubViews() {
		setOrientation(VERTICAL);
		prepareBaseStatsViews();
		prepareStatViews(StatType.IV, ivsViews, IVS_VIEW_ID);
		prepareStatViews(StatType.EV, evsViews, EVS_VIEW_ID);
		prepareStatViews(StatType.VALUE, valuesViews, VALUES_VIEW_ID);
		prepareModifiersViews();
	}

	private void prepareBaseStatsViews() {
		View v = LayoutInflater.from(getContext()).inflate(R.layout.base_stats_view, null);
		rootViews.put(StatType.BASE, v);
		enabledViews.put(StatType.BASE, true);
		v.setId(BASE_STATS_ID);
		addView(v);
		baseStatsViews.put(HP, (TextView) v.findViewById(R.id.base_hp));
		baseStatsViews.put(ATTACK, (TextView) v.findViewById(R.id.base_attack));
		baseStatsViews.put(DEFENSE, (TextView) v.findViewById(R.id.base_defense));
		baseStatsViews.put(SPECIAL_ATTACK, (TextView) v.findViewById(R.id.base_special_attack));
		baseStatsViews.put(SPECIAL_DEFENSE, (TextView) v.findViewById(R.id.base_special_defense));
		baseStatsViews.put(SPEED, (TextView) v.findViewById(R.id.base_speed));
	}

	private void prepareStatViews(final StatType statType, Map<Stat, EditText> viewsMap,
								  int viewId) {
		View v = LayoutInflater.from(getContext()).inflate(R.layout.evs_ivs_view, null);
		rootViews.put(statType, v);
		enabledViews.put(statType, true);
		v.setId(viewId);
		addView(v);
		((TextView) v.findViewById(R.id.label)).setText(statType.resId());
		viewsMap.put(HP, (EditText) v.findViewById(R.id.hp));
		viewsMap.put(ATTACK, (EditText) v.findViewById(R.id.attack));
		viewsMap.put(DEFENSE, (EditText) v.findViewById(R.id.defense));
		viewsMap.put(SPECIAL_ATTACK, (EditText) v.findViewById(R.id.special_attack));
		viewsMap.put(SPECIAL_DEFENSE, (EditText) v.findViewById(R.id.special_defense));
		viewsMap.put(SPEED, (EditText) v.findViewById(R.id.speed));
		for (Stat stat : viewsMap.keySet()) {
			EditText editText = viewsMap.get(stat);
			editText.addTextChangedListener(new StatViewTextWatcher(editText, statType, stat));
		}
	}

	private void prepareModifiersViews() {
		View v = LayoutInflater.from(getContext()).inflate(R.layout.stat_modifiers, null);
		rootViews.put(StatType.MODIFIER, v);
		enabledViews.put(StatType.MODIFIER, true);
		v.setId(MODIFIERS_ID);
		addView(v);
		modifiersViews.put(Stat.ATTACK, (Spinner) v.findViewById(R.id.attack_modifier));
		modifiersViews.put(Stat.DEFENSE, (Spinner) v.findViewById(R.id.defense_modifier));
		modifiersViews
				.put(Stat.SPECIAL_ATTACK, (Spinner) v.findViewById(R.id.special_attack_modifier));
		modifiersViews
				.put(Stat.SPECIAL_DEFENSE, (Spinner) v.findViewById(R.id.special_defense_modifier));
		modifiersViews.put(Stat.SPEED, (Spinner) v.findViewById(R.id.speed_modifier));
		for (final Stat stat : modifiersViews.keySet()) {
			modifiersViews.get(stat).setAdapter(
					new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
							getContext().getResources().getStringArray(R.array.stat_modifiers)));
			modifiersViews.get(stat).setSelection(6);
			modifiersViews.get(stat).setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
					int newValue = Integer.parseInt((String) adapterView.getItemAtPosition(i));
					listener.onStatChanged(StatType.MODIFIER, stat, newValue);

				}

				@Override public void onNothingSelected(AdapterView<?> adapterView) {
				}
			});
		}
	}

	public void setStats(Stats stats) {
		this.isExternalUpdate = true;
		this.stats = stats;
		updateViews();
		this.isExternalUpdate = false;
	}

	private void updateViews() {
		for (Stat stat : Stat.values(6)) {
			updateStatView(StatType.BASE, baseStatsViews, stat);
			updateStatView(StatType.IV, ivsViews, stat);
			updateStatView(StatType.EV, evsViews, stat);
			updateStatView(StatType.VALUE, valuesViews, stat);
		}
	}

	private void updateStatView(StatType type, Map<Stat, ? extends TextView> views, Stat stat) {
		if (enabledViews.get(type)) {
			TextView view = views.get(stat);
			view.setText(Integer.toString(stats.get(stat, type)));
		}
	}

	public void setNature(Nature nature) {
		for (Stat stat : baseStatsViews.keySet()) {
			View v = baseStatsViews.get(stat);
			if (stat == nature.decreasedStat()) {
				v.setBackgroundColor(getResources().getColor(R.color.decreased_stat));
			} else if (stat == nature.increasedStat()) {
				v.setBackgroundColor(getResources().getColor(R.color.increased_stat));
			} else {
				v.setBackgroundColor(Color.TRANSPARENT);
			}
		}
	}

	public void enableMods() {
		rootViews.get(StatType.MODIFIER).setVisibility(View.VISIBLE);
		enabledViews.put(StatType.MODIFIER, true);
	}

	public void disableMods() {
		rootViews.get(StatType.MODIFIER).setVisibility(View.GONE);
		enabledViews.put(StatType.MODIFIER, false);

	}

	public interface OnStatsChangedListener {
		void onStatChanged(StatType type, Stat stat, int newValue);
	}

	private class StatViewTextWatcher implements TextWatcher {
		EditText editText;
		StatType statType;
		Stat stat;

		public StatViewTextWatcher(EditText editText, StatType statType, Stat stat) {
			this.editText = editText;
			this.statType = statType;
			this.stat = stat;
		}

		@Override
		public void beforeTextChanged(CharSequence string, int start, int count, int after) {
			if (after > 0 && editText.getError() != null) {
				editText.setError(null);
			}
		}

		@Override public void onTextChanged(CharSequence string, int start, int before, int count) {

		}

		@Override public void afterTextChanged(Editable editable) {
			if (isExternalUpdate) {
				return;
			}
			try {
				listener.onStatChanged(statType, stat, parseNewValue(statType, stat, editable));
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
	}
}
