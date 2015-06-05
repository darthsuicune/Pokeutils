json.array!(@pokemons) do |pokemon|
  json.extract! pokemon, :id, :dex_number, :form, :egg_group_1, :egg_group_2, :height, :pokemon_name_id, :weight, :ability_1, :ability_2, :ability_3, :min_level, :base_hp, :base_attack, :base_defense, :base_sp_attack, :base_sp_defense, :base_speed, :type_1, :type_2
  json.url pokemon_url(pokemon, format: :json)
end
