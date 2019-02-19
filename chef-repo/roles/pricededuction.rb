name        "pricededuction"
description "App for pricededuction"
run_list    "recipe[ewe-s3curl]","recipe[pricededuction]"

override_attributes(
 "java" => {
    "jdk_name" => "jdk8_0_05",
  }
)
