plugins {
    id("java-library")
    id("maven-publish")
}

allprojects {
    plugins.apply("java-library")
    plugins.apply("maven-publish")

    group = "dk.nelind"
    version = "${rootProject.properties["version"]}"

    val targetJavaVersion = 21

    tasks.withType(JavaCompile::class.java).configureEach {
        options.encoding = "UTF-8"
        options.release.set(targetJavaVersion)
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }

    repositories {
        mavenCentral()
        maven("https://libraries.minecraft.net/")
    }

    publishing {
        repositories {
            val username = "NELIND_MAVEN_USERNAME".let { System.getenv(it) ?: findProperty(it) }?.toString()
            val password = "NELIND_MAVEN_PASSWORD".let { System.getenv(it) ?: findProperty(it) }?.toString()
            if (username != null && password != null) {
                maven("https://maven.nelind.dk/releases") {
                    name = "NelindReleases"
                    credentials {
                        this.username = username
                        this.password = password
                    }
                }
            } else {
                println("Nelind Maven credentials not present. Repository not added as publication destination")
            }
        }

        publications.create<MavenPublication>("main") {
            from(components["java"])
        }
    }
}
