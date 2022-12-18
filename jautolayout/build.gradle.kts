plugins {
    id("java")
}

group = "org.JAutoLayout"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("junit:junit:4.13.2")
    implementation("org.rekex:rekex-parser:1.2.0")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}