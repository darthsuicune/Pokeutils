require 'rails_helper'

RSpec.describe "pokemons/new", :type => :view do
  before(:each) do
    assign(:pokemon, Pokemon.new(
      :dex_number => 1,
      :form => 1,
      :egg_group_1 => 1,
      :egg_group_2 => 1,
      :evolution => 1,
      :height => "",
      :pokemon_name_id => 1,
      :weight => "",
      :ability_1 => 1,
      :ability_2 => 1,
      :ability_3 => 1,
      :min_level => 1,
      :base_hp => 1,
      :base_attack => 1,
      :base_defense => 1,
      :base_sp_attack => 1,
      :base_sp_defense => 1,
      :base_speed => 1,
      :type_1 => 1,
      :type_2 => 1
    ))
  end

  it "renders new pokemon form" do
    render

    assert_select "form[action=?][method=?]", pokemons_path, "post" do

      assert_select "input#pokemon_dex_number[name=?]", "pokemon[dex_number]"

      assert_select "input#pokemon_form[name=?]", "pokemon[form]"

      assert_select "input#pokemon_egg_group_1[name=?]", "pokemon[egg_group_1]"

      assert_select "input#pokemon_egg_group_2[name=?]", "pokemon[egg_group_2]"

      assert_select "input#pokemon_evolution[name=?]", "pokemon[evolution]"

      assert_select "input#pokemon_height[name=?]", "pokemon[height]"

      assert_select "input#pokemon_pokemon_name_id[name=?]", "pokemon[pokemon_name_id]"

      assert_select "input#pokemon_weight[name=?]", "pokemon[weight]"

      assert_select "input#pokemon_ability_1[name=?]", "pokemon[ability_1]"

      assert_select "input#pokemon_ability_2[name=?]", "pokemon[ability_2]"

      assert_select "input#pokemon_ability_3[name=?]", "pokemon[ability_3]"

      assert_select "input#pokemon_min_level[name=?]", "pokemon[min_level]"

      assert_select "input#pokemon_base_hp[name=?]", "pokemon[base_hp]"

      assert_select "input#pokemon_base_attack[name=?]", "pokemon[base_attack]"

      assert_select "input#pokemon_base_defense[name=?]", "pokemon[base_defense]"

      assert_select "input#pokemon_base_sp_attack[name=?]", "pokemon[base_sp_attack]"

      assert_select "input#pokemon_base_sp_defense[name=?]", "pokemon[base_sp_defense]"

      assert_select "input#pokemon_base_speed[name=?]", "pokemon[base_speed]"

      assert_select "input#pokemon_type_1[name=?]", "pokemon[type_1]"

      assert_select "input#pokemon_type_2[name=?]", "pokemon[type_2]"
    end
  end
end
