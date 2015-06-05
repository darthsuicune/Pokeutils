class CreatePokemonAttacks < ActiveRecord::Migration
  def change
    create_table :pokemon_attacks do |t|
      t.integer :pokemon_id
      t.integer :attack_code

      t.timestamps
    end
	 
	 remove_column :pokemons, :evolution
  end
end
