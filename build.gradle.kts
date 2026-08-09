import org.jetbrains.intellij.platform.gradle.TestFrameworkType

val junitVersion: String = project.property("junitVersion") as String

plugins {
    id("java")
    id("org.jetbrains.intellij.platform")
    id("org.jetbrains.changelog") version "2.5.0"
}

dependencies {
    testImplementation("junit:junit:$junitVersion")

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        intellijIdea("2025.2.6.2")
        bundledPlugin("com.intellij.java")
        bundledPlugin("com.intellij.java.ide")
        testFramework(TestFrameworkType.Platform)
    }
}

changelog {
    path = "CHANGELOG.md"
}