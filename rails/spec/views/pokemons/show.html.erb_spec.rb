require 'rails_helper'

RSpec.describe "pokemons/show", :type => :view do
  before(:each) do
    @pokemon = assign(:pokemon, Pokemon.create!(
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
    ))
  end

  it "renders attributes in <p>" do
    render
    expect(rendered).to match(/1/)
    expect(rendered).to match(/2/)
    expect(rendered).to match(/3/)
    expect(rendered).to match(/4/)
    expect(rendered).to match(/5/)
    expect(rendered).to match(//)
    expect(rendered).to match(/6/)
    expect(rendered).to match(//)
    expect(rendered).to match(/7/)
    expect(rendered).to match(/8/)
    expect(rendered).to match(/9/)
    expect(rendered).to match(/10/)
    expect(rendered).to match(/11/)
    expect(rendered).to match(/12/)
    expect(rendered).to match(/13/)
    expect(rendered).to match(/14/)
    expect(rendered).to match(/15/)
    expect(rendered).to match(/16/)
    expect(rendered).to match(/17/)
    expect(rendered).to match(/18/)
  end
end
