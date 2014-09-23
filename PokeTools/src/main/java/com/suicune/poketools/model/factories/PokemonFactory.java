package com.suicune.poketools.model.factories;

import android.content.Context;
import android.content.res.AssetManager;

import com.suicune.poketools.model.Ability;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.Stats;
import com.suicune.poketools.model.Type;
import com.suicune.poketools.model.gen6.Gen6Pokemon;
import com.suicune.poketools.utils.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by lapuente on 17.09.14.
 */
public class PokemonFactory {
	public static Pokemon createPokemon(Context context, int gen, int dexNumber, int form,
										int level) throws IOException, JSONException {
		switch (gen) {
			case 6:
				return createGen6Pokemon(context, dexNumber, form, level);
			default:
				return null;
		}
	}

	private static Pokemon createGen6Pokemon(Context context, int dexNumber, int form, int level)
			throws IOException, JSONException {
		AssetManager manager = context.getAssets();
		JSONArray pokemonForms = FileUtils.toJsonArray(manager.open("gen5/" + dexNumber + ".json"));
		JSONObject pokemon = pokemonForms.getJSONObject(form);

		JSONArray typeArray = pokemon.getJSONArray(Gen6Pokemon.ARG_TYPES);
		JSONArray abilityArray = pokemon.getJSONArray(Gen6Pokemon.ARG_ABILITIES);
		JSONArray statsArray = pokemon.getJSONArray(Gen6Pokemon.ARG_BASE_STATS);

		Type[] types = new Type[2];
		types[0] = TypeFactory.createType(6, typeArray.getInt(0));
		types[1] = TypeFactory.createType(6, typeArray.getInt(1));

		int[] baseStats = new int[6];
		for (int i = 0; i < 6; i++) {
			baseStats[i] = statsArray.getInt(i);
		}
		Stats stats = StatsFactory.createStats(6, level, baseStats);

		Ability[] abilities = new Ability[3];
		for (int i = 0; i < 3; i++) {
			abilities[i] = AbilityFactory.createAbility(context, 6, abilityArray.getInt(i));
		}

		Gen6Pokemon poke =
				new Gen6Pokemon(level, pokemon, stats, types, abilities, pokemonForms.length());
		return poke;
	}
}
