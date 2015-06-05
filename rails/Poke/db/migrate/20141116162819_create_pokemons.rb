class CreatePokemons < ActiveRecord::Migration
  def change
    create_table :pokemons do |t|
      t.integer :dex_number
      t.integer :form
      t.integer :egg_group_1
      t.integer :egg_group_2
      t.integer :evolution
      t.float :height
      t.integer :pokemon_name_id
      t.float :weight
      t.integer :ability_1
      t.integer :ability_2
      t.integer :ability_3
      t.integer :min_level
      t.integer :base_hp
      t.integer :base_attack
      t.integer :base_defense
      t.integer :base_sp_attack
      t.integer :base_sp_defense
      t.integer :base_speed
      t.integer :type_1
      t.integer :type_2

      t.timestamps
    end
  end
end
