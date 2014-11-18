require 'rails_helper'

RSpec.describe "pokemons/index", :type => :view do
  before(:each) do
    assign(:pokemons, [
      Pokemon.create!(
        :dex_number => 1,
        :form => 2,
        :egg_group_1 => 3,
        :egg_group_2 => 4,
        :evolution => 5,
        :height => "",
        :pokemon_name_id => 6,
        :weight => "",
        :ability_1 => 7,
        :ability_2 => 8,
        :ability_3 => 9,
        :min_level => 10,
        :base_hp => 11,
        :base_attack => 12,
        :base_defense => 13,
        :base_sp_attack => 14,
        :base_sp_defense => 15,
        :base_speed => 16,
        :type_1 => 17,
        :type_2 => 18
      ),
      Pokemon.create!(
        :dex_number => 1,
        :form => 2,
        :egg_group_1 => 3,
        :egg_group_2 => 4,
        :evolution => 5,
        :height => "",
        :pokemon_name_id => 6,
        :weight => "",
        :ability_1 => 7,
        :ability_2 => 8,
        :ability_3 => 9,
        :min_level => 10,
        :base_hp => 11,
        :base_attack => 12,
        :base_defense => 13,
        :base_sp_attack => 14,
        :base_sp_defense => 15,
        :base_speed => 16,
        :type_1 => 17,
        :type_2 => 18
      )
    ])
  end

  it "renders a list of pokemons" do
    render
    assert_select "tr>td", :text => 1.to_s, :count => 2
    assert_select "tr>td", :text => 2.to_s, :count => 2
    assert_select "tr>td", :text => 3.to_s, :count => 2
    assert_select "tr>td", :text => 4.to_s, :count => 2
    assert_select "tr>td", :text => 5.to_s, :count => 2
    assert_select "tr>td", :text => "".to_s, :count => 2
    assert_select "tr>td", :text => 6.to_s, :count => 2
    assert_select "tr>td", :text => "".to_s, :count => 2
    assert_select "tr>td", :text => 7.to_s, :count => 2
    assert_select "tr>td", :text => 8.to_s, :count => 2
    assert_select "tr>td", :text => 9.to_s, :count => 2
    assert_select "tr>td", :text => 10.to_s, :count => 2
    assert_select "tr>td", :text => 11.to_s, :count => 2
    assert_select "tr>td", :text => 12.to_s, :count => 2
    assert_select "tr>td", :text => 13.to_s, :count => 2
    assert_select "tr>td", :text => 14.to_s, :count => 2
    assert_select "tr>td", :text => 15.to_s, :count => 2
    assert_select "tr>td", :text => 16.to_s, :count => 2
    assert_select "tr>td", :text => 17.to_s, :count => 2
    assert_select "tr>td", :text => 18.to_s, :count => 2
  end
end
