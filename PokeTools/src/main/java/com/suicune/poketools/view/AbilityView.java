package com.suicune.poketools.view;

import android.content.Context;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Ability;
import com.suicune.poketools.model.factories.AbilityFactory;

public class AbilityView extends Spinner {
	private Pair<Ability, Integer> currentAbility;
	private OnAbilitySelectedListener listener;
	private String[] abilities;
	private ArrayAdapter<String> adapter;

	public AbilityView(Context context) {
		super(context);
	}

	public AbilityView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AbilityView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	public void setup(OnAbilitySelectedListener listener) {
		this.listener = listener;
		setAdapter();
	}

	private void setAdapter() {
		abilities = getResources().getStringArray(R.array.abilities);
		adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
						abilities);
		setAdapter(adapter);
		setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override public void onItemSelected(AdapterView<?> adapterView, View view, int i,
												 long l) {
				if(currentAbility == null || i != currentAbility.second) {
					setAbility(i);
				}
			}

			@Override public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
	}

	private void setAbility(int index) {
		currentAbility = new Pair<>(AbilityFactory.createAbility(getContext(), 6, index), index);
		if (listener != null) {
			listener.onAbilitySelected(currentAbility.first);
		}
	}

	public Ability ability() {
		return currentAbility.first;
	}

	@Override public Object getSelectedItem() {
		return (currentAbility != null) ? currentAbility.first : null;
	}

	public interface OnAbilitySelectedListener {
		void onAbilitySelected(Ability ability);
	}
}
