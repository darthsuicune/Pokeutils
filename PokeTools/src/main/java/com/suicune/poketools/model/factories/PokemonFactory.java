package com.suicune.poketools.model.factories;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.suicune.poketools.model.Ability;
import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.Nature;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.Stats;
import com.suicune.poketools.model.Type;
import com.suicune.poketools.model.gen6.Gen6Pokemon;
import com.suicune.poketools.utils.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
		int oldHappiness = pokemon.happiness();
		List<Attack> attacks = pokemon.currentAttacks();
		Ability ability = pokemon.currentAbility();
		Type additionalType = pokemon.additionalType();
		Nature nature = pokemon.nature();
		String nickname = null;
		if (!pokemon.nickname()
				.equals(Pokemon.getName(context, pokemon.dexNumber(), pokemon.formNumber()))) {
			nickname = pokemon.nickname();
		}
		Stats oldStats = pokemon.stats();

		Pokemon result = createGen6Pokemon(context, pokemon.dexNumber(), form, pokemon.level());

		if (nickname != null) {
			result.setNickname(nickname);
		}
		result.setHappiness(oldHappiness).setNature(nature).addAdditionalType(additionalType)
				.setCurrentAbility(ability).setCurrentAttacks(attacks);
		result.stats()
				.setIvs(oldStats.ivs().get(Stats.Stat.HP), oldStats.ivs().get(Stats.Stat.ATTACK),
						oldStats.ivs().get(Stats.Stat.DEFENSE),
						oldStats.ivs().get(Stats.Stat.SPECIAL_ATTACK),
						oldStats.ivs().get(Stats.Stat.SPECIAL_DEFENSE),
						oldStats.ivs().get(Stats.Stat.SPEED));
		result.stats()
				.setEvs(oldStats.evs().get(Stats.Stat.HP), oldStats.evs().get(Stats.Stat.ATTACK),
						oldStats.evs().get(Stats.Stat.DEFENSE),
						oldStats.evs().get(Stats.Stat.SPECIAL_ATTACK),
						oldStats.evs().get(Stats.Stat.SPECIAL_DEFENSE),
						oldStats.evs().get(Stats.Stat.SPEED));
		return result;
	}

	private static Pokemon createGen6Pokemon(Context context, int dexNumber, int form, int level)
			throws IOException, JSONException {
		AssetManager manager = context.getAssets();
		JSONArray pokemonForms =
				FileUtils.toJsonArray(manager.open("gen5/pokes/" + dexNumber + ".json"));
		JSONObject pokemon = pokemonForms.getJSONObject(form);

		JSONArray typeArray = pokemon.getJSONArray(Pokemon.ARG_TYPES);
		JSONArray abilityArray = pokemon.getJSONArray(Pokemon.ARG_ABILITIES);
		JSONArray statsArray = pokemon.getJSONArray(Pokemon.ARG_BASE_STATS);
		JSONArray formAttacksArray = pokemon.getJSONArray(Pokemon.ARG_ATTACKS);
		JSONArray attacksArray = pokemonForms.getJSONObject(0).getJSONArray(Pokemon.ARG_ATTACKS);

		//Prepare attacks
		List<Attack> attacks = new ArrayList<>();
		if(attacksArray != formAttacksArray) {
			for (int i = 0; i < formAttacksArray.length(); i++) {
				attacks.add(AttackFactory.create(context, 6, attacksArray.getInt(i)));
			}
		}
		for(int i = 0; i < attacksArray.length(); i++) {
			Attack attack = AttackFactory.create(context, 6, attacksArray.getInt(i));
			if(!attacks.contains(attack)) {
				attacks.add(attack);
			}
		}

		//Prepare Types
		Type[] types = new Type[2];
		types[0] = TypeFactory.createType(6, typeArray.getInt(0));
		types[1] = TypeFactory.createType(6, typeArray.getInt(1));

		//Prepare base stats
		int[] baseStats = new int[6];
		for (int i = 0; i < 6; i++) {
			baseStats[i] = statsArray.getInt(i);
		}
		Stats stats = StatsFactory.createStats(6, level, baseStats);

		//Prepare abilities
		Ability[] abilities = new Ability[3];
		for (int i = 0; i < 3; i++) {
			abilities[i] = AbilityFactory.createAbility(context, 6, abilityArray.getInt(i));
		}

		String name = Pokemon.getName(context, dexNumber, form);

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
					pokemon = new Gen6Pokemon(bundle,
							StatsFactory.fromBundle(6, bundle.getBundle(Pokemon.ARG_STATS)),
							TypeFactory.createType(6, bundle.getInt(Pokemon.ARG_TYPE_1)),
							TypeFactory.createType(6, bundle.getInt(Pokemon.ARG_TYPE_2)),
							AbilityFactory.fromBundle(6, bundle.getBundle(Pokemon.ARG_ABILITY_1)),
							AbilityFactory.fromBundle(6, bundle.getBundle(Pokemon.ARG_ABILITY_2)),
							AbilityFactory
									.fromBundle(6, bundle.getBundle(Pokemon.ARG_ABILITY_HIDDEN)),
							AttackFactory.fromBundle(context, 6,
									bundle.getIntArray(Pokemon.ARG_ATTACKS)));
					break;
				default:
			}
		}
		return pokemon;
	}
}
