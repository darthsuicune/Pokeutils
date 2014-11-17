class AddPokemonCodeToNames < ActiveRecord::Migration
  def change
	  add_column :pokemon_names, :dex_number, :integer
	  add_column :pokemon_names, :form_number, :integer
  end
end
