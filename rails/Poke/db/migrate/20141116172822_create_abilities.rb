class CreateAbilities < ActiveRecord::Migration
  def change
    create_table :abilities do |t|
      t.integer :code
      t.string :name
      t.string :battle_description
      t.string :description
      t.string :message

      t.timestamps
    end
  end
end
