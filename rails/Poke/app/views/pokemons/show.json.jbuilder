json.array! (@all_forms) do |form|
  json.extract! form, :dex_number, :form, :egg_group_1, :egg_group_2, :height, :weight, :abilities, :min_level, :baseStats, :types, :attacks
end
