package com.suicune.poketools.model.factories;

import android.content.Context;
import android.content.res.AssetManager;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.gen6.Gen6Attack;
import com.suicune.poketools.utils.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lapuente on 14.11.14.
 */
public class AttackFactory {
	public static Attack create(Context context, int gen, int id) throws IOException,
			JSONException {
		switch(gen) {
			case 5:
			case 6:
			default:
				return createGen6Attack(context, id);
		}
	}

	private static Attack createGen6Attack(Context context, int id)
			throws IOException, JSONException {
		AssetManager manager = context.getAssets();
		JSONObject attack = FileUtils.toJson(manager.open("gen5/attacks/" + id + ".json"));
		return new Gen6Attack(TypeFactory.createType(6, attack.getInt(Attack.ARG_TYPE)),
				attack.getInt(Attack.ARG_CLASS),
				attack.getInt(Attack.ARG_POWER),
				attack.getInt(Attack.ARG_PRIORITY),
				attack.getInt(Attack.ARG_ACCURACY),
				attack.getInt(Attack.ARG_PP),
				context.getResources().getStringArray(R.array.attack_names)[id],
				"");
	}

	public static List<Attack> fromBundle(Context context, int gen, int[] attacks)
			throws IOException, JSONException {
		List<Attack> attackList = new ArrayList<>();
		for(int attack : attacks) {
			switch(gen) {
				case 5:
				case 6:
				default:
					attackList.add(createGen6Attack(context, attack));
			}
		}
		return attackList;
	}
}
