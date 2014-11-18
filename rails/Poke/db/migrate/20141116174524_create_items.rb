class CreateItems < ActiveRecord::Migration
  def change
    create_table :items do |t|
      t.integer :code
      t.string :name
      t.string :description
      t.integer :secret_power
      t.string :message

      t.timestamps
    end
  end
end
