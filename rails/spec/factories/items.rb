# Read about factories at https://github.com/thoughtbot/factory_girl

FactoryGirl.define do
  factory :item do
    code 1
    name "MyString"
    description "MyString"
    secret_power 1
    message "MyString"
  end
end
