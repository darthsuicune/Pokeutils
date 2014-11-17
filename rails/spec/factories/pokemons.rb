# Read about factories at https://github.com/thoughtbot/factory_girl

FactoryGirl.define do
  factory :pokemon do
    dex_number 1
    form 1
    egg_group_1 1
    egg_group_2 1
    evolution 1
    height ""
    pokemon_name_id 1
    weight ""
    ability_1 1
    ability_2 1
    ability_3 1
    min_level 1
    base_hp 1
    base_attack 1
    base_defense 1
    base_sp_attack 1
    base_sp_defense 1
    base_speed 1
    type_1 1
    type_2 1
  end
end
