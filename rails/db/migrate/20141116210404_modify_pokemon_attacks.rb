class ModifyPokemonAttacks < ActiveRecord::Migration
  def change
	  rename_column :pokemon_attacks, :attack_code, :attack_id
  end
end
