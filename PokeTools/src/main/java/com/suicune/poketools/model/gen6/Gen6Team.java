package com.suicune.poketools.model.gen6;

import android.content.Context;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.PokemonTeam;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lapuente on 22.09.14.
 */
public class Gen6Team implements PokemonTeam {
	public String mName;
	public Map<Integer, Pokemon> members;

	public Gen6Team() {
		members = new HashMap<>();
	}

	@Override public String getName() {
		return mName;
	}

	@Override public PokemonTeam setName(String name) {
		mName = name;
		return this;
	}

	@Override public Pokemon getMember(int position) {
		return members.get(position);
	}

	@Override public int getMemberCount() {
		return members.size();
	}

	@Override public PokemonTeam addMember(int position, Pokemon pokemon) {
		members.put(position, pokemon);
		return this;
	}

	@Override public String[] getNames(Context context) {
		String[] names = new String[7];
		names[0] = (mName != null) ? mName : context.getString(R.string.team_main_screen);
		for (int i = 0; i <= 6; i++) {
			names[i] = getMemberName(i, context, R.string.team_member6);
		}

		return names;
	}

	public String getMemberName(int position, Context context, int resId) {
		if(members.get(position) != null) {
			Pokemon member = members.get(position);
			return member.getDefaultName(context, member.dexNumber(), member.formNumber());
		} else {
			return context.getString(resId);
		}
	}
}
