#deploy app

node.default[:springboot][:application_name] = 'pricededuction'
node.default[:springboot][:role_name] = 'pricededuction'

include_recipe "expedia-platform::springboot"
