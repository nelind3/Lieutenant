plugins {
    id("java-library")
}

allprojects {
    plugins.apply("java-library")

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
}
