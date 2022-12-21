plugins {
    id("java-library")
    id("maven-publish")
}

group = "org.JAutoLayout"
version = "master-SNAPSHOT"

publishing {
    publications {
        register("maven", MavenPublication::class) {
            from(components["java"])
        }
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.0")
    implementation("org.rekex:rekex-parser:1.2.0")
    implementation("com.github.kausko:kiwi-java:master-SNAPSHOT")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}