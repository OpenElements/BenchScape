plugins {
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "com.open-elements.benchscape"
version = "0.4.0-SNAPSHOT"
description = "BenchScape JMH Gradle Plugin"

gradlePlugin.plugins.register("jmh-gradle-plugin") {
    id = "com.open-elements.benchscape.jmh"
    implementationClass = "com.openelements.benchscape.jmh.gradle.JmhPlugin"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

// TODO publish repo for SNAPSHOT
