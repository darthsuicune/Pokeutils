class PokemonAttack < ActiveRecord::Base
	belongs_to :pokemon
	belongs_to :attack
end
