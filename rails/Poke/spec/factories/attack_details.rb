# Read about factories at https://github.com/thoughtbot/factory_girl

FactoryGirl.define do
  factory :attack_detail, :class => 'AttackDetails' do
    attack_code 1
    accuracy 1
    category_code 1
    caused_effect 1
    crit_rate 1
    damage_class 1
    effect_chance 1
    flinch_chance 1
    healing 1
    max_turns 1
    min_turns 1
    power 1
    pp 1
    priority 1
    recoil 1
    status 1
    type 1
  end
end
