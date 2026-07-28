import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java")
    id("org.jetbrains.intellij.platform")
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        intellijIdea("2025.2.6.2")
        bundledPlugin("com.intellij.java")
        bundledPlugin("com.intellij.java.ide")
        testFramework(TestFrameworkType.Platform)
    }
}
