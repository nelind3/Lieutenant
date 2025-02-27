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

    compileOnly(libs.velocity.api)
}
