class CreateAttacks < ActiveRecord::Migration
  def change
    create_table :attacks do |t|
      t.integer :code
      t.string :description
      t.string :effect
      t.string :message
      t.string :name

      t.timestamps
    end
  end
end
