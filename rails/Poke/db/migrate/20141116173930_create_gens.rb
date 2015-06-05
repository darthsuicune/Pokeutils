class CreateGens < ActiveRecord::Migration
  def change
    create_table :gens do |t|
      t.integer :code
      t.string :name

      t.timestamps
    end
  end
end
