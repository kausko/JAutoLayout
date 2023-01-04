plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
//    implementation(project(":jautolayout"))
    implementation("com.github.kausko:JAutoLayout:main-SNAPSHOT")
}

//tasks.jar {
//    manifest.attributes["Main-Class"] = "Demo"
//    manifest.attributes["Class-Path"] = configurations
//        .runtimeClasspath
//        .get()
//        .joinToString(separator = " ") { file ->
//            "libs/${file.name}"
//        }
//}