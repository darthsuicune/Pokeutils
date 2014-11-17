class CreatePokemonNames < ActiveRecord::Migration
  def change
    create_table :pokemon_names do |t|
      t.string :name
      t.string :description

      t.timestamps
    end
  end
end
