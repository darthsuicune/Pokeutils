class AddClassificationToPokemonNames < ActiveRecord::Migration
  def change
	  add_column :pokemon_names, :classification, :string
  end
end
