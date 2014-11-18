class Pokemon < ActiveRecord::Base
	default_scope { order(dex_number: :asc, form: :asc) }
	
	belongs_to :pokemon_name
	has_many :pokemon_attacks
	has_many :attack_codes, through: :pokemon_attacks, source: "attack"
	
	def name
		pokemon_name.name
	end
	
	def attacks
		result = []
		self.attack_codes.each do |att|
			result << att.code
		end
		result
	end
	
	def abilities
		[self.ability_1, self.ability_2, self.ability_3]
	end
	
	def baseStats
		[self.base_hp, self.base_attack, self.base_defense, self.base_sp_attack, self.base_sp_defense, self.base_speed]
	end
	
	def types
		[self.type_1, self.type_2]
	end
end
