rootProject.name = "Lieutenant"

include(":lieutenant-core")
project(":lieutenant-core").projectDir = file("core")

include(":lieutenant-paper")
project(":lieutenant-paper").projectDir = file("paper")

include(":lieutenant-velocity")
project(":lieutenant-velocity").projectDir = file("velocity")
