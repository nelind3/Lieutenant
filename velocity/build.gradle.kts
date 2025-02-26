plugins {
    id("com.gradleup.shadow") version "8.3.6"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
}

dependencies {
    api(project(":lieutenant-core")) {
        // Velocity bundles brigadier and adventure
        exclude("com.mojang", "brigadier")
        exclude("net.kyori", "adventure-api")
    }

    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
}
