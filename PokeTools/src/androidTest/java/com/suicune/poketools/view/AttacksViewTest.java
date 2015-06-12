package com.suicune.poketools.view;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.suicune.poketools.view.AttacksView.OnAttacksChangedListener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(AndroidJUnit4.class)
public class AttacksViewTest {
	AttacksView view;
	OnAttacksChangedListener listener;

	@Test public void afterAssigningAPokemonTheAttacksAppearOnTheSpinners() throws Exception {
		createView();
	}

	private void createView() {
		view = new AttacksView(InstrumentationRegistry.getTargetContext());
		listener = Mockito.mock(OnAttacksChangedListener.class);
		view.setup(listener);
	}
}