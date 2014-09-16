package com.suicune.poketools.model.team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TeamList {

    public List<PokemonTeam> mItems = new ArrayList<>();
    public Map<String, PokemonTeam> mItemMap = new HashMap<>();

    public void addItem(PokemonTeam item) {
        mItems.add(item);
        mItemMap.put(item.id, item);
    }
}
