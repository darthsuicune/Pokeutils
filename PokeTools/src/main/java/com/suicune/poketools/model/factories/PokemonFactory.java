package com.suicune.poketools.model.factories;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.suicune.poketools.model.Ability;
import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.Stats;
import com.suicune.poketools.model.Type;
import com.suicune.poketools.model.gen6.Gen6Pokemon;
import com.suicune.poketools.utils.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

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

	public static Pokemon changeForm(Context context, int gen, Pokemon pokemon, int form)
			throws IOException, JSONException {
		switch (gen) {
			case 6:
				return changeGen6Form(context, pokemon, form);
			default:
				return null;
		}
	}

	private static Pokemon changeGen6Form(Context context, Pokemon pokemon, int form)
			throws IOException, JSONException {
		Bundle bundle = pokemon.save();
		Pokemon result = createGen6Pokemon(context, pokemon.dexNumber(), form, pokemon.level());
		result.load(context, bundle);
		return result;
	}

	private static Pokemon createGen6Pokemon(Context context, int dexNumber, int form, int level)
			throws IOException, JSONException {
		AssetManager manager = context.getAssets();
		JSONArray pokemonForms =
				FileUtils.toJsonArray(manager.open("gen6/pokes/" + dexNumber + ".json"));
		JSONObject pokemon = pokemonForms.getJSONObject(form);

		JSONArray typeArray = pokemon.getJSONArray(Pokemon.ARG_TYPES);
		JSONArray abilityArray = pokemon.getJSONArray(Pokemon.ARG_ABILITIES);
		JSONArray statsArray = pokemon.getJSONArray(Pokemon.ARG_BASE_STATS);
		JSONArray formAttacksArray = pokemon.getJSONArray(Pokemon.ARG_ATTACKS);
		JSONArray attacksArray = pokemonForms.getJSONObject(0).getJSONArray(Pokemon.ARG_ATTACKS);

		List<Attack> attacks = AttackFactory.fromJSON(context, 6, attacksArray, formAttacksArray);
		Type[] types = TypeFactory.fromJSON(6, typeArray);
		Ability[] abilities = AbilityFactory.fromJson(context, 6, abilityArray);
		Stats stats = StatsFactory.baseFromJson(6, level, statsArray);
		String name = Pokemon.getDefaultName(context, dexNumber, form);

		return new Gen6Pokemon(level, pokemon, stats, types, abilities, pokemonForms.length(), name,
				attacks);
	}

	public static Pokemon createFromBundle(Context context, Bundle bundle)
			throws IOException, JSONException {
		if (bundle == null) {
			return null;
		}
		Pokemon pokemon = null;
		if (bundle.containsKey(Pokemon.ARG_GEN)) {
			switch (bundle.getInt(Pokemon.ARG_GEN)) {
				case 6:
				default:
					pokemon = createGen6Pokemon(context, bundle.getInt(Pokemon.ARG_DEX_NUMBER),
							bundle.getInt(Pokemon.ARG_FORM), bundle.getInt(Pokemon.ARG_LEVEL));
					pokemon = pokemon.load(context, bundle);
					break;
			}
		}
		return pokemon;
	}
}
