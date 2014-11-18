#run with:
# bundle exec rails runner "eval(File.read 'exportData.rb')"
require 'jbuilder'

def writePokemons
	last = Pokemon.last.dex_number
	json = JBuilder.new
	(0..last).each do |dex|
		json.array! (dex) do |form|
			json.extract! form, :dex_number, :form, :egg_group_1, :egg_group_2, :height, :weight, :abilities, :min_level, :baseStats, :types, :attacks
		end
	end
end

def writeAttacks
end



writePokemons
writeAttacks