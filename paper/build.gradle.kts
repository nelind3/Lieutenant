repositories {
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
}

dependencies {
    api(project(":lieutenant-core")) {
        // Paper bundles brigadier and adventure
        exclude("com.mojang", "brigadier")
        exclude("net.kyori", "adventure-api")
    }
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
}
