class Pokemon < ActiveRecord::Base
	default_scope { order(dex_number: :asc, form: :asc) }
	
	belongs_to :pokemon_name
	has_many :pokemon_attacks
	has_many :attacks, through: :pokemon_attacks, foreign_key: "attack_code"
	
	def name
		pokemon_name.name
	end
	
	def attack_codes
		result = []
		self.attacks.each do |att|
			result << att.code
		end
		result
	end
	
	def baseStats
		[self.base_hp, self.base_attack, self.base_defense, self.base_sp_attack, self.base_sp_defense, self.base_speed]
	end
end
