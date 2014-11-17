class ChangeTypeInAttackDetails < ActiveRecord::Migration
  def change
	  rename_column :attack_details, :type, :pokemon_type_code
  end
end
