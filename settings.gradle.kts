plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "classcharts-api"

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        google()

        maven {
            name = "Sonatype Snapshots (Legacy)"
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }

        maven {
            name = "Sonatype Snapshots"
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
        }

    }
}