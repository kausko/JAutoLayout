import org.gradle.api.internal.file.pattern.PatternMatcherFactory.compile

plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":jautolayout"))
}