package com.suicune.poketools.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.suicune.poketools.model.Attack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttacksView extends RelativeLayout {
	@IdRes public static final int ATTACK_1_SPINNER_ID = 110;
	@IdRes public static final int ATTACK_2_SPINNER_ID = 111;
	@IdRes public static final int ATTACK_3_SPINNER_ID = 112;
	@IdRes public static final int ATTACK_4_SPINNER_ID = 113;

	OnAttacksChangedListener listener;
	Map<Integer, Spinner> attackViews = new HashMap<>();
	List<Attack> attacks = new ArrayList<>();
	ArrayAdapter<Attack> attackAdapter;

	public AttacksView(Context context) {
		super(context);
	}

	public AttacksView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AttacksView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setup(OnAttacksChangedListener listener) {
		this.listener = listener;
	}

	public void setAttacks(List<Attack> attacks) {
		this.attacks = attacks;
		attackAdapter = new ArrayAdapter<>(getContext(),
				android.R.layout.simple_spinner_dropdown_item, attacks);
		createSubViews();
	}

	private void createSubViews() {
		for (int i = 0; i < 4; i++) {
			Spinner spinner = prepareAttackSpinner(i);
			attackViews.put(i, spinner);
		}
	}

	private Spinner prepareAttackSpinner(final int index) {
		Spinner spinner = attackViews.get(index);
		if (spinner == null) {
			spinner = new Spinner(getContext());
			spinner.setId(index + 110); // Build the ids with 110 + index
			this.addView(spinner, layoutOptions(index));
		}

		spinner.setAdapter(attackAdapter);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position,
									   long l) {
				Attack attack = attackAdapter.getItem(position);
				view.setBackgroundColor(getResources().getColor(attack.color()));
				listener.onAttackChanged(index, attack);
			}

			@Override public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
		return spinner;
	}

	public RelativeLayout.LayoutParams layoutOptions(int i) {
		RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		switch(i) {
			case 2:
				params.addRule(BELOW, attackViews.get(0).getId());
			case 0:
				params.addRule((Build.VERSION.SDK_INT >= 17) ? ALIGN_PARENT_START : ALIGN_PARENT_LEFT);
				break;
			case 3:
				params.addRule(BELOW, attackViews.get(1).getId());
			case 1:
				params.addRule((Build.VERSION.SDK_INT >= 17) ? ALIGN_PARENT_END : ALIGN_PARENT_RIGHT);
				break;
		}
		return params;
	}

	public void setCurrentAttacks(Map<Integer, Attack> currentAttacks) {
		for (Integer index : currentAttacks.keySet()) {
			Spinner spinner = attackViews.get(index);
			spinner.setSelection(attackAdapter.getPosition(currentAttacks.get(index)));
		}
	}

	public interface OnAttacksChangedListener {
		void onAttackChanged(int index, Attack attack);
	}
}
