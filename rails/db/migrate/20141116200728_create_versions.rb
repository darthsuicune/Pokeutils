class CreateVersions < ActiveRecord::Migration
  def change
    create_table :versions do |t|
      t.integer :gen
      t.integer :code
      t.string :name

      t.timestamps
    end
  end
end
