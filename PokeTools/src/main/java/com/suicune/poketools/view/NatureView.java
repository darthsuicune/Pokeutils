package com.suicune.poketools.view;

import android.content.Context;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Nature;
import com.suicune.poketools.model.factories.NatureFactory;

public class NatureView extends Spinner{
	OnNatureSelectedListener listener;
	String[] natures;
	ArrayAdapter<String> adapter;
	Pair<Nature, Integer> currentNature;

	public NatureView(Context context) {
		super(context);
	}

	public NatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NatureView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setup(OnNatureSelectedListener listener) {
		this.listener = listener;
		setAdapter();
	}

	private void setAdapter() {
		natures = getResources().getStringArray(R.array.natures);
		adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
						natures);
		setAdapter(adapter);
		setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override public void onItemSelected(AdapterView<?> adapterView, View view, int i,
												 long l) {
				if(currentNature == null || i != currentNature.second) {
					setNature(i);
				}
			}

			@Override public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
	}

	private void setNature(int index) {
		currentNature = new Pair<>(NatureFactory.get(6, index), index);
		if(listener != null) {
			listener.onNatureSelected(currentNature.first);
		}
	}

	public void setNature(Nature nature) {
		currentNature = new Pair<>(nature, nature.save());
		setSelection(nature.save());
	}

	public Nature nature() {
		return (currentNature != null) ? currentNature.first : null;
	}

	@Override public Object getSelectedItem() {
		return (currentNature != null) ? currentNature.first : null;
	}

	public interface OnNatureSelectedListener {
		void onNatureSelected(Nature nature);
	}
}
