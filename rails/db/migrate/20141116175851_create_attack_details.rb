class CreateAttackDetails < ActiveRecord::Migration
  def change
    create_table :attack_details do |t|
      t.integer :attack_code
      t.integer :accuracy
      t.integer :category_code
      t.integer :caused_effect
      t.integer :crit_rate
      t.integer :damage_class
      t.integer :effect_chance
      t.integer :flinch_chance
      t.integer :healing
      t.integer :max_turns
      t.integer :min_turns
      t.integer :power
      t.integer :pp
      t.integer :priority
      t.integer :recoil
      t.integer :status
      t.integer :type

      t.timestamps
    end
  end
end
