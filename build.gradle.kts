plugins {
    id("java")
    id("io.freefair.lombok") version "8.6"
    id("org.springframework.boot") version "3.3.1"
    id("info.solidsoft.pitest") version "1.15.0"
}

group = "org.lukario"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.3.1")

}

tasks.test {
    useJUnitPlatform()
}

pitest {
    junit5PluginVersion="1.2.1"
    pitestVersion = "1.15.2"
    outputFormats.addAll("HTML", "XML", "CSV")
    exportLineCoverage = true
    //TODO: let's get these close to 100
    coverageThreshold = 80
    mutationThreshold = 80
    testStrengthThreshold = 90
    mutators.add("ALL")
}