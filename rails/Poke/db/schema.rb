# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20141116211102) do

  create_table "abilities", force: true do |t|
    t.integer  "code"
    t.string   "name"
    t.string   "battle_description"
    t.string   "description"
    t.string   "message"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "attack_details", force: true do |t|
    t.integer  "attack_code"
    t.integer  "accuracy"
    t.integer  "category_code"
    t.integer  "caused_effect"
    t.integer  "crit_rate"
    t.integer  "damage_class"
    t.integer  "effect_chance"
    t.integer  "flinch_chance"
    t.integer  "healing"
    t.integer  "max_turns"
    t.integer  "min_turns"
    t.integer  "power"
    t.integer  "pp"
    t.integer  "priority"
    t.integer  "recoil"
    t.integer  "status"
    t.integer  "pokemon_type_code"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "attacks", force: true do |t|
    t.integer  "code"
    t.string   "description"
    t.string   "effect"
    t.string   "message"
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "berries", force: true do |t|
    t.integer  "berry_code"
    t.integer  "secret_power_type"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "categories", force: true do |t|
    t.integer  "code"
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "egg_groups", force: true do |t|
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "evolutions", force: true do |t|
    t.integer  "pokemon_id"
    t.integer  "evolution_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "gens", force: true do |t|
    t.integer  "code"
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "items", force: true do |t|
    t.integer  "code"
    t.string   "name"
    t.string   "description"
    t.integer  "secret_power"
    t.string   "message"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "natures", force: true do |t|
    t.integer  "code"
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "pokemon_attacks", force: true do |t|
    t.integer  "pokemon_id"
    t.integer  "attack_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "pokemon_names", force: true do |t|
    t.string   "name"
    t.string   "description"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "dex_number"
    t.integer  "form_number"
    t.string   "classification"
  end

  create_table "pokemon_types", force: true do |t|
    t.integer  "code"
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "pokemons", force: true do |t|
    t.integer  "dex_number"
    t.integer  "form"
    t.integer  "egg_group_1"
    t.integer  "egg_group_2"
    t.float    "height"
    t.integer  "pokemon_name_id"
    t.float    "weight"
    t.integer  "ability_1"
    t.integer  "ability_2"
    t.integer  "ability_3"
    t.integer  "min_level"
    t.integer  "base_hp"
    t.integer  "base_attack"
    t.integer  "base_defense"
    t.integer  "base_sp_attack"
    t.integer  "base_sp_defense"
    t.integer  "base_speed"
    t.integer  "type_1"
    t.integer  "type_2"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "versions", force: true do |t|
    t.integer  "gen"
    t.integer  "code"
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

end
