class Attack < ActiveRecord::Base
	def details
		AttackDetails.find_by_attack_code(self.code)
	end
	
	def attack_code
		details.attack_code
	end
	
	def accuracy
		details.accuracy
	end
	
	def category_code
		details.category_code
	end
	
	def caused_effect
		details.caused_effect
	end
	
	def crit_rate
		details.crit_rate
	end
	
	def damage_class
		details.damage_class
	end
	
	def effect_chance
		details.effect_chance
	end
	
	def flinch_chance
		details.flinch_chance
	end
	
	def healing
		details.healing
	end
	
	def max_turns
		details.max_turns
	end
	
	def min_turns
		details.min_turns
	end
	
	def power
		details.power
	end
	
	def pp
		details.pp
	end
	
	def priority
		details.priority
	end
	
	def recoil
		details.recoil
	end
	
	def status
		details.status
	end
	
	def attack_type
		details.pokemon_type_code
	end
end
