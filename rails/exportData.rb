def writePokemons
	last = Pokemon.last.dex_number
	(0..last).each do |dex|
		forms = []
		Pokemon.where(dex_number: dex).each do |form|
			forms << form.as_json(methods: [:attack_codes,:baseStats ])
		end
		writeToFile(:pokemon, dex, forms.to_json)
	end
end

def writeAttacks
end

def writeToFile(what, id, content)
	puts content
end

writePokemons
writeAttacks