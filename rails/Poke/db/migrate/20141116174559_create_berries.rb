class CreateBerries < ActiveRecord::Migration
  def change
    create_table :berries do |t|
      t.integer :berry_code
      t.integer :secret_power_type

      t.timestamps
    end
  end
end
