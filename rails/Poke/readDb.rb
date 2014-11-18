base = "db/pokemon"
abilities_folder = "#{base}/abilities"
items_folder = "#{base}/items"
gens_folder = "#{base}/gens"
moves_folder = "#{base}/moves"
pokes_folder = "#{base}/pokes"


def process_abilities(folder)
	File.open "#{folder}/abilities.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			Ability.create(code: code, name: value) unless Ability.find_by_code(code)
		end
	end
	File.open "#{folder}/ability_battledesc.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			a = Ability.find_by_code(code)
			a.battle_description = value unless a.battle_description
		end
	end
	File.open "#{folder}/ability_desc.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			a = Ability.find_by_code(code)
			a.description = value unless a.description
		end
	end
	File.open "#{folder}/ability_messages.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			a = Ability.find_by_code(code)
			a.message = value unless a.message
		end
	end
end

def process_categories
	if Category.all.empty?
		Category.create(code: 0, name: "Other")
		Category.create(code: 1, name: "Physical")
		Category.create(code: 2, name: "Special")
	end
end

def process_gens(folder)
	File.open "#{folder}/gens.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			Gen.create(code: code, name: value) if Gen.where(code: code, name: value).empty?
		end
	end
end

def process_items(folder)
end

def process_moves(folder)
	File.open "#{folder}/moves.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			Attack.create(code: code, name: value) if Attack.where(code: code, name: value).empty?
			AttackDetails.create(attack_code: code) if AttackDetails.where(attack_code: code).empty?
		end
	end
	File.open "#{folder}/move_description.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			a = Attack.find_by_code(code)
			a.description = value unless a.description
			a.save
		end
	end
	File.open "#{folder}/move_effect.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			a = Attack.find_by_code(code)
			a.effect = value unless a.effect
			a.save
		end
	end
	File.open "#{folder}/move_message.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			a = Attack.find_by_code(code)
			a.message = value unless a.message
			a.save
		end
	end
	folder = "#{folder}/6G"
	File.open "#{folder}/accuracy.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.accuracy = value unless d.accuracy
			d.save
		end
	end
	File.open "#{folder}/category.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.category_code = value unless d.category_code
			d.save
		end
	end
	File.open "#{folder}/caused_effect.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.caused_effect = value unless d.caused_effect
			d.save
		end
	end
	File.open "#{folder}/crit_rate.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.crit_rate = value unless d.crit_rate
			d.save
		end
	end
	File.open "#{folder}/damage_class.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.damage_class = value unless d.damage_class
			d.save
		end
	end
	AttackDetails.find_each { |ad| ad.damage_class = 0 unless ad.damage_class; ad.save }
	File.open "#{folder}/effect_chance.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.effect_chance = value unless d.effect_chance
			d.save
		end
	end
	File.open "#{folder}/flinch_chance.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.flinch_chance = value unless d.flinch_chance
			d.save
		end
	end
	File.open "#{folder}/healing.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.healing = value unless d.healing
			d.save
		end
	end
	File.open "#{folder}/max_turns.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.max_turns = value unless d.max_turns
			d.save
		end
	end
	File.open "#{folder}/min_turns.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.min_turns = value unless d.min_turns
			d.save
		end
	end
	File.open "#{folder}/power.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.power = value unless d.power
			d.save
		end
	end
	File.open "#{folder}/pp.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.pp = value unless d.pp
			d.save
		end
	end
	File.open "#{folder}/priority.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.priority = value unless d.priority
			d.save
		end
	end
	File.open "#{folder}/recoil.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.recoil = value unless d.recoil
			d.save
		end
	end
	File.open "#{folder}/status.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.status = value unless d.status
			d.save
		end
	end
	File.open "#{folder}/type.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split(" ", 2)
			d = AttackDetails.find_by_attack_code(code)
			d.pokemon_type_code = value unless d.pokemon_type_code
			d.save
		end
	end
	AttackDetails.find_each { |ad| ad.pokemon_type_code = 0 unless ad.pokemon_type_code; ad.save }
end

def process_natures
	if Nature.all.empty?
		Nature.create(code: 0, name: "Hardy")
		Nature.create(code: 1, name: "Lonely")
		Nature.create(code: 2, name: "Brave")
		Nature.create(code: 3, name: "Adamant")
		Nature.create(code: 4, name: "Naughty")
		Nature.create(code: 5, name: "Bold")
		Nature.create(code: 6, name: "Docile")
		Nature.create(code: 7, name: "Relaxed")
		Nature.create(code: 8, name: "Impish")
		Nature.create(code: 9, name: "Lax")
		Nature.create(code: 10, name: "Timid")
		Nature.create(code: 11, name: "Hasty")
		Nature.create(code: 12, name: "Serious")
		Nature.create(code: 13, name: "Jolly")
		Nature.create(code: 14, name: "Naive")
		Nature.create(code: 15, name: "Modest")
		Nature.create(code: 16, name: "Mild")
		Nature.create(code: 17, name: "Quiet")
		Nature.create(code: 18, name: "Bashful")
		Nature.create(code: 19, name: "Rash")
		Nature.create(code: 20, name: "Calm")
		Nature.create(code: 21, name: "Gentle")
		Nature.create(code: 22, name: "Sassy")
		Nature.create(code: 23, name: "Careful")
		Nature.create(code: 24, name: "Quirky")
	end
end
def process_pokes(folder)
	File.open "#{folder}/pokemons.txt", "r" do |f|
		f.each_line do |line|
			poke_id, value = line.split(" ", 2)
			dex,form = poke_id.split(":")
			value.
			PokemonName.create(dex_number: dex, form_number: form, name: value.chomp) if PokemonName.where(dex_number: dex, form_number: form).empty?
			Pokemon.create(dex_number: dex, form: form, pokemon_name_id: name.id) if Pokemon.where(dex_number: dex, form: form).empty?
		end
	end
	File.open "#{folder}/classification.txt", "r" do |f|
		f.each_line do |line|
			dex_number, value = line.split(" ", 2)
			name = PokemonName.where(dex_number: dex_number, form_number: 0).first
			name.classification = value if name.classification.nil?
			name.save
		end
	end
	File.open "#{folder}/egg_group_1.txt", "r" do |f|
		f.each_line do |line|
			dex_number, value = line.split(" ", 2)
			egg = if EggGroup.where(name: value).empty?
				EggGroup.create(name: value)
			else
				EggGroup.where(name: value).first
			end
			pok = Pokemon.find_by_dex_number(dex_number)
			pok.egg_group_1 = egg.id if pok.egg_group_1.nil?
			pok.save
		end
	end
	File.open "#{folder}/egg_group_2.txt", "r" do |f|
		f.each_line do |line|
			dex_number, value = line.split(" ", 2)
			egg = if EggGroup.where(name: value).empty?
				EggGroup.create(name: value)
			else
				EggGroup.where(name: value).first
			end
			pok = Pokemon.find_by_dex_number(dex_number)
			pok.egg_group_2 = egg.id if pok.egg_group_2.nil?
			pok.save
		end
	end
	File.open "#{folder}/evos.txt", "r" do |f|
		f.each_line do |line|
			values = line.split(" ")
			dex_number = values.shift
			values.each do |evo|
				poke = Pokemon.find_by_dex_number(dex_number)
				new_evo = Pokemon.find_by_dex_number(evo)
				Evolution.create(pokemon_id: poke.id, evolution_id: new_evo.id) if Evolution.where(pokemon_id: poke.id, evolution_id: new_evo.id).empty?
			end
		end
	end
	File.open "#{folder}/height.txt", "r" do |f|
		f.each_line do |line|
			poke_id, value = line.split(" ", 2)
			dex,form = poke_id.split(":")
			pok = Pokemon.where(dex_number: dex, form: form).first
			pok.height = value if pok.height.nil?
			pok.save
		end
	end
	File.open "#{folder}/weight.txt", "r" do |f|
		f.each_line do |line|
			poke_id, value = line.split(" ", 2)
			dex,form = poke_id.split(":")
			pok = Pokemon.where(dex_number: dex, form: form).first
			pok.weight = value if pok.weight.nil?
			pok.save
		end
	end
	File.open "#{folder}/description_16.txt", "r" do |f|
		f.each_line do |line|
			dex, value = line.split(" ", 2)
			pok = PokemonName.where(dex_number: dex, form_number: 0).first
			pok.description = value if pok.description.nil?
			pok.save
		end
	end
	folder = "#{folder}/6G"
	File.open "#{folder}/ability1.txt", "r" do |f|
		f.each_line do |line|
			poke_id, value = line.split(" ", 2)
			dex,form = poke_id.split(":")
			pok = Pokemon.where(dex_number: dex, form: form).first
			pok.ability_1 = value if pok.ability_1.nil?
			pok.save
		end
	end
	File.open "#{folder}/ability2.txt", "r" do |f|
		f.each_line do |line|
			poke_id, value = line.split(" ", 2)
			dex,form = poke_id.split(":")
			pok = Pokemon.where(dex_number: dex, form: form).first
			pok.ability_2 = value if pok.ability_2.nil?
			pok.save
		end
	end
	File.open "#{folder}/ability3.txt", "r" do |f|
		f.each_line do |line|
			poke_id, value = line.split(" ", 2)
			dex,form = poke_id.split(":")
			pok = Pokemon.where(dex_number: dex, form: form).first
			pok.ability_3 = value if pok.ability_3.nil?
			pok.save
		end
	end
	File.open "#{folder}/minlevels.txt", "r" do |f|
		f.each_line do |line|
			poke_id, value = line.split(" ")
			dex,form = poke_id.split(":")
			pok = Pokemon.where(dex_number: dex, form: form).first
			a,b = value.split("/")
			pok.min_level = (b.nil?) ? a : b unless pok.min_level
			pok.save
		end
	end
	File.open "#{folder}/stats.txt", "r" do |f|
		f.each_line do |line|
			values = line.split(" ")
			poke_id = values.shift
			dex, form = poke_id.split(":")
			pok = Pokemon.where(dex_number: dex, form: form).first
			hp, att, defe, spatt, spdef, speed = values
			pok.base_hp = hp unless pok.base_hp
			pok.base_attack = att unless pok.base_attack
			pok.base_defense = defe unless pok.base_defense
			pok.base_sp_attack = spatt unless pok.base_sp_attack
			pok.base_sp_defense = spdef unless pok.base_sp_defense
			pok.base_speed = speed unless pok.base_speed
			pok.save
		end
	end
	File.open "#{folder}/type1.txt", "r" do |f|
		f.each_line do |line|
			poke_id, value = line.split(" ", 2)
			dex,form = poke_id.split(":")
			pok = Pokemon.where(dex_number: dex, form: form).first
			pok.type_1 = value if pok.type_1.nil?
			pok.save
		end
	end
	File.open "#{folder}/type2.txt", "r" do |f|
		f.each_line do |line|
			poke_id, value = line.split(" ", 2)
			dex,form = poke_id.split(":")
			pok = Pokemon.where(dex_number: dex, form: form).first
			pok.type_2 = value if pok.type_2.nil?
			pok.save
		end
	end
	File.open "#{folder}/all_moves.txt", "r" do |f|
		f.each_line do |line|
			values = line.split(" ")
			poke_id = values.shift
			dex, form = poke_id.split(":")
			pok = Pokemon.where(dex_number: dex, form: form).first
			values.each do |attack|
				a = Attack.find_by_code(attack).id
				PokemonAttack.create(attack_id: a, pokemon_id: pok.id) if PokemonAttack.where(attack_id: a, pokemon_id: pok.id).empty?
			end
		end
	end
end
def process_types
	if(PokemonType.all.empty?)
		PokemonType.create(code: 0, name: "???")
		PokemonType.create(code: 1, name: "Normal")
		PokemonType.create(code: 2, name: "Fighting")
		PokemonType.create(code: 3, name: "Flying")
		PokemonType.create(code: 4, name: "Poison")
		PokemonType.create(code: 5, name: "Ground")
		PokemonType.create(code: 6, name: "Rock")
		PokemonType.create(code: 7, name: "Bug")
		PokemonType.create(code: 8, name: "Ghost")
		PokemonType.create(code: 9, name: "Steel")
		PokemonType.create(code: 10, name: "Fire")
		PokemonType.create(code: 11, name: "Water")
		PokemonType.create(code: 12, name: "Grass")
		PokemonType.create(code: 13, name: "Electric")
		PokemonType.create(code: 14, name: "Psychic")
		PokemonType.create(code: 15, name: "Ice")
		PokemonType.create(code: 16, name: "Dragon")
		PokemonType.create(code: 17, name: "Dark")
		PokemonType.create(code: 18, name: "Fairy")
		#Pokemons and AttackDetails have types:
		Pokemon.find_each do |poke|
			poke.type_1 = (poke.type_1 == 18) ? 0 : poke.type_1 + 1 if poke.type_1
			poke.type_2 = (poke.type_2 == 18) ? 0 : poke.type_2 + 1 if poke.type_2
			poke.save
		end
		AttackDetails.find_each do |attack|
			attack.pokemon_type_code = (attack.pokemon_type_code == 18) ? 0 : attack.pokemon_type_code + 1 if attack.pokemon_type_code
			attack.save
		end
	end
end
def process_versions(folder)
	File.open "#{folder}/versions.txt", "r" do |f|
		f.each_line do |line|
			code, value = line.split " ", 2
			gen, variant = code.split ":"
			Version.create(gen: gen, code: variant, name: value) if Version.where(gen: gen, code: variant, name: value).empty?
		end
	end
end

def fill_gaps
	Pokemon.where("form > 0").each do |poke|
		original = Pokemon.where(dex_number: poke.dex_number, form: 0).first
		poke.egg_group_1 = original.egg_group_1 unless poke.egg_group_1
		poke.egg_group_2 = original.egg_group_2 unless poke.egg_group_2
		poke.height = original.height unless poke.height
		poke.weight = original.weight unless poke.weight
		poke.ability_1 = original.ability_1 unless poke.ability_1
		poke.ability_2 = original.ability_2 unless poke.ability_2
		poke.ability_3 = original.ability_3 unless poke.ability_3
		poke.min_level = original.min_level unless poke.min_level
		poke.type_1 = original.type_1 unless poke.type_1
		poke.type_2 = original.type_2 unless poke.type_2
		poke.base_hp = original.base_hp unless poke.base_hp
		poke.base_attack = original.base_attack unless poke.base_attack
		poke.base_defense = original.base_defense unless poke.base_defense
		poke.base_sp_attack = original.base_sp_attack unless poke.base_sp_attack
		poke.base_sp_defense = original.base_sp_defense unless poke.base_sp_defense
		poke.base_speed = original.base_speed unless poke.base_speed
		poke.save
	end
end

process_abilities(abilities_folder)
process_categories
process_gens(gens_folder)
process_items(items_folder)
process_moves(moves_folder)
process_natures
process_pokes(pokes_folder)
process_types
process_versions(gens_folder)
fill_gaps