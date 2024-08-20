val kotlinVersion: String by project
val kotlinxCoroutinesVersion: String by project
val kotlinxSerializationVersion: String by project
val slf4jVersion: String by project
val junixsocketVersion: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `maven-publish`
}

group = "dev.cbyrne"
version = "0.2.2"

repositories {
    mavenCentral()
}

sourceSets {
    create("example")
}

val exampleImplementation by configurations
exampleImplementation.extendsFrom(configurations.implementation.get())

val shadowBundle by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")

    // SLF4J
    implementation("org.slf4j:slf4j-api:$slf4jVersion")

    // JunixSocket
    shadowBundle(implementation("com.kohlschutter.junixsocket:junixsocket-core:$junixsocketVersion")!!)

    // Example
    exampleImplementation(sourceSets.main.get().output)
    exampleImplementation("org.apache.logging.log4j:log4j-api:2.17.1")
    exampleImplementation("org.apache.logging.log4j:log4j-core:2.17.1")
    exampleImplementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.1")
}

tasks {
    shadowJar {
        configurations = listOf(shadowBundle)
        archiveClassifier.set("") // fix jitpack

        minimize()
    }

    jar {
        enabled = false
        dependsOn(shadowJar)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
