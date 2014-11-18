class CreatePokemonTypes < ActiveRecord::Migration
  def change
    create_table :pokemon_types do |t|
      t.integer :code
      t.string :name

      t.timestamps
    end
  end
end
