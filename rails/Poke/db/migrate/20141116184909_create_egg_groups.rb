class CreateEggGroups < ActiveRecord::Migration
  def change
    create_table :egg_groups do |t|
      t.string :name

      t.timestamps
    end
  end
end
